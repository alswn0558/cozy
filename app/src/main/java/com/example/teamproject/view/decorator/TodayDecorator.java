package com.example.teamproject.view.decorator;

import android.content.Context;

import com.example.teamproject.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
/**
 * Created by 양민주
 */
public class TodayDecorator implements DayViewDecorator {

    private final CalendarDay currentDate;
    private final Context context;

    public TodayDecorator(Context context, CalendarDay currentDate) {
        this.currentDate = currentDate;
        this.context = context;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {

        return currentDate.equals(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(context.getDrawable(R.drawable.ic_today_star));
    }
}
