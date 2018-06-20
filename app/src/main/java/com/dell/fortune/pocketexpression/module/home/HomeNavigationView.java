/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.pocketexpression.module.home;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dell.fortune.core.callback.ToastQueryListener;
import com.dell.fortune.core.common.BaseActivity;
import com.dell.fortune.core.util.UserUtil;
import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.core.config.FlagConstant;
import com.dell.fortune.pocketexpression.util.common.FrescoProxy;
import com.dell.fortune.pocketexpression.util.common.PictureSelectorUtil;
import com.dell.fortune.pocketexpression.util.common.RxApi;
import com.dell.fortune.tools.IntentUtil;
import com.dell.fortune.tools.dialog.DialogSureCancel;
import com.dell.fortune.tools.toast.ToastUtil;
import com.dell.fortune.tools.update.UpdateBuilder;
import com.dell.fortune.tools.update.UpdateConfiguration;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.update.AppVersion;
import io.reactivex.functions.Consumer;

public class HomeNavigationView extends NavigationView {
    private SimpleDraweeView userHeadSdv;
    private TextView userNickNameTv;

    public HomeNavigationView(Context context) {
        this(context, null);
    }

    public HomeNavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(context, R.layout.app_header_home_user, null);
        addHeaderView(view);
        inflateMenu(R.menu.app_menu_home_user);
        userHeadSdv = getHeaderView(0).findViewById(R.id.user_head_sdv);
        userNickNameTv = getHeaderView(0).findViewById(R.id.user_nick_name_tv);
        if (UserUtil.user != null) {
            FrescoProxy.showNetSimpleView(userHeadSdv, UserUtil.user.getHeadUrl());
            userNickNameTv.setText(UserUtil.user.getNickName());
        }
        setItemIconTintList(null);
        //打开相册
        userHeadSdv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelectorUtil.showPictureSelector((BaseActivity) getContext());
            }
        });

        //菜单选择
        setNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int i = item.getItemId();
                if (i == R.id.clean_item) {
                    cleanCache();//清理缓存

                } else if (i == R.id.invite_item) {
                    IntentUtil.shareText(getContext(), "斗图斗图！下载地址：");

                } else if (i == R.id.upgrade_item) {//更新
                    checkVersion();

                } else if (i == R.id.exit_item) {
                    exitUser();

                }
                return true;
            }
        });
    }

    //清理缓存
    private void cleanCache() {
        RxApi.create(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                File dir = new File(FlagConstant.TEMP_SHARE_DIR);
                File[] files = dir.listFiles();
                for (File file : files) {
                    file.delete();
                }
                return null;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                ToastUtil.showToast("清理缓存");
            }
        });
    }


    public void checkVersion() {
        try {
            final PackageInfo pi = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), PackageManager.GET_ACTIVITIES);
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
                        UpdateBuilder updateBuilder = new UpdateBuilder(getContext());
                        UpdateConfiguration configuration = new UpdateConfiguration(versionCodeStr,
                                versionContent,
                                url,
                                isForce,
                                Environment.getExternalStorageDirectory().getPath(),
                                R.mipmap.core_ic_launcher);
                        updateBuilder.setConfiguration(configuration).update();
                    }
                }
            });
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    //注销登录
    public void exitUser() {
        final DialogSureCancel dialogSureCancel = new DialogSureCancel(getContext());
        dialogSureCancel.getTvTitle().setText("退出登录");
        dialogSureCancel.getTvContent().setText("是否退出登录？（将重启应用）");
        dialogSureCancel.getTvSure().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserUtil.logOut();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        dialogSureCancel.getTvCancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSureCancel.dismiss();
            }
        });
        dialogSureCancel.show();
    }

    //设置头像连接
    public void setHead(String url) {
        FrescoProxy.showNetSimpleView(userHeadSdv, url);
    }

}
