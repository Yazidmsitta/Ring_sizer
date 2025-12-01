package com.ringsize.app.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    // URL pour l'émulateur Android : 10.0.2.2 pointe vers localhost de la machine hôte
    // Pour un appareil physique : utiliser l'IP locale de votre machine (ex: "http://192.168.1.100:8000/api/")
    // Pour trouver votre IP: Windows (ipconfig) ou Mac/Linux (ifconfig)
    // Changez cette IP selon votre réseau local
    private const val BASE_URL = "http://192.168.1.16:8000/api/"
    
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
    
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}


