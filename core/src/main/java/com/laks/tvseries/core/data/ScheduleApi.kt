package com.laks.tvseries.core.data

import com.laks.tvseries.core.data.model.ScheduleModelResponse
import retrofit2.Response
import retrofit2.http.GET

interface ScheduleApi {
    @GET("/schedule")
    suspend fun getScheduleFull(): Response<ScheduleModelResponse>
}