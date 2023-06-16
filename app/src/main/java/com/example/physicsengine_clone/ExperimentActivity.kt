package com.example.physicsengine_clone

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.physicsengine_clone.R
import com.example.physicsengine_clone.databinding.ActivityExperimentBinding

class ExperimentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExperimentBinding
    private var expFrag: Fragment? = null
    private lateinit var expType: ExperimentType

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_experiment)

        val fragmentManager = supportFragmentManager
        val extras = intent.extras
        if (extras == null) {
            expType = ExperimentType.eUnknown
        } else {
            expType = extras.getSerializable("expType") as ExperimentType
        }

        // Create the appropriate fragment based on the experiment type
        expFrag = when (expType) {
            ExperimentType.eAccelerometer -> AccelerometerFragment()
            ExperimentType.eGyroscope -> GyroscopeFragment()
            ExperimentType.eMagnetometer -> MagnetometerFragment()
            ExperimentType.eBarometer -> BarometerFragment()
            ExperimentType.eGPS -> GPSFragment()
            else -> null
        }

        // Replace the fragment in the container if it exists
        expFrag?.let {
            fragmentManager.commit {
                replace(R.id.fragmentContainerView, it)
                setReorderingAllowed(true)
                addToBackStack("name")
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Enable the back function to the button on press
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    // Create an action bar button
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Handle button activities
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == R.id.action_play) {
            if (expFrag is RenderingExperiment) {
                val renderingExp = expFrag as RenderingExperiment
                if (renderingExp.isRendering) {
                    renderingExp.onStopClicked()
                    item.setIcon(R.drawable.ic_play_button)
                } else {
                    renderingExp.onStartClicked()
                    item.setIcon(R.drawable.ic_pause_button)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
