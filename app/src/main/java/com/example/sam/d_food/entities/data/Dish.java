/*
* Dish Class
* */
package com.example.sam.d_food.entities.data;

public class Dish {
    private int id;           //dish id
    private double price;   //dish price
    private String name;     //dish name

    public Dish(int id, double price, String name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

