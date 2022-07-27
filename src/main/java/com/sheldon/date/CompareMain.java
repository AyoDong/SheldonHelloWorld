package com.sheldon.date;

import java.time.LocalDateTime;

/**
 * @author fangxiaodong
 * @date 2020/12/30
 */
public class CompareMain {

    public static void main(String[] args) {
//        LocalDate date = LocalDate.now();
//
//        LocalDate date1 = LocalDate.now().plusDays(1);
//        LocalDate date2 = LocalDate.now().minusDays(1);
//
//        System.out.println(date.compareTo(date1));
//        System.out.println(date.compareTo(date2));

        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        System.out.println(now.minusDays(7L));
        System.out.println(now.minusDays(30L));
        System.out.println(now.minusMonths(6L));


    }
}
