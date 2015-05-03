/*
* Activity for deliveryman home, display all the tasks for a deliveryman
* */
package com.example.sam.d_food.presentation.deliveryman_page;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.sam.d_food.R;
import com.example.sam.d_food.entities.deliveryman.Task;
import com.example.sam.d_food.entities.deliveryman.TaskProxy;
import com.example.sam.d_food.entities.user.User;
import com.example.sam.d_food.presentation.intents.IntentToDeliverymanMap;
import com.example.sam.d_food.ws.processes.GetTasksProcess;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class DeliveryHomePageActivity extends Activity {
    Button startDeliveryButton;     //button to start delivery

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_delivery_home_page);

        startDeliveryButton = (Button)findViewById(R.id.startDeliverybutton);

        try {
            GetTasksProcess getTasksProcess = new GetTasksProcess(User.getId(), DeliveryHomePageActivity.this);
            getTasksProcess.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<String> itemsArray = new ArrayList<>();
        ArrayList<Task> myTasks = TaskProxy.getTaskList();
        for(int i = 0; i < myTasks.size(); i++) {
            /* Prepare the deliveryman task list */
            itemsArray.add(myTasks.get(i).getUserName() + "  " + myTasks.get(i).getName());
        }

        /* set a simple array adapter to show the tasks of one deliveryman */
        ArrayAdapter adapter = new ArrayAdapter<>(this,R.layout.layout_listview, itemsArray);
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

    /* go to the deliveryman map to see task map */
    public void deliveryMap() {
        IntentToDeliverymanMap intent = new IntentToDeliverymanMap(this);
        intent.putExtra("deliverymanID", String.valueOf(User.getId()));
        startActivity(intent);
    }
}
