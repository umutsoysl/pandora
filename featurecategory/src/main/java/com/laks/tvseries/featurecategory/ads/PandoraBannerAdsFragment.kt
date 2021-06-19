package com.laks.tvseries.featurecategory.ads

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.laks.tvseries.featurecategory.R

class PandoraBannerAdsFragment: Fragment() {
    private lateinit var mAdView : AdView
    private val TAG: String = com.laks.tvseries.featurecategory.ads.PandoraBannerAdsFragment::class.java.getSimpleName()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
       val view =  inflater.inflate(R.layout.fragment_banner_ads, container, false)
        mAdView = view.findViewById(R.id.adView)
        mAdView.adListener = mAdListener;
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private val mAdListener: AdListener = object : AdListener() {
        override fun onAdClosed() {
            Log.d(TAG, "onAdClosed")
        }

        override fun onAdFailedToLoad(errorCode: Int) {
            var message = "Unknown"
            when (errorCode) {
                AdRequest.ERROR_CODE_INTERNAL_ERROR -> message = "ERROR_CODE_INTERNAL_ERROR"
                AdRequest.ERROR_CODE_INVALID_REQUEST -> message = "ERROR_CODE_INVALID_REQUEST"
                AdRequest.ERROR_CODE_NETWORK_ERROR -> message = "ERROR_CODE_NETWORK_ERROR"
                AdRequest.ERROR_CODE_NO_FILL -> message = "ERROR_CODE_NO_FILL"
            }
            Log.d(TAG, message)
        }

        override fun onAdLeftApplication() {
            Log.d(TAG, "onAdLeftApplication")
        }

        override fun onAdOpened() {
            Log.d(TAG, "onAdOpened")
        }

        override fun onAdLoaded() {
            Log.d(TAG, "onAdLoaded")
        }
    }

    companion object {
        fun newInstance() = PandoraBannerAdsFragment()
    }
}