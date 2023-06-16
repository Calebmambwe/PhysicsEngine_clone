package com.example.physicsengine_clone


import android.hardware.Sensor
import android.hardware.SensorManager
import com.example.physicsengine_clone.databinding.FragmentMagnetometerBinding

// Import necessary libraries

// MagnetometerFragment extends ThreeAxisBaseExperimentFragment and implements SensorExperiment
class MagnetometerFragment : ThreeAxisBaseExperimentFragment<FragmentMagnetometerBinding>(FragmentMagnetometerBinding::inflate),
    SensorExperiment {

    override val sensorTag: String
        get() = "Magnetometer"
    override val sensorType: Int
        get() = Sensor.TYPE_MAGNETIC_FIELD
    override var mSensorManager: SensorManager? = null
    override var mSensor: Sensor? = null
}
