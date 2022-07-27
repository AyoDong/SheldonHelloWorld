package com.sheldon.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author fangxiaodong
 * @date 2021/08/17
 */
public class FirstAndLastDayOfMonth {

    public static void main(String[] args) {
        int monthValue = LocalDate.now().getMonthValue();
        Date firstDayOfMonth = getFirstDayOfMonth(monthValue);
        Date lastDayOfMonth = getLastPlusOneDayOfMonth();
        System.out.println(firstDayOfMonth);
        System.out.println(lastDayOfMonth);

        System.out.println(LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")));
        System.out.println(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    private static Date getLastPlusOneDayOfMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTime();
    }

    private static Date getFirstDayOfMonth(int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTime();
    }

}
