package com.laks.tvseries.core.data

import com.laks.tvseries.core.global.GlobalConstants

object PandoraActivities {
    private const val FEATURE_CATEGORY = "featurecategory"
    private const val PANDORA = "pandora"
    private const val CORE = "core"
    private const val CATEGORY = "category"
    private const val FEATURE_ACTOR = "featureactor"
    private const val DETAIL = "detail"
    private const val RECOMMENDED = "recommended"
    private const val FEATURE_SEARCH = "featuresearch"
    private const val FEATURE_SETTINGS = "featuresettings"

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

    val topRatedMovieFragmentClassName: String
        get() = "${GlobalConstants.PACKAGE_NAME}.$FEATURE_CATEGORY.$CATEGORY.movie.TopRatedMovieFragment"

    val recommendedMovieFragmentClassName: String
        get() = "${GlobalConstants.PACKAGE_NAME}.$FEATURE_CATEGORY.$RECOMMENDED.RecommendedMovieFragment"

    val actorDetailActivityClassName: String
        get() = "${GlobalConstants.PACKAGE_NAME}.$FEATURE_ACTOR.$DETAIL.ActorDetailActivity"

    val searchActivityClassName: String
        get() = "${GlobalConstants.PACKAGE_NAME}.$FEATURE_SEARCH.SearchActivity"

    val movieDetailActivityClassName: String
        get() = "${GlobalConstants.PACKAGE_NAME}.$FEATURE_CATEGORY.$DETAIL.MovieDetailActivity"

    val languageActivityClassName: String
        get() = "${GlobalConstants.PACKAGE_NAME}.$FEATURE_SETTINGS.language.LanguageActivity"
}