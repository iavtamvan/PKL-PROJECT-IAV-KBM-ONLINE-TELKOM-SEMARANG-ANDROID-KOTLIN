package com.iavariav.kbmonline.rest;

import com.iavariav.kbmonline.model.LoginModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api_login.php")
    Call<LoginModel> login(@Query("username") String username,
                           @Query("password") String password);


//    @GET("api_get.php")
//    Call<ArrayList<PelaporModel>> getAllData(
//            @Query("change") String change
//    );
//    @GET("api_get.php")
//    Call<ArrayList<PelaporModel>> getHistory(
//            @Query("change") String change,
//            @Query("id_user_validator") String idUser
//    );
//


    @GET("firebase")
    Call<ResponseBody> postDataNotif(
            @Query("title") String title,
            @Query("message") String message,
            @Query("push_type") String push_type,
            @Query("regId") String regId

    );


}
