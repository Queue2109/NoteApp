package com.example.noted

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import org.w3c.dom.Text

class TodayFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_today, container, false)
        val bundle: Bundle? = arguments
        if(bundle != null) {
            setData(bundle, view)
        }
        return view
    }

    private fun setData(data: Bundle, view:View) {
        if(data != null) {
            val title: String? = data.getString("title")
            val date: String? = data.getString("date")
            val titleField = view.findViewById<TextView>(R.id.todaysTitle)
            val dateField = view.findViewById<TextView>(R.id.todaysDate)
            val linearLayout = view.findViewById<LinearLayout>(R.id.linearLayout)
            titleField?.setText(title)
            dateField?.text = date
            val actionsString = data.getParcelableArrayList<ActionClass>("actions")
            if(actionsString != null) {
                for (action in actionsString) {
                    val checkbox = CheckBox(context)
                    checkbox.text = action.description
                    checkbox.isChecked = action.checked
                    linearLayout?.addView(checkbox)
                }
            }
        }
    }
}