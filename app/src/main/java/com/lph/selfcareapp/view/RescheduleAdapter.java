package com.lph.selfcareapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.lph.selfcareapp.R;
import com.lph.selfcareapp.databinding.RescheduleListItemBinding;
import com.lph.selfcareapp.databinding.TicketListItemBinding;
import com.lph.selfcareapp.model.Appointment;
import com.lph.selfcareapp.model.Reschedule;

import java.util.List;

public class RescheduleAdapter extends RecyclerView.Adapter<RescheduleAdapter.TicketHolder>{
    private Context context;
    private List<Reschedule> ticketList;

    public RescheduleAdapter(Context context, List<Reschedule> ticketList) {
        this.context = context;
        this.ticketList = ticketList;
    }

    @NonNull
    @Override
    public TicketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RescheduleListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.reschedule_list_item,
                parent,
                false
        );
        return new TicketHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketHolder holder, int position) {
        Reschedule appointment = ticketList.get(position);
        holder.rescheduleListItemBinding.ticketDoctor.setText("Bác sĩ: " + appointment.getDocname());
        holder.rescheduleListItemBinding.ticketPatient.setText("Bệnh nhân: " + appointment.getPname());
        holder.rescheduleListItemBinding.ticketClinic.setText("Phòng khám: " + appointment.getClinicname());
        holder.rescheduleListItemBinding.ticketaddress.setText("Địa chỉ: " + appointment.getAddress());
        holder.rescheduleListItemBinding.ticketdate.setText("Ngày tái khám: " + appointment.getScheduuledate());
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    public class TicketHolder extends RecyclerView.ViewHolder{
        private RescheduleListItemBinding rescheduleListItemBinding;
        public TicketHolder(RescheduleListItemBinding rescheduleListItemBinding){
            super(rescheduleListItemBinding.getRoot());
            this.rescheduleListItemBinding = rescheduleListItemBinding;
        }
    }
}
