package com.example.noted

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import org.w3c.dom.Text

class EditNoteFragment : Fragment() {
    var setDate:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_note, container, false)

        setDate(view)
        addActions(view)
        return view
    }

    private fun addActions(view: View) {
        val addActionView:TextView = view.findViewById(R.id.addAction)
        val linearLayout:LinearLayout = view.findViewById(R.id.linearLayout)
        addActionView.setOnClickListener {
            linearLayout.addView(addEditText())
        }
    }

    private fun addEditText() : EditText {
        val editText = EditText(view?.context)
        editText.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        editText.hint = "Change this"
        return editText
    }


    private fun setDate(view: View) {

        setDate = view.findViewById(R.id.datePicker)
        val calendar:Calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { v, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            setLabel(calendar)
        }

        setDate?.setOnClickListener {
            Log.d("here","hello")
            DatePickerDialog(view.context, datePicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun setLabel(calendar: Calendar) {
        var format:String = ""
        val month = setMonth(calendar.get(Calendar.MONTH))
        val day = setDay(calendar.get(Calendar.DAY_OF_MONTH))
        format = month + " " + day + " " + calendar.get(Calendar.YEAR)
        setDate?.setText(format)
    }

    // add two letters after number
    private fun setDay(day:Int) : String {
        var daytext:String = ""
        when(day % 10) {
            1 -> daytext = "" + day + "st"
            2 -> daytext = "" + day + "nd"
            3 -> daytext = "" + day + "rd"
            else -> daytext = "" + day + "th"
        }
        return daytext
    }

    // write months with their full name
    private fun setMonth(month: Int) : String {
        var monthText:String = ""
        when(month) {
            0 -> monthText = "January"
            1 -> monthText = "February"
            2 -> monthText = "March"
            3 -> monthText = "April"
            4 -> monthText = "May"
            5 -> monthText = "June"
            6 -> monthText = "July"
            7 -> monthText = "August"
            8 -> monthText = "September"
            9 -> monthText = "October"
            10 -> monthText = "November"
            11 -> monthText = "December"
        }
        return monthText
    }
}