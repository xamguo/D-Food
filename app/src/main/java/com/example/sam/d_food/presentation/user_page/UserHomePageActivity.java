/* User home activity, just a guide page */
package com.example.sam.d_food.presentation.user_page;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sam.d_food.R;
import com.example.sam.d_food.entities.user.User;
import com.example.sam.d_food.presentation.intents.IntentToCheck;
import com.example.sam.d_food.presentation.intents.IntentToUserMap;


public class UserHomePageActivity extends Activity {
    Button orderButton;
    Button trackButton;
    Button infoButton;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_home_page);
        orderButton = (Button)findViewById(R.id.orders);
        trackButton = (Button)findViewById(R.id.track);
        infoButton = (Button)findViewById(R.id.info);
        name = (TextView)findViewById(R.id.userName);

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userCart();
            }
        });
        trackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userMap();
            }
        });
        if(User.getName() != null)
            name.setText(User.getName());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_home_page, menu);
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
    public void userMap() {
        IntentToUserMap intent = new IntentToUserMap(this);
        startActivity(intent);
    }
    public void userCart() {
        IntentToCheck intentToCheck = new IntentToCheck(this);
        startActivity(intentToCheck);
    }
}
