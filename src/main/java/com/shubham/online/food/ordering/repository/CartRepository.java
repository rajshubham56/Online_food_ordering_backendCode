package com.shubham.online.food.ordering.repository;

import com.shubham.online.food.ordering.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {

    public Cart findByCustomerId(Long userId);


}
