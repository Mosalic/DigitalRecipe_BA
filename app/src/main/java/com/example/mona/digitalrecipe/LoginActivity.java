package com.example.mona.digitalrecipe;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.mona.digitalrecipe.Interfaces.AsyncTaskCallback;

import org.json.JSONArray;
import org.json.JSONException;

public class LoginActivity extends AppCompatActivity implements AsyncTaskCallback {
    //Variables
    private EditText etUsername, etPassword;
    private AlertDialog alertDialog;
    private String jsonID;
    private boolean jsonIsUser;
    private BackgroundHandler backgroundHandler;
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
        backgroundHandler = new BackgroundHandler(this);
        backgroundHandler.execute(type, userrole, username, password);
    }

    //execute from Register-Button, TEST, später eigene Activity
    public void onRegister(View view){
        Log.d(TAG, "onCrRegister: Button clicked"); //Test output

        //get text from the userinputs
        String username = etUsername.getText().toString();
       /* String password = etPassword.getText().toString();
        String type = "register";
        String userrole = "Patienten";

        //create instance of BackgroundWorker Class
        BackgroundHandler backgroundHandler = new BackgroundHandler(this);
        backgroundHandler.execute(type, userrole, username, password);*/

       userIsLoggedIn(username, true);
    }

    //test funtion call from async
    public void returnResult(String id){

    }

    //method from Interface, get result back from BackgroundHandler
    @Override
    public void getAsyncResult(JSONArray jsonArray) {
        //when id arrives start new Activity and pass the id with it, stop the thread
        backgroundHandler.cancel(true);

        //initialize Dialog
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Login Status");

        //get the id from the object index 0 (API just return one object with id for Login)
        try {
            jsonID = jsonArray.getJSONObject(0).getString("id");
            jsonIsUser =  Boolean.valueOf(jsonArray.getJSONObject(0).getString("isUser"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Interface in LoginActivity getAsyncResult: " + jsonID); //Test output

        //check if API returns if user exists in database
        if(jsonIsUser == true){
            //Testausspielung zeigt das Result an in einem Dialog
            /*alertDialog.setMessage(jsonID);
            alertDialog.show();*/

            //bei richtigem Login wird der User weitergeleitet
            userIsLoggedIn(jsonID, jsonIsUser);

        }else {
            alertDialog.setMessage("Überprüfen Sie ihre Angaben!");
            alertDialog.show();
        }
    }

    //transfer parameter to HomeActivity, and open HomeActivity
    private void userIsLoggedIn(String jsonID, boolean jsonIsUser){
        Intent intent = new Intent(this, HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("id", jsonID);
        bundle.putBoolean("isLoggedIn", jsonIsUser);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
