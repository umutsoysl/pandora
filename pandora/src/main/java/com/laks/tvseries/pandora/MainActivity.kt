package com.laks.tvseries.pandora

import android.app.Activity
import android.os.Bundle
import com.laks.tvseries.core.loading.LoadingDialogFragment

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // LoadingDialogFragment().show(supportFragmentManager, "Example")
    }
}