package com.iavariav.kbmonline.rest;

import com.iavariav.kbmonline.model.AtasanAprovalModel;
import com.iavariav.kbmonline.model.LoginModel;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api_login.php")
    Call<LoginModel> login(@Query("NIK_USER") String username,
                           @Query("PASSWORD_USER") String password);


    @GET("api_get.php")
    Call<ArrayList<AtasanAprovalModel>> getAllData(
            @Query("change") String change
    );
    @GET("api_get.php")
    Call<ArrayList<AtasanAprovalModel>> getDataHistoriAtasan(
            @Query("change") String change,
            @Query("ID_USER_ATASAN") String idUser
    );


//    @FormUrlEncoded
//    @POST("api_update_validator.php")
//    Call<ResponseErrorModel> updateStatusValidator(
//            @Field("id_pelapor") String id_pelapor,
//            @Field("id_user_validator") String id_user_validator,
//            @Field("status") String status);

    @FormUrlEncoded
    @POST("approvel/api_update_aprovel.php")
    Call<ResponseBody> updateStatusPemesanan(
            @Field("ID_PEMESANAN") String idPesanan,
            @Field("ID_USER_ATASAN") String idUserAtasan,
            @Field("STATUS_PEMESANAN") String status
    );

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
