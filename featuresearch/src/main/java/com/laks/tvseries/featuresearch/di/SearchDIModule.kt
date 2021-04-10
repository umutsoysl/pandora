package com.laks.tvseries.featuresearch.di

import com.laks.tvseries.core.data.model.PersonModel
import com.laks.tvseries.featuresearch.SearchViewModel
import org.koin.dsl.module

val searchDIModule = module {
    single { PersonModel() }
    single { SearchViewModel(get()) }
}