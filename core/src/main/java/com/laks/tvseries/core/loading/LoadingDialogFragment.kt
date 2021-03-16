package com.laks.tvseries.core.loading

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.laks.tvseries.core.R

class LoadingDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false
        return inflater.inflate(R.layout.loading_view, container, false)
    }

    override fun show(manager: FragmentManager, tag: String?) {
        tag?.let {
            if (manager.findFragmentByTag(it) != null) {
                return
            }
        }
        try {
            manager.executePendingTransactions()
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            ft.commitNowAllowingStateLoss()
        } catch (ignored: IllegalStateException) {
        }
    }
}