package com.casestudy.ecommerce.controller;

import com.casestudy.ecommerce.entity.Product;
import com.casestudy.ecommerce.exception.ProductAlreadyExistsException;
import com.casestudy.ecommerce.exception.ProductNotFoundException;
import com.casestudy.ecommerce.service.ProductService;
import com.casestudy.ecommerce.to.ProductTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping("/products/add")
    public ResponseEntity<String> addProduct(@RequestBody ProductTO product)
    {
        try{
            String response = productService.addProduct(product);
            return ResponseEntity.ok(response);
        }
        catch(ProductAlreadyExistsException exception)
        {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }
    @GetMapping("/products/all")
    public ResponseEntity<List<Product>> viewProducts()
    {
        try {
            List<Product> productList = productService.getAllProducts();
            return ResponseEntity.ok(productList);
        }
        catch (ProductNotFoundException notFoundException)
        {
            return ResponseEntity.
                    status(HttpStatus.NOT_FOUND).
                    build();
        }
    }
    @GetMapping("/search/{productName}")
    public ResponseEntity<Product> getProductByName(@PathVariable String productName)
    {
        try {
            Product product = productService.getProductByName(productName);
            return ResponseEntity.ok(product);
        }
        catch (ProductNotFoundException e)
        {
            return ResponseEntity.
                    status(HttpStatus.NOT_FOUND).
                    build();
        }
    }
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId)
    {
        try {
            Product product = productService.getProductById(productId);
            return ResponseEntity.ok(product);
        }
        catch (ProductNotFoundException e)
        {
            return ResponseEntity.
                    status(HttpStatus.NOT_FOUND).
                    build();
        }
    }
}

