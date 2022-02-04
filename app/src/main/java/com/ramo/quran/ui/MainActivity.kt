package com.ramo.quran.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.annotation.DrawableRes
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.ramo.quran.R
import com.ramo.quran.core.SimpleBaseActivity
import com.ramo.quran.core.ext.gone
import com.ramo.quran.core.ext.visible
import com.ramo.quran.data.shared_pref.AppSharedPref
import com.ramo.quran.databinding.ActivityMainBinding
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

    fun showSurahIndicator() = withVB {
        layoutSurahIndicator.visible()
        bottomLine.visible()
    }

    fun hideSurahIndicator() = withVB {
        layoutSurahIndicator.gone()
        bottomLine.gone()
    }

    private fun setKeepScreenOn() {
        val isKeepScreenOn = AppSharedPref(this).isKeepScreenOn
        if (isKeepScreenOn) window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private fun initNavigationComponent() {
        val navController = findNavController(R.id.navHostFragment)
        withVB {
            navView.setupWithNavController(navController)
            setSupportActionBar(toolbar)
            toolbar.setupWithNavController(navController, drawerLayout)
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.read -> changeToolbarIcon(R.drawable.ic_navigation)
                else -> changeToolbarIcon(R.drawable.ic_back)
            }
        }
    }

    private fun changeToolbarIcon(@DrawableRes iconId: Int) =
        binding.toolbar.setNavigationIcon(iconId)

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