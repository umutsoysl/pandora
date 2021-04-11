package com.laks.tvseries.core.language

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import java.util.Locale

object LocalizationUtility {

    private fun getLocaleFromConfiguration(configuration: Configuration): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            configuration.locales.get(0)
        } else {
            configuration.locale
        }
    }

    fun applyLocalizationContext(baseContext: Context): Context {
        val baseLocale = getLocaleFromConfiguration(baseContext.resources.configuration)
        val currentLocale = LanguageSettings.getLanguage(baseContext)
        if(!baseLocale.toString().equals(currentLocale.toString(), ignoreCase = true)){
            val context = LocalizationContext(baseContext)
            val config = context.resources.configuration

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                config.setLocale(currentLocale)
                val localeList = LocaleList(currentLocale)
                LocaleList.setDefault(localeList)
                config.setLocales(localeList)
                return context.createConfigurationContext(config)
            } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                config.setLocale(currentLocale)
                return context.createConfigurationContext(config)
            } else {
                return context
            }
        }else{
            return baseContext
        }
    }
}