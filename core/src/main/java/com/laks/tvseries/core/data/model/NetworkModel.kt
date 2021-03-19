package com.laks.tvseries.core.data.model

data class NetworkModel (
        val id: Long,
        val name: String,
        val country: Country? = null
)

data class Country (
        val name: String,
        val code: String,
        val timezone: String
)

data class Rating(
        val average: Double? = null
)