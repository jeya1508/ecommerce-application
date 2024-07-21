package com.casestudy.ecommerce.to;

import com.casestudy.ecommerce.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseTO {
    private String token;
    private Integer userId;
    private Role role;
}
