package com.example.physicsengine_clone

import android.hardware.Sensor
import android.hardware.SensorManager
import com.example.physicsengine_clone.databinding.FragmentAccelerometerBinding

class AccelerometerFragment : ThreeAxisBaseExperimentFragment<FragmentAccelerometerBinding>(FragmentAccelerometerBinding::inflate),
    SensorExperiment {

    override val sensorTag: String
        get() = "Accelerometer" // Tag for the accelerometer sensor

    override val sensorType: Int
        get() = Sensor.TYPE_ACCELEROMETER // Type of the accelerometer sensor

    override var mSensorManager: SensorManager? = null // SensorManager instance to manage sensors
    override var mSensor: Sensor? = null // Accelerometer sensor instance
}
