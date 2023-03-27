package com.example.noted

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    companion object {
        private const val NUM_OF_TABS = 2
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        configureTabLayout()
    }

    private fun configureTabLayout() {
        val viewPager2 = findViewById<ViewPager2>(R.id.viewPager2)
        val tabAdapter = TabPagerAdapter(this, NUM_OF_TABS)
        viewPager2.adapter = tabAdapter
        if(intent.hasExtra("position")) {
            viewPager2.currentItem = intent.getIntExtra("position", 1)
        } else {
            viewPager2.currentItem = 1
        }
    }

}

class TabPagerAdapter(fa: FragmentActivity?, private val tabCounter: Int) : FragmentStateAdapter(fa!!) {
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                NotesListFragment()
            }
            1 -> {
                TodayFragment()
            }
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

    override fun getItemCount(): Int {
        return tabCounter
    }
}