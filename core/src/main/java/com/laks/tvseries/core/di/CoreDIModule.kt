package com.laks.tvseries.core.di

import com.laks.tvseries.core.base.service.ServiceManager
import org.koin.dsl.module

val coreDIModule = module {
    single { ServiceManager.provideHttpClient() }
}