package com.example.noted

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson


class NotesListAdapter(context: Context): RecyclerView.Adapter<NotesListAdapter.CardViewHolder?>() {

    // get sharedPreferences
    private var sharedPreferences: SharedPreferences? = context.getSharedPreferences("notes", Context.MODE_PRIVATE)
    // get keys from sharedPreferences
    private var keys:List<String>? = sharedPreferences?.all?.keys?.toList()


    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemTitle:TextView? = null
        var itemDate:TextView? = null
        init{
            itemTitle =  itemView.findViewById(R.id.noteTitle)
            itemDate =  itemView.findViewById(R.id.noteDate)

            // on item click, go to EditNoteFragment but with data already applied
            itemView.setOnClickListener {
                // create bundle
                val intent = Intent(itemView.context, NewNoteActivity::class.java)
                val bundle = Bundle()
                bundle.putString("title", itemTitle?.text.toString())
                bundle.putString("date", itemDate?.text.toString())
                val actions = returnNotedDataObject(itemDate?.text.toString())?.actions
                bundle.putParcelableArrayList("actions", actions)

                intent.putExtras(bundle)
                itemView.context.startActivity(intent)
                //val activity = itemView.context as AppCompatActivity
                //activity.supportFragmentManager.beginTransaction().replace(R.id.fragment_edit_note, fragment).addToBackStack(null).commit()
            }
        }
    }

    fun returnNotedDataObject(key: String): NotedDataClass? {
        val json = sharedPreferences?.getString(key, null)
        val gson = Gson()
        return gson.fromJson(json, NotedDataClass::class.java)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val notedDataObject = returnNotedDataObject(keys!![position])

        holder.itemTitle?.text = notedDataObject?.title
        holder.itemDate?.text = notedDataObject?.date

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

