package com.example.mona.digitalrecipe

import android.app.AlertDialog
import android.content.Context
import android.os.AsyncTask
import android.util.Log

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder


//Connection Problems, so instead using BackgroundHandler.java
class BackgroundWorker(private val context: Context) : AsyncTask<String, Void, String>() {
    internal var alertDialog: AlertDialog? = null

    override fun doInBackground(vararg params: String): String? {

        val type = params[0]
        val loginURL = "http://10.0.2.2/android_connect/login.php" //set WiFi IP (ipconfig 192.168.0.104), for localhost 10.0.2.2
        val registerURL = "http://10.0.2.2/android_connect/register.php"

        //Behavior for Login
        if (type == "login") {
            Log.i("Debug", "Background doInBackground type Login")
            try {
                val strUsername = params[1]
                val strUserpassword = params[2]

                val url = URL(loginURL)
                val httpURLConnection = url.openConnection() as HttpURLConnection
                httpURLConnection.requestMethod = "POST"
                httpURLConnection.doOutput = true
                httpURLConnection.doInput = true

                val outputStream = httpURLConnection.outputStream
                val bufferedWriter = BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))

                //"user_name" und "user_password" insert to login.php
                val postData = (URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(strUsername, "UTF-8") + "&"
                        + URLEncoder.encode("user_password", "UTF-8") + "=" + URLEncoder.encode(strUserpassword, "UTF-8"))

                bufferedWriter.write(postData)
                bufferedWriter.flush()
                bufferedWriter.close()
                outputStream.close()

                val inputStream = httpURLConnection.inputStream
                val bufferedReader = BufferedReader(InputStreamReader(inputStream, "iso-8859-1"))

                var result = ""
                var line = bufferedReader.readLine()
                while (line  != null) {
                    result += line
                }

                bufferedReader.close()
                inputStream.close()

                httpURLConnection.disconnect()

                return result

            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        } else if (type == "register") {
            Log.i("Debug", "Background doInBackground type Register")
            try {
                val strUsername = params[1]
                val strUserpassword = params[2]

                val url = URL(registerURL)
                val httpURLConnection = url.openConnection() as HttpURLConnection
                httpURLConnection.requestMethod = "POST"
                httpURLConnection.doOutput = true
                httpURLConnection.doInput = true

                val outputStream = httpURLConnection.outputStream
                val bufferedWriter = BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))

                //"user_name" und "user_password" insert to login.php
                val postData = (URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(strUsername, "UTF-8") + "&"
                        + URLEncoder.encode("user_password", "UTF-8") + "=" + URLEncoder.encode(strUserpassword, "UTF-8"))

                bufferedWriter.write(postData)
                bufferedWriter.flush()
                bufferedWriter.close()
                outputStream.close()

                val inputStream = httpURLConnection.inputStream
                val bufferedReader = BufferedReader(InputStreamReader(inputStream, "iso-8859-1"))

                var result = ""
                var line = bufferedReader.readLine()
                while (line != null) {
                    result += line
                }

                bufferedReader.close()
                inputStream.close()

                httpURLConnection.disconnect()

                return result

            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }//Behavior for register

        return null
    }

    override fun onPreExecute() {
        //super.onPreExecute();
        Log.i("Debug", "Background OnPreExecute")
        alertDialog = AlertDialog.Builder(context).create()
        alertDialog?.setTitle("Login Status")
    }

    override fun onPostExecute(result: String) {
        //super.onPostExecute(aVoid);
        Log.i("Debug", "Background OnPostExecute $result")
        alertDialog?.setMessage(result)
        alertDialog?.show()

    }

    override fun onProgressUpdate(vararg values: Void) {
        super.onProgressUpdate(*values)
    }
}
