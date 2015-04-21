package com.example.sam.d_food.presentation.intents;

import android.app.Activity;
import android.content.Intent;

import com.example.sam.d_food.presentation.check_page.CheckActivity;

/**
 * Created by Sam on 4/20/2015.
 */
public class IntentToCheck extends Intent {
    public IntentToCheck (Activity activity){
        super(activity,CheckActivity.class);
    }
}
