package com.example.sam.d_food.presentation.main_page;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sam.d_food.R;
import com.example.sam.d_food.business.user.Search;
import com.example.sam.d_food.business.user.SearchReceiver;
import com.example.sam.d_food.integration.service.DataService;
import com.example.sam.d_food.presentation.login_page.LoginPageActivity;
import com.example.sam.d_food.presentation.restaurant_page.RestaurantResultListActivity;


public class HomePageActivity extends Activity {
    boolean mBound = false;
    SearchReceiver searchReceiver;
    public static ProgressDialog dialog;
    private String price;
    static boolean receiverSign = false;
    private EditText textView_location;
    Search s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //submission Test
        setContentView(R.layout.activity_home_page);

        textView_location = (EditText) findViewById(R.id.locationField);
        startService();

        SeekBar seekBar = (SeekBar)findViewById(R.id.homeSeekBar);
        seekBar.incrementProgressBy(10);
        seekBar.setMax(20);
        seekBar.setProgress(20);
        price = "2";

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress / 10;
                progress = progress * 10;
                switch (progress){
                    case 0:
                        price = "0";
                        break;
                    case 10:
                        price = "1";
                        break;
                    case 20:
                        price = "2";
                        break;
                    default:
                        //Exception
                        price = "3";
                        break;
                }
                seekBar.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.login)
        {
            Intent intent = new Intent(this,LoginPageActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void searchbutton(View view){

        String location = textView_location.getText().toString();

        if(s != null) {
            s.unbindService();
        }

        if(receiverSign == true){
            unReceiver();
        }

        receiver();

        dialog = new ProgressDialog(this);
        dialog.show();

        s = new Search(HomePageActivity.this);
        double x,y;
        x=1.1;
        y=2.2;      //value for simulation
        s.search(x,y,price,location);
    }

    @Override
    protected void onPause() {
        if(s != null) {
            //unReceiver();
            s.unbindService();
            Log.v("unbindService","here");
        }
        super.onPause();
    }

    private void startService() {
        startService(new Intent(this, DataService.class));
    }


    private void receiver() {
        searchReceiver = new SearchReceiver(this, RestaurantResultListActivity.class);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("MY_ACTION");
        registerReceiver(searchReceiver, intentFilter);
        receiverSign = true;
    }

    private void unReceiver() {
        receiverSign = false;
        unregisterReceiver(searchReceiver);
    }

}
