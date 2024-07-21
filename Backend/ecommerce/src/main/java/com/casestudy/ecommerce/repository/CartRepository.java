package com.casestudy.ecommerce.repository;

import com.casestudy.ecommerce.entity.Cart;
import com.casestudy.ecommerce.entity.CartId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, CartId> {
    List<Cart> findByUserId(int userId);
    void deleteById(CartId cartId);
    Cart findByUserIdAndProductId(Integer userId, Integer productId);
    boolean existsByUserIdAndProductId(Integer userId, Integer productId);
}
