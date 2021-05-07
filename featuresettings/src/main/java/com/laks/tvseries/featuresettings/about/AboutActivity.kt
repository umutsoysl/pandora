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
import com.laks.tvseries.core.base.activity.BaseActivity
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

    private var aboutHtml = "<h5>Pandora; Movie Review Application Bringing Thousands of Movies, TV Series and Actors Together with Users</h5><br/>" +
            "<p>Pandora; It gives you access to thousands of movies and series from multiple categories, including trending, popular, top rated, now playing, recommendations and upcoming.</p>\n" +
            "<p><br/>It allows you to search among millions of movies and TV shows.</p><br/>" +
            "<p>Pandora; recommends movies to you according to your mood.</p></br>" +
            "<p>No need to try many other movies apps, Pandora will be anough for all.</p><br/>" +
            "<p>You can enjoy Pandora and its pleasant experience from your Android devices.</p>" +
            "<br/><h3>Support</h3><br/>" +
            "<p>We always appreciate your feedback. We would like continue improving Pandora with your help.</p><br/>" +
            "<p>Please send us your feedback and questions.</p>" +
            "<p><a href=\"https://play.google.com/store/apps/details?id=com.laks.tvseries.pandora\" target=\"_blank\">Visit google play</a></p>"
}
