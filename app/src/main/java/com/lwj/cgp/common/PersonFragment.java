package com.lwj.cgp.common;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lwj.cgp.base.BaseFragment;
import com.lwj.cgp.R;
import com.lwj.cgp.base.CommonData;
import com.lwj.cgp.base.CommonUtil;
import com.lwj.cgp.base.GlideEngine;
import com.lwj.cgp.base.MainManager;
import com.lwj.cgp.data.GoodsData;
import com.lwj.cgp.data.UserData;
import com.tencent.mmkv.MMKV;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;
import static com.lwj.cgp.base.Constants.BASE_IP;

public class PersonFragment extends BaseFragment {
    private ImageView photo;
    private TextView name;
    private TextView uid;
    private TextView phone;
    private ImageView edit;
    private Button psw;
    private Button logout;
    private UserData mData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mData = CommonData.getCommonData().getUserInfo();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_person, container, false);
        initView(root);
        return root;
    }

    void initView(View root) {
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
                MMKV.defaultMMKV().encode("auto", false);
                CommonData.getCommonData().setUserInfo(new UserData());
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.showUserEditDialog(PersonFragment.this, mData);
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector.create(getActivity())
                        .openGallery(PictureMimeType.ofImage())
                        .imageEngine(new GlideEngine())
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }
        });
        psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ChangePswActivity.class);
                startActivity(intent);
            }
        });
        if (!TextUtils.isEmpty(mData.name)) {
            name.setText(mData.name);
        }
        if (!TextUtils.isEmpty(mData.phone)) {
            phone.setText("手机号码：" + mData.phone);
        }
        if (!TextUtils.isEmpty( mData.uid)) {
            uid.setText("当前账号：" +mData.uid);
        }

        if (!TextUtils.isEmpty(mData.url)) {
            Glide.with(getContext()).load(mData.url).into(photo);
        }
    }

    /**
     * 更新界面
     */
    @Override
    public void updateView() {
        super.updateView();
        if (!TextUtils.isEmpty(mData.name)) {
            name.setText(mData.name);
        }
        if (!TextUtils.isEmpty(mData.phone)) {
            phone.setText("手机号码：" + mData.phone);
        }
        if (!TextUtils.isEmpty("当前账号：" + mData.uid)) {
            uid.setText(mData.uid);
        }

        if (!TextUtils.isEmpty(mData.url)) {
            Glide.with(getContext()).load(mData.url).into(photo);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST) {
            List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
            if (list != null && !list.isEmpty()) {
                LocalMedia media = list.get(0);
                mData.url = media.getPath();
                uploadImg(media.getPath());
            }
        }
    }

    /**
     * 上传头像
     *
     * @param path
     */
    void uploadImg(String path) {
        String img = CommonUtil.imageToBase64(path);
        Map<String, String> map = new HashMap<>();
        map.put("img", img);
        MainManager.getInstance().getNetService().uploadImg(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GoodsData>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "网络不佳，请重试！", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(GoodsData result) {
                        if (!TextUtils.isEmpty(result.imgUrl)) {
                            updateUserInfo(result.imgUrl);
                        }
                    }
                });
    }

    /**
     * 更新用户信息
     *
     * @param name
     */
    void updateUserInfo(String name) {
        Map<String, String> map = new HashMap<>();
        String url = "http://" + BASE_IP + "/EasyGo/img/" + name;
        map.put("url", url);
        map.put("psw", CommonData.getCommonData().getUserInfo().password);
        map.put("name", CommonData.getCommonData().getUserInfo().name);
        map.put("uid", CommonData.getCommonData().getUserInfo().uid);
        MainManager.getInstance().getNetService().updateUserInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "网络不佳，请重试！", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(Boolean result) {
                        if (result) {
                            Toast.makeText(getContext(), "更新头像成功！", Toast.LENGTH_LONG).show();

                            GlideEngine.getInstance().loadImage(getContext(), url, photo);
                            mData.url = url;
                            CommonData.getCommonData().setUserInfo(mData);
                        } else {
                            Toast.makeText(getContext(), "更新头像失败，请重试！", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
