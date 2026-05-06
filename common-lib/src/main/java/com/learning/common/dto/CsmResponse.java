package com.learning.common.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CsmResponse {

    private Long id;
    private String csmName;
    private String csmCode;
    private Long userId;

    // From auth-service! ✅
    private String username;
    private String email;
    private String role;
    private Boolean isActive;
}