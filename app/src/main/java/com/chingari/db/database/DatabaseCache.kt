package com.chingari.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.chingari.App
import com.chingari.db.dao.WeatherDao
import com.chingari.db.entity.WeatherModel
import com.chingari.network.Const.db_name

@Database(entities = [WeatherModel::class], version = 1)
abstract class DatabaseCache : RoomDatabase() {
    abstract fun currentDao(): WeatherDao

    companion object {
        @Volatile
        private var INSTANCE: DatabaseCache? = null
        fun getInstance(): DatabaseCache =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(App.appContext!!).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            databaseBuilder(context.applicationContext, DatabaseCache::class.java, db_name).build()
    }
}