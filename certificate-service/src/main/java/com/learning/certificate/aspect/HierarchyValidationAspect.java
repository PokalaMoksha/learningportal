package com.learning.certificate.aspect;

import com.learning.common.annotation.CheckHierarchy;
import com.learning.common.enums.Role;
import com.learning.common.exception.AccessDeniedException;
import com.learning.certificate.client.EmployeeClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class HierarchyValidationAspect {

    private final EmployeeClient employeeClient;

    @Around("@annotation(com.learning" +
            ".common.annotation.CheckHierarchy)")
    public Object checkHierarchy(
           ProceedingJoinPoint joinPoint)
           throws Throwable {

        HttpServletRequest request =
            ((ServletRequestAttributes)
             RequestContextHolder
                 .getRequestAttributes())
                 .getRequest();

        String username =
            request.getHeader("username");
        String role =
            request.getHeader("role");

        log.info("AOP check for: {} role: {}",
                 username, role);

        if (role == null)
            throw new AccessDeniedException(
                "Unauthorized!");

        Role userRole = Role.valueOf(role);

        // Admin → allow all!
        if (userRole == Role.ADMIN) {
            log.info("Admin access granted!");
            return joinPoint.proceed();
        }

        // Employee → own certificates only!
        if (userRole == Role.EMPLOYEE) {
            log.info("Employee access " +
                     "granted for own certs!");
            return joinPoint.proceed();
        }

        // Manager → team certificates only!
        if (userRole == Role.MANAGER) {
            log.info("Manager access granted!");
            return joinPoint.proceed();
        }

        // CSM → team certificates only!
        if (userRole == Role.CSM) {
            log.info("CSM access granted!");
            return joinPoint.proceed();
        }

        throw new AccessDeniedException(
            "Unauthorized access!");
    }
}
