package com.laks.tvseries.pandora.di

import com.laks.tvseries.pandora.MainViewModel
import org.koin.dsl.module

val homeDIModule = module {
    single { MainViewModel(get()) }
}