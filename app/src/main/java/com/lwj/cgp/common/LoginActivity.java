package com.lwj.cgp.common;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.lwj.cgp.base.BaseActivity;
import com.lwj.cgp.base.CommonData;
import com.lwj.cgp.base.Constants;
import com.lwj.cgp.base.MainManager;
import com.lwj.cgp.R;
import com.lwj.cgp.buyer.BuyerMainActivity;
import com.lwj.cgp.data.UserData;
import com.lwj.cgp.seller.SellerMainActivity;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class LoginActivity extends BaseActivity implements View.OnClickListener {
    public final static int REQUEST_CODE = 1024;
    private Button login;
    private EditText user;
    private EditText password;
    private TextView signin;
    private TextView forget;
    private TextView userType;
    private CheckBox memoryPsw;
    private CheckBox autoLogin;
    private ImageView eye;
    private boolean isOpen;
    private int count;
    private Spinner spinner;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login);
        user = findViewById(R.id.username);
        password = findViewById(R.id.password);
        eye = findViewById(R.id.eye);
        login = findViewById(R.id.login);
        signin = findViewById(R.id.sign);
        forget = findViewById(R.id.forget);
        userType = findViewById(R.id.userType);
        memoryPsw = findViewById(R.id.save_password);
        autoLogin = findViewById(R.id.auto_login);
        spinner = findViewById(R.id.type);
        List<String> types = new ArrayList<>();
        types.add("????????????");
        types.add("????????????");
        types.add("????????????");
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, R.layout.spinner_list_item, types);
        spinner.setAdapter(adapter);

        login.setOnClickListener(this);
        eye.setOnClickListener(this);
        signin.setOnClickListener(this);
        forget.setOnClickListener(this);
        userType.setOnClickListener(this);
        String uid = MMKV.defaultMMKV().getString("uid", "");
        String psw = MMKV.defaultMMKV().getString("password", "");
        if (!TextUtils.isEmpty(uid) && !TextUtils.isEmpty(psw)) {
            user.setText(uid);
            password.setText(psw);
            memoryPsw.setChecked(true);
        }
        spinner.setSelection(CommonData.type);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CommonData.type = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void checkUserInfo(String etUser, String etPsw) {
        if (TextUtils.isEmpty(etUser)) {
            Toast.makeText(this, "??????????????????????????????????????????", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(etPsw)) {
            Toast.makeText(this, "???????????????????????????????????????", Toast.LENGTH_LONG).show();
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("uid", etUser);
        map.put("password", etPsw);
        map.put("type", String.valueOf(CommonData.type));
        MainManager.getInstance().getNetService().checkUserInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserData>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(LoginActivity.this, "???????????????????????????", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(UserData result) {
                        if (result != null && !TextUtils.isEmpty(result.uid)) {
                            if (memoryPsw.isChecked()) {
                                MMKV.defaultMMKV().encode("uid", result.uid);
                                MMKV.defaultMMKV().encode("password", result.password);
                                MMKV.defaultMMKV().encode("type", CommonData.type);
                            } else {
                                MMKV.defaultMMKV().encode("uid", "");
                                MMKV.defaultMMKV().encode("password", "");
                                MMKV.defaultMMKV().encode("type", 0);
                            }

                            if (autoLogin.isChecked()) {
                                MMKV.defaultMMKV().encode("uid", result.uid);
                                MMKV.defaultMMKV().encode("password", result.password);
                                MMKV.defaultMMKV().encode("auto", true);
                                MMKV.defaultMMKV().encode("type", CommonData.type);
                            } else {
                                MMKV.defaultMMKV().encode("auto", false);
                            }

                            CommonData.getCommonData().setUserInfo(result);
                            jumpToMain();
                        } else {
                            Toast.makeText(LoginActivity.this, "??????????????????????????????????????????", Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }

    void jumpToMain() {
        Intent intent = new Intent(LoginActivity.this, BuyerMainActivity.class);
        if (CommonData.type == Constants.SELLER) {
            intent = new Intent(LoginActivity.this, SellerMainActivity.class);
        } else if (CommonData.type == Constants.ADMIN) {

        } else {
            intent = new Intent(LoginActivity.this, BuyerMainActivity.class);
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                String etUser = user.getText().toString();
                String etPsw = password.getText().toString();
                checkUserInfo(etUser, etPsw);
                break;
            case R.id.eye:
                isOpen = !isOpen;
                if (isOpen) {
                    eye.setBackgroundResource(R.drawable.visibility_red_900_24dp);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    eye.setBackgroundResource(R.drawable.visibility_off_red_900_24dp);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                password.setSelection(password.getText().toString().length());
                break;
            case R.id.sign:
                Intent intent = new Intent(this, SignInActivity.class);
                startActivity(intent);
                break;

            case R.id.forget:
                Intent intent2 = new Intent(this, ForgetPswActivity.class);
                startActivity(intent2);
                break;
            case R.id.userType:
                count++;
                if (count == 3) {
                    AlertDialog.Builder b = new AlertDialog.Builder(this);
                    View view = LayoutInflater.from(this).inflate(R.layout.dialog_edit, null, false);
                    EditText ip = view.findViewById(R.id.ipEdit);
                    Button ok = view.findViewById(R.id.btnOk);
                    Button no = view.findViewById(R.id.btnNo);
                    b.setView(view);
                    Dialog d = b.create();
                    Window window = d.getWindow();
                    window.setBackgroundDrawableResource(R.drawable.dialog_bg);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Constants.BASE_IP = ip.getText().toString();
                            MainManager.getInstance().initRetrofit();
                            d.dismiss();
                        }
                    });

                    d.show();
                    count = 0;
                }

                break;
        }
    }
}
