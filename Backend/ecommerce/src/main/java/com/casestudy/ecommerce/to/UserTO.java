package com.casestudy.ecommerce.to;

import com.casestudy.ecommerce.entity.Role;
import com.casestudy.ecommerce.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserTO {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;

    public UserTO(String name, String email, String phoneNumber, String password) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public User toEntity()
    {
        User newUser = new User();
        newUser.setName(this.name);
        newUser.setEmail(this.email);
        newUser.setPhoneNumber(this.phoneNumber);
        newUser.setRole(Role.USER);
        newUser.setPassword(this.getPassword());
        return newUser;
    }
}
