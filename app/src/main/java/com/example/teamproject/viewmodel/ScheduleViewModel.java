package com.example.teamproject.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.teamproject.model.Schedule;
import com.example.teamproject.repository.ScheduleRepo;
import com.example.teamproject.utill.DateOperation;
import com.example.teamproject.view.AddScheduleActivity;
import com.example.teamproject.viewmodel.async.DeleteScheduleAsyncTask;
import com.example.teamproject.viewmodel.async.LoadScheduleListAsyncTask;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class ScheduleViewModel extends ViewModel {

    private static final String TAG = "ScheduleViewModel";
    private final ScheduleRepo scheduleRepo = new ScheduleRepo();
    private MutableLiveData<List<Schedule>> mutableScheduleLiveData = new MutableLiveData<>();;
    private Schedule newSchedule;
    private CalendarDay currentSelectedDate;

    //일정 저장
    public boolean saveSchedule(Schedule schedule) {
        boolean result = scheduleRepo.uploadSchedule(schedule);
        if (result) {
            Log.d(TAG, "saveSchedule: " + result);
            newSchedule = null;
            return result;
        }
        return false;
    }

    public Schedule getNewSchedule() {
        if (newSchedule == null) {
            newSchedule = new Schedule(UUID.randomUUID().toString());
        }
        return newSchedule;
    }
    // 저장될 스케줄에 대한 검사
    public boolean inspectionSchedule(Schedule schedule) {
        if (schedule.getDate() == null)
            return false;
        if (schedule.getTitle() == null || schedule.getTitle().isEmpty())
            return false;
        if (schedule.getContents() == null || schedule.getContents().isEmpty())
            return false;
        if (schedule.getWriter() == null || schedule.getWriter().isEmpty())
            return false;
        return true;
    }
    // 메인 액티비티 실행시 데이터 갱신.
    public void upDateSchedule(){
        LoadScheduleListAsyncTask asyncTask = new LoadScheduleListAsyncTask(this);
        asyncTask.execute();
    }
    // 삭제시 실행.
    public void upDateSchedule(Boolean result){
        LoadScheduleListAsyncTask asyncTask = new LoadScheduleListAsyncTask(this);
        asyncTask.execute();
    }
    // 라이브 데이터 공급
    public LiveData<List<Schedule>> getScheduleLiveData() {
        if (mutableScheduleLiveData == null)
            mutableScheduleLiveData = new MutableLiveData<>();

        return mutableScheduleLiveData;
    }
    // 라이브 데이터 내부값변경.
    public void setMutableScheduleLiveData(List<Schedule> scheduleList) {
        mutableScheduleLiveData.setValue(scheduleList);
    }
    // repo 에서 데이터 받아옴.(비동기 실행)
    public List<Schedule> loadSchedules() {
        return scheduleRepo.loadSchedules();
    }

    public Set<CalendarDay> makeDotSet(String Type) {

        Set<CalendarDay> scheduleDates = new HashSet<>();
        List<Date> dateList = new ArrayList<>();

        for (Schedule schedule : Objects.requireNonNull(mutableScheduleLiveData.getValue())) {
            if (schedule.getType().equals(Type))
                dateList.add(schedule.getDate());
        }
        for (Date date : dateList) {
            int[] intDate = DateOperation.getIntDate(date);
            scheduleDates.add(CalendarDay.from(intDate[0], intDate[1], intDate[2]));
        }
        return scheduleDates;
    }
    // 달력날짜 선택시 맞는 스케줄 제공.
    public List<Schedule> getPickedDate(Date pickedDate) {
        if (mutableScheduleLiveData == null)
            return null;
        //Log.d(TAG, "getPickedDate:pickedDate "+pickedDate.toString());
        List<Schedule> scheduleList = mutableScheduleLiveData.getValue();
        List<Schedule> pickedDateScheduleList = new ArrayList<>();

        for(Schedule schedule : scheduleList){
            if(pickedDate.equals(schedule.getDate()))
                pickedDateScheduleList.add(schedule);
        }
        //Log.d(TAG, "getPickedDate: "+pickedDateScheduleList.toString());
        return pickedDateScheduleList;
    }

    public CalendarDay getCurrentSelectedDate() {
        return currentSelectedDate;
    }

    public void setCurrentSelectedDate(CalendarDay currentSelectedDate) {
        this.currentSelectedDate = currentSelectedDate;
    }

    public void onDeleteBtnClicked(String scheduleId) {
        Log.d(TAG, "onDeleteSchedule: "+scheduleId);
        DeleteScheduleAsyncTask deleteScheduleAsyncTask = new DeleteScheduleAsyncTask(this);
        deleteScheduleAsyncTask.execute(scheduleId);
    }

    public boolean deleteSchedule(String scheduleId){
        return scheduleRepo.deleteSchedule(scheduleId);
    }

    public void onUpdateSchedule(View view, Schedule schedule) {
        Log.d(TAG, "onUpdateSchedule: "+ schedule.toString());
        Context context = view.getContext();
        Intent intent = new Intent(view.getContext(), AddScheduleActivity.class);
        intent.putExtra("upDateSchedule", schedule);
        context.startActivity(intent);
    }

    public void setNewSchedule(Schedule newSchedule) {
        this.newSchedule = newSchedule;
    }
}