package com.example.noted

import NotedDataClass
import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.gson.Gson


class EditNoteFragment : Fragment() {
    var setDate:TextView? = null
    private var linearLayout:LinearLayout? = null
    private var arrayOfActions: ArrayList<ActionClass> = ArrayList()
    private var titleField:EditText? = null
    private var dateField:TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_note, container, false)
        linearLayout  = view.findViewById(R.id.linearLayout)
        titleField = view.findViewById(R.id.newTitle)
        dateField = view.findViewById(R.id.datePicker)

        Log.d("bundle", savedInstanceState.toString())

        val bundle: Bundle? = arguments
        if(bundle != null) {
            setData(bundle)
            Log.d("bundle", bundle.toString())
        }
        setDate(view)
        addActions(view)

        // when the user clicks on save button, we pass all the arguments via sharedPreferences as one object - NotedDataClass
        val saveButton: Button = view.findViewById(R.id.saveNote)
        val sharedPref = view.context.getSharedPreferences("notes", Context.MODE_PRIVATE)
        var editor = sharedPref.edit()

        saveButton.setOnClickListener{

            // build an arraylist with actions
            addActionsToArrayList()

            // get title
            val titleText = titleField?.text.toString()

            // get date
            val dateText = dateField?.text.toString()
            if(titleText.isEmpty() || dateText.isEmpty()) {
                Toast.makeText(context, "Please make an effort to insert at least the title and date.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            //title, date and arraylist of actions
            val note = NotedDataClass(titleText, dateText, arrayOfActions)
            val json = Gson().toJson(note)

            editor.putString(dateText, json)
            editor.apply()

            // change fragment
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.main_activity, NotesListFragment())?.commit()
        }
        return view
    }

    private fun setData(data: Bundle) {
        if(data != null) {
            val title: String? = data.getString("title")
            val date: String? = data.getString("date")
            titleField?.setText(title)
            dateField?.text = date
            val actionsString = data.getParcelableArrayList<ActionClass>("actions")
            if(actionsString != null) {
                for (action in actionsString) {
                    var editText = EditText(context)
                    editText.layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    editText.setText(action.description)

                    // set click listener to delete an action
                    editText.setOnLongClickListener{
                        linearLayout?.removeView(editText)
                        true
                    }
                    linearLayout?.addView(editText)
                }
            }
        }
    }

    private fun addActionsToArrayList() {
        for (i in 0 until linearLayout!!.childCount) {
            val editText  = linearLayout?.getChildAt(i) as EditText
            val actionClass = ActionClass(false, editText.text.toString())
            arrayOfActions.add(actionClass)
        }
    }


    private fun addActions(view: View) {
        val addActionView:TextView = view.findViewById(R.id.addAction)
        addActionView.setOnClickListener {
            linearLayout?.addView(addEditText())
        }
    }

    private fun addEditText() : EditText {
        val editText = EditText(view?.context)
        editText.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        editText.hint = "Change this"

        // set click listener to delete an action
        editText.setOnLongClickListener{
            linearLayout?.removeView(editText)
            true
        }
        return editText
    }

    // set date as content on textView
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
        var daytext: String
        when(day % 10) {
            1 -> if(day % 100 == 11) daytext = "" + day + "th" else daytext = "" + day + "st"
            2 -> if(day % 100 == 12) daytext = "" + day + "th" else daytext = "" + day + "nd"
            3 -> if(day % 100 == 13) daytext = "" + day + "th" else daytext = "" + day + "rd"
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
