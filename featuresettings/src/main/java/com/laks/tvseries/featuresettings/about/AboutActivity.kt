package com.laks.tvseries.featuresettings.about

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.laks.tvseries.core.base.activity.BaseActivity
import com.laks.tvseries.core.cache.MemoryCache
import com.laks.tvseries.core.data.PandoraActivities
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.featuresettings.R
import com.laks.tvseries.featuresettings.SettingsViewModel
import com.laks.tvseries.featuresettings.databinding.ActivityAboutBinding
import com.laks.tvseries.featuresettings.settingsDIModule
import org.koin.core.module.Module

class AboutActivity : BaseActivity<SettingsViewModel>(SettingsViewModel::class) {

    override val modules: List<Module>
        get() = listOf(settingsDIModule)
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(R.layout.activity_about)
        binding.viewModel = baseViewModel
        binding.lifecycleOwner = this
        setToolbarTitle(resources.getString(com.laks.tvseries.core.R.string.about_pandora))
        removeHeaderSearchButton()
        textLink()
        createBannerAds()
    }

    private fun createBannerAds() {
        val fragMan: FragmentManager? = supportFragmentManager
        val fragTransaction: FragmentTransaction = fragMan!!.beginTransaction()

        val myFrag: Fragment = (Class.forName(PandoraActivities.pandoraBannerAdsFragmentClassName).newInstance() as Fragment)
        fragTransaction.replace(binding.layoutAds.id, myFrag, "pandoraFragmentAdsAboutPage")
        fragTransaction.commit()
    }

    private fun textLink() {
        var sequence: CharSequence = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(aboutHtml, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(aboutHtml)
        }

        val strBuilder = SpannableStringBuilder(sequence)
        val urls: Array<URLSpan> = strBuilder.getSpans(0, sequence.length, URLSpan::class.java)
        for (span in urls) {
            makeLinkClickable(strBuilder, span)
        }

        binding.labelAbout.text = strBuilder
        binding.labelAbout.linksClickable = true
        binding.labelAbout.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun makeLinkClickable(strBuilder: SpannableStringBuilder, span: URLSpan?) {
        val start = strBuilder.getSpanStart(span)
        val end = strBuilder.getSpanEnd(span)
        val flags = strBuilder.getSpanFlags(span)
        val clickable: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val appPackageName = packageName
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
                } catch (_: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                        )
                    )
                }
            }
        }
        strBuilder.setSpan(clickable, start, end, flags)
        strBuilder.removeSpan(span)
    }

    private var aboutHtml = MemoryCache.cache.findMemoryCacheValueAny(GlobalConstants.FIREBASE_ABOUT_TABLE).let { it.toString() }
}
