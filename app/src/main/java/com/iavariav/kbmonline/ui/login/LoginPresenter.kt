package com.iavariav.kbmonline.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast

import com.iavariav.kbmonline.helper.Config
import com.iavariav.kbmonline.model.LoginModel
import com.iavariav.kbmonline.rest.ApiConfig
import com.iavariav.kbmonline.rest.ApiService
import com.iavariav.kbmonline.ui.atasan.AtasanActivity
import com.iavariav.kbmonline.ui.user.UserActivity

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter {
    private var loginModel: LoginModel? = null

    fun login(context: Context, username: String, password: String) {
        val apiService = ApiConfig.apiService
        apiService.login(username, password)
                .enqueue(object : Callback<LoginModel> {
                    override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
                        if (response.isSuccessful) {
                            loginModel = response.body()
                            //                            Toast.makeText(context, "" + loginModel.getUsername(), Toast.LENGTH_SHORT).show();
                            val sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString(Config.SHARED_PREF_ID, loginModel!!.id)
                            editor.putString(Config.SHARED_PREF_NAMA_LENGKAP, loginModel!!.username)
                            editor.putString(Config.SHARED_PREF_RULE, loginModel!!.rule)
                            editor.apply()
                            val regID = sharedPreferences.getString("regId", "")
                            updateRegID(context, regID, loginModel!!.id)

                            val rule = loginModel!!.rule
                            if (rule!!.contains("pimpinan")) {
                                context.startActivity(Intent(context, AtasanActivity::class.java))
                            } else if (rule.contains("user")) {
                                context.startActivity(Intent(context, UserActivity::class.java))
                                //                                Toast.makeText(context, "User RUle", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                        Toast.makeText(context, "" + t.message, Toast.LENGTH_SHORT).show()

                    }
                })
    }

    private fun updateRegID(context: Context, regID: String?, idUser: String?) {
        val apiService = ApiConfig.apiService
        apiService.updateRegID(regID!!, idUser!!)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            //                            Toast.makeText(context, "Berhasil reg id", Toast.LENGTH_SHORT).show();

                            (context as LoginActivity).finishAffinity()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(context, "" + t.message, Toast.LENGTH_SHORT).show()
                    }
                })
    }
}
