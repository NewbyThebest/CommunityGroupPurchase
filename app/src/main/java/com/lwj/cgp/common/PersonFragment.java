package com.lwj.cgp.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lwj.cgp.base.BaseFragment;
import com.lwj.cgp.R;
import com.tencent.mmkv.MMKV;

public class PersonFragment extends BaseFragment {
    private ImageView photo;
    private TextView name;
    private TextView uid;
    private TextView phone;
    private ImageView edit;
    private Button psw;
    private Button logout;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_person, container, false);
        initView(root);
        return root;
    }

    void initView(View root){
        photo = root.findViewById(R.id.photo);
        name = root.findViewById(R.id.name);
        uid = root.findViewById(R.id.uid);
        phone = root.findViewById(R.id.phone);
        edit = root.findViewById(R.id.edit);
        psw = root.findViewById(R.id.change_psw);
        logout = root.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MMKV.defaultMMKV().encode("auto",false);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}
