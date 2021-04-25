package com.laks.tvseries.pandora

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.laks.tvseries.core.CoreActivity
import com.laks.tvseries.core.cache.MemoryCache
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.core.global.StoreShared
import java.util.*

class SplashActivity : CoreActivity() {

    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 2200
    private var waitTimer: Timer? = null

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            try{
                getLocaleLanguage()
            }catch (e:Exception){
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mDelayHandler = Handler()
        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
    }

    private fun initTimer() {
        waitTimer = Timer()
        waitTimer!!.schedule(object : TimerTask() {
            override fun run() {
                this@SplashActivity.runOnUiThread(Runnable {
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent) })
            }
        }, 900)
    }

    private fun getLocaleLanguage() {
        val languageCode = StoreShared(this).getStringValue(GlobalConstants.SHARED_LANGUAGE_WITH_CODE_COUNTRY)
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.SHARED_LANGUAGE_WITH_CODE_COUNTRY, if (languageCode.isNullOrEmpty()) "en-US" else languageCode)
        initTimer()
    }

    override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }

}