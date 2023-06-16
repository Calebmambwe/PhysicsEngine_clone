package com.example.physicsengine_clone

import android.hardware.Sensor
import android.hardware.SensorManager
import com.example.physicsengine_clone.databinding.FragmentGyroscopeBinding

// GyroscopeFragment extends ThreeAxisBaseExperimentFragment and implements SensorExperiment
class GyroscopeFragment : ThreeAxisBaseExperimentFragment<FragmentGyroscopeBinding>(FragmentGyroscopeBinding::inflate),
    SensorExperiment {

    // Override sensorTag property to specify the sensor tag as "Gyroscope"
    override val sensorTag: String
        get() = "Gyroscope"

    // Override sensorType property to specify the sensor type as Sensor.TYPE_GYROSCOPE
    override val sensorType: Int
        get() = Sensor.TYPE_GYROSCOPE

    // Declare mSensorManager and mSensor variables as nullable SensorManager and Sensor types respectively
    override var mSensorManager: SensorManager? = null
    override var mSensor: Sensor? = null
}
