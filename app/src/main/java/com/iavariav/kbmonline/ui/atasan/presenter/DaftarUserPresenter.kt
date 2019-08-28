package com.iavariav.kbmonline.ui.atasan.presenter

import android.R
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity

import com.iavariav.kbmonline.model.ErrorModel
import com.iavariav.kbmonline.rest.ApiConfig
import com.iavariav.kbmonline.ui.atasan.AtasanActivity
import com.iavariav.kbmonline.ui.user.UserActivity
import com.yarolegovich.lovelydialog.LovelyStandardDialog

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DaftarUserPresenter {
    private var errorModel: ErrorModel? = null
    fun daftarkanUser(context: FragmentActivity?, namaUser: String, nik: String, password: String, regID: String) {
        val apiService = ApiConfig.apiService
        apiService.postRegisterUser(namaUser, nik, password, regID)
                .enqueue(object : Callback<ErrorModel> {
                    override fun onResponse(call: Call<ErrorModel>, response: Response<ErrorModel>) {
                        if (response.isSuccessful) {
                            errorModel = response.body()
                            Toast.makeText(context, "" + errorModel!!.errorMsg!!, Toast.LENGTH_SHORT).show()
                            LovelyStandardDialog(context, LovelyStandardDialog.ButtonLayout.VERTICAL)
                                    .setTopColorRes(R.color.holo_red_light)
                                    .setButtonsColorRes(R.color.holo_red_light)
                                    .setIcon(R.drawable.stat_sys_upload_done)
                                    .setTitle("Sukses Daftar")
                                    .setMessage("Mohon tunggu persetujuan...")
                                    .setPositiveButton(android.R.string.ok, object : View.OnClickListener {
                                        override fun onClick(v: View) {
                                            Toast.makeText(context, "Ok Makasih", Toast.LENGTH_SHORT).show()
                                            (context as AtasanActivity).setDaftarUser()
                                        }
                                    })
                                    .setNegativeButton("", View.OnClickListener {
                                        Toast.makeText(context, "Ok Makasih", Toast.LENGTH_SHORT).show()
                                        (context as AtasanActivity).setDaftarUser()
                                    })
                                    .show()
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
