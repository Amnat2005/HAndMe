package com.example.handme.model

data class Category(
    val name: String,        // รหัส category สำหรับ API
    val displayName: String, // ชื่อแสดงผลบน UI
    val imageUrl: String     // รูปหมวดสินค้า
)
