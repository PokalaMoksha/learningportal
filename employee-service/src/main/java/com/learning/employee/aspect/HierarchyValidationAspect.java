package com.learning.employee.aspect;

import com.learning.common.annotation.CheckHierarchy;
import com.learning.common.enums.Role;
import com.learning.common.exception.AccessDeniedException;
import com.learning.employee.client.AuthClient;
import com.learning.employee.repository.EmployeeRepository;
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

    private final EmployeeRepository
                  employeeRepository;
    private final AuthClient authClient;

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

        // Employee → self access only!
        if (userRole == Role.EMPLOYEE) {

            log.info("Employee self " +
                     "access granted!");

            // my-details, my-learning etc
            // are already self access! ✅
            // No extra check needed!
            return joinPoint.proceed();
        }

        // Manager, CSM, LD → allow!
        // They access via their
        // own services! ✅
        return joinPoint.proceed();
    }
}
