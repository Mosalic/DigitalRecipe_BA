package com.example.mona.digitalrecipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    //Variables
    private EditText etUsername, etPassword;
    private static final String TAG = "LoginActivity"; //TAG for test outputs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d(TAG, "onCreate"); //Test output

        //initialise
        etUsername = (EditText) findViewById(R.id.et_Username);
        etPassword = (EditText) findViewById(R.id.et_Password);
    }

    //execute from Login-Button
    public void onLogin(View view){
        Log.d(TAG, "onLogin: Button clicked"); //Test output

        //get text from the userinputs
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String type = "login";
        String userrole = "Patienten";

        //create instance of BackgroundWorker Class
        BackgroundHandler backgroundHandler = new BackgroundHandler(this);
        backgroundHandler.execute(type, userrole, username, password);
    }

    //execute from Register-Button
    public void onRegister(View view){
        Log.d(TAG, "onCrRegister: Button clicked"); //Test output

        //get text from the userinputs
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String type = "register";
        String userrole = "Patienten";

        //create instance of BackgroundWorker Class
        BackgroundHandler backgroundHandler = new BackgroundHandler(this);
        backgroundHandler.execute(type, userrole, username, password);
    }
}
