package com.iavariav.kbmonline.helper

import android.content.Context
import android.content.Intent
import android.widget.Toast

import com.iavariav.kbmonline.ui.login.LoginActivity
import com.iavariav.kbmonline.rest.ApiConfig

import org.json.JSONException
import org.json.JSONObject

import java.io.IOException

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import android.content.Context.MODE_PRIVATE
import androidx.fragment.app.FragmentActivity

object Config {
    val SHARED_PREF_NAME = "KBM_TELKOM"
    val SHARED_PREF_ID = "KBM_ID_USER"
    val SHARED_PREF_NAMA_LENGKAP = "KBM_USERNAME"
    val SHARED_PREF_RULE = "KBM_RULE"
    val SHARED_PREF_REGID_FIREBASE = "KBM_REGID_FIREBASE"
    val SHARED_PREF_ERROR_MSG = "KBM_ERROR_MSG"

    // global topic to receive app wide push notifications
    val TOPIC_GLOBAL = "global"

    // broadcast receiver intent filters
    val REGISTRATION_COMPLETE = "registrationComplete"
    val PUSH_NOTIFICATION = "pushNotification"

    // id to handle the notification in the notification tray
    val NOTIFICATION_ID = 100
    val NOTIFICATION_ID_BIG_IMAGE = 101


    fun sharedPref(context: Context, idUser: String, username: String, rule: String) {
        val sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString(Config.SHARED_PREF_ID, idUser)
        editor.putString(Config.SHARED_PREF_NAMA_LENGKAP, username)
        editor.putString(Config.SHARED_PREF_RULE, rule)


        editor.apply()
    }

    fun pushNotif(context: FragmentActivity?, tittle: String, message: String, pushtype: String, regid: String?) {
        val apiService = ApiConfig.apiService
        regid?.let {
            apiService.postDataNotif(tittle, message, pushtype, it)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            try {
                                val jsonObject = JSONObject(response.body()!!.string())
                                val tittle = jsonObject.optString("tittle")
                                //                                Toast.makeText(context, "" + tittle, Toast.LENGTH_SHORT).show();
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }

                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(context, "" + t.message, Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

    fun logout(context: Context) {
        val sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString(Config.SHARED_PREF_ID, "")
        editor.putString(Config.SHARED_PREF_NAMA_LENGKAP, "")
        editor.putString(Config.SHARED_PREF_RULE, "")
        //        editor.putString("regId", "");
        editor.apply()

        context.startActivity(Intent(context, LoginActivity::class.java))
    }
}
