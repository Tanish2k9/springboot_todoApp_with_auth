package com.tanish.Task_Springboot.dto;

import com.tanish.Task_Springboot.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwt;
    private Long userId;
    private UserRole userRole;
}
