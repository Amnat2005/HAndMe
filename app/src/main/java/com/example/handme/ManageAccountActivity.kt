package com.example.handme

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ManageAccountActivity : AppCompatActivity() {

    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var saveButton: Button
    private lateinit var backButton: ImageButton  // แก้จาก Button → ImageButton
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_account)

        prefs = getSharedPreferences("UserData", MODE_PRIVATE)

        usernameInput = findViewById(R.id.usernameInput)
        passwordInput = findViewById(R.id.passwordInput)
        saveButton = findViewById(R.id.saveButton)
        backButton = findViewById(R.id.backButton) // ImageButton

        // โหลดค่าปัจจุบัน
        usernameInput.setText(prefs.getString("username", ""))
        passwordInput.setText(prefs.getString("password", ""))

        saveButton.setOnClickListener {
            val newUsername = usernameInput.text.toString()
            val newPassword = passwordInput.text.toString()

            if (newUsername.isNotBlank()) {
                prefs.edit().putString("username", newUsername).apply()
            }
            if (newPassword.isNotBlank()) {
                prefs.edit().putString("password", newPassword).apply()
            }

            Toast.makeText(this, "บัญชีถูกอัปเดตแล้ว", Toast.LENGTH_SHORT).show()
        }

        backButton.setOnClickListener {
            finish() // กลับหน้าหลัก MainActivity
        }
    }
}
