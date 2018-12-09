package com.example.mona.digitalrecipe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.mona.digitalrecipe.Interfaces.AsyncTaskCallback;

import org.json.JSONArray;

public class RegisterActivity extends AppCompatActivity implements AsyncTaskCallback {
    //Variables
    private EditText etUsername, etPassword;
    private static final String TAG = "RegisterActivity"; //TAG for test outputs

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
        Log.d(TAG, "onRegister: Button clicked"); //Test output

        //get text from the userinputs
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String type = "register";
        String userrole = "Patienten";

        //create instance of BackgroundWorker Class
        BackgroundHandler backgroundHandler = new BackgroundHandler(this);
        backgroundHandler.execute(type, userrole, username, password);
    }

    @Override
    public void getAsyncResult(JSONArray jsonArray, String type) {

    }
}
