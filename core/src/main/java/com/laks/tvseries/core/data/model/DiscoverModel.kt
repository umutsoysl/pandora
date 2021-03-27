package com.laks.tvseries.core.data.model

import com.google.gson.annotations.SerializedName
import com.laks.tvseries.core.base.service.BaseResponse

data class DiscoverMovieListModel(
        val page: Long? = 0,
        val results: ArrayList<MovieModel>? = arrayListOf(),

        @SerializedName("total_pages")
        val totalPages: Long? = 0,

        @SerializedName("total_results")
        val totalResults: Long? = 0
) : BaseResponse()


data class MovieModel(
        val adult: Boolean = false,

        @SerializedName("backdrop_path")
        val backdropPath: String? = null,

        @SerializedName("genre_ids")
        val genreIDS: ArrayList<Long>? = arrayListOf(),
        val genres: List<Genre>? = arrayListOf(),
        val budget: Long? = 0,
        val id: Long? = 0,
        val homepage: String? = null,

        @SerializedName("imdb_id")
        val imdbID: String? = null,

        @SerializedName("original_language")
        val originalLanguage: String? = null,

        @SerializedName("original_title")
        val originalTitle: String? = null,

        val overview: String? = null,
        val popularity: Double? = 0.0,

        @SerializedName("poster_path")
        val posterPath: String? = null,

        @SerializedName("release_date")
        val releaseDate: String? = null,

        val status: String? = null,
        @SerializedName("tagline")
        val tagLine: String? = null,
        @SerializedName("title", alternate = ["name"])
        val title: String? = null,
        val video: Boolean = false,

        @SerializedName("vote_average")
        val voteAverage: Double? = 0.0,

        @SerializedName("vote_count")
        val voteCount: Long? = 0
)

data class Genre (
        val id: Long,
        val name: String
)