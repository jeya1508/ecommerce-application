package com.casestudy.ecommerce.controller;

import com.casestudy.ecommerce.exception.UserNotFoundException;
import com.casestudy.ecommerce.service.ProfileService;
import com.casestudy.ecommerce.to.ProfileTO;
import com.casestudy.ecommerce.to.UserTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("profile")
@CrossOrigin
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @PutMapping("/edit")
    public ResponseEntity<String> updateProfile(@RequestBody ProfileTO profileTO)
    {
        try{
            String response = profileService.editProfile(profileTO);
            return ResponseEntity.ok(response);
        }
        catch (UserNotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserTO> getProfile(@PathVariable Integer userId)
    {
        try{
            UserTO userDetails = profileService.getUserDetails(userId);
            return ResponseEntity.ok(userDetails);
        }
        catch (UserNotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
