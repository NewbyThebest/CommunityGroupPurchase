package com.lwj.cgp;

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


import java.util.ArrayList;
import java.util.List;

public class GroupFragment extends BaseFragment {
    private List<GoodsData> mListData = new ArrayList<>();
    private RecyclerView mGroupRv;
    private GroupRvAdapter mGroupRvAdapter;
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

        mGroupRvAdapter = new GroupRvAdapter(mListData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mGroupRv.setLayoutManager(layoutManager);
        mGroupRv.setAdapter(mGroupRvAdapter);
        mGroupRvAdapter.setOnItemClickListener(new GroupRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GoodsData data) {
                Intent intent = new Intent(getActivity(),GoodsDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
