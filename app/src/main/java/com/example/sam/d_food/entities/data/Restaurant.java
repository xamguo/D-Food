package com.example.sam.d_food.entities.data;

import java.util.ArrayList;

/**
 * Created by Sam on 4/11/2015.
 */
public class Restaurant {
    String name;
    String rating;
    String description;
    String pic_url;
    ArrayList<Dish> dishes;
    boolean dishesSet;

    public Restaurant(String name, String rating, String description, String pic_url) {
        this.name = name;
        this.rating = rating;
        this.description = description;
        this.pic_url = pic_url;
        this.dishes = new ArrayList<Dish>();
        this.dishesSet = false;
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


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public ArrayList<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<Dish> dishes) {
        this.dishes = dishes;
        setDishesSet(true);
    }

    public boolean isDishesSet() {
        return dishesSet;
    }

    public void setDishesSet(boolean dishesSet) {
        this.dishesSet = dishesSet;
    }
}
