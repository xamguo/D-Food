package com.example.sam.d_food.presentation.main_flow_pages.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sam.d_food.R;
import com.example.sam.d_food.entities.data.Dish;

import java.util.ArrayList;

public class CustomDishArrayAdapter extends ArrayAdapter {
    ArrayList<Dish> dishes;
    Activity activity;
    TextView name;
    TextView price;

    public CustomDishArrayAdapter(Activity a, ArrayList<Dish> list) {
        super(a, R.layout.support_restaurant_list, list);
        dishes = list;
        activity = a;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;

        LayoutInflater inflater=activity.getLayoutInflater();
        row=inflater.inflate(R.layout.support_dish_list, parent, false);
        name = (TextView)row.findViewById(R.id.dishName);
        price = (TextView)row.findViewById(R.id.dishPrice);
        name.setText(dishes.get(position).getName());
        price.setText(String.valueOf(dishes.get(position).getPrice()));


        return row;
    }
}
