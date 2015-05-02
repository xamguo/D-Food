package com.example.sam.d_food.presentation.deliveryman_page;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.sam.d_food.R;
import com.example.sam.d_food.presentation.intents.IntentToDeliverymanMap;
import com.example.sam.d_food.ws.remote.GetTasksProcess;


public class DeliveryHomePageActivity extends Activity {
    private String dManID;
    Button startDeliveryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_delivery_home_page);

        GetTasksProcess getTasksProcess = new GetTasksProcess(2,DeliveryHomePageActivity.this);
        getTasksProcess.execute();

        startDeliveryButton = (Button)findViewById(R.id.startDeliverybutton);

        String[] itemsArray = {"Pizza, Arlington Ave", "Cake1, CMU", "Cake2, Downtown", "Cake3, S Aiken, Hurry"};

        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.layout_listview, itemsArray);
        ListView listView = (ListView) findViewById(R.id.deliveryList);
        listView.setAdapter(adapter);

        startDeliveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deliveryMap();
            }
        });
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

    public void deliveryMap() {
        IntentToDeliverymanMap intent = new IntentToDeliverymanMap(this);   //what data is needed
        dManID = "1";
        intent.putExtra("deliverymanID", dManID);
        startActivity(intent);
    }
}
