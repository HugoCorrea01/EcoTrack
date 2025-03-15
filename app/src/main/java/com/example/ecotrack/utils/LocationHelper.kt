package com.example.ecotrack.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

object LocationHelper {
    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(context: Context): Pair<Double, Double>? {
        val locationManager = ContextCompat.getSystemService(context, LocationManager::class.java)
        val provider = LocationManager.GPS_PROVIDER

        // 🔴 Verifica se a permissão foi concedida
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null // Se a permissão não foi concedida, retorna nulo
        }

        return suspendCancellableCoroutine { continuation ->
            val location: Location? = locationManager?.getLastKnownLocation(provider)
            if (location != null) {
                continuation.resume(Pair(location.latitude, location.longitude))
            } else {
                continuation.resume(null)
            }
        }
    }
}
