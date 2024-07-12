package com.tanish.Task_Springboot.services.auth;

import com.tanish.Task_Springboot.dto.SignUpRequest;
import com.tanish.Task_Springboot.dto.UserDto;
import com.tanish.Task_Springboot.entities.User;
import com.tanish.Task_Springboot.enums.UserRole;
import com.tanish.Task_Springboot.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class AuthServiceImpl implements AuthService{
    @Autowired
    private  UserRepository userRepository;

    @PostConstruct
    public void createAdminAccount(){
       Optional<User> optionalUser =  userRepository.findByUserRole(UserRole.ADMIN);

       if(optionalUser.isEmpty()){
           User user = new User();
           user.setEmail("admin@test.com");
           user.setName("admin");
           user.setPassword(new BCryptPasswordEncoder().encode("admin"));
           user.setUserRole(UserRole.ADMIN);
           userRepository.save(user);
           System.out.println("Admin created successfully");
       }else{
           System.out.println("Admin already exist");
       }
    }

    @Override
    public UserDto signupuser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setName(signUpRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        user.setUserRole(UserRole.EMPLOYEE);
        User createdUser = userRepository.save(user);
        return createdUser.getUserDto();
    }

    @Override
    public Boolean hasUserEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
