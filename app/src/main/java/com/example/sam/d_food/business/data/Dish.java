package com.example.sam.d_food.business.data;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.example.sam.d_food.integration.service.DataService;

/**
 * Created by Sam on 4/11/2015.
 */
public class Dish {
    boolean mBound = false;
    Activity activity;
    int restaurant_id;
    String location;

    public Dish(Activity activity) {
        this.activity = activity;
    }

    public void getDish(int restaurant_id) {
        this.restaurant_id = restaurant_id;
        bindService(String.valueOf(restaurant_id));
    }

    public void unbindService() {
        if (mBound) {
            activity.unbindService(mConnection);
            mBound = false;
            Log.v("Search class", "unbindService");
        }
    }

    private void bindService(String restaurant_id) {

        Intent intent = new Intent(activity, DataService.class);
        intent.putExtra("Mode","Dish");
        intent.putExtra("restaurant_id",restaurant_id);

        mBound = true;
        boolean b = activity.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        Log.v("connected service",String.valueOf(b));
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            mBound = true;
            Log.v("connected service","xiao");
        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
}

