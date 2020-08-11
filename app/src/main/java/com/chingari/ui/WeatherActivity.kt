package com.chingari.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.chingari.R
import com.chingari.db.entity.WeatherModel
import com.chingari.mvvm.CurrentWeatherViewModel
import com.chingari.mvvm.LocationViewModel
import com.chingari.utils.showSnack
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResult
import com.google.android.gms.location.LocationSettingsStatusCodes
import kotlinx.android.synthetic.main.activity_weather.*
import org.koin.android.viewmodel.ext.android.viewModel

class WeatherActivity : AppCompatActivity() {

    private val viewModel: CurrentWeatherViewModel by viewModel()
    private val viewModelLocation: LocationViewModel by viewModel()
    private var lat = 0.0
    private var lon = 0.0

    companion object {
        private val TAG = WeatherActivity::class.java.simpleName
        private const val MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        private const val REQUEST_CHECK_SETTINGS = 199
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        setupViewModel()
        viewLoad()
        checkPermission()

    }

    override fun onResume() {
        super.onResume()
        enableGps()
    }

    override fun onPause() {
        super.onPause()
        viewModelLocation.stopLocationUpdates()
    }


    private fun viewLoad() {
        viewModelLocation.initLocation()
        btnRefresh.setOnClickListener {
            enableGps()
            progressHideShow(true)
            loadCurrentWeatherApi()
        }

        btnForecast.setOnClickListener {
            startActivity(
                Intent(this, ForeCastActivity::class.java).putExtra("lat", lat)
                    .putExtra("lon", lon)
            )
        }

    }

    private fun loadCurrentWeatherApi() {
        viewModel.loadCurrentWeatherApi(lat, lon)
    }


    private fun setupViewModel() {
        //For Location viewModel
        viewModelLocation.locationDetails.observe(this, Observer {
            progressHideShow(false)
            lat=it.latitude
            lon=it.longitude
            loadCurrentWeatherApi()
        })


        //For API Callback viewModel
        viewModel.getWeatherModel().observe(this, Observer {
            if (it != null) {
                progressHideShow(false)
                setData(it)

            }
        })

        //For API Callback Error viewModel
        viewModel.getErrorMessage().observe(this, Observer {
            progressHideShow(false)
            showSnack(it)
        })
    }



    @SuppressLint("SetTextI18n")
    private fun setData(it: WeatherModel) {
        tv_address.text = it.address
        tv_day.text = it.day
        tv_updated_at.text = it.updated_at
        tv_status.text = it.status
        tv_temp.text = it.temp
        tv_temp_min.text = it.temp_min
        tv_temp_max.text = it.temp_max
        tv_sunrise.text = it.sunrise
        tv_sunset.text = it.sunset
        tv_wind.text = it.speed+"km/h"
        tv_pressure.text = it.pressure+'%'
        tv_humidity.text = it.humidity+"%"
        tv_seaLevel.text = it.seaLevel
    }


    private fun progressHideShow(flag: Boolean) {
        if (flag) {
            loader.visibility = View.VISIBLE
            mainContainer.visibility = View.GONE
        } else {
            mainContainer.visibility = View.VISIBLE
            loader.visibility = View.GONE
        }
    }



    //for GPS
    private fun enableGps() {
        val googleApiClient = GoogleApiClient.Builder(this).addApi(LocationServices.API).build()
        googleApiClient.connect()
        val builder = LocationSettingsRequest.Builder().addLocationRequest(viewModelLocation.locationRequest)
        builder.setAlwaysShow(true)
        val result: PendingResult<LocationSettingsResult> = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        result.setResultCallback { result ->
            val status: Status = result.status
            when (status.statusCode) {
                LocationSettingsStatusCodes.SUCCESS ->
                    try {
                        viewModelLocation.startLocationUpdates()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
                    try {
                        status.startResolutionForResult(this, REQUEST_CHECK_SETTINGS)
                    } catch (e: IntentSender.SendIntentException) { }
                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                }
            }
        }
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
            return
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Log.i(TAG, "Permission was granted for Location")
                    viewModelLocation.startLocationUpdates()
                } else {
                    Log.i(TAG, "Permission was denied for Location")
                    checkPermission()
                }
                return
            }
        }
    }
}
