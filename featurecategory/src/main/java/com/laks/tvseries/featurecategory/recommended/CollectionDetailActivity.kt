package com.laks.tvseries.featurecategory.recommended

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.laks.tvseries.core.base.activity.BaseActivity
import com.laks.tvseries.core.cache.MemoryCache
import com.laks.tvseries.core.common.media.MediaListItemOnClickListener
import com.laks.tvseries.core.data.model.CollectionSliderModel
import com.laks.tvseries.core.data.model.MovieModel
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.featurecategory.R
import com.laks.tvseries.featurecategory.category.CategoryViewModel
import com.laks.tvseries.featurecategory.databinding.ActivityCollectionDetailBinding
import com.laks.tvseries.featurecategory.detail.MovieDetailActivity
import com.laks.tvseries.featurecategory.di.categoryDIModule
import com.laks.tvseries.featurecategory.list.adapter.AllMovieListAdapter
import com.squareup.picasso.Picasso
import org.koin.core.module.Module

class CollectionDetailActivity: BaseActivity<CategoryViewModel>(CategoryViewModel::class), MediaListItemOnClickListener {

    override val modules: List<Module>
        get() = listOf(categoryDIModule)
    private lateinit var binding: ActivityCollectionDetailBinding
    private lateinit var adapter: AllMovieListAdapter
    private lateinit var collectionModel: CollectionSliderModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collection_detail)
        binding.viewModel = baseViewModel
        binding.lifecycleOwner = this

        setCollectionData()
        setAdapter()
        bindingViewModel()
    }

    private fun bindingViewModel() {
        baseViewModel.allMovieList.observe(this, Observer {
            adapter.submitList(it.results)
            adapter.notifyDataSetChanged()
            binding.rootRelativeView.requestLayout()
            binding.invalidateAll()
            binding.executePendingBindings()
        })

        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setCollectionData() {

        collectionModel = MemoryCache.cache.findMemoryCacheValue(GlobalConstants.COLLECTION_MODEL) as CollectionSliderModel

        Picasso.with(this).load("${GlobalConstants.SERVER_BACK_DROP_IMAGE_URL}${collectionModel.image}").into(binding.imageBackdrop)
        binding.labelMovieTitle.text = collectionModel.title
        baseViewModel.getCollectionMediaList(collectionModel.id)
    }

    private fun setAdapter() {
        adapter = AllMovieListAdapter(context = this, clickListener = this)
        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerMovieList.layoutManager = layoutManager
        binding.recyclerMovieList.adapter = adapter
    }

    override fun mediaListItemOnClickListener(movie: MovieModel) {
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_TYPE, collectionModel.mediaType)
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_ID, movie.id!!)
        val intent = Intent(this@CollectionDetailActivity, MovieDetailActivity::class.java)
        startActivity(intent)
    }
}