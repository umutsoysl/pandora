package com.laks.tvseries.core.base.activity

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.BuildConfig
import com.laks.tvseries.core.R
import com.laks.tvseries.core.base.viewmodel.BaseViewModel
import com.laks.tvseries.core.cache.ViewModelState
import com.laks.tvseries.core.databinding.ActivityBaseBinding
import com.laks.tvseries.core.loading.MemoryCacheHelper
import com.laks.tvseries.core.loading.LoadingEventObserver
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.unloadKoinModules
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.java.KoinJavaComponent
import kotlin.reflect.KClass

@Suppress("NAME_SHADOWING")
abstract class BaseActivity<Q : BaseViewModel>(clazz: KClass<Q>) : AppCompatActivity() {

    val baseViewModel: Q by viewModel(clazz)
    abstract val modules: List<Module>
    private val loadingStateObserver by lazy { LoadingEventObserver(supportFragmentManager) }
    private lateinit var binding: ActivityBaseBinding
    private val classTag = this.javaClass.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUp()
        if (BuildConfig.DEBUG) {
            Log.d("className", this.javaClass.simpleName + "")
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_base)
        initStatusBar()
        observeLoadingState()
    }

    private fun initStatusBar() {
        supportActionBar?.setDisplayShowTitleEnabled(false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor: View = window.decorView
            val flags = decor.systemUiVisibility
            decor.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
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

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(modules)
        MemoryCacheHelper.removeCache(classTag)
    }

    private fun setUp() {
        org.koin.core.context.startKoin {
            androidLogger(Level.DEBUG)
            modules(modules)
        }
    }
}