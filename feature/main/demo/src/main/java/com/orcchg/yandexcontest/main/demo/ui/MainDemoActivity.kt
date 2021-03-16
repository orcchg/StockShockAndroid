package com.orcchg.yandexcontest.main.demo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.orcchg.yandexcontest.main.demo.R
import com.orcchg.yandexcontest.main.demo.ui.view.SectionsPagerAdapter

internal class MainDemoActivity : AppCompatActivity(R.layout.main_demo_activity) {

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
    }
}
