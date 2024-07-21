package com.casestudy.ecommerce.controller;

import com.casestudy.ecommerce.entity.Cart;
import com.casestudy.ecommerce.exception.ItemsAlreadyExistsException;
import com.casestudy.ecommerce.exception.ItemsNotFoundException;
import com.casestudy.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cart")
@CrossOrigin
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Cart>> getAllCartProducts(@PathVariable Integer userId)
    {
        try{
            List<Cart>cartList = cartService.getAllCartItems(userId);
            return ResponseEntity.ok(cartList);
        }
        catch (ItemsNotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addItems(@RequestBody Cart cart)
    {
        try{
            String response = cartService.addItemsToCart(cart);
            return ResponseEntity.ok(response);
        }
        catch (ItemsAlreadyExistsException e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<String> deleteItems(@PathVariable Integer userId, @PathVariable Integer productId)
    {
        try{
            String response = cartService.deleteItemsFromCart(userId,productId);
            return ResponseEntity.ok(response);
        }
        catch (ItemsNotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
