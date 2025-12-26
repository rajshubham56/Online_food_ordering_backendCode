package com.shubham.online.food.ordering.response;

import com.shubham.online.food.ordering.model.USER_ROLE;
import lombok.Data;
@Data
public class AuthResponse {
    public String jwt;
    public String message;
    private USER_ROLE role;
}
