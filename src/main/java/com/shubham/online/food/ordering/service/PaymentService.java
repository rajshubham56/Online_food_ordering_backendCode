package com.shubham.online.food.ordering.service;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.shubham.online.food.ordering.model.Order;
import com.shubham.online.food.ordering.model.Payment;
import com.shubham.online.food.ordering.repository.OrderRepository;
import com.shubham.online.food.ordering.repository.PaymentRepository;
import com.shubham.online.food.ordering.request.PaymentVerificationRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;


    public Map<String, Object> createRazorpayOrder(Long orderId) throws Exception {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("Order not found"));

        RazorpayClient client = new RazorpayClient(keyId, keySecret);

        JSONObject options = new JSONObject();
        options.put("amount", order.getTotalPrice() * 100); // paise mein
        options.put("currency", "INR");
        options.put("receipt", "order_" + orderId);

        com.razorpay.Order razorpayOrder = client.orders.create(options);

        // Payment record banao DB mein
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setRazorpayOrderId(razorpayOrder.get("id"));
        payment.setAmount(order.getTotalPrice() * 100);
        payment.setCurrency("INR");
        payment.setStatus("PENDING");
        paymentRepository.save(payment);

        // Frontend ko response
        Map<String, Object> response = new HashMap<>();
        response.put("razorpayOrderId", razorpayOrder.get("id"));
        response.put("amount", order.getTotalPrice() * 100);
        response.put("currency", "INR");
        response.put("keyId", keyId);
        response.put("orderId", orderId);
        return response;
    }

    // ── Razorpay Payment Verify ───────────────────────────
    public Payment verifyRazorpayPayment(PaymentVerificationRequest req) throws Exception {
        // Signature verify karo
        String data = req.getRazorpayOrderId() + "|" + req.getRazorpayPaymentId();
        String generatedSignature = hmacSHA256(data, keySecret);

        Payment payment = paymentRepository.findByRazorpayOrderId(req.getRazorpayOrderId());
        if (payment == null) throw new Exception("Payment not found");

        if (generatedSignature.equals(req.getRazorpaySignature())) {
            // Payment successful
            payment.setRazorpayPaymentId(req.getRazorpayPaymentId());
            payment.setRazorpaySignature(req.getRazorpaySignature());
            payment.setStatus("SUCCESS");

            // Order status bhi update karo
            Order order = payment.getOrder();
            order.setOrderStatus("CONFIRMED");
            orderRepository.save(order);
        } else {
            payment.setStatus("FAILED");
        }

        return paymentRepository.save(payment);
    }


    public Payment createCodPayment(Long orderId) throws Exception {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("Order not found"));

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalPrice());
        payment.setCurrency("INR");
        payment.setStatus("COD");

        order.setOrderStatus("CONFIRMED");
        orderRepository.save(order);

        return paymentRepository.save(payment);
    }


    public Payment getPaymentByOrderId(Long orderId) throws Exception {
        Payment payment = paymentRepository.findByOrderId(orderId);
        if (payment == null) throw new Exception("Payment not found for order " + orderId);
        return payment;
    }


    private String hmacSHA256(String data, String secret) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(data.getBytes());
        StringBuilder hex = new StringBuilder();
        for (byte b : hash) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }
}