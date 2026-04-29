package com.shubham.online.food.ordering.controller;

import com.shubham.online.food.ordering.model.Payment;
import com.shubham.online.food.ordering.model.User;
import com.shubham.online.food.ordering.request.PaymentVerificationRequest;
import com.shubham.online.food.ordering.service.PaymentService;
import com.shubham.online.food.ordering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;


    @PostMapping("/razorpay/{orderId}")
    public ResponseEntity<Map<String, Object>> createRazorpayOrder(
            @PathVariable Long orderId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Map<String, Object> response = paymentService.createRazorpayOrder(orderId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Razorpay payment verify karo
    @PostMapping("/razorpay/verify")
    public ResponseEntity<Payment> verifyPayment(
            @RequestBody PaymentVerificationRequest req,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Payment payment = paymentService.verifyRazorpayPayment(req);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    // COD payment
    @PostMapping("/cod/{orderId}")
    public ResponseEntity<Payment> codPayment(
            @PathVariable Long orderId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Payment payment = paymentService.createCodPayment(orderId);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    // Payment status check
    @GetMapping("/{orderId}")
    public ResponseEntity<Payment> getPayment(
            @PathVariable Long orderId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Payment payment = paymentService.getPaymentByOrderId(orderId);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }
}