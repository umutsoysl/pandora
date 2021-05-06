package com.laks.tvseries.pandora.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.laks.tvseries.core.base.fragment.BaseFragment
import com.laks.tvseries.core.dao.MediaDao
import com.laks.tvseries.core.data.PandoraActivities
import com.laks.tvseries.core.db.PandoraDatabase
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
        clickListener()

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

    private fun clickListener() {
        val db: MediaDao? = PandoraDatabase.getDatabase(requireActivity())?.mediaDao()
        binding.removeMovieList.setOnClickListener {
            val builder = AlertDialog.Builder(requireActivity(), com.laks.tvseries.core.R.style.PandoraAlertDialogTheme)
            builder.setTitle(resources.getString(R.string.alert))
            builder.setMessage(resources.getString(R.string.movie_remove_watch_list_dsc))
            builder.setPositiveButton(resources.getString(R.string.delete)) { _, _ ->
                db?.deleteAll(isMovie = true)
            }
            builder.setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.cancel()
            }
            builder.show()
        }

        binding.removeTvList.setOnClickListener {
            val builder = AlertDialog.Builder(requireActivity(), com.laks.tvseries.core.R.style.PandoraAlertDialogTheme)
            builder.setTitle(resources.getString(R.string.alert))
            builder.setMessage(resources.getString(R.string.tv_remove_watch_list_dsc))
            builder.setPositiveButton(resources.getString(R.string.delete)) { _, _ ->
                db?.deleteAll(isMovie = false)
            }
            builder.setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.cancel()
            }
            builder.show()
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