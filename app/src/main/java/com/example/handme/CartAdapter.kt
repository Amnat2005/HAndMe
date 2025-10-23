package com.example.handme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.handme.model.Product

class CartAdapter(
    val cartItems: MutableList<Product>,
    private val onCartUpdated: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.productImage)
        val title: TextView = itemView.findViewById(R.id.productTitle)
        val price: TextView = itemView.findViewById(R.id.productPrice)
        val qtyText: TextView = itemView.findViewById(R.id.quantityText)
        val plusBtn: ImageButton = itemView.findViewById(R.id.plusButton)
        val minusBtn: ImageButton = itemView.findViewById(R.id.minusButton)
        val removeBtn: Button = itemView.findViewById(R.id.removeButton)

        fun bind(product: Product) {
            title.text = product.title
            price.text = "฿${product.price * product.quantity}"
            qtyText.text = product.quantity.toString()
            Glide.with(itemView.context).load(product.thumbnail).into(image)

            plusBtn.setOnClickListener {
                product.quantity++
                qtyText.text = product.quantity.toString()
                price.text = "฿${product.price * product.quantity}"
                onCartUpdated()
            }

            minusBtn.setOnClickListener {
                if (product.quantity > 1) {
                    product.quantity--
                    qtyText.text = product.quantity.toString()
                    price.text = "฿${product.price * product.quantity}"
                    onCartUpdated()
                }
            }

            removeBtn.setOnClickListener {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    cartItems.removeAt(pos)
                    CartManager.removeFromCart(product) // ต้องเพิ่มฟังก์ชันใน CartManager
                    notifyItemRemoved(pos)
                    onCartUpdated()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart_product, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount() = cartItems.size
}
