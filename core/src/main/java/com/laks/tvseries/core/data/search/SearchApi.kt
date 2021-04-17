package com.laks.tvseries.core.data.search

import com.laks.tvseries.core.data.model.DiscoverMovieListModel
import com.laks.tvseries.core.data.model.PersonModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("/3/search/movie")
    suspend fun searchMovie(@Query("query") query: String, @Query("page") page: Int): Response<DiscoverMovieListModel>

    @GET("/3/search/tv")
    suspend fun searchTv( @Query("query") query: String, @Query("page") page: Int): Response<DiscoverMovieListModel>

    @GET("/3/search/person")
    suspend fun searchActor(@Query("query") query: String, @Query("page") page: Int): Response<PersonModel>

}