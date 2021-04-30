package com.lwj.cgp.common;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.lwj.cgp.base.BaseActivity;
import com.lwj.cgp.base.CommonData;
import com.lwj.cgp.base.Constants;
import com.lwj.cgp.base.MainManager;
import com.lwj.cgp.buyer.BuyerMainActivity;
import com.lwj.cgp.data.UserData;
import com.lwj.cgp.seller.SellerMainActivity;
import com.tencent.mmkv.MMKV;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPhonePermission();
    }

    private void requestPhonePermission() {
        //判断Android版本是否大于23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkStatePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
            int checkWritePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (checkStatePermission != PackageManager.PERMISSION_GRANTED || checkWritePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                jumpToLogin();
            }
        } else {
            jumpToLogin();
        }
    }

    void jumpToLogin() {
        boolean isAutoLogin = MMKV.defaultMMKV().getBoolean("auto", false);
        String uid = MMKV.defaultMMKV().getString("uid", "");
        String psw = MMKV.defaultMMKV().getString("password", "");
        int type = MMKV.defaultMMKV().getInt("type", 0);
        CommonData.type = type;
        if (isAutoLogin) {
            checkUserInfo(uid, psw, type);
        }else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    void jumpToMain(){
        Intent intent = new Intent(this, BuyerMainActivity.class);
        if (CommonData.type == Constants.SELLER) {
            intent = new Intent(this, SellerMainActivity.class);
        } else if (CommonData.type == Constants.ADMIN) {

        } else {
            intent = new Intent(this, BuyerMainActivity.class);
        }
        startActivity(intent);
        finish();
    }

    void checkUserInfo(String etUser, String etPsw,int type) {

        Map<String, String> map = new HashMap<>();
        map.put("uid", etUser);
        map.put("password", etPsw);
        map.put("type", String.valueOf(type));
        MainManager.getInstance().getNetService().checkUserInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserData>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(SplashActivity.this, "网络不佳，请重试！", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(UserData result) {
                        if (result != null && !TextUtils.isEmpty(result.uid)) {
                            CommonData.getCommonData().setUserInfo(result);
                           jumpToMain();
                        }
                    }
                });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            jumpToLogin();
        } else {
            // Permission Denied
            Toast.makeText(this, "无权限无法正常使用应用！", Toast.LENGTH_SHORT).show();
            requestPhonePermission();
        }
    }

}
