package com.ringsize.app.data.remote

import com.ringsize.app.data.remote.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // Authentication
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
    
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>
    
    @POST("auth/logout")
    suspend fun logout(@Header("Authorization") token: String): Response<Unit>
    
    @POST("auth/forgot-password")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): Response<Unit>
    
    // Measurements
    @GET("measurements")
    suspend fun getMeasurements(@Header("Authorization") token: String): Response<List<MeasurementResponse>>
    
    @POST("measurements")
    suspend fun createMeasurement(
        @Header("Authorization") token: String,
        @Body request: MeasurementRequest
    ): Response<MeasurementResponse>
    
    @PUT("measurements/{id}")
    suspend fun updateMeasurement(
        @Header("Authorization") token: String,
        @Path("id") id: Long,
        @Body request: MeasurementRequest
    ): Response<MeasurementResponse>
    
    @DELETE("measurements/{id}")
    suspend fun deleteMeasurement(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<Unit>
    
    // Gold Prices
    @GET("gold-prices")
    suspend fun getGoldPrices(
        @Header("Authorization") token: String,
        @Query("karat") karat: String? = null,
        @Query("period") period: String? = null
    ): Response<List<GoldPriceResponse>>
    
    // Products
    @GET("products")
    suspend fun getProducts(
        @Header("Authorization") token: String,
        @Query("category") category: String? = null,
        @Query("karat") karat: String? = null,
        @Query("vendor_id") vendorId: Long? = null,
        @Query("min_price") minPrice: Double? = null,
        @Query("max_price") maxPrice: Double? = null
    ): Response<List<ProductResponse>>
    
    @GET("products/{id}")
    suspend fun getProduct(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<ProductResponse>
    
    // Settings
    @GET("settings")
    suspend fun getSettings(@Header("Authorization") token: String): Response<SettingsResponse>
    
    @PUT("settings")
    suspend fun updateSettings(
        @Header("Authorization") token: String,
        @Body request: SettingsRequest
    ): Response<SettingsResponse>
}

