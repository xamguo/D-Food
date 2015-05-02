package com.example.sam.d_food.presentation.intents;

import android.app.Activity;
import android.content.Intent;

import com.example.sam.d_food.presentation.deliveryman_page.DeliveryHomePageActivity;

public class IntentToDeliverymanHome extends Intent{
    public IntentToDeliverymanHome (Activity activity){
        super(activity, DeliveryHomePageActivity.class);
    }
}
