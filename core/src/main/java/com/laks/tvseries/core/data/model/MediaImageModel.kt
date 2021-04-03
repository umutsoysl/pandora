package com.laks.tvseries.core.data.model

import com.google.gson.annotations.SerializedName

data class MediaImageModel(
    val id: Long,
    val backdrops: ArrayList<ImageObject> = arrayListOf(),
    val posters: ArrayList<ImageObject> = arrayListOf()
)

data class ImageObject(
    @SerializedName("file_path")
    val filePath: String,
)