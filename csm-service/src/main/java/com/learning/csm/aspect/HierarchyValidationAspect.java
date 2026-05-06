package com.learning.csm.aspect;

import com.learning.common.annotation.CheckHierarchy;
import com.learning.common.enums.Role;
import com.learning.common.exception.AccessDeniedException;
import com.learning.csm.repository.CsmRepository;
import com.learning.csm.repository.TeamHierarchyRepository;
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

    private final CsmRepository csmRepository;
    private final TeamHierarchyRepository
                  teamHierarchyRepository;

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

        // CSM → validate own teams!
        if (userRole == Role.CSM) {

            String path =
                request.getRequestURI();

            Long employeeId =
                extractIdFromPath(path);

            if (employeeId != null) {

                var csmOpt =
                    csmRepository
                        .findAll()
                        .stream()
                        .filter(c ->
                            c.getUserId() != null)
                        .findFirst();

                if (csmOpt.isPresent()) {

                    boolean exists =
                        teamHierarchyRepository
                            .existsByCsmIdAndEmployeeId(
                             csmOpt.get().getId(),
                             employeeId);

                    if (!exists) {
                        log.warn(
                            "CSM {} tried to " +
                            "access employee {} " +
                            "not in teams!",
                            username, employeeId);
                        throw new
                            AccessDeniedException(
                            "Employee not in " +
                            "your teams!");
                    }
                }
            }
        }

        return joinPoint.proceed();
    }

    private Long extractIdFromPath(
                 String path) {
        try {
            String[] parts = path.split("/");
            for (int i = 0;
                 i < parts.length - 1; i++) {
                try {
                    return Long.parseLong(
                           parts[i]);
                } catch (
                    NumberFormatException e) {
                    continue;
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
