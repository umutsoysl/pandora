package com.laks.tvseries.featurecategory.detail

import android.annotation.SuppressLint
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
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.laks.tvseries.core.base.activity.BaseActivity
import com.laks.tvseries.core.cache.MemoryCache
import com.laks.tvseries.core.common.media.MediaListItemAdapter
import com.laks.tvseries.core.common.media.MediaListItemOnClickListener
import com.laks.tvseries.core.common.people.PeopleItemClickListener
import com.laks.tvseries.core.common.people.PeopleListItemAdapter
import com.laks.tvseries.core.dao.MediaDao
import com.laks.tvseries.core.data.PandoraActivities
import com.laks.tvseries.core.data.model.MediaType
import com.laks.tvseries.core.data.model.MovieModel
import com.laks.tvseries.core.data.model.PersonInfo
import com.laks.tvseries.core.db.PandoraDatabase
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.featurecategory.R
import com.laks.tvseries.featurecategory.databinding.ActivityMovieDetailBinding
import com.laks.tvseries.featurecategory.detail.season.SeasonListAdapter
import com.laks.tvseries.featurecategory.di.detailDIModule
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.squareup.picasso.Picasso
import org.koin.core.module.Module


class MovieDetailActivity : BaseActivity<MovieDetailViewModel>(MovieDetailViewModel::class), PeopleItemClickListener, MediaListItemOnClickListener,
    BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    override val modules: List<Module>
        get() = listOf(detailDIModule)

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var adapter: GenreListItemAdapter
    private var type: String? = null
    private var isMore = true
    private var isAllSeason = true
    private lateinit var adapterCast: PeopleListItemAdapter
    private lateinit var adapterMedia: MediaListItemAdapter
    private lateinit var adapterSeason: SeasonListAdapter

    private var isFavoriteClick = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        binding.viewModel = baseViewModel
        binding.lifecycleOwner = this

        setAdapter()
        setCastListAdapter()
        setMediaListAdapter()
        setSeasonListAdapter()
        getDetail()
        bindingViewModel()
        favoriteButtonClick()
        backButtonClick()
        createAdapterListObserver()
        createImageSlider()
    }

    private fun getDetail() {
        var movieID = MemoryCache.cache.findMemoryCacheValueAny(GlobalConstants.MEDIA_DETAIL_ID).toString()
        type = MemoryCache.cache.findMemoryCacheValueAny(GlobalConstants.MEDIA_DETAIL_TYPE).toString()

        if(type == MediaType.movie) {
            baseViewModel.getMovieDetail(movieID = movieID)
        } else {
            baseViewModel.getTVDetail(movieID = movieID)
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun bindingViewModel() {

        baseViewModel.movieModel.observe(this, Observer { movie ->
            movie.backdropPath.let {
                Picasso.with(this).load("${GlobalConstants.SERVER_BACK_DROP_IMAGE_URL}${it}")
                    .centerCrop().fit().into(
                        binding.imageBackdrop
                    )
            }
            movie.posterPath.let {
                Picasso.with(this).load("${GlobalConstants.SERVER_IMAGE_URL}${it}").fit().into(
                    binding.imageSchedule
                )
            }
            binding.labelOverView.text = baseViewModel.movieModel.value?.overview?.let {
                if (it.length > 110) "${
                    it.substring(
                        0,
                        110
                    )
                }..." else it
            }
            adapter.submitList(movie.genres)
            adapter.notifyDataSetChanged()
            binding.labelRuntime.text = if (type == MediaType.movie) (resources.getString(
                    com.laks.tvseries.core.R.string.run_time,
                movie.runtime
            )) else (resources.getString(com.laks.tvseries.core.R.string.run_time, movie.tvRuntime?.get(0)))
            baseViewModel.findDBEntity(applicationContext, movie.id)
            requestLayout()
        })

        baseViewModel.moreButtonClickEvent.observe(this, Observer {
            if (isMore) {
                binding.labelOverView.text = baseViewModel.movieModel.value?.overview
                binding.labelMore.text = resources.getString(com.laks.tvseries.core.R.string.read_less)
            } else {
                binding.labelOverView.text = baseViewModel.movieModel.value?.overview?.let {
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

        baseViewModel.videoKey.observe(this, Observer {
            playVideo(it)
        })

        baseViewModel.allSeasonButtonOnClickEvent.observe(this, Observer {
            if (isAllSeason) {
                adapterSeason.submitList(baseViewModel.createSeasonListModel(false))
                binding.labelAllSeasonText.text = resources.getString(com.laks.tvseries.core.R.string.less_season)
            } else {
                adapterSeason.submitList(baseViewModel.createSeasonListModel(true))
                binding.labelAllSeasonText.text = resources.getString(com.laks.tvseries.core.R.string.more_season)
            }
            adapterSeason.notifyDataSetChanged()
            requestLayout()

            isAllSeason = !isAllSeason
        })

        baseViewModel.mediaDBMediaEntity.observe(this, Observer {
            if (it.id > 0) {
                if(it.isFavorite) {
                    binding.favoriteButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                } else {
                    binding.favoriteButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                }
                isFavoriteClick = !it.isFavorite
            }
        })
    }

    private fun createAdapterListObserver() {
        baseViewModel.castListModel.observe(this, Observer {
            adapterCast.submitList(it)
            adapterCast.notifyDataSetChanged()
            requestLayout()
        })

        baseViewModel.recommendationsList.observe(this, Observer {
            adapterMedia.submitList(it.results)
            adapterMedia.notifyDataSetChanged()
            requestLayout()
        })

        baseViewModel.seasonList.observe(this, Observer {
            adapterSeason.submitList(baseViewModel.createSeasonListModel(it.size > 2))
            adapterSeason.notifyDataSetChanged()
            requestLayout()
        })
    }

    private fun createImageSlider() {
        baseViewModel.mediaImageList.observe(this, Observer {
            var sliderImages = it

            for (slider in sliderImages) {
                val textSliderView = TextSliderView(this)
                textSliderView
                    .image("${GlobalConstants.SERVER_BACK_DROP_IMAGE_URL}${slider.filePath}")
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this)
                textSliderView.bundle(Bundle())
                binding.sliderLayout.addSlider(textSliderView)
            }

            binding.sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion)
            binding.sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom)
            binding.sliderLayout.setCustomAnimation(DescriptionAnimation())
            binding.sliderLayout.setDuration(4000)
            binding.sliderLayout.addOnPageChangeListener(this)
        })
    }

    private fun setAdapter() {
        adapter = GenreListItemAdapter(this@MovieDetailActivity)
        val layoutManager = FlexboxLayoutManager(this@MovieDetailActivity)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        binding.recyclerGenreList.layoutManager = layoutManager
        binding.recyclerGenreList.adapter = adapter
    }

    private fun setCastListAdapter() {
        adapterCast = PeopleListItemAdapter(
            context = this@MovieDetailActivity,
            clickListener = this
        )
        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerCastList.layoutManager = layoutManager
        binding.recyclerCastList.adapter = adapterCast

    }

    private fun setMediaListAdapter() {
        adapterMedia = MediaListItemAdapter(
            context = this@MovieDetailActivity,
            clickListener = this
        )
        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerRecommendationsMovieList.layoutManager = layoutManager
        binding.recyclerRecommendationsMovieList.adapter = adapterMedia

    }

    private fun setSeasonListAdapter() {
        adapterSeason = SeasonListAdapter(context = this@MovieDetailActivity)
        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerSeasonList.layoutManager = layoutManager
        binding.recyclerSeasonList.adapter = adapterSeason

    }

    private fun playVideo(videoId: String?) {
        lifecycle.addObserver(binding.youtubePlayerView)

        binding.youtubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                videoId?.let { youTubePlayer.loadVideo(it, 0f) }
            }
        })
    }

    private fun backButtonClick() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun favoriteButtonClick() {
        binding.favoriteButton.setOnClickListener {
            if(!isFavoriteClick) {
                binding.favoriteButton.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                binding.favoriteButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
            addFavoriteDatabase(!isFavoriteClick)
            isFavoriteClick = !isFavoriteClick
        }
    }

    private fun addFavoriteDatabase(isFavorite: Boolean) {
        val mediaDao: MediaDao? = PandoraDatabase.getDatabase(applicationContext)?.mediaDao()
        baseViewModel.mediaDBMediaEntity.value?.isFavorite = isFavorite
        PandoraDatabase.execute.execute {
            mediaDao?.insert(baseViewModel.mediaDBMediaEntity.value)
        }
    }

    private fun requestLayout() {
        binding.invalidateAll()
        binding.executePendingBindings()
    }

    override fun personClickListener(person: PersonInfo) {
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.ACTOR_DETAIL_ID, person.id!!)
        var intent = Intent(Intent.ACTION_VIEW).setClassName(this, PandoraActivities.actorDetailActivityClassName)
        startActivity(intent)
    }

    override fun mediaListItemOnClickListener(scheduleInfo: MovieModel) {
        MemoryCache.cache.setMemoryCacheValue(
            GlobalConstants.MEDIA_DETAIL_TYPE,
            if (type == MediaType.movie) MediaType.movie else MediaType.tv
        )
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_ID, scheduleInfo.id!!)
        var intent = Intent(this, MovieDetailActivity::class.java)
        startActivity(intent)
    }

    override fun onSliderClick(slider: BaseSliderView?) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {}

    override fun onPageScrollStateChanged(state: Int) {}
}