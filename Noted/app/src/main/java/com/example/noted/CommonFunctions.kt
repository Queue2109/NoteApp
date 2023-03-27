package com.example.noted

import NotedDataClass
import android.content.SharedPreferences
import android.icu.util.Calendar
import com.google.gson.Gson

// add two letters after number
fun setDay(day:Int) : String {
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
fun setMonth(month: Int) : String {
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

// return string of a date
fun setLabel(calendar: Calendar):String {
    var format:String = ""
    val month = setMonth(calendar.get(Calendar.MONTH))
    val day = setDay(calendar.get(Calendar.DAY_OF_MONTH))
    format = month + " " + day + " " + calendar.get(Calendar.YEAR)
    return format
}

// return string of current date
fun getTodaysDate():String {
    val calendar = Calendar.getInstance()
    val year = calendar.get(java.util.Calendar.YEAR)
    val month = calendar.get(java.util.Calendar.MONTH)
    val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)
    calendar.set(year, month, day)
    return setLabel(calendar)
}
