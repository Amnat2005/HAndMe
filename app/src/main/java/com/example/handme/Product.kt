package com.example.handme.model

import java.io.Serializable

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val thumbnail: String,
    var quantity: Int = 1
) : Serializable
