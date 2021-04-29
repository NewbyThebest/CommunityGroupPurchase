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

public class ChatFragment extends BaseFragment{
    private List<ChatData> mListData = new ArrayList<>();
    private RecyclerView mChatRv;
    private ChatRvAdapter mChatRvAdapter;
    private ImageView mEmptyView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        initView(root);
        return root;
    }

    void initData() {
        mListData.add(new ChatData());
        mListData.add(new ChatData());
        mListData.add(new ChatData());
        mListData.add(new ChatData());
    }

    void initView(View root) {
        mChatRv = root.findViewById(R.id.rv_chat);
        mEmptyView = root.findViewById(R.id.empty_view);

        mChatRvAdapter = new ChatRvAdapter(mListData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mChatRv.setLayoutManager(layoutManager);
        mChatRv.setAdapter(mChatRvAdapter);
        mChatRvAdapter.setOnItemClickListener(new ChatRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ChatData data) {
                Intent intent = new Intent(getActivity(),GoodsDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
