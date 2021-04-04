package com.laks.tvseries.featureactor.di

import com.laks.tvseries.featureactor.detail.ActorDetailViewModel
import org.koin.dsl.module


val actorDetailDIModule = module {
    single { ActorDetailViewModel(get()) }
}

