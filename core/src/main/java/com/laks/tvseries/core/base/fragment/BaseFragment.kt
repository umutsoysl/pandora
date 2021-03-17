package com.laks.tvseries.core.base.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.laks.tvseries.core.BuildConfig
import com.laks.tvseries.core.base.viewmodel.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

abstract class BaseFragment<Q : BaseViewModel>(clazz: KClass<Q>) : Fragment() {
    val baseViewModel: Q by viewModel(clazz)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (BuildConfig.DEBUG) {
            Log.d("className", this.javaClass.simpleName + "")
        }
    }
}