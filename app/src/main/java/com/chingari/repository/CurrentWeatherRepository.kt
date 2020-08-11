package com.chingari.repository

import androidx.lifecycle.MutableLiveData
import com.chingari.App
import com.chingari.R
import com.chingari.db.dao.WeatherDao
import com.chingari.db.database.DatabaseCache
import com.chingari.model.CurrentWeatherModel
import com.chingari.db.entity.WeatherModel
import com.chingari.network.ApiStatus.isCheckAPIStatus
import com.chingari.network.Const.api_key
import com.chingari.network.Const.unit
import com.chingari.network.RestClient
import com.chingari.utils.dateStyle
import com.chingari.utils.dayFormat
import com.chingari.utils.finalDayFormatStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CurrentWeatherRepository(private val restClient: RestClient) {

    private val data = MutableLiveData<WeatherModel>()
    private val errorMess = MutableLiveData<String>()
    private var job: Job? = null
    var mDao: WeatherDao = DatabaseCache.getInstance().currentDao()


    fun loadCurrentWeatherApi(lat: Double, lon: Double) {
        job = CoroutineScope(Dispatchers.IO).launch {
            try {
                restClient.webServices().getCurrentWeatherAsync(lat, lon, api_key, unit).await().let {
                    if (it.isSuccessful) {
                        val result = it.body()
                        result?.let {
                            mDao.deleteAllData()
                            mDao.saveAndUpdate(dataParse(result))
                            data.postValue(dataParse(result))
                        }

                    } else {
                         data.postValue(mDao.getAllData())
                        errorMess.postValue(isCheckAPIStatus(it.code(), it.errorBody()))
                    }
                }
            } catch (e: Exception) {
                 data.postValue(mDao.getAllData())
                e.printStackTrace()
                errorMess.postValue(App.appContext?.getString(R.string.no_internet_available))
            }
        }
    }

    private fun dataParse(it: CurrentWeatherModel) : WeatherModel {
       return WeatherModel(
           it.id!!,
           it.name + "," + it.sys?.country,
           it.dt?.finalDayFormatStyle()!!,
           it.dt?.dateStyle()!!,
           it.weather?.get(0)?.description!!,
           it.main?.temp.toString() + "°C",
           "Min temp: " + it.main?.tempMin + "°C",
           "Max temp: " + it.main?.tempMax + "°C",
           it.sys?.sunrise?.dayFormat()!!,
           it.sys?.sunset?.dayFormat()!!,
           it.wind?.speed!!,
           it.main?.pressure!!,
           it.main?.humidity!!,
           it.main?.seaLevel?:"0"
       )
    }


    fun getWeatherModel(): MutableLiveData<WeatherModel> = data
    fun getErrorMessage(): MutableLiveData<String> = errorMess

    fun onCleared() {
        job?.cancel()
    }
}

