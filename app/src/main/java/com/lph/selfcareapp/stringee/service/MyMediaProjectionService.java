package com.lph.selfcareapp.stringee.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.ServiceCompat;

import com.lph.selfcareapp.stringee.common.Constant;
import com.lph.selfcareapp.stringee.common.NotificationUtils;
import com.lph.selfcareapp.stringee.common.Utils;
import com.lph.selfcareapp.stringee.manager.CallManager;

public class MyMediaProjectionService extends Service {
    private CallManager callManager;

    @Override
    public void onCreate() {
        super.onCreate();
        callManager = CallManager.getInstance(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (!Utils.isStringEmpty(action)) {
                if (action.equals(Constant.ACTION_START_FOREGROUND_SERVICE)) {
                    Notification notification = NotificationUtils.getInstance(this).createMediaNotification();
                    int type = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        type = ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PROJECTION;
                    }
                    try {
                        ServiceCompat.startForeground(this, Constant.MEDIA_SERVICE_ID, notification, type);
                    } catch (Exception e) {
                        Utils.reportException(CallManager.class, e);
                    }
                    callManager.startCapture(this, intent);
                }
            }
        }
        return START_NOT_STICKY;
    }

    public void stopService() {
        stopForeground(STOP_FOREGROUND_REMOVE);
        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
