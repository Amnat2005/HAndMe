package com.example.handme.api

import com.example.handme.model.Product
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import com.example.handme.model.ProductsResponse


interface ApiService {

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): ProductsResponse

    companion object {
        private const val BASE_URL = "https://dummyjson.com/"

        fun create(): ApiService {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
