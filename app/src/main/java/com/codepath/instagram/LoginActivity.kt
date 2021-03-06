package com.codepath.instagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Check if there's a user logged in
        if (ParseUser.getCurrentUser() != null) {
            gotoMainActivity()
        }

        // If there is, take them to MainActivity

        findViewById<Button>(R.id.login_button).setOnClickListener{
            val username = findViewById<EditText>(R.id.et_username).text.toString()
            val password = findViewById<EditText>(R.id.et_password).text.toString()
            loginUser(username, password)
        }

        findViewById<Button>(R.id.signupBtn).setOnClickListener{
            val username = findViewById<EditText>(R.id.et_username).text.toString()
            val password = findViewById<EditText>(R.id.et_password).text.toString()
            signUpUser(username, password)
        }
    }

    private fun signUpUser(username: String, password: String) {
        // Create the ParseUser
        val user = ParseUser()

        //Set fields for the user to be created
        user.setUsername(username)
        user.setPassword(password)

        user.signUpInBackground { e ->
            if (e == null){
                // User has successfully created a new account
                Log.i(TAG, "Successfully signed up user")
                // Navigate the user to MainActivity
                gotoMainActivity()
                //Show a toast to indicate user successfully signed up for an account
                Toast.makeText(this, "Successfully signing up", Toast.LENGTH_SHORT).show()
            } else {
                // Show a toast to tell user sign up was not successful
                e.printStackTrace()
                Toast.makeText(this, "Error signing up", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(username: String, password: String) {
        ParseUser.logInInBackground(username, password, ({user, e->
            if (user != null ) {
                // User logged in
                Log.i(TAG, "Successfully logged in user")
                gotoMainActivity()
            } else {
                // SignUp failed. ParseException needs to be seen
                e.printStackTrace()
                Toast.makeText(this, "Error logging in", Toast.LENGTH_SHORT).show()
            }
        }))
    }

    private fun gotoMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity:: class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        const val TAG = "LoginActivity"
    }
}