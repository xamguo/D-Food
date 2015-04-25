package com.example.sam.d_food.entities.data;

/**
 * Created by Sam on 4/11/2015.
 */
public class Restaurant {
    String name;
    String rating;

    public Restaurant(String name, String rating) {
        this.name = name;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
