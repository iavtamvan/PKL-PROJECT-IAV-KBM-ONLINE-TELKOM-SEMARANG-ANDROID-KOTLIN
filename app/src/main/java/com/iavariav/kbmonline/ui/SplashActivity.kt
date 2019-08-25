package com.iavariav.kbmonline.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.widget.Toast

import com.iavariav.kbmonline.R
import com.iavariav.kbmonline.helper.Config
import com.iavariav.kbmonline.ui.atasan.AtasanActivity
import com.iavariav.kbmonline.ui.login.LoginActivity
import com.iavariav.kbmonline.ui.user.UserActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar!!.hide()

        Handler().postDelayed({
            val sp = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE)
            val username = sp.getString(Config.SHARED_PREF_NAMA_LENGKAP, "")
            val rule = sp.getString(Config.SHARED_PREF_RULE, "")

            // TODO jika belum masuk ke LoginActivity
            if (username!!.equals("", ignoreCase = true) || TextUtils.isEmpty(username)) {
                finishAffinity()
                startActivity(Intent(applicationContext, LoginActivity::class.java))
            } else {
                if (rule!!.contains("pimpinan")) {
                    finishAffinity()
                    startActivity(Intent(applicationContext, AtasanActivity::class.java))
                } else if (rule.contains("user")) {
                    //                        Toast.makeText(SplashActivity.this, "rule : " + rule, Toast.LENGTH_SHORT).show();
                    finishAffinity()
                    startActivity(Intent(applicationContext, UserActivity::class.java))
                }

            }// TODO jika sudah nantinya akan masuk ke Home
        }, 2000)
    }
}
