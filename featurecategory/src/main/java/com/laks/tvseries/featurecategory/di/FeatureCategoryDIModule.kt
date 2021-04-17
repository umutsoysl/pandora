package com.laks.tvseries.featurecategory.di

import com.laks.tvseries.core.data.actor.ActorRepository
import com.laks.tvseries.core.data.category.CategoryRepository
import com.laks.tvseries.core.data.main.MediaRepository
import com.laks.tvseries.featurecategory.category.CategoryViewModel
import com.laks.tvseries.featurecategory.detail.MovieDetailViewModel
import org.koin.dsl.module

val categoryDIModule = module {
    single { CategoryViewModel(get()) }
    single { CategoryRepository() }
    single { ActorRepository() }
}

val detailDIModule = module {
    single { MovieDetailViewModel(get()) }
    single { MediaRepository() }
}