package com.casestudy.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "product")
public class Product {
    @Transient
    public static final String SEQUENCE_NAME = "product_sequence";

    @Id
    private int id;

    private String name;
    private String seller;
    private String description;
    private int stockCount;
    private double price;
    private List<String> review;
}
