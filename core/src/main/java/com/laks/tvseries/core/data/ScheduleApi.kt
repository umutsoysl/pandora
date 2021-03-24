package com.laks.tvseries.core.data

import com.laks.tvseries.core.data.model.TVShowModel
import retrofit2.Response
import retrofit2.http.GET

interface ScheduleApi {
    @GET("/schedule/full")
    suspend fun getScheduleFull(): Response<ArrayList<TVShowModel>>

    @GET("/schedule")
    suspend fun getSchedule(): Response<ArrayList<TVShowModel>>
}