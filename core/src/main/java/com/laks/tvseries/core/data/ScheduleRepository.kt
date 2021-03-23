package com.laks.tvseries.core.data

import com.laks.tvseries.core.base.service.BaseRepository
import com.laks.tvseries.core.data.model.ScheduleAllListModelResponse
import kotlinx.coroutines.flow.Flow

class ScheduleRepository: BaseRepository<ScheduleApi>(ScheduleApi::class.java) {

    fun getScheduleFull(): Flow<ArrayList<ScheduleAllListModelResponse>?> {
        return fetchData {
            api.getScheduleFull()
        }
    }

    fun getSchedule(): Flow<ArrayList<ScheduleAllListModelResponse>?> {
        return fetchData {
            api.getSchedule()
        }
    }
}