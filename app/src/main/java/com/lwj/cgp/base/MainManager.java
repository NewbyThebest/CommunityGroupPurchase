package com.lwj.cgp.base;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainManager {
    private static MainManager manager;
    private ApiInterface mNetService;


    private MainManager() {

    }

    public static MainManager getInstance() {
        if(manager == null){
            synchronized (MainManager.class){
                if (manager == null){
                    manager = new MainManager();
                }
            }
        }
        return manager;
    }

    public void initRetrofit(){
        String BASE_URL = "http://" + Constants.BASE_IP + "/EasyGo/Data/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mNetService = retrofit.create(ApiInterface.class);
    }

    public ApiInterface getNetService(){
        return mNetService;
    }

}
