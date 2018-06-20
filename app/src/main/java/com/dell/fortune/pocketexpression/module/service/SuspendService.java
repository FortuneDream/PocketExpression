package com.dell.fortune.pocketexpression.module.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.view.View;

import com.dell.fortune.core.config.IntentConstant;
import com.dell.fortune.pocketexpression.module.suspend.SuspendContentActivity;
import com.dell.fortune.pocketexpression.module.suspend.SuspendIconWindowView;
import com.dell.fortune.tools.LogUtils;


public class SuspendService extends Service {
    private SuspendIconWindowView mIconWindowView;
    private SuspendServiceReceiver mReceiver;//用于接受Accessibility的消息

    public SuspendService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e("Suspend", "onCreate");
        initIconWindow();
        initReceiver();
    }

    private void initIconWindow() {
        mIconWindowView = new SuspendIconWindowView(this);
        //拖动
        mIconWindowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //表情包列表
                mIconWindowView.dismiss();
                startContentActivity();
            }
        });
    }

    private void initReceiver() {
        mReceiver = new SuspendServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(IntentConstant.SUSPEND_ACTION);
        registerReceiver(mReceiver, filter);
    }


    //打开
    private void startContentActivity() {
        Intent intent = new Intent(this, SuspendContentActivity.class);
        startActivity(intent);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e("Suspend", "onDestroy");
        mIconWindowView.dismiss();
        unregisterReceiver(mReceiver);
    }

    //应用内广播?
    public class SuspendServiceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isShowSuspend = intent.getBooleanExtra(IntentConstant.SUSPEND_PARAM_CONTROL_SUSPEND, false);
            if (!isShowSuspend) {
                LogUtils.e("Suspend", "Broadcast->关闭悬浮窗");
                mIconWindowView.dismiss();
            } else {
                LogUtils.e("Suspend", "Broadcast->开启悬浮窗");
                mIconWindowView.show();
            }
        }
    }
}
