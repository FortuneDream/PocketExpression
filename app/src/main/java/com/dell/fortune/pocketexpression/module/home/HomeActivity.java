/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.pocketexpression.module.home;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.dell.fortune.core.common.BaseActivity;
import com.dell.fortune.core.config.FlagConstant;
import com.dell.fortune.core.util.UserUtil;
import com.dell.fortune.pocketexpression.app.R;
import com.dell.fortune.pocketexpression.util.common.FrescoProxy;
import com.dell.fortune.pocketexpression.util.common.JNIUtil;
import com.dell.fortune.tools.LogUtils;
import com.dell.fortune.tools.tab.BottomTabView;
import com.dell.fortune.tools.toast.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends BaseActivity<HomePresenter.IView, HomePresenter>
        implements HomePresenter.IView, View.OnClickListener {

    private List<BottomTabView> bottomTabViews = new ArrayList<>();
    private boolean mIsAuthorityStatusOpen;
    private SimpleDraweeView mContentUserHeadSdv;
    private SwitchCompat mOpenSuspendWindowSwitch;
    private Toolbar mToolbar;
    private ViewFlipper mInfoFlipper;
    private FrameLayout mHomeContent;
    private BottomTabView mHomeCategoryTab;
    private BottomTabView mHomeCollectionTab;
    private HomeNavigationView mHomeUserNv;
    private DrawerLayout mDrawerLayout;

    @Override
    public int setContentResource() {
        return R.layout.app_activity_home;
    }

    @Override
    public void initView() {
        if (!UserUtil.checkLocalUser(false, this)) {
            ToastUtil.showToast("登录可以收藏表情包哦~");
        }
        initFlipper();
        initBottomTabList();
        mIsAuthorityStatusOpen = presenter.checkAuthority();
        mOpenSuspendWindowSwitch.setChecked(presenter.checkAuthority());//检测是否打开
        presenter.clickBottomTab(0);
        ToastUtil.showToast(new JNIUtil().getSignature());
    }

    //底部栏
    private void initBottomTabList() {
        bottomTabViews.add(0, mHomeCategoryTab);
        bottomTabViews.add(1, mHomeCollectionTab);
    }


    //消息栏
    private void initFlipper() {
        LinearLayout beginLl = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.app_item_info_flipper, null);
        TextView beginTv = beginLl.findViewById(R.id.info_tv);
        beginTv.setText("数码宝贝世界大门打开倒计时！");
        mInfoFlipper.addView(beginLl);
        for (int i = 10; i >= 1; i--) {
            LinearLayout contentLl = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.app_item_info_flipper, null);
            TextView infoTv = contentLl.findViewById(R.id.info_tv);
            infoTv.setText(i + "!");
            mInfoFlipper.addView(contentLl);
        }
        LinearLayout endLl = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.app_item_info_flipper, null);
        TextView endTv = endLl.findViewById(R.id.info_tv);
        endTv.setText("打开！");
        mInfoFlipper.addView(endLl);
        mInfoFlipper.startFlipping();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (UserUtil.user != null) {
            LogUtils.e("已登录用户：", UserUtil.user.toString());
            setHeadUrl(UserUtil.user.getHeadUrl());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (UserUtil.user != null && mDrawerLayout.isDrawerOpen(Gravity.START)) {
                mDrawerLayout.closeDrawer(Gravity.START);//先关闭侧拉栏
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
        for (int i = 0; i < bottomTabViews.size(); i++) {
            if (i == nextIndex) {
                BottomTabView selectView = bottomTabViews.get(i);
                selectView.onSelect(true);
                selectAnim(selectView);
            } else {
                BottomTabView unselectView = bottomTabViews.get(i);
                unselectView.onSelect(false);
            }
        }
        if (curIndex != presenter.mCurIndex) {//初始态不变
            unselectAnim(bottomTabViews.get(curIndex));
        }
    }


    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected void findViewSetListener() {
        mContentUserHeadSdv = (SimpleDraweeView) findViewById(R.id.content_user_head_sdv);
        mContentUserHeadSdv.setOnClickListener(this);
        mOpenSuspendWindowSwitch = (SwitchCompat) findViewById(R.id.open_suspend_window_switch);
        mOpenSuspendWindowSwitch.setOnClickListener(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mInfoFlipper = (ViewFlipper) findViewById(R.id.info_flipper);
        mHomeContent = (FrameLayout) findViewById(R.id.home_content);
        mHomeCategoryTab = (BottomTabView) findViewById(R.id.home_category_tab);
        mHomeCategoryTab.setOnClickListener(this);
        mHomeCollectionTab = (BottomTabView) findViewById(R.id.home_collection_tab);
        mHomeCollectionTab.setOnClickListener(this);
        mHomeUserNv = (HomeNavigationView) findViewById(R.id.home_user_nv);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }


    //修改当前状态
    private void changeSwitchStatus() {
        if (mIsAuthorityStatusOpen) {//当前状态为打开
            presenter.closeSuspend();
            mIsAuthorityStatusOpen = !mIsAuthorityStatusOpen;
        } else {
            boolean isReallyOpen = presenter.openSuspend();//是否真正的开了
            if (isReallyOpen) {
                mIsAuthorityStatusOpen = !mIsAuthorityStatusOpen;
            }
        }
        mOpenSuspendWindowSwitch.setChecked(mIsAuthorityStatusOpen);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FlagConstant.REQUEST_LOGIN) {//请求登录
            UserUtil.onActivityResult(resultCode, data);
        }
        if (resultCode == RESULT_OK) {//选择图片后的回调
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    LocalMedia localMedia = selectList.get(0);
                    LogUtils.e("原图路径", localMedia.getPath());
                    presenter.changeHead(localMedia.getPath());
                    break;
            }
        }
    }

    //未选中动画
    private void unselectAnim(View view) {
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1.1f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", 1.1f, 1f);
        AnimatorSet set = new AnimatorSet();
        List<Animator> list = new ArrayList<>();
        list.add(scaleXAnimator);
        list.add(scaleYAnimator);
        set.setDuration(200);
        set.playTogether(list);
        set.start();
    }

    //选中动画
    private void selectAnim(View view) {
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.1f);
        AnimatorSet set = new AnimatorSet();
        List<Animator> list = new ArrayList<>();
        list.add(scaleXAnimator);
        list.add(scaleYAnimator);
        set.setDuration(200);
        set.playTogether(list);
        set.start();
    }

    //两个Head
    @Override
    public void setHeadUrl(String url) {
        mHomeUserNv.setHead(url);
        FrescoProxy.showNetSimpleView(mContentUserHeadSdv, url);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.content_user_head_sdv) {
            UserUtil.checkLocalUser(true, this);
            if (UserUtil.user != null) {
                mDrawerLayout.openDrawer(Gravity.START);//点击头像出侧拉栏
            }

        } else if (i == R.id.home_category_tab) {
            presenter.clickBottomTab(0);

        } else if (i == R.id.home_collection_tab) {
            if (UserUtil.checkLocalUser(true, this)) {
                presenter.clickBottomTab(1);
            }

        } else if (i == R.id.open_suspend_window_switch) {
            changeSwitchStatus();

        }
    }
}
