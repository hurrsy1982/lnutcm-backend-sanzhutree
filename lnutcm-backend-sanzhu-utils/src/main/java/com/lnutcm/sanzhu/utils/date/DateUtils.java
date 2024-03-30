package com.lnutcm.sanzhu.utils.date;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;


/**
 * 项目名称：springboot 后端脚手架<br>
 * 程序名称：lutcm-backend-sanzhu-utils<br>
 * 日期：2024-03-23<br>
 * 作者：胡蓉蓉<br>
 * 模块：日期函数模块<br>
 * 描述：1.编写目的:抽离业务逻辑中关于时间处理的部分，避免业务逻辑代码被污染<br>
 *      2.函数功能:格式化指定日期为特定格式的函数<br>
 *
 * 备注：禁止商用<br>
 * ------------------------------------------------------------<br>
 * 修改历史
 * 序号               日期              修改人       修改原因
 *
 * 修改备注：
 * @version
*/

public class DateUtils {
// 根据指定格式显示日期和时间
    /** yyyy */
    private static final DateTimeFormatter YYYY_EN = DateTimeFormatter.ofPattern("yyyy");
    /** yyyy-MM */
    private static final DateTimeFormatter YYYY_MM_EN = DateTimeFormatter.ofPattern("yyyy-MM");
    /** yyyy-MM-dd */
    private static final DateTimeFormatter YYYY_MM_DD_EN = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /** yyyy-MM-dd HH */
    private static final DateTimeFormatter YYYY_MM_DD_HH_EN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
    /** yyyy-MM-dd HH:mm */
    private static final DateTimeFormatter YYYY_MM_DD_HH_MI_EN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    /** yyyy-MM-dd HH:mm:ss */
    private static final DateTimeFormatter YYYY_MM_DD_HH_MI_SS_EN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    private static final DateTimeFormatter YYYYMMDDHHMISSEN = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private static final DateTimeFormatter YYYYMMDDHHMIEN = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final DateTimeFormatter YYYYMMDDEN = DateTimeFormatter.ofPattern("yyyyMMdd");
    /**
     * Function name:getStringDate
     * @Param date(Date)
     * Inside the function:
     * 1.获取参数指定时间
     * 2.把时间转化为YYYY_MM_DD_HH_MI_SS_EN 格式字符串格式返回
     */

