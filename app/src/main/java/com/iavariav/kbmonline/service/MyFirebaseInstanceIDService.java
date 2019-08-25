package com.iavariav.kbmonline.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.iavariav.kbmonline.helper.Config;

public class MyFirebaseInstanceIDService extends IntentService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    public MyFirebaseInstanceIDService() {
        super(TAG);
    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token);
    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.INSTANCE.getSHARED_PREF_NAME(), 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.apply();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);

        // sending reg id to your server
        sendRegistrationToServer(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Config.INSTANCE.getREGISTRATION_COMPLETE());
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }
}

