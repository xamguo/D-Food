/*
 * The user cart class, all items stored into cart are stored here.
 * An inner class is defined here that represent the user's order.
  * */
package com.example.sam.d_food.entities.user;

import java.util.ArrayList;

public class Cart {
    static public ArrayList<Order> myOrders = new ArrayList<>();

    public void addOrder(String name, String restaurantName, int id, int quantity, double wholePrice) {
           myOrders.add(new Order(name, restaurantName, id, quantity, wholePrice));
    }

    public int getOrderNum() {
        return myOrders.size();
    }

    public int getOrderID(int index) {
        return myOrders.get(index).getId();
    }

    public String getOrderName(int index) {
        return myOrders.get(index).getName();
    }

    public int getOrderQuantity(int index) {
        return myOrders.get(index).getQuantity();
    }

    public double getOrderPrice(int index) {
        return myOrders.get(index).getWholePrice();
    }

    public String getOrderRestaurantName(int index) {
        return myOrders.get(index).getRestaurantName();
    }

    public double getTotalPrice() {
        double price = 0;
        for(int i = 0; i < myOrders.size(); i++){
            price += this.getOrderPrice(i);
        }
        return price;
    }

    /* inner class. User's order */
    private static class Order{
        String name;            //dish name
        String restaurantName;//restaurant name
        double wholePrice;    //total price
        int id;                 //order id
        int quantity;          //quantities

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
