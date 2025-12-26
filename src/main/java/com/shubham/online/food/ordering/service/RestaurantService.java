package com.shubham.online.food.ordering.service;

import com.shubham.online.food.ordering.DTO.RestaurantDto;
import com.shubham.online.food.ordering.model.Restaurant;
import com.shubham.online.food.ordering.model.User;
import com.shubham.online.food.ordering.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user);

    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant> getAllRestaurant() throws Exception;

    public List<Restaurant> searchRestaurant(String keyword) throws Exception;

    public Restaurant findRestaurantById(Long id) throws Exception;
    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception;

    public Restaurant updatedRestaurantStatus(Long id) throws Exception;

}
