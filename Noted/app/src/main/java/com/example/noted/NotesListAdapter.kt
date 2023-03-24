package com.example.noted

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class NotesListAdapter(): RecyclerView.Adapter<NotesListAdapter.CardViewHolder?>() {

    init {

    }
    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init{

        }
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CardViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.mini_note,
            viewGroup, false)

        return CardViewHolder(view)
    }


    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}