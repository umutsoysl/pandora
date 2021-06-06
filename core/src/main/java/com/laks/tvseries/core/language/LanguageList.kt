package com.laks.tvseries.core.language

import android.content.Context
import com.laks.tvseries.core.R

fun getLanguageList(context: Context) : ArrayList<LanguageModel> {
    val languageList: ArrayList<LanguageModel>? = ArrayList()
    val enModel = LanguageModel(
        context.resources.getString(R.string.english),
        context.resources.getString(R.string.english_en),
        false,
        "en",
        "US",
        R.string.english
    )
    val deModel = LanguageModel(
        context.resources.getString(R.string.german),
        context.resources.getString(R.string.german_en),
        false,
        "de",
        "DE",
        R.string.german
    )
    val frModel = LanguageModel(
        context.resources.getString(R.string.french),
        context.resources.getString(R.string.french_en),
        false,
        "fr",
        "FR",
        R.string.french
    )
    val hiModel = LanguageModel(
        context.resources.getString(R.string.hindi),
        context.resources.getString(R.string.hindi_en),
        false,
        "hi",
        "IN",
        R.string.hindi
    )
    val trModel = LanguageModel(
        context.resources.getString(R.string.turkish),
        context.resources.getString(R.string.turkish_en),
        false,
        "tr",
        "TR",
        R.string.turkish
    )
    val ruModel = LanguageModel(
        context.resources.getString(R.string.russian),
        context.resources.getString(R.string.russian_en),
        false,
        "ru",
        "RU",
        R.string.russian
    )
    val zhModel = LanguageModel(
        context.resources.getString(R.string.china),
        context.resources.getString(R.string.china_en),
        false,
        "zh",
        "CN",
        R.string.china
    )

    val ptModel = LanguageModel(
        context.resources.getString(R.string.portuguese),
        context.resources.getString(R.string.portuguese_en),
        false,
        "pt",
        "PT",
        R.string.portuguese
    )
    val jaModel = LanguageModel(
        context.resources.getString(R.string.japanese),
        context.resources.getString(R.string.japanese_en),
        false,
        "ja",
        "JP",
        R.string.japanese
    )
    val arModel = LanguageModel(
        context.resources.getString(R.string.arabic),
        context.resources.getString(R.string.arabic_en),
        false,
        "ar",
        "SA",
        R.string.arabic
    )
    val esModel = LanguageModel(
        context.resources.getString(R.string.spanish),
        context.resources.getString(R.string.spanish_en),
        false,
        "es",
        "ES",
        R.string.spanish
    )
    val itModel = LanguageModel(
        context.resources.getString(R.string.italian),
        context.resources.getString(R.string.italian_en),
        false,
        "it",
        "IT",
        R.string.italian
    )

    val csModel = LanguageModel(
            context.resources.getString(R.string.czech),
            context.resources.getString(R.string.china_en),
            false,
            "cs",
            "CZ",
            R.string.czech
    )

    val grModel = LanguageModel(
            context.resources.getString(R.string.greek),
            context.resources.getString(R.string.greek_en),
            false,
            "el",
            "GR",
            R.string.greek
    )

    val hrModel = LanguageModel(
            context.resources.getString(R.string.croatian),
            context.resources.getString(R.string.croatian_en),
            false,
            "hr",
            "HR",
            R.string.croatian
    )

    languageList!!.add(enModel)
    languageList.add(deModel)
    languageList.add(frModel)
    languageList.add(hiModel)
    languageList.add(trModel)
    languageList.add(ruModel)
    languageList.add(zhModel)
    languageList.add(arModel)
    languageList.add(itModel)
    languageList.add(ptModel)
    languageList.add(jaModel)
    languageList.add(esModel)
    languageList.add(csModel)
    languageList.add(grModel)
    languageList.add(hrModel)

    return languageList
}

data class LanguageModel (
    val title: String ?= null,
    val desc: String ?= null,
    var isSelect: Boolean ?= false,
    val code: String ?= null,
    val countryCode: String ?= null,
    val res: Int? = null
)