package com.ringsize.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ringsize.app.RingSizerApplication
import com.ringsize.app.data.remote.ApiService
import com.ringsize.app.data.remote.RetrofitClient
import com.ringsize.app.data.repository.AuthRepository
import com.ringsize.app.data.repository.MeasurementRepository
import com.ringsize.app.util.SharedPreferencesManager

class AuthViewModelFactory(
    private val prefsManager: SharedPreferencesManager,
    private val application: RingSizerApplication
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            val apiService = RetrofitClient.apiService
            val authRepository = AuthRepository(apiService)
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(authRepository, prefsManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MeasurementViewModelFactory(
    private val prefsManager: SharedPreferencesManager,
    private val application: RingSizerApplication
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MeasurementViewModel::class.java)) {
            val apiService = RetrofitClient.apiService
            val measurementRepository = MeasurementRepository(
                application.database.measurementDao(),
                apiService
            )
            @Suppress("UNCHECKED_CAST")
            return MeasurementViewModel(measurementRepository, prefsManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class GoldPriceViewModelFactory(
    private val prefsManager: SharedPreferencesManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GoldPriceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GoldPriceViewModel(prefsManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class ProductViewModelFactory(
    private val prefsManager: SharedPreferencesManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(prefsManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


