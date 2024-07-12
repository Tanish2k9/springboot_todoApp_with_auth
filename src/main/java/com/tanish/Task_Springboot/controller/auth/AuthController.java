package com.tanish.Task_Springboot.controller.auth;


import com.tanish.Task_Springboot.dto.AuthenticationRequest;
import com.tanish.Task_Springboot.dto.AuthenticationResponse;
import com.tanish.Task_Springboot.dto.SignUpRequest;
import com.tanish.Task_Springboot.dto.UserDto;
import com.tanish.Task_Springboot.entities.User;
import com.tanish.Task_Springboot.repositories.UserRepository;
import com.tanish.Task_Springboot.services.auth.AuthService;
import com.tanish.Task_Springboot.services.jwt.UserService;
import com.tanish.Task_Springboot.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final  UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignUpRequest signUpRequest){
        if(authService.hasUserEmail(signUpRequest.getEmail())){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("user already exist");
        }

        UserDto createdUserDto = authService.signupuser(signUpRequest);
        if(createdUserDto == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST ).body("user not created");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()));
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username and Password");
        }
        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getEmail());

        Optional<User> optionalUser = userRepository.findFirstByEmail(authenticationRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if(optionalUser.isPresent()){
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }
        return authenticationResponse;

    }
}
