package com.example.noted

import NotedDataClass
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

class NewNoteActivity : AppCompatActivity() {
    private var setDate:TextView? = null
    private var linearLayout:LinearLayout? = null
    private var arrayOfActions: ArrayList<ActionClass> = ArrayList()
    private var titleField:EditText? = null
    private var dateField:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note_activity)
        linearLayout  = this.findViewById(R.id.linearLayout)
        titleField = this.findViewById(R.id.newTitle)
        dateField = this.findViewById(R.id.datePicker)



        val bundle: Bundle? = intent.extras
        if(bundle != null) {
            setData(bundle)
        } else {
            dateField?.text = getTodaysDate()
        }
        setDate()
        addActions()

        // when the user clicks on save button, we pass all the arguments via sharedPreferences as one object - NotedDataClass
        val saveButton: Button = this.findViewById(R.id.saveNote)
        val sharedPref = this.getSharedPreferences("notes", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        saveButton.setOnClickListener{

            // build an arraylist with actions
            addActionsToArrayList()

            // get title
            val titleText = titleField?.text.toString()

            // get date
            val dateText = dateField?.text.toString()
            if(titleText.isEmpty() || dateText.isEmpty()) {
                Toast.makeText(this, "Title and date are required.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(duplicateDate(dateText)) {
                Toast.makeText(this, "TO-DO list with this date already exists", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            //title, date and arraylist of actions
            val note = NotedDataClass(titleText, dateText, arrayOfActions)
            val json = Gson().toJson(note)

            editor.putString(dateText, json)
            editor.apply()

            // change fragment
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("position", 0)
            startActivityForResult(intent, 1)

        }
    }
    private fun setData(data: Bundle) {
        val title: String? = data.getString("title")
        val date: String? = data.getString("date")
        titleField?.setText(title)
        dateField?.text = date
        val actionsString = data.getParcelableArrayList<ActionClass>("actions")
        if(actionsString != null) {
            for (action in actionsString) {
                val editText = EditText(this)
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

    private fun addActionsToArrayList() {
        for (i in 0 until linearLayout!!.childCount) {
            val editText  = linearLayout?.getChildAt(i) as EditText
            val actionClass = ActionClass(false, editText.text.toString())
            arrayOfActions.add(actionClass)
        }
    }


    private fun addActions() {
        val addActionView:TextView = this.findViewById(R.id.addAction)
        addActionView.setOnClickListener {
            linearLayout?.addView(addEditText())
        }
    }

    private fun addEditText() : EditText {
        val editText = EditText(this)
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
    private fun setDate() {

        setDate = this.findViewById(R.id.datePicker)
        val calendar:Calendar = Calendar.getInstance()
        var format = ""
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            format = setLabel(calendar)
            setDate?.text = format
        }


        setDate?.setOnClickListener {
            Log.d("here","hello")
            DatePickerDialog(this, datePicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        Log.d("formatDate", format)
    }

    // checks if you try to save to do list with the same date as one that is already saved
    private fun duplicateDate(date: String) : Boolean {
        val sharedPreferences = this.getSharedPreferences("notes", Context.MODE_PRIVATE)
        val keys = sharedPreferences?.all?.keys?.toList()
        for (key in keys!!) {
            if(key.equals(date)) {
                return true
            }
        }
        return false

    }

}

