package com.laks.tvseries.core.language

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.LocaleList
import java.util.*

@Suppress("DEPRECATION")
class LocalizationLanguageManager(private val activity: Activity) {

    // Boolean flag to check that activity was recreated from locale changed.
    private var isLocalizationChanged = false

    // Prepare default language.
    private var currentLanguage = LanguageSettings.defaultLanguage
    private val localeChangedListeners = ArrayList<OnLocaleLanguageChangedListener>()

    fun addOnLocaleChangedListener(onLocaleChangedListener: OnLocaleLanguageChangedListener) {
        localeChangedListeners.add(onLocaleChangedListener)
    }

    fun onCreate(savedInstanceState: Bundle?) {
        setupLanguage()
        checkBeforeLocaleChanging()
    }

    // If activity is run to back stack. So we have to check if this activity is resume working.
    fun onResume(context: Context) {
        Handler().post {
            checkLocaleChange(context)
            checkAfterLocaleChanging()
        }
    }

    fun attachBaseContext(context: Context): Context {
        val locale = LanguageSettings.getLanguage(context)
        val config = context.resources.configuration
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                config.setLocale(locale)
                val localeList = LocaleList(locale)
                LocaleList.setDefault(localeList)
                config.setLocales(localeList)
                return context.createConfigurationContext(config)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 -> {
                config.setLocale(locale)
                return context.createConfigurationContext(config)
            }
            else -> {
                return context
            }
        }
    }

    fun getApplicationContext(applicationContext: Context): Context {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            LocalizationUtility.applyLocalizationContext(applicationContext)
        } else {
            applicationContext
        }
    }

    fun getResources(resources: Resources): Resources {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            val config = resources.configuration
            config.locale = LanguageSettings.getLanguage(activity)
            val metrics = resources.displayMetrics
            Resources(activity.assets, metrics, config)
        } else {
            resources
        }
    }

    // Provide method to set application language by country name.
    fun setLanguage(context: Context, language: String) {
        val locale = Locale(language)
        setLanguage(context, locale)
    }

    fun setLanguage(context: Context, language: String, country: String) {
        val locale = Locale(language, country)
        setLanguage(context, locale)
    }

    fun setLanguage(context: Context, locale: Locale) {
        if (!isCurrentLanguageSetting(context, locale)) {
            currentLanguage = locale
            LanguageSettings.setLanguage(activity, locale)
            notifyLanguageChanged()
        }
    }

    fun setDefaultLanguage(language: String) {
        val locale = Locale(language)
        setDefaultLanguage(locale)
    }

    fun setDefaultLanguage(language: String, country: String) {
        val locale = Locale(language, country)
        setDefaultLanguage(locale)
    }

    fun setDefaultLanguage(locale: Locale) {
        LanguageSettings.defaultLanguage = locale
    }

    // Get current language
    fun getLanguage(context: Context): Locale {
        return LanguageSettings.getLanguage(context)
    }

    // Check that bundle come from locale change.
    // If yes, bundle will obe remove and set boolean flag to "true".
    private fun checkBeforeLocaleChanging() {
        val isLocalizationChanged = activity.intent.getBooleanExtra(KEY_ACTIVITY_LOCALE_CHANGED, false)
        if (isLocalizationChanged) {
            this.isLocalizationChanged = true
            activity.intent.removeExtra(KEY_ACTIVITY_LOCALE_CHANGED)
        }
    }

    // Setup language to locale and language preference.
    // This method will called before onCreate.
    private fun setupLanguage() {
        val locale = LanguageSettings.getLanguage(activity)
        setupLocale(locale)
        currentLanguage = locale
        LanguageSettings.setLanguage(activity, locale)
    }

    // Set locale configuration.
    private fun setupLocale(locale: Locale) {
        updateLocaleConfiguration(activity, locale)
    }


    private fun updateLocaleConfiguration(context: Context, locale: Locale) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val config = context.resources.configuration
            config.locale = locale
            val dm = context.resources.displayMetrics
            context.resources.updateConfiguration(config, dm)
        }
    }

    // Avoid duplicated setup
    private fun isCurrentLanguageSetting(context: Context, locale: Locale): Boolean {
        return locale.toString() == LanguageSettings.getLanguage(context).toString()
    }

    // Let's take it change! (Using recreate method that available on API 11 or more.
    private fun notifyLanguageChanged() {
        sendOnBeforeLocaleChangedEvent()
        activity.intent.putExtra(KEY_ACTIVITY_LOCALE_CHANGED, true)
    }

    // Check if locale has change while this activity was run to back stack.
    private fun checkLocaleChange(context: Context) {
        if (!isCurrentLanguageSetting(context, currentLanguage)) {
            sendOnBeforeLocaleChangedEvent()
            isLocalizationChanged = true
            activity.recreate()
        }
    }

    // Call override method if local is really changed
    private fun checkAfterLocaleChanging() {
        if (isLocalizationChanged) {
            sendOnAfterLocaleChangedEvent()
            isLocalizationChanged = false
        }
    }

    private fun sendOnBeforeLocaleChangedEvent() {
        for (changedListener in localeChangedListeners) {
            changedListener.onBeforeLocaleChanged()
        }
    }

    private fun sendOnAfterLocaleChangedEvent() {
        for (listener in localeChangedListeners) {
            listener.onAfterLocaleChanged()
        }
    }

    companion object {
        private val KEY_ACTIVITY_LOCALE_CHANGED = "activity_locale_changed"
    }
}