package com.example.sync.clipboard
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class ClipboardService : Service() {

    private lateinit var clipboardManager: ClipboardManager

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "ClipboardServiceChannel"
        private const val NOTIFICATION_ID = 101
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.addPrimaryClipChangedListener {
            val clipData = clipboardManager.primaryClip
            val item = clipData?.getItemAt(0)
            val clipboardText = item?.text.toString()
            Log.i("ClipboardService", clipboardText)
        }

        createNotificationChannel()
        val notification = createNotification()

        // Start the service as a foreground service
        startForeground(NOTIFICATION_ID, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        clipboardManager.removePrimaryClipChangedListener {}
    }

    private fun createNotificationChannel() {
        val name = "Clipboard Service"
        val descriptionText = "Clipboard Service Notification Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification(): Notification {
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Clipboard Service")
            .setContentText("Running...")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setColor(ContextCompat.getColor(this, R.color.black))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        return notificationBuilder.build()
    }
}

