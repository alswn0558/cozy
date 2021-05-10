package com.example.teamproject.utill;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
/**
 * Created by 양민주
 */
public class DateOperation {

    // 교집합
    static public Set<CalendarDay> Intersection(Set<CalendarDay> a, Set<CalendarDay> b) {
        Set<CalendarDay> result = new HashSet<>(a);
        result.retainAll(b);
        return result;
    }
    // 차집합 A - B
    static public Set<CalendarDay> difference(Set<CalendarDay> a, Set<CalendarDay> b) {
        Set<CalendarDay> result = new HashSet<>(a);
        result.removeAll(b);
        return result;
    }
    // 합집합.
    static public Set<CalendarDay> union(Set<CalendarDay> a, Set<CalendarDay> b) {
        Set<CalendarDay> result = new HashSet<>();
        result.addAll(a);
        result.addAll(b);
        return result;
    }

    // 숫자 -> Date 객체로
    public static Date getDate(int year, int month, int date) {

        Calendar cal = Calendar.getInstance();
        cal.set(year, month-1, date-1,24,0,0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }
    // Date -> year / month / day 로
    public static int[] getIntDate(Date date){
        int year = date.getYear()+1900;
        int month = date.getMonth();
        int day = date.getDate();
        return new int[]{year, month, day};
    }

}