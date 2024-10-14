package com.lph.selfcareapp.viewmodel;

import com.lph.selfcareapp.model.CallDoctor;

public interface ChooseCallDoctorListener {
    void onItemCliked(CallDoctor callDoctor);
    void call(String callId);
}
