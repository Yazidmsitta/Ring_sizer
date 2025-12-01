package com.ringsize.app.data.repository

import com.ringsize.app.data.local.dao.MeasurementDao
import com.ringsize.app.data.local.entity.MeasurementEntity
import com.ringsize.app.data.remote.ApiService
import com.ringsize.app.data.remote.model.MeasurementRequest
import com.ringsize.app.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MeasurementRepository(
    private val measurementDao: MeasurementDao,
    private val apiService: ApiService
) {
    fun getAllMeasurements(): Flow<List<MeasurementEntity>> {
        return measurementDao.getAllMeasurements()
    }
    
    suspend fun getMeasurementById(id: Long): MeasurementEntity? {
        return withContext(Dispatchers.IO) {
            measurementDao.getMeasurementById(id)
        }
    }
    
    suspend fun insertMeasurement(measurement: MeasurementEntity): Long {
        return withContext(Dispatchers.IO) {
            measurementDao.insertMeasurement(measurement.copy(isSynced = false))
        }
    }
    
    suspend fun updateMeasurement(measurement: MeasurementEntity) {
        withContext(Dispatchers.IO) {
            measurementDao.updateMeasurement(measurement.copy(isSynced = false))
        }
    }
    
    suspend fun deleteMeasurement(measurement: MeasurementEntity) {
        withContext(Dispatchers.IO) {
            measurementDao.deleteMeasurement(measurement)
        }
    }
    
    suspend fun syncMeasurements(token: String, userId: Long?): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                // Get unsynced measurements
                val unsynced = measurementDao.getUnsyncedMeasurements()
                
                // Sync each unsynced measurement
                for (measurement in unsynced) {
                    val request = MeasurementRequest(
                        name = measurement.name,
                        type = measurement.type.name,
                        diameterMm = measurement.diameterMm,
                        circumferenceMm = measurement.circumferenceMm,
                        sizeEu = measurement.sizeEu,
                        sizeUs = measurement.sizeUs
                    )
                    
                    val response = if (measurement.userId == null) {
                        apiService.createMeasurement("Bearer $token", request)
                    } else {
                        apiService.updateMeasurement("Bearer $token", measurement.id, request)
                    }
                    
                    if (response.isSuccessful && response.body() != null) {
                        val syncedMeasurement = measurement.copy(
                            userId = response.body()!!.userId,
                            isSynced = true,
                            lastSyncedAt = System.currentTimeMillis()
                        )
                        measurementDao.updateMeasurement(syncedMeasurement)
                    }
                }
                
                // Fetch remote measurements
                val remoteResponse = apiService.getMeasurements("Bearer $token")
                if (remoteResponse.isSuccessful && remoteResponse.body() != null) {
                    val remoteMeasurements = remoteResponse.body()!!
                    for (remote in remoteMeasurements) {
                        val entity = MeasurementEntity(
                            id = remote.id,
                            userId = remote.userId,
                            name = remote.name,
                            type = com.ringsize.app.data.model.MeasurementType.valueOf(remote.type),
                            diameterMm = remote.diameterMm,
                            circumferenceMm = remote.circumferenceMm,
                            sizeEu = remote.sizeEu,
                            sizeUs = remote.sizeUs,
                            createdAt = System.currentTimeMillis(), // Parse from remote.createdAt
                            lastSyncedAt = System.currentTimeMillis(),
                            isSynced = true
                        )
                        measurementDao.insertMeasurement(entity)
                    }
                }
                
                Result.Success(Unit)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}







