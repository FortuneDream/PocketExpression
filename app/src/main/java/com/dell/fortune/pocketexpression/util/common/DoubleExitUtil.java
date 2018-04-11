package com.dell.fortune.pocketexpression.util.common;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import android.view.KeyEvent;

import com.dell.fortune.pocketexpression.common.BaseActivity;

import java.security.Key;

/**
 * Created by 81256 on 2018/4/4.
 */

public class DoubleExitUtil {
    private static boolean isExit = false;

    public Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    public void doubleExit(Context context, int keyCode) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;
                ToastUtil.showToast("再按一次退出程序");

            } else {
                if (context instanceof BaseActivity) {
                    ((BaseActivity) context).finish();
                    System.exit(0);
                }
            }
        }
    }
}
