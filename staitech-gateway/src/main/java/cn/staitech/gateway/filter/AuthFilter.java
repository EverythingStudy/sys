package cn.staitech.gateway.filter;

import cn.staitech.common.core.constant.CacheConstants;
import cn.staitech.common.core.constant.HttpStatus;
import cn.staitech.common.core.constant.SecurityConstants;
import cn.staitech.common.core.constant.TokenConstants;
import cn.staitech.common.core.utils.JwtUtils;
import cn.staitech.common.core.utils.ServletUtils;
import cn.staitech.common.core.utils.StringUtils;
import cn.staitech.common.redis.service.RedisService;
import cn.staitech.gateway.config.properties.IgnoreWhiteProperties;
import cn.staitech.gateway.util.LanguageUtils;
import cn.staitech.gateway.util.MessageSource;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关鉴权
 *
 * @author staitech
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

    // 排除过滤的 uri 地址，nacos自行添加
    @Autowired
    private IgnoreWhiteProperties ignoreWhite;

    @Autowired
    private RedisService redisService;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder mutate = request.mutate();

        String url = request.getURI().getPath();

        // 跳过不需要验证的路径
        if (StringUtils.matches(url, ignoreWhite.getWhites())) {
            // log.info("ignoreWhite:{}",url);
            return chain.filter(exchange);
        }

        // 获取中英文标识
        String language = LanguageUtils.getLanguage(request);

        // 获取剪裁prefix前的token
        String token = getToken(request);
        if (StringUtils.isEmpty(token)) {
            return unauthorizedResponse(exchange, MessageSource.M("TOKEN_IS_NULL", language));
        }

        // 如果前端设置了令牌前缀，则裁剪掉前缀
        token = getNoPrefixToken(token);
        if (token == null) {
            return unauthorizedResponse(exchange, MessageSource.M("INCORRECT_TOKEN_PREFIX",language));
        }

        Claims claims = null;
        try {
            claims = JwtUtils.parseToken(token);
        } catch (Exception e) {
            return unauthorizedResponse(exchange, MessageSource.M("TOKEN_CANNOT_BE_PARSED_CORRECTLY",language) + e.getMessage());
        }

        if (claims == null) {
            return unauthorizedResponse(exchange, MessageSource.M("TOKEN_HAS_EXPIRED_OR_VERIFICATION_IS_INCORRECT",language));
        }
        String userkey = JwtUtils.getUserKey(claims);
        boolean islogin = redisService.hasKey(getTokenKey(userkey));
        if (!islogin) {
          if("zh-cn".equals(language)){
              return unauthorizedResponse(exchange, "登录状态已过期");
          }else{
              return unauthorizedResponse(exchange, "LOGIN_STATUS_HAS_EXPIRED");
          }

        }
        String userid = JwtUtils.getUserId(claims);
        String username = JwtUtils.getUserName(claims);
        if (StringUtils.isEmpty(userid) || StringUtils.isEmpty(username)) {
            return unauthorizedResponse(exchange, MessageSource.M("TOKEN_VERIFICATION_FAILED",language));
        }

        // 设置用户信息到请求
        addHeader(mutate, SecurityConstants.USER_KEY, userkey);
        addHeader(mutate, SecurityConstants.DETAILS_USER_ID, userid);
        addHeader(mutate, SecurityConstants.DETAILS_USERNAME, username);
        // 内部请求来源参数清除
        removeHeader(mutate, SecurityConstants.FROM_SOURCE);
        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }

    private void addHeader(ServerHttpRequest.Builder mutate, String name, Object value) {
        if (value == null) {
            return;
        }
        String valueStr = value.toString();
        String valueEncode = ServletUtils.urlEncode(valueStr);
        mutate.header(name, valueEncode);
    }

    private void removeHeader(ServerHttpRequest.Builder mutate, String name) {
        mutate.headers(httpHeaders -> httpHeaders.remove(name)).build();
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String msg) {
        log.error("[鉴权异常处理]请求路径:{}", exchange.getRequest().getPath());
        return ServletUtils.webFluxResponseWriter(exchange.getResponse(), msg, HttpStatus.UNAUTHORIZED);
    }

    /**
     * 获取缓存key
     */
    private String getTokenKey(String token) {
        return CacheConstants.LOGIN_TOKEN_KEY + token;
    }

    /**
     * 获取请求带PREFIX的token
     */
    private String getToken(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst(TokenConstants.AUTHENTICATION);
        // log.info("[token]{}", token);
        return token;
    }

    /**
     * 如果前端设置了令牌前缀，则裁剪掉前缀
     *
     * @param token
     * @return
     */
    private String getNoPrefixToken(String token) {
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith(TokenConstants.PREFIX)) {
            return token.replaceFirst(TokenConstants.PREFIX, StringUtils.EMPTY);
        }

        return null;
    }

    @Override
    public int getOrder() {
        return -200;
    }
}