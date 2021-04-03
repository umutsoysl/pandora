package com.laks.tvseries.core.data.model

import com.google.gson.annotations.SerializedName

data class PersonModel(
    val page: Long? = 0,
    val results: ArrayList<PersonInfo>? = arrayListOf(),

    @SerializedName("total_pages")
    val totalPages: Long? = 0,

    @SerializedName("total_results")
    val totalResults: Long? = 0
)


data class PersonInfo(
    val id: Long? = 0,
    val name: String? = null,
    @SerializedName("profile_path")
    val posterPath: String? = null,
)

enum class Department {
    Directing, Editing, Writing, Screenplay
}