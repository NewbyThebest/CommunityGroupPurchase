package com.lwj.cgp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;


public class ChangePswActivity extends BaseActivity {
    private Button commit;
    private Button back;
    private EditText oldPsw;
    private EditText newPsw;
    private EditText newPsw2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_psw);
        commit = findViewById(R.id.commit);
        back = findViewById(R.id.back);
        oldPsw = findViewById(R.id.current_psw);
        newPsw = findViewById(R.id.new_password);
        newPsw2 = findViewById(R.id.new_password2);

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    void checkUserInfo() {


    }
}
