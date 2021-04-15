package com.laks.tvseries.pandora.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.laks.tvseries.core.base.fragment.BaseFragment
import com.laks.tvseries.core.data.PandoraActivities
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.core.global.StoreShared
import com.laks.tvseries.pandora.R
import com.laks.tvseries.pandora.databinding.FragmentSettingsBinding
import com.laks.tvseries.pandora.MainViewModel

class SettingsFragment: BaseFragment<MainViewModel>(MainViewModel::class) {

    private lateinit var binding: FragmentSettingsBinding

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = baseViewModel

        initLanguage()
        goLanguageScreen()

        return binding.root
    }

    private fun initLanguage() {
        var resLanguage = StoreShared(requireActivity()).getIntValue(GlobalConstants.SHARED_LANGUAGE)
        if (resLanguage == 0) {
            binding.labelLanguage.text = resources.getString(R.string.english)
        } else {
            binding.labelLanguage.text = resources.getString(resLanguage!!)
        }
    }

    private fun goLanguageScreen() {
        binding.languageBox.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW).setClassName(requireActivity(), PandoraActivities.languageActivityClassName)
            startActivity(intent)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        val TAG: String = SettingsFragment::class.java.simpleName
        fun newInstance() = SettingsFragment()
    }
}