package com.example.physicsengine_clone

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.PhilJay.MPAndroidChart.tree.master
import androidx.viewbinding.ViewBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.text.DecimalFormat
import java.util.*

abstract class BaseExperimentFragment<V : ViewBinding>(
    private val bindingInflater: (inflate: LayoutInflater) -> V
) : Fragment(), Experiment, RenderingExperiment {
    private lateinit var _binding: V
    val binding get() = _binding

    internal abstract val sensorTag: String

    private var thread: Thread? = null
    private var plotData = true
    override var isRendering = false
    private var startTime: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        if (this.activity != null) {
            setupExperiment(this.activity!!)
        }
        return binding.root
    }

    // Initializes the LineChart with provided description and settings
    fun initChart(c: LineChart, desc: String) {
        c.description.isEnabled = true
        c.description.text = desc
        c.description.textColor = Color.WHITE

        c.setTouchEnabled(true) // Enable touch gestures
        c.isDragEnabled = true // Enable scaling and dragging
        c.setScaleEnabled(true)
        c.setDrawGridBackground(false)
        c.setPinchZoom(true) // If disabled, scaling can be done on x- and y-axis separately

        val data = LineData()
        data.setValueTextColor(Color.WHITE)
        c.data = data // Add empty data

        val l: Legend = c.legend // Get the legend
        l.form = Legend.LegendForm.LINE // Modify the legend
        l.textColor = Color.WHITE

        val xl: XAxis = c.xAxis
        xl.textColor = Color.WHITE
        xl.setDrawGridLines(true)
        xl.setAvoidFirstLastClipping(true)
        xl.isEnabled = true
        xl.mDecimals = 0
        xl.valueFormatter = MyAxisValueFormatter()
        xl.setPosition(XAxis.XAxisPosition.BOTTOM)

        val leftAxis: YAxis = c.axisLeft
        leftAxis.setTextColor(Color.WHITE)
        leftAxis.setDrawGridLines(true)
        leftAxis.valueFormatter = MyAxisValueFormatter()

        c.xAxis.setDrawGridLines(true)
        c.xAxis.setDrawAxisLine(true)
        c.axisLeft.setDrawGridLines(true)
        c.axisLeft.setDrawAxisLine(true)
        c.setDrawBorders(false)
        c.axisRight.isEnabled = false
    }
    // Custom value formatter for chart axes
    class MyAxisValueFormatter : ValueFormatter() {
        private val mFormat: DecimalFormat

        init {
            mFormat = DecimalFormat("###,###,##0.0") // Use one decimal
        }

        override fun getFormattedValue(value: Float): String {
            return mFormat.format(value)
        }
    }

    internal fun addEntry(yVal: Float, data: LineData?, color: Int): Boolean {
        if (data != null) {
            var set: ILineDataSet? = data.getDataSetByIndex(0)
            // set.addEntry(...); // Can be called as well
            if (set == null) {
                set = createSet(ContextCompat.getColor(requireContext(), color))
                data.addDataSet(set)
            }

            // Add a new entry to the LineData
            data.addEntry(
                Entry(
                    (Calendar.getInstance().timeInMillis - startTime).div(1000f),
                    yVal
                ), 0
            )
            data.notifyDataChanged()
        }
        return data != null
    }
    internal fun createSet(color: Int): LineDataSet {
        val set = LineDataSet(null, "Dynamic Data")
        set.axisDependency = YAxis.AxisDependency.LEFT
        set.lineWidth = 1f
        set.color = color
        set.isHighlightEnabled = false
        set.setDrawValues(false)
        set.setDrawCircles(false)
        set.mode = LineDataSet.Mode.CUBIC_BEZIER
        set.cubicIntensity = 0.2f
        return set
    }

    internal fun feedMultiple() {
        startTime = Calendar.getInstance().timeInMillis
        if (thread != null) {
            thread!!.interrupt()
        }
        thread = Thread {
            while (true) {
                plotData = true
                try {
                    Thread.sleep(10)
                } catch (e: InterruptedException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }
            }
        }
        thread!!.start()
    }

    internal fun stopFeeding(){
        isRendering = false
        thread?.interrupt()
    }

    override fun onPause() {
        super.onPause()
        thread?.interrupt()
        unregisterListener()
    }

    override fun onStopClicked(){
        unregisterListener()
        stopFeeding()
    }

    override fun onResume() {
        super.onResume()
        registerListener()
    }

    override fun onDestroy() {
        unregisterListener()
        thread?.interrupt()
        super.onDestroy()
    }

}