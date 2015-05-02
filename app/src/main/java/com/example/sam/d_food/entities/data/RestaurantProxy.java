/*
* All restaurants and dishes editor function entities are here.
* */
package com.example.sam.d_food.entities.data;

import java.util.ArrayList;

public class RestaurantProxy {

    /* static ArrayList for the restaurants */
    private static ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();

    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(ArrayList<Restaurant> restaurants) {
        RestaurantProxy.restaurants = restaurants;
    }

    public void clearProxy() {
        restaurants = new ArrayList<Restaurant>();
    }

    public void putRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
    }

    public void createRestaurant(String name, String rating, String description, String pic_url) {
        Restaurant restaurant = new Restaurant(name, rating, description, pic_url);
        putRestaurant(restaurant);
    }

    public void setDishList(String restaurantName, ArrayList<Dish> dishes) {
        int i = getRestaurantIndex(restaurantName);
        if(i != 0)
            restaurants.get(i).setDishes(dishes);
    }

    //get the sign of restaurant dishList set, not used
    public boolean isDishListSet(String restaurantName) {
        int i = getRestaurantIndex(restaurantName);
        if(i != 0)
            return restaurants.get(i).isDishesSet();
        else
            return false;
    }

    public ArrayList<Dish> getDishList(String restaurantName) {
        int i = getRestaurantIndex(restaurantName);
        return restaurants.get(i).getDishes();
    }

    public ArrayList<Dish> getDishList(int restaurantIndex) {
        return restaurants.get(restaurantIndex).getDishes();
    }

    private int getRestaurantIndex(String restaurantName) {
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
