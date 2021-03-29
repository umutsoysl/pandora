package com.laks.tvseries.core.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.json.JsonObject

data class MovieDetailModel(
        val adult: Boolean = false,

        @SerializedName("backdrop_path")
        val backdropPath: String? = null,

        @SerializedName("belongs_to_collection")
        val belongsToCollection: JsonObject? = null,

        val budget: Long = 0,
        val genres: ArrayList<Genre>? = arrayListOf(),
        val homepage: String? = null,
        val id: Long = 0,

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

        @SerializedName("release_date", alternate = ["first_air_date"])
        val releaseDate: String? = null,

        val revenue: Long = 0,
        val runtime: String = "",
        @SerializedName("episode_run_time")
        val tvRuntime: ArrayList<String>? = arrayListOf(),
        val status: String? = null,
        val tagline: String? = null,
        @SerializedName("title", alternate = ["name"])
        val title: String? = null,
        val video: Boolean = false,

        @SerializedName("vote_average")
        val voteAverage: Double = 0.0,

        @SerializedName("vote_count")
        val voteCount: Long = 0
)