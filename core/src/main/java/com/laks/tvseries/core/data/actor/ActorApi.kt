package com.laks.tvseries.core.data.actor

import com.laks.tvseries.core.data.model.MovieCreditsModel
import com.laks.tvseries.core.data.model.actor.ActorDetailModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ActorApi {

    @GET("/3/person/{personID}")
    suspend fun getActorDetail(@Path("personID") personID: String, @Query("api_key") authToken: String, @Query("language") language: String): Response<ActorDetailModel>

    @GET("/3/person/{personID}/combined_credits")
    suspend fun getActorBackDropImage(@Path("personID") personID: String, @Query("api_key") authToken: String, @Query("language") language: String): Response<MovieCreditsModel>

    @GET("/3/person/{personID}/movie_credits")
    suspend fun getActorMovies(@Path("personID") personID: String, @Query("api_key") authToken: String, @Query("language") language: String): Response<MovieCreditsModel>

    @GET("/3/person/{personID}/tv_credits")
    suspend fun getActorTv(@Path("personID") personID: String, @Query("api_key") authToken: String, @Query("language") language: String): Response<MovieCreditsModel>

}