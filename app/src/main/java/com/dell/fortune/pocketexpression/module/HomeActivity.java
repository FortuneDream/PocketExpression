package com.dell.fortune.pocketexpression.module;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.dell.fortune.pocketexpression.util.common.IntentUtil;
import com.dell.fortune.pocketexpression.util.common.UserUtil;
import com.dell.fortune.pocketexpression.util.common.update.UpdateUtils;
import com.dell.fortune.tools.info.LogUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity<HomePresenter.IView, HomePresenter>
        implements HomePresenter.IView, NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.content_user_head_sdv)
    SimpleDraweeView contentUserHeadSdv;
    @BindView(R.id.open_suspend_window_btn)
    Button openSuspendWindowBtn;
    @BindView(R.id.home_user_nv)
    NavigationView homeUserNv;
    @BindView(R.id.info_flipper)
    ViewFlipper infoFlipper;
    @BindView(R.id.category_tv)
    TextView categoryTv;
    @BindView(R.id.find_tv)
    TextView findTv;
    @BindView(R.id.make_tv)
    TextView makeTv;
    @BindView(R.id.tab_ll)
    LinearLayout tabLl;
    @BindView(R.id.home_content)
    FrameLayout homeContent;

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
        presenter.clickBottomTab(0);
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
            FrescoProxy.showSimpleView(contentHeadSdv, UserUtil.user.getHeadUrl());
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
        if (curIndex != -1 && nextIndex != -1) {
            tabLl.getChildAt(curIndex).setBackgroundResource(R.drawable.shape_home_tab_normal);
            tabLl.getChildAt(nextIndex).setBackgroundResource(R.drawable.shape_home_tab_press);
        } else {
            //第一次加载
            tabLl.getChildAt(0).setBackgroundResource(R.drawable.shape_home_tab_press);
            for (int i = 1; i < 3; i++) {
                tabLl.getChildAt(i).setBackgroundResource(R.drawable.shape_home_tab_normal);
            }
        }

    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }


    @OnClick({R.id.content_user_head_sdv, R.id.open_suspend_window_btn, R.id.category_tv, R.id.find_tv, R.id.make_tv})
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
            case R.id.category_tv:
                presenter.clickBottomTab(0);
                break;
            case R.id.find_tv:
                presenter.clickBottomTab(1);
                break;
            case R.id.make_tv:
                presenter.clickBottomTab(2);
                break;
        }
    }


    //侧拉点击事件
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.collection_item:
                presenter.enterUserCollectionActivity();
                break;
            case R.id.invite_item:
                IntentUtil.shareText(mContext, "斗图斗图！下载地址：");
                break;
            case R.id.upgrade_item:
                UpdateUtils.getInstance().update(mContext);
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
}
