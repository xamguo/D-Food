package com.example.sam.d_food.entities.data;

import java.util.ArrayList;

/**
 * Created by Sam on 5/1/2015.
 */
public interface RestaurantEditor {
    public ArrayList<Restaurant> getRestaurants();
    public void setRestaurants(ArrayList<Restaurant> restaurants);
    public void clearProxy();
    public void putRestaurant(Restaurant restaurant);
    public void createRestaurant(String name, String rating, String description, String pic_url);

}
