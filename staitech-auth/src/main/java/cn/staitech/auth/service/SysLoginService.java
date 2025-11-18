package cn.staitech.auth.service;

import cn.staitech.auth.elasticsearchRepositories.LoginInfoLogESRepository;
import cn.staitech.auth.form.out.PublicKeyGetOut;
import cn.staitech.common.core.utils.DateUtils;
import cn.staitech.common.core.utils.RSAUtils;
import cn.staitech.common.redis.service.RedisService;
import cn.staitech.common.security.service.TokenService;
import cn.staitech.system.api.domain.document.SysLoginInfoDoc;
import cn.staitech.system.api.model.OrganizationAuthorization;
import cn.staitech.system.api.model.OrganizationRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import cn.staitech.common.core.constant.Constants;
import cn.staitech.common.core.constant.SecurityConstants;
import cn.staitech.common.core.constant.UserConstants;
import cn.staitech.common.core.domain.R;
import cn.staitech.common.core.enums.UserStatus;
import cn.staitech.common.core.exception.ServiceException;
import cn.staitech.common.core.utils.ServletUtils;
import cn.staitech.common.core.utils.StringUtils;
import cn.staitech.common.core.utils.ip.IpUtils;
import cn.staitech.common.security.utils.SecurityUtils;
import cn.staitech.system.api.RemoteLogService;
import cn.staitech.system.api.RemoteUserService;
import cn.staitech.system.api.domain.SysLogininfor;
import cn.staitech.system.api.domain.SysUser;
import cn.staitech.system.api.model.LoginUser;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.KeyPair;
import java.util.Date;
import java.util.Objects;

import static cn.staitech.common.core.constant.UserConstants.RSA_KEYS;

/**
 * 登录校验方法
 *
 * @author staitech
 */
@Component
public class SysLoginService {

    @Autowired
    private RedisService redisService;

    @Autowired
    public RedisTemplate redisTemplate;

    @Autowired
    private RemoteLogService remoteLogService;

    @Autowired
    private RemoteUserService remoteUserService;

