package com.example.cabify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginScreen : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)

        emailEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        val forgotPassword = findViewById<Button>(R.id.forgotPassword)
        val loginBtn = findViewById<Button>(R.id.register_Btn)
        val register = findViewById<Button>(R.id.register)

        forgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordScreen::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener {

            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            val db = DatabaseHelper(this).readableDatabase
            val selection = "email = ? AND password = ?"
            val selectionArgs = arrayOf(email, password)
            val cursor = db.query("users", null, selection, selectionArgs, null, null, null)

            if (cursor.moveToFirst()) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, DashboardScreen::class.java)
                startActivity(intent)
                finish()
            } else {

                Toast.makeText(this, "Login failed. Invalid credentials.", Toast.LENGTH_SHORT).show()
            }

            cursor.close()
        }

        register.setOnClickListener {
            val intent = Intent(this, RegisterScreen::class.java)
            startActivity(intent)
        }
    }
}