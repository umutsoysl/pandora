package com.laks.tvseries.core.data.model

data class ShowLinksModel (
        val self: Self,
        val previousepisode: Self? = null,
        val nextepisode: Self? = null
)

data class Self (
        val href: String
)

data class Links (
        val self: Self
)