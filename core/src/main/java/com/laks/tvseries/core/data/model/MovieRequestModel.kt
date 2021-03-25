package com.laks.tvseries.core.data.model


data class MovieRequestModel(
        var movieID: Int? = 0,
        var apiKey: String?  = "apikey",
        var language: String? = "en-US",
        var page: Int? = 1,
        var year: Int? = 0,
)