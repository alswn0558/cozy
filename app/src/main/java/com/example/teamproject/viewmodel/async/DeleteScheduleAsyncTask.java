package com.example.teamproject.viewmodel.async;

import android.os.AsyncTask;

import com.example.teamproject.viewmodel.ScheduleViewModel;

public class DeleteScheduleAsyncTask extends AsyncTask<String, Void, Boolean> {

    ScheduleViewModel viewModel;


    public DeleteScheduleAsyncTask(ScheduleViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        boolean result = false;
        for(String str : strings)
            result = viewModel.deleteSchedule(str);
        return result;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        viewModel.upDateSchedule(aBoolean);
    }
}
