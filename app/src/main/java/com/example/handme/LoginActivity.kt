package com.example.handme

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        prefs = getSharedPreferences("UserData", MODE_PRIVATE)

        val usernameEdit = findViewById<EditText>(R.id.loginUsername)
        val passwordEdit = findViewById<EditText>(R.id.loginPassword)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerText = findViewById<TextView>(R.id.registerText)

        // **ลบส่วนตรวจสอบ isLoggedIn**
        // ถ้าอยาก Login ทุกครั้ง ไม่ต้องเช็คสถานะ

        loginButton.setOnClickListener {
            val username = usernameEdit.text.toString()
            val password = passwordEdit.text.toString()

            val savedUser = prefs.getString("username", null)
            val savedEmail = prefs.getString("email", null)
            val savedPassword = prefs.getString("password", null)

            if ((username == savedUser || username == savedEmail) && password == savedPassword) {
                // **ลบการเซฟ isLoggedIn**
                Toast.makeText(this, "เข้าสู่ระบบสำเร็จ", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง", Toast.LENGTH_SHORT).show()
            }
        }

        registerText.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
