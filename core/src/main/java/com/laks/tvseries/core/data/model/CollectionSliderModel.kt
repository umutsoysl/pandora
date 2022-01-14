package com.laks.tvseries.core.data.model

import com.google.gson.annotations.SerializedName
import com.laks.tvseries.core.base.model.BaseModel

data class CollectionSliderModel (
    var id: Int? = 0,
    var image: String? = null,
    var mediaType: String = MediaType.movie,
    @SerializedName(value = "title", alternate = ["name"])
    var title: String? = null,
): BaseModel()


data class CollectionArray (
    var collectionList: ArrayList<CollectionSliderModel> = arrayListOf()
): BaseModel()