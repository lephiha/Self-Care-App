package com.lph.selfcareapp.stringee.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleObserver;

import com.lph.selfcareapp.R.id;
import com.lph.selfcareapp.R.string;
import com.lph.selfcareapp.databinding.StringeeActivityBinding;
import com.lph.selfcareapp.stringee.common.Constant;
import com.lph.selfcareapp.stringee.common.PermissionsUtils;
import com.lph.selfcareapp.stringee.common.Utils;
import com.lph.selfcareapp.databinding.ActivityMainBinding;
import com.lph.selfcareapp.stringee.manager.ClientManager;

public class StringeeActivity extends AppCompatActivity implements View.OnClickListener, LifecycleObserver {
    private StringeeActivityBinding binding;
    private ClientManager clientManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = StringeeActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent i= getIntent();
        String callId = i.getStringExtra("callId");
        binding.etTo.setText(callId);
//        binding.btnVoiceCall.setOnClickListener(this);
//        binding.btnVideoCall.setOnClickListener(this);
//        binding.btnVoiceCall2.setOnClickListener(this);
//        binding.btnVideoCall2.setOnClickListener(this);
//        makeCall(true, true);
        clientManager = ClientManager.getInstance(this);

        initAndConnectStringee();

        requestPermission();

    }

    private void requestPermission() {
        if (!PermissionsUtils.getInstance().checkSelfPermission(this)) {
            PermissionsUtils.getInstance().requestPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isGranted = PermissionsUtils.getInstance().verifyPermissions(grantResults);
        if (requestCode == PermissionsUtils.REQUEST_PERMISSION) {
            clientManager.isPermissionGranted = isGranted;
            if (!isGranted) {
                if (PermissionsUtils.getInstance().shouldRequestPermissionRationale(this)) {
                    Builder builder = new Builder(this);
                    builder.setTitle(string.app_name);
                    builder.setMessage("Permissions must be granted for the call");
                    builder.setPositiveButton("Ok", (dialogInterface, id) -> dialogInterface.cancel());
                    builder.setNegativeButton("Settings", (dialogInterface, id) -> {
                        dialogInterface.cancel();
                        // open app setting
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    });
                    builder.create().show();
                }
            }
        }
    }

    public void initAndConnectStringee() {
        clientManager.addOnConnectionListener(status -> runOnUiThread(() -> binding.tvStatus.setText(status)));
        clientManager.connect();
    }

    @Override
    public void onClick(View view) {
        int vId = view.getId();
        if (vId == id.btn_voice_call) {
            makeCall(true, false);
        } else if (vId == id.btn_video_call) {
            makeCall(true, true);
        } else if (vId == id.btn_voice_call2) {
            makeCall(false, false);
        } else if (vId == id.btn_video_call2) {
            makeCall(false, true);
        }
    }

    private void makeCall(boolean isStringeeCall, boolean isVideoCall) {
        if (Utils.isStringEmpty(binding.etTo.getText()) || !clientManager.getStringeeClient().isConnected()) {
            return;
        }
        if (!clientManager.isPermissionGranted) {
            PermissionsUtils.getInstance().requestPermissions(this);
            return;
        }
        Intent intent = new Intent(this, CallActivity.class);
        intent.putExtra(Constant.PARAM_TO, binding.etTo.getText().toString().trim());
        intent.putExtra(Constant.PARAM_IS_VIDEO_CALL, isVideoCall);
        intent.putExtra(Constant.PARAM_IS_INCOMING_CALL, false);
        intent.putExtra(Constant.PARAM_IS_STRINGEE_CALL, isStringeeCall);
        startActivity(intent);
    }
}
