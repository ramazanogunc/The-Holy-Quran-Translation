package com.ramo.quran.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.ramo.quran.R
import com.ramo.quran.dataAccess.LocalSqliteHelper
import com.ramo.quran.dataAccess.abstr.SqliteResponse
import com.ramo.quran.helper.*
import com.ramo.quran.model.Config
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (isLangLoaded(this)){
            setLangIsLoaded(this,false)
        }
        else{
            setLocale()
            setLangIsLoaded(this,true)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

    private fun setLocale() {
        LocalSqliteHelper(this).getAllConfig(object :SqliteResponse<Config>{
            override fun onSuccess(response: Config) {
                setLocale(this@MainActivity, if (response.language.id ==1 )"tr" else "en" )
                this@MainActivity.recreate()
            }

            override fun onFail(failMessage: String) {
                showError()
            }
        })
    }
}