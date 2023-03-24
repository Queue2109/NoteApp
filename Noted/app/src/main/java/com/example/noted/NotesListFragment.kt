package com.example.noted

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class NotesListFragment : Fragment() {


    private var recyclerView: RecyclerView? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: NotesListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notes_list, container, false)
        // find list where notes will be displayed
        recyclerView = view.findViewById(R.id.notesList)
        // define layout manager
        layoutManager = LinearLayoutManager(view.context)
        // set it to recycler view
        recyclerView?.layoutManager = layoutManager
        // define adapter
        adapter = NotesListAdapter(view.context)

        // pass it to recyclerView.adapter
        recyclerView?.adapter = adapter

        // implement fab button click listener and go to EditNoteFragment
        val fabButton = view.findViewById<FloatingActionButton>(R.id.fabButton)
        fabButton.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.main_activity, EditNoteFragment())?.addToBackStack(null)?.commit()
        }

        return view
    }

}