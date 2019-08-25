package com.iavariav.kbmonline.ui.atasan.presenter

import android.content.Context
import android.content.Intent
import android.widget.Toast

import com.iavariav.kbmonline.model.ErrorModel
import com.iavariav.kbmonline.rest.ApiConfig
import com.iavariav.kbmonline.rest.ApiService

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DaftarUserPresenter {
    private var errorModel: ErrorModel? = null
    fun daftarkanUser(context: Context, namaUser: String, nik: String, password: String, regID: String) {
        val apiService = ApiConfig.apiService
        apiService.postRegisterUser(namaUser, nik, password, regID)
                .enqueue(object : Callback<ErrorModel> {
                    override fun onResponse(call: Call<ErrorModel>, response: Response<ErrorModel>) {
                        if (response.isSuccessful) {
                            errorModel = response.body()
                            Toast.makeText(context, "" + errorModel!!.errorMsg!!, Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "" + errorModel!!.errorMsg!!, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ErrorModel>, t: Throwable) {
                        Toast.makeText(context, "" + t.message, Toast.LENGTH_SHORT).show()
                    }
                })
    }
}
