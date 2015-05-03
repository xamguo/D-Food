/*
* Activity to show the orders and order prices
* */
package com.example.sam.d_food.presentation.check_page;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.sam.d_food.R;
import com.example.sam.d_food.entities.user.CartBuilder;
import com.example.sam.d_food.entities.user.CartEditor;
import com.example.sam.d_food.entities.user.User;
import com.example.sam.d_food.ws.processes.PlaceOrderProcess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CheckActivity extends Activity {

    ListView listView;      //The list to show orders
    Button checkoutButton; //button to check out
    int userID;             //user id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_check);

        /* user id detection */
        userID = User.getId();
        if(userID == -1) {
            userID = 123;
        }

        /* Set the check-out list */
        listView = (ListView)findViewById(R.id.checklistView);
        SimpleAdapter adapter = new SimpleAdapter(this,getCheckData(),R.layout.support_check,
                new String[]{"check_name","check_restaurant","check_quantity","check_price"},
                new int[]{R.id.check_name,R.id.check_restaurant,R.id.check_quantity,R.id.check_price});
        listView.setAdapter(adapter);

        /* Set up the check-out button */
        checkoutButton = (Button)findViewById(R.id.startDeliverybutton);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });
    }

    /* Get the order info */
    private List<Map<String, String>> getCheckData() {
        CartEditor cartEditor = new CartBuilder();  //use cart to get the stored items
        List<Map<String, String>> list = new ArrayList<>();
        if(cartEditor.getOrderNum() != 0) {
            Map<String, String> map_total = new HashMap<>();
            map_total.put("check_name", "Total");
            map_total.put("check_restaurant", cartEditor.getOrderRestaurantName(0));
            map_total.put("check_quantity", " ");
            map_total.put("check_price", "$" + cartEditor.getTotalPrice());
            list.add(map_total);
        }
        for(int i = 0; i < cartEditor.getOrderNum(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put("check_name", cartEditor.getOrderName(i));
            map.put("check_restaurant", cartEditor.getOrderRestaurantName(i));
            map.put("check_quantity", String.valueOf(cartEditor.getOrderQuantity(i)));
            map.put("check_price", "$" + cartEditor.getOrderPrice(i));
            list.add(map);
        }
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_check, menu);
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

    private void check() {
        CartEditor cartEditor = new CartBuilder();
        for(int i = 0; i < cartEditor.getOrderNum(); i++) {
            PlaceOrderProcess order = new PlaceOrderProcess(this);
            order.execute(String.valueOf(cartEditor.getOrderID(i)),String.valueOf(cartEditor.getOrderQuantity(i)), String.valueOf(userID));
        }
    }
}
