package com.laks.tvseries.core.data

import com.laks.tvseries.core.base.service.BaseRepository
import com.laks.tvseries.core.data.model.ScheduleModelResponse
import kotlinx.coroutines.flow.Flow

class ScheduleRepository: BaseRepository<ScheduleApi>(ScheduleApi::class.java) {

    fun getScheduleFull(): Flow<ScheduleModelResponse?> {
        return fetchData() {
            api.getScheduleFull()
        }
    }
}