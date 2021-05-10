package com.example.teamproject.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamproject.R;
import com.example.teamproject.databinding.ListItemScheduleBinding;
import com.example.teamproject.model.Schedule;
import com.example.teamproject.viewmodel.ScheduleViewModel;
/**
 * Created by 양민주
 */
public class ScheduleRecyclerViewAdapter extends ListAdapter<Schedule, ScheduleRecyclerViewAdapter.ViewHolder> {

    private ScheduleViewModel viewModel;

    public ScheduleRecyclerViewAdapter(@NonNull DiffUtil.ItemCallback<Schedule> diffCallback, ScheduleViewModel viewModel) {
        super(diffCallback);
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemScheduleBinding binding = ListItemScheduleBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Schedule schedule = getItem(position);
        holder.onBind(schedule);
    }


    // viewHolder 클래스(내부클래스)
    public class ViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "ViewHolder";
        private final ListItemScheduleBinding binding;

        public ViewHolder(@NonNull ListItemScheduleBinding listItemScheduleBinding) {
            super(listItemScheduleBinding.getRoot());
            this.binding = listItemScheduleBinding;

            binding.showDetailBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: ");
                    if(binding.detailLayout.getVisibility() == View.GONE){
                        binding.detailLayout.setVisibility(View.VISIBLE);
                        binding.showDetailBtn.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
                        Log.d(TAG, "onClick: VISIBLE");
                    }
                    else if(binding.detailLayout.getVisibility() == View.VISIBLE) {
                        binding.detailLayout.setVisibility(View.GONE);
                        binding.showDetailBtn.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);
                        Log.d(TAG, "onClick: GONE");
                    }
                }
            });

        }
        public void onBind(Schedule schedule){
            binding.showDetailBtn.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);
            binding.detailLayout.setVisibility(View.GONE);
            binding.setSchedule(schedule);
            binding.setViewModel(viewModel);
        }
    }
}
