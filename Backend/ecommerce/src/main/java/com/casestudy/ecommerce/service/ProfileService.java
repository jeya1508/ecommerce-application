package com.casestudy.ecommerce.service;

import com.casestudy.ecommerce.config.JavaConstant;
import com.casestudy.ecommerce.config.ResourceBO;
import com.casestudy.ecommerce.entity.User;
import com.casestudy.ecommerce.exception.UserNotFoundException;
import com.casestudy.ecommerce.repository.UserRepository;
import com.casestudy.ecommerce.to.ProfileTO;
import com.casestudy.ecommerce.to.UserTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private UserRepository userRepository;
    public static final Logger logger = LoggerFactory.getLogger(ProfileService.class);
    public String editProfile(ProfileTO profileTO) throws UserNotFoundException {
        Optional<User> credentials = userRepository.findById(profileTO.getUserId());
        if(credentials.isPresent()){
            if(profileTO.getName()!=null)
            {
                credentials.get().setName(profileTO.getName());
            }
            if(profileTO.getEmail()!=null)
            {
                credentials.get().setEmail(profileTO.getEmail());
            }
            if(profileTO.getPhoneNumber()!=null)
            {
                credentials.get().setPhoneNumber(profileTO.getPhoneNumber());
            }
            userRepository.save(credentials.get());
        }
        else{
            throw new UserNotFoundException(ResourceBO.getKey(JavaConstant.USER_NOT_FOUND));
        }
        return ResourceBO.getKey(JavaConstant.PROFILE_UPDATE_SUCCESS);
    }
    public UserTO getUserDetails(int userId) throws UserNotFoundException
    {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent())
        {
            UserTO userTO = new UserTO();
            userTO.setName(user.get().getName());
            userTO.setEmail(user.get().getEmail());
            userTO.setPhoneNumber(user.get().getPhoneNumber());
            return userTO;
        }
        else{
            throw new UserNotFoundException(ResourceBO.getKey(JavaConstant.USER_NOT_FOUND));
        }
    }

}
