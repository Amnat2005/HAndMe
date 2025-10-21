package com.example.handme

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ManageAccountActivity : AppCompatActivity() {

    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var addressInput: EditText
    private lateinit var saveButton: Button
    private lateinit var backButton: ImageButton
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_account)

        prefs = getSharedPreferences("UserData", MODE_PRIVATE)

        usernameInput = findViewById(R.id.usernameInput)
        passwordInput = findViewById(R.id.passwordInput)
        phoneInput = findViewById(R.id.phoneInput)
        addressInput = findViewById(R.id.addressInput)
        saveButton = findViewById(R.id.saveButton)
        backButton = findViewById(R.id.backButton)

        // โหลดข้อมูลปัจจุบัน
        usernameInput.setText(prefs.getString("username", ""))
        passwordInput.setText(prefs.getString("password", ""))
        phoneInput.setText(prefs.getString("phone", ""))
        addressInput.setText(prefs.getString("address", ""))

        // ปุ่มบันทึก
        saveButton.setOnClickListener {
            val newUsername = usernameInput.text.toString()
            val newPassword = passwordInput.text.toString()
            val newPhone = phoneInput.text.toString()
            val newAddress = addressInput.text.toString()

            prefs.edit().apply {
                if (newUsername.isNotBlank()) putString("username", newUsername)
                if (newPassword.isNotBlank()) putString("password", newPassword)
                if (newPhone.isNotBlank()) putString("phone", newPhone)
                if (newAddress.isNotBlank()) putString("address", newAddress)
                apply()
            }

            Toast.makeText(this, "บัญชีถูกอัปเดตแล้ว", Toast.LENGTH_SHORT).show()
        }

        backButton.setOnClickListener {
            finish() // กลับหน้าหลัก
        }
    }
}
