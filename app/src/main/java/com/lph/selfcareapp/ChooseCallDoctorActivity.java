package com.lph.selfcareapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lph.selfcareapp.model.CallDoctor;
import com.lph.selfcareapp.serviceAPI.RetrofitInstance;
import com.lph.selfcareapp.stringee.activity.CallActivity;
import com.lph.selfcareapp.stringee.activity.StringeeActivity;
import com.lph.selfcareapp.stringee.common.Constant;
import com.lph.selfcareapp.stringee.common.PermissionsUtils;
import com.lph.selfcareapp.stringee.common.Utils;
import com.lph.selfcareapp.stringee.manager.ClientManager;
import com.lph.selfcareapp.view.CallDoctorAdapter;
import com.lph.selfcareapp.viewmodel.ChooseCallDoctorListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseCallDoctorActivity extends AppCompatActivity implements ChooseCallDoctorListener {
    RecyclerView recyclerView;
    List<CallDoctor> doctorList;
    TextView navText;
    ImageView back;
    ClientManager clientManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_call_doctor);
        recyclerView = findViewById(R.id.doctorRecyclerview);
        navText = findViewById(R.id.navText);
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        navText.setText("Tư vấn khám bệnh qua video");
        SharedPreferences sp = getSharedPreferences("UserData", MODE_PRIVATE);
        int id = sp.getInt("id",0);
        RetrofitInstance.getService().getCallDoctor(id).enqueue(new Callback<List<CallDoctor>>() {
            @Override
            public void onResponse(Call<List<CallDoctor>> call, Response<List<CallDoctor>> response) {
                doctorList = response.body();
                CallDoctorAdapter adapter = new CallDoctorAdapter(ChooseCallDoctorActivity.this, doctorList, ChooseCallDoctorActivity.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(ChooseCallDoctorActivity.this));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CallDoctor>> call, Throwable throwable) {

            }
        });
    }

    @Override
    public void onItemCliked(CallDoctor callDoctor) {
        Intent intent = new Intent(ChooseCallDoctorActivity.this, ConfirmCallActivity.class);
        intent.putExtra("callDoctor", callDoctor);
        startActivity(intent);
    }

    @Override
    public void call(String callId) {

        clientManager = ClientManager.getInstance(ChooseCallDoctorActivity.this);

        initAndConnectStringee();

        requestPermission();
        makeCall(true,true,callId);
//        Intent intent = new Intent(ChooseCallDoctorActivity.this, StringeeActivity.class);
//        intent.putExtra("callId", callId);
//        startActivity(intent);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.app_name);
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
//        clientManager.addOnConnectionListener(status -> runOnUiThread(() -> binding.tvStatus.setText(status)));
        clientManager.connect();
    }

    private void makeCall(boolean isStringeeCall, boolean isVideoCall, String callId) {
        if (Utils.isStringEmpty(callId) || !clientManager.getStringeeClient().isConnected()) {
            return;
        }
        if (!clientManager.isPermissionGranted) {
            PermissionsUtils.getInstance().requestPermissions(this);
            return;
        }
        Intent intent = new Intent(this, CallActivity.class);
        intent.putExtra(Constant.PARAM_TO, callId);
        intent.putExtra(Constant.PARAM_IS_VIDEO_CALL, isVideoCall);
        intent.putExtra(Constant.PARAM_IS_INCOMING_CALL, false);
        intent.putExtra(Constant.PARAM_IS_STRINGEE_CALL, isStringeeCall);
        startActivity(intent);
    }
}