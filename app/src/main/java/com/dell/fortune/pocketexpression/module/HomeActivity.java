package com.dell.fortune.pocketexpression.module;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.common.BaseActivity;
import com.dell.fortune.pocketexpression.config.FlagConstant;
import com.dell.fortune.pocketexpression.config.IntentConstant;
import com.dell.fortune.pocketexpression.util.common.DoubleExitUtil;
import com.dell.fortune.pocketexpression.util.common.IntentUtil;
import com.dell.fortune.pocketexpression.util.common.LogUtils;
import com.dell.fortune.pocketexpression.util.common.ToastUtil;
import com.dell.fortune.pocketexpression.util.common.UserUtil;
import com.dell.fortune.pocketexpression.util.common.update.UpdateUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity<HomePresenter.IView, HomePresenter>
        implements HomePresenter.IView, NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.home_content)
    FrameLayout homeContent;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.content_user_head_sdv)
    SimpleDraweeView contentUserHeadSdv;
    @BindView(R.id.open_suspend_window_btn)
    Button openSuspendWindowBtn;
    @BindView(R.id.home_user_nv)
    NavigationView homeUserNv;

    @Override
    public int setContentResource() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {
        homeUserNv.setNavigationItemSelectedListener(this);
        UserUtil.checkLocalUser(false, this);
        presenter.clickBottomTab(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //判断用户是否登录
        if (UserUtil.user != null) {
            LogUtils.e(UserUtil.user.toString());
            //Toolbar
            SimpleDraweeView contentHeadSdv = findViewById(R.id.content_user_head_sdv);
            contentHeadSdv.setImageURI(Uri.parse(UserUtil.user.getHeadUrl()));
            //侧拉
            LinearLayout headerLl = (LinearLayout) homeUserNv.getHeaderView(0);
            SimpleDraweeView headerHeadIv = headerLl.findViewById(R.id.user_head_sdv);
            headerHeadIv.setImageURI(Uri.parse(UserUtil.user.getHeadUrl()));
            headerHeadIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //点击头像，修改头像

                }
            });
            TextView headerNickNameTv = headerLl.findViewById(R.id.user_nick_name_tv);
            headerNickNameTv.setText(UserUtil.user.getNickName());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (UserUtil.user != null && drawerLayout.isDrawerOpen(Gravity.START)) {
                drawerLayout.closeDrawer(Gravity.START);//先关闭侧拉栏
            } else {
                presenter.doubleExit(keyCode);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public int getContentResId() {
        return R.id.home_content;
    }

    @Override
    public FragmentManager getFm() {
        return getSupportFragmentManager();
    }

    @Override
    public void onSelectTabResult(int curIndex, int nextIndex) {

    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }


    @OnClick({R.id.content_user_head_sdv, R.id.open_suspend_window_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.content_user_head_sdv:
                UserUtil.checkLocalUser(true, this);
                if (UserUtil.user != null) {
                    drawerLayout.openDrawer(Gravity.START);//点击头像出侧拉栏
                }
                break;
            case R.id.open_suspend_window_btn:
                presenter.openSuspendWindows();
                break;
        }
    }


    //侧拉点击事件
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getGroupId()) {
            case R.id.collection_group:
                presenter.enterUserCollectionActivity();
                break;
            case R.id.invite_group:
                IntentUtil.shareText(mContext, "斗图斗图！下载地址：");
                break;
            case R.id.upgrade_group:
                UpdateUtils.getInstance().update(mContext);
                break;
            case R.id.joy_group:
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FlagConstant.REQUEST_LOGIN) {//请求登录
            UserUtil.onActivityResult(resultCode, data);
        }
    }
}
