package com.lwj.cgp.buyer;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lwj.cgp.R;
import com.lwj.cgp.base.BaseActivity;

public class GoodsDetailActivity extends BaseActivity {
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
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
    }
}
