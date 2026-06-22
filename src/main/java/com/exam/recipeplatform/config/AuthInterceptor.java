package com.exam.recipeplatform.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        String path = request.getRequestURI();

        // 1. Allow guest routes through without checking sessions
        if (path.equals("/") || path.equals("/login") || path.equals("/register")
                || path.startsWith("/css/") || path.startsWith("/js/")) {
            return true;
        }

        // 2. If no user is in the session, block and send to login
        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("/login");
            return false;
        }

        return true; // Session valid, proceed to controller
    }
}