package com.example.porotosqlite.controller

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.porotosqlite.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.usernameID)
        val password = findViewById<EditText>(R.id.passwordID)
        val login = findViewById<Button>(R.id.loginID)
        val forgetPassword = findViewById<Button>(R.id.forgetPasswordID)

        login.setOnClickListener {
            if (
                !TextUtils.isEmpty(username.text.toString()) &&
                !TextUtils.isEmpty(password.text.toString())
            ) {
                if (username.text.toString() == "ADMIN") {
                    startActivity(Intent(this, AdminChoreListActivity::class.java))
                } else {
                    UserChoreListActivity.username=username.text.toString()
                    startActivity(Intent(this, UserChoreListActivity::class.java))
                }
            } else {
                Toast.makeText(this, "Enter text fields", Toast.LENGTH_LONG).show()
            }
        }

    }
}