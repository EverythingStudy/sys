package cn.staitech.common.security.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cn.staitech.common.core.constant.CacheConstants;
import cn.staitech.common.core.constant.SecurityConstants;
import cn.staitech.common.core.utils.JwtUtils;
import cn.staitech.common.core.utils.ServletUtils;
import cn.staitech.common.core.utils.StringUtils;
import cn.staitech.common.core.utils.ip.IpUtils;
import cn.staitech.common.core.utils.uuid.IdUtils;
import cn.staitech.common.redis.service.RedisService;
import cn.staitech.common.security.utils.SecurityUtils;
import cn.staitech.system.api.model.LoginUser;

/**
 * token验证处理
 *
 * @author staitech
 */
@Component
public class TokenService {
    @Autowired
    private RedisService redisService;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private final static long expireTime = CacheConstants.EXPIRATION;

    private final static String ACCESS_TOKEN = CacheConstants.LOGIN_TOKEN_KEY;

    private final static Long MILLIS_MINUTE_TEN = CacheConstants.REFRESH_TIME * MILLIS_MINUTE;


    private static final Logger log = LoggerFactory.getLogger(TokenService.class);


    /**
     * 创建令牌
     * 1.redis存储用户信息
     * 2.生成token
     */
    public Map<String, Object> createToken(LoginUser loginUser) {
        String token = IdUtils.fastUUID();
        Long userId = loginUser.getSysUser().getUserId();
        String userName = loginUser.getSysUser().getUserName();
        loginUser.setToken(token);
        loginUser.setUserid(userId);
        loginUser.setUsername(userName);
        loginUser.setIpaddr(IpUtils.getIpAddr(ServletUtils.getRequest()));

        refreshToken(loginUser, true);

        // Jwt存储信息
        Map<String, Object> claimsMap = new HashMap<String, Object>();
        claimsMap.put(SecurityConstants.USER_KEY, token);
        claimsMap.put(SecurityConstants.DETAILS_USER_ID, userId);
        claimsMap.put(SecurityConstants.DETAILS_USERNAME, userName);

        // 接口返回信息
        Map<String, Object> rspMap = new HashMap<String, Object>();
        rspMap.put("access_token", JwtUtils.createToken(claimsMap));
        rspMap.put("expires_in", expireTime);
        return rspMap;
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser() {
        return getLoginUser(ServletUtils.getRequest());
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = SecurityUtils.getToken(request);
        return getLoginUser(token);
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(String token) {
        LoginUser user = null;
        try {
            if (StringUtils.isNotEmpty(token)) {
                String userkey = JwtUtils.getUserKey(token);
                user = redisService.getCacheObject(getTokenKey(userkey));
                return user;
            }
        } catch (Exception e) {
        }
        return user;
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginUser loginUser) {
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken())) {
            refreshToken(loginUser, false);
        }
    }

    /**
     * 删除用户缓存信息
     */
    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            String userkey = JwtUtils.getUserKey(token);
            String userName = JwtUtils.getUserName(token);
            log.error("TokenService delete redis user token:{}", getTokenKey(userkey));
            redisService.deleteObject(getTokenKey(userkey));
            redisService.deleteObject(getTokenKey(userName));
        }
    }

    /**
     * 验证令牌有效期，相差不足120分钟，自动刷新缓存
     *
     * @param loginUser
     */
    public void verifyToken(LoginUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(loginUser, false);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     *                  1、判断是不是登录是登陆进行判断操作
     *                  2、不是登录维持原操作刷新时长即可
     */
    public void refreshToken(LoginUser loginUser, boolean loginFlag) {

        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        String userNameKey = getTokenKey(loginUser.getUsername());

        if (loginFlag) {
            String cacheObject = redisService.getCacheObject(userNameKey);
            //如果已经登陆了，删掉之前的数据
            if (!StringUtils.isNull(cacheObject)) {
                redisService.deleteObject(cacheObject);
            }
            //写新的信息
            log.error("TokenService delete redis user refreshToken:{},{},{},{}", userKey, loginUser, expireTime, TimeUnit.MINUTES);
            redisService.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
            log.error("TokenService delete redis user refreshToken:{},{},{},{}", userNameKey, userKey, expireTime, TimeUnit.MINUTES);
            redisService.setCacheObject(userNameKey, userKey, expireTime, TimeUnit.MINUTES);

        } else {
            log.error("TokenService delete redis user refreshToken:{},{},{},{}", userKey, loginUser, expireTime, TimeUnit.MINUTES);
            redisService.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
            log.error("TokenService delete redis user refreshToken:{},{},{},{}", userNameKey, userKey, expireTime, TimeUnit.MINUTES);
            redisService.setCacheObject(userNameKey, userKey, expireTime, TimeUnit.MINUTES);
        }

    }

    private String getTokenKey(String token) {
        return ACCESS_TOKEN + token;
    }
}