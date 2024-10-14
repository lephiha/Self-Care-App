package com.lph.selfcareapp.stringee.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lph.selfcareapp.stringee.common.Constant;
import com.lph.selfcareapp.stringee.common.NotificationUtils;
import com.lph.selfcareapp.stringee.manager.CallManager;

public class RejectCallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationUtils.getInstance(context).cancelNotification(Constant.INCOMING_CALL_ID);
        CallManager.getInstance(context).endCall(false);
    }
}