package com.laks.tvseries.featurecategory.di

import com.laks.tvseries.featurecategory.category.TrendCategoryViewModel
import com.laks.tvseries.featurecategory.detail.MovieDetailViewModel
import org.koin.dsl.module

val trendCategoryDIModule = module {
    single { TrendCategoryViewModel(get()) }
}

val detailDIModule = module {
    single { MovieDetailViewModel(get()) }
}
