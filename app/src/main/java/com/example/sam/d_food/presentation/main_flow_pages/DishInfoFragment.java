package com.example.sam.d_food.presentation.main_flow_pages;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sam.d_food.R;
import com.example.sam.d_food.entities.data.Dish;
import com.example.sam.d_food.entities.user.Cart;
import com.example.sam.d_food.presentation.intents.IntentToCheck;

/**
 * Created by Sam on 4/16/2015.
 */
public class DishInfoFragment extends Fragment{
    Button checkButton;
    Spinner spinner;
    TextView info;
    TextView comment;
    TextView restName;

    Dish dish;
    String restaurantName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_dish_info, container, false);
        spinner = (Spinner)v.findViewById(R.id.dishSpinner);
        info = (TextView)v.findViewById(R.id.dishInfo);
        restName = (TextView)v.findViewById(R.id.restaurantName);
        comment = (TextView)v.findViewById(R.id.commentsView);

        restName.setText(restaurantName);
        info.setText(dish.getName() + "\r\n" + "$" + dish.getPrice());
        comment.setText("Good dish");

        checkButton = (Button)v.findViewById(R.id.addToCart);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = spinner.getSelectedItemPosition() + 1;
                //Log.v("check quantity", String.valueOf(quantity));
                Cart.addOrder(dish.getName(), restaurantName, dish.getId(), quantity, dish.getPrice()*quantity);
                checkout();
            }
        });
        return v;
    }
    public void checkout() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Check out?")
                .setMessage("Cart added, check out?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        goCheck();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel(); // do nothing
                        getActivity().onBackPressed();
                    }
                })
                .show();

    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    private void goCheck(){
        IntentToCheck intentToCheck = new IntentToCheck(getActivity());
        startActivity(intentToCheck);
    }
}
