package com.iavariav.kbmonline.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.iavariav.kbmonline.ui.LoginActivity;
import com.iavariav.kbmonline.rest.ApiConfig;
import com.iavariav.kbmonline.rest.ApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public final class Config {
    public static final String SHARED_PREF_NAME = "KBM_TELKOM";
    public static final String SHARED_PREF_ID = "KBM_ID_USER";
    public static final String SHARED_PREF_USERNAME = "KBM_USERNAME";
    public static final String SHARED_PREF_RULE = "KBM_RULE";
    public static final String SHARED_PREF_REGID_FIREBASE = "KBM_REGID_FIREBASE";
    public static final String SHARED_PREF_ERROR_MSG = "KBM_ERROR_MSG";

    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;


    public static void sharedPref(Context context, String idUser, String username, String rule) {
        SharedPreferences sharedPreferences  = context.getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Config.SHARED_PREF_ID, idUser);
        editor.putString(Config.SHARED_PREF_USERNAME, username);
        editor.putString(Config.SHARED_PREF_RULE, rule);


        editor.apply();
    }

    public static void pushNotif(final Context context, String tittle, String message, String pushtype, String regid){
        ApiService apiService = ApiConfig.getApiService();
        apiService.postDataNotif(tittle, message, pushtype, regid)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                String tittle = jsonObject.optString("tittle");
                                Toast.makeText(context, "" + tittle, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }

    public static void logout(Context context){
        SharedPreferences sharedPreferences  = context.getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Config.SHARED_PREF_ID, "");
        editor.putString(Config.SHARED_PREF_USERNAME, "");
        editor.putString(Config.SHARED_PREF_RULE, "");
        editor.apply();

        context.startActivity(new Intent(context, LoginActivity.class));
    }
}
