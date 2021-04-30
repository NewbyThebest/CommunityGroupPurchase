package com.lwj.cgp.buyer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lwj.cgp.base.BaseFragment;
import com.lwj.cgp.base.CommonData;
import com.lwj.cgp.base.MainManager;
import com.lwj.cgp.data.GoodsData;
import com.lwj.cgp.R;
import com.lwj.cgp.seller.SellerHomeFragment;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BuyerHomeFragment extends BaseFragment {
    private MZBannerView mMZBanner;
    private List<GoodsData> mBanners = new ArrayList<>();
    private List<GoodsData> mListData = new ArrayList<>();
    private RecyclerView mHomeRv;
    private BuyerHomeRvAdapter mHomeRvAdapter;
    private ImageView mEmptyView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_buyer, container, false);
        initView(root);
        return root;
    }

    void initData() {
        mBanners.clear();
        mBanners.add(new GoodsData());
        mBanners.add(new GoodsData());
        mBanners.add(new GoodsData());

        Map<String, String> map = new HashMap<>();
        map.put("uid", CommonData.getCommonData().getUserInfo().uid);
        MainManager.getInstance().getNetService().queryBannerGoods(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GoodsData>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getActivity() != null) {
                            Toast.makeText(getActivity(), "网络不佳，请重试！", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onNext(List<GoodsData> datas) {
                        mBanners.clear();
                        if (datas != null && !datas.isEmpty()) {
                            for (int i = 0; i < 3; i++) {
                                int size = datas.size();
                                Random random = new Random();
                                int pos = random.nextInt(size);
                                mBanners.add(datas.get(pos));
                            }
                            // 设置数据
                            mMZBanner.setPages(mBanners, new MZHolderCreator<BuyerHomeFragment.BannerViewHolder>() {
                                @Override
                                public BuyerHomeFragment.BannerViewHolder createViewHolder() {
                                    return new BuyerHomeFragment.BannerViewHolder();
                                }
                            });
                        } else {
                            mBanners.add(new GoodsData());
                            mBanners.add(new GoodsData());
                            mBanners.add(new GoodsData());
                        }
                    }
                });


        Map map1 = new HashMap<String, String>();
        map1.put("buyerId", CommonData.getCommonData().getUserInfo().uid);
        MainManager.getInstance().getNetService().queryBuyerGoods(map1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GoodsData>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getActivity() != null) {
                            Toast.makeText(getActivity(), "网络不佳，请重试！", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onNext(List<GoodsData> datas) {
                        mListData.clear();
                        if (datas != null && !datas.isEmpty()) {
                            mListData.addAll(datas);
                            if (mEmptyView != null) {
                                mEmptyView.setVisibility(View.GONE);
                            }
                        } else {
                            if (mEmptyView != null) {
                                mEmptyView.setVisibility(View.VISIBLE);
                            }

                        }
                        if (mHomeRvAdapter != null) {
                            mHomeRvAdapter.setList(mListData);
                            mHomeRvAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        mMZBanner.start();
    }

    void initView(View root) {

        mMZBanner = root.findViewById(R.id.banner);
        mHomeRv = root.findViewById(R.id.rv_home);
        mEmptyView = root.findViewById(R.id.empty_view);
        if (mListData.isEmpty()) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
        }
        mHomeRvAdapter = new BuyerHomeRvAdapter(mListData);
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
        mHomeRvAdapter.setOnItemClickListener(new BuyerHomeRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GoodsData data) {
                Intent intent = new Intent(getActivity(), BuyerGoodsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", data);
                intent.putExtra("bundle", bundle);
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
                    Intent intent = new Intent(getActivity(), BuyerGoodsDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", data);
                    intent.putExtra("bundle", bundle);
                    startActivity(intent);
                }
            });
            Glide.with(mImageView.getContext()).load(data.imgUrl)
                    .placeholder(R.drawable.img_default)
                    .into(mImageView);
        }


    }
}
