package com.lwj.cgp.buyer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lwj.cgp.base.BaseFragment;
import com.lwj.cgp.data.GoodsData;
import com.lwj.cgp.R;

import java.util.ArrayList;
import java.util.List;

public class CarFragment extends BaseFragment {
    private List<GoodsData> mListData = new ArrayList<>();
    private RecyclerView mCarRv;
    private CarRvAdapter mCarRvAdapter;
    private ImageView mEmptyView;
    private Button mBtnClear;
    private Button mBtnBuy;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_car, container, false);
        initView(root);
        return root;
    }

    void initData() {
        mListData.add(new GoodsData());
        mListData.add(new GoodsData());
        mListData.add(new GoodsData());
        mListData.add(new GoodsData());
    }

    void initView(View root) {
        mCarRv = root.findViewById(R.id.rv_car);
        mEmptyView = root.findViewById(R.id.empty_view);
        mBtnClear = root.findViewById(R.id.car_clear);
        mBtnBuy = root.findViewById(R.id.car_buy);

        mCarRvAdapter = new CarRvAdapter(mListData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mCarRv.setLayoutManager(layoutManager);
        mCarRv.setAdapter(mCarRvAdapter);
        mCarRvAdapter.setOnItemClickListener(new CarRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GoodsData data) {
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemRemoveClick(GoodsData data) {

            }
        });
    }
}
