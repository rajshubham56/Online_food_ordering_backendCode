package com.shubham.online.food.ordering.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private Long price;

    @ManyToOne
    private Category foodCategory;

    @Column(length = 1000)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> images;

    private boolean available;

    @ManyToOne
    @JsonIgnore
    private Restaurant restaurant;

    private boolean vegetarian;
    private boolean seasonal;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<IngredientsItem> ingredientsItems = new ArrayList<>();

    private Date creationDate;
}