package com.example.teamproject.view.decorator;

import android.graphics.Color;
import android.util.Log;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;
/**
 * Created by 양민주
 */
public class EventDecorator implements DayViewDecorator {

    public static final String DECO_TYPE_DORMITORY = "dormitory";
    public static final String DECO_TYPE_INDIVIDUAL = "individual";
    public static final String DECO_TYPE_ALL = "ALL";

    private static final String TAG = "EventDecorator";

    // 점의 색상값들 및 표기할 날짜들.
    private final int[] colors = {Color.BLUE, Color.RED};
    private final HashSet<CalendarDay> dates;

    private final String type;
    private final int radius;

    // 생성자 HashSet 이기에 이벤트가 포함됨과 아님을 구별할뿐 이벤트의 개수만큼 점을 생성하지 않음.
    public EventDecorator(String type, Collection<CalendarDay> dates, int radius) {
        this.dates = new HashSet<>(dates);
        this.type = type;
        this.radius = radius;
        Log.d(TAG, "shouldDecorate: "+dates.toString());
    }

    // 데코 해야하는 날짜인지 캘린더 뷰와 비교
    @Override
    public boolean shouldDecorate(CalendarDay day) {

        return dates.contains(day);
    }

    // 데코하기.
    @Override
    public void decorate(DayViewFacade view) {
        if(type.equals(DECO_TYPE_DORMITORY))
            view.addSpan((new CustomMultipleDotSpan(radius, colors[1])));

        if(type.equals(DECO_TYPE_INDIVIDUAL))
            view.addSpan((new CustomMultipleDotSpan(radius, colors[0])));

        if(type.equals(DECO_TYPE_ALL))
            view.addSpan((new CustomMultipleDotSpan(radius, colors)));


    }

}
