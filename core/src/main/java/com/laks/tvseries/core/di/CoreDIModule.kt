package com.laks.tvseries.core.di

import com.laks.tvseries.core.base.service.ServiceManager
import com.laks.tvseries.core.cache.ViewModelState
import com.laks.tvseries.core.data.ScheduleRepository
import org.koin.dsl.module

val coreDIModule = module {
    single { ServiceManager.provideHttpClient() }
}

val stateDIModule = module {
    factory { ViewModelState() }
}