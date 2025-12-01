package com.ringsize.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ringsize.app.data.model.MeasurementType

@Entity(tableName = "measurements")
data class MeasurementEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long? = null,
    val name: String,
    val type: MeasurementType,
    val diameterMm: Double?,
    val circumferenceMm: Double?,
    val sizeEu: Double?,
    val sizeUs: Double?,
    val createdAt: Long = System.currentTimeMillis(),
    val lastSyncedAt: Long? = null,
    val isSynced: Boolean = false
)







