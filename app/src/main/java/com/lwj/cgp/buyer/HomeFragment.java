package com.lwj.cgp.buyer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lwj.cgp.base.BaseFragment;
import com.lwj.cgp.data.GoodsData;
import com.lwj.cgp.R;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {
    private MZBannerView mMZBanner;
    private List<GoodsData> mBanners = new ArrayList<>();
    private List<GoodsData> mListData = new ArrayList<>();
    private RecyclerView mHomeRv;
    private HomeRvAdapter mHomeRvAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initView(root);
        return root;
    }

    void initData(){
        mBanners.add(new GoodsData());
        mBanners.add(new GoodsData());
        mBanners.add(new GoodsData());

        mListData.add(new GoodsData());
        mListData.add(new GoodsData());
        mListData.add(new GoodsData());
    }

    @Override
    public void onResume() {
        super.onResume();
        mMZBanner.start();
    }

    void initView(View root){

        mMZBanner = root.findViewById(R.id.banner);
        mHomeRv = root.findViewById(R.id.rv_home);
        mHomeRvAdapter = new HomeRvAdapter(mListData);
        // 设置数据
        mMZBanner.setPages(mBanners, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mHomeRv.setLayoutManager(layoutManager);
        mHomeRv.setAdapter(mHomeRvAdapter);
        mHomeRvAdapter.setOnItemClickListener(new HomeRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GoodsData data) {
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    public class BannerViewHolder implements MZViewHolder<GoodsData> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, GoodsData data) {
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            Glide.with(mImageView.getContext()).load(data.imgUrl)
                    .placeholder(R.drawable.img_default)
                    .into(mImageView);
        }


    }
}
