package com.laks.tvseries.pandora.favorite.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.laks.tvseries.core.base.fragment.BaseFragment
import com.laks.tvseries.pandora.MainViewModel
import com.laks.tvseries.pandora.R
import com.laks.tvseries.pandora.databinding.FragmentTabsFavoriteTvBinding

class FavoriteTvShowsFragment: BaseFragment<MainViewModel>(MainViewModel::class){

    private lateinit var binding: FragmentTabsFavoriteTvBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tabs_favorite_tv, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = baseViewModel

        return binding.root
    }
}