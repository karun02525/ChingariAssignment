package com.chingari.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chingari.model.ListModel
import com.chingari.repository.ForecastRepository

class ForeCastViewModel(private val repository: ForecastRepository) : ViewModel() {

    fun loadForeCastApi(lat: Double,lon: Double) = repository.loadForeCastApi(lat,lon)
    fun getWeatherModel(): MutableLiveData<List<ListModel>> = repository.getWeatherListModel()
    fun getErrorMessage(): MutableLiveData<String> =  repository.getErrorMessage()

    override fun onCleared() {
        repository.onCleared()
        super.onCleared()
    }
}
