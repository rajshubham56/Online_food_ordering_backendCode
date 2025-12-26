package com.shubham.online.food.ordering.service;

import com.shubham.online.food.ordering.model.Order;
import com.shubham.online.food.ordering.model.User;
import com.shubham.online.food.ordering.request.OrderRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface OrderService {

    public Order createOrder(OrderRequest order, User user) throws Exception;

    public Order updateOrder(Long orderId, String orderStatus) throws Exception;

    public void cancelOrder(Long orderId) throws Exception;

    public List<Order> getUsersOrder(Long userId) throws Exception;

    public List<Order> getRestaurantsOrder(Long restaurantId,String orderStatus) throws Exception;

    public Order findOrderById(Long orderId) throws Exception;
}
