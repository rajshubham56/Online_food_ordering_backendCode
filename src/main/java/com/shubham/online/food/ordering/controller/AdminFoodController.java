package com.shubham.online.food.ordering.controller;

import com.shubham.online.food.ordering.model.Food;
import com.shubham.online.food.ordering.model.Restaurant;
import com.shubham.online.food.ordering.model.User;
import com.shubham.online.food.ordering.request.CreateFoodRequest;
import com.shubham.online.food.ordering.response.MessageResponse;
import com.shubham.online.food.ordering.service.FoodService;
import com.shubham.online.food.ordering.service.RestaurantService;
import com.shubham.online.food.ordering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {
    @Autowired
    private FoodService foodService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;
    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,
                                           @RequestHeader("Authentication") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(req.getRestaurentId());
        Food food = foodService.createFood(req , req.getCategory(),restaurant);

        return  new ResponseEntity<>(food, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id,
                                                      @RequestHeader("Authentication") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        foodService.deleteFood(id);
        MessageResponse res = new MessageResponse();
        res.setMessage("food delete successfully");

        return  new ResponseEntity<>(res, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvaibilityStatus(@RequestBody Long id,
                                           @RequestHeader("Authentication") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Food food = foodService.updateAvaibilityStatus(id);

        return  new ResponseEntity<>(food, HttpStatus.CREATED);

    }





}
