package com.ringsize.app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ringsize.app.data.repository.ProductRepository
import com.ringsize.app.data.remote.RetrofitClient
import com.ringsize.app.data.remote.model.ProductResponse
import com.ringsize.app.util.Result
import com.ringsize.app.util.SharedPreferencesManager
import kotlinx.coroutines.launch

class ProductViewModel(
    private val prefsManager: SharedPreferencesManager
) : ViewModel() {
    
    private val repository = ProductRepository(RetrofitClient.apiService)
    
    private val _products = MutableLiveData<Result<List<ProductResponse>>>()
    val products: LiveData<Result<List<ProductResponse>>> = _products
    
    private val _selectedProduct = MutableLiveData<ProductResponse?>()
    val selectedProduct: LiveData<ProductResponse?> = _selectedProduct
    
    fun loadProducts(
        category: String? = null,
        karat: String? = null,
        vendorId: Long? = null,
        minPrice: Double? = null,
        maxPrice: Double? = null
    ) {
        viewModelScope.launch {
            val token = prefsManager.getAuthToken()
            if (token == null) {
                _products.value = Result.Error(Exception("Non authentifié"))
                return@launch
            }
            
            _products.value = Result.Loading
            _products.value = repository.getProducts(token, category, karat, vendorId, minPrice, maxPrice)
        }
    }
    
    fun loadProduct(id: Long) {
        viewModelScope.launch {
            val token = prefsManager.getAuthToken()
            if (token == null) {
                _selectedProduct.value = null
                return@launch
            }
            
            when (val result = repository.getProduct(token, id)) {
                is Result.Success -> {
                    _selectedProduct.value = result.data
                }
                else -> {
                    _selectedProduct.value = null
                }
            }
        }
    }
}






