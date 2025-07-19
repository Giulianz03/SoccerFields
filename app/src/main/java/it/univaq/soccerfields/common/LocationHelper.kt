package it.univaq.soccerfields.common

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.content.ContextCompat

class LocationHelper(private val context: Context, private val onLocationChanged: (Location) -> Unit) {
    //Recupero dal sistema il Location Service
    private val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?

    private val listener = LocationListener{ location ->
        onLocationChanged(location)
    }

    fun start() {
        //Controllo se sono attivi una serie di provider
        val isGPSEnable = manager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false
        val isNetworkEnable = manager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ?: false

        //Controllo se sono garantiti i permessi
        val isFineGaranted = ContextCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val isCoarseGaranted = ContextCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        if(isGPSEnable && isFineGaranted){
            manager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10f, listener)
        } else if (isNetworkEnable && isCoarseGaranted){
            manager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10f, listener)
        }
    }

    fun stop() {
        manager?.removeUpdates(listener)
    }
}