package com.example.emparejapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.emparejapp.data.local.PlayerDao
import com.example.emparejapp.data.models.PlayerEntity

@Database(entities = [PlayerEntity::class], version = 1)
abstract class AppDatabase :RoomDatabase(){
    abstract fun PlayerDao(): PlayerDao
    companion object{
        private var INSTANCE : AppDatabase ?= null

        fun getDb(context: Context):AppDatabase{
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "pairs_db"
            ).build()
            return INSTANCE!!
        }
    }
}