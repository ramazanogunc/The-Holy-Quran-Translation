package com.ramo.quran.ui

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.MobileAds
import com.google.android.material.navigation.NavigationView
import com.ramo.quran.R
import com.ramo.quran.dataAccess.LocalSqliteHelper
import com.ramo.quran.dataAccess.abstr.SqliteResponse
import com.ramo.quran.helper.*
import com.ramo.quran.model.Config
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this) {} // init ads
        setSupportActionBar(toolbar)

        initNavigationComponenet()
    }

    private fun initNavigationComponenet() {
        val navController = findNavController(R.id.navHostFragment)
        findViewById<NavigationView>(R.id.nav_view)
            .setupWithNavController(navController)
        // toolbar
        findViewById<Toolbar>(R.id.toolbar).setupWithNavController(navController,drawerLayout)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun attachBaseContext(newBase: Context?) {
        var lang = "en"
        LocalSqliteHelper(newBase).getAllConfig(object : SqliteResponse<Config> {
            override fun onSuccess(response: Config) {
                lang = if (response.language.id == 1 )"tr" else "en"
            }
            override fun onFail(failMessage: String) {
                TODO("Not yet implemented")
            }
        })
        super.attachBaseContext(LocaleWrapper.wrap(newBase!!,lang))
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.close()
        else
            super.onBackPressed()
    }

}