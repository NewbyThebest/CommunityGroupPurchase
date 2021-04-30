package com.lwj.cgp.common;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.lwj.cgp.base.BaseActivity;
import com.lwj.cgp.R;
import com.lwj.cgp.base.CommonData;
import com.lwj.cgp.base.MainManager;
import com.lwj.cgp.data.UserData;
import com.tencent.mmkv.MMKV;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


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
                changePsw();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    void changePsw() {
        UserData data = CommonData.getCommonData().getUserInfo();
        String old = oldPsw.getText().toString();
        String new1 = newPsw.getText().toString();
        String new2 = newPsw2.getText().toString();
        if (TextUtils.isEmpty(old) || TextUtils.isEmpty(new1) || TextUtils.isEmpty(new2)) {
            Toast.makeText(ChangePswActivity.this, "密码不能为空，请重新输入！", Toast.LENGTH_LONG).show();
        } else if (!old.equals(data.password)) {
            Toast.makeText(ChangePswActivity.this, "当前密码不正确，请重新输入！", Toast.LENGTH_LONG).show();
        } else if (!new1.equals(new2)) {
            Toast.makeText(ChangePswActivity.this, "两次密码不相同，请重新输入！", Toast.LENGTH_LONG).show();
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("url", CommonData.getCommonData().getUserInfo().url);
            map.put("psw", new1);
            map.put("name", CommonData.getCommonData().getUserInfo().name);
            map.put("uid", CommonData.getCommonData().getUserInfo().uid);
            MainManager.getInstance().getNetService().updateUserInfo(map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Boolean>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(ChangePswActivity.this, "网络不佳，请重试！", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onNext(Boolean result) {
                            if (result) {
                                MMKV.defaultMMKV().encode("auto", false);
                                CommonData.getCommonData().getUserInfo().password = new1;
                                Toast.makeText(ChangePswActivity.this, "更新密码成功！", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(ChangePswActivity.this, "更新密码失败，请重试！", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}
