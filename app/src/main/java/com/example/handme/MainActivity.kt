package com.example.handme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handme.api.ApiService
import com.example.handme.model.Category
import com.example.handme.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var productRecyclerView: RecyclerView
    private val api = ApiService.create()

    private val categories = listOf(
        Category("mens-shirts", "เสื้อผู้ชาย", "https://img.lazcdn.com/g/p/745f595705cec51ebf20055040132f5e.jpg_720x720q80.jpg"),
        Category("mens-pants", "กางเกงผู้ชาย", "https://media.allonline.7eleven.co.th/pdmain/677575-00-allonline-fs.jpg"),
        Category("mens-shoes", "รองเท้าผู้ชาย", "https://www.top10.in.th/wp-content/uploads/2022/07/%E0%B8%A3%E0%B8%AD%E0%B8%87%E0%B9%80%E0%B8%97%E0%B9%89%E0%B8%B2%E0%B8%9C%E0%B9%89%E0%B8%B2%E0%B9%83%E0%B8%9A-%E0%B8%9C%E0%B8%B9%E0%B9%89%E0%B8%8A%E0%B8%B2%E0%B8%A2.jpg"),
        Category("womens-dresses", "ชุดผู้หญิง", "https://img.lazcdn.com/g/p/661f2ea3d0da3b7d417591e55892b7e1.jpg_720x720q80.jpg"),
        Category("womens-tops", "เสื้อผู้หญิง", "https://cf.shopee.co.th/file/2e197fe819211021421999f07cc4848c"),
        Category("womens-shoes", "รองเท้าผู้หญิง", "https://img.lazcdn.com/g/p/69f556f58290011eed12a3cddda4ddbd.jpg_720x720q80.jpg")
    )

    private var allProducts = mutableListOf<Product>() // เก็บสินค้าทั้งหมด

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Views
        searchEditText = findViewById(R.id.searchEditText)
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView)
        productRecyclerView = findViewById(R.id.recyclerView)

        // LayoutManagers
        categoryRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        productRecyclerView.layoutManager = GridLayoutManager(this, 2)

        // Adapter หมวดสินค้า
        categoryRecyclerView.adapter = CategoryAdapter(categories) { category ->
            fetchProductsByCategory(category.name)
        }

        // โหลดสินค้าทั้งหมดเริ่มต้น
        fetchAllClothes()

        // ฟิลเตอร์การค้นหาแบบ real-time
        searchEditText.addTextChangedListener { text ->
            val query = text.toString().lowercase()
            val filtered = allProducts.filter { p -> p.title.lowercase().contains(query) }
            productRecyclerView.adapter = ProductAdapter(filtered)
        }
    }

    private fun fetchAllClothes() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                allProducts.clear()
                for (cat in categories) {
                    val response = api.getProductsByCategory(cat.name)
                    allProducts.addAll(response.products)
                }
                withContext(Dispatchers.Main) {
                    productRecyclerView.adapter = ProductAdapter(allProducts)
                }
            } catch (e: Exception) {
                Log.e("API", "Error fetching products: ${e.message}")
            }
        }
    }

    private fun fetchProductsByCategory(category: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = api.getProductsByCategory(category)
                withContext(Dispatchers.Main) {
                    productRecyclerView.adapter = ProductAdapter(response.products)
                }
            } catch (e: Exception) {
                Log.e("API", "Error fetching products: ${e.message}")
            }
        }
    }
}
