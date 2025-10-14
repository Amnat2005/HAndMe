package com.example.handme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handme.api.ApiService
import com.example.handme.model.Product
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val api = ApiService.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchAllClothes()
    }

    private fun fetchAllClothes() {
        val categories = listOf(
            "mens-shirts",
            "mens-pants",
            "mens-shoes",
            "womens-dresses",
            "womens-tops",
            "womens-shoes"
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val allProducts = mutableListOf<Product>()
                for (cat in categories) {
                    val response = api.getProductsByCategory(cat)
                    allProducts.addAll(response.products)
                }
                withContext(Dispatchers.Main) {
                    recyclerView.adapter = ProductAdapter(allProducts)
                }
            } catch (e: Exception) {
                Log.e("API", "Error fetching products: ${e.message}")
            }
        }
    }
}
