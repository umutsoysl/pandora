package com.laks.tvseries.core.di

import com.laks.tvseries.core.data.category.CategoryRepository
import com.laks.tvseries.core.data.main.ScheduleRepository
import com.laks.tvseries.core.data.model.DiscoverMovieListModel
import com.laks.tvseries.core.data.model.MovieModel
import com.laks.tvseries.core.data.model.TvSeriesListModel
import org.koin.dsl.module

val scheduleDIModule = module {
    single { DiscoverMovieListModel() }
    single { TvSeriesListModel() }
    single { MovieModel() }
    single { ScheduleRepository() }
    single { CategoryRepository() }
}
