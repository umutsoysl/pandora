package com.laks.tvseries.core.base.activity

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.BuildConfig
import com.google.android.material.appbar.AppBarLayout
import com.laks.tvseries.core.R
import com.laks.tvseries.core.base.viewmodel.BaseViewModel
import com.laks.tvseries.core.cache.ViewModelState
import com.laks.tvseries.core.databinding.ActivityBaseBinding
import com.laks.tvseries.core.loading.MemoryCacheHelper
import com.laks.tvseries.core.loading.LoadingEventObserver
import com.laks.tvseries.core.view.FullyTransparentStatusBar
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
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
    var qnbAppBarLayout: AppBarLayout? = null
    private var qnbNestedScrollView: NestedScrollView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stopKoin()
        setUp()
        if (BuildConfig.DEBUG) {
            Log.d("className", this.javaClass.simpleName + "")
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_base)
        setViewStateId()
        initStatusBar()
        observeLoadingState()
        onClickHeaderBackButton()
    }

    fun <T : ViewDataBinding> inflate(layoutResId: Int): T {
        return DataBindingUtil.inflate(layoutInflater, layoutResId,
                binding.contentLayout, true)
    }

    private fun onClickHeaderBackButton() {
        binding.buttonBack.setOnClickListener {
            onBackPressed()
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

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(modules)
        MemoryCacheHelper.removeCache(classTag)
    }

    private fun setUp() {
        org.koin.core.context.startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@BaseActivity)
            modules(modules)
        }
    }
}