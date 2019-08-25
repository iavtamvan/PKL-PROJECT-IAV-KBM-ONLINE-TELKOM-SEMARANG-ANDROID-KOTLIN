package com.iavariav.kbmonline.rest.uploadImage

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * Created by LENOVO on 25/10/2017.
 */

interface APIServiceUploadImage {

    @Multipart
    @POST("upload.php")
    fun postIMmage(@Part image: MultipartBody.Part): Call<Result>


}
