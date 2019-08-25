package com.iavariav.kbmonline.rest.uploadImage

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by LENOVO on 26/10/2017.
 */


class RetroClientUploadImage {
    companion object {


//    private static final String ROOT_URL = "http://sig.upgris.ac.id/api_iav/image/";
        //    private static final String ROOT_URL = "http://inssang.can.web.id/images/";
        private val ROOT_URL = "http://devlop.can.web.id/uploads/client_profile_images/3/"

        private val retrofitClient: Retrofit
            get() = Retrofit.Builder()
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build()

        val service: APIServiceUploadImage
            get() = retrofitClient.create(APIServiceUploadImage::class.java)
    }
}
