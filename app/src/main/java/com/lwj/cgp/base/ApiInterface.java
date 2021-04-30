package com.lwj.cgp.base;

import com.lwj.cgp.data.GoodsData;
import com.lwj.cgp.data.UserData;

import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("CheckUserInfo")
    Observable<UserData> checkUserInfo(@FieldMap Map<String,  String> map);

    @FormUrlEncoded
    @POST("addUserInfo")
    Observable<Boolean> addUserInfo(@FieldMap Map<String,  String> map);

    @FormUrlEncoded
    @POST("UpdateUserInfo")
    Observable<Boolean> updateUserInfo(@FieldMap Map<String,  String> map);

    @FormUrlEncoded
    @POST("crateGroupOrder")
    Observable<Boolean> crateGroupOrder(@FieldMap Map<String,  String> map);

    @FormUrlEncoded
    @POST("uoloadImg")
    Observable<GoodsData> uploadImg(@FieldMap Map<String,  String> map);

    @FormUrlEncoded
    @POST("queryCategoryGoods")
    Observable<List<GoodsData>> queryCategoryGoods(@FieldMap Map<String,  String> map);

    @FormUrlEncoded
    @POST("queryBuyerGoods")
    Observable<List<GoodsData>> queryBuyerGoods(@FieldMap Map<String,  String> map);

    @FormUrlEncoded
    @POST("querySellerGoods")
    Observable<List<GoodsData>> querySellerGoods(@FieldMap Map<String,  String> map);

    @FormUrlEncoded
    @POST("queryBannerGoods")
    Observable<List<GoodsData>> queryBannerGoods(@FieldMap Map<String,  String> map);


    /**
     * 查询商家的轮播图商品信息
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("querySellerBannerGoods")
    Observable<List<GoodsData>> querySellerBannerGoods(@FieldMap Map<String,  String> map);


    @FormUrlEncoded
    @POST("addGoodsInfo")
    Observable<Boolean> addGoods(@FieldMap Map<String,  String> map);

    @FormUrlEncoded
    @POST("updateGoodsInfo")
    Observable<Boolean> updateGoods(@FieldMap Map<String,  String> map);

    @FormUrlEncoded
    @POST("deleteGoods")
    Observable<String> deleteGoods(@FieldMap Map<String,  String> map);

}
