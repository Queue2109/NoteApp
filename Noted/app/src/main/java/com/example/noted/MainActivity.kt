package com.example.noted

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        changeFragment(NotesListFragment())
    }

    private fun changeFragment(fragment: Fragment) {
        val fragmentManager:FragmentManager = supportFragmentManager
        val fragmentTransaction:FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_activity, fragment)
        fragmentTransaction.commit()
    }
}