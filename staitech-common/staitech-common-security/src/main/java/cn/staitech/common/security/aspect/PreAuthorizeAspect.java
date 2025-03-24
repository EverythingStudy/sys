package cn.staitech.common.security.aspect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import cn.staitech.common.security.annotation.RequiresLogin;
import cn.staitech.common.security.annotation.RequiresPermissions;
import cn.staitech.common.security.annotation.RequiresRoles;
import cn.staitech.common.security.annotation.RequiresSpecialPermissions;
import cn.staitech.common.security.auth.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 基于 Spring Aop 的注解鉴权
 *
 * @author kong
 */
@Slf4j
@Aspect
@Component
public class PreAuthorizeAspect {

    /**
     * 构建
     */
    public PreAuthorizeAspect() {
    }

    /**
     * 定义AOP签名 (切入所有使用鉴权注解的方法)
     */
    public static final String POINTCUT_SIGN = " @annotation(cn.staitech.common.security.annotation.RequiresLogin) || "
            + "@annotation(cn.staitech.common.security.annotation.RequiresPermissions) || "
            + "@annotation(cn.staitech.common.security.annotation.RequiresSpecialPermissions) || "
            + "@annotation(cn.staitech.common.security.annotation.RequiresRoles)";

    /**
     * 声明AOP签名
     */
    @Pointcut(POINTCUT_SIGN)
    public void pointcut() {
    }

    /**
     * 环绕切入
     *
     * @param joinPoint 切面对象
     * @return 底层方法执行后的返回值
     * @throws Throwable 底层方法抛出的异常
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] paramsObject = joinPoint.getArgs();
        Long value = 0L;
        for (Object object : paramsObject) {
            if (object != null) {
                String checkField = checkField(object);
                if (checkField != null) {
                    value = (Long) getFieldValueByName(object, checkField);
                    // System.out.println("-------------------specialId-------------------" + value);
                }
                // System.out.println("------------------------" + object);
            }
        }
        // 注解鉴权
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        checkMethodAnnotation(signature.getMethod(), value);
        try {
            // 执行原有逻辑
            Object obj = joinPoint.proceed();
            return obj;
        } catch (Throwable e) {
            throw e;
        }
    }

    /**
     * 判断是否存在某对象是否具有某属性
     *
     * @param object
     * @return boolean
     */
    private static String checkField(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            if ("specialId".equals(fieldName)) {
                return fieldName;
            }
        }
        return null;
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
     * 对一个Method对象进行注解检查
     */
    public void checkMethodAnnotation(Method method, Long specialId) {
        // 校验 @RequiresLogin 注解
        RequiresLogin requiresLogin = method.getAnnotation(RequiresLogin.class);
        if (requiresLogin != null) {
            AuthUtil.checkLogin();
        }

        // 校验 @RequiresRoles 注解
        RequiresRoles requiresRoles = method.getAnnotation(RequiresRoles.class);
        if (requiresRoles != null) {
            AuthUtil.checkRole(requiresRoles);
        }

        // 校验 @RequiresPermissions 注解
        RequiresPermissions requiresPermissions = method.getAnnotation(RequiresPermissions.class);
        if (requiresPermissions != null) {
            AuthUtil.checkPermi(requiresPermissions);
        }

        // 校验 @RequiresSpecialPermissions 注解
        RequiresSpecialPermissions requiresSpecialPermissions = method.getAnnotation(RequiresSpecialPermissions.class);
        if (requiresSpecialPermissions != null) {
            AuthUtil.checkSpecialPermi(requiresSpecialPermissions, specialId);
        }
    }
}
