package com.mxy.mypro.interceptor;

import com.mxy.mypro.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        System.out.println("进入jwt拦截");
        System.out.println("token:" + token);

        if (isWebSocketConnection(request)) {
            // 这是一个WebSocket连接,不进行JWT验证
            System.out.println("WebSocket连接,放行");
            return true;
        }


        if (token != null && token.startsWith("Bearer ")) {
            System.out.println("token存在");
            token = token.substring(7);
            try {
                String username = JwtUtils.getClaimsByToken(token).getSubject();
                // 在这里可以做一些自定义的逻辑,比如将用户信息存储到请求中
                System.out.println("校验token");
                if (username != null) {
                    System.out.println("token认证成功" + username);
                }else {
                    System.out.println("认证失败");
                }

                return true;
            } catch (Exception e) {
                // 如果 JWT 解析失败,说明 JWT 无效或已过期
                //response.setStatus(HttpStatus.UNAUTHORIZED.value());
                System.out.println("异常");
                return false;
            }
        }

        // 如果请求中没有携带 JWT,也返回 401 Unauthorized
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return false;
    }

    private boolean isWebSocketConnection(HttpServletRequest request) {
        String upgradeHeader = request.getHeader("Upgrade");
        String connectionHeader = request.getHeader("Connection");
        return "websocket".equalsIgnoreCase(upgradeHeader) && "Upgrade".equalsIgnoreCase(connectionHeader);
    }
}
