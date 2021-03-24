package com.laks.tvseries.core.di

import com.laks.tvseries.core.data.ScheduleRepository
import com.laks.tvseries.core.data.model.TVShowModel
import org.koin.dsl.module

val scheduleDIModule = module {
    single { TVShowModel() }
    single { ScheduleRepository() }
}
