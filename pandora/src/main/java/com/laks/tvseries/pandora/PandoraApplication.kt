package com.laks.tvseries.pandora

import android.app.Application
import com.laks.tvseries.core.di.stateDIModule
import org.koin.android.ext.koin.androidContext

open class PandoraApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        org.koin.core.context.startKoin {
            androidContext(this@PandoraApplication)
            modules(listOf(stateDIModule))
        }
    }
}