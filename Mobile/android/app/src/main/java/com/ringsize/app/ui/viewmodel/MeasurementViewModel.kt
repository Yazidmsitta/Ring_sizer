package com.ringsize.app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.asLiveData
import com.ringsize.app.data.local.entity.MeasurementEntity
import com.ringsize.app.data.model.MeasurementType
import com.ringsize.app.data.repository.MeasurementRepository
import com.ringsize.app.util.MeasurementCalculator
import com.ringsize.app.util.Result
import com.ringsize.app.util.SharedPreferencesManager
import kotlinx.coroutines.launch

class MeasurementViewModel(
    private val measurementRepository: MeasurementRepository,
    private val prefsManager: SharedPreferencesManager
) : ViewModel() {
    
    val measurements: LiveData<List<MeasurementEntity>> = 
        measurementRepository.getAllMeasurements().asLiveData()
    
    private val _syncResult = MutableLiveData<Result<Unit>>()
    val syncResult: LiveData<Result<Unit>> = _syncResult
    
    private val _currentMeasurement = MutableLiveData<MeasurementCalculator.MeasurementResult?>()
    val currentMeasurement: LiveData<MeasurementCalculator.MeasurementResult?> = _currentMeasurement
    
    fun calculateFromDiameter(diameterMm: Double) {
        val result = MeasurementCalculator.calculateFromDiameter(diameterMm)
        _currentMeasurement.value = result
    }
    
    fun calculateFromCircumference(circumferenceMm: Double) {
        val result = MeasurementCalculator.calculateFromCircumference(circumferenceMm)
        _currentMeasurement.value = result
    }
    
    fun saveMeasurement(
        name: String,
        type: MeasurementType,
        diameterMm: Double?,
        circumferenceMm: Double?
    ) {
        viewModelScope.launch {
            val userId = prefsManager.getUserId()
            val result = if (diameterMm != null) {
                MeasurementCalculator.calculateFromDiameter(diameterMm)
            } else if (circumferenceMm != null) {
                MeasurementCalculator.calculateFromCircumference(circumferenceMm)
            } else {
                return@launch
            }
            
            val measurement = MeasurementEntity(
                userId = userId,
                name = name,
                type = type,
                diameterMm = result.diameterMm,
                circumferenceMm = result.circumferenceMm,
                sizeEu = result.sizeEu,
                sizeUs = result.sizeUs
            )
            
            measurementRepository.insertMeasurement(measurement)
            _currentMeasurement.value = null
        }
    }
    
    fun updateMeasurement(measurement: MeasurementEntity) {
        viewModelScope.launch {
            measurementRepository.updateMeasurement(measurement)
        }
    }
    
    fun deleteMeasurement(measurement: MeasurementEntity) {
        viewModelScope.launch {
            measurementRepository.deleteMeasurement(measurement)
        }
    }
    
    fun syncMeasurements() {
        viewModelScope.launch {
            val token = prefsManager.getAuthToken()
            val userId = prefsManager.getUserId()
            if (token != null && userId != null) {
                _syncResult.value = Result.Loading
                _syncResult.value = measurementRepository.syncMeasurements(token, userId)
            }
        }
    }
}







