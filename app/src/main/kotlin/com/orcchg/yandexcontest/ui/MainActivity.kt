package com.orcchg.yandexcontest.ui

import android.os.Build
import androidx.navigation.findNavController
import com.orcchg.yandexcontest.R
import com.orcchg.yandexcontest.coreui.BaseActivity
import com.orcchg.yandexcontest.main.ui.R as Main

class MainActivity : BaseActivity(R.layout.activity_main) {

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val nav = findNavController(Main.id.main_nav_subhost_fragment)
            val destination = nav.currentDestination
            if (destination == null || destination.id == Main.id.main_stock_pages_fragment) {
                finishAfterTransition()
                return
            }
        }

        super.onBackPressed()
    }
}
