/*
* interface to edit the cart
* */
package com.example.sam.d_food.entities.user;

public interface CartEditor {
    public void addOrder(String name, String restaurantName, int id, int quantity, double wholePrice);
    public int getOrderNum();
    public int getOrderID(int index);
    public String getOrderName(int index);
    public int getOrderQuantity(int index);
    public double getOrderPrice(int index);
    public String getOrderRestaurantName(int index);
    public double getTotalPrice();
}
