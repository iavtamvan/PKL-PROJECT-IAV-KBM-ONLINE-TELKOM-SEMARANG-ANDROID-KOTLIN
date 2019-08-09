package com.iavariav.kbmonline.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.iavariav.kbmonline.helper.Config;
import com.iavariav.kbmonline.model.LoginModel;
import com.iavariav.kbmonline.rest.ApiConfig;
import com.iavariav.kbmonline.rest.ApiService;
import com.iavariav.kbmonline.ui.atasan.AtasanActivity;
import com.iavariav.kbmonline.ui.user.UserActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter {
    private LoginModel loginModel;

    public void login(final Context context, String username, String password){
        ApiService apiService = ApiConfig.getApiService();
        apiService.login(username, password)
                .enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                        if (response.isSuccessful()){
                            loginModel = response.body();
                            Toast.makeText(context, "" + loginModel.getUsername(), Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(Config.SHARED_PREF_ID, loginModel.getId());
                            editor.putString(Config.SHARED_PREF_NAMA_LENGKAP, loginModel.getUsername());
                            editor.putString(Config.SHARED_PREF_RULE, loginModel.getRule());
                            editor.apply();

                            String rule= loginModel.getRule();
                            if (rule.contains("pimpinan")){
                                context.startActivity(new Intent(context, AtasanActivity.class));
                            } else if (rule.contains("user")){
                                context.startActivity(new Intent(context, UserActivity.class));
                                Toast.makeText(context, "User RUle", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginModel> call, Throwable t) {
                        Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
