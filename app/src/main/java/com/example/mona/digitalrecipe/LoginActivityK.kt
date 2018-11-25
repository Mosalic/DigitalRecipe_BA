package com.example.mona.digitalrecipe

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText

class LoginActivityK : AppCompatActivity() {

    var etUsername: EditText? = null
    var etPassword: EditText? = null
    var strUsername: String = ""
    var strPassword: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Log.i("Debug", "LoginActivity OnCreate")
        etUsername = findViewById<View>(R.id.et_Username) as EditText
        etPassword = findViewById<View>(R.id.et_Password) as EditText
    }

    fun onLogin(view: View) {
        strUsername = etUsername?.text.toString()
        strPassword = etPassword?.text.toString()
        val type = "login"
        Log.i("Debug", "LoginActivity Login-Eingaben: $strUsername, $strPassword")
        //create instance of BackgroundWorker Class
        val backgroundHandler = BackgroundHandler(this)
        backgroundHandler.execute(type, strUsername, strPassword)
    }

    fun onRegister(view: View) {
        strUsername = etUsername?.text.toString()
        strPassword = etPassword?.text.toString()
        val type = "register"
        Log.i("Debug", "LoginActivity Register-Eingaben: $strUsername, $strPassword")
        //create instance of BackgroundWorker Class
        val backgroundHandler = BackgroundHandler(this)
        backgroundHandler.execute(type, strUsername, strPassword)
    }
}
