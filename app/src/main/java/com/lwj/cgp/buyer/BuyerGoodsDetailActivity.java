package com.lwj.cgp.buyer;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.lwj.cgp.R;
import com.lwj.cgp.base.BaseActivity;
import com.lwj.cgp.base.CommonData;
import com.lwj.cgp.base.GlideEngine;
import com.lwj.cgp.base.MainManager;
import com.lwj.cgp.common.ChangePswActivity;
import com.lwj.cgp.data.GoodsData;
import com.tencent.mmkv.MMKV;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BuyerGoodsDetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mIvBigImg;
    private TextView mTvPrice;
    private TextView mTvCount;
    private TextView mTvTitle;
    private TextView mTvSeller;
    private TextView mTvContent;
    private ViewGroup mGroupLayout;
    private ImageView mIvPhoto;
    private TextView mTvPeopleCount;
    private TextView mTvTime;
    private Button mBtnGoGroup;
    private Button mBtnDirectBuy;
    private Button mBtnGroupBuy;
    private ImageView mIvChat;
    private ImageView mIvCar;
    private GoodsData mData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(true).init();
        initData();
        mIvBigImg = findViewById(R.id.img);
        mTvPrice = findViewById(R.id.price);
        mTvCount = findViewById(R.id.count);
        mTvTitle = findViewById(R.id.title);
        mTvSeller = findViewById(R.id.seller);
        mTvContent = findViewById(R.id.content);
        mGroupLayout = findViewById(R.id.group_layout);
        mIvPhoto = findViewById(R.id.photo);
        mTvPeopleCount = findViewById(R.id.people_count);
        mTvTime = findViewById(R.id.time);
        mBtnGoGroup = findViewById(R.id.go_group);
        mBtnDirectBuy = findViewById(R.id.direct_buy);
        mBtnGroupBuy = findViewById(R.id.group_buy);
        mIvChat = findViewById(R.id.iv_chat);
        mIvCar = findViewById(R.id.iv_car);
        GlideEngine.getInstance().loadImage(this, mData.imgUrl, mIvBigImg);
        mTvPrice.setText("￥" + mData.price);
        mTvCount.setText("库存：" + mData.count);
        mTvTitle.setText(mData.title);
        mTvSeller.setText("商家：" + mData.seller);
        mTvContent.setText(mData.detail);
        mBtnDirectBuy.setText("直接购买\n￥" + mData.price);
        if (mData.isGroup.equals("1")) {
            mBtnGroupBuy.setEnabled(true);
            double p = Double.parseDouble(mData.price);
            p = p * 0.8;
            mBtnGroupBuy.setText("发起拼单\n￥" + String.format("%.1f", p));
        } else {
            mBtnGroupBuy.setEnabled(false);
            mBtnGroupBuy.setText("暂无团购");
        }
    }

    void initData() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        mData = (GoodsData) bundle.getSerializable("data");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_group:
                Map<String, String> map = new HashMap<>();
                map.put("uid", CommonData.getCommonData().getUserInfo().uid);
                map.put("goodsId", mData.uid);
                MainManager.getInstance().getNetService().crateGroupOrder(map)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(BuyerGoodsDetailActivity.this, "网络不佳，请重试！", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNext(Boolean result) {
                                if (result) {
                                    Toast.makeText(BuyerGoodsDetailActivity.this, "拼单成功！", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(BuyerGoodsDetailActivity.this, "等待其他用户拼单！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
        }
    }
}
