package com.laks.tvseries.core.data.model.actor

import com.google.gson.annotations.SerializedName

data class ActorDetailModel(
    val adult: Boolean = false,

    @SerializedName("also_known_as")
    val alsoKnownAs: ArrayList<String> = arrayListOf(),

    val biography: String? = null,
    val birthday: String? = null,
    val gender: String = "0",
    val id: Long = 0,

    @SerializedName("imdb_id")
    val imdbID: String? = null,

    @SerializedName("known_for_department")
    val knownForDepartment: String? = null,

    val name: String? = null,

    @SerializedName("place_of_birth")
    val placeOfBirth: String? = null,

    val popularity: Double = 0.0,

    @SerializedName("profile_path")
    val profilePath: String? = null
)