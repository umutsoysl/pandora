package com.laks.tvseries.featureactor.di

import com.laks.tvseries.core.data.actor.ActorRepository
import com.laks.tvseries.core.data.model.MovieCreditsModel
import com.laks.tvseries.featureactor.detail.ActorDetailViewModel
import org.koin.dsl.module


val actorDetailDIModule = module {
    single { MovieCreditsModel() }
    single { ActorDetailViewModel(get()) }
    single { ActorRepository() }
}

