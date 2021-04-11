package com.laks.tvseries.featureactor.detail

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.daimajia.slider.library.Animations.DescriptionAnimation
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.daimajia.slider.library.Tricks.ViewPagerEx
import com.laks.tvseries.core.base.activity.BaseActivity
import com.laks.tvseries.core.cache.MemoryCache
import com.laks.tvseries.core.common.media.CastListItemAdapter
import com.laks.tvseries.core.common.media.CastListItemOnClickListener
import com.laks.tvseries.core.data.model.CastObject
import com.laks.tvseries.core.data.model.MediaType
import com.laks.tvseries.core.di.mediaDIModule
import com.laks.tvseries.core.di.stateDIModule
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.featureactor.R
import com.laks.tvseries.featureactor.databinding.ActivityActorDetailBinding
import com.laks.tvseries.featureactor.di.actorDetailDIModule
import com.laks.tvseries.featureactor.list.ActorMovieTvShowListActivity
import com.laks.tvseries.featurecategory.detail.MovieDetailActivity
import com.squareup.picasso.Picasso
import org.koin.core.module.Module

class ActorDetailActivity : BaseActivity<ActorDetailViewModel>(ActorDetailViewModel::class), CastListItemOnClickListener,
        BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    override val modules: List<Module>
        get() = listOf(actorDetailDIModule, stateDIModule, mediaDIModule)

    private lateinit var binding: ActivityActorDetailBinding
    private lateinit var adapterMovie: CastListItemAdapter
    private lateinit var adapterTv: CastListItemAdapter
    private var isMore = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_actor_detail)
        binding.viewModel = baseViewModel
        binding.lifecycleOwner = this

        setMovieListAdapter()
        setTvListAdapter()
        getActorDetail()
        bindingViewModel()
        createActorBackDropImageSlider()
        moreButtonAction()
    }

    private fun getActorDetail() {
        var actorDetail = MemoryCache.cache.findMemoryCacheValueAny(GlobalConstants.ACTOR_DETAIL_ID).toString()
        baseViewModel.getActorDetail(actorDetail)
    }

    private fun bindingViewModel() {
        baseViewModel.actorDetailModel.observe(this, Observer { actor ->
            actor.profilePath.let {
                Picasso.with(this).load("${GlobalConstants.SERVER_IMAGE_URL}${it}").fit().into(binding.imageSchedule)
            }

            binding.labelOverView.text = actor.biography?.let {
                if (it.length > 110) "${
                    it.substring(
                        0,
                        110
                    )
                }..." else it
            }
        })

        baseViewModel.movieCreditsModel.observe(this, Observer {
            adapterMovie.submitList(it)
            adapterMovie.notifyDataSetChanged()
            requestLayout()
        })

        baseViewModel.tvCreditsModel.observe(this, Observer {
            adapterTv.submitList(it)
            adapterTv.notifyDataSetChanged()
            requestLayout()
        })

        baseViewModel.allMovieClickEvent.observe(this, Observer {
            setTransferMediaList(isMovie = true)
        })

        baseViewModel.allTvShowClickEvent.observe(this, Observer {
            setTransferMediaList(isMovie = false)
        })
    }

    private fun setTransferMediaList(isMovie: Boolean) {
        if (isMovie) {
            baseViewModel.movieCreditsModel.value?.let { MemoryCache.cache.setMemoryCacheValue(GlobalConstants.KEY_ACTOR_MEDIA_LIST, it) }
        } else {
            baseViewModel.tvCreditsModel.value?.let { MemoryCache.cache.setMemoryCacheValue(GlobalConstants.KEY_ACTOR_MEDIA_LIST, it) }
        }
        startActivity(Intent(this, ActorMovieTvShowListActivity::class.java))
    }

    private fun moreButtonAction() {
        baseViewModel.moreButtonClickEvent.observe(this, Observer {
            if (isMore) {
                binding.labelOverView.text = baseViewModel.actorDetailModel.value?.biography
                binding.labelMore.text = resources.getString(com.laks.tvseries.core.R.string.read_less)
            } else {
                binding.labelOverView.text = baseViewModel.actorDetailModel.value?.biography?.let {
                    if (it.length > 110) "${
                        it.substring(
                            0,
                            110
                        )
                    }..." else it
                }
                binding.labelMore.text = resources.getString(com.laks.tvseries.core.R.string.more)
            }
            isMore = !isMore
        })
    }

    private fun createActorBackDropImageSlider() {
        baseViewModel.actorBackDropImages.observe(this, Observer { sliderImages ->
            for (image in sliderImages) {
                val textSliderView = TextSliderView(this)
                textSliderView
                        .image("${GlobalConstants.SERVER_BACK_DROP_IMAGE_URL}${image}")
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this)
                textSliderView.bundle(Bundle())
                binding.sliderLayout.addSlider(textSliderView)
            }

            binding.sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion)
            binding.sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom)
            binding.sliderLayout.setCustomAnimation(DescriptionAnimation())
            binding.sliderLayout.setDuration(5000)
            binding.sliderLayout.addOnPageChangeListener(this)
        })
    }

    private fun setMovieListAdapter() {
        adapterMovie = CastListItemAdapter(
            context = this@ActorDetailActivity,
            clickListener = this
        )
        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerMovieList.layoutManager = layoutManager
        binding.recyclerMovieList.adapter = adapterMovie

    }

    private fun setTvListAdapter() {
        adapterTv = CastListItemAdapter(
            context = this@ActorDetailActivity,
            clickListener = this
        )
        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerTvList.layoutManager = layoutManager
        binding.recyclerTvList.adapter = adapterTv

    }

    private fun requestLayout() {
        binding.rootRelativeView.requestLayout()
        binding.invalidateAll()
        binding.executePendingBindings()
    }

    override fun onSliderClick(slider: BaseSliderView?) {}
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
    override fun onPageSelected(position: Int) {}
    override fun onPageScrollStateChanged(state: Int) {}

    override fun castListItemOnClickListener(cast: CastObject) {
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_TYPE, if(cast.isMovie!!) MediaType.movie else MediaType.tv)
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_ID, cast.id!!)
        var intent = Intent(this@ActorDetailActivity, MovieDetailActivity::class.java)
        startActivity(intent)
    }

}