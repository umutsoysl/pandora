package com.laks.tvseries.featurecategory.di

import com.laks.tvseries.featurecategory.category.trend.TrendCategoryViewModel
import org.koin.dsl.module

val trendCategoryDIModule = module {
    single { TrendCategoryViewModel(get()) }
}
