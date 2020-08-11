package com.chingari.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.chingari.db.entity.WeatherModel


@Dao
interface WeatherDao {

    @Insert
    fun saveAndUpdate(data: WeatherModel)

    @Query("SELECT * FROM WeatherModel")
    fun getAllData(): WeatherModel

    @Query("DELETE FROM WeatherModel")
    fun deleteAllData()
}