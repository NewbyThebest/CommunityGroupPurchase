package com.lwj.cgp;

import android.app.Application;

import com.mob.MobSDK;
import com.tencent.mmkv.MMKV;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.submitPolicyGrantResult(true, null);
        MainManager.getInstance().initRetrofit();
        MMKV.initialize(this);
    }
}
