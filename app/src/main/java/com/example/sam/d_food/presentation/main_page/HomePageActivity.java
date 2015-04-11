package com.example.sam.d_food.presentation.main_page;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;

import com.example.sam.d_food.R;
import com.example.sam.d_food.integration.service.DataService;
import com.example.sam.d_food.presentation.login_page.LoginPageActivity;
import com.example.sam.d_food.presentation.restaurant_page.RestaurantResultListActivity;


public class HomePageActivity extends Activity {
    DataService service;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //submission Test
        setContentView(R.layout.activity_home_page);

        startService();


        SeekBar seekBar = (SeekBar)findViewById(R.id.homeSeekBar);
        seekBar.incrementProgressBy(10);
        seekBar.setMax(20);
        seekBar.setProgress(20);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress / 10;
                progress = progress * 10;
                seekBar.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                bindService();
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
        Intent intent = new Intent(this,RestaurantResultListActivity.class);
        startActivity(intent);
    }

    private void startService() {
        startService(new Intent(this, DataService.class));
    }

    private void unbindService() {
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    private void bindService() {
        Intent intent = new Intent(this, DataService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
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
