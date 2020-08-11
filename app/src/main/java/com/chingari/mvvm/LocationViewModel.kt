package com.chingari.mvvm

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.chingari.model.Location
import com.google.android.gms.location.*

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private val TAG = LocationViewModel::class.java.simpleName
    }

    private var fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)
    lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    var locationDetails: MutableLiveData<Location> = MutableLiveData()

    fun initLocation() {
        locationRequest = LocationRequest.create()
        locationRequest.interval = 300000 //every 5mit
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (loc in locationResult.locations) {
                    locationDetails.value = Location(loc.latitude, loc.longitude)
                    Log.i(TAG, "lat: ${loc.latitude}")
                    Log.i(TAG, "long: ${loc.longitude}")
                }
            }
        }
    }

    fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}
