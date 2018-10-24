package com.example.mona.digitalrecipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    String str_username, str_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i("Debug", "MainActivity OnCreate");
        etUsername = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);
    }

    public void onLogin(View view){
        str_username = etUsername.getText().toString();
        str_password = etPassword.getText().toString();
        String type = "login";
        Log.i("Debug", "MainActivity Login-Eingaben: " + str_username + ", " + str_password);
        //create instance of BackgroundWorker Class
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, str_username, str_password);
    }

    public void onRegister(View view){
        str_username = etUsername.getText().toString();
        str_password = etPassword.getText().toString();
        String type = "register";
        Log.i("Debug", "MainActivity Register-Eingaben: " + str_username + ", " + str_password);
        //create instance of BackgroundWorker Class
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, str_username, str_password);
    }
}
