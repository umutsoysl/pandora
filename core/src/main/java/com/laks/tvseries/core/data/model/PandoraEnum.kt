package com.laks.tvseries.core.data.model

import androidx.annotation.StringDef
import com.laks.tvseries.core.data.model.MovieType.Companion.nowPlaying
import com.laks.tvseries.core.data.model.MovieType.Companion.popular
import com.laks.tvseries.core.data.model.MovieType.Companion.trend
import com.laks.tvseries.core.data.model.MovieType.Companion.upComing

@StringDef(trend, nowPlaying, upComing, popular)
@Retention(AnnotationRetention.SOURCE)
annotation class MovieType {
    companion object {
        const val trend = "trend"
        const val nowPlaying = "nowPlaying"
        const val upComing = "upComing"
        const val popular = "popular"
        const val popularTV = "popularTV"
        const val topRated = "topRated"
    }
}