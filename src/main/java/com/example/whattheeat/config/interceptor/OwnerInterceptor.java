package com.example.whattheeat.config.interceptor;

import com.example.whattheeat.constant.Const;
import com.example.whattheeat.enums.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

public class OwnerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        if (session == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "세션이 끊어졌습니다");
        }

        if (request.getMethod().equals(HttpMethod.GET.name())) {
            return true;
        }

        UserRole userRole = UserRole.valueOf(session.getAttribute(Const.AUTHENTICATION).toString());

        if (userRole != UserRole.OWNER) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "OWNER 권한이 없습니다");
        }

        return true;
    }
}
