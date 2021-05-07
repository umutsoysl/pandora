package com.laks.tvseries.featuresettings.language

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.laks.tvseries.core.base.activity.BaseActivity
import com.laks.tvseries.core.cache.MemoryCache
import com.laks.tvseries.core.common.language.LanguageOnItemClickListener
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.core.global.StoreShared
import com.laks.tvseries.core.language.LanguageModel
import com.laks.tvseries.core.language.getLanguageList
import com.laks.tvseries.featuresettings.R
import com.laks.tvseries.featuresettings.SettingsViewModel
import com.laks.tvseries.featuresettings.databinding.ActivityLanguageBinding
import com.laks.tvseries.featuresettings.settingsDIModule
import org.koin.core.module.Module

class LanguageActivity : BaseActivity<SettingsViewModel>(SettingsViewModel::class), LanguageOnItemClickListener {

    override val modules: List<Module>
        get() = listOf(settingsDIModule)
    private lateinit var binding: ActivityLanguageBinding
    private var adapter: LanguageListViewAdapter? = null
    private var languageList: ArrayList<LanguageModel>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(R.layout.activity_language)
        binding.viewModel = baseViewModel
        binding.lifecycleOwner = this
        setToolbarTitle(resources.getString(com.laks.tvseries.core.R.string.language))
        removeHeaderSearchButton()

        createLanguageList()
    }

    private fun createLanguageList() {
        languageList = getLanguageList(this)

        var code = StoreShared(this).getStringValue(GlobalConstants.SHARED_LANGUAGE_CODE)
        if (code.isNullOrEmpty()) code = "en"

        for (item in languageList!!) {
            if (item.code == code) {
                item.isSelect = true
            }
        }

        adapter = LanguageListViewAdapter(this, languageList!!, this)
        binding.recyclerViewLanguage.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewLanguage.adapter = adapter
        adapter!!.notifyDataSetChanged()
        binding.layoutSearchParent.requestLayout()
        binding.layoutSearchParent.invalidate()
    }

    override fun languageOnItemClickListener(model: LanguageModel) {
        setLanguage(model.code!!, model.countryCode!!)
        StoreShared(this).setIntKeyValue(GlobalConstants.SHARED_LANGUAGE, model.res!!)
        StoreShared(this).setStringKeyValue(GlobalConstants.SHARED_LANGUAGE_CODE, model.code!!)
        StoreShared(this).setStringKeyValue(GlobalConstants.SHARED_LANGUAGE_COUNTRY, model.countryCode!!)
        StoreShared(this).setStringKeyValue(GlobalConstants.SHARED_LANGUAGE_WITH_CODE_COUNTRY, "${model.code!!}-${model.countryCode!!}")
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.SHARED_LANGUAGE_WITH_CODE_COUNTRY, "${model.code!!}-${model.countryCode!!}")
        onBackPressed()
    }

}