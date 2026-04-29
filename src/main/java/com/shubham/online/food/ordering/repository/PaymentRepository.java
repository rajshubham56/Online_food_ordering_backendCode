package com.shubham.online.food.ordering.repository;

import com.shubham.online.food.ordering.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByRazorpayOrderId(String razorpayOrderId);
    Payment findByOrderId(Long orderId);
}