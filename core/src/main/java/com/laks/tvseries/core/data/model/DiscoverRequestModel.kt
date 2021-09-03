package com.laks.tvseries.core.data.model

import androidx.annotation.StringDef
import com.google.gson.annotations.SerializedName
import com.laks.tvseries.core.data.model.ShortBy.Companion.popularityAsc
import com.laks.tvseries.core.data.model.ShortBy.Companion.popularityDesc
import com.laks.tvseries.core.data.model.ShortBy.Companion.releaseDateAsc
import com.laks.tvseries.core.data.model.ShortBy.Companion.releaseDateDesc
import com.laks.tvseries.core.data.model.ShortBy.Companion.voteAverageAsc
import com.laks.tvseries.core.data.model.ShortBy.Companion.voteAverageDesc

data class DiscoverRequestModel(
        @SerializedName("with_genres")
        var genre: String = "",

        @SerializedName("sort_by")
        @ShortBy val sortBy: String = popularityDesc,

        var page: Int? = 1,
        val year: Int? = null,

        @SerializedName("vote_average.gte")
        val voteAverageGte: Double? = 0.0,

        @SerializedName("vote_average.lte")
        val voteAverageLte: Double? = 10.0
)


@StringDef(popularityDesc, popularityAsc, releaseDateAsc, releaseDateDesc, voteAverageAsc, voteAverageDesc)
@Retention(AnnotationRetention.SOURCE)
annotation class ShortBy {
    companion object {
        const val popularityDesc = "popularity.desc"
        const val popularityAsc = "popularity.asc"
        const val releaseDateAsc = "release_date.asc"
        const val releaseDateDesc = "release_date.desc"
        const val voteAverageAsc = "vote_average.asc"
        const val voteAverageDesc = "vote_average.desc"
    }
}