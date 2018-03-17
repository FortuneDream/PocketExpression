package com.dell.fortune.pocketexpression;

import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(this)) {//悬浮权限
                Intent intent = new Intent(this, MyService.class);
                startService(intent);
                finish();
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                Toast.makeText(this, "需要取得悬浮权限", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        } else {
            Intent intent=new Intent(this,MyService.class);
            startService(intent);
            finish();
        }
    }
}
