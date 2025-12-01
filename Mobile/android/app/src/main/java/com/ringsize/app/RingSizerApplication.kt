package com.ringsize.app

import android.app.Application
import androidx.room.Room
import com.ringsize.app.data.local.RingSizerDatabase

class RingSizerApplication : Application() {
    val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            RingSizerDatabase::class.java,
            RingSizerDatabase.DATABASE_NAME
        ).build()
    }
}







