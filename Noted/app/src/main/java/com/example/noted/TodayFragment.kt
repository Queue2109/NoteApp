package com.example.noted

import NotedDataClass
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.gson.Gson

class TodayFragment : Fragment() {

    private var sharedPref: SharedPreferences? = null

    private var keys:List<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_today, container, false)

        sharedPref = view.context.getSharedPreferences("notes", Context.MODE_PRIVATE)
        keys = sharedPref?.all?.keys?.toList()
        if(getDate() != null) {
            setData(getDate()!!, view)
        }

        return view
    }

    private fun setData(data: NotedDataClass, view:View) {
        val title: String = data.title
        val date: String = data.date
        val titleField = view.findViewById<TextView>(R.id.todaysTitle)
        val dateField = view.findViewById<TextView>(R.id.todaysDate)
        val linearLayout = view.findViewById<LinearLayout>(R.id.linearLayout)
        titleField?.text = title
        dateField?.text = date
        val actions = data.actions
        for (action in actions) {
            val checkbox = CheckBox(context)
            checkbox.text = action.description
            linearLayout?.addView(checkbox)
        }
    }

    private fun getDate():NotedDataClass? {
        val key = getTodaysDate()
        val json = sharedPref?.getString(key, null)
        if(json != null) {
            val gson = Gson()
            return gson.fromJson(json, NotedDataClass::class.java)
        }
        return null
      }
}