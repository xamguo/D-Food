package com.example.sam.d_food.presentation.login_page;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sam.d_food.R;
import com.example.sam.d_food.presentation.intents.IntentToDeliverymanHome;
import com.example.sam.d_food.presentation.intents.IntentToUserHome;


public class LoginPageActivity extends Activity {
/*
    private TextView accountID;
    private TextView password;
    private boolean toggle;
    private Switch userSwitch;
    private com.example.sam.d_food.entities.user.Login customer;
    private Login deliveryMan;
    private DatabaseConnector db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_page);

        accountID=(TextView)findViewById(R.id.accountID);
        password=(TextView)findViewById(R.id.password);

        userSwitch=(Switch)findViewById(R.id.switchUser);
        userSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggle=true;
                } else {
                    toggle=false;
                }
            }
        });


        Button login = (Button) findViewById(R.id.login);
        Button register = (Button) findViewById(R.id.register);

        login.setOnClickListener(loginClicked);
        register.setOnClickListener(registerClicked);

    }


    View.OnClickListener loginClicked = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
                AsyncTask<Object, Object, Object> saveContactTask =
                        new AsyncTask<Object, Object, Object>()
                        {
                            @Override
                            protected Object doInBackground(Object... params)
                            {
                                //check login
                                return null;
                            } // end method doInBackground

                            @Override
                            protected void onPostExecute(Object result)
                            {
                                IntentToUserHome launchUser = null;
                                IntentToDeliverymanHome launchDeliveryman = null;
                                String user = null;
                                db=new DatabaseConnector(getApplicationContext());
                                deliveryMan=new Login(accountID.getText().toString(), password.getText().toString(), db);
                                customer=new com.example.sam.d_food.entities.user.Login(accountID.getText().toString(), password.getText().toString(), db);
                                if (toggle && deliveryMan.authenticate()){
                                    launchDeliveryman = new IntentToDeliverymanHome(LoginPageActivity.this);
                                    user="Deliveryman";
                                    launchDeliveryman.putExtra("toggle", user);
                                    finish();
                                    startActivity(launchDeliveryman);
                                }else if(!toggle && customer.authenticate()){
                                    launchUser=new IntentToUserHome(LoginPageActivity.this);
                                    user="Customer";
                                    launchUser.putExtra("toggle", user);
                                    finish();
                                    startActivity(launchUser);
                                }else{
                                    Toast.makeText(getApplicationContext(), "Wrong credentials  ", Toast.LENGTH_LONG).show();
                                }
                            } // end method onPostExecute
                        }; // end AsyncTask

                // save the contact to the database using a separate thread
                saveContactTask.execute((Object[]) null);
        } // end method onClick
    };

    View.OnClickListener registerClicked = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            AsyncTask<Object, Object, Object> saveContactTask =
                    new AsyncTask<Object, Object, Object>()
                    {
                        @Override
                        protected Object doInBackground(Object... params)
                        {
                            return null;
                        } // end method doInBackground

                        @Override
                        protected void onPostExecute(Object result)
                        {
                            String user;
                            if (toggle){
                                user="Deliveryman";
                            }else{
                                user="Customer";
                            }
                            Intent launchRegister=new Intent(LoginPageActivity.this, RegisterPageActivity.class);
                            launchRegister.putExtra("toggle", user);
                            startActivity(launchRegister);
                        } // end method onPostExecute
                    }; // end AsyncTask

            // save the contact to the database using a separate thread
            saveContactTask.execute((Object[]) null);
        } // end method onClick
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_page, menu);
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
    */
}
