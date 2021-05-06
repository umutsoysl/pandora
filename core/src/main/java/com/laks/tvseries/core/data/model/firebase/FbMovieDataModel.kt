package com.laks.tvseries.core.data.model.firebase

import androidx.annotation.LongDef
import com.laks.tvseries.core.data.model.firebase.ModeType.Companion.adventure
import com.laks.tvseries.core.data.model.firebase.ModeType.Companion.classic
import com.laks.tvseries.core.data.model.firebase.ModeType.Companion.drama
import com.laks.tvseries.core.data.model.firebase.ModeType.Companion.fantastic
import com.laks.tvseries.core.data.model.firebase.ModeType.Companion.friend
import com.laks.tvseries.core.data.model.firebase.ModeType.Companion.funny
import com.laks.tvseries.core.data.model.firebase.ModeType.Companion.impressive
import com.laks.tvseries.core.data.model.firebase.ModeType.Companion.musical
import com.laks.tvseries.core.data.model.firebase.ModeType.Companion.pandoraChoose
import com.laks.tvseries.core.data.model.firebase.ModeType.Companion.real
import com.laks.tvseries.core.data.model.firebase.ModeType.Companion.romantic
import com.laks.tvseries.core.data.model.firebase.ModeType.Companion.scienceFiction
import com.laks.tvseries.core.data.model.firebase.ModeType.Companion.thriller
import com.laks.tvseries.core.data.model.firebase.ModeType.Companion.war

data class FbMovieDataModel (
        var id: Long? = null,
        var name: String? = null
)

@LongDef(drama, friend, scienceFiction, funny, impressive, thriller,
        romantic, fantastic, adventure, war, real, musical, classic, pandoraChoose)
@Retention(AnnotationRetention.SOURCE)
annotation class ModeType {
    companion object {
        const val drama = 1L
        const val friend = 2L
        const val scienceFiction = 3L
        const val funny = 4L
        const val impressive = 5L
        const val thriller = 6L
        const val romantic = 7L
        const val fantastic = 8L
        const val adventure = 9L
        const val war = 10L
        const val real = 11L
        const val musical = 12L
        const val classic = 13L
        const val pandoraChoose = 14L
    }
}