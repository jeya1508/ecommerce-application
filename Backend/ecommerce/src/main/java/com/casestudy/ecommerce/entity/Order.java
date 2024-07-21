package com.casestudy.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "productId")
    private Integer productId;

    @Column(name = "date_of_order")
    private LocalDate orderedDate;

    @Column(name = "date_of_delivery")
    private LocalDate deliveryDate;

}
