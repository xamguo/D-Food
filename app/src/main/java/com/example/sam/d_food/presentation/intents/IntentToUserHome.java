package com.example.sam.d_food.presentation.intents;

import android.app.Activity;
import android.content.Intent;

import com.example.sam.d_food.presentation.user_page.UserHomePageActivity;

/**
 * Created by Sam on 4/20/2015.
 */
public class IntentToUserHome extends Intent{
    public IntentToUserHome (Activity activity){
        super(activity, UserHomePageActivity.class);
    }
}
