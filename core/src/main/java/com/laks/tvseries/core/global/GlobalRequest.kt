package com.laks.tvseries.core.global

import com.google.gson.annotations.SerializedName

class GlobalRequest<T>(
        @SerializedName("Content")
        var content: T? = null,
        @SerializedName("ClassName")
        val className: String = ""
)