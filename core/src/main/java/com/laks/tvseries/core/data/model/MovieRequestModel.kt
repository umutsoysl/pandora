package com.laks.tvseries.core.data.model

import androidx.annotation.StringDef
import com.laks.tvseries.core.data.model.MediaType.Companion.all
import com.laks.tvseries.core.data.model.MediaType.Companion.movie
import com.laks.tvseries.core.data.model.MediaType.Companion.person
import com.laks.tvseries.core.data.model.MediaType.Companion.tv
import com.laks.tvseries.core.data.model.Time.Companion.day
import com.laks.tvseries.core.data.model.Time.Companion.week


data class MovieRequestModel(
        var movieID: String? = "0",
        var actorID: String? = "0",
        var apiKey: String?  = "api_key",
        var language: String? = "en-US",
        var query: String? = null,
        var page: Int? = 1,
        @Time var time: String? = null,
        @MediaType var type: String? = null,
        var year: Int? = 0,
)

@StringDef(all, movie, tv, person)
@Retention(AnnotationRetention.SOURCE)
annotation class MediaType {
        companion object {
                const val all = "all"
                const val movie = "movie"
                const val tv = "tv"
                const val person = "person"
        }
}

@StringDef(day, week)
@Retention(AnnotationRetention.SOURCE)
annotation class Time {
        companion object {
                const val day = "day"
                const val week = "week"
        }
}