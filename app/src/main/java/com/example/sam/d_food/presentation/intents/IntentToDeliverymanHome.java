package com.example.sam.d_food.presentation.intents;

import android.app.Activity;
import android.content.Intent;

import com.example.sam.d_food.presentation.deliveryman_page.DeliveryHomePageActivity;

/**
 * Created by Sam on 4/20/2015.
 */
public class IntentToDeliverymanHome extends Intent{
    public IntentToDeliverymanHome (Activity activity){
        super(activity, DeliveryHomePageActivity.class);
    }
}
