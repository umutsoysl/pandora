package com.laks.tvseries.pandora.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.laks.tvseries.core.base.fragment.BaseFragment
import com.laks.tvseries.core.data.model.MovieModel
import com.laks.tvseries.pandora.MainActivity
import com.laks.tvseries.pandora.MainViewModel
import com.laks.tvseries.pandora.R
import com.laks.tvseries.pandora.databinding.FragmentHomeBinding


class HomeFragment: BaseFragment<MainViewModel>(MainViewModel::class), HomeMovieListItemOnClickListener{

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomeMovieListAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        baseViewModel.repository?.classTag = (activity as MainActivity).javaClass.canonicalName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
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
        adapter = HomeMovieListAdapter(context = requireActivity(), clickListener = this)
        var layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerScheduleList.layoutManager = layoutManager
        binding.recyclerScheduleList.adapter = adapter

    }

    companion object {
        val TAG: String = HomeFragment::class.java.simpleName
        fun newInstance() = HomeFragment()
    }

    override fun homeMovieListItemOnClickListener(scheduleInfo: MovieModel) {
        TODO("Not yet implemented")
    }
}