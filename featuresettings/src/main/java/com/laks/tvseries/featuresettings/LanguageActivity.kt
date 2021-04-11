package com.laks.tvseries.featuresettings

import android.os.Bundle
import com.laks.tvseries.core.base.activity.BaseActivity
import com.laks.tvseries.core.di.mediaDIModule
import com.laks.tvseries.core.di.stateDIModule
import com.laks.tvseries.featuresettings.databinding.ActivityLanguageBinding
import org.koin.core.module.Module

class LanguageActivity : BaseActivity<SettingsViewModel>(SettingsViewModel::class) {

    override val modules: List<Module>
        get() = listOf(settingsDIModule, stateDIModule, mediaDIModule)
    private lateinit var binding: ActivityLanguageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(R.layout.activity_language)
        binding.viewModel = baseViewModel
        binding.lifecycleOwner = this
        setToolbarTitle(resources.getString(com.laks.tvseries.core.R.string.language))
    }
}