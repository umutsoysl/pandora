package com.laks.tvseries.core.language

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import java.util.*

object LanguageSettings {
    private const val PREFERENCE_LANGUAGE = "pref_language"
    private const val KEY_LANGUAGE = "key_language"
    const val LANGUAGE_CODE_EN = "en"
    const val LANGUAGE_COUNTRY_EN = "US"

    var defaultLanguage = Locale(
        LANGUAGE_CODE_EN,
        LANGUAGE_COUNTRY_EN
    )

    fun setLanguage(context: Context, locale: Locale) {
        Locale.setDefault(locale)
        val editor = getLanguagePreference(
            context
        ).edit()
        editor.putString(KEY_LANGUAGE, locale.toString())
        editor.apply()

        LocalizationUtility.applyLocalizationContext(context)
    }

    fun getLanguage(context: Context): Locale {
        val language = getLanguagePreference(
            context
        )
            .getString(KEY_LANGUAGE, defaultLanguage.toString())!!
            .split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val locale: Locale
        if (language.size == 1) {
            locale = Locale(language[0])
        } else if (language.size == 2) {
            locale = Locale(language[0], language[1].toUpperCase())
        } else {
            locale =
                defaultLanguage
        }
        return locale
    }

    private fun getLanguagePreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCE_LANGUAGE, Activity.MODE_PRIVATE)
    }
}