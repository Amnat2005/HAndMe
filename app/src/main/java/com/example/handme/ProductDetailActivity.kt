package com.example.handme

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.handme.model.Product

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val imageView: ImageView = findViewById(R.id.productImage)
        val titleView: TextView = findViewById(R.id.productTitle)
        val priceView: TextView = findViewById(R.id.productPrice)
        val backButton: ImageButton = findViewById(R.id.backButton)
        val addToCartButton: Button = findViewById(R.id.addToCartButton)

        // รับ Product จาก Intent
        product = intent.getSerializableExtra("product") as Product

        // แสดงข้อมูลสินค้า
        titleView.text = product.title
        priceView.text = "฿${product.price}"
        Glide.with(this).load(product.thumbnail).into(imageView)

        // ปุ่มย้อนกลับ
        backButton.setOnClickListener {
            finish()
        }

        // ปุ่มเพิ่มลงตะกร้า
        addToCartButton.setOnClickListener {
            CartManager.addToCart(product)
            Toast.makeText(this, "เพิ่มลงตะกร้าแล้ว", Toast.LENGTH_SHORT).show()
        }
    }
}
