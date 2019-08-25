package com.iavariav.kbmonline.ui.atasan.presenter

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.iavariav.kbmonline.model.UserModel
import com.iavariav.kbmonline.rest.ApiConfig
import com.iavariav.kbmonline.rest.ApiService
import com.iavariav.kbmonline.ui.atasan.adapter.DataUserAdapter

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataUserPresenter {
    private var dataUserAdapter: DataUserAdapter? = null
    private var userModels: ArrayList<UserModel>? = null
    fun dataUSer(context: Context, change: String, recyclerView: RecyclerView, div: LinearLayout) {
        userModels = ArrayList()
        val apiService = ApiConfig.apiService
        apiService.getDataUser(change)
                .enqueue(object : Callback<ArrayList<UserModel>> {
                    override fun onResponse(call: Call<ArrayList<UserModel>>, response: Response<ArrayList<UserModel>>) {
                        if (response.isSuccessful) {
                            userModels = response.body()
                            dataUserAdapter = DataUserAdapter(context, userModels!!)
                            recyclerView.layoutManager = LinearLayoutManager(context)
                            recyclerView.adapter = dataUserAdapter
                            dataUserAdapter!!.notifyDataSetChanged()

                            if (userModels!!.isEmpty()) {
                                recyclerView.visibility = View.GONE
                                div.visibility = View.VISIBLE
                            } else {
                                recyclerView.visibility = View.VISIBLE
                                div.visibility = View.GONE
                            }
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<UserModel>>, t: Throwable) {
                        Toast.makeText(context, "" + t.message, Toast.LENGTH_SHORT).show()
                    }
                })
    }
}
