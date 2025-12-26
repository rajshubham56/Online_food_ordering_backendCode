package com.shubham.online.food.ordering.service;

import com.shubham.online.food.ordering.model.Category;
import com.shubham.online.food.ordering.model.Food;
import com.shubham.online.food.ordering.model.Restaurant;
import com.shubham.online.food.ordering.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);

    void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantsFood(Long restaurantId,
                                         boolean isVegitarian,
                                         boolean isNonveg,
                                         boolean isSeasonal,
                                         String foodCategory
    );

    public List<Food> searchFood(String keyyword);

    public Food findFoodById(Long foodId)throws Exception;

    public Food updateAvaibilityStatus(Long foodId) throws Exception;



}
