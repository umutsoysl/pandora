package com.laks.tvseries.core.loading

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.laks.tvseries.core.common.CoreActivity

class LoadingEventObserver(private val fm: FragmentManager) {

    private val loadingFragment by lazy { LoadingDialogFragment() }

    fun setup(
        loadingState: LiveData<MutableMap<String, Boolean>>,
        lifeCycleOwner: LifecycleOwner,
        stateId: String?
    ) {
        loadingState.observe(lifeCycleOwner) {
            if (LoadingErrorStateHelper.aliveLoading(stateId)) {
                if (lifeCycleOwner is CoreActivity && !loadingFragment.isAdded) {
                    loadingFragment.show(lifeCycleOwner.supportFragmentManager, LoadingEventObserver::class.simpleName)
                } else {
                    show()
                }
            } else {
                hide()
            }
        }
    }

    private fun show() {
        if (!loadingFragment.isAdded) {
            loadingFragment.show(fm, LoadingEventObserver::class.simpleName)
        }
    }

    private fun hide() {
        if (loadingFragment.isAdded) {
            loadingFragment.dismissAllowingStateLoss()
        }
    }
}