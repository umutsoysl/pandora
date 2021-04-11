package com.laks.tvseries.core.language

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources

@Suppress("DEPRECATION")
class LocalizationContext(base: Context): ContextWrapper(base) {
    override fun getResources(): Resources {
        val conf = super.getResources().configuration
        conf.locale = LanguageSettings.getLanguage(this)
        val metrics = super.getResources().displayMetrics
        return Resources(assets, metrics, conf)
    }
}