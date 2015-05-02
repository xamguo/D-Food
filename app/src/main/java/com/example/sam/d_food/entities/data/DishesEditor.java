/*
* The interface to access dishes data
* */
package com.example.sam.d_food.entities.data;

import java.util.ArrayList;

public interface DishesEditor {
    public void setDishList(String restaurantName, ArrayList<Dish> dishes);
    public boolean isDishListSet(String restaurantName);
    public ArrayList<Dish> getDishList(String restaurantName);
    public ArrayList<Dish> getDishList(int restaurantIndex);
}
