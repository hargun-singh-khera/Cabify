package com.example.cabify

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class RegisterScreen : AppCompatActivity() {

    private lateinit var nameEditText: EditText
//    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var mobileEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var confirmPasswordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_screen)

        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        mobileEditText = findViewById(R.id.mobileEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)

        val loginBtn = findViewById<Button>(R.id.register_Btn)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        toolbar.setTitle("Register")
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val cpassword = confirmPasswordEditText.text.toString()
            val mobile = mobileEditText.text.toString()

            if(password != cpassword) {
                Toast.makeText(this, "Password & Confirm Password doesn't matches.", Toast.LENGTH_SHORT).show()
            }
            else if (name.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty() && mobile.isNotEmpty() && cpassword.isNotEmpty()) {
                val values = ContentValues()
                values.put("name", name)
                values.put("email",email)
                values.put("mobile",mobile)
                values.put("password", password)

                // Insert user data into the SQLite database
                val db = DatabaseHelper(this).writableDatabase
                val newRowId = db.insert("users", null, values)

                if (newRowId != -1L) {
                    Toast.makeText(this@RegisterScreen, "Registration successful!", Toast.LENGTH_SHORT).show()
                    // Redirect to the login activity
                    val intent = Intent(this, LoginScreen::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Registration failed. Try again.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            }
        }

    }
}