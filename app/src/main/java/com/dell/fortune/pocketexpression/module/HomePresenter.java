package com.dell.fortune.pocketexpression.module;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.Fragment;

import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.callback.ToastQueryListener;
import com.dell.fortune.pocketexpression.common.BaseActivity;
import com.dell.fortune.pocketexpression.common.BaseMutiPresenter;
import com.dell.fortune.pocketexpression.common.IBaseMutiView;
import com.dell.fortune.pocketexpression.module.home.category.HomeCategoryFragment;
import com.dell.fortune.pocketexpression.module.home.find.HomeFindFragment;
import com.dell.fortune.pocketexpression.module.home.make.HomeMakeFragment;
import com.dell.fortune.pocketexpression.module.service.SuspendService;
import com.dell.fortune.pocketexpression.module.user.UserCollectionActivity;
import com.dell.fortune.pocketexpression.util.common.DoubleExitUtil;
import com.dell.fortune.pocketexpression.util.common.PictureSelectorUtil;
import com.dell.fortune.pocketexpression.util.common.ToastUtil;
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
    private HomeFindFragment homeFindFragment;
    private HomeMakeFragment homeMakeFragment;
    private DoubleExitUtil doubleExitUtil;


    public HomePresenter(IView view) {
        super(view);
        doubleExitUtil = new DoubleExitUtil();
    }

    @Override
    public List<Fragment> initFragment() {
        List<Fragment> list = new ArrayList<>();
        homeCategoryFragment = new HomeCategoryFragment();
        homeFindFragment = new HomeFindFragment();
        homeMakeFragment = new HomeMakeFragment();
        list.add(homeCategoryFragment);
        list.add(homeFindFragment);
        list.add(homeMakeFragment);
        return list;
    }

    public void openSuspendWindows() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(mContext)) {//悬浮权限
                Intent intent = new Intent(mContext, SuspendService.class);
                mView.getCurrentContext().startService(intent);
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                ToastUtil.showToast("需要取得悬浮窗权限");
                mContext.startActivity(intent);
            }
        } else {
            Intent intent = new Intent(mContext, SuspendService.class);
            mContext.startService(intent);
        }
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
