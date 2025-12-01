package com.ringsize.app.data.remote.model

import com.google.gson.annotations.SerializedName
import com.ringsize.app.data.model.MeasurementType

// Authentication Models
data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val password_confirmation: String
)

data class AuthResponse(
    val token: String,
    val user: UserResponse
)

data class UserResponse(
    val id: Long,
    val name: String,
    val email: String,
    @SerializedName("user_type") val userType: String
)

// Measurement Models
data class MeasurementResponse(
    val id: Long,
    @SerializedName("user_id") val userId: Long?,
    val name: String,
    val type: String,
    @SerializedName("diameter_mm") val diameterMm: Double?,
    @SerializedName("circumference_mm") val circumferenceMm: Double?,
    @SerializedName("size_eu") val sizeEu: Double?,
    @SerializedName("size_us") val sizeUs: Double?,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("last_synced_at") val lastSyncedAt: String?
)

data class MeasurementRequest(
    val name: String,
    val type: String,
    @SerializedName("diameter_mm") val diameterMm: Double?,
    @SerializedName("circumference_mm") val circumferenceMm: Double?,
    @SerializedName("size_eu") val sizeEu: Double?,
    @SerializedName("size_us") val sizeUs: Double?
)

// Gold Price Models
data class GoldPriceResponse(
    val id: Long,
    val karat: String,
    @SerializedName("price_per_gram") val pricePerGram: Double,
    val currency: String,
    @SerializedName("date_recorded") val dateRecorded: String
)

// Product Models
data class ProductResponse(
    val id: Long,
    @SerializedName("vendor_id") val vendorId: Long,
    val name: String,
    val description: String?,
    val price: Double,
    val currency: String,
    val karat: String?,
    val category: String,
    @SerializedName("main_image_url") val mainImageUrl: String?,
    @SerializedName("is_available") val isAvailable: Boolean,
    val vendor: VendorResponse?
)

data class VendorResponse(
    val id: Long,
    @SerializedName("shop_name") val shopName: String,
    val description: String?,
    @SerializedName("logo_url") val logoUrl: String?,
    @SerializedName("website_url") val websiteUrl: String?,
    @SerializedName("is_verified") val isVerified: Boolean
)

// Settings Models
data class SettingsResponse(
    @SerializedName("preferred_unit") val preferredUnit: String,
    val language: String
)

data class SettingsRequest(
    @SerializedName("preferred_unit") val preferredUnit: String,
    val language: String
)

data class ForgotPasswordRequest(
    val email: String
)

