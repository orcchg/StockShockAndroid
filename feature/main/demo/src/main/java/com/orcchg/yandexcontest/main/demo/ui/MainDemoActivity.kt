package com.orcchg.yandexcontest.main.demo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.orcchg.yandexcontest.main.demo.R
import com.orcchg.yandexcontest.main.demo.ui.view.SectionsPagerAdapter

class MainDemoActivity : AppCompatActivity(R.layout.main_activity_demo) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Stocks"
                else -> "Favourite"
            }
        }.attach()
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
//                tab.setCustomView(R.layout.main_tab_title_layout_selected)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
//                tab.setCustomView(R.layout.main_tab_title_layout)
            }

            override fun onTabReselected(tab: TabLayout.Tab) = Unit
        })
    }
}