    public static String getStringDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        String dateStr= localDateTime.format(YYYY_MM_DD_HH_MI_SS_EN);
        return dateStr;
    }

    /**
     * Function name:getStringDateTimeStamp
     * @Param date(Date)
     * Inside the function:
     * 1.获取参数指定时间
     * 2.把时间转化为YYYYMMDDHHMISSEN 格式字符串格式返回
     */

    public static String getStringDateTimeStamp
            (Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        String dateText = localDateTime.format(YYYYMMDDHHMISSEN);
        return dateText;
    }

    /**
     * Function name:getStringDateShort
     * @Param date(Date)
     * Inside the function:
     * 1.获取参数指定时间
     * 2.把时间转化为YYYYMMDDHHMISSEN 格式字符串格式返回
     */
    public static String getStringDateShort(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        String dateText = localDateTime.format(YYYY_MM_DD_EN);
        return dateText;
    }

    /**
     * Function name:getStringDateShortTimeStamp
     * @Param date(Date)
     * Inside the function:
     * 1.获取参数指定时间
     * 2.把时间转化为YYYYMMDDEN 格式字符串格式返回
     */

    public static String getStringDateShortTimeStamp(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        String dateText = localDateTime.format(YYYYMMDDEN);
        return dateText;
    }

    /**
     * Function name:getStringMonthShort
     * @Param date(Date)
     * Inside the function:
     * 1.获取参数指定时间
     * 2.把时间转化为  YYYY_MM_EN 格式字符串格式返回
     */

    public static String getStringMonthShort(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        String dateText = localDateTime.format(YYYY_MM_EN);
        return dateText;
    }

    public static String getStringYearShort(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        String dateText = localDateTime.format(YYYY_EN);
        return dateText;
    }

    public static String getStringCurrentDate() {
        Date currentTime = new Date();
        Instant instant = currentTime.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        String dateText = localDateTime.format(YYYY_MM_DD_HH_MI_SS_EN);
        return dateText;
    }

    public static String getStringCurrentDateShort() {
        Date currentTime = new Date();
        Instant instant = currentTime.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        String dateText = localDateTime.format(YYYY_MM_DD_EN);
        return dateText;
    }


    public static String getStringNowMonth() {
        Date currentTime = new Date();
        Instant instant = currentTime.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        String dateText = localDateTime.format(YYYY_MM_EN);
        return dateText;
    }

    public static String getStringNowYear() {
        Date currentTime = new Date();
        Instant instant = currentTime.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        String dateText = localDateTime.format(YYYY_EN);
        return dateText;
    }

    public static String getStringNowPreMonth(int delay) {
        Date currentTime = new Date();
        Instant instant = currentTime.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDateTime localPrevDateTime = localDateTime.minusMonths(delay);
        String dateText = localPrevDateTime.format(YYYY_MM_EN);
        return dateText;
    }

    public static String getStringNowPreMonthDate(int delay) {
        Date currentTime = new Date();
        Instant instant = currentTime.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDateTime localPrevDateTime = localDateTime.minusMonths(delay);
        String dateText = localPrevDateTime.format(YYYY_MM_DD_EN);
        return dateText;
    }

    public static String getStringNowPreWeekDate(int delay) {
        Date currentTime = new Date();
        Instant instant = currentTime.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDateTime localPrevDateTime = localDateTime.minusWeeks(delay);
        String dateText = localPrevDateTime.format(YYYY_MM_DD_EN);
        return dateText;
    }

    public static String getStringNowPreYear(int delay) {
        Date currentTime = new Date();
        Instant instant = currentTime.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDateTime localPrevDateTime = localDateTime.minusYears(delay);
        String dateText = localPrevDateTime.format(YYYY_EN);
        return dateText;
    }

    public static String getStringNowPreDate(int delay) {
        Date currentTime = new Date();
        Instant instant = currentTime.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDateTime localPrevDateTime = localDateTime.minusDays(delay);
        String dateText = localPrevDateTime.format(YYYY_MM_DD_EN);
        return dateText;
    }

    public static Date getNowPreDate(int delay) {
        Date currentTime = new Date();
        Instant instant = currentTime.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDateTime localPrevDateTime = localDateTime.minusDays(delay);
        Instant instantNext = localPrevDateTime.atZone(zone).toInstant();
        java.util.Date resultDate = Date.from(instantNext);
        return resultDate;
    }

    public static Date getNowDate() {
        Date currentTime = new Date();
        Instant instant = currentTime.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        Instant instanNow = localDateTime.atZone(zone).toInstant();
        java.util.Date resultDate = Date.from(instanNow);

        return resultDate;
    }


    public static Date getDateNextDate(Date date, int delay) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDateTime localNextDateTime = localDateTime.plusDays(delay);
        Instant instantNext = localNextDateTime.atZone(zone).toInstant();
        java.util.Date resultDate = Date.from(instantNext);
        return resultDate;
    }

    public static Date getDateNextMonth(Date date, int delay) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDateTime localNextDateTime = localDateTime.plusMonths(delay);
        Instant instantNext = localNextDateTime.atZone(zone).toInstant();
        java.util.Date resultDate = Date.from(instantNext);
        return resultDate;
    }

    public static Date getDateNextYear(Date date, int delay) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDateTime localNextDateTime = localDateTime.plusYears(delay);
        Instant instantNext = localNextDateTime.atZone(zone).toInstant();
        java.util.Date resultDate = Date.from(instantNext);
        return resultDate;
    }

    public static Date strToDateShort(String strDate) {

        LocalDate localDate = LocalDate.parse(strDate, YYYY_MM_DD_EN);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    public static Date strToDate(String strDate) {

        LocalDateTime localDateTime = LocalDateTime.parse(strDate, YYYY_MM_DD_HH_MI_SS_EN);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        java.util.Date date = Date.from(instant);
        return date;
    }

    public static Date  getDatePreDateDay(Date  date,int delay) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDateTime localPrevDateTime = localDateTime.minusDays(delay);
        String   yearDateText = localPrevDateTime.format(YYYY_MM_DD_EN);
        java.util.Date resultDate = strToDateShort(yearDateText) ;
        return resultDate;
    }

    public static Date  getDateNextDateDay(Date  date,int delay) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDateTime localPrevDateTime = localDateTime.plusDays(delay);
        String   yearDateText = localPrevDateTime.format(YYYY_MM_DD_EN);
        java.util.Date resultDate = strToDateShort(yearDateText) ;
        return resultDate;
    }

    public static Date  getNowPreDateYear(int delay) {
        Date currentTime = new Date();
        Instant instant = currentTime.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDateTime localPrevDateTime = localDateTime.minusYears(delay);
        String   yearDateText = localPrevDateTime.format(YYYY_MM_DD_EN);
        java.util.Date resultDate = strToDateShort(yearDateText) ;
        return resultDate;
    }

}
