package com.example.sam.d_food.presentation.intents;

import android.app.Activity;
import android.content.Intent;

import com.example.sam.d_food.presentation.user_page.TrackDeliveryManActivity;

/**
 * Created by Sam on 4/20/2015.
 */
public class IntentToUserMap extends Intent{
    public IntentToUserMap (Activity activity){
        super(activity, TrackDeliveryManActivity.class);
    }
}
