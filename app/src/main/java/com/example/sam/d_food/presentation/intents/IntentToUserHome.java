package com.example.sam.d_food.presentation.intents;

import android.app.Activity;
import android.content.Intent;

import com.example.sam.d_food.presentation.user_page.UserHomePageActivity;

public class IntentToUserHome extends Intent{
    public IntentToUserHome (Activity activity){
        super(activity, UserHomePageActivity.class);
    }
}
