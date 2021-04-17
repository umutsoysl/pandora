package com.laks.tvseries.core.di

import com.laks.tvseries.core.base.service.ServiceManager
import com.laks.tvseries.core.cache.ViewModelState
import com.laks.tvseries.core.data.model.DiscoverMovieListModel
import com.laks.tvseries.core.data.model.MovieModel
import com.laks.tvseries.core.data.model.TvSeriesListModel
import org.koin.dsl.module

val stateDIModule = module {
    factory { ViewModelState() }
    single { ServiceManager.interceptOkHttpClient() }
    single { DiscoverMovieListModel() }
    single { TvSeriesListModel() }
    single { MovieModel() }
}