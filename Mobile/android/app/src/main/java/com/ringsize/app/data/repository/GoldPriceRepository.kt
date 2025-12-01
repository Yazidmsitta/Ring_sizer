package com.ringsize.app.data.repository

import com.ringsize.app.data.remote.ApiService
import com.ringsize.app.data.remote.model.GoldPriceResponse
import com.ringsize.app.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GoldPriceRepository(private val apiService: ApiService) {
    suspend fun getGoldPrices(
        token: String,
        karat: String? = null,
        period: String? = null
    ): Result<List<GoldPriceResponse>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getGoldPrices("Bearer $token", karat, period)
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






