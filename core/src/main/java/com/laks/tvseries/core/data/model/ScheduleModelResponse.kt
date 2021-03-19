package com.laks.tvseries.core.data.model

import com.laks.tvseries.core.base.service.BaseResponse
import kotlinx.serialization.SerialName

open class ScheduleModelResponse(
        var id: Long? = null,
        var url: String? = null,
        var name: String? = null,
        var season: Long? = null,
        var number: Long? = null,
        @ScheduleType var type: String? = null,
        var airdate: String? = null,
        var airtime: String? = null,
        var airstamp: String? = null,
        var runtime: Long? = null,
        var image: Image? = null,
        var summary: String? = null,
        @SerialName("_links")
        var links: Links? = null,
        @SerialName("_embedded")
        var embedded: EmbeddedModel? = null
) : BaseResponse()

data class Schedule(
        val time: String?= null,
        val days: ArrayList<Day>? = arrayListOf()
)

data class Image(
        val medium: String?= null,
        val original: String?= null
)

data class EmbeddedModel(
        val show: Show?= null
)