/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.pocketexpression.module.home;

import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.dell.fortune.core.callback.ToastUpdateListener;
import com.dell.fortune.core.common.BaseMutiPresenter;
import com.dell.fortune.core.common.IBaseMutiView;
import com.dell.fortune.core.util.UserUtil;
import com.dell.fortune.pocketexpression.module.home.category.HomeCategoryFragment;
import com.dell.fortune.pocketexpression.module.home.collection.HomeCollectionFragment;
import com.dell.fortune.pocketexpression.module.service.SuspendService;
import com.dell.fortune.tools.DoubleExitUtil;
import com.dell.fortune.tools.LogUtils;
import com.dell.fortune.tools.toast.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by 81256 on 2018/3/17.
 */

public class HomePresenter extends BaseMutiPresenter<HomePresenter.IView> {
    private HomeCategoryFragment mHomeCategoryFragment;
    private HomeCollectionFragment mHomeCollectionFragment;
    private DoubleExitUtil doubleExitUtil;
    private static final String accessibilityServiceName = "com.dell.fortune.pocketexpression.module.service.AccessibilityMonitorService";


    public HomePresenter(IView view) {
        super(view);
        doubleExitUtil = new DoubleExitUtil();
    }

    @Override
    public List<Fragment> initFragment() {
        List<Fragment> list = new ArrayList<>();
        mHomeCategoryFragment = new HomeCategoryFragment();
        mHomeCollectionFragment = new HomeCollectionFragment();
        list.add(mHomeCategoryFragment);
        list.add(mHomeCollectionFragment);
        return list;
    }

    //检测权限
    public boolean openSuspend() {
        if (!checkAccessibilityAuthority()) {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            mContext.startActivity(intent);
            return false;
        } else if (!checkSuspendAuthority()) {//版本号小于M，直接返回True
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            ToastUtil.showToast("需要取得悬浮窗权限");
            mContext.startActivity(intent);
            return false;
        } else {
            ToastUtil.showToast("配置成功");
            Intent intent = new Intent(mContext, SuspendService.class);
            mContext.startService(intent);
            return true;
        }
    }

    public void closeSuspend() {
        Intent intent = new Intent(mContext, SuspendService.class);
        mContext.stopService(intent);
    }


    //检测悬浮窗权限
    private boolean checkSuspendAuthority() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(mContext);
        } else {
            return true;
        }
    }

    //检测Accessibility权限
    private boolean checkAccessibilityAuthority() {
        //是否已经打开
        int accessibilityEnable = 0;
        String serviceName = mContext.getPackageName() + "/" + accessibilityServiceName;
        try {
            accessibilityEnable = Settings.Secure.getInt(mContext.getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED, 0);
        } catch (Exception e) {
            LogUtils.e("get accessibility enable failed, the err:" + e.getMessage());
        }
        if (accessibilityEnable == 1) {
            TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
            String settingValue = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();
                    if (accessibilityService.equalsIgnoreCase(serviceName)) {
                        LogUtils.v(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
            LogUtils.d(TAG, "Accessibility service disable");
        }
        return false;

    }

    public void doubleExit(int keyCode) {
        doubleExitUtil.doubleExit(mContext, keyCode);
    }


    public boolean checkAuthority() {
        if (!checkSuspendAuthority()) {
            ToastUtil.showToast("请打开悬浮窗权限");
            return false;
        }

        if (!checkAccessibilityAuthority()) {
            ToastUtil.showToast("请打开辅助服务");
            return false;
        }
        return true;
    }

    public void changeHead(String path) {
        mView.showLoading(true);
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e != null) {
                    mView.showLoading(false);
                    ToastUtil.showToast("发生错误");
                    return;
                }
                UserUtil.user.setHeadUrl(bmobFile.getUrl());
                UserUtil.user.update(new ToastUpdateListener() {
                    @Override
                    public void onSuccess() {
                        mView.showLoading(false);
                        mView.setHeadUrl(bmobFile.getUrl());
                    }
                });
            }

        });
    }

    public interface IView extends IBaseMutiView {

        void setHeadUrl(String url);
    }
}
