package com.laks.tvseries.featuresettings

import org.koin.dsl.module

val settingsDIModule = module {
    single { SettingsViewModel(get()) }
}