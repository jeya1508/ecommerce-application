package com.casestudy.ecommerce.controller;

import com.casestudy.ecommerce.entity.Order;
import com.casestudy.ecommerce.exception.OrdersNotFoundException;
import com.casestudy.ecommerce.service.OrderService;
import com.casestudy.ecommerce.to.OrderTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Order>> getAllOrders(@PathVariable Integer userId)
    {
        try{
            List<Order> orderList = orderService.getAllOrders(userId);
            return ResponseEntity.ok(orderList);
        }
        catch (OrdersNotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }
    @PostMapping("/{userId}/{productId}")
    public ResponseEntity<Order> purchaseItems(@PathVariable Integer userId, @PathVariable Integer productId)
    {
        OrderTO order = new OrderTO(userId,productId);
        return ResponseEntity.ok(orderService.placeOrder(order));
    }
}
