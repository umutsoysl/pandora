package com.laks.tvseries.core.base.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.laks.tvseries.core.BuildConfig
import com.laks.tvseries.core.base.viewmodel.BaseViewModel
import com.laks.tvseries.core.cache.ViewModelState
import com.laks.tvseries.core.loading.MemoryCacheHelper
import com.laks.tvseries.core.loading.LoadingEventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent
import kotlin.reflect.KClass

abstract class BaseFragment<Q : BaseViewModel>(clazz: KClass<Q>) : Fragment() {
    val baseViewModel: Q by viewModel(clazz)

    private val classTag = this.javaClass.canonicalName
    private val loadingStateObserver by lazy { activity?.supportFragmentManager?.let { LoadingEventObserver(it) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (BuildConfig.DEBUG) {
            Log.d("className", this.javaClass.simpleName + "")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setViewStateId()
        observeLoadingState()
    }

    private fun setViewStateId() {
        baseViewModel?.baseRepository?.classTag = classTag
    }

    private fun observeLoadingState() {
        var viewModelState = MemoryCacheHelper.findLoadingCache(classTag)
        if (viewModelState == null) {
            val vms by KoinJavaComponent.inject(ViewModelState::class.java)
            viewModelState = vms
            MemoryCacheHelper.setLoadingCache(classTag, viewModelState)
        }

        loadingStateObserver?.setup(viewModelState.loading, this, classTag)
    }

    override fun onDestroy() {
        super.onDestroy()
        MemoryCacheHelper.removeCache(classTag)
    }
}