package com.yaohy.intelligentfarmingbackend.util;

import javax.annotation.Resource;
import javax.swing.text.DateFormatter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Valentine
 */
public class TimeUtil {
    public static String getNowTime() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.format(formatter1);
    }

    public static Date getDate() {
        long l = System.currentTimeMillis();
        return new Date(l);
    }

    public static String getTimeByDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(date);
    }
}
