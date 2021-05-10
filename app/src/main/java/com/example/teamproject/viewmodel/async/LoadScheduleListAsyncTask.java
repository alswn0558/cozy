package com.example.teamproject.viewmodel.async;

import android.os.AsyncTask;

import com.example.teamproject.model.Schedule;
import com.example.teamproject.viewmodel.ScheduleViewModel;

import java.util.List;

public class LoadScheduleListAsyncTask extends AsyncTask<Void, List<Schedule>, List<Schedule>> {

    ScheduleViewModel viewModel;

    public LoadScheduleListAsyncTask(ScheduleViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    protected List<Schedule> doInBackground(Void... voids) {
        return viewModel.loadSchedules();
    }

    @Override
    protected void onPostExecute(List<Schedule> schedules) {
        super.onPostExecute(schedules);
        viewModel.setMutableScheduleLiveData(schedules);
    }
}