package com.iavariav.kbmonline.ui.login

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

import com.google.firebase.messaging.FirebaseMessaging
import com.iavariav.kbmonline.BuildConfig
import com.iavariav.kbmonline.R
import com.iavariav.kbmonline.helper.Config
import com.iavariav.kbmonline.helper.NotificationUtils
import com.iavariav.kbmonline.ui.SplashActivity

import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

class LoginActivity : AppCompatActivity() {

    private var edtNik: EditText? = null
    private var edtPassword: EditText? = null
    private var btnLogin: Button? = null
    private var tvVersion: TextView? = null

    private var loginPresenter: LoginPresenter? = null

    private var mRegistrationBroadcastReceiver: BroadcastReceiver? = null
    private var regId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
        supportActionBar!!.hide()
        loginPresenter = LoginPresenter()
        methodRequiresTwoPermission()
        tvVersion!!.text = "Version apps " + BuildConfig.VERSION_CODE + " (build " + BuildConfig.VERSION_NAME + ")"
        btnLogin!!.setOnClickListener {
            loginPresenter!!.login(this@LoginActivity,
                    edtNik!!.text.toString().trim { it <= ' ' },
                    edtPassword!!.text.toString().trim { it <= ' ' })
            finishAffinity()
        }

        displayFirebaseRegId()
        mRegistrationBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {

                // checking for type intent filter
                if (intent.action == Config.REGISTRATION_COMPLETE) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL)

                    displayFirebaseRegId()

                } else if (intent.action == Config.PUSH_NOTIFICATION) {
                    // new push notification is received

                    val message = intent.getStringExtra("message")
                }
            }
        }
    }


    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private fun methodRequiresTwoPermission() {
        val perms = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            // Already have permission, do the thing
            // ...
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.app_name),
                    RC_CAMERA_AND_LOCATION, *perms)
        }
    }


    override fun onResume() {
        super.onResume()

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver!!,
                IntentFilter(Config.REGISTRATION_COMPLETE))

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver!!,
                IntentFilter(Config.PUSH_NOTIFICATION))

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(applicationContext)
    }

    override fun onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver!!)
        super.onPause()
    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private fun displayFirebaseRegId() {
        val pref = applicationContext.getSharedPreferences(Config.SHARED_PREF_NAME, 0)
        regId = pref.getString("regId", null)
        Log.e(TAG, "Firebase reg id: " + regId!!)
    }


    private fun initView() {
        edtNik = findViewById(R.id.edt_nik)
        edtPassword = findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.btn_login)
        tvVersion = findViewById(R.id.tv_version)
    }

    companion object {

        private const val RC_CAMERA_AND_LOCATION = 1
        private val TAG = SplashActivity::class.java.simpleName
    }
}
