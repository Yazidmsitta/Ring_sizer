package com.ringsize.app.data.repository

import com.ringsize.app.data.remote.ApiService
import com.ringsize.app.data.remote.model.ProductResponse
import com.ringsize.app.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(private val apiService: ApiService) {
    suspend fun getProducts(
        token: String,
        category: String? = null,
        karat: String? = null,
        vendorId: Long? = null,
        minPrice: Double? = null,
        maxPrice: Double? = null
    ): Result<List<ProductResponse>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getProducts("Bearer $token", category, karat, vendorId, minPrice, maxPrice)
                if (response.isSuccessful && response.body() != null) {
                    Result.Success(response.body()!!)
                } else {
                    Result.Error(Exception(response.message()))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
    
    suspend fun getProduct(token: String, id: Long): Result<ProductResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getProduct("Bearer $token", id)
                if (response.isSuccessful && response.body() != null) {
                    Result.Success(response.body()!!)
                } else {
                    Result.Error(Exception(response.message()))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}






