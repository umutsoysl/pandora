package com.laks.tvseries.core.data.model

import androidx.annotation.StringDef
import com.google.gson.annotations.SerializedName
import com.laks.tvseries.core.data.model.SortBy.Companion.originalTitleDesc
import com.laks.tvseries.core.data.model.SortBy.Companion.popularityDesc
import com.laks.tvseries.core.data.model.SortBy.Companion.releaseDateDesc
import com.laks.tvseries.core.data.model.SortBy.Companion.revenueDesc
import com.laks.tvseries.core.data.model.SortBy.Companion.voteAverageDesc

data class DiscoverRequestModel(
        @SerializedName("with_genres")
        var genre: String = "",

        @SerializedName("sort_by")
        @SortBy var sortBy: String = voteAverageDesc,

        var page: Int? = 1,

        @SerializedName("vote_average.gte")
        var voteAverageGte: Double = 0.0,

        @SerializedName("vote_average.lte")
        var voteAverageLte: Double = 10.0,

        @SerializedName("release_date.gte")
        var minYear: String = "1900-01-01",

        @SerializedName("release_date.lte")
        var maxYear: String = "2050-12-30",
)


@StringDef(popularityDesc, revenueDesc, originalTitleDesc, releaseDateDesc, voteAverageDesc)
@Retention(AnnotationRetention.SOURCE)
annotation class SortBy {
    companion object {
        const val popularityDesc = "popularity.desc"
        const val revenueDesc = "revenue.desc"
        const val originalTitleDesc = "original_title.desc"
        const val releaseDateDesc = "release_date.desc"
        const val voteAverageDesc = "vote_average.desc"
    }
}