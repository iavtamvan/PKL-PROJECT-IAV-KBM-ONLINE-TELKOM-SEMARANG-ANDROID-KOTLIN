package com.iavariav.kbmonline.rest

import com.iavariav.kbmonline.model.ErrorModel
import com.iavariav.kbmonline.model.LoginModel
import com.iavariav.kbmonline.model.MobilModel
import com.iavariav.kbmonline.model.PemesananModel
import com.iavariav.kbmonline.model.UserModel

import java.util.ArrayList

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("api_login.php")
    fun login(@Query("NIK_USER") username: String,
              @Query("PASSWORD_USER") password: String): Call<LoginModel>


    @GET("api_get.php")
    fun getAllData(
            @Query("change") change: String
    ): Call<ArrayList<PemesananModel>>

    @GET("api_get.php")
    fun getDataHistoriAtasan(
            @Query("change") change: String,
            @Query("ID_USER_ATASAN") idUser: String
    ): Call<ArrayList<PemesananModel>>

    @GET("api_get.php")
    fun getDataUser(
            @Query("change") change: String
    ): Call<ArrayList<UserModel>>

    @GET("api_get.php")
    fun getDataMobil(
            @Query("change") change: String
    ): Call<ArrayList<MobilModel>>

    @GET("api_get.php")
    fun getDataUserHistoriPemesanan(
            @Query("change") change: String,
            @Query("NAMA_PEMESAN") NAMA_PEMESAN: String
    ): Call<ArrayList<PemesananModel>>

    @GET("api_get.php")
    fun getDataMobilByStatus(
            @Query("change") change: String,
            @Query("TYPE_MOBIL") TYPE_MOBIL: String
    ): Call<ArrayList<MobilModel>>

    @FormUrlEncoded
    @POST("user/api_tambah_registrasi.php")
    fun postRegisterUser(
            @Field("NAMA_USER") NAMA_USER: String,
            @Field("NIK_USER") NIK_USER: String,
            @Field("PASSWORD_USER") PASSWORD_USER: String,
            @Field("REG_ID") REG_ID: String
    ): Call<ErrorModel>

    @FormUrlEncoded
    @POST("approvel/api_update_aprovel.php")
    fun updateStatusPemesanan(
            @Field("ID_PEMESANAN") idPesanan: String,
            @Field("ID_USER_ATASAN") idUserAtasan: String,
            @Field("STATUS_PEMESANAN") status: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("api_update_reg_id.php")
    fun updateRegID(
            @Field("REG_ID") regID: String,
            @Field("ID_USER") idUser: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST("user/api_tambah_pemesanan.php")
    fun postDataPemesanan(
            @Field("ID_USER_ATASAN") ID_USER_ATASAN: String,
            @Field("NAMA_PEMESAN") NAMA_PEMESAN: String,
            @Field("JENIS_KEPERLUAN") JENIS_KEPERLUAN: String,
            @Field("JENIS_PEMESANAN") JENIS_PEMESANAN: String,
            @Field("JENIS_KENDARAAN") JENIS_KENDARAAN: String,
            @Field("KEBERANGKATAN_KAWASAN") KEBERANGKATAN_KAWASAN: String,
            @Field("KEBERANGKATAN_WITEL") KEBERANGKATAN_WITEL: String,
            @Field("KEBERANGKATAN_AREA_POOL") KEBERANGKATAN_AREA_POOL: String,
            @Field("TUJUAN_ALAMAT_JEMPUT") TUJUAN_ALAMAT_JEMPUT: String,
            @Field("TUJUAN_AREA") TUJUAN_AREA: String,
            @Field("TUJUAN_ALAMAT_DETAIL_MAPS") TUJUAN_ALAMAT_DETAIL_MAPS: String,
            @Field("LAT_AWAL") LAT_AWAL: String,
            @Field("LONG_AWAL") LONG_AWAL: String,
            @Field("LAT_TUJUAN") LAT_TUJUAN: String,
            @Field("LONG_TUJUAN") LONG_TUJUAN: String,
            @Field("WAKTU_KEBERANGKATAN") WAKTU_KEBERANGKATAN: String,
            @Field("WAKTU_KEPULANGAN") WAKTU_KEPULANGAN: String,
            @Field("NO_TELEPON_KANTOR") NO_TELEPON_KANTOR: String,
            @Field("NO_HP") NO_HP: String,
            @Field("JUMLAH_PENUMPANG") JUMLAH_PENUMPANG: String,
            @Field("ISI_PENUMPANG") ISI_PENUMPANG: String,
            @Field("KETERANGAN") KETERANGAN: String,
            @Field("JARAK_PER_KM") JARAK_PER_KM: String,
            @Field("BENSIN_PER_LITER") BENSIN_PER_LITER: String,
            @Field("NAMA_ATASAN") NAMA_ATASAN: String,
            @Field("REG_TOKEN_PEMESANAN") REG_TOKEN_PEMESANAN: String,
            @Field("REG_ID") REG_ID: String
    ): Call<ResponseBody>

    //    @GET("api_get.php")
    //    Call<ArrayList<PelaporModel>> getHistory(
    //            @Query("change") String change,
    //            @Query("id_user_validator") String idUser
    //    );
    //


    @GET("firebase")
    fun postDataNotif(
            @Query("title") title: String,
            @Query("message") message: String,
            @Query("push_type") push_type: String,
            @Query("regId") regId: String

    ): Call<ResponseBody>


}
