package com.dell.fortune.core.util;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dell.fortune.core.common.BaseActivity;

import com.dell.fortune.core.config.FlagConstant;
import com.dell.fortune.core.model.bean.MyUser;


/**
 * Created by 鹏君 on 2017/1/26.
 */

public class UserUtil {
    public static MyUser user;
    public static final String RESULT_USER = "result_user";//返回的User

    public static boolean checkLocalUser(boolean enterLoginActivity, Fragment fragment) {
        MyUser user = MyUser.getCurrentUser(MyUser.class);
        if (user == null) {
            if (enterLoginActivity) {
                //路由跳转到LoginActivity
                ARouter.getInstance().build("/auth/login").navigation(fragment.getActivity(), FlagConstant.REQUEST_LOGIN);
            }
            return false;
        } else {
            UserUtil.user = user;
            return true;
        }
    }

    public static boolean checkLocalUser(boolean enterLoginActivity, FragmentActivity activity) {
        MyUser user = MyUser.getCurrentUser(MyUser.class);
        if (user == null) {
            if (enterLoginActivity) {
                ARouter.getInstance().build("/auth/login").navigation(activity, FlagConstant.REQUEST_LOGIN);
            }
            return false;
        } else {
            UserUtil.user = user;
            return true;
        }
    }

    //退出登录
    public static void logOut() {
        user = null;
        MyUser.logOut();
    }


    public static void onActivityResult(int resultCode, Intent data) {
        if (resultCode == BaseActivity.RESULT_OK) {
            UserUtil.user = (MyUser) data.getSerializableExtra(RESULT_USER);//成功登录并复制
        } else if (resultCode == BaseActivity.RESULT_CANCELED) {
            UserUtil.user = null;//登录失败
        }
    }

}
