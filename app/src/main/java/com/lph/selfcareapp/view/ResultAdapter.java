package com.lph.selfcareapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lph.selfcareapp.R;
import com.lph.selfcareapp.databinding.DiagnoseListItemPatientBinding;
import com.lph.selfcareapp.databinding.TicketListItemBinding;
import com.lph.selfcareapp.model.Appointment;
import com.lph.selfcareapp.model.Appointment2;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.TicketHolder>{
    private Context context;
    private List<Appointment2> ticketList;

    public ResultAdapter(Context context, List<Appointment2> ticketList) {
        this.context = context;
        this.ticketList = ticketList;
    }

    @NonNull
    @Override
    public TicketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DiagnoseListItemPatientBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.diagnose_list_item_patient,
                parent,
                false
        );
        return new TicketHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketHolder holder, int position) {
        Appointment2 appointment = ticketList.get(position);
        holder.diagnoseListItemPatientBinding.ticketId.setText("Mã phiếu: " + appointment.getAppoid());
        holder.diagnoseListItemPatientBinding.ticketDoctor.setText("Bác sĩ: " + appointment.getDocname());
        holder.diagnoseListItemPatientBinding.ticketPatient.setText("Bệnh nhân: " + appointment.getPname());
        holder.diagnoseListItemPatientBinding.ticketTime.setText("GIờ khám: " + appointment.getScheduledate() + " " + appointment.getStarttime() +"-" + appointment.getEndtime());
        Glide.with(holder.diagnoseListItemPatientBinding.diagImageview.getContext()).load(appointment.getImage()).into(holder.diagnoseListItemPatientBinding.diagImageview);
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    public class TicketHolder extends RecyclerView.ViewHolder{
        private DiagnoseListItemPatientBinding diagnoseListItemPatientBinding;
        public TicketHolder(DiagnoseListItemPatientBinding ticketListItemBinding){
            super(ticketListItemBinding.getRoot());
            this.diagnoseListItemPatientBinding = ticketListItemBinding;
        }
    }
}
