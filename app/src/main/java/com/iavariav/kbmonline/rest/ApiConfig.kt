package com.iavariav.kbmonline.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    //                .baseUrl("http://192.168.1.18/~mac/kbm_online/")
    //                .baseUrl("http://10.81.11.13/~mac/kbm_online/")
    //                .baseUrl("http://sig.upgris.ac.id/api_iav/sertifikasi_android/")
    val apiService: ApiService
        get() {
            val retrofit = Retrofit.Builder()
                    .baseUrl("http://192.168.43.182/~mac/kbm_online/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(ApiService::class.java)
        }
}
