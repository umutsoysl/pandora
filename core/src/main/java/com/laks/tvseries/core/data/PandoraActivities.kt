package com.laks.tvseries.core.data

import com.laks.tvseries.core.global.GlobalConstants

object PandoraActivities {
    private const val FEATURE_CATEGORY = "featurecategory"
    private const val PANDORA = "pandora"
    private const val CORE = "core"
    private const val CATEGORY = "category"

    val trendingMovieFragmentClassName: String
        get() = "${GlobalConstants.PACKAGE_NAME}.$FEATURE_CATEGORY.$CATEGORY.movie.TrendMovieFragment"

    val trendingTVFragmentClassName: String
        get() = "${GlobalConstants.PACKAGE_NAME}.$FEATURE_CATEGORY.$CATEGORY.tv.TrendTvShowFragment"

    val nowPlayingMovieListFragmentClassName: String
        get() = "${GlobalConstants.PACKAGE_NAME}.$FEATURE_CATEGORY.$CATEGORY.movie.NowPlayingMovieFragment"

    val popularTvShowsFragmentClassName: String
        get() = "${GlobalConstants.PACKAGE_NAME}.$FEATURE_CATEGORY.$CATEGORY.tv.PopularTvShowFragment"

    val popularPeopleFragmentClassName: String
        get() = "${GlobalConstants.PACKAGE_NAME}.$FEATURE_CATEGORY.$CATEGORY.people.PopularPeopleFragment"

    val upComingMovieFragmentClassName: String
        get() = "${GlobalConstants.PACKAGE_NAME}.$FEATURE_CATEGORY.$CATEGORY.movie.UpComingMovieFragment"
}