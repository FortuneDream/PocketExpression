package com.dell.fortune.tools;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;

import com.dell.fortune.tools.toast.ToastUtil;

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
                mHandler.sendEmptyMessageDelayed(0, 2000);
            } else {
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                    System.exit(0);
                }
            }
        }
    }
}
