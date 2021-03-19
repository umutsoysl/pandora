package com.laks.tvseries.core.data.model

import kotlinx.serialization.SerialName

data class Show (
        val id: Long,
        val url: String,
        val name: String,
        val type: ShowType,
        val language: String? = null,
        val genres: List<Genre>,
        val status: Status,
        val runtime: Long? = null,
        val premiered: String,
        val officialSite: String? = null,
        val schedule: Schedule,
        val rating: Rating,
        val weight: Long,
        val network: NetworkModel? = null,
        val webChannel: NetworkModel? = null,
        val externals: Externals,
        val image: Image? = null,
        val summary: String? = null,
        val updated: Long,

        @SerialName("_links")
        val links: ShowLinksModel
)

data class Externals (
        val tvrage: Long? = null,
        val thetvdb: Long? = null,
        val imdb: String? = null
)