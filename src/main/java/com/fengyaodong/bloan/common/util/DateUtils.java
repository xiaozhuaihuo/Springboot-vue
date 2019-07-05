package com.fengyaodong.bloan.common.util;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/8 13:59
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/8 13:59
 */
@Slf4j
public class DateUtils {
    /**
     * 日期格式:yyyy-MM-dd
     */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 日期格式:yyyyMMdd
     */
    public static final String YYYYMMDD = "yyyyMMdd";

    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = { YYYY_MM_DD, YYYY_MM_DD_HH_MM_SS, "yyyy-MM-dd HH:mm", "yyyy/MM/dd",
            "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" };

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     *
     * @param pattern
     * @return
     */
    public static String getDate(String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date());
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;

        if (pattern != null && pattern.length > 0) {
            DateFormat format = new SimpleDateFormat(pattern[0].toString());
            formatDate = format.format(date);
        } else {
            DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);
            formatDate = dateFormat.format(date);
        }
        return formatDate;
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDateStr(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
        String formatDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            formatDate = dateFormat.format(sdf.parse(date));
        } catch (Exception e) {
            log.error("DateUtils 日期转换异常...", e);
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     *
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     *
     * @return
     */
    public static String getDateTime() {
        return formatDate(new Date(), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * @param date
     * @return
     */
    public static Date getDateStart(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        try {
            date = sdf.parse(formatDate(date, YYYY_MM_DD) + " 00:00:00");
        } catch (ParseException e) {
            log.error("DateUtils 日期转换异常...", e);
        }
        return date;
    }

    /**
     * @param date
     * @return
     */
    public static Date getDateEnd(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        try {
            date = sdf.parse(formatDate(date, YYYY_MM_DD) + " 23:59:59");
        } catch (ParseException e) {
            log.error("DateUtils 日期转换异常...", e);
        }
        return date;
    }

    /**
     * 格式化时间
     *
     * @param timestamp
     * @return
     */
    public static String dateFormat(Date timestamp) {
        SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return format.format(timestamp);
    }

    /**
     * 得到当前时间的日期  YYYYMMDD
     *
     * @return
     */
    public static String getCurDT() {
        return getCurTime(YYYYMMDD);
    }

    /**
     * 当前时间
     *
     * @param format
     * @return
     */
    public static String getCurTime(String format) {
        StringBuilder str = new StringBuilder();
        Date ca = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        str.append(sdf.format(ca));
        return str.toString();
    }

    /**
     * 拼接日期时间字符串（BMM表字段）
     *
     * @param dateString 日期字符串
     * @param timeString 时间字符串
     * @return
     */
    public static String formatDateTimeString(String dateString, String timeString) {
        StringBuilder str = new StringBuilder();
        str.append(dateString).append(timeString);
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = dateFormat.parse(str.toString());
            return formatDateTime(date);
        } catch (Exception e) {
            log.error("日期格式化出错", e);
        }
        return "";
    }

    /**
     * 拼接日期时间字符串（BMM表字段）
     *
     * @param dateString 日期字符串
     * @param timeString 时间字符串
     * @return
     */
    public static Date parseDateTimeString(String dateString, String timeString) {
        StringBuilder str = new StringBuilder();
        str.append(dateString).append(timeString);
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = dateFormat.parse(str.toString());
            return date;
        } catch (Exception e) {
            log.error("日期格式化出错", e);
        }
        return null;
    }

    /**
     * 获取指定时间的差值
     *
     * @param calendarField
     * @param value
     * @return
     */
    private static Date addCalendarField(Date date, Integer calendarField, Integer value) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(calendarField, value);
        return ca.getTime();
    }

    /**
     * 获取到当前日期的时间差（天数）
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static long betweenDays(Date beginDate, Date endDate) {
        long time = beginDate.getTime() - endDate.getTime();
        return time / (24 * 60 * 60 * 1000);
    }

    /**
     * @param specifiedDay
     * @return
     * @Description: 获取指定日期的后一天到当天的天数差
     */
    public static long getDayAfter(String specifiedDay) {
        String dayAfter = getDayAfterOne(specifiedDay);
        long time = 0;
        if (StringUtil.isNotEmpty(dayAfter)) {
            time = System.currentTimeMillis() - parseDate(dayAfter).getTime();
        }
        return time / (24 * 60 * 60 * 1000);
    }

    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 指定时间添加差值
     *
     * @param date
     * @param value
     * @return
     */
    public static Date addDay(Date date, Integer value) {

        return addCalendarField(date, Calendar.DATE, value);
    }

    /**
     * @param specifiedDay
     * @return
     * @Description: 获取指定日期的后一天
     */
    public static String getDayAfterOne(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(YYYYMMDD).parse(specifiedDay);
        } catch (ParseException e) {
            log.error("DateUtils 日期转换异常...", e);
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);
        String dayAfter = new SimpleDateFormat(YYYY_MM_DD).format(c.getTime());
        return dayAfter;

    }
}
