/* intent to login page */
package com.example.sam.d_food.presentation.intents;

import android.app.Activity;
import android.content.Intent;

import com.example.sam.d_food.presentation.login_page.LoginPageActivity;

public class IntentToLogin extends Intent {
    public IntentToLogin (Activity activity){
        super(activity, LoginPageActivity.class);
    }
}
