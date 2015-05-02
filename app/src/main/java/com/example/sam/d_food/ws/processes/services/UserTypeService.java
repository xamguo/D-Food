package com.example.sam.d_food.ws.processes.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class UserTypeService extends IntentService {
    static String userType;

    public UserTypeService() {
        super("UserTypeService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v("myService","Bind_inside");
        String dataString = userType;
        Toast.makeText(getApplicationContext(),
                dataString, Toast.LENGTH_SHORT).show();
        return super.onBind(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    public static void setUserType(String userType) {
        UserTypeService.userType = userType;
    }
}
