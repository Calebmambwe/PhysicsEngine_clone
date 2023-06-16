package com.example.physicsengine_clone

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.physicsengine_clone.R


class MyListAdapter     // Store the references to the activity, data arrays, and image IDs
    (
    private val context: Activity,
    private val maintitle: Array<String>,
    private val subtitle: Array<String>,
    private val imgid: Array<Int>?
) :
    ArrayAdapter<String?>(context, R.layout.sample_menu_item_list_view, maintitle) {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView: View = inflater.inflate(R.layout.sample_menu_item_list_view, null, true)

        // Get references to the views within the list item layout
        val titleText = rowView.findViewById<View>(R.id.title) as TextView
        val imageView = rowView.findViewById<View>(R.id.icon) as ImageView
        val subtitleText = rowView.findViewById<View>(R.id.subtitle) as TextView

        // Set the data to the views
        titleText.text = maintitle[position]
        titleText.setTextColor(Color.WHITE)
        subtitleText.setTextColor(ContextCompat.getColor(context, R.color.lightgray))

        // Set the image resource if available
        if (imgid != null) {
            imageView.setImageResource(imgid[position])
        }
        subtitleText.text = subtitle[position]
        return rowView
    }
}
