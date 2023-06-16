package com.example.physicsengine_clone

import androidx.fragment.app.FragmentActivity
import com.example.physicsengine_clone.databinding.FragmentGyroscopeBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest

// Import necessary libraries

class GPSFragment : ThreeAxisBaseExperimentFragment<FragmentGyroscopeBinding>(FragmentGyroscopeBinding::inflate),
    LocationExperiment {

    override val sensorTag: String
        get() = "GPS"
    override var fusedLocationClient: FusedLocationProviderClient? = null
    override var locationRequest: LocationRequest? = null
    override var locationCallback: LocationCallback? = null
    override var fragActivity: FragmentActivity? = null

}
