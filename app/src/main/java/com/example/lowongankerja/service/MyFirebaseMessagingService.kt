package com.example.lowongankerja.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.preference.PreferenceManager
import android.util.Log

import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.lowongankerja.BottomActivity
import com.example.lowongankerja.R

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

import java.net.HttpURLConnection
import java.net.URL
import java.util.Random

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private var notificationManager: NotificationManager? = null
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        
        val notificationIntent = Intent(this, BottomActivity::class.java)
        
//        if (BottomActivity.isAppRunning) {
//            //Some action
//        } else {
//            //Show notification as usual
//        }

        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this,
                0 /* Request code */, notificationIntent,
                PendingIntent.FLAG_ONE_SHOT)

        //You should use an actual ID instead
        val notificationId = Random().nextInt(60000)
        remoteMessage?.let {
            val bitmap = getBitmapFromUrl(remoteMessage.data["image-url"])

            val likeIntent = Intent(this, LikeService::class.java)
            likeIntent.putExtra(NOTIFICATION_ID_EXTRA, notificationId)
            likeIntent.putExtra(IMAGE_URL_EXTRA, remoteMessage.data["image-url"])
            val likePendingIntent = PendingIntent.getService(this,
                    notificationId + 1, likeIntent, PendingIntent.FLAG_ONE_SHOT)

            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setupChannels()
            }

            val notificationBuilder = NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                    .setLargeIcon(bitmap)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(remoteMessage!!.data["title"])
                    .setStyle(NotificationCompat.BigPictureStyle()
                            .setSummaryText(remoteMessage.data["body"])
                            .bigPicture(bitmap))/*Notification with Image*/
                    .setContentText(remoteMessage.data["body"])
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)

            notificationManager?.notify(notificationId, notificationBuilder.build())
        }
    }

    override fun onNewToken(token: String?) {
        Log.d("TAG", "Refreshed token: $token")

    }

    private fun getBitmapFromUrl(imageUrl: String?): Bitmap? {
        return try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            BitmapFactory.decodeStream(input)

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupChannels() {
        val adminChannelName = "channel_name"
        val adminChannelDescription = "channel_description"

        val adminChannel: NotificationChannel
        adminChannel = NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_LOW)
        adminChannel.description = adminChannelDescription
        adminChannel.enableLights(true)
        adminChannel.lightColor = Color.RED
        adminChannel.enableVibration(true)
        notificationManager?.createNotificationChannel(adminChannel)

    }

    companion object {

        private const val NOTIFICATION_ID_EXTRA = "notificationId"
        private const val IMAGE_URL_EXTRA = "imageUrl"
        private const val ADMIN_CHANNEL_ID = "admin_channel"
    }
}