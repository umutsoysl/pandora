package com.laks.tvseries.pandora.discover

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.laks.tvseries.core.base.fragment.BaseFragment
import com.laks.tvseries.core.cache.MemoryCache
import com.laks.tvseries.core.common.media.GenreListItemOnClickListener
import com.laks.tvseries.core.common.media.MediaListItemAdapter
import com.laks.tvseries.core.common.media.MediaListItemOnClickListener
import com.laks.tvseries.core.data.PandoraActivities
import com.laks.tvseries.core.data.model.Genre
import com.laks.tvseries.core.data.model.MediaType
import com.laks.tvseries.core.data.model.MovieModel
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.pandora.MainViewModel
import com.laks.tvseries.pandora.R
import com.laks.tvseries.pandora.databinding.FragmentDiscoverBinding
import com.laks.tvseries.pandora.discover.genre.GenreAdapter

class DiscoverFragment: BaseFragment<MainViewModel>(MainViewModel::class), MediaListItemOnClickListener, GenreListItemOnClickListener {

    private lateinit var binding: FragmentDiscoverBinding
    private lateinit var adapterMovie: MediaListItemAdapter
    private lateinit var adapterTV: MediaListItemAdapter
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var genreTvAdapter: GenreAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createBannerAds()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_discover, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = baseViewModel

        setAdapterMovie()
        setAdapterTV()
        setGenreAdapter()
        goSearchScreen()

        bindingViewModel()

        return binding.root
    }

    private fun createBannerAds() {
        val fragMan: FragmentManager? = childFragmentManager
        val fragTransaction: FragmentTransaction = fragMan!!.beginTransaction()

        val myFrag: Fragment = (Class.forName(PandoraActivities.pandoraBannerAdsFragmentClassName).newInstance() as Fragment)
        fragTransaction.replace(binding.layoutAds.id, myFrag, "pandoraFragmentAdsDiscoverMovie")
        fragTransaction.commit()
    }

    private fun bindingViewModel() {
        baseViewModel.discoverMovieList.observe(requireActivity(), Observer {
            adapterMovie.submitList(it.results)
            adapterMovie.notifyDataSetChanged()
            binding.rootRelativeView.requestLayout()
            binding.invalidateAll()
        })

        baseViewModel.discoverTVList.observe(requireActivity(), Observer {
            adapterTV.submitList(it.results)
            adapterTV.notifyDataSetChanged()
            binding.rootRelativeView.requestLayout()
            binding.invalidateAll()
        })

        baseViewModel.genreMovieList.observe(requireActivity(), Observer {
            genreAdapter.submitList(it.genres)
            genreAdapter.notifyDataSetChanged()
            binding.recyclerMovieGenreList.requestLayout()
            binding.invalidateAll()
        })

        baseViewModel.genreTvList.observe(requireActivity(), Observer {
            genreTvAdapter.submitList(it.genres)
            genreTvAdapter.notifyDataSetChanged()
            binding.recyclerTvGenreList.requestLayout()
            binding.invalidateAll()
        })

        binding.buttonMoreMovie.setOnClickListener {
            MemoryCache.cache.setMemoryCacheValue(GlobalConstants.DISCOVER_MEDIA_TITLE, resources.getString(R.string.movies))
            MemoryCache.cache.setMemoryCacheValue(GlobalConstants.DISCOVER_MEDIA_TYPE, MediaType.movie)
            startActivity(Intent(requireActivity(), DiscoverMediaListActivity::class.java))
        }

        binding.buttonMoreTV.setOnClickListener {
            MemoryCache.cache.setMemoryCacheValue(GlobalConstants.DISCOVER_MEDIA_TITLE, resources.getString(R.string.tv_shows))
            MemoryCache.cache.setMemoryCacheValue(GlobalConstants.DISCOVER_MEDIA_TYPE, MediaType.tv)
            startActivity(Intent(requireActivity(), DiscoverMediaListActivity::class.java))
        }
    }

    private fun goSearchScreen() {
        binding.searchBox.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW).setClassName(requireActivity(), PandoraActivities.searchActivityClassName)
            startActivity(intent)
        }
    }

    private fun setAdapterMovie() {
        adapterMovie = MediaListItemAdapter(context = requireActivity(), clickListener = this)
        var layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerDiscoverMovieList.layoutManager = layoutManager
        binding.recyclerDiscoverMovieList.adapter = adapterMovie
    }

    private fun setAdapterTV() {
        adapterTV = MediaListItemAdapter(context = requireActivity(), clickListener = this)
        var layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerDiscoverTvList.layoutManager = layoutManager
        binding.recyclerDiscoverTvList.adapter = adapterTV
    }

    private fun setGenreAdapter() {
        val layoutManager = FlexboxLayoutManager(requireContext())
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER

        genreAdapter = GenreAdapter(requireActivity(), this, isMovie = true)
        binding.recyclerMovieGenreList.layoutManager = layoutManager
        binding.recyclerMovieGenreList.adapter = genreAdapter

        val layoutManagerTv = FlexboxLayoutManager(requireContext())
        layoutManagerTv.flexDirection = FlexDirection.ROW
        layoutManagerTv.justifyContent = JustifyContent.CENTER

        genreTvAdapter = GenreAdapter(requireActivity(), this, isMovie = false)
        binding.recyclerTvGenreList.layoutManager = layoutManagerTv
        binding.recyclerTvGenreList.adapter = genreTvAdapter
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseViewModel.getDiscoverMovieList(1)
        baseViewModel.getDiscoverTVList(1)
        baseViewModel.getMovieGenreList()
        baseViewModel.getTvGenreList()
    }

    companion object {
        val TAG: String = DiscoverFragment::class.java.simpleName
        fun newInstance() = DiscoverFragment()
    }

    override fun mediaListItemOnClickListener(scheduleInfo: MovieModel) {
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_TYPE, if(scheduleInfo.isMovie) MediaType.movie else MediaType.tv)
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_ID, scheduleInfo.id!!)
        var intent = Intent(Intent.ACTION_VIEW).setClassName(requireActivity(), PandoraActivities.movieDetailActivityClassName)
        startActivity(intent)
    }

    override fun genreListItemOnClickListener(genre: Genre) {
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.DISCOVER_MEDIA_TITLE,  genre.name)
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.DISCOVER_MEDIA_TYPE, if(genre.isMovie) MediaType.movie else MediaType.tv)
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.GENRE_ID, genre.id.toString())
        startActivity(Intent(requireActivity(), DiscoverMediaListActivity::class.java))
    }
}