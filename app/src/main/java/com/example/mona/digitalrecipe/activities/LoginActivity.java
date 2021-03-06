package com.example.mona.digitalrecipe.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mona.digitalrecipe.asynctasks.BackgroundHandler;
import com.example.mona.digitalrecipe.interfaces.AsyncTaskCallback;

import org.json.JSONArray;
import org.json.JSONException;

public class LoginActivity extends AppCompatActivity implements AsyncTaskCallback {
    //Variables
    private EditText etUsername, etPassword;
    private AlertDialog alertDialog;
    private String jsonID;
    private boolean jsonIsUser;
    private BackgroundHandler backgroundHandler;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.mona.digitalrecipe.R.layout.activity_login);

        //initialise
        etUsername = (EditText) findViewById(com.example.mona.digitalrecipe.R.id.et_Username);
        etPassword = (EditText) findViewById(com.example.mona.digitalrecipe.R.id.et_Password);
    }

    //execute from Login-Button
    public void onLogin(View view){

        //get text from the userinputs
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String type = "login";
        String userrole = "Patienten";

        //create instance of BackgroundWorker Class
        backgroundHandler = new BackgroundHandler(this);
        backgroundHandler.execute(type, userrole, username, password);
    }

    //link user to RegisterActivity
    public void onRegister(View view) {
        Log.d(TAG, " LoginActivity onRegister" );
        Toast.makeText(this, "Registrierung noch in Bearbeitung", Toast.LENGTH_LONG).show();
    }


    //method from Interface, get result back from BackgroundHandler
    @Override
    public void getAsyncResult(JSONArray jsonArray, String type) {
        //when id arrives start new Activity and pass the id with it, stop the thread
        backgroundHandler.cancel(true);

        if(type.equals("login")){

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

            //Log.d(TAG, "Interface in LoginActivity getAsyncResult: " + jsonID);

            //check if API returns if user exists in database
            if(jsonIsUser == true){
                userIsLoggedIn(jsonID, jsonIsUser);
            }else {
                alertDialog.setMessage("Überprüfen Sie ihre Angaben!");
                alertDialog.show();
            }
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
