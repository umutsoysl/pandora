package com.laks.tvseries.core.di

import com.laks.tvseries.core.data.actor.ActorRepository
import com.laks.tvseries.core.data.category.CategoryRepository
import com.laks.tvseries.core.data.main.MediaRepository
import com.laks.tvseries.core.data.model.DiscoverMovieListModel
import com.laks.tvseries.core.data.model.MovieModel
import com.laks.tvseries.core.data.model.TvSeriesListModel
import com.laks.tvseries.core.data.search.SearchRepository
import org.koin.dsl.module

val mediaDIModule = module {
    single { DiscoverMovieListModel() }
    single { TvSeriesListModel() }
    single { MovieModel() }
    single { MediaRepository() }
    single { CategoryRepository() }
    single { ActorRepository() }
    single { SearchRepository() }
}
