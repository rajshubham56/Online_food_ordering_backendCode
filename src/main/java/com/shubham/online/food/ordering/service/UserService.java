package com.shubham.online.food.ordering.service;

import com.shubham.online.food.ordering.model.User;
import org.springframework.stereotype.Service;

public interface UserService {

    public User findUserByJwtToken(String jwt) throws Exception;
    public User findUserByEmail(String Email) throws Exception;


}
