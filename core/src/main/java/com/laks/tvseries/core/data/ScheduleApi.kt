package com.laks.tvseries.core.data

import com.laks.tvseries.core.data.model.ScheduleModelResponse
import com.laks.tvseries.core.global.GlobalResponse
import retrofit2.Response
import retrofit2.http.GET

interface ScheduleApi {
    @GET("/schedule/full")
    suspend fun getScheduleFull(): Response<GlobalResponse<ScheduleModelResponse>>
}