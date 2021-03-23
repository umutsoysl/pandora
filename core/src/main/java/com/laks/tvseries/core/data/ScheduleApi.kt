package com.laks.tvseries.core.data

import com.laks.tvseries.core.data.model.ScheduleAllListModelResponse
import retrofit2.Response
import retrofit2.http.GET

interface ScheduleApi {
    @GET("/schedule/full")
    suspend fun getScheduleFull(): Response<ArrayList<ScheduleAllListModelResponse>>

    @GET("/schedule")
    suspend fun getSchedule(): Response<ArrayList<ScheduleAllListModelResponse>>
}