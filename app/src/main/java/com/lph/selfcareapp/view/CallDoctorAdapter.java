package com.lph.selfcareapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.lph.selfcareapp.ChooseCallDoctorActivity;
import com.lph.selfcareapp.R;
import com.lph.selfcareapp.databinding.CallDoctorListItemBinding;
import com.lph.selfcareapp.databinding.DoctorListItemBinding;
import com.lph.selfcareapp.model.CallDoctor;
import com.lph.selfcareapp.model.Doctor;
import com.lph.selfcareapp.viewmodel.ChooseCallDoctorListener;
import com.lph.selfcareapp.viewmodel.ChooseDoctorListener;

import java.util.List;

public class CallDoctorAdapter extends RecyclerView.Adapter<CallDoctorAdapter.CallDoctorHolder> {
    private Context context;
    private List<CallDoctor> doctorList;
    private ChooseCallDoctorListener listener;
    public CallDoctorAdapter(Context context, List<CallDoctor> doctorList, ChooseCallDoctorListener listener){
        this.context = context;
        this.doctorList = doctorList;
        this.listener = listener;
    }
    public class CallDoctorHolder extends RecyclerView.ViewHolder{
        private CallDoctorListItemBinding callDoctorListItemBinding;
        public CallDoctorHolder(CallDoctorListItemBinding callDoctorListItemBinding){
            super(callDoctorListItemBinding.getRoot());
            this.callDoctorListItemBinding = callDoctorListItemBinding;
        }
    }

    @NonNull
    @Override
    public CallDoctorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CallDoctorListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.call_doctor_list_item,
                parent,
                false
        );
            return new CallDoctorHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CallDoctorHolder holder, int position) {
        CallDoctor doctor = doctorList.get(position);
        String price = String.format("%,d", doctor.getPrice());
        price = price +"đ";
        String docname = doctor.getAcacdemicrank() + " " + doctor.getDocname();
        if(doctor.getSex().equals("M"))
            holder.callDoctorListItemBinding.imageView.setImageResource(R.drawable.maledoctor);
        else
            holder.callDoctorListItemBinding.imageView.setImageResource(R.drawable.femaledoctor);
        holder.callDoctorListItemBinding.textView10.setText(price);
        holder.callDoctorListItemBinding.textView6.setText(docname);
        holder.callDoctorListItemBinding.textView8.setText(doctor.getSname());
        holder.callDoctorListItemBinding.materialButton.setOnClickListener(v -> listener.onItemCliked(doctor));
        holder.callDoctorListItemBinding.materialButton2.setOnClickListener(v -> listener.call(doctor.getCallId()));
        if(doctor.getPaid().equals("1")){
            holder.callDoctorListItemBinding.materialButton.setVisibility(View.GONE);
            holder.callDoctorListItemBinding.materialButton2.setVisibility(View.VISIBLE);
        }else{
            holder.callDoctorListItemBinding.materialButton.setVisibility(View.VISIBLE);
            holder.callDoctorListItemBinding.materialButton2.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }
}