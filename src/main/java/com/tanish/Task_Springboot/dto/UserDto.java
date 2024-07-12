package com.tanish.Task_Springboot.dto;

import com.tanish.Task_Springboot.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long Id;
    private String name;
    private String email;
    private String password;
    private UserRole userRole;
}
