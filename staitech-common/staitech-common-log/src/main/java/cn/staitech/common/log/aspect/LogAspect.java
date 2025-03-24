package cn.staitech.common.log.aspect;

import cn.staitech.common.core.constant.LogConstants;
import cn.staitech.common.core.utils.DateUtils;
import cn.staitech.common.core.utils.ServletUtils;
import cn.staitech.common.core.utils.StringUtils;
import cn.staitech.common.core.utils.ip.IpUtils;
import cn.staitech.common.log.annotation.Log;
import cn.staitech.common.log.elasticsearchRepositories.OperLogESRepository;
import cn.staitech.common.log.enums.BusinessStatus;
import cn.staitech.common.log.service.AsyncLogService;
import cn.staitech.common.security.utils.SecurityUtils;
import cn.staitech.system.api.domain.SysOperLog;
import cn.staitech.system.api.domain.document.SysOperLogDoc;
import cn.staitech.system.api.model.LoginUser;
import com.alibaba.fastjson2.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * 操作日志记录处理
 *
 * @author staitech
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private AsyncLogService asyncLogService;
    @Resource
    private OperLogESRepository operLogESRepository;

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log controllerLog, Object jsonResult) {
        handleLog(joinPoint, controllerLog, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log controllerLog, Exception e) {
        handleLog(joinPoint, controllerLog, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, Log controllerLog, final Exception e, Object jsonResult) {
        try {
            // *========数据库日志=========*//
            SysOperLog operLog = new SysOperLog();
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            // 请求的地址
            String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
            operLog.setOperIp(ip);
            operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            LoginUser loginUser = SecurityUtils.getLoginUser();
            String username = loginUser.getSysUser().getUserName();
            Long userId = loginUser.getSysUser().getUserId();
            if (StringUtils.isNotBlank(username)) {
                operLog.setOperName(username);
            }
            if (StringUtils.isNotNull(userId)) {
                operLog.setUserId(userId);
            }
            if (e != null) {
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, operLog, jsonResult);
            // 保存数据库
            asyncLogService.saveSysLog(operLog);
            SysOperLogDoc sysOperLogDoc = new SysOperLogDoc();
            BeanUtils.copyProperties(operLog, sysOperLogDoc);
            //es日志添加菜单信息
            sysOperLogDoc.setMenu(controllerLog.menu());
            sysOperLogDoc.setSubMenu(controllerLog.subMenu());
            sysOperLogDoc.setOperId(DateUtils.getNowDate().getTime());
            //判断是否修改，如修改before_updating  | 更新前数据 ，| after_updating    | 更新后数据
            sysOperLogDoc.setTime(DateUtils.getNowDate().getTime());
            sysOperLogDoc.setAccessTime(DateUtils.dateTimeNow("yyyy-MM-dd HH:mm:ss"));
            sysOperLogDoc.setAfterUpdating(operLog.getJsonResult());
            sysOperLogDoc.setBeforeUpdating(joinPoint.getArgs().toString());
            operLogESRepository.save(sysOperLogDoc);
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("前置通知异常 异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log     日志
     * @param operLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, SysOperLog operLog, Object jsonResult) throws Exception {
        // 设置action动作
        operLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        operLog.setTitle(log.title());
        // 设置操作人类别
        operLog.setOperatorType(log.operatorType().ordinal());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, operLog);
        }
        // 是否需要保存response，参数和值
        if (log.isSaveResponseData() && StringUtils.isNotNull(jsonResult)) {
            operLog.setJsonResult(StringUtils.substring(JSON.toJSONString(jsonResult), 0, 2000));
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint, SysOperLog operLog) throws Exception {
        String requestMethod = operLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            Object[] paramsArray = joinPoint.getArgs();

            if (paramsArray != null && paramsArray.length > 0) {
                for (Object o : paramsArray) {
                    if (StringUtils.isNotNull(o) && !isFilterObject(o)) {
                        try {
                            // 判断某对象是否具有某属性
                            String fieldName = checkField(o);
                                /*if(fieldName != null){
                                    // 通过反射访问Object获取对象属性值
                                    Long fieldValue = Long.valueOf(getFieldValueByName(o,fieldName).toString());
                                    switch (fieldName){
                                        case LogConstants.projectId:
                                            operLog.setProjectId(fieldValue);
                                            break;
                                        case LogConstants.slideId:
                                            operLog.setSlideId(fieldValue);
                                            break;
                                        default:
                                            break;
                                    }
                                }*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            String params = argsArrayToString(paramsArray);
            operLog.setOperParam(StringUtils.substring(params, 0, 2000));
        }
    }

    /**
     * 通过反射访问Object获取对象属性值
     *
     * @param object    对象
     * @param fieldName 字段名称
     */
    public static Object getFieldValueByName(Object object, String fieldName) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = object.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(object, new Object[]{});
            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断某对象是否具有某属性
     *
     * @param object
     * @return String
     */
    private static String checkField(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            switch (fieldName) {
                case LogConstants.projectId:
                case LogConstants.slideId:
                    return fieldName;
                default:
                    break;
            }
        }
        return null;
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (StringUtils.isNotNull(o) && !isFilterObject(o)) {
                    try {
                        Object jsonObj = JSON.toJSON(o);
                        params += jsonObj.toString() + " ";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }
}
