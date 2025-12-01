package com.ringsize.app.data.local.dao

import androidx.room.*
import com.ringsize.app.data.local.entity.MeasurementEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MeasurementDao {
    @Query("SELECT * FROM measurements ORDER BY createdAt DESC")
    fun getAllMeasurements(): Flow<List<MeasurementEntity>>
    
    @Query("SELECT * FROM measurements WHERE id = :id")
    suspend fun getMeasurementById(id: Long): MeasurementEntity?
    
    @Query("SELECT * FROM measurements WHERE isSynced = 0")
    suspend fun getUnsyncedMeasurements(): List<MeasurementEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeasurement(measurement: MeasurementEntity): Long
    
    @Update
    suspend fun updateMeasurement(measurement: MeasurementEntity)
    
    @Delete
    suspend fun deleteMeasurement(measurement: MeasurementEntity)
    
    @Query("DELETE FROM measurements WHERE id = :id")
    suspend fun deleteMeasurementById(id: Long)
}







