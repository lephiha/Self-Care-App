package com.lph.selfcareapp.view;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.lph.selfcareapp.R;
import com.lph.selfcareapp.model.ScheduleTime;
import com.lph.selfcareapp.viewmodel.ChooseTImeListener;

import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeHolder> {
    private List<ScheduleTime> scheduleTimeList;
    private Context context;
    private ChooseTImeListener listener;
    public TimeAdapter(List<ScheduleTime> scheduleTimeList, Context context, ChooseTImeListener listener) {
        this.scheduleTimeList = scheduleTimeList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.date_button_item,
                        parent,
                        false);
        return new TimeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeHolder holder, int position) {
        ScheduleTime scheduleTime = scheduleTimeList.get(position);
        String starttime = scheduleTime.getStartTime();
        starttime = starttime.substring(0, starttime.length()-3);
        String endtime = scheduleTime.getEndTime();
        endtime = endtime.substring(0, endtime.length()-3);
        holder.timeButton.setText(starttime + "-" + endtime);
        if (scheduleTime.getBooked() == 0){
            holder.timeButton.setBackgroundColor(ContextCompat.getColor(context, R.color.link_blue));
            holder.timeButton.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.timeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onButtonClicked(scheduleTime);
                }
            });
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            holder.timeButton.setAllowClickWhenDisabled(false);
        }

    }

    @Override
    public int getItemCount() {
        return scheduleTimeList.size();
    }

    public class TimeHolder extends RecyclerView.ViewHolder{
        Button timeButton;
        public TimeHolder(@NonNull View itemVIew){
            super(itemVIew);
            timeButton = itemVIew.findViewById(R.id.chooseDateBtn);
        }
    }

}
