package com.example.mona.digitalrecipe.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.mona.digitalrecipe.interfaces.AsyncTaskCallback;

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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

//this class stays in java, in Kotlin there are connection probems
public class BackgroundHandler extends AsyncTask<String, Void, String>{

    //private Context context;
    private AsyncTaskCallback asyncCallback;
    private String type;
    private String str_userRole;
    private String str_username;
    private String str_userPassword;
    private URL url;
    private HttpURLConnection httpURLConnection ;
    private String post_data;
    private OutputStream outputStream;
    private BufferedWriter bufferedWriter;
    private InputStream inputStream;
    private BufferedReader bufferedReader;
    private static final String TAG = "BackgroundHandler"; //TAG for test outputs

    public BackgroundHandler(AsyncTaskCallback ctx){
        Log.d(TAG, "Constructor"); //Test output
        asyncCallback = ctx;
    }

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        Log.d(TAG, "onPreExecute"); //Test output

    }

    @Override
    protected String doInBackground(String... params) {

        type = params[0];
        String login_url = "http://192.168.0.101/API_Data/login.php"; //set WiFi IP (ipconfig 192.168.0.104), for localhost 10.0.2.2
        String register_url = "http://10.0.2.2/API_Data/registerUser.php";
        String getRequires_url = "http://192.168.0.101/API_Data/getRequires.php";
        String getRecipes_url = "http://192.168.0.101/API_Data/getRecipes.php";
        String getDoctors_url = "http://192.168.0.101/API_Data/getDoctors.php";
        String releaseRequire_url = "http://192.168.0.101/API_Data/releaseRequire.php";

        //Behavior for Login
        if(type.equals("login")){
            Log.d(TAG, "doInBackground: type Login"); //Test output

            setParams(params);

            setAPIConnection(login_url, "POST");

            setLoginData();

            postingData(post_data);

            return receivingData();

        }
        //Behavior for register
        else if(type.equals("register")){
            Log.d(TAG, "doInBackground: type Register"); //Test output

            try {
                setParams(params);

                //setAPIConnection(register_url, "POST");
                url = new URL(register_url);
                httpURLConnection = (HttpURLConnection) url.openConnection();
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
        }else if (type.equals("getRequires")){
            Log.d(TAG, "doInBackground: type getRequires"); //Test output

            str_userRole = params[1];
            String str_userID = params[2];

            setAPIConnection(getRequires_url, "POST");

            try {
                post_data = URLEncoder.encode("userRole", "UTF-8") + "=" + URLEncoder.encode(str_userRole, "UTF-8") + "&"
                        + URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(str_userID, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            postingData(post_data);

            return receivingData();
        }else if (type.equals("getRecipes")){
            Log.d(TAG, "doInBackground: type getRecipes"); //Test output

            str_userRole = params[1];
            String str_userID = params[2];

            setAPIConnection(getRecipes_url, "POST");

            try {
                post_data = URLEncoder.encode("userRole", "UTF-8") + "=" + URLEncoder.encode(str_userRole, "UTF-8") + "&"
                        + URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(str_userID, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            postingData(post_data);

            return receivingData();
        }else if (type.equals("getDoctors")){
            Log.d(TAG, "doInBackground: type getDoctors"); //Test output

            str_userRole = params[1];

            setAPIConnection(getDoctors_url, "POST");

            try {
                post_data = URLEncoder.encode("userRole", "UTF-8") + "=" + URLEncoder.encode(str_userRole, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            postingData(post_data);

            return receivingData();
        }else if (type.equals("createNewRequire")){
            Log.d(TAG, "doInBackground: type createNewRequire"); //Test output

            str_userRole = params[1];
            String str_userID = params[2];
            String str_usersComplaint = params[3];
            String str_usersMedicine = params[4];
            String str_usersDoctor = params[5];

            setAPIConnection(releaseRequire_url, "POST");

            try {
                post_data = URLEncoder.encode("userRole", "UTF-8") + "=" + URLEncoder.encode(str_userRole, "UTF-8") + "&"
                        + URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(str_userID, "UTF-8") + "&"
                        + URLEncoder.encode("usersComplaint", "UTF-8") + "=" + URLEncoder.encode(str_usersComplaint, "UTF-8") + "&"
                        + URLEncoder.encode("usersMedicine", "UTF-8") + "=" + URLEncoder.encode(str_usersMedicine, "UTF-8") + "&"
                        + URLEncoder.encode("usersDoctor", "UTF-8") + "=" + URLEncoder.encode(str_usersDoctor, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            postingData(post_data);

            return receivingData();
        }

        Log.d(TAG, "doInBackground: receivingData null "); //Test output result
        return null; //??
    }


    @Override
    protected void onPostExecute(String result) {
        //super.onPostExecute(aVoid);
        //JSONObject jsonObject;
        Log.d(TAG, "onPostExecute result:" + result); //Test output result

        JSONArray jsonArray = null;
        String jsonID = "";
        boolean jsonIsUser = false;

        try{
            jsonArray = new JSONArray(result);
            //parse result into JSON
            //get the id from the object index 0 (API just return one object with id for Login)
            /*jsonID = jsonArray.getJSONObject(0).getString("id");
            jsonIsUser =  Boolean.valueOf(jsonArray.getJSONObject(0).getString("isUser"));*/
            //transfer result back to the activity
           // asyncCallback.getAsyncResult(jsonArray);
        }catch(JSONException e){
            Log.d(TAG, "onPostExecute: unexpected JSONException "); //Test output result

            //set Default JSONArray when Exceptions executes
            JSONObject jsonObject = new JSONObject();
            jsonID = "unexpected JSONException";

            //no result, return ErrorInfo
            try {
                if(type.equals("login")){
                    Log.d(TAG, "onPostExecute: unexpected JSONException: type Login"); //Test output result
                    jsonObject.put("id", jsonID);
                    jsonObject.put("isUser", jsonIsUser);
                    Log.d(TAG, "onPostExecute: unexpected JSONException: result: " + jsonID + ", " + jsonIsUser); //Test output result
                    //transfer result back to the activity
                    //asyncCallback.getAsyncResult(jsonArray);
                }else if(type.equals("getRequires")){
                    Log.d(TAG, "onPostExecute: unexpected JSONException type getRequires"); //Test output result
                    //jsonObject.put("request", false);
                }
                jsonArray = new JSONArray();
                Log.d(TAG, "onPostExecute: unexpected JSONException jsonObject: " + jsonObject); //Test output result
                jsonArray.put(jsonObject);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }

        //transfer result back to the activity
        asyncCallback.getAsyncResult(jsonArray, type);


    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


    //own functions
    //set the Parameter from the Login
    private void setParams(String... params){
        str_userRole = params[1];
        str_username = params[2];
        str_userPassword = params[3];
    }

    //open connection to API/database
    private void setAPIConnection(String str_url, String request_method){
        try {
            url = new URL(str_url);

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(request_method);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //set posting data
    private void setLoginData() {
        //"userRole", "userName" und "userPassword" insert to login.php
        try {
            post_data = URLEncoder.encode("userRole", "UTF-8") + "=" + URLEncoder.encode(str_userRole, "UTF-8") + "&"
                    + URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(str_username, "UTF-8") + "&"
                    + URLEncoder.encode("userPassword", "UTF-8") + "=" + URLEncoder.encode(str_userPassword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void postingData(String post_data) {
        try {
            outputStream = httpURLConnection.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String receivingData(){
        String result = "";

        try {
            inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

            String line = "";
            while((line = bufferedReader.readLine()) != null){
                result += line;
            }

            bufferedReader.close();
            inputStream.close();

            httpURLConnection.disconnect();

            Log.d(TAG, "receivingData result: " + result); //Test output result

            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return "Unexpected IOException, no result";
        }
    }
}
