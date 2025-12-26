package com.shubham.online.food.ordering.request;

import com.shubham.online.food.ordering.model.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private Long restaurantId;
    private Address deliveryAddress;
}
