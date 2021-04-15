package com.laks.tvseries.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.laks.tvseries.core.di.stateDIModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level

open class CoreActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stopKoin()
        startKoin()
    }

    private fun startKoin() {
        org.koin.core.context.startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@CoreActivity)
            modules(listOf(stateDIModule))
        }
    }
}