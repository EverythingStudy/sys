package cn.staitech.auth.util;

import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @author: wangfeng
 * @create: 2023-10-17 17:11:48
 * @Description: 语言判断
 */

public class LanguageUtils {
    public static boolean isEn(ServerHttpRequest request) {
        if (request.getHeaders().getFirst("Language") != null && "en-us".equals(request.getHeaders().getFirst("Language"))) {
            return true;
        }
        return false;
    }

    public static String getLanguage(ServerHttpRequest request) {
        return request.getHeaders().getFirst("Language") != null ? request.getHeaders().getFirst("Language") : "zh-cn";
    }
}
