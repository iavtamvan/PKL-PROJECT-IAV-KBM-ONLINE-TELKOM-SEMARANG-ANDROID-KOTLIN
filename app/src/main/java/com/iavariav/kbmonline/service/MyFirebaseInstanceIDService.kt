package com.iavariav.kbmonline.service

import android.app.IntentService
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log

import androidx.localbroadcastmanager.content.LocalBroadcastManager

import com.google.firebase.iid.FirebaseInstanceId
import com.iavariav.kbmonline.helper.Config

class MyFirebaseInstanceIDService : IntentService(TAG) {

    private fun sendRegistrationToServer(token: String?) {
        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token!!)
    }

    private fun storeRegIdInPref(token: String?) {
        val pref = applicationContext.getSharedPreferences(Config.SHARED_PREF_NAME, 0)
        val editor = pref.edit()
        editor.putString("regId", token)
        editor.apply()
    }

    override fun onHandleIntent(intent: Intent?) {
        val refreshedToken = FirebaseInstanceId.getInstance().token

        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken)

        // sending reg id to your server
        sendRegistrationToServer(refreshedToken)

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        val registrationComplete = Intent(Config.REGISTRATION_COMPLETE)
        registrationComplete.putExtra("token", refreshedToken)
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete)
    }

    companion object {
        private val TAG = MyFirebaseInstanceIDService::class.java.simpleName
    }
}

