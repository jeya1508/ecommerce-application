package com.casestudy.ecommerce.to;

import com.casestudy.ecommerce.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
@AllArgsConstructor
@NoArgsConstructor
public class ProductTO {
    private String name;
    private String seller;
    private String description;
    private int stockCount;
    private double price;
    private List<String> review;

    public Product toEntity() {
        Product product = new Product();
        product.setName(this.getName());
        product.setDescription(this.getDescription());
        product.setSeller(this.getSeller());
        product.setStockCount(this.getStockCount());
        product.setPrice(this.getPrice());
        product.setReview(this.getReview());
        return product;
    }
}
