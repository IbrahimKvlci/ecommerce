package com.ibrahimkvlci.ecommerce.order.utils;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class RequestUtils {

    public enum ClientType {
        MOBILE,
        TABLET,
        DESKTOP,
        BOT,
        UNKNOWN
    }

    public static String getClientIp(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            return ip.split(",")[0].trim();
        }
        ip = request.getHeader("Proxy-Client-IP");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("WL-Proxy-Client-IP");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    public static ClientType getClientType(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null) return ClientType.UNKNOWN;

        userAgent = userAgent.toLowerCase();

        // Simple checks, can be expanded
        if (userAgent.contains("mobi")) return ClientType.MOBILE;
        if (userAgent.contains("tablet") || userAgent.contains("ipad")) return ClientType.TABLET;
        if (userAgent.contains("bot") || userAgent.contains("crawler") || userAgent.contains("spider"))
            return ClientType.BOT;

        return ClientType.DESKTOP;
    }
}
