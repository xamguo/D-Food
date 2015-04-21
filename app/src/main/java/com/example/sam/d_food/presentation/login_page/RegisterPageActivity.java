package com.example.sam.d_food.presentation.login_page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sam.d_food.R;
import com.example.sam.d_food.entities.user.CRUD;
import com.example.sam.d_food.ws.remote.DatabaseConnector;
import com.example.sam.d_food.presentation.deliveryman_page.DeliveryHomePageActivity;
import com.example.sam.d_food.presentation.user_page.UserHomePageActivity;


public class RegisterPageActivity extends Activity {

    private EditText userName;
    private EditText password;
    private EditText re_password;
    private EditText address;
    private CRUD customer;
    private com.example.sam.d_food.entities.deliveryman.CRUD deliveryMan;
    private String toggle;
    private DatabaseConnector db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        userName=(EditText)findViewById(R.id.accountID);
        password=(EditText)findViewById(R.id.password);
        re_password=(EditText)findViewById(R.id.rePassword);
        address=(EditText)findViewById(R.id.address);


        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click

                db=new DatabaseConnector(getApplicationContext());
                if(password.getText().toString().equals(re_password.getText().toString())){
                    Intent intent=getIntent();
                    toggle=intent.getStringExtra("toggle");
                    if(toggle.equals("Deliveryman")){
                        deliveryMan=new com.example.sam.d_food.entities.deliveryman.CRUD(userName.getText().toString(), password.getText().toString(),address.getText().toString(), db);
                        deliveryMan.addDeliveryman();
                        Toast.makeText(getApplicationContext(), "Saved  ", Toast.LENGTH_SHORT).show();
                        Intent launchDeliveryHome=new Intent(RegisterPageActivity.this, DeliveryHomePageActivity.class);
                        launchDeliveryHome.putExtra("userName",userName.getText().toString());
                        startActivity(launchDeliveryHome);
                    }else {
                        customer=new CRUD(userName.getText().toString(), password.getText().toString(),address.getText().toString(), db);
                        customer.addUser();
                        Toast.makeText(getApplicationContext(), "Saved  ", Toast.LENGTH_SHORT).show();
                        Intent launchCustomerHome=new Intent(RegisterPageActivity.this, UserHomePageActivity.class);
                        launchCustomerHome.putExtra("userName",userName.getText().toString());
                        startActivity(launchCustomerHome);
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_page, menu);
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

    public void userhome(View view){
        Intent intent = new Intent(this, UserHomePageActivity.class);
        startActivity(intent);
    }
}
