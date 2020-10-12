package com.example.module_tool.utils

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.example.module_tool.base.BaseConstant

class MyLocation private constructor(){
    companion object{
        val instance by lazy { MyLocation() }
        private const val LOCATION_PROVIDERS_CHANGED = "android.location.PROVIDERS_CHANGED"
    }
    private val locationManager= BaseConstant.application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private val receiver=LocationChanged()
    private val locationCallbacks=ArrayList<LocationCallback>()
    private var locationListener=object : LocationListener{

        override fun onProviderEnabled(provider: String) {
            Log.e("111111111",true.toString())
            if (provider==LocationManager.GPS_PROVIDER){
                locationCallbacks.forEach {
                    it.onLocationEnabled(true)
                }
            }
        }

        override fun onProviderDisabled(provider: String) {
            Log.e("111111111",false.toString())
            if (provider==LocationManager.GPS_PROVIDER){
                locationCallbacks.forEach {
                    it.onLocationEnabled(false)
                }
            }
        }

        override fun onLocationChanged(location: Location) {
            location?:return
//            if (location?.hasAltitude()==true) {
//                location.altitude
//            }
            locationCallbacks.forEach {
                it.onLocationResult(location)
            }
            Log.e("111111111",location.altitude.toString())
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            Log.e("111111111",status.toString())
        }


    }

    fun startLocationProvidersChanged(){
        val intentFilter=IntentFilter(LOCATION_PROVIDERS_CHANGED)
        BaseConstant.application.registerReceiver(receiver,intentFilter)
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ContextCompat.checkSelfPermission(BaseConstant.application,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                startLocation()
            }
        }
    }

    fun removeLocationProvidersChanged(){
        BaseConstant.application.unregisterReceiver(receiver)
        stopLocation()
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    private fun startLocation(){
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L,5f, locationListener)
        }
    }

    private fun stopLocation(){
        locationManager.removeUpdates(locationListener)
    }

    private inner class LocationChanged: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                if (ContextCompat.checkSelfPermission(BaseConstant.application,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                    startLocation()
                }
                locationCallbacks.forEach {
                    it.onLocationEnabled(true)
                }
            }else{
                locationCallbacks.forEach {
                    it.onLocationEnabled(false)
                }
                stopLocation()
            }
        }
    }

    fun addCallback(locationCallback: LocationCallback){
        if (!locationCallbacks.contains(locationCallback)) {
            locationCallbacks.add(locationCallback)
        }
    }

    fun removeCallback(locationCallback: LocationCallback){
        locationCallbacks.remove(locationCallback)
    }

    interface LocationCallback{
        fun onLocationEnabled(enabled:Boolean)

        fun onLocationResult(location: Location)
    }
}