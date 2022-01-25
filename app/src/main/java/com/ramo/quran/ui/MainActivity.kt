package com.ramo.quran.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.ramo.quran.R
import com.ramo.quran.core.SimpleBaseActivity
import com.ramo.quran.databinding.ActivityMainBinding
import com.ramo.quran.ext.AppSharedPref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : SimpleBaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        withVB {
            setSupportActionBar(toolbar)
        }

        initNavigationComponent()
        setKeepScreenOn()
    }

    private fun setKeepScreenOn() {
        val isKeepScreenOn = AppSharedPref(this).isKeepScreenOn()
        if (isKeepScreenOn) window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private fun initNavigationComponent() {
        val navController = findNavController(R.id.navHostFragment)
        findViewById<NavigationView>(R.id.nav_view)
            .setupWithNavController(navController)
        withVB {
            // toolbar
            findViewById<Toolbar>(R.id.toolbar).setupWithNavController(navController, drawerLayout)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        withVB {
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
                drawerLayout.close()
            else
                super.onBackPressed()
        }
    }

}