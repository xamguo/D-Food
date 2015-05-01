package com.example.sam.d_food.entities.data;

import java.util.ArrayList;

/**
 * Created by Sam on 5/1/2015.
 */
public interface DishesEditor {
    public void setDishList(String restaurantName, ArrayList<Dish> dishes);
    public boolean isDishListSet(String restaurantName);
    public ArrayList<Dish> getDishList(String restaurantName);
    public ArrayList<Dish> getDishList(int restaurantIndex);
}
