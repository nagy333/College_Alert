package com.example.collegealert.utils


import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.example.collegealert.R
import dagger.hilt.android.HiltAndroidApp
import com.example.collegealert.utils.Constants.CHANNEL_ID

@HiltAndroidApp
class CollegeAlertApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val name = getString(R.string.channel_name)
        val channelDesc = getString(R.string.channel_Desc)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val myChannel = NotificationChannel(CHANNEL_ID,name,importance)
        myChannel.description = channelDesc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(myChannel)
    }
}