package com.laks.tvseries.core.data

import com.laks.tvseries.core.base.model.BaseModel
import com.laks.tvseries.core.base.service.BaseRepository
import com.laks.tvseries.core.data.model.ScheduleModelResponse
import com.laks.tvseries.core.global.GlobalRequest
import kotlinx.coroutines.flow.Flow

class ScheduleRepository: BaseRepository<ScheduleApi>(ScheduleApi::class.java) {

    fun getScheduleFull(request: GlobalRequest<BaseModel>): Flow<ScheduleModelResponse?> {
        return fetchData {
            api.getScheduleFull(request)
        }
    }
}