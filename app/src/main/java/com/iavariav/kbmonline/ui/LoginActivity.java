package com.iavariav.kbmonline.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.iavariav.kbmonline.R;

public class LoginActivity extends AppCompatActivity {

    private EditText edtNik;
    private EditText edtPassword;
    private Button btnLogin;

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        loginPresenter = new LoginPresenter();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPresenter.login(LoginActivity.this,
                        edtNik.getText().toString().trim(),
                        edtPassword.getText().toString().trim());
                finishAffinity();
            }
        });
    }

    private void initView() {
        edtNik = findViewById(R.id.edt_nik);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
    }
}
