package com.laks.tvseries.core.data.model

import com.google.gson.annotations.SerializedName

data class MovieCreditsModel(
        val id: Long? = 0,
        val cast: ArrayList<CastObject> = arrayListOf(),
        val crew: ArrayList<CastObject> = arrayListOf()
)

data class CastObject(
        val adult: Boolean? = false,
        val gender: Long? = 0,
        val id: Long? = 0,

        @SerializedName("known_for_department")
        val knownForDepartment: String? = null,

        val name: String? = null,

        @SerializedName("original_name")
        val originalName: String? = null,

        val popularity: Double? = 0.0,

        @SerializedName("profile_path")
        val profilePath: String? = null,

        @SerializedName("cast_id")
        val castID: Long? = 0,

        val character: String? = null,

        @SerializedName("credit_id")
        val creditID: String,

        val order: Long? = null,
        val department: String? = null,
        val job: String? = null,
)