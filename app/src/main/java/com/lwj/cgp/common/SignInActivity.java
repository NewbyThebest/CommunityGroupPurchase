package com.lwj.cgp.common;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lwj.cgp.base.BaseActivity;
import com.lwj.cgp.base.MainManager;
import com.lwj.cgp.R;

import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SignInActivity extends BaseActivity implements View.OnClickListener {
    private static final int READ_PHONE_STATE = 1;
    private Button sign;
    private Button back;
    private EditText user;
    private EditText password;
    private EditText phone;
    private EditText code;
    private Button send;
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
        phone = findViewById(R.id.phone);
        code = findViewById(R.id.code);
        send = findViewById(R.id.send_code);
        sign.setOnClickListener(this);
        back.setOnClickListener(this);
        userType.setOnClickListener(this);
        send.setOnClickListener(this);
        SMSSDK.registerEventHandler(eh);

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
        addUserInfo();
//        checkCode();
    }

    void addUserInfo(){
        String uid = user.getText().toString();
        String psw = password.getText().toString();
        String p = phone.getText().toString();

        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        map.put("password", psw);
        map.put("type", "0");
        map.put("phone", p);

        MainManager.getInstance().getNetService().addUserInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(SignInActivity.this, "网络不佳，请重试！", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(Boolean result) {
                        if (result) {
                            Toast.makeText(SignInActivity.this, "注册成功！", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        } else {
                            Toast.makeText(SignInActivity.this, "用户已存在，请重新输入！", Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    void checkCode() {
        String p = phone.getText().toString();
        String c = code.getText().toString();
        SMSSDK.submitVerificationCode("86", p, c);
    }


    void setCode() {
        String p = phone.getText().toString();
        if (TextUtils.isEmpty(p)) {
            Toast.makeText(this, "手机号码不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            SMSSDK.getVerificationCode("86", p);
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
            case R.id.send_code:
                setCode();
                break;
        }
    }

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            int result = msg.arg1;
            int event = msg.arg2;
            Object data = msg.getData();
            if (result == SMSSDK.RESULT_COMPLETE) {
                //成功回调
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交短信、语音验证码成功
                    addUserInfo();
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取短信验证码成功
                    Toast.makeText(SignInActivity.this, "获取验证码成功！", Toast.LENGTH_SHORT).show();
                }
            } else if (result == SMSSDK.RESULT_ERROR) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交短信、语音验证码失败

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取短信验证码失败
                    Toast.makeText(SignInActivity.this, "获取验证码失败！", Toast.LENGTH_SHORT).show();

                }
                //失败回调
            } else {
                //其他失败回调
                ((Throwable) data).printStackTrace();
            }
            return false;
        }
    });

    EventHandler eh = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            // TODO 此处不可直接处理UI线程，处理后续操作需传到主线程中操作
            Message msg = new Message();
            msg.arg1 = result;
            msg.arg2 = event;
            msg.obj = data;
            mHandler.sendMessage(msg);

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }
}
