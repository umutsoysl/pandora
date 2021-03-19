package com.laks.tvseries.core.data

import com.laks.tvseries.core.base.service.BaseUseCase
import com.laks.tvseries.core.data.model.ScheduleModelResponse
import kotlinx.coroutines.flow.Flow

class ScheduleUseCase(private val repo: ScheduleRepository): BaseUseCase(repo) {

    fun getScheduleFullList(): Flow<ScheduleModelResponse?> {
        return repo.getScheduleFull(getRequest())
    }
}