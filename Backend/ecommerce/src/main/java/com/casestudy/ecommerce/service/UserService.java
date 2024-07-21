package com.casestudy.ecommerce.service;

import com.casestudy.ecommerce.config.JavaConstant;
import com.casestudy.ecommerce.config.ResourceBO;
import com.casestudy.ecommerce.entity.Role;
import com.casestudy.ecommerce.entity.User;
import com.casestudy.ecommerce.exception.UserAuthenticationException;
import com.casestudy.ecommerce.exception.ValidationException;
import com.casestudy.ecommerce.repository.UserRepository;
import com.casestudy.ecommerce.to.SignInTO;
import com.casestudy.ecommerce.to.UserTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class UserService  {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserValidation userValidation;
    @Autowired
    private SequenceGeneratorService generatorService;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public String addUser(@RequestBody UserTO userTO) throws UserAuthenticationException, ValidationException {
            User user = userTO.toEntity();

        if(!userRepository.existsByEmail(user.getEmail()))
        {
            if(userValidation.isValidEmail(user.getEmail()) && userValidation.isValidPassword(user.getPassword()))
            {
                user.setId(generatorService.getSequenceNumber(User.SEQUENCE_NAME));
                userRepository.save(user);
                return ResourceBO.getKey(JavaConstant.USER_ADDED_SUCCESSFUL);
            }
            else if(!userValidation.isValidEmail(user.getEmail()) )
            {
                throw new ValidationException(ResourceBO.getKey(JavaConstant.INVALID_EMAIL));
            }
            else if (!userValidation.isValidPassword(user.getPassword()))
            {
                throw new ValidationException(ResourceBO.getKey(JavaConstant.INVALID_PASSWORD));
                
            }
            else{
                throw new ValidationException(ResourceBO.getKey(JavaConstant.REGISTRATION_ERROR));
            }

        }
        else{
                throw new UserAuthenticationException(ResourceBO.getKey(JavaConstant.REGISTRATION_ERROR));
        }
    }

    public String login(SignInTO signInTO) throws UserAuthenticationException {
        String response =null;
        if(userRepository.existsByEmail(signInTO.getEmail()))
        {
            Optional<User> accountDetail = userRepository.findByEmail(signInTO.getEmail());
            if(accountDetail.isPresent()) {
                if (accountDetail.get().getPassword().equals(signInTO.getPassword())) {
                    if(accountDetail.get().getRole()== Role.USER) {
                        response = ResourceBO.getKey(JavaConstant.LOGIN_SUCCESS_MESSAGE);
                    }
                    if(accountDetail.get().getRole() == Role.ADMIN) {
                        response = ResourceBO.getKey(JavaConstant.ADMIN_SUCCESS_MESSAGE);
                    }
                }
                else {
                    throw new UserAuthenticationException(ResourceBO.getKey(JavaConstant.LOGIN_FAILED_MESSAGE));
                }
            }
            else{
                throw new UserAuthenticationException(ResourceBO.getKey(JavaConstant.USER_NOT_REGISTERED));
            }
        }
        return response;
    }


}
