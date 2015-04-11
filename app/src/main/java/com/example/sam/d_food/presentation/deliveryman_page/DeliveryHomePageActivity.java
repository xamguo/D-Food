package com.example.sam.d_food.presentation.deliveryman_page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sam.d_food.R;


public class DeliveryHomePageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_home_page);

        String[] itemsArray = {"Pizza, Arlington Ave", "Cake1, CMU", "Cake2, Downtown", "Cake3, S Aiken, Hurry"};

        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_listview, itemsArray);
        ListView listView = (ListView) findViewById(R.id.deliveryList);
        listView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delivery_home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void deliverymap(View view) {
        Intent intent = new Intent(this, DeliveryManMapActivity.class);
        startActivity(intent);
    }

}
