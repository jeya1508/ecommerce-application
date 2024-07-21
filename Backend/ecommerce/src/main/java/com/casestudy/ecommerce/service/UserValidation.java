package com.casestudy.ecommerce.service;

import com.casestudy.ecommerce.config.JavaConstant;
import com.casestudy.ecommerce.config.ResourceBO;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserValidation {

    public boolean isValidEmail(String email)
    {
        String emailRegex = ResourceBO.getKey(JavaConstant.EMAIL_REGEX);
        boolean isValid = false;

        if(!email.isEmpty() && Pattern.compile(emailRegex).matcher(email).matches())
        {
            isValid=true;
        }
        return isValid;
    }

    public boolean isValidPassword(String password)
    {
        boolean isValid = false;
        String passwordRegex = ResourceBO.getKey(JavaConstant.PASSWORD_REGEX);

        if(!password.isEmpty() && Pattern.compile(passwordRegex).matcher(password).matches())
        {
            isValid=true;
        }
        return isValid;
    }



}
