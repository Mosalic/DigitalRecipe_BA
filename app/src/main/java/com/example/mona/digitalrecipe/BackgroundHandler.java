package com.example.mona.digitalrecipe;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

//this class stays in java, in Kotlin there are connection probems
public class BackgroundHandler extends AsyncTask<String, Void, String>{

    private Context context;
    AlertDialog alertDialog;
    private static final String TAG = "BackgroundHandler"; //TAG for test outputs

    public BackgroundHandler(Context ctx){
        Log.d(TAG, "Constructor"); //Test output
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        String type = params[0];
        String login_url = "http://192.168.0.101/API_Data/login.php"; //set WiFi IP (ipconfig 192.168.0.104), for localhost 10.0.2.2
        String register_url = "http://10.0.2.2/API_Data/registerUser.php";

        //Behavior for Login
        if(type.equals("login")){
            Log.d(TAG, "doInBackground: type Login"); //Test output

            try {
                String str_userRole = params[1];
                String str_username = params[2];
                String str_userPassword = params[3];

                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                //"userRole", "userName" und "userPassword" insert to login.php
                String post_data = URLEncoder.encode("userRole", "UTF-8") + "=" + URLEncoder.encode(str_userRole, "UTF-8") + "&"
                                     + URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(str_username, "UTF-8") + "&"
                                     + URLEncoder.encode("userPassword", "UTF-8") + "=" + URLEncoder.encode(str_userPassword, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String result = "";
                String line = "";
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();

                httpURLConnection.disconnect();

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        //Behavior for register
        else if(type.equals("register")){
            Log.d(TAG, "doInBackground: type Register"); //Test output

            try {
                String str_userRole = params[1];
                String str_username = params[2];
                String str_userPassword = params[3];

                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                //"userRole", "userName" und "userPassword" insert to login.php
                String post_data = URLEncoder.encode("userRole", "UTF-8") + "=" + URLEncoder.encode(str_userRole, "UTF-8") + "&"
                        + URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(str_username, "UTF-8") + "&"
                        + URLEncoder.encode("userPassword", "UTF-8") + "=" + URLEncoder.encode(str_userPassword, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String result = "";
                String line = "";
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();

                httpURLConnection.disconnect();

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        Log.d(TAG, "onPreExecute"); //Test output

        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result) {
        //super.onPostExecute(aVoid);
        //JSONObject jsonObject;
        JSONArray jsonArray;
        String jsonString = "";
        try{
            /*jsonObject = new JSONObject(result);
            jsonString = jsonObject.getString("id");*/
            //parse result to JSON
            jsonArray = new JSONArray(result);
            //get the id from the object index 0 (API just return one object with id for Login)
            jsonString = jsonArray.getJSONObject(0).getString("id");
        }catch(JSONException e){
            Log.e(TAG, "unexpected JSONException");
        }


        alertDialog.setMessage(jsonString);
        alertDialog.show();

        Log.d(TAG, "onPostExecute: result: " + jsonString); //Test output result
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}