package com.laks.tvseries.core.data.model

import com.google.gson.annotations.SerializedName
import com.laks.tvseries.core.base.service.BaseResponse

data class TvSeriesListModel(
        val page: Long? = 0,
        val results: ArrayList<TVModel>? = arrayListOf(),

        @SerializedName("total_pages")
        val totalPages: Long? = 0,

        @SerializedName("total_results")
        val totalResults: Long? = 0
) : BaseResponse()


data class TVModel(
        val adult: Boolean = false,
        val gender: Long? = 0,
        val id: Long? = 0,

        @SerializedName("known_for")
        val knownFor: ArrayList<KnownFor>? = arrayListOf(),

        @SerializedName("known_for_department")
        val knownForDepartment: String? = null,

        val name: String? = null,
        val popularity: Double? = 0.0,

        @SerializedName("profile_path")
        val profilePath: String? = null
)

data class KnownFor(
        val adult: Boolean = false,

        @SerializedName("backdrop_path")
        val backdropPath: String? = null,

        @SerializedName("genre_ids")
        val genreIDS: List<Long> = arrayListOf(),

        val id: Long? = 0,

        @SerializedName("media_type")
        val mediaType: String? = null,

        @SerializedName("original_language")
        val originalLanguage: String? = null,

        @SerializedName("original_title")
        val originalTitle: String? = null,

        val overview: String? = null,

        @SerializedName("poster_path")
        val posterPath: String? = null,

        @SerializedName("release_date")
        val releaseDate: String? = null,

        val title: String? = null,
        val video: Boolean = false,

        @SerializedName("vote_average")
        val voteAverage: Double? = 0.0,

        @SerializedName("vote_count")
        val voteCount: Long? = 0
)