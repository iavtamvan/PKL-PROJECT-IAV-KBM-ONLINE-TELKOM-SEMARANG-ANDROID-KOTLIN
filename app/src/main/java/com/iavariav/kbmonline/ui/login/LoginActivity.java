package com.iavariav.kbmonline.ui.login;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessaging;
import com.iavariav.kbmonline.BuildConfig;
import com.iavariav.kbmonline.R;
import com.iavariav.kbmonline.helper.Config;
import com.iavariav.kbmonline.helper.NotificationUtils;
import com.iavariav.kbmonline.ui.SplashActivity;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_CAMERA_AND_LOCATION = 1;

    private EditText edtNik;
    private EditText edtPassword;
    private Button btnLogin;
    private TextView tvVersion;

    private LoginPresenter loginPresenter;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private static final String TAG = SplashActivity.class.getSimpleName();
    private String regId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        getSupportActionBar().hide();
        loginPresenter = new LoginPresenter();
        methodRequiresTwoPermission();
        tvVersion.setText("Version apps " +BuildConfig.VERSION_CODE + " (build " + BuildConfig.VERSION_NAME + ")");
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPresenter.login(LoginActivity.this,
                        edtNik.getText().toString().trim(),
                        edtPassword.getText().toString().trim());
                finishAffinity();
            }
        });

        displayFirebaseRegId();
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.INSTANCE.getREGISTRATION_COMPLETE())) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.INSTANCE.getTOPIC_GLOBAL());

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.INSTANCE.getPUSH_NOTIFICATION())) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");
                }
            }
        };
    }


    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.app_name),
                    RC_CAMERA_AND_LOCATION, perms);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.INSTANCE.getREGISTRATION_COMPLETE()));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.INSTANCE.getPUSH_NOTIFICATION()));

        // clear the notification area when the app is opened
        NotificationUtils.Companion.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.INSTANCE.getSHARED_PREF_NAME(), 0);
        regId = pref.getString("regId", null);
        Log.e(TAG, "Firebase reg id: " + regId);
    }


    private void initView() {
        edtNik = findViewById(R.id.edt_nik);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        tvVersion = findViewById(R.id.tv_version);
    }
}
