package com.learning.certificate.aspect;

import com.learning.certificate.client.AuditClient;
import com.learning.common.annotation.Auditable;
import com.learning.common.entity.AuditLog;
import com.learning.common.enums.AuditAction;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component

public class AuditAspect {
    @Autowired(required = false)
    private  AuditClient auditClient;

    @Around("@annotation(com.learning" +
            ".common.annotation.Auditable)")
    public Object audit(
           ProceedingJoinPoint joinPoint)
           throws Throwable {

        HttpServletRequest request =
            ((ServletRequestAttributes)
             RequestContextHolder
                 .getRequestAttributes())
                 .getRequest();

        String username =
            request.getHeader("username");

        MethodSignature signature =
            (MethodSignature)
            joinPoint.getSignature();

        Method method =
            signature.getMethod();

        Auditable auditable =
            method.getAnnotation(
                  Auditable.class);

        AuditAction action =
            auditable.action();

        log.info("Audit: {} by {}",
                 action, username);

        Object result = joinPoint.proceed();

        try {
            AuditLog auditLog =
                AuditLog.builder()
                    .action(action)
                    .entityType(
                     joinPoint.getTarget()
                              .getClass()
                              .getSimpleName())
                    .performedAt(
                     LocalDateTime.now())
                    .performedByUserId(0L)
                    .ipAddress(
                     request.getRemoteAddr())
                    .build();

            auditClient.saveAuditLog(auditLog);

            log.info("Audit saved!");

        } catch (Exception e) {
            log.error("Audit failed: {}",
                      e.getMessage());
        }

        return result;
    }
}
