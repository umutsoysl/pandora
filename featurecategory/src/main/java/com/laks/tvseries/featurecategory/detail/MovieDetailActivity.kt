package com.laks.tvseries.featurecategory.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.laks.tvseries.core.base.activity.BaseActivity
import com.laks.tvseries.core.cache.MemoryCache
import com.laks.tvseries.core.common.media.MediaListItemAdapter
import com.laks.tvseries.core.common.media.MediaListItemOnClickListener
import com.laks.tvseries.core.common.people.PeopleItemClickListener
import com.laks.tvseries.core.common.people.PeopleListItemAdapter
import com.laks.tvseries.core.data.model.MediaType
import com.laks.tvseries.core.data.model.MovieModel
import com.laks.tvseries.core.data.model.PersonInfo
import com.laks.tvseries.core.di.scheduleDIModule
import com.laks.tvseries.core.di.stateDIModule
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.featurecategory.R
import com.laks.tvseries.featurecategory.databinding.ActivityMovieDetailBinding
import com.laks.tvseries.featurecategory.di.detailDIModule
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.squareup.picasso.Picasso
import org.koin.core.module.Module


class MovieDetailActivity : BaseActivity<MovieDetailViewModel>(MovieDetailViewModel::class), PeopleItemClickListener, MediaListItemOnClickListener {

    override val modules: List<Module>
        get() = listOf(detailDIModule, stateDIModule, scheduleDIModule)

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var adapter: GenreListItemAdapter
    private var type: String? = null
    private var isMore = true
    private lateinit var adapterCast: PeopleListItemAdapter
    private lateinit var adapterMedia: MediaListItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        binding.viewModel = baseViewModel
        binding.lifecycleOwner = this

        setAdapter()
        setCastListAdapter()
        setMediaListAdapter()
        getDetail()
        bindingViewModel()
        backButtonClick()
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
                R.string.run_time,
                movie.runtime
            )) else (resources.getString(R.string.run_time, movie.tvRuntime?.get(0)))
            requestLayout()
        })

        baseViewModel.moreButtonClickEvent.observe(this, Observer {
            if (isMore) {
                binding.labelOverView.text = baseViewModel.movieModel.value?.overview
                binding.labelMore.text = resources.getString(R.string.read_less)
            } else {
                binding.labelOverView.text = baseViewModel.movieModel.value?.overview?.let {
                    if (it.length > 110) "${
                        it.substring(
                            0,
                            110
                        )
                    }..." else it
                }
                binding.labelMore.text = resources.getString(R.string.more)
            }
            isMore = !isMore
        })

        baseViewModel.videoKey.observe(this, Observer {
            playVideo(it)
        })


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
        adapterCast = PeopleListItemAdapter(context = this@MovieDetailActivity, clickListener = this)
        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerCastList.layoutManager = layoutManager
        binding.recyclerCastList.adapter = adapterCast

    }

    private fun setMediaListAdapter() {
        adapterMedia = MediaListItemAdapter(context = this@MovieDetailActivity, clickListener = this)
        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerRecommendationsMovieList.layoutManager = layoutManager
        binding.recyclerRecommendationsMovieList.adapter = adapterMedia

    }

    private fun playVideo(videoId: String?) {
        lifecycle.addObserver(binding.youtubePlayerView)

        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
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

    private fun requestLayout() {
        binding.rootRelativeView.requestLayout()
        binding.invalidateAll()
        binding.executePendingBindings()
    }

    override fun personClickListener(person: PersonInfo) {
        TODO("Not yet implemented")
    }

    override fun mediaListItemOnClickListener(scheduleInfo: MovieModel) {
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_TYPE, if(type == MediaType.movie) MediaType.movie else MediaType.tv)
        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_ID, scheduleInfo.id!!)
        var intent = Intent(this, MovieDetailActivity::class.java)
        startActivity(intent)
    }
}