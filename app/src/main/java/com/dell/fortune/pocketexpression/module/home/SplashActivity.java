package com.dell.fortune.pocketexpression.module.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.util.common.RxTimerUtil;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        RxTimerUtil.timer(1500, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                enterHomeActivity();
            }
        });
    }

    private void enterHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
