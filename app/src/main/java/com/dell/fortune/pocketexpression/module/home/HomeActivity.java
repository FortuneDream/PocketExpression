/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.pocketexpression.module.home;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.common.BaseActivity;
import com.dell.fortune.pocketexpression.config.FlagConstant;
import com.dell.fortune.pocketexpression.util.common.FrescoProxy;
import com.dell.fortune.pocketexpression.util.common.UserUtil;
import com.dell.fortune.tools.IntentUtil;
import com.dell.fortune.tools.LogUtils;
import com.dell.fortune.tools.tab.BottomTabView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity<HomePresenter.IView, HomePresenter>
        implements HomePresenter.IView, NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.content_user_head_sdv)
    SimpleDraweeView contentUserHeadSdv;
    @BindView(R.id.open_suspend_window_btn)
    Button openSuspendWindowBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.info_flipper)
    ViewFlipper infoFlipper;
    @BindView(R.id.home_content)
    FrameLayout homeContent;
    @BindView(R.id.home_user_nv)
    NavigationView homeUserNv;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.home_category_tab)
    BottomTabView homeCategoryTab;
    @BindView(R.id.home_collection_tab)
    BottomTabView homeCollectionTab;
    private List<BottomTabView> bottomTabViews = new ArrayList<>();

    @Override
    public int setContentResource() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {
        homeUserNv.setNavigationItemSelectedListener(this);
        homeUserNv.setItemIconTintList(null);
        UserUtil.checkLocalUser(false, this);
        initFlipper();
        initBottomTabList();
        presenter.clickBottomTab(0);
    }

    private void initBottomTabList() {
        bottomTabViews.add(0, homeCategoryTab);
        bottomTabViews.add(1, homeCollectionTab);
    }


    private void initFlipper() {
        for (int i = 0; i < 5; i++) {
            LinearLayout contentLl = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.item_info_flipper, null);
            TextView infoTv = contentLl.findViewById(R.id.info_tv);
            infoTv.setText("这是第" + i + "条");
            infoFlipper.addView(contentLl);
        }
        infoFlipper.startFlipping();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //判断用户是否登录
        if (UserUtil.user != null) {
            LogUtils.e("已登录用户：", UserUtil.user.toString());
            //Toolbar
            SimpleDraweeView contentHeadSdv = findViewById(R.id.content_user_head_sdv);
            FrescoProxy.showNetSimpleView(contentHeadSdv, UserUtil.user.getHeadUrl());
            //侧拉
            LinearLayout headerLl = (LinearLayout) homeUserNv.getHeaderView(0);
            SimpleDraweeView headerHeadIv = headerLl.findViewById(R.id.user_head_sdv);
            headerHeadIv.setImageURI(Uri.parse(UserUtil.user.getHeadUrl()));
            headerHeadIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //点击头像，修改头像
                    presenter.openPictureSelector();
                }
            });
            TextView headerNickNameTv = headerLl.findViewById(R.id.user_nick_name_tv);
            headerNickNameTv.setText(UserUtil.user.getNickName());
        }
        presenter.checkAuthority();
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


    @OnClick({R.id.content_user_head_sdv, R.id.open_suspend_window_btn, R.id.home_category_tab, R.id.home_collection_tab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.content_user_head_sdv:
                UserUtil.checkLocalUser(true, this);
                if (UserUtil.user != null) {
                    drawerLayout.openDrawer(Gravity.START);//点击头像出侧拉栏
                }
                break;
            case R.id.open_suspend_window_btn:
                presenter.openSuspend();
                break;
            case R.id.home_category_tab:
                presenter.clickBottomTab(0);
                break;
            case R.id.home_collection_tab:
                presenter.clickBottomTab(1);
                break;
        }
    }


    //侧拉点击事件
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.invite_item:
                IntentUtil.shareText(mContext, "斗图斗图！下载地址：");
                break;
            case R.id.upgrade_item:
                //更新
                presenter.checkVersion();
                break;
            case R.id.exit_item:
                presenter.exitUser();
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
        if (resultCode == RESULT_OK) {//选择图片后的回调
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    LocalMedia localMedia = selectList.get(0);
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
}
