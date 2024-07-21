package com.casestudy.ecommerce.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileTO {
    private String name;
    private String email;
    private String phoneNumber;
    private Integer userId;
}
