package com.tanish.Task_Springboot.services.auth;

import com.tanish.Task_Springboot.dto.SignUpRequest;
import com.tanish.Task_Springboot.dto.UserDto;

public interface AuthService {

    UserDto signupuser(SignUpRequest signUpRequest);
    Boolean hasUserEmail(String email);
}
