package com.laks.tvseries.featurecategory.detail

import android.content.Context
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.laks.tvseries.core.base.viewmodel.BaseViewModel
import com.laks.tvseries.core.dao.MediaDao
import com.laks.tvseries.core.data.db.DBMediaEntity
import com.laks.tvseries.core.data.main.MediaRepository
import com.laks.tvseries.core.data.model.*
import com.laks.tvseries.core.db.PandoraDatabase
import com.laks.tvseries.core.util.currencyFormat
import com.laks.tvseries.core.util.getDate
import com.laks.tvseries.core.util.getProcessDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MovieDetailViewModel(var mediaRepo: MediaRepository?) : BaseViewModel(mediaRepo) {

    var movieModel = MutableLiveData<MovieDetailModel>()
    var releaseDate = MutableLiveData<String>()
    var loadingComplete = MutableLiveData<Boolean>(false)
    var videoKey = MutableLiveData<String>()
    var castLoadingShimmer = MutableLiveData<Boolean>(true)
    var recommendationsListLoadingShimmer = MutableLiveData<Boolean>(true)
    var castListModel = MutableLiveData<ArrayList<PersonInfo>>()

    val recommendationsList = MutableLiveData<DiscoverMovieListModel>()

    val seasonList = MutableLiveData<ArrayList<SeasonModel>>()

    var mediaDBMediaEntity = MutableLiveData<DBMediaEntity>()

    private val _moreClickEvent = MutableLiveData<Unit>()
    val moreButtonClickEvent: LiveData<Unit> = _moreClickEvent

    private val _allSeasonClickEvent = MutableLiveData<Unit>()
    val allSeasonButtonOnClickEvent: LiveData<Unit> = _allSeasonClickEvent

    val mediaImageList = MutableLiveData<ArrayList<ImageObject>>()

    val mediaDirector = MutableLiveData<String>()
    val mediaWriting = MutableLiveData<String>()
    val formatRunTime = MutableLiveData<String>()
    val formatRevenue = MutableLiveData<String>()
    val formatBudget = MutableLiveData<String>()

    fun allSeasonButtonOnClickListener() {
        _allSeasonClickEvent.value = Unit
    }

    fun moreButtonOnClickListener() {
        _moreClickEvent.value = Unit
    }

    fun getMovieDetail(movieID: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var requestModel = GlobalRequestModel()
                requestModel.movieID = movieID
                mediaRepo?.getMovieDetail(requestModel)?.collect {
                    loadingComplete.postValue(true)
                    movieModel.postValue(it)
                    createDBEntity(movieModel = it!!, true)
                    releaseDate.postValue(it?.releaseDate?.let { it1 -> getFormatDate(it1) })
                    createRunTime(it?.runtime!!)
                    formatBudget.postValue(moneyFormat(it.budget))
                    formatRevenue.postValue(moneyFormat(it.revenue))
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
                var requestModel = GlobalRequestModel()
                requestModel.movieID = movieID
                mediaRepo?.getTVDetail(requestModel)?.collect {
                    loadingComplete.postValue(true)
                    movieModel.postValue(it)
                    createDBEntity(movieModel = it!!, false)
                    seasonList.postValue(it?.seasons)
                    releaseDate.postValue(it?.releaseDate?.let { it1 -> getFormatDate(it1) })
                    createRunTime(it?.tvRuntime?.get(0)!!)
                    formatBudget.postValue(moneyFormat(it.budget))
                    formatRevenue.postValue(moneyFormat(it.revenue))
                    getTVCredits(requestModel)
                    getTVVideo(requestModel)
                    getTvImages(requestModel)
                    getTvRecommendations(requestModel)
                }
            }
        }
    }

    fun findDBEntity(context: Context, id: Long) {
        val mediaDao: MediaDao? = PandoraDatabase.getDatabase(context)?.mediaDao()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                PandoraDatabase.execute.execute {
                    mediaDao?.findMedia(mediaID = id)?.let {
                        if (it.id > 0) {
                            mediaDBMediaEntity.postValue(it)
                        }
                    }
                }
            }
        }
    }

    private fun createDBEntity(movieModel: MovieDetailModel, isMovie: Boolean) {
        var entity = DBMediaEntity()
        entity.image = movieModel.posterPath!!
        entity.title = movieModel.title!!
        entity.rating = movieModel.voteAverage
        entity.id = movieModel.id
        entity.isMovie = isMovie
        entity.processDate = getProcessDate()
        entity.insertDate = getDate()

        mediaDBMediaEntity.postValue(entity)
    }

    private fun getMovieVideo(requestModel: GlobalRequestModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mediaRepo?.getMovieVideo(requestModel)?.collect {
                    videoKey.postValue(it?.let {it.results.first().key})
                }
            }
        }
    }

    private fun getTVVideo(requestModel: GlobalRequestModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mediaRepo?.getTvVideo(requestModel)?.collect {
                    videoKey.postValue(it?.let {it.results.first().key})
                }
            }
        }
    }

    private fun getMovieCredits(requestModel: GlobalRequestModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mediaRepo?.getMovieCredits(requestModel)?.collect {
                    castToPersonModel(it!!)
                    getFindDirector(it)
                    getFindWriting(it)
                }
            }
        }
    }

    private fun getTVCredits(requestModel: GlobalRequestModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mediaRepo?.getTvCredits(requestModel)?.collect {
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

    private fun getMovieImages(requestModel: GlobalRequestModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mediaRepo?.getMovieImage(requestModel)?.collect {
                    mediaImageList.postValue(it?.backdrops)
                }
            }
        }
    }

    private fun getTvImages(requestModel: GlobalRequestModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mediaRepo?.getTvImage(requestModel)?.collect {
                    mediaImageList.postValue(it?.backdrops)
                }
            }
        }
    }

    private fun getMovieRecommendations(requestModel: GlobalRequestModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mediaRepo?.getMovieRecommendations(requestModel)?.collect {
                    recommendationsList.postValue(it)
                    recommendationsListLoadingShimmer.postValue(false)
                }
            }
        }
    }

    private fun getTvRecommendations(requestModel: GlobalRequestModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mediaRepo?.getTvRecommendations(requestModel)?.collect {
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

    private fun createRunTime(time: Int){
        if (time > 60) {
            var hour = time / 60
            var minute = time % 60
            formatRunTime.postValue("$hour hrs $minute mins")
        } else if (time in 1..59) {
            var minute = time % 60
            formatRunTime.postValue("$minute mins")
        }
    }

    private fun moneyFormat(money: String): String {
        return if(money!=null && money!="-") {
            currencyFormat(money)!!
        } else {
            "?"
        }
    }
}