package com.example.teamproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.teamproject.R;
import com.example.teamproject.adapter.ScheduleRecyclerViewAdapter;
import com.example.teamproject.databinding.FragmentCalendarBinding;
import com.example.teamproject.model.Schedule;
import com.example.teamproject.utill.DateOperation;
import com.example.teamproject.view.decorator.EventDecorator;
import com.example.teamproject.view.decorator.TodayDecorator;
import com.example.teamproject.viewmodel.ScheduleViewModel;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Date;
import java.util.List;
import java.util.Set;
/**
 * Created by 양민주
 */
public class CalendarFragment extends Fragment {

    private static final String TAG = "CalendarFragment";
    private static final int DEFAULT_RADIUS = 7;
    private FragmentCalendarBinding binding;
    private ScheduleViewModel viewModel;
    private ScheduleRecyclerViewAdapter adapter;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        // 데이터 바인딩 셋팅.
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container,false);
        // FAB 클릭시 addScheduleActivity 로 이동셋팅.
        binding.addScheduleFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), AddScheduleActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        // 뷰모델 셋팅 및 라이브데이터 구독.
        viewModel = new ViewModelProvider(requireActivity()).get(ScheduleViewModel.class);
        viewModel.setCurrentSelectedDate(CalendarDay.from(new Date(System.currentTimeMillis())));
        viewModel.getScheduleLiveData().observe(getViewLifecycleOwner(), new Observer<List<Schedule>>() {
            @Override
            public void onChanged(List<Schedule> schedules) {
                //변경된 스케쥴.
                for(Schedule schedule : schedules)
                    Log.d(TAG, "onChanged: "+schedule.toString());
                // 오늘 날짜 또는 사용자가 선택후 일정을 생성하러 갈 경우 어텝터 갱신.
                List<Schedule> scheduleList
                        = viewModel.getPickedDate(viewModel.getCurrentSelectedDate().getDate());
                adapter.submitList(scheduleList);
                // 달력 데코
                decoCalendar();
            }
        });
        // 리사이클러뷰 셋팅.
        adapter = new ScheduleRecyclerViewAdapter(Schedule.scheduleItemCallback, viewModel);
        binding.recyclerView.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 캘린더 데이트 체이지 리스너
        binding.calenderView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Log.d(TAG, "onDateSelected: "+date.getDate().toString());
                viewModel.setCurrentSelectedDate(date);
                List<Schedule> pickedDateSchedule = viewModel.getPickedDate(date.getDate());
                adapter.submitList(pickedDateSchedule);
            }
        });
        Log.d(TAG, "onActivityCreated: ");
    }

    // 달력에 점 및 오늘 날짜 강조.
    private void decoCalendar() {
        binding.calenderView.removeDecorators();
        Set<CalendarDay> dormitory_dot = viewModel.makeDotSet(Schedule.TYPE_DORMITORY);
        Set<CalendarDay> individual_dot = viewModel.makeDotSet(Schedule.TYPE_INDIVIDUAL);

        Log.d(TAG, "decoCalendar: ");
        binding.calenderView.addDecorators(
                new EventDecorator(EventDecorator.DECO_TYPE_DORMITORY,
                        DateOperation.difference(dormitory_dot, individual_dot),
                        DEFAULT_RADIUS),
                new EventDecorator(EventDecorator.DECO_TYPE_INDIVIDUAL,
                        DateOperation.difference(individual_dot, dormitory_dot),
                        DEFAULT_RADIUS),
                new EventDecorator(EventDecorator.DECO_TYPE_ALL,
                        DateOperation.Intersection(dormitory_dot, individual_dot),
                        DEFAULT_RADIUS),
                new TodayDecorator(requireContext(), CalendarDay.today())
        );
    }

}