    @Resource
    private LoginInfoLogESRepository loginInfoLogESRepository;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Boolean userLoginVerify(String username, String pwd) {
        // 获取密钥对解密密码
        String cacheObject = redisService.getCacheObject(RSA_KEYS + username);
        if (Objects.isNull(cacheObject)) {
            throw new ServiceException("当前用户名和密码错误，请核对");
        }
        String password;
        //解析
        try {
            password = RSAUtils.getStringByPrivateKey(cacheObject, pwd);
        } catch (Exception e) {
            throw new ServiceException("密码解密异常" + e);
        }
        //每次解析完删除密钥对
        redisService.deleteObject(RSA_KEYS + username);
        //
        //log.info("解密后的密码" + password);
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password)) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "用户/密码必须填写");
            throw new ServiceException("用户/密码必须填写");
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "用户密码不在指定范围");
            throw new ServiceException("用户密码不在指定范围");
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "用户名不在指定范围");
            throw new ServiceException("用户名不在指定范围");
        }
        // 查询用户信息
        R<LoginUser> userResult = remoteUserService.getUserInfo(username, SecurityConstants.INNER);
        //远程接口调用异常
        if (R.FAIL == userResult.getCode()) {
            throw new ServiceException(userResult.getMsg());
        }
        //用户数据为空
        if (StringUtils.isNull(userResult) || StringUtils.isNull(userResult.getData())) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "登录用户不存在");
            throw new ServiceException("登录用户：" + username + " 不存在");
        }

        SysUser user = userResult.getData().getSysUser();
        if (UserStatus.DISABLE.getCode().equals(user.getDelFlag())) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "对不起，您的账号已被删除");
            throw new ServiceException("对不起，您的账号：" + username + " 已被删除");
        }
        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "用户已停用，请联系管理员");
            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }
        if (!SecurityUtils.matchesPassword(password, user.getPassword())) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "用户密码错误");
            throw new ServiceException("用户不存在/密码错误");
        }
        return true;
    }

    /**
     * 登录
     */
    public LoginUser login(String username, String pwd) {
        //获取密钥对解密密码
        String cacheObject = redisService.getCacheObject(RSA_KEYS + username);
        if (Objects.isNull(cacheObject)) {
            throw new ServiceException("获得redis密钥对异常");
        }
        String password;
        //解析
        try {
            password = RSAUtils.getStringByPrivateKey(cacheObject, pwd);
            //passwordEncoder.matches(pwd,"");
        } catch (Exception e) {
            throw new ServiceException("密码解密异常" + e);
        }
        //每次解析完删除密钥对
        redisService.deleteObject(RSA_KEYS + username);

        //log.info("解密后的密码" + password);
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password)) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "用户/密码必须填写");
            throw new ServiceException("用户/密码必须填写");
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "用户密码不在指定范围");
            throw new ServiceException("用户密码不在指定范围");
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "用户名不在指定范围");
            throw new ServiceException("用户名不在指定范围");
        }
        // 查询用户信息
        R<LoginUser> userResult = remoteUserService.getUserInfo(username, SecurityConstants.INNER);
        //远程接口调用异常
        if (R.FAIL == userResult.getCode()) {
            throw new ServiceException(userResult.getMsg());
        }
        //用户数据为空
        if (StringUtils.isNull(userResult) || StringUtils.isNull(userResult.getData())) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "登录用户不存在");
            throw new ServiceException("登录用户：" + username + " 不存在");
        }
        //登陆人数据
        LoginUser userInfo = userResult.getData();
        SysUser user = userResult.getData().getSysUser();
        if (!SecurityUtils.matchesPassword(password, user.getPassword())) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "用户密码错误");
            throw new ServiceException("密码错误");
        }
        if (UserStatus.DISABLE.getCode().equals(user.getDelFlag())) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "对不起，您的账号已被删除");
            throw new ServiceException("对不起，您的账号：" + username + " 已被删除");
        }
        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            recordLogininfor(username, Constants.LOGIN_FAIL, "用户已停用，请联系管理员");
            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }
        if (!user.getUserName().equalsIgnoreCase("admin")) {
            //机构id
            long organizationId = user.getOrganizationId();

            //查询结构是否可用
            R<OrganizationRes> organizationSelectRes = remoteUserService.getOrganizationById(organizationId + "", SecurityConstants.INNER);
            //远程接口调用异常
            if (R.FAIL == organizationSelectRes.getCode()) {
                throw new ServiceException(organizationSelectRes.getMsg());
            }
            //机构数据
            OrganizationRes resData = organizationSelectRes.getData();

            //机构数据为空
            if (StringUtils.isNull(organizationSelectRes) || StringUtils.isNull(organizationSelectRes.getData())) {
                recordLogininfor(username, Constants.LOGIN_FAIL, "当前机构授权不存在，请联系管理员");
                throw new ServiceException("登录用户：" + username + " 机构不存在");
            }

            long status = resData.getStatus();
            long delFlag = resData.getDelFlag();

            if (status == 1 || delFlag == 2) {
                //失效
                recordLogininfor(username, Constants.LOGIN_FAIL, "当前机构授权已到期，请联系管理员");
                throw new ServiceException("登录用户：" + username + " 当前机构授权已到期，请联系管理员");
            }

            //查询结构是否过期
            R<OrganizationAuthorization> organizationResult = remoteUserService.selectByOrganizationId(organizationId + "", SecurityConstants.INNER);
            //远程接口调用异常
            if (R.FAIL == organizationResult.getCode()) {
                throw new ServiceException(organizationResult.getMsg());
            }
            //机构数据
            OrganizationAuthorization organizationAuthorization = organizationResult.getData();

            //机构数据为空
            if (StringUtils.isNull(organizationResult) || StringUtils.isNull(organizationResult.getData())) {
                recordLogininfor(username, Constants.LOGIN_FAIL, "当前机构授权不存在，请联系管理员");
                throw new ServiceException("登录用户：" + username + " 机构不存在");
            }

            String expirationTime = organizationAuthorization.getExpirationTime();
            Date expirationTimeDate = DateUtils.dateTime("yyyy-MM-dd HH:mm:ss", expirationTime);
            if (expirationTimeDate.compareTo(new Date()) < 1) {
                //失效
                recordLogininfor(username, Constants.LOGIN_FAIL, "当前机构授权已到期，请联系管理员");
                throw new ServiceException("登录用户：" + username + " 当前机构授权已到期，请联系管理员");
            }
        }
        recordLogininfor(username, Constants.LOGIN_SUCCESS, "登录成功");
        return userInfo;
    }

    public void logout(String loginName) {
        recordLogininfor(loginName, Constants.LOGOUT, "退出成功");
    }

    /**
     * 注册
     */
    public void register(String username, String password) {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password)) {
            throw new ServiceException("用户/密码必须填写");
        }
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            throw new ServiceException("账户长度必须在2到20个字符之间");
        }
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            throw new ServiceException("密码长度必须在5到20个字符之间");
        }

        // 注册用户信息
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);
        sysUser.setNickName(username);
        sysUser.setPassword(SecurityUtils.encryptPassword(password));
        R<?> registerResult = remoteUserService.registerUserInfo(sysUser, SecurityConstants.INNER);

        if (R.FAIL == registerResult.getCode()) {
            throw new ServiceException(registerResult.getMsg());
        }
        recordLogininfor(username, Constants.REGISTER, "注册成功");
    }

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息内容
     * @return
     */
    public void recordLogininfor(String username, String status, String message) {
        SysLogininfor logininfor = new SysLogininfor();
        logininfor.setUserName(username);
        logininfor.setIpaddr(IpUtils.getIpAddr(ServletUtils.getRequest()));
        logininfor.setMsg(message);
        // 日志状态
        if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
            logininfor.setStatus(Constants.LOGIN_SUCCESS_STATUS);
        } else if (Constants.LOGIN_FAIL.equals(status)) {
            logininfor.setStatus(Constants.LOGIN_FAIL_STATUS);
        }
        SysLoginInfoDoc loginInfoDoc = new SysLoginInfoDoc();
        BeanUtils.copyProperties(logininfor, loginInfoDoc);
        loginInfoDoc.setTime(DateUtils.getNowDate().getTime());
        loginInfoDoc.setAccessTime(DateUtils.dateTimeNow("yyyy-MM-dd HH:mm:ss"));
        String browser = request.getHeader("user-agent");
        loginInfoDoc.setInfoId(DateUtils.getNowDate().getTime());
        loginInfoDoc.setBrowser(browser);
        loginInfoLogESRepository.save(loginInfoDoc);
        remoteLogService.saveLogininfor(logininfor, SecurityConstants.INNER);
    }

    /**
     * 获得公钥
     *
     * @param userName
     * @return
     */
    public PublicKeyGetOut getPublicKey(String userName) {
        //创建相应
        PublicKeyGetOut resp = new PublicKeyGetOut();
        //获得公钥私钥
        KeyPair keyPair = RSAUtils.generateKeyPair();
        String admin123 = RSAUtils.encryptByPublicKey("Admin123", keyPair);
        System.out.println(admin123);
        String publicKey = RSAUtils.getPublicKey(keyPair);
        String privateKey = RSAUtils.getPrivateKey(keyPair);
        redisService.setCacheObject(RSA_KEYS + userName, privateKey);
        resp.setPublicKey(publicKey);
        //相应
        return resp;
    }
}