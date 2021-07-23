package com.example.common.utils;


import com.example.common.enumeration.StatisticsTypeEnum;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author wuhao
 * @desc ...
 * @date 2021-07-09 10:29:31
 */
@Slf4j
public class LocalDateTimeUtil {
    public static void main(String[] args) {
//        System.out.println(forEach(StatisticsTypeEnum.D,"2020-07-09","2021-07-09"));
//        System.out.println(forEach(StatisticsTypeEnum.W,"2020-07-09","2021-07-09"));
//        System.out.println(forEach(StatisticsTypeEnum.M,"2020-07-09","2021-07-09"));
        System.out.println(getBetweenWeek("2020-06-01","2021-06-01"));
    }

    //获取一段时间map
    public static Map<String,Long> forEach(StatisticsTypeEnum type, String startDate, String endDate){
        Map<String,Long> map=new LinkedHashMap<>();
        switch (type){
            case D:
                getBetweenDate(startDate,endDate).forEach(item->{
                    map.put(item,0L);
                });
                break;
            case W:
                getBetweenWeek(startDate,endDate).forEach(item->{
                    map.put(item,0L);
                });
                break;
            case M:
                getBetweenMonth(startDate,endDate).forEach(item->{
                    map.put(item,0L);
                });
                break;
        }
        return map;
    }
    //获取一段时间list
    public static List<String> getBetweenDate(String start, String end) {
        List<String> list = new ArrayList<>();
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        long distance = ChronoUnit.DAYS.between(startDate, endDate);
        if (distance < 1) {
            return list;
        }
        Stream.iterate(startDate, d -> d.plusDays(1)).limit(distance + 1).forEach(f -> list.add(f.toString()));
        return list;
    }

    public static List<String> getBetweenWeek(String start, String end) {
        List<String> list = new ArrayList<>();
        List<String> range = new ArrayList<>();
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        long distance = ChronoUnit.WEEKS.between(startDate, endDate);
        if (distance < 1) {
            return list;
        }
        // WeekFields.ISO = 一星期从周一开始算.
        WeekFields weekFields = WeekFields.ISO;
        Stream.iterate(startDate, d -> d.plusWeeks(1)).limit(distance + 1).forEach(f -> list.add(String.valueOf(f.getYear()).concat("-").concat(String.valueOf(f.get(weekFields.weekOfWeekBasedYear())))));
        TemporalField fieldISO = WeekFields.of(Locale.FRANCE).dayOfWeek();
        Stream.iterate(startDate, d -> d.plusWeeks(1)).limit(distance + 1).forEach(f -> range.add(String.valueOf(f.with(fieldISO, 1)).concat("~").concat(String.valueOf(f.with(fieldISO, 7)))));
        log.info(String.valueOf(range));
        return list;
    }

    public static List<String> getBetweenMonth(String start, String end) {
        List<String> list = new ArrayList<>();
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        long distance = ChronoUnit.MONTHS.between(startDate, endDate);
        if (distance < 1) {
            return list;
        }
        Stream.iterate(startDate, d -> d.plusMonths(1)).limit(distance + 1).forEach(f -> list.add(String.valueOf(f.getYear()).concat("-").concat(String.valueOf(f.getMonth().getValue()))));
        return list;
    }
}
