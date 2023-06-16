package com.example.physicsengine_clone


import androidx.fragment.app.FragmentActivity

interface Experiment {
    fun setupExperiment(activity: FragmentActivity)
    fun addEntries(values: FloatArray)
    fun registerListener()
    fun unregisterListener()
}