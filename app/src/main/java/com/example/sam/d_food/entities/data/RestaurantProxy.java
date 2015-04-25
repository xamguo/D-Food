package com.example.sam.d_food.entities.data;

import java.util.ArrayList;

/**
 * Created by Sam on 4/24/2015.
 */
public class RestaurantProxy {
    private static ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();

    public static ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    public static void setRestaurants(ArrayList<Restaurant> restaurants) {
        RestaurantProxy.restaurants = restaurants;
    }

    public static void clearProxy() {
        restaurants = new ArrayList<Restaurant>();
    }

    public static void putRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
    }

    public static void createRestaurant(String name, String rating) {
        Restaurant restaurant = new Restaurant(name, rating);
        putRestaurant(restaurant);
    }
}
