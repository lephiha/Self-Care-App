package com.lph.selfcareapp.view;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.lph.selfcareapp.R;
import com.lph.selfcareapp.databinding.DiagnoseListItemBinding;
import com.lph.selfcareapp.databinding.TicketActivityBinding;
import com.lph.selfcareapp.databinding.TicketListItemBinding;
import com.lph.selfcareapp.model.Appointment;
import com.lph.selfcareapp.viewmodel.ChooseDoctorListener;
import com.lph.selfcareapp.viewmodel.DiagnoseListener;

import java.util.List;

public class DiagnoseAdapter extends RecyclerView.Adapter<DiagnoseAdapter.TicketHolder>{
    private Context context;
    private List<Appointment> ticketList;
    private DiagnoseListener listener;
    public DiagnoseAdapter(Context context, List<Appointment> ticketList, DiagnoseListener listener) {
        this.context = context;
        this.ticketList = ticketList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TicketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DiagnoseListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.diagnose_list_item,
                parent,
                false
        );
        return new TicketHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketHolder holder, int position) {
        Appointment appointment = ticketList.get(position);
        holder.ticketListItemBinding.ticketId.setText("Mã phiếu: " + appointment.getAppoid());
        holder.ticketListItemBinding.ticketDoctor.setText("Bác sĩ: " + appointment.getDocname());
        holder.ticketListItemBinding.ticketPatient.setText("Bệnh nhân: " + appointment.getPname());
        holder.ticketListItemBinding.ticketTime.setText("GIờ khám: " + appointment.getScheduledate() + " " + appointment.getStarttime() +"-" + appointment.getEndtime());
        holder.ticketListItemBinding.diagnoseBtn.setOnClickListener(v->listener.onButtonClicked(appointment));
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    public class TicketHolder extends RecyclerView.ViewHolder{
        private DiagnoseListItemBinding ticketListItemBinding;
        public TicketHolder(DiagnoseListItemBinding ticketListItemBinding){
            super(ticketListItemBinding.getRoot());
            this.ticketListItemBinding = ticketListItemBinding;
        }
    }
}
