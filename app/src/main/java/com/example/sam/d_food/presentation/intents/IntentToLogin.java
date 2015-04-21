package com.example.sam.d_food.presentation.intents;

import android.app.Activity;
import android.content.Intent;

import com.example.sam.d_food.presentation.login_page.LoginPageActivity;

/**
 * Created by Sam on 4/20/2015.
 */
public class IntentToLogin extends Intent {
    public IntentToLogin (Activity activity){
        super(activity, LoginPageActivity.class);
    }
}
