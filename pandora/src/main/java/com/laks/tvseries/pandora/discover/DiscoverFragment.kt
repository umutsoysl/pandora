package com.laks.tvseries.pandora.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.laks.tvseries.core.base.fragment.BaseFragment
import com.laks.tvseries.core.data.model.MovieModel
import com.laks.tvseries.pandora.R
import com.laks.tvseries.pandora.MainViewModel
import com.laks.tvseries.pandora.databinding.FragmentDiscoverBinding

class DiscoverFragment: BaseFragment<MainViewModel>(MainViewModel::class), DiscoverMovieListItemOnClickListener {

    private lateinit var binding: FragmentDiscoverBinding
    private lateinit var adapter: DiscoverMovieListAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_discover, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = baseViewModel

        setAdapter()
        bindingViewModel()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseViewModel.getDiscoverMovieList(1)
    }

    private fun bindingViewModel() {
        baseViewModel.discoverMovieList.observe(requireActivity(), Observer {
            adapter.submitList(it.results)
            adapter.notifyDataSetChanged()
            binding.rootRelativeView.requestLayout()
            binding.invalidateAll()
            binding.executePendingBindings()
        })
    }

    private fun setAdapter() {
        adapter = DiscoverMovieListAdapter(context = requireActivity(), clickListener = this)
        var layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerScheduleList.layoutManager = layoutManager
        binding.recyclerScheduleList.adapter = adapter

    }


    companion object {
        val TAG: String = DiscoverFragment::class.java.simpleName
        fun newInstance() = DiscoverFragment()
    }

    override fun discoverMovieListItemOnClickListener(scheduleInfo: MovieModel) {
        TODO("Not yet implemented")
    }
}