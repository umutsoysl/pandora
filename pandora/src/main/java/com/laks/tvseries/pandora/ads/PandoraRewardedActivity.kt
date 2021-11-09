package com.laks.tvseries.pandora.ads

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.core.language.LocalizationLanguageManager
import com.laks.tvseries.core.language.OnLocaleLanguageChangedListener
import com.laks.tvseries.pandora.R


class PandoraRewardedActivity : Activity(), RewardedVideoAdListener,
    OnLocaleLanguageChangedListener {

    private var mRewardedVideoAd: RewardedVideoAd? = null
    private val localizationManager = LocalizationLanguageManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        localizationManager.addOnLocaleChangedListener(this)
        localizationManager.onCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pandora_rewarded)

        MobileAds.initialize(this, getString(R.string.app_id))
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        mRewardedVideoAd?.rewardedVideoAdListener = this

        adsLoad()
    }

    fun showAdsButtonClickListener(v: View) {
        if (mRewardedVideoAd?.isLoaded!!) {
            mRewardedVideoAd?.show()
        }
    }

    fun cancelButtonClickListener(v: View) {
        val intent = Intent()
        intent.putExtra(GlobalConstants.IS_WATCH_ADS, false)
        setResult(GlobalConstants.REWARD_ADS_RESULT_CODE, intent)
        finish()
    }

    private fun adsLoad() {
        mRewardedVideoAd!!.loadAd(
            getString(R.string.ads_reward_id),  //use this id for testing
            AdRequest.Builder().build()
        )
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        MobileAds.initialize(this, getString(R.string.app_id))
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)

        adsLoad()
    }

    private fun finishAds() {
        val intent = Intent()
        intent.putExtra(GlobalConstants.IS_WATCH_ADS, true)
        setResult(GlobalConstants.REWARD_ADS_RESULT_CODE, intent)
        finish()
    }

    override fun onRewardedVideoAdLoaded() {}
    override fun onRewardedVideoAdOpened() {}
    override fun onRewardedVideoStarted() {}
    override fun onRewardedVideoAdClosed() { finishAds() }
    override fun onRewarded(p0: RewardItem?) {}
    override fun onRewardedVideoAdLeftApplication() {}
    override fun onRewardedVideoAdFailedToLoad(p0: Int) { finishAds() }
    override fun onRewardedVideoCompleted() {}

    override fun onBeforeLocaleChanged() {}

    override fun onAfterLocaleChanged() {}

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(localizationManager.attachBaseContext(newBase!!))
    }

    override fun onResume() {
        super.onResume()
        localizationManager.onResume(this)
    }
}