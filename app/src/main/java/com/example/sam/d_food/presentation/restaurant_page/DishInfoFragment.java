package com.example.sam.d_food.presentation.restaurant_page;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.sam.d_food.R;
import com.example.sam.d_food.presentation.check_page.CheckActivity;

/**
 * Created by Sam on 4/16/2015.
 */
public class DishInfoFragment extends Fragment{
    Button checkButton;
    Spinner spinner;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_dish_info, container, false);
        spinner = (Spinner)v.findViewById(R.id.dishSpinner);

        checkButton = (Button)v.findViewById(R.id.addToCart);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = spinner.getSelectedItemPosition();
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
    private void goCheck(){
        Intent intent = new Intent(getActivity(), CheckActivity.class);
        startActivity(intent);
    }
}
