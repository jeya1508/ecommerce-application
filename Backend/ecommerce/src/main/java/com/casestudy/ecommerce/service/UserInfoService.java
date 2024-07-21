package com.casestudy.ecommerce.service;

import com.casestudy.ecommerce.config.JavaConstant;
import com.casestudy.ecommerce.config.ResourceBO;
import com.casestudy.ecommerce.entity.User;
import com.casestudy.ecommerce.exception.UserAuthenticationException;
import com.casestudy.ecommerce.exception.ValidationException;
import com.casestudy.ecommerce.repository.UserRepository;
import com.casestudy.ecommerce.to.UserTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserValidation userValidation;
    @Autowired
    private SequenceGeneratorService generatorService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userDetail = repository.findByEmail(username);

        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public String addUser(UserTO userInfo) throws ValidationException,UserAuthenticationException{


        User user = userInfo.toEntity();

        if(!repository.existsByEmail(user.getEmail()))
        {
            if(userValidation.isValidEmail(user.getEmail()) && userValidation.isValidPassword(user.getPassword()))
            {
                user.setPassword(encoder.encode(user.getPassword()));
                user.setId(generatorService.getSequenceNumber(User.SEQUENCE_NAME));
                repository.save(user);
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


}
