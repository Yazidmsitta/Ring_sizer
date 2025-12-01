package com.ringsize.app.data.local.dao

import androidx.room.*
import com.ringsize.app.data.local.entity.SettingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    @Query("SELECT * FROM settings WHERE userId = :userId")
    fun getSettings(userId: Long): Flow<SettingsEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: SettingsEntity)
    
    @Update
    suspend fun updateSettings(settings: SettingsEntity)
}







