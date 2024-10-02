package com.lph.selfcareapp.viewmodel;

import android.widget.TextView;

import com.lph.selfcareapp.model.Doctor;

public interface ChooseDoctorListener {
    void onItemClicked(Doctor doctor);
}
