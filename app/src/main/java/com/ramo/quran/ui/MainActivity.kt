package com.ramo.quran.ui

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.material.navigation.NavigationView
import com.ramo.quran.R
import com.ramo.quran.dataAccess.LocalSqliteHelper
import com.ramo.quran.dataAccess.abstr.SqliteResponse
import com.ramo.quran.helper.LocaleWrapper
import com.ramo.quran.helper.setProgressDialog
import com.ramo.quran.helper.showError
import com.ramo.quran.helper.showInfo
import com.ramo.quran.model.Config
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val rewardedAd: RewardedAd by lazy {
        RewardedAd(this, "ca-app-pub-3405727563403529/5085998150")
    }

    private val adCallback = object : RewardedAdCallback() {
        override fun onRewardedAdOpened() {}
        override fun onRewardedAdClosed() {}
        override fun onUserEarnedReward(@NonNull reward: RewardItem) {
            showInfo(getString(R.string.thanks_for_watch_ads))
        }

        override fun onRewardedAdFailedToShow(adError: AdError) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this) {} // init ads
        setSupportActionBar(toolbar)

        initNavigationComponent()
    }

    private fun initNavigationComponent() {
        val navController = findNavController(R.id.navHostFragment)
        findViewById<NavigationView>(R.id.nav_view)
            .setupWithNavController(navController)
        // toolbar
        findViewById<Toolbar>(R.id.toolbar).setupWithNavController(navController, drawerLayout)
        // handle watch ads menu item
        nav_view.setNavigationItemSelectedListener {
            drawerLayout.close()
            if (it.itemId == R.id.watch_ads) {
                showAds()
                false
            } else {
                NavigationUI.onNavDestinationSelected(it, navController)
                true
            }
        }
    }

    private fun showAds() {
        val dialog = setProgressDialog(this, "Ads is loading. Please wait")
        dialog.show()
        rewardedAd.loadAd(AdRequest.Builder().build(), object : RewardedAdLoadCallback() {
            override fun onRewardedAdLoaded() {
                dialog.dismiss()
                rewardedAd.show(this@MainActivity, adCallback)
            }

            override fun onRewardedAdFailedToLoad(adError: LoadAdError) {
                dialog.dismiss()
                showError()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun attachBaseContext(newBase: Context?) {
        var lang = "en"
        LocalSqliteHelper(newBase).getAllConfig(object : SqliteResponse<Config> {
            override fun onSuccess(response: Config) {
                lang = if (response.language.id == 1) "tr" else "en"
            }

            override fun onFail(failMessage: String) {

            }
        })
        super.attachBaseContext(LocaleWrapper.wrap(newBase!!, lang))
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.close()
        else
            super.onBackPressed()
    }

}