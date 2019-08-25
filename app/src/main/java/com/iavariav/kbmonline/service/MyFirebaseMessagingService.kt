package com.iavariav.kbmonline.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.provider.Settings
import android.text.TextUtils
import android.util.Log

import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.iavariav.kbmonline.R
import com.iavariav.kbmonline.helper.Config
import com.iavariav.kbmonline.helper.NotificationUtils
import com.iavariav.kbmonline.ui.SplashActivity

import org.json.JSONException
import org.json.JSONObject

class MyFirebaseMessagingService : FirebaseMessagingService() {


    private var notificationUtils: NotificationUtils? = null
    private var mNotificationManager: NotificationManager? = null
    private var mBuilder: NotificationCompat.Builder? = null

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Log.e(TAG, "From: " + remoteMessage!!.from!!)

        if (remoteMessage == null)
            return

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.notification!!.body!!)
            handleNotification(remoteMessage.notification!!.body)
        }

        // Check if message contains a data payload.
        if (remoteMessage.data.size > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.data.toString())

            try {
                val json = JSONObject(remoteMessage.data.toString())
                handleDataMessage(json)
            } catch (e: Exception) {
                Log.e(TAG, "Exception: " + e.message)
            }

        }
    }

    private fun handleNotification(message: String?) {
        if (!NotificationUtils.isAppIsInBackground(applicationContext)) {
            // app is in foreground, broadcast the push message
            val pushNotification = Intent(Config.PUSH_NOTIFICATION)
            pushNotification.putExtra("message", message)
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification)

            // play notification sound
            val notificationUtils = NotificationUtils(applicationContext)
            notificationUtils.playNotificationSound()
        } else {
            // If the app is in background, firebase itself handles the notification
        }
    }

    private fun handleDataMessage(json: JSONObject) {
        Log.e(TAG, "push json: $json")

        try {
            val data = json.getJSONObject("data")

            val title = data.getString("title")
            val message = data.getString("message")
            val isBackground = data.getBoolean("is_background")
            val imageUrl = data.getString("image")
            val timestamp = data.getString("timestamp")
            val payload = data.getJSONObject("payload")

            Log.e(TAG, "title: $title")
            Log.e(TAG, "message: $message")
            Log.e(TAG, "isBackground: $isBackground")
            Log.e(TAG, "payload: $payload")
            Log.e(TAG, "imageUrl: $imageUrl")
            Log.e(TAG, "timestamp: $timestamp")


            if (!NotificationUtils.isAppIsInBackground(applicationContext)) {
                // app is in foreground, broadcast the push message
                val pushNotification = Intent(Config.PUSH_NOTIFICATION)
                pushNotification.putExtra("message", message)
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification)

                // play notification sound
                val notificationUtils = NotificationUtils(applicationContext)
                notificationUtils.playNotificationSound()
            } else {
                // app is in background, show the notification in notification tray
                val resultIntent = Intent(applicationContext, SplashActivity::class.java)
                resultIntent.putExtra("message", message)

                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(applicationContext, title, message)
                } else {
                    // image is present, show notification with image
                    //                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
            }
        } catch (e: JSONException) {
            Log.e(TAG, "Json Exception: " + e.message)
        } catch (e: Exception) {
            Log.e(TAG, "Exception: " + e.message)
        }

    }

    /**
     * Showing notification with text only
     */

    fun showNotificationMessage(context: Context, title: String, message: String) {
        /**Creates an explicit intent for an Activity in your app */
        val resultIntent = Intent(context, SplashActivity::class.java)
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        val resultPendingIntent = PendingIntent.getActivity(context,
                0 /* Request code */, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)

        mBuilder = NotificationCompat.Builder(context)
        mBuilder!!.setSmallIcon(R.mipmap.ic_launcher)
        mBuilder!!.setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(false)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent)

        mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            assert(mNotificationManager != null)
            mBuilder!!.setChannelId(NOTIFICATION_CHANNEL_ID)
            mNotificationManager!!.createNotificationChannel(notificationChannel)
        }
        assert(mNotificationManager != null)
        mNotificationManager!!.notify(0 /* Request Code */, mBuilder!!.build())
    }

    /**
     * Showing notification with text and image
     */
    private fun showNotificationMessageWithBigImage(context: Context, title: String, message: String, timeStamp: String, intent: Intent, imageUrl: String) {
        notificationUtils = NotificationUtils(context)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        notificationUtils!!.showNotificationMessage(title, message, timeStamp, intent, imageUrl)
    }

    companion object {

        private val TAG = MyFirebaseMessagingService::class.java.simpleName
        val NOTIFICATION_CHANNEL_ID = "10001"
    }
}
