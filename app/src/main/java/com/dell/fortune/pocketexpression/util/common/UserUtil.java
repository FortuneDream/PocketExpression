package com.dell.fortune.pocketexpression.util.common;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.dell.fortune.pocketexpression.config.FlagConstant;
import com.dell.fortune.pocketexpression.model.bean.MyUser;
import com.dell.fortune.pocketexpression.module.auth.login.LoginActivity;


/**
 * Created by 鹏君 on 2017/1/26.
 */

public class UserUtil {
    public static MyUser user;
    public static final String RESULT_USER = "result_user";//返回的User

    public static boolean checkLocalUser(boolean enterLoginActivity,Fragment fragment) {
        MyUser user = MyUser.getCurrentUser(MyUser.class);
        if (user == null) {
            if (enterLoginActivity) {
                Intent intent = new Intent(fragment.getContext(), LoginActivity.class);
                fragment.startActivityForResult(intent, FlagConstant.REQUEST_LOGIN);
            }
            return false;
        } else {
            UserUtil.user = user;
            LogUtils.e(SharedPrefsUtil.getString("user", ""));
            return true;
        }
    }

    public static boolean checkLocalUser(boolean enterLoginActivity,FragmentActivity activity) {
        MyUser user = MyUser.getCurrentUser(MyUser.class);
        if (user == null) {
            if (enterLoginActivity){
                Intent intent = new Intent(activity, LoginActivity.class);
                activity.startActivityForResult(intent, FlagConstant.REQUEST_LOGIN);
            }
            return false;
        } else {
            UserUtil.user = user;
            LogUtils.e(SharedPrefsUtil.getString("user", ""));
            return true;
        }
    }


    public static void onActivityResult(int resultCode, Intent data) {
        if (resultCode == FlagConstant.SUCCESS) {
            UserUtil.user = (MyUser) data.getSerializableExtra(RESULT_USER);//成功登录并复制
        } else if (resultCode == FlagConstant.FAIL) {
            UserUtil.user = null;//登录失败
        }
    }

}
