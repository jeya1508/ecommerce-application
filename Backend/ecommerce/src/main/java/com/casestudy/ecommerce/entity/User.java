package com.casestudy.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class User
{
    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";
    @Id
    private int id;

    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private Role role;
}
