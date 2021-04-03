package com.laks.tvseries.featurecategory.detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.laks.tvseries.core.base.viewmodel.BaseViewModel
import com.laks.tvseries.core.data.main.ScheduleRepository
import com.laks.tvseries.core.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MovieDetailViewModel(var scheduleRepo: ScheduleRepository?) : BaseViewModel(scheduleRepo) {

    var movieModel = MutableLiveData<MovieDetailModel>()
    var releaseDate = MutableLiveData<String>()
    var loadingComplete = MutableLiveData<Boolean>(false)
    var videoKey = MutableLiveData<String>()
    var castLoadingShimmer = MutableLiveData<Boolean>(true)
    var recommendationsListLoadingShimmer = MutableLiveData<Boolean>(true)
    var castListModel = MutableLiveData<ArrayList<PersonInfo>>()

    val recommendationsList = MutableLiveData<DiscoverMovieListModel>()

    val seasonList = MutableLiveData<ArrayList<SeasonModel>>()

    private val _moreClickEvent = MutableLiveData<Unit>()
    val moreButtonClickEvent: LiveData<Unit> = _moreClickEvent

    private val _allSeasonClickEvent = MutableLiveData<Unit>()
    val allSeasonButtonOnClickEvent: LiveData<Unit> = _allSeasonClickEvent

    val mediaImageList = MutableLiveData<ArrayList<ImageObject>>()

    val mediaDirector = MutableLiveData<String>()
    val mediaWriting = MutableLiveData<String>()
    val formatRunTime = MutableLiveData<String>()

    fun allSeasonButtonOnClickListener() {
        _allSeasonClickEvent.value = Unit
    }

    fun moreButtonOnClickListener() {
        _moreClickEvent.value = Unit
    }

    fun getMovieDetail(movieID: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var requestModel = MovieRequestModel()
                requestModel.movieID = movieID
                scheduleRepo?.getMovieDetail(requestModel)?.collect {
                    loadingComplete.postValue(true)
                    movieModel.postValue(it)
                    releaseDate.postValue(it?.releaseDate?.let { it1 -> getFormatDate(it1) })
                    createRunTime(it?.runtime!!)
                    getMovieCredits(requestModel)
                    getMovieVideo(requestModel)
                    getMovieImages(requestModel)
                    getMovieRecommendations(requestModel)
                }
            }
        }
    }

    fun getTVDetail(movieID: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var requestModel = MovieRequestModel()
                requestModel.movieID = movieID
                scheduleRepo?.getTVDetail(requestModel)?.collect {
                    loadingComplete.postValue(true)
                    movieModel.postValue(it)
                    seasonList.postValue(it?.seasons)
                    releaseDate.postValue(it?.releaseDate?.let { it1 -> getFormatDate(it1) })
                    createRunTime(it?.tvRuntime?.get(0)!!)
                    getTVCredits(requestModel)
                    getTVVideo(requestModel)
                    getTvImages(requestModel)
                    getTvRecommendations(requestModel)
                }
            }
        }
    }

    private fun getMovieVideo(requestModel: MovieRequestModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                scheduleRepo?.getMovieVideo(requestModel)?.collect {
                    videoKey.postValue(it?.let {it.results.first().key})
                }
            }
        }
    }

    private fun getTVVideo(requestModel: MovieRequestModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                scheduleRepo?.getTvVideo(requestModel)?.collect {
                    videoKey.postValue(it?.let {it.results.first().key})
                }
            }
        }
    }

    private fun getMovieCredits(requestModel: MovieRequestModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                scheduleRepo?.getMovieCredits(requestModel)?.collect {
                    castToPersonModel(it!!)
                    getFindDirector(it)
                    getFindWriting(it)
                }
            }
        }
    }

    private fun getTVCredits(requestModel: MovieRequestModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                scheduleRepo?.getTvCredits(requestModel)?.collect {
                    castToPersonModel(it!!)
                    getFindDirector(it)
                    getFindWriting(it)
                }
            }
        }
    }

    private fun castToPersonModel(model: MovieCreditsModel) {
        castListModel.value?.clear()
        var list: ArrayList<PersonInfo>? = arrayListOf()

        model.cast.forEachIndexed{ index, item ->
            list?.add(PersonInfo(
                    id = item.id,
                    name = item.name,
                    posterPath = item.profilePath
            ))

            if(index>10) {
                castListModel.postValue(list)
                castLoadingShimmer.postValue(false)
                return
            }
        }
        castListModel.postValue(list)
        castLoadingShimmer.postValue(false)
    }

    private fun getMovieImages(requestModel: MovieRequestModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                scheduleRepo?.getMovieImage(requestModel)?.collect {
                    mediaImageList.postValue(it?.backdrops)
                }
            }
        }
    }

    private fun getTvImages(requestModel: MovieRequestModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                scheduleRepo?.getTvImage(requestModel)?.collect {
                    mediaImageList.postValue(it?.backdrops)
                }
            }
        }
    }

    private fun getMovieRecommendations(requestModel: MovieRequestModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                scheduleRepo?.getMovieRecommendations(requestModel)?.collect {
                    recommendationsList.postValue(it)
                    recommendationsListLoadingShimmer.postValue(false)
                }
            }
        }
    }

    private fun getTvRecommendations(requestModel: MovieRequestModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                scheduleRepo?.getTvRecommendations(requestModel)?.collect {
                    recommendationsList.postValue(it)
                    recommendationsListLoadingShimmer.postValue(false)
                }
            }
        }
    }

    fun createSeasonListModel(isLess: Boolean): ArrayList<SeasonModel> {
        var seasons = ArrayList<SeasonModel>()

        seasonList.value?.forEachIndexed { index, seasonModel ->
            if (!seasonModel.airDate.isNullOrEmpty() && seasonModel.airDate.contains("-")) {
                seasonModel.airDate = getFormatDate(seasonModel.airDate)
            }
            seasons.add(seasonModel)
            if (isLess && index == 2) return seasons
        }

        return seasons
    }

    private fun getFindDirector(model: MovieCreditsModel) {
        var crewList = model.crew

        crewList.forEach { crew ->
            if(crew.knownForDepartment == Department.Directing.name && crew.department == Department.Directing.name) {
                mediaDirector.postValue(crew.name)
                return
            }
        }
    }

    private fun getFindWriting(model: MovieCreditsModel) {
        var crewList = model.crew

        crewList.forEach { crew ->
            if(crew.job == Department.Screenplay.name && crew.department == Department.Writing.name) {
                mediaWriting.postValue(crew.name)
                return
            }
        }
    }

    private fun getFormatDate(releaseDate: String): String {
        var format = SimpleDateFormat("yyyy-mm-dd")
        val newDate: Date = format.parse(releaseDate)

        format = SimpleDateFormat("MMM yyyy")
        return  format.format(newDate)
    }

    fun createRunTime(time: Int){
        if (time > 60) {
            var hour = time / 60
            var minute = time % 60
            formatRunTime.postValue("${hour} hrs ${minute} mins")
        } else if (time > 0 && time < 60) {
            var minute = time % 60
            formatRunTime.postValue("${minute} mins")
        }
    }
}