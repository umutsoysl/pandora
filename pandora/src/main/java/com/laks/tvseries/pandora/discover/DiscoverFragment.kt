package com.laks.tvseries.pandora.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.laks.tvseries.core.base.fragment.BaseFragment
import com.laks.tvseries.pandora.R
import com.laks.tvseries.pandora.MainViewModel
import com.laks.tvseries.pandora.databinding.FragmentDiscoverBinding

class DiscoverFragment: BaseFragment<MainViewModel>(MainViewModel::class) {

    private lateinit var binding: FragmentDiscoverBinding

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_discover, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = baseViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        val TAG: String = DiscoverFragment::class.java.simpleName
        fun newInstance() = DiscoverFragment()
    }
}