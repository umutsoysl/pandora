package com.laks.tvseries.featuresettings.about

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import com.laks.tvseries.core.base.activity.BaseActivity
import com.laks.tvseries.core.cache.MemoryCache
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.featuresettings.R
import com.laks.tvseries.featuresettings.SettingsViewModel
import com.laks.tvseries.featuresettings.databinding.ActivityAboutBinding
import com.laks.tvseries.featuresettings.settingsDIModule
import org.koin.core.module.Module

class TermOfUseActivity : BaseActivity<SettingsViewModel>(SettingsViewModel::class) {

    override val modules: List<Module>
        get() = listOf(settingsDIModule)
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(R.layout.activity_about)
        binding.viewModel = baseViewModel
        binding.lifecycleOwner = this
        setToolbarTitle(resources.getString(com.laks.tvseries.core.R.string.term_of_use))
        removeHeaderSearchButton()

        var sequence: CharSequence = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(termsOfUseHtml, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(termsOfUseHtml)
        }
        val strBuilder = SpannableStringBuilder(sequence)
        binding.labelAbout.text = strBuilder
        binding.labelAbout.linksClickable = true
        binding.labelAbout.movementMethod = LinkMovementMethod.getInstance()
    }

    private var termsOfUseHtml = MemoryCache.cache.findMemoryCacheValueAny(GlobalConstants.FIREBASE_TERM_OF_USE_TABLE).let { it.toString() }
}