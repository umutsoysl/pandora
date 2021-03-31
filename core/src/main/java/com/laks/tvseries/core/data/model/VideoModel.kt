package com.laks.tvseries.core.data.model

import com.google.gson.annotations.SerializedName

data class VideoModel(
    val id: Long,
    val results: List<VideoInfoObject>
)

data class VideoInfoObject(
    val id: String,
    @SerializedName("iso_639_1")
    val iso639_1: String,
    @SerializedName("iso_3166_1")
    val iso3166_1: String,
    val key: String,
    val name: String,
    val site: String,
    val size: Long,
    val type: String
)