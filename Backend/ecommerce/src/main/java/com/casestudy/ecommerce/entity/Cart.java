package com.casestudy.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="cart")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CartId.class)
public class Cart {
    @Id

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "productId")
    private Integer productId;
}
