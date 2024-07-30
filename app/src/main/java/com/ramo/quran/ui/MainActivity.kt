package com.ramo.quran.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.activity.addCallback
import androidx.annotation.DrawableRes
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.ramo.core.ViewBindingActivity
import com.ramo.quran.R
import com.ramo.quran.data.shared_pref.AppSharedPref
import com.ramo.quran.databinding.ActivityMainBinding
import com.ramo.quran.utils.FirebaseAnalyticsUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ViewBindingActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseAnalyticsUtil.screenEvent(this.javaClass)
        setSupportActionBar(binding.toolbar)
        initNavigationComponent()
        setKeepScreenOn()
        initBackPress()
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

    private fun initBackPress(){
        onBackPressedDispatcher.addCallback {
            withVB {
                if (drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.close()
                else
                    onBackPressedDispatcher.onBackPressed()
            }
        }
    }

}