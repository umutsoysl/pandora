package com.laks.tvseries.pandora.discover

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.laks.tvseries.core.base.activity.BaseActivity
import com.laks.tvseries.core.base.activity.HeaderIconItemClickListener
import com.laks.tvseries.core.cache.MemoryCache
import com.laks.tvseries.core.common.media.GenreListItemOnClickListener
import com.laks.tvseries.core.common.media.MediaListItemOnClickListener
import com.laks.tvseries.core.data.PandoraActivities
import com.laks.tvseries.core.data.model.*
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.pandora.MainViewModel
import com.laks.tvseries.pandora.R
import com.laks.tvseries.pandora.databinding.ActivityDiscoverMediaListBinding
import com.laks.tvseries.pandora.databinding.DiscoverSheetFilterBinding
import com.laks.tvseries.pandora.di.homeDIModule
import com.laks.tvseries.pandora.discover.genre.GenreCheckListAdapter
import org.koin.core.module.Module

class DiscoverMediaListActivity: BaseActivity<MainViewModel>(MainViewModel::class), MediaListItemOnClickListener, HeaderIconItemClickListener, GenreListItemOnClickListener {
    override val modules: List<Module>
        get() = listOf(homeDIModule)
    private lateinit var binding: ActivityDiscoverMediaListBinding
    private lateinit var adapter: DiscoverMediaListAdapter
    private var page = 1
    private var movieList = ArrayList<MovieModel>()
    private var mediaType = MediaType.movie
    private var genre = ""
    private var isFilterApplyClick = false
    private var genreList: List<Genre>? = arrayListOf()
    var filterGenreList: HashMap<Long, String>? = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(R.layout.activity_discover_media_list)
        binding.viewModel = baseViewModel
        binding.lifecycleOwner = this
        showFilterButton(this)
        val title = MemoryCache.cache.findMemoryCacheValueAny(GlobalConstants.DISCOVER_MEDIA_TITLE)
        setToolbarTitle(title.let { title.toString() })

        mediaType = MemoryCache.cache.findMemoryCacheValueAny(GlobalConstants.DISCOVER_MEDIA_TYPE) as String
        genre = MemoryCache.cache.findMemoryCacheValueAny(GlobalConstants.GENRE_ID) as String

        if (genre.isNullOrEmpty()) {
            genre = ""
        } else {
            filterGenreList?.put(genre.toLong(), "genre")
        }

