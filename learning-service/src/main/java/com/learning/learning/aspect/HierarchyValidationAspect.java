package com.learning.learning.aspect;

import com.learning.common.annotation.CheckHierarchy;
import com.learning.common.enums.Role;
import com.learning.common.exception.AccessDeniedException;
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

        // Only LD and Admin can
        // update learning! ✅
        if (userRole == Role.LD ||
            userRole == Role.ADMIN) {
            log.info("LD/Admin access granted!");
            return joinPoint.proceed();
        }

        log.warn("Unauthorized learning " +
                 "update attempt by: {} role: {}",
                 username, role);

        throw new AccessDeniedException(
            "Only L&D and Admin can " +
            "update learning details!");
    }
}
