package com.ringsize.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ringsize.app.data.local.dao.MeasurementDao
import com.ringsize.app.data.local.dao.SettingsDao
import com.ringsize.app.data.local.entity.MeasurementEntity
import com.ringsize.app.data.local.entity.SettingsEntity

@Database(
    entities = [MeasurementEntity::class, SettingsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RingSizerDatabase : RoomDatabase() {
    abstract fun measurementDao(): MeasurementDao
    abstract fun settingsDao(): SettingsDao
    
    companion object {
        const val DATABASE_NAME = "ring_sizer_db"
    }
}







