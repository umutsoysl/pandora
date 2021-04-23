package com.laks.tvseries.pandora.favorite.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.laks.tvseries.core.base.fragment.BaseFragment
import com.laks.tvseries.core.common.media.DBFavoriteListItemClickListener
import com.laks.tvseries.core.data.db.DBMediaEntity
import com.laks.tvseries.pandora.MainViewModel
import com.laks.tvseries.pandora.R
import com.laks.tvseries.pandora.databinding.FragmentTabsFavoriteMovieBinding
import com.laks.tvseries.pandora.favorite.FavoriteMediaListAdapter

class FavoriteMovieFragment: BaseFragment<MainViewModel>(MainViewModel::class), DBFavoriteListItemClickListener{

    private lateinit var binding: FragmentTabsFavoriteMovieBinding
    private lateinit var adapter: FavoriteMediaListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tabs_favorite_movie, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = baseViewModel

        baseViewModel.getDBWatchList(requireActivity(), true)
        setAdapter()
        bindingViewModel()

        return binding.root
    }

    private fun bindingViewModel() {
        baseViewModel.movieWatchList.observe(requireActivity(), Observer {
            if (!it.isNullOrEmpty()) {
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
                binding.rootRelativeView.requestLayout()
                binding.rootRelativeView.invalidate()
            }
        })
    }

    private fun setAdapter() {
        adapter = FavoriteMediaListAdapter(context = requireActivity(), clickListener = this)
        var layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerMovieList.layoutManager = layoutManager
        binding.recyclerMovieList.adapter = adapter
    }

    override fun mediaListItemOnClickListener(cast: DBMediaEntity) {
        TODO("Not yet implemented")
    }
}