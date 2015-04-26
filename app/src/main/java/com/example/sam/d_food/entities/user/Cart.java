package com.example.sam.d_food.entities.user;

import java.util.ArrayList;

/**
 * Created by Sam on 4/11/2015.
 */
public class Cart {
    static public ArrayList<Order> myOders = new ArrayList<Order>();

    static public void addOder(String name, String restaurantName, int id, int quantity) {
           myOders.add(new Order(name,restaurantName,id,quantity));
    }

    private static class Order{
        String name;
        String restaurantName;
        int id;
        int quantity;

        private Order(String name, String restaurantName, int id, int quantity) {
            this.name = name;
            this.id = id;
            this.quantity = quantity;
            this.restaurantName = restaurantName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getRestaurantName() {
            return restaurantName;
        }

        public void setRestaurantName(String restaurantName) {
            this.restaurantName = restaurantName;
        }
    }
}
