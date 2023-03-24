package com.example.noted

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class NotesListAdapter(context: Context): RecyclerView.Adapter<NotesListAdapter.CardViewHolder?>() {

    // get sharedPreferences
    private var sharedPreferences: SharedPreferences? = context.getSharedPreferences("notes", Context.MODE_PRIVATE)
    // get keys from sharedPreferences
    private var keys:List<String>? = sharedPreferences?.all?.keys?.toList()

    private var titleText:String? = null
    private var dateText:String? = null


    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemTitle:TextView? = null
        var itemDate:TextView? = null
        init{

            itemTitle =  itemView.findViewById(R.id.noteTitle)
            itemDate =  itemView.findViewById(R.id.noteDate)
        }
    }

    // get title and text from string
    private fun setTitleAndDate(data: String)  {
        val pattern = "NotedDataClass\\(title=(.*), date=(.*), actions=\\[(.*)\\]\\)".toRegex()
        val matchResult = pattern.find(data)
        Log.d("matchResult", matchResult?.groups?.get(1)?.value.toString())
        if(matchResult != null)  {
            titleText = matchResult.groups[1]?.value
            dateText = matchResult.groups[2]?.value
        }

    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        var notedDataList: String? = sharedPreferences?.getString(keys?.get(position), "")
        setTitleAndDate(notedDataList.toString())
        Log.d("sharedPref", notedDataList.toString())
        holder.itemTitle?.setText(titleText)
        holder.itemDate?.setText(dateText)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CardViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.mini_note,
            viewGroup, false)

        return CardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return keys!!.size
    }
}