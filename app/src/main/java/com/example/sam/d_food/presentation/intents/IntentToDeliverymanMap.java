/* intent to deliveryman map page */
package com.example.sam.d_food.presentation.intents;

import android.app.Activity;
import android.content.Intent;

import com.example.sam.d_food.presentation.deliveryman_page.DeliveryManMapActivity;

public class IntentToDeliverymanMap extends Intent {
    public IntentToDeliverymanMap (Activity activity){
        super(activity, DeliveryManMapActivity.class);
    }
}
