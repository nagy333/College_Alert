package com.example.collegealert.alarm

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.collegealert.MainActivity
import com.example.collegealert.R
import com.example.collegealert.utils.Constants.CHANNEL_ID
import com.example.collegealert.utils.Constants.STOP_ALARM

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) {
            Log.e("AlarmReceiver", "Context or Intent is null, aborting receiver.")
            return
        }
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val mediaPlayer: MediaPlayer = MediaPlayer.create(context,Settings.System.DEFAULT_ALARM_ALERT_URI)
        mediaPlayer.isLooping = true


        if (intent.action == STOP_ALARM){
            val id = intent.getIntExtra("Id",2)
            NotificationManagerCompat.from(context).cancel(id)
            mediaPlayer.stop()
            mediaPlayer.release()
            val pIntent = PendingIntent.getBroadcast(
                context,
                id,
                Intent(context,AlarmReceiver::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager.cancel(pIntent)
        }
        val title = intent.getStringExtra("title") ?: "no title"
        val id = intent.getIntExtra("id",0)

        showNotification(context,mediaPlayer, title = title, alarmId = id)
    }

    private fun showNotification(
        context: Context,
        mediaPlayer: MediaPlayer,
        alarmId: Int,
        title: String){
        val intent = Intent(context,MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            alarmId,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val stopPendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            Intent(context,AlarmReceiver::class.java).apply {
                action = STOP_ALARM
                putExtra("Id",alarmId)

            },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context,CHANNEL_ID)
            .setSmallIcon(R.drawable.time)
            .setAutoCancel(true)
            .setContentTitle(title)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent)
            .addAction(
                R.drawable.ic_launcher_background,
                "Stop",
                stopPendingIntent

            )
        mediaPlayer.start()
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat.from(context).notify(alarmId,notification.build())
        }

    }

}