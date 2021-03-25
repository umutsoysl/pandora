package com.laks.tvseries.core.di

import com.laks.tvseries.core.data.ScheduleRepository
import com.laks.tvseries.core.data.model.DiscoverMovieListModel
import com.laks.tvseries.core.data.model.MovieModel
import com.laks.tvseries.core.data.model.TvSeriesListModel
import org.koin.dsl.module

val scheduleDIModule = module {
    single { DiscoverMovieListModel() }
    single { TvSeriesListModel() }
    single { MovieModel() }
    single { ScheduleRepository() }
}
