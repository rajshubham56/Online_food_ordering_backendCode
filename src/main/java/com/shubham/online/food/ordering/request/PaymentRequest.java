package com.shubham.online.food.ordering.request;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long orderId;
    private String paymentMethod; // "RAZORPAY" or "COD"
}