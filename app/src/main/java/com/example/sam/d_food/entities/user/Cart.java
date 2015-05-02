package com.example.sam.d_food.entities.user;

import java.util.ArrayList;

public class Cart {
    static public ArrayList<Order> myOrders = new ArrayList<Order>();

    static public void addOrder(String name, String restaurantName, int id, int quantity, double wholePrice) {
           myOrders.add(new Order(name, restaurantName, id, quantity, wholePrice));
    }

    static public int getOrderNum() {
        return myOrders.size();
    }

    static public int getOrderID(int index) {
        return myOrders.get(index).getId();
    }

    static public String getOrderName(int index) {
        return myOrders.get(index).getName();
    }

    static public int getOrderQuantity(int index) {
        return myOrders.get(index).getQuantity();
    }

    static public double getOrderPrice(int index) {
        return myOrders.get(index).getWholePrice();
    }

    static public String getOrderRestaurantName(int index) {
        return myOrders.get(index).getRestaurantName();
    }

    static public double getTotalPrice() {
        double price = 0;
        for(int i = 0; i < myOrders.size(); i++){
            price += Cart.getOrderPrice(i);
        }
        return price;
    }

    private static class Order{
        String name;
        String restaurantName;
        double wholePrice;
        int id;
        int quantity;

        private Order(String name, String restaurantName, int id, int quantity, double wholePrice) {
            this.name = name;
            this.id = id;
            this.quantity = quantity;
            this.restaurantName = restaurantName;
            this.wholePrice = wholePrice;
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

        public double getWholePrice() {
            return wholePrice;
        }

        public void setWholePrice(double wholePrice) {
            this.wholePrice = wholePrice;
        }
    }
}
