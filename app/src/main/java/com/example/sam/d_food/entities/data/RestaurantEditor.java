/*
* The interface to access restaurants data
* */
package com.example.sam.d_food.entities.data;

import java.util.ArrayList;

public interface RestaurantEditor {
    public ArrayList<Restaurant> getRestaurants();
    public void setRestaurants(ArrayList<Restaurant> restaurants);
    public void clearProxy();
    public void putRestaurant(Restaurant restaurant);
    public void createRestaurant(String name, String rating, String description, String pic_url);
}
