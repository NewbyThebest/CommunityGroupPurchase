package com.lwj.cgp.base;

import com.lwj.cgp.data.UserData;

public class CommonData {
    private static CommonData mCommonData;

    private UserData mUserInfo = new UserData();
    public static int type;

    private CommonData() {

    }

    public static CommonData getCommonData() {

        if (mCommonData == null) {
            synchronized (CommonData.class) {
                if (mCommonData == null) {
                    mCommonData = new CommonData();
                }
            }
        }
        return mCommonData;
    }


    public UserData getUserInfo() {
        return mUserInfo;
    }

    public void setUserInfo(UserData mUserInfo) {
        this.mUserInfo = mUserInfo;
    }
}
