package com.example.sam.d_food.business.user;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.example.sam.d_food.integration.service.DataService;

public class Search {
    boolean mBound = false;
    Activity activity;

    public Search(Activity activity) {
        this.activity = activity;
    }

    public void search(double longitude, double latitude) {
        bindService();
    }

    private void unbindService() {
        if (mBound) {
            activity.unbindService(mConnection);
            mBound = false;
        }
    }

    private void bindService() {
        Intent intent = new Intent(activity, DataService.class);
        Log.v("Start bind service", "xiao");
        intent.putExtra("Test","This String is from homepage");
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
