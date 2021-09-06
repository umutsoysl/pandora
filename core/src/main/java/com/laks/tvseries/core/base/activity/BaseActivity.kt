package com.laks.tvseries.core.base.activity

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.BuildConfig
import com.laks.tvseries.core.R
import com.laks.tvseries.core.base.viewmodel.BaseViewModel
import com.laks.tvseries.core.cache.ViewModelState
import com.laks.tvseries.core.data.PandoraActivities
import com.laks.tvseries.core.data.model.HeaderIconType
import com.laks.tvseries.core.data.model.PersonInfo
import com.laks.tvseries.core.databinding.ActivityBaseBinding
import com.laks.tvseries.core.language.LocalizationLanguageManager
import com.laks.tvseries.core.language.OnLocaleLanguageChangedListener
import com.laks.tvseries.core.loading.MemoryCacheHelper
import com.laks.tvseries.core.loading.LoadingEventObserver
import com.laks.tvseries.core.view.FullyTransparentStatusBar
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.java.KoinJavaComponent
import java.util.*
import kotlin.reflect.KClass

@Suppress("NAME_SHADOWING")
abstract class BaseActivity<Q : BaseViewModel>(clazz: KClass<Q>) : AppCompatActivity(),
    OnLocaleLanguageChangedListener {

    val baseViewModel: Q by viewModel(clazz)
    abstract val modules: List<Module>
    private val loadingStateObserver by lazy { LoadingEventObserver(supportFragmentManager) }
    private lateinit var binding: ActivityBaseBinding
    private val classTag = this.javaClass.canonicalName
    private var qnbNestedScrollView: NestedScrollView? = null
    private var headerIconItemClickListener: HeaderIconItemClickListener? = null
    private val localizationManager = LocalizationLanguageManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        localizationManager.addOnLocaleChangedListener(this)
        localizationManager.onCreate(savedInstanceState)
        super.onCreate(savedInstanceState)

        unloadKoinModules(modules)
        loadKoinModules(modules)

        if (BuildConfig.DEBUG) {
            Log.d("className", this.javaClass.simpleName + "")
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_base)
        setViewStateId()
        initStatusBar()
        observeLoadingState()
        onClickHeaderBackButton()
        onClickHeaderSearchButton()
    }

    fun <T : ViewDataBinding> inflate(layoutResId: Int): T {
        return DataBindingUtil.inflate(layoutInflater, layoutResId,
                binding.contentLayout, true)
    }

    fun removeHeaderSearchButton() {
        binding.buttonSearch.visibility = View.INVISIBLE
    }

    fun showFilterButton(handler: HeaderIconItemClickListener) {
        headerIconItemClickListener = handler
        binding.buttonFilter.visibility = View.VISIBLE
    }

    private fun onClickHeaderBackButton() {
        binding.buttonBack.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun onClickHeaderSearchButton() {
        binding.buttonSearch.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).setClassName(this, PandoraActivities.searchActivityClassName)
            startActivity(intent)
        }

        binding.buttonFilter.setOnClickListener {
            headerIconItemClickListener?.headerIconClickListener(HeaderIconType.filter)
        }
    }

    @JvmName("setToolbarTitle")
    fun setToolbarTitle(title: String) {
        binding.labelTitle.text = title
    }

    @JvmName("setToolbarVisible")
    fun setToolbarVisible(isVisible: Boolean) {
        binding.constraintLayout.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun setViewStateId() {
        baseViewModel.repository?.classTag = classTag
    }

    private fun initStatusBar() {
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val fullyTransparentStatusBar = FullyTransparentStatusBar(this, window)
        fullyTransparentStatusBar.setFullTransparentStatus()
    }

    fun openKeyboard(view: View) {
        view.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        view.requestLayout()
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        view?.let { v ->
            val im = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            im?.let { it.hideSoftInputFromWindow(v.windowToken, 0) }
        }
    }

    private fun observeLoadingState() {
        var viewModelState = MemoryCacheHelper.findLoadingCache(classTag)
        if (viewModelState == null) {
            val vms by KoinJavaComponent.inject(ViewModelState::class.java)
            viewModelState = vms
            MemoryCacheHelper.setLoadingCache(classTag, viewModelState)
        }

        loadingStateObserver.setup(viewModelState.loading, this, classTag)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(localizationManager.attachBaseContext(newBase!!))
    }

    override fun onResume() {
        super.onResume()
        localizationManager.onResume(this)
    }

    override fun getApplicationContext(): Context {
        return localizationManager.getApplicationContext(super.getApplicationContext())
    }

    override fun getResources(): Resources {
        return localizationManager.getResources(super.getResources())
    }

    fun setLanguage(language: String, country: String) {
        localizationManager.setLanguage(this, language, country)
    }

    fun setLanguage(language: String) {
        localizationManager.setLanguage(this, language)
    }

    fun setDefaultLanguage(language: String, country: String) {
        localizationManager.setDefaultLanguage(language, country)
    }

    fun setDefaultLanguage(language: String) {
        localizationManager.setDefaultLanguage(language)
    }

    fun getCurrentLanguage(): Locale {
        return localizationManager.getLanguage(this)
    }

    override fun onBeforeLocaleChanged() {}

    override fun onAfterLocaleChanged() {}


    override fun onDestroy() {
        unloadKoinModules(modules)
        MemoryCacheHelper.removeCache(classTag)
        super.onDestroy()
    }

    private fun setUp() {
        org.koin.core.context.startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@BaseActivity)
            modules(modules)
        }
    }
}

interface HeaderIconItemClickListener {
    fun headerIconClickListener(headerIconType: String)
}