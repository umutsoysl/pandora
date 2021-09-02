package com.laks.tvseries.featurecategory.detail

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.laks.tvseries.core.cache.MemoryCache
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.core.view.FullyTransparentStatusBar
import com.laks.tvseries.featurecategory.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class FullScreenVideoPlayerActivity : AppCompatActivity() {
    private var youtubePlayerView : YouTubePlayerView? = null
    private var videoID: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_video_player)
        initStatusBar()
        youtubePlayerView = findViewById(R.id.youtube_player_view)
        var backButton = findViewById<FloatingActionButton>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
            finish()
        }
        getVideoID()
    }

    private fun playVideo(second: Float) {
        youtubePlayerView?.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                videoID?.let { youTubePlayer.cueVideo(it, 0f) }
            }
        })
    }

    private fun getVideoID() {
        videoID = MemoryCache.cache.findMemoryCacheValueAny(GlobalConstants.YOUTUBE_VIDEO_ID).toString()
        var duraction = MemoryCache.cache.findMemoryCacheValueAny(GlobalConstants.YOUTUBE_VIDEO_DURACTION).toString().toFloat()
        if(!videoID.isNullOrEmpty()) {
            playVideo(duraction/1000)
        }
    }

    private fun initStatusBar() {
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val fullyTransparentStatusBar = FullyTransparentStatusBar(this, window)
        fullyTransparentStatusBar.setFullTransparentStatus()
    }
}