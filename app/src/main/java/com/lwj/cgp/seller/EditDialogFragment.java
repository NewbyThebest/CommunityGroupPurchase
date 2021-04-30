package com.lwj.cgp.seller;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lwj.cgp.R;
import com.lwj.cgp.base.CommonData;
import com.lwj.cgp.base.CommonUtil;
import com.lwj.cgp.base.GlideEngine;
import com.lwj.cgp.base.MainManager;
import com.lwj.cgp.data.GoodsData;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;
import static com.lwj.cgp.base.Constants.BASE_IP;

public class EditDialogFragment extends DialogFragment {
    private GoodsData goodData;
    private ImageView imageView;
    private String mImgPath;
    private String mTitle;
    private String mPrice;
    private String mDetail;
    private String mCount;
    private boolean mIsAdd;
    private String mImgTemp;
    private int time = 1;
    private boolean isGroup;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.dialogStyle);
        goodData = (GoodsData) getArguments().getSerializable("data");
        if (goodData == null) {
            mIsAdd = true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_goods_edit, null);
        EditText etTitle = view.findViewById(R.id.et_title);
        EditText etPrice = view.findViewById(R.id.et_price);
        EditText etDetail = view.findViewById(R.id.et_detail);
        EditText etCount = view.findViewById(R.id.et_count);
        TextView tvGroup = view.findViewById(R.id.tv_group);
        TextView tvTime = view.findViewById(R.id.tv_category);
        SwitchCompat swGroup = view.findViewById(R.id.sw_group);
        AppCompatSpinner spinner = view.findViewById(R.id.spinner);
        String[] times = {"限时1小时", "限时6小时", "限时12小时", "限时24小时"};
        ArrayAdapter adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, times);
        spinner.setAdapter(adapter);

        swGroup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                spinner.setEnabled(swGroup.isChecked());
                isGroup = swGroup.isChecked();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    time = 1;
                } else if (position == 1) {
                    time = 6;
                } else if (position == 2) {
                    time = 12;
                } else {
                    time = 24;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        imageView = view.findViewById(R.id.iv_img);
        Button delete = view.findViewById(R.id.delete);
        Button commit = view.findViewById(R.id.commit);
        Button close = view.findViewById(R.id.close);
        if (!mIsAdd) {
            etTitle.setText(goodData.title);
            etPrice.setText(goodData.price);
            etDetail.setText(goodData.detail);
            etCount.setText(goodData.count);
            if (goodData.isGroup.equals("1")) {
                swGroup.setChecked(true);
            } else {
                swGroup.setChecked(false);
            }
            swGroup.setEnabled(false);
            mImgPath = goodData.imgUrl;
            mImgTemp = mImgPath;
            swGroup.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            tvGroup.setVisibility(View.GONE);
            tvTime.setVisibility(View.GONE);
            Glide.with(this).load(mImgPath).into(imageView);
        }

        spinner.setEnabled(swGroup.isChecked() && swGroup.isEnabled());
        isGroup = swGroup.isChecked();


        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(R.drawable.dialog_bg);

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitle = etTitle.getText().toString();
                mPrice = etPrice.getText().toString();
                mDetail = etDetail.getText().toString();
                mCount = etCount.getText().toString();
                if (TextUtils.isEmpty(mTitle)) {
                    Toast.makeText(getActivity(), "标题不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mPrice)) {
                    Toast.makeText(getActivity(), "价格不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(mCount)) {
                    Toast.makeText(getActivity(), "库存不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mImgPath)) {
                    Toast.makeText(getActivity(), "图片为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mIsAdd) {
                    uploadImg(mImgPath);
                } else {
                    if (mImgPath.equals(mImgTemp)) {
                        updateGoodsInfo(null);
                    } else {
                        uploadImg(mImgPath);
                    }
                }


            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new UpdateGoodsEvent());
                getDialog().dismiss();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector.create(EditDialogFragment.this)
                        .openGallery(PictureMimeType.ofImage())
                        .imageEngine(new GlideEngine())
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST) {
            List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
            if (list != null && !list.isEmpty()) {
                mImgPath = list.get(0).getPath();
                GlideEngine.getInstance().loadImage(getContext(), mImgPath, imageView);
            }
        }
    }

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
                            if (mIsAdd) {
                                addGoodsInfo(result.imgUrl);
                            } else {
                                updateGoodsInfo(result.imgUrl);
                            }
                        }
                    }
                });
    }

    void updateGoodsInfo(String name) {
        Map<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(name)) {
            String url = "http://" + BASE_IP + "/EasyGo/img/" + name;
            map.put("img", url);
        } else {
            map.put("img", goodData.imgUrl);
        }
        map.put("uid", goodData.uid);
        map.put("title", mTitle);
        map.put("detail", mDetail);
        map.put("price", mPrice);
        map.put("count", mCount);

        MainManager.getInstance().getNetService().updateGoods(map)
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
                            Toast.makeText(getContext(), "更新商品成功！", Toast.LENGTH_LONG).show();
                            EventBus.getDefault().post(new UpdateGoodsEvent());
                            getDialog().dismiss();
                        } else {
                            Toast.makeText(getContext(), "更新商品失败，请重试！", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    void addGoodsInfo(String name) {
        Map<String, String> map = new HashMap<>();
        String url = "http://" + BASE_IP + "/EasyGo/img/" + name;
        map.put("img", url);
        map.put("title", mTitle);
        map.put("detail", mDetail);
        map.put("price", mPrice);
        map.put("count", mCount);
        if (isGroup) {
            map.put("isGroup", "1");
            long t = System.currentTimeMillis() + time * 3600 * 1000;
            map.put("time", String.valueOf(t));
        } else {
            map.put("isGroup", "0");
            map.put("time", "0");
        }
        map.put("seller", CommonData.getCommonData().getUserInfo().name);
        map.put("sellerId", CommonData.getCommonData().getUserInfo().uid);
        MainManager.getInstance().getNetService().addGoods(map)
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
                            Toast.makeText(getContext(), "添加商品成功！", Toast.LENGTH_LONG).show();
                            EventBus.getDefault().post(new UpdateGoodsEvent());
                            getDialog().dismiss();
                        } else {
                            Toast.makeText(getContext(), "添加商品失败，请重试！", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
