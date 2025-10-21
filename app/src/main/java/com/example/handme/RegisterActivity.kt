package com.example.handme

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        prefs = getSharedPreferences("UserData", MODE_PRIVATE)

        val usernameEdit = findViewById<EditText>(R.id.registerUsername)
        val emailEdit = findViewById<EditText>(R.id.registerEmail)
        val phoneEdit = findViewById<EditText>(R.id.registerPhone)
        val addressEdit = findViewById<EditText>(R.id.registerAddress)
        val passwordEdit = findViewById<EditText>(R.id.registerPassword)
        val registerButton = findViewById<Button>(R.id.registerButton)
        val loginText = findViewById<TextView>(R.id.loginText)

        registerButton.setOnClickListener {
            val username = usernameEdit.text.toString().trim()
            val email = emailEdit.text.toString().trim()
            val phone = phoneEdit.text.toString().trim()
            val address = addressEdit.text.toString().trim()
            val password = passwordEdit.text.toString().trim()

            if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบทุกช่อง", Toast.LENGTH_SHORT).show()
            } else {
                prefs.edit()
                    .putString("username", username)
                    .putString("email", email)
                    .putString("phone", phone)
                    .putString("address", address)
                    .putString("password", password)
                    .apply()

                Toast.makeText(this, "สมัครสมาชิกสำเร็จ", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        loginText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
