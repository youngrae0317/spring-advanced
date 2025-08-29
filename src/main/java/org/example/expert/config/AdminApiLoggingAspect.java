package org.example.expert.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class AdminApiLoggingAspect {

    @Around("execution(* org.example.expert.domain.comment.controller.CommentAdminController.*(..)) || " +
            "execution(* org.example.expert.domain.user.controller.UserAdminController.*(..))")
    public Object logAdminApi(ProceedingJoinPoint pjp) throws Throwable {

        HttpServletRequest request =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        long userId = (long) request.getAttribute("userId");
        String url = request.getRequestURI();

        String requestBody = Arrays.toString(pjp.getArgs());

        log.info("[관리자 API Request] 요청 User ID: " + userId + ", 요청 시각: " + LocalDateTime.now() + ", URL: " + url + ", request: " + requestBody);

        Object result = pjp.proceed();

        log.info("[관리자 API Response] 응답 User ID: " + userId + ", 요청 시각: " + LocalDateTime.now() + ", URL: " + url + ", response: " + result);

        return result;
    }

}
