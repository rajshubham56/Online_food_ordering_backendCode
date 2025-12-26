package com.shubham.online.food.ordering.repository;

import com.shubham.online.food.ordering.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
