package com.ringsize.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ringsize.app.data.model.PreferredUnit
import com.ringsize.app.data.model.Language

@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey
    val userId: Long,
    val preferredUnit: PreferredUnit = PreferredUnit.MM,
    val language: Language = Language.FR
)







