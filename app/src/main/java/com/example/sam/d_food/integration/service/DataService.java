package com.example.sam.d_food.integration.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.sam.d_food.integration.database.DatabaseConnector;


public class DataService extends IntentService {
    DatabaseConnector db;

    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.sam.d_food.integration.service.action.FOO";
    private static final String ACTION_BAZ = "com.example.sam.d_food.integration.service.action.BAZ";

    private static final String EXTRA_PARAM1 = "com.example.sam.d_food.integration.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.sam.d_food.integration.service.extra.PARAM2";

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, DataService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, DataService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public DataService() {
        super("DataService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v("Service Started", "D-food");
        setupDatabase();//create database

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {

        Log.v("Binded from service","xiao");

        Bundle extras = intent.getExtras();
        String mode = (String)extras.get("Mode");
        if(mode.equals("Search"))
            searchMode(extras);
        return super.onBind(intent);
    }

    private void searchMode(Bundle extras) {

        String longitude = (String)extras.get("longitude");
        String latitude = (String)extras.get("latitude");
        String price = (String)extras.get("price");

        Log.v("Service get longitude",longitude);
        Log.v("Service get latitude",latitude);
        Log.v("Service get price",price);

        Search mySearch = new Search();
        mySearch.start();

    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public class Search extends Thread{

        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                Thread.sleep(500);
                Intent intent = new Intent();
                intent.setAction("MY_ACTION");

                intent.putExtra("DATAPASSED", 0);

                sendBroadcast(intent);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            stopSelf();
        }
    }

    public void setupDatabase() {
        db = new DatabaseConnector(this);
    }
}
