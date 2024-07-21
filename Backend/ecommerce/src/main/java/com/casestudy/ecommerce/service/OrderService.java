package com.casestudy.ecommerce.service;

import com.casestudy.ecommerce.config.JavaConstant;
import com.casestudy.ecommerce.config.ResourceBO;
import com.casestudy.ecommerce.entity.Order;
import com.casestudy.ecommerce.exception.OrdersNotFoundException;
import com.casestudy.ecommerce.repository.OrderRepository;
import com.casestudy.ecommerce.to.OrderTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders(Integer userId) throws OrdersNotFoundException {
        List<Order> orders = orderRepository.findByUserId(userId);
        if(!orders.isEmpty())
        {
            return orders;
        }
        else{
            throw new OrdersNotFoundException(ResourceBO.getKey(JavaConstant.ORDERS_NOT_FOUND));
        }
    }
    public Order placeOrder(OrderTO order)
    {
        Order orderEntity = new Order();
        orderEntity.setUserId(order.getUserId());
        orderEntity.setProductId(order.getProductId());
        orderEntity.setOrderedDate(LocalDate.now());
        orderEntity.setDeliveryDate(LocalDate.now().plusDays(3));
        return orderRepository.save(orderEntity);
    }

}
