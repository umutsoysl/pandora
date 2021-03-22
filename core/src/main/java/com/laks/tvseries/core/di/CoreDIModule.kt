package com.laks.tvseries.core.di

import com.laks.tvseries.core.base.service.ServiceManager
import com.laks.tvseries.core.cache.ViewModelState
import org.koin.dsl.module

val stateDIModule = module {
    factory { ViewModelState() }
    single { ServiceManager.interceptOkHttpClient() }
}