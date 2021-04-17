package com.laks.tvseries.featuresettings

import com.laks.tvseries.core.data.main.MediaRepository
import org.koin.dsl.module

val settingsDIModule = module {
    single { SettingsViewModel(get()) }
    single { MediaRepository() }
}