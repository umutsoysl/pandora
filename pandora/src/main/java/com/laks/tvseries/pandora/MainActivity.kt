package com.laks.tvseries.pandora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.laks.tvseries.core.loading.LoadingDialogFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LoadingDialogFragment().show(supportFragmentManager, "Example")
    }
}