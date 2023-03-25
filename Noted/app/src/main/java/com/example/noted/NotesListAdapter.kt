package com.example.noted

import NotedDataClass
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class NotesListAdapter(context: Context): RecyclerView.Adapter<NotesListAdapter.CardViewHolder?>() {

    // get sharedPreferences
    private var sharedPreferences: SharedPreferences? = context.getSharedPreferences("notes", Context.MODE_PRIVATE)
    // get keys from sharedPreferences
    private var keys:List<String>? = sharedPreferences?.all?.keys?.toList()

    private var titleText:String? = null
    private var dateText:String? = null

    var bundle = Bundle()

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemTitle:TextView? = null
        var itemDate:TextView? = null
        init{
            itemTitle =  itemView.findViewById(R.id.noteTitle)
            itemDate =  itemView.findViewById(R.id.noteDate)

            // on item click, go to EditNoteFragment but with data already applied
            itemView.setOnClickListener {
                // create bundle
                bundle.putString("title", itemTitle?.text.toString())
                bundle.putString("date", itemDate?.text.toString())
                val actions = returnNotedDataObject(itemDate?.text.toString())?.actions
                bundle.putParcelableArrayList("actions", actions)
                val fragment = EditNoteFragment()
                fragment.arguments = bundle
                val activity = itemView.context as AppCompatActivity
                activity.supportFragmentManager.beginTransaction().replace(R.id.main_activity, fragment).addToBackStack(null).commit()
            }
        }
    }


    private fun returnNotedDataObject(key: String): NotedDataClass? {
        val json = sharedPreferences?.getString(key, null)
        val gson = Gson()
        return gson.fromJson(json, NotedDataClass::class.java)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val notedDataObject = returnNotedDataObject(keys!![position])
        Log.d("notedData", notedDataObject.toString())

        holder.itemTitle?.setText(notedDataObject?.title)
        holder.itemDate?.setText(notedDataObject?.date)
        Log.d("itemDate", holder.itemDate?.text.toString())

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

