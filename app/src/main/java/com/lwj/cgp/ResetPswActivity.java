package com.lwj.cgp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;


public class ResetPswActivity extends BaseActivity {
    private Button commit;
    private Button back;
    private EditText newPsw;
    private EditText newPsw2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_psw);
        commit = findViewById(R.id.commit);
        back = findViewById(R.id.back);
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
