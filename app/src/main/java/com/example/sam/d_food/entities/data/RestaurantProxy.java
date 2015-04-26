package com.example.sam.d_food.entities.data;

import android.util.Log;

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

    public static void createRestaurant(String name, String rating, String description, String pic_url) {
        Restaurant restaurant = new Restaurant(name, rating, description, pic_url);
        putRestaurant(restaurant);
    }

    public static void setDishList(String restaurantName, ArrayList<Dish> dishes) {
        int i = getRestaurantIndex(restaurantName);
        if(i != 0)
            restaurants.get(i).setDishes(dishes);
    }

    //get the sign of restaurant dishList set, not used
    public static boolean isDishListSet(String restaurantName) {
        int i = getRestaurantIndex(restaurantName);
        if(i != 0)
            return restaurants.get(i).isDishesSet();
        else
            return false;
    }

    public static ArrayList<Dish> getDishList(String restaurantName) {
        int i = getRestaurantIndex(restaurantName);
        return restaurants.get(i).getDishes();
    }

    public static ArrayList<Dish> getDishList(int restaurantIndex) {
        return restaurants.get(restaurantIndex).getDishes();
    }

    private static int getRestaurantIndex(String restaurantName) {
        int index = -1;
        for(int i = 0; i < restaurants.size(); i++) {
            if(restaurants.get(i).getName().equals(restaurantName)) {
                index = i;
                break;
            }
        }
        return index;
    }
}
