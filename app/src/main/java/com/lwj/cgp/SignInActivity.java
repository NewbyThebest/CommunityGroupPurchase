package com.lwj.cgp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SignInActivity extends BaseActivity implements View.OnClickListener {
    private Button sign;
    private Button back;
    private EditText user;
    private EditText password;
    private TextView userType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        sign = findViewById(R.id.signin);
        user = findViewById(R.id.username);
        password = findViewById(R.id.password);
        back = findViewById(R.id.back);
        userType = findViewById(R.id.userType);
        sign.setOnClickListener(this);
        back.setOnClickListener(this);
        userType.setOnClickListener(this);

    }

    void checkUserInfo() {
        String etUser = user.getText().toString();
        String etPsw = password.getText().toString();
        if (TextUtils.isEmpty(etUser)) {
            Toast.makeText(this, "用户名不能为空，请重新输入！", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(etPsw)) {
            Toast.makeText(this, "密码不能为空，请重新输入！", Toast.LENGTH_LONG).show();
            return;
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin:
                checkUserInfo();
                break;
            case R.id.back:
                onBackPressed();
                break;
        }
    }
}
