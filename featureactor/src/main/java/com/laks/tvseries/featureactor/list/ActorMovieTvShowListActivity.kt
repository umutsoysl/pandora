package com.laks.tvseries.featureactor.list

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.laks.tvseries.core.base.activity.BaseActivity
import com.laks.tvseries.core.cache.MemoryCache
import com.laks.tvseries.core.common.media.CastListItemOnClickListener
import com.laks.tvseries.core.data.model.CastObject
import com.laks.tvseries.core.data.model.MediaType
import com.laks.tvseries.core.di.mediaDIModule
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.featureactor.R
import com.laks.tvseries.featureactor.databinding.ActivityActorMediaListBinding
import com.laks.tvseries.featureactor.detail.ActorDetailViewModel
import com.laks.tvseries.featureactor.di.actorDetailDIModule
import com.laks.tvseries.featurecategory.detail.MovieDetailActivity
import org.koin.core.module.Module

class ActorMovieTvShowListActivity : BaseActivity<ActorDetailViewModel>(ActorDetailViewModel::class), CastListItemOnClickListener {

    override val modules: List<Module>
        get() = listOf(actorDetailDIModule, mediaDIModule)
    private lateinit var binding: ActivityActorMediaListBinding
    private lateinit var adapter: ActorMediaListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(R.layout.activity_actor_media_list)
        binding.viewModel = baseViewModel
        binding.lifecycleOwner = this
        setToolbarTitle(resources.getString(com.laks.tvseries.core.R.string.all_media))

        setAdapter()
        getActorMediaList()
    }

    private fun getActorMediaList() {
        var mediaList = MemoryCache.cache.findMemoryCacheValueAny(GlobalConstants.KEY_ACTOR_MEDIA_LIST) as ArrayList<CastObject>

        mediaList?.let {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun setAdapter() {
        adapter = ActorMediaListAdapter(context = this, clickListener = this)
        var layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerMovieList.layoutManager = layoutManager
        binding.recyclerMovieList.adapter = adapter
    }

    override fun castListItemOnClickListener(cast: CastObject) {
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_TYPE, if(cast.isMovie!!) MediaType.movie else MediaType.tv)
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_ID, cast.id!!)
        var intent = Intent(this@ActorMovieTvShowListActivity, MovieDetailActivity::class.java)
        startActivity(intent)
    }
}