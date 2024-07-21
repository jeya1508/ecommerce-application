package com.casestudy.ecommerce.service;

import com.casestudy.ecommerce.config.JavaConstant;
import com.casestudy.ecommerce.config.ResourceBO;
import com.casestudy.ecommerce.entity.Cart;
import com.casestudy.ecommerce.entity.CartId;
import com.casestudy.ecommerce.exception.ItemsAlreadyExistsException;
import com.casestudy.ecommerce.exception.ItemsNotFoundException;
import com.casestudy.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public List<Cart> getAllCartItems (Integer userId) throws ItemsNotFoundException
    {
        List<Cart> cartList = cartRepository.findByUserId(userId);
        if(!cartList.isEmpty())
        {
            return cartList;
        }
        else {
            throw new ItemsNotFoundException(ResourceBO.getKey(JavaConstant.CART_EMPTY_MESSAGE));
        }
    }
    public String addItemsToCart(Cart cart) throws ItemsAlreadyExistsException {
        Cart existingItems = cartRepository.findByUserIdAndProductId(cart.getUserId(), cart.getProductId());
        if(existingItems==null)
        {
            cartRepository.save(cart);
            return ResourceBO.getKey(JavaConstant.ADD_ITEMS_SUCCESS);
        }
        else {
            throw new ItemsAlreadyExistsException(ResourceBO.getKey(JavaConstant.ITEMS_ALREADY_PRESENT));
        }
    }
    public String deleteItemsFromCart(Integer userId,Integer productId) throws ItemsNotFoundException {
        if(cartRepository.existsByUserIdAndProductId(userId,productId)) {
            CartId cartId = new CartId(userId, productId);
            cartRepository.deleteById(cartId);
            return ResourceBO.getKey(JavaConstant.ITEM_DELETE_SUCCESS);
        }
        else {
            throw new ItemsNotFoundException(ResourceBO.getKey(JavaConstant.CART_EMPTY_MESSAGE));
        }

    }
}
