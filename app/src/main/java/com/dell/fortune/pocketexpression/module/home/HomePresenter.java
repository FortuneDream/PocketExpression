/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.pocketexpression.module.home;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.callback.ToastQueryListener;
import com.dell.fortune.pocketexpression.common.BaseActivity;
import com.dell.fortune.pocketexpression.common.BaseMutiPresenter;
import com.dell.fortune.pocketexpression.common.IBaseMutiView;

import com.dell.fortune.pocketexpression.module.service.SuspendService;
import com.dell.fortune.pocketexpression.module.user.UserCollectionActivity;
import com.dell.fortune.pocketexpression.util.common.DoubleExitUtil;
import com.dell.fortune.pocketexpression.util.common.PictureSelectorUtil;
import com.dell.fortune.pocketexpression.util.common.ToastUtil;
import com.dell.fortune.tools.LogUtils;
import com.dell.fortune.tools.update.UpdateBuilder;
import com.dell.fortune.tools.update.UpdateConfiguration;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.update.AppVersion;

/**
 * Created by 81256 on 2018/3/17.
 */

public class HomePresenter extends BaseMutiPresenter<HomePresenter.IView> {
    private HomeCategoryFragment homeCategoryFragment;

    private DoubleExitUtil doubleExitUtil;
    private static final String accessibilityServiceName = "com.dell.fortune.pocketexpression.module.service.AccessibilityMonitorService";


    public HomePresenter(IView view) {
        super(view);
        doubleExitUtil = new DoubleExitUtil();
    }

    @Override
    public List<Fragment> initFragment() {
        List<Fragment> list = new ArrayList<>();
        homeCategoryFragment = new HomeCategoryFragment();
        list.add(homeCategoryFragment);
        return list;
    }

    public void openSuspendWindows() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(mContext)) {//悬浮权限
                if (checkAccessibilityAuthority()) {//Accessibility权限
                    ToastUtil.showToast("配置成功");
                    Intent intent = new Intent(mContext, SuspendService.class);
                    mContext.startService(intent);
                } else {
                    Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    mContext.startActivity(intent);
                }
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                ToastUtil.showToast("需要取得悬浮窗权限");
                mContext.startActivity(intent);
            }
        } else {
            if (checkAccessibilityAuthority()) {//Accessibility权限
                ToastUtil.showToast("配置成功");
                Intent intent = new Intent(mContext, SuspendService.class);
                mContext.startService(intent);
            } else {
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                mContext.startActivity(intent);
            }
        }
    }

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

    //用户收藏Activity
    public void enterUserCollectionActivity() {
        Intent intent = new Intent(mContext, UserCollectionActivity.class);
        mContext.startActivity(intent);
    }

    public void openPictureSelector() {
        PictureSelectorUtil.showPictureSelector((BaseActivity) mContext);
    }

    //注销登录
    public void exitUser() {

    }

    public void synLocal() {


    }

    public void checkVersion() {
        try {
            final PackageInfo pi = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            BmobQuery<AppVersion> query = new BmobQuery<>();
            query.addWhereGreaterThan("version_i", pi.versionCode);
            query.findObjects(new ToastQueryListener<AppVersion>() {
                @Override
                public void onSuccess(List<AppVersion> list) {
                    if (list.size() >= 1) {
                        AppVersion appVersion = list.get(list.size() - 1);
                        String versionContent = appVersion.getUpdate_log();
                        String versionCodeStr = appVersion.getVersion();
                        String url = appVersion.getPath().getUrl();
                        boolean isForce = appVersion.getIsforce();
                        UpdateBuilder updateBuilder = new UpdateBuilder(mContext);
                        UpdateConfiguration configuration = new UpdateConfiguration(versionCodeStr,
                                versionContent,
                                url,
                                isForce,
                                Environment.getExternalStorageDirectory().getPath(),
                                R.mipmap.ic_launcher);
                        updateBuilder.setConfiguration(configuration).update();
                    }
                }
            });
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public interface IView extends IBaseMutiView {

    }
}
