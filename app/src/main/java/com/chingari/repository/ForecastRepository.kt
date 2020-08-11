package com.chingari.repository

import androidx.lifecycle.MutableLiveData
import com.chingari.App
import com.chingari.R
import com.chingari.model.ListModel
import com.chingari.network.ApiStatus.isCheckAPIStatus
import com.chingari.network.Const.api_key
import com.chingari.network.Const.unit
import com.chingari.network.RestClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ForecastRepository(private val restClient: RestClient) {

    private val data = MutableLiveData<List<ListModel>>()
    private val errorMess = MutableLiveData<String>()
    private var job: Job? = null

     fun loadForeCastApi(lat: Double,lon: Double) {
        job=CoroutineScope(Dispatchers.IO).launch {
            try {
                restClient.webServices().getForecastWeatherAsync(lat,lon,api_key,unit).await().let {
                    if (it.isSuccessful) {
                        val result = it.body()
                        result?.let {
                            data.postValue(result.list)
                        }

                    } else {
                        errorMess.postValue(isCheckAPIStatus(it.code(), it.errorBody()))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorMess.postValue(App.appContext?.getString(R.string.no_internet_available))
            }
        }
    }



    fun getWeatherListModel(): MutableLiveData<List<ListModel>> = data
    fun getErrorMessage(): MutableLiveData<String> = errorMess

    fun onCleared(){
        job?.cancel()
    }
}

