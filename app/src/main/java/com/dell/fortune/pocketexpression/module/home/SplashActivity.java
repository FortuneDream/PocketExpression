package com.dell.fortune.pocketexpression.module.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.module.HomeActivity;
import com.dell.fortune.pocketexpression.util.common.RxTimerUtil;
import com.dell.fortune.pocketexpression.util.common.ToastUtil;
import com.dell.fortune.tools.dialog.DialogSure;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.util.List;

public class SplashActivity extends AppCompatActivity {
//    private Rationale mRationale = new Rationale() {
//        @Override
//        public void showRationale(Context context, Object data, final RequestExecutor executor) {
//            DialogSure dialogSure = new DialogSure(SplashActivity.this);
//            dialogSure.getTvTitle().setText("权限说明");
//            dialogSure.getTvContent().setText("读取外部存储卡：存储表情包\n读取手机信息：识别唯一手机,如这两个权限应用程序无法正常运行。");
//            dialogSure.getTvSure().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    executor.execute();
//                }
//            });
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AndPermission.with(this)
                .runtime()
                .permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE, Permission.READ_PHONE_STATE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        RxTimerUtil.timer(1000, new RxTimerUtil.IRxNext() {
                            @Override
                            public void doNext(long number) {
                                enterHomeActivity();
                            }
                        });
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        if (AndPermission.hasAlwaysDeniedPermission(SplashActivity.this, data)) {
                            ToastUtil.showToast("请到设置中心设置权限");
                        }
                        finish();
                    }
                })
                .start();

    }

    private void enterHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
