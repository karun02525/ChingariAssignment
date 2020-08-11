package com.chingari.network

import com.chingari.model.CurrentWeatherModel
import com.chingari.model.ListModelWeather
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/data/2.5/weather")
    fun getCurrentWeatherAsync(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") key: String,
        @Query("units") units: String
    ): Deferred<Response<CurrentWeatherModel>>



    @GET("/data/2.5/forecast")
    fun getForecastWeatherAsync(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") key: String,
        @Query("units") units: String
    ): Deferred<Response<ListModelWeather>>



}