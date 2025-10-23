package com.example.handme

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handme.model.Product

class CartActivity : AppCompatActivity() {

    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var totalPriceText: TextView
    private lateinit var backButton: ImageButton
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        cartRecyclerView = findViewById(R.id.cartRecyclerView)
        totalPriceText = findViewById(R.id.totalPriceText)
        backButton = findViewById(R.id.backButton)

        backButton.setOnClickListener { finish() }

        cartAdapter = CartAdapter(CartManager.getCartItems().toMutableList()) { updateTotalPrice() }
        cartRecyclerView.layoutManager = LinearLayoutManager(this)
        cartRecyclerView.adapter = cartAdapter

        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        val total = cartAdapter.cartItems.sumOf { it.price * it.quantity }
        totalPriceText.text = "รวม: ฿${total}"
    }
}