        setAdapter()
        bindingViewModel()
        getDiscoverData()
        getGenreList()
    }

    private fun bindingViewModel() {
        baseViewModel.discoverMovieList.observe(this, Observer {
            if (isFilterApplyClick) {
                movieList = it.results!!
            } else {
                it.results?.let { it1 -> movieList.addAll(it1) }
            }
            adapter.submitList(movieList)
            adapter.notifyDataSetChanged()
            baseViewModel.shimmerVisible.postValue(false)
            binding.rootRelativeView.requestLayout()
            binding.invalidateAll()
        })

        baseViewModel.discoverTVList.observe(this, Observer {
            if (isFilterApplyClick) {
                movieList = it.results!!
            } else {
                it.results?.let { it1 -> movieList.addAll(it1) }
            }
            adapter.submitList(movieList)
            adapter.notifyDataSetChanged()
            baseViewModel.shimmerVisible.postValue(false)
            binding.rootRelativeView.requestLayout()
            binding.invalidateAll()
        })

        binding.buttonMore.setOnClickListener {
            isFilterApplyClick = false
            baseViewModel.shimmerVisible.postValue(true)
            page += 1
            getDiscoverData()
        }

        baseViewModel.genreCheckList.observe(this, Observer {
            genreList = it.genres
        })
    }

    private fun getDiscoverData() {
        when(mediaType) {
            MediaType.movie -> baseViewModel.getDiscoverMovieList(page, genre)
            MediaType.tv -> baseViewModel.getDiscoverTVList(page, genre)
        }
    }

    private fun getGenreList() {
        when(mediaType) {
            MediaType.movie -> baseViewModel.getMovieGenreList()
            MediaType.tv -> baseViewModel.getTvGenreList()
        }
    }

    private fun setAdapter() {
        adapter = DiscoverMediaListAdapter(context = this, clickListener = this)
        var layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerMovieList.layoutManager = layoutManager
        binding.recyclerMovieList.adapter = adapter
    }

    override fun mediaListItemOnClickListener(media: MovieModel) {
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_TYPE, mediaType)
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_ID, media.id!!)
        var intent = Intent(Intent.ACTION_VIEW).setClassName(this, PandoraActivities.movieDetailActivityClassName)
        startActivity(intent)
    }

    override fun headerIconClickListener(headerIconType: String) {
        when(headerIconType) {
            HeaderIconType.filter -> showFilterDialog()
        }
    }

    private fun showFilterDialog() {
        var sortByStr = baseViewModel.sortBy.value
        val dialog = BottomSheetDialog(this@DiscoverMediaListActivity)
        val bindingSheet = DataBindingUtil.inflate<DiscoverSheetFilterBinding>(
                layoutInflater,
                R.layout.discover_sheet_filter,
                null,
                false
        )

        val genreAdapter = GenreCheckListAdapter(context = this@DiscoverMediaListActivity, clickListener = this@DiscoverMediaListActivity, mediaType == MediaType.movie)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        bindingSheet.recyclerGenreList.layoutManager = layoutManager
        bindingSheet.recyclerGenreList.adapter = genreAdapter

        dialog.setContentView(bindingSheet.root)
        dialog.setCanceledOnTouchOutside(true)

        if (baseViewModel.minVoteAverage.value!! > 0) {
            bindingSheet.minVoteAverage.setText(baseViewModel.minVoteAverage.value!!.toInt().toString())
        }

        if (baseViewModel.maxVoteAverage.value!! > 0) {
            bindingSheet.maxVoteAverage.setText(baseViewModel.maxVoteAverage.value!!.toInt().toString())
        }

        if (baseViewModel.minYear.value!! > 0) {
            bindingSheet.minYear.setText(baseViewModel.minYear.value!!.toString())
        }

        if (baseViewModel.maxYear.value!! > 0) {
            bindingSheet.maxYear.setText(baseViewModel.maxYear.value!!.toString())
        }

        if(!filterGenreList.isNullOrEmpty()) {
            filterGenreList?.forEach { (id, _) ->
                genreList?.forEach { genre ->
                    if(id == genre.id) {
                        genre.isSelect = true
                        return@forEach
                    }
                }
            }
        }

        genreAdapter.submitList(genreList)
        genreAdapter.notifyDataSetChanged()
        bindingSheet.recyclerGenreList.requestLayout()
        bindingSheet.invalidateAll()

        when(baseViewModel.sortBy.value) {
            SortBy.popularityDesc -> bindingSheet.radioSortBy.check(R.id.radioPopularity)
            SortBy.releaseDateDesc -> bindingSheet.radioSortBy.check(R.id.radioReleaseDate)
            SortBy.revenueDesc -> bindingSheet.radioSortBy.check(R.id.radioRevenue)
            SortBy.voteAverageDesc -> bindingSheet.radioSortBy.check(R.id.radioVoteAverage)
            SortBy.originalTitleDesc -> bindingSheet.radioSortBy.check(R.id.radioAlphabetical)
        }

        bindingSheet.sortByButton.setOnClickListener(View.OnClickListener {
            bindingSheet.layoutSortBy.visibility = View.VISIBLE
            bindingSheet.layoutVoteAverage.visibility = View.GONE
            bindingSheet.layoutGenre.visibility = View.GONE
            bindingSheet.layoutYear.visibility = View.GONE

            bindingSheet.line2.visibility = View.INVISIBLE
            bindingSheet.line1.visibility = View.VISIBLE
            bindingSheet.line3.visibility = View.INVISIBLE
            bindingSheet.line4.visibility = View.INVISIBLE
        })

        bindingSheet.ratingButton.setOnClickListener(View.OnClickListener {
            bindingSheet.layoutSortBy.visibility = View.GONE
            bindingSheet.layoutYear.visibility = View.GONE
            bindingSheet.layoutGenre.visibility = View.GONE
            bindingSheet.layoutVoteAverage.visibility = View.VISIBLE

            bindingSheet.line2.visibility = View.VISIBLE
            bindingSheet.line1.visibility = View.INVISIBLE
            bindingSheet.line3.visibility = View.INVISIBLE
            bindingSheet.line4.visibility = View.INVISIBLE
        })

        bindingSheet.yearButton.setOnClickListener(View.OnClickListener {
            bindingSheet.layoutSortBy.visibility = View.GONE
            bindingSheet.layoutVoteAverage.visibility = View.GONE
            bindingSheet.layoutGenre.visibility = View.GONE
            bindingSheet.layoutYear.visibility = View.VISIBLE

            bindingSheet.line2.visibility = View.INVISIBLE
            bindingSheet.line1.visibility = View.INVISIBLE
            bindingSheet.line3.visibility = View.VISIBLE
            bindingSheet.line4.visibility = View.INVISIBLE
        })

        bindingSheet.genresButton.setOnClickListener(View.OnClickListener {
            bindingSheet.layoutSortBy.visibility = View.GONE
            bindingSheet.layoutVoteAverage.visibility = View.GONE
            bindingSheet.layoutYear.visibility = View.GONE
            bindingSheet.layoutGenre.visibility = View.VISIBLE

            bindingSheet.line2.visibility = View.INVISIBLE
            bindingSheet.line1.visibility = View.INVISIBLE
            bindingSheet.line4.visibility = View.VISIBLE
            bindingSheet.line3.visibility = View.INVISIBLE
        })

        bindingSheet.radioSortBy.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId) {
                R.id.radioPopularity -> sortByStr = SortBy.popularityDesc
                R.id.radioReleaseDate ->  sortByStr = SortBy.releaseDateDesc
                R.id.radioRevenue ->  sortByStr =  SortBy.revenueDesc
                R.id.radioVoteAverage ->  sortByStr = SortBy.voteAverageDesc
                R.id.radioAlphabetical ->  sortByStr = SortBy.originalTitleDesc
            }
        }

        bindingSheet.applyButton.setOnClickListener {
            baseViewModel.sortBy.value = sortByStr
            if (bindingSheet.minVoteAverage.text.isNotEmpty()) {
                baseViewModel.minVoteAverage.value = bindingSheet.minVoteAverage.text.toString().toDouble()
            }
            if (bindingSheet.maxVoteAverage.text.isNotEmpty()) {
                baseViewModel.maxVoteAverage.value = bindingSheet.maxVoteAverage.text.toString().toDouble()
            }
            if (bindingSheet.minYear.text.isNotEmpty()) {
                baseViewModel.minYear.value = bindingSheet.minYear.text.toString().toInt()
            }
            if (bindingSheet.maxYear.text.isNotEmpty()) {
                baseViewModel.maxYear.value = bindingSheet.maxYear.text.toString().toInt()
            }
            page = 1
            isFilterApplyClick = true
            if (!filterGenreList?.isNullOrEmpty()!!) {
                genre = baseViewModel.addRequestModelGenre(filterGenreList!!)
            }
            getDiscoverData()
            dialog.cancel()
        }

        bindingSheet.clearButton.setOnClickListener {
            baseViewModel.sortBy.value = SortBy.popularityDesc
            baseViewModel.minVoteAverage.value = 0.0
            baseViewModel.maxVoteAverage.value = 10.0
            filterGenreList?.clear()
            bindingSheet.minVoteAverage.text.clear()
            bindingSheet.maxVoteAverage.text.clear()
            page = 1
            isFilterApplyClick = true
            getDiscoverData()
            dialog.cancel()
        }

        bindingSheet.close.setOnClickListener {
            dialog.cancel()
        }

        dialog.show()
    }

    override fun genreListItemOnClickListener(genre: Genre) {
        if(genre.isSelect) {
            filterGenreList?.put(genre.id, genre.name)
        } else {
           filterGenreList?.remove(genre.id)
        }
    }
}