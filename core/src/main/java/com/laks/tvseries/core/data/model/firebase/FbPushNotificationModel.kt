package com.laks.tvseries.core.data.model.firebase

import androidx.annotation.IntDef
import com.laks.tvseries.core.data.model.MediaType

data class FbPushNotificationModel (
    var id: Long? = null,
    var name: String? = null,
    var image: String? = null,
    @MediaType var mediaType: String? = null,
    @NotificationType var notificationType: Int? = null,
    @ScreenType var screenType: Int? = null
)

@IntDef(ScreenType.splash, ScreenType.detail)
@Retention(AnnotationRetention.SOURCE)
annotation class ScreenType {
    companion object {
        const val splash = 1
        const val detail = 2
    }
}

@IntDef(NotificationType.default, NotificationType.viewDetail)
@Retention(AnnotationRetention.SOURCE)
annotation class NotificationType {
    companion object {
        const val default = 1
        const val viewDetail = 2
        const val weekRandom = 3
    }
}