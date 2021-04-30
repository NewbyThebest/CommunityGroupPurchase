package com.lwj.cgp.buyer;

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


import com.lwj.cgp.base.BaseFragment;
import com.lwj.cgp.data.GoodsData;
import com.lwj.cgp.R;

import java.util.ArrayList;
import java.util.List;

public class BuyerGroupFragment extends BaseFragment {
    private List<GoodsData> mListData = new ArrayList<>();
    private RecyclerView mGroupRv;
    private BuyerGroupRvAdapter mGroupRvAdapter;
    private ImageView mEmptyView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_group, container, false);
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
        mGroupRv = root.findViewById(R.id.rv_group);
        mEmptyView = root.findViewById(R.id.empty_view);

        mGroupRvAdapter = new BuyerGroupRvAdapter(mListData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mGroupRv.setLayoutManager(layoutManager);
        mGroupRv.setAdapter(mGroupRvAdapter);
        mGroupRvAdapter.setOnItemClickListener(new BuyerGroupRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GoodsData data) {
                Intent intent = new Intent(getActivity(), BuyerGoodsDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
