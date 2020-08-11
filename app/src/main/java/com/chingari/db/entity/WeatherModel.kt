package com.chingari.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val address: String,
    val day: String,
    val updated_at: String,
    val status: String,
    val temp: String,
    val temp_min: String,
    val temp_max: String,
    val sunrise: String,
    val sunset: String,
    val speed: String,
    val pressure: String,
    val humidity: String,
    val seaLevel: String
    )