package com.lwj.cgp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


public class ForgetPswActivity extends BaseActivity implements View.OnClickListener {
    private Button commit;
    private Button back;
    private Button send;
    private EditText phone;
    private EditText code;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_psw);
        commit = findViewById(R.id.commit);
        back = findViewById(R.id.back);
        send = findViewById(R.id.send_code);
        phone = findViewById(R.id.phone);
        code = findViewById(R.id.code);
        commit.setOnClickListener(this);

        back.setOnClickListener(this);
        send.setOnClickListener(this);
        SMSSDK.registerEventHandler(eh);

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
            case R.id.commit:
                checkCode();
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
                    Intent intent = new Intent(ForgetPswActivity.this,ResetPswActivity.class);
                    startActivity(intent);
                    finish();
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取短信验证码成功
                    Toast.makeText(ForgetPswActivity.this, "获取验证码成功！", Toast.LENGTH_SHORT).show();
                }
            } else if (result == SMSSDK.RESULT_ERROR) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交短信、语音验证码失败
                    Toast.makeText(ForgetPswActivity.this, "验证码有误！", Toast.LENGTH_SHORT).show();

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取短信验证码失败
                    Toast.makeText(ForgetPswActivity.this, "获取验证码失败！", Toast.LENGTH_SHORT).show();

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
