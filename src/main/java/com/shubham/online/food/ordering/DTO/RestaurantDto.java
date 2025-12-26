package com.shubham.online.food.ordering.DTO;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Embeddable
public class RestaurantDto {
    private String title;

    private List<String> images;

    private String description;
    private Long id;
}
