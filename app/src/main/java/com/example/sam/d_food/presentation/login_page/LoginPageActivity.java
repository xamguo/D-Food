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
import com.example.sam.d_food.ws.remote.LoginProcess;


public class LoginPageActivity extends Activity {

    private TextView accountID;
    private TextView password;
    private boolean toggle;
    private Switch userSwitch;
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_page);

        accountID=(TextView)findViewById(R.id.accountID);
        password=(TextView)findViewById(R.id.password);

        mode = "user";
        userSwitch=(Switch)findViewById(R.id.switchUser);
        userSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggle=true;
                    mode = "deliveryman";
                } else {
                    toggle=false;
                    mode = "user";
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
            LoginProcess loginProcess = new LoginProcess(LoginPageActivity.this);
            loginProcess.execute(mode, accountID.getText().toString(), password.getText().toString());
        } // end method onClick
    };

    View.OnClickListener registerClicked = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
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
        }
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
