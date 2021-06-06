package com.laks.tvseries.featurecategory.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.laks.tvseries.core.R
import com.laks.tvseries.core.cache.MemoryCache
import com.laks.tvseries.core.data.PandoraActivities
import com.laks.tvseries.core.data.model.firebase.NotificationType
import com.laks.tvseries.core.data.model.firebase.ScreenType
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.core.global.StoreShared
import com.laks.tvseries.core.util.isActivityCreated

class PandoraFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (!remoteMessage.data.isNullOrEmpty()) {
            sendNotification(remoteMessage.data)
        }
    }

    private fun sendNotification(data: Map<String, String> ) {

        val mediaID = data["mediaID"]
        val mediaType = data["mediaType"]
        val body: String? = data["body"]
        val title: String? = data["title"]
        val screenType = data["screenType"] as String
        val notificationType = data["notificationType"] as String

        mediaType?.let { MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_TYPE, it) }
        mediaID?.let { MemoryCache.cache.setMemoryCacheValue(GlobalConstants.MEDIA_DETAIL_ID, mediaID) }

        val intent = if(isActivityCreated(this)) {
            StoreShared(this).setIntKeyValue(GlobalConstants.SCREEN_TYPE, screenType.toInt())
            StoreShared(this).setBooleanKeyValue(GlobalConstants.IS_APP_KILL_NOTIFICATION, true)
            Intent(Intent.ACTION_VIEW).setClassName(this, PandoraActivities.splashScreenClassName)
        } else {
            getScreenCreateIntent(screenType)
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT)

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_toast_done)
            .setContentTitle(getContentTitle(notificationType, title!!))
            .setContentText(getContentText(notificationType, body!!))
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                "Channel pandora notification",
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

    private fun getContentTitle(notificationType: String, defaultTitle: String): String {
        return when (notificationType.toInt()) {
            NotificationType.viewDetail -> {
                getString(com.laks.tvseries.core.R.string.notification_movie_title)
            }
            NotificationType.weekRandom -> {
                getString(com.laks.tvseries.core.R.string.notification_random_movie_title)
            }
            else -> {
                defaultTitle
            }
        }
    }

    private fun getContentText(notificationType: String, defaultTitle: String): String {
        return when (notificationType.toInt()) {
            NotificationType.default -> {
                defaultTitle
            }
            NotificationType.viewDetail, NotificationType.weekRandom -> {
                getString(com.laks.tvseries.core.R.string.notification_random_movie_desc, defaultTitle)
            }
            else -> {
                defaultTitle
            }
        }
    }

    private fun getScreenCreateIntent(screenType: String): Intent {
        return when (screenType.toInt()) {
            ScreenType.splash -> {
                Intent(Intent.ACTION_VIEW).setClassName(this, PandoraActivities.splashScreenClassName)
            }
            ScreenType.detail -> {
                Intent(Intent.ACTION_VIEW).setClassName(this, PandoraActivities.movieDetailActivityClassName)
            }
            else -> {
                Intent(Intent.ACTION_VIEW).setClassName(this, PandoraActivities.splashScreenClassName)
            }
        }
    }

}
