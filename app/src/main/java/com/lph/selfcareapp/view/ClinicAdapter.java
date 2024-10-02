package com.lph.selfcareapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.lph.selfcareapp.R;
import com.lph.selfcareapp.databinding.ClinicListItemBinding;
import com.lph.selfcareapp.model.Clinic;
import com.lph.selfcareapp.model.ClinicList;

public class ClinicAdapter extends RecyclerView.Adapter<ClinicAdapter.CLinicHolder>{
    private Context context;
    private ClinicList clinicList;

    public ClinicAdapter(Context context, ClinicList clinicList){
        this.context = context;
        this.clinicList = clinicList;
    }

    @NonNull
    @Override
    public CLinicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ClinicListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.clinic_list_item,
                parent,
                false
        );
                return new CLinicHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CLinicHolder holder, int position) {
        Clinic clinic = clinicList.get(position);
        holder.clinicListItemBinding.setClinic(clinic);
    }

    @Override
    public int getItemCount() {
        return clinicList.size();
    }

    public class CLinicHolder extends RecyclerView.ViewHolder{
        private ClinicListItemBinding clinicListItemBinding;
        public CLinicHolder(ClinicListItemBinding clinicListItemBinding){
            super(clinicListItemBinding.getRoot());
            this.clinicListItemBinding = clinicListItemBinding;
        }
    }
}
