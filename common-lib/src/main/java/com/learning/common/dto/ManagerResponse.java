package com.learning.common.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerResponse {

    private Long id;
    private String managerName;
    private String managerCode;
    private Long csmId;
    private Long userId;

    // From auth-service! ✅
    private String username;
    private String email;
    private String role;
    private Boolean isActive;
}