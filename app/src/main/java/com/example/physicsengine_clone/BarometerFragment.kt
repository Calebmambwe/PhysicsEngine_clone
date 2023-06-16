package com.example.physicsengine_clone

import android.hardware.Sensor
import android.hardware.SensorManager
import  com.example.physicsengine_clone.databinding.FragmentBarometerBinding
import  com.example.physicsengine_clone.databinding.FragmentGyroscopeBinding

class BarometerFragment : SingleValueBaseExperimentFragment<FragmentBarometerBinding>(FragmentBarometerBinding::inflate),
    SensorExperiment {

    override val sensorTag: String
        get() = "Barometer"
    override val sensorType: Int
        get() = Sensor.TYPE_PRESSURE
    override var mSensorManager: SensorManager? = null
    override var mSensor: Sensor? = null
}