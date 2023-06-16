package com.example.physicsengine_clone

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var menuList: ArrayList<String>
    lateinit var menuListView: ListView
    var adapter: ArrayAdapter<String>? = null

    // Arrays holding titles, subtitles, and image resource IDs for list view items
    var maintitle = arrayOf(
        "Accelerometer", "Gyroscope",
        "Magnetometer", "Barometer",
        "GPS"
    )

    var subtitle = arrayOf(
        "Raw 3 Axis Acceleration", "Raw 3 Axis Rotation",
        "Raw 3 Axis Magnetic Fields", "Raw Pressure and Relative Altitude",
        "Lat/Long and other location data"
    )

    var imgid = arrayOf<Int>(
        R.drawable.accelerometer_icon, R.drawable.gyroscope_icon,
        R.drawable.magnetometer_icon, R.drawable.barometer_icon,
        R.drawable.gps_icon
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Physics"

        // Initializing the menuListView by finding the ListView in the layout
        menuListView = findViewById(R.id.menuList)

        // Creating a custom adapter instance and setting it as the adapter for the menuListView
        val adapter = MyListAdapter(this, maintitle, subtitle, imgid)
        menuListView.adapter = adapter

        // Setting an item click listener for the menuListView
        menuListView.setOnItemClickListener { parent, view, position, id ->
            // Creating an intent to start the ExperimentActivity class
            startActivity(Intent(this, ExperimentActivity::class.java).apply {
                // Putting an extra value in the intent based on the clicked item position
                when (position) {
                    0 -> putExtra("expType", ExperimentType.eAccelerometer)
                    1 -> putExtra("expType", ExperimentType.eGyroscope)
                    2 -> putExtra("expType", ExperimentType.eMagnetometer)
                    3 -> putExtra("expType", ExperimentType.eBarometer)
                    4 -> putExtra("expType", ExperimentType.eGPS)
                }
            })
        }
    }
}
