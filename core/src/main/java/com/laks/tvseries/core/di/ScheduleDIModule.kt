package com.laks.tvseries.core.di

import com.laks.tvseries.core.data.ScheduleRepository
import com.laks.tvseries.core.data.model.ScheduleModelResponse
import org.koin.dsl.module

val scheduleDIModule = module {
    single { ScheduleModelResponse() }
    single { ScheduleRepository() }
}
