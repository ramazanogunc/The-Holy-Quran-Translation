package com.ramo.quran.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.ramo.quran.R
import com.ramo.quran.helper.showError
import com.ramo.quran.helper.showSuccess


class DonateFragment: Fragment() {

    private lateinit var rewardedAd: RewardedAd

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_donate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.buttonWatchAds).setOnClickListener { onWatchAdsClick() }

        activity?.let {
            rewardedAd = initRewardAds(it);
        }

    }

    private fun onWatchAdsClick() {
        rewardedAd?.let {
            if (it.isLoaded) {
                activity?.let {
                    rewardedAd.show(it, adCallback)
                    loadAds(rewardedAd,adLoadCallback)
                }
            }
            else {
                loadAds(rewardedAd,adLoadAndShowCallback)
            }
        }
    }

    private fun initRewardAds(activity: Activity): RewardedAd {
        return RewardedAd(activity, "ca-app-pub-3405727563403529/5085998150")
    }

    private fun loadAds(rewardedAd: RewardedAd, adLoadCallback: RewardedAdLoadCallback){
        rewardedAd.loadAd(AdRequest.Builder().build(), adLoadCallback)
    }

    private val adLoadCallback = object: RewardedAdLoadCallback() {
        override fun onRewardedAdLoaded() {
        }
        override fun onRewardedAdFailedToLoad(adError: LoadAdError) {
        }
    }

    private val adLoadAndShowCallback = object: RewardedAdLoadCallback() {
        override fun onRewardedAdLoaded() {
            rewardedAd?.let(fun(ads: RewardedAd) {
                activity?.let {
                    ads.show(it,adCallback)
                }
            })
        }
        override fun onRewardedAdFailedToLoad(adError: LoadAdError) {
            activity?.showError()
        }
    }

    private val adCallback = object: RewardedAdCallback() {
        override fun onRewardedAdOpened() {
        }
        override fun onRewardedAdClosed() {
            loadAds(rewardedAd,adLoadCallback)
        }
        override fun onUserEarnedReward(@NonNull reward: RewardItem) {
            activity?.showSuccess()
            activity?.let { loadAds(rewardedAd ,adLoadCallback) }
        }
        override fun onRewardedAdFailedToShow(adError: AdError) {
            loadAds(rewardedAd,adLoadCallback)
        }
    }
}