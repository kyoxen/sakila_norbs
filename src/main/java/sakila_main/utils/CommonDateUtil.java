package sakila_main.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author VN
 * @date 2019年12月4日
 * @description
 */
public class CommonDateUtil {

    public static ZoneId defaultZone = ZoneId.of("+08:00");

    public final static String YYYY_MM = "yyyy-MM";
    public final static String YYYY_MM_DD = "yyyy-MM-dd";
    public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 根据字符串转换LocalDate日期
     *
     * @param str 2019-11
     * @return LocalDate
     * @throws ParseException
     */
    public static LocalDate parseLocalDate(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = sdf.parse(str);
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDate localDate = LocalDateTime.ofInstant(instant, zone).toLocalDate();
        return localDate;
    }

    public static String nowStr() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTimeFormatter.format(now());
    }

    public static LocalDateTime defaultTime() {
        return LocalDateTime.of(1970,1,1,0,0,0);
    }

    public static LocalDateTime parseLocalDateFormat(String dateLoc, String format) throws ParseException {
//        DateTimeFormatter formatTime = DateTimeFormatter.ofPattern(format);
//        LocalDateTime localDate = LocalDateTime.parse(dateLoc, formatTime);

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = sdf.parse(dateLoc);
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDate = LocalDateTime.ofInstant(instant, zone);
        return localDate;
    }

    public static LocalDateTime now() {
        return LocalDateTime.now(defaultZone);
    }

    /**
     * DATE转换LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime parseLocalDateTimeByDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime;
    }

    /**
     * 日期转时间戳(秒)
     *
     * @param dateStr
     * @param formats
     * @return Long
     */
    public static Long dateToSecondTime(String dateStr, String formats) {
        return (new SimpleDateFormat(formats)).parse(dateStr, new ParsePosition(0)).getTime() / 1000;
    }

    /**
     * 时间戳(秒)转日期
     *
     * @param secondTime
     * @param formats
     * @return String
     */
    public static String secondTimeToDate(Long secondTime, String formats) {
        return new SimpleDateFormat(formats).format(new Date(secondTime * 1000));
    }

    /**
     * LocalDateTime转时间戳
     *
     * @param time
     * @return String
     */
    public static Long localDateTimeToTime(LocalDateTime time) {
        return time.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    /**
     * LocalDateTime转时间戳
     *
     * @return String
     */
    public static Long localDateTimeToTime() {
        return localDateTimeToTime(CommonDateUtil.now());
    }

    /**
     * LocalDateTime时间比较
     *
     * @param sTime
     * @param eTime
     * @return
     */
    public static Integer localDateTimeCompare(LocalDateTime sTime, LocalDateTime eTime) {
        Long time1 = localDateTimeToTime(sTime);
        Long time2 = localDateTimeToTime(eTime);
        Long res = time1 - time2;
        return res > 0 ? 1 : res == 0 ? 0 : -1;
    }

    /**
     * LocalDateTime转换为Date
     *
     * @param localDateTime
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZonedDateTime zdt = localDateTime.atZone(defaultZone);//Combines this date-time with a time-zone to create a  ZonedDateTime.
        return Date.from(zdt.toInstant());
    }

    /***
     * @Description
     **/
    public static String format(String format, LocalDateTime time) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return dateTimeFormatter.format(time);
    }

    /***
     * @Description
     **/
    public static String format(String format, LocalDate time) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return dateTimeFormatter.format(time);
    }

    /***
     * 获取两个时间之间的月份
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<String> getBetweenDateMonth(LocalDate startDate, LocalDate endDate) {
        ArrayList<String> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

        Calendar min = GregorianCalendar.from(startDate.atStartOfDay(ZoneId.systemDefault()));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        Calendar max = GregorianCalendar.from(endDate.atStartOfDay(ZoneId.systemDefault()));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        while (min.before(max)) {
            result.add(sdf.format(min.getTime()));
            min.add(Calendar.MONTH, 1);
        }

        return result;
    }

    /***
     * 判断开始时间、结束时间是否同一个月份
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean withinSameMonth(LocalDate startDate, LocalDate endDate) {
        return format(YYYY_MM, startDate).equals(format(YYYY_MM, endDate));
    }
}
