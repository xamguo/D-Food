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

import com.example.sam.d_food.R;
import com.example.sam.d_food.presentation.deliveryman_page.DeliveryHomePageActivity;
import com.example.sam.d_food.presentation.user_page.UserHomePageActivity;


public class LoginPageActivity extends Activity {

    private TextView accountID;
    private TextView password;
    private boolean toggle;
    private Switch userSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

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
                                Intent launchUser;
                                if (toggle){
                                    launchUser=new Intent(LoginPageActivity.this, DeliveryHomePageActivity.class);
                                }else{
                                    launchUser=new Intent(LoginPageActivity.this, UserHomePageActivity.class);
                                }
                                launchUser.putExtra("toggle", toggle);
                                startActivity(launchUser);
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
                            Intent launchRegister=new Intent(LoginPageActivity.this, RegisterPageActivity.class);
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
}
