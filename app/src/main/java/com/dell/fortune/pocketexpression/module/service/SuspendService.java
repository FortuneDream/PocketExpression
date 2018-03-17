package com.dell.fortune.pocketexpression.module.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SuspendService extends Service {
    public SuspendService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
