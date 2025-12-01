package com.ringsize.app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ringsize.app.data.repository.GoldPriceRepository
import com.ringsize.app.data.remote.RetrofitClient
import com.ringsize.app.data.remote.model.GoldPriceResponse
import com.ringsize.app.util.Result
import com.ringsize.app.util.SharedPreferencesManager
import kotlinx.coroutines.launch

class GoldPriceViewModel(
    private val prefsManager: SharedPreferencesManager
) : ViewModel() {
    
    private val repository = GoldPriceRepository(RetrofitClient.apiService)
    
    private val _goldPrices = MutableLiveData<Result<List<GoldPriceResponse>>>()
    val goldPrices: LiveData<Result<List<GoldPriceResponse>>> = _goldPrices
    
    private val _selectedPeriod = MutableLiveData<String>("month")
    val selectedPeriod: LiveData<String> = _selectedPeriod
    
    fun loadGoldPrices(karat: String? = null, period: String? = null) {
        viewModelScope.launch {
            val token = prefsManager.getAuthToken()
            if (token == null) {
                _goldPrices.value = Result.Error(Exception("Non authentifié"))
                return@launch
            }
            
            _goldPrices.value = Result.Loading
            _goldPrices.value = repository.getGoldPrices(token, karat, period ?: _selectedPeriod.value)
            if (period != null) {
                _selectedPeriod.value = period
            }
        }
    }
    
    fun setPeriod(period: String) {
        _selectedPeriod.value = period
        loadGoldPrices(period = period)
    }
}






