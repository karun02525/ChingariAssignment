package com.chingari.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chingari.db.entity.WeatherModel
import com.chingari.repository.CurrentWeatherRepository

class CurrentWeatherViewModel(private val repository: CurrentWeatherRepository) : ViewModel() {

    fun loadCurrentWeatherApi(lat: Double,lon: Double) = repository.loadCurrentWeatherApi(lat,lon)
    fun getWeatherModel(): MutableLiveData<WeatherModel> = repository.getWeatherModel()
    fun getErrorMessage(): MutableLiveData<String> =  repository.getErrorMessage()

    override fun onCleared() {
        repository.onCleared()
        super.onCleared()
    }
}
