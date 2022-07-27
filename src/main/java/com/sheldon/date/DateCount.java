package com.sheldon.date;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

/**
 * @author fangxiaodong
 * @date 2021/09/26
 */
public class DateCount {

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(format.format(calendar.getTime()));


        LocalDate now = LocalDate.parse("2021-09-01").plusDays(-1);
        int endDayInt = now.getDayOfMonth();

        int times = endDayInt / 7;
        times += endDayInt % 7 == 0 ? 0 : 1;

        while (times != 0){

            int startDayInt = endDayInt - 6;
            startDayInt = startDayInt <= 1 ? 1 : startDayInt;
            String startDay = startDayInt < 10 ? "0" + startDayInt : "" + startDayInt;
            String endDay = endDayInt < 10 ? "0" + endDayInt : "" + endDayInt;

            System.out.println("startDay: " + startDay + ", endDay: " + endDay);

            endDayInt -= 7;
            times --;
        }
    }
}
