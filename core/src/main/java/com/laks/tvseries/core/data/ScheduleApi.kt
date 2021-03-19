package com.laks.tvseries.core.data

import com.laks.tvseries.core.base.model.BaseModel
import com.laks.tvseries.core.data.model.ScheduleModelResponse
import com.laks.tvseries.core.global.GlobalRequest
import com.laks.tvseries.core.global.GlobalResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface ScheduleApi {
    @GET("/schedule/full")
    suspend fun getScheduleFull(@Body request: GlobalRequest<BaseModel>): Response<GlobalResponse<ScheduleModelResponse>>
}