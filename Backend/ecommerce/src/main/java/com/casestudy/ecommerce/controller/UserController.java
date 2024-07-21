package com.casestudy.ecommerce.controller;

import com.casestudy.ecommerce.config.JavaConstant;
import com.casestudy.ecommerce.config.ResourceBO;
import com.casestudy.ecommerce.entity.User;
import com.casestudy.ecommerce.exception.UserAuthenticationException;
import com.casestudy.ecommerce.exception.ValidationException;
import com.casestudy.ecommerce.repository.UserRepository;
import com.casestudy.ecommerce.service.JwtService;
import com.casestudy.ecommerce.service.UserInfoService;
import com.casestudy.ecommerce.service.UserService;
import com.casestudy.ecommerce.to.AuthResponseTO;
import com.casestudy.ecommerce.to.SignInTO;
import com.casestudy.ecommerce.to.UserTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserInfoService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserTO userTO) {
        try {
            String response = userService.addUser(userTO);
            return ResponseEntity.ok(response);

        } catch (UserAuthenticationException userAuthenticationException) {
            return ResponseEntity.
                    status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(userAuthenticationException.getMessage());

        }
        catch (ValidationException validationException)
        {
            return ResponseEntity.
                    status(HttpStatus.BAD_REQUEST).
                    body(validationException.getMessage());
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> loginUser(@RequestBody SignInTO signInTO)
//    {
//        try{
//            String response = userService.login(signInTO);
//
//            return (response==null)?ResponseEntity.badRequest().body(ResourceBO.getKey(JavaConstant.USER_NOT_REGISTERED)):ResponseEntity.ok(response);
//        }
//        catch (UserAuthenticationException exception)
//        {
//            return ResponseEntity.
//                    status(HttpStatus.INTERNAL_SERVER_ERROR).
//                    body(exception.getMessage());
//        }
//    }
@PostMapping("/login")
public AuthResponseTO authenticateAndGetToken(@RequestBody SignInTO authRequest) {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
    AuthResponseTO authResponseTO=new AuthResponseTO();
    Optional<User> user=userRepository.findByEmail(authRequest.getEmail());
    if (authentication.isAuthenticated()) {
        String token = jwtService.generateToken(authRequest.getEmail());
        authResponseTO.setToken(token);
        authResponseTO.setUserId(user.get().getId());
        authResponseTO.setRole(user.get().getRole());

    } else {
        throw new UsernameNotFoundException("invalid user request !");
    }
    return authResponseTO;
}
}
