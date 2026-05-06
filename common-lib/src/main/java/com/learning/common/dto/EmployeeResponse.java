package com.learning.common.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

    private Long id;
    private String employeeName;
    private String employeeCode;
    private Long managerId;
    private Long userId;

    // From auth-service! ✅
    private String username;
    private String email;
    private String role;
    private Boolean isActive;
}