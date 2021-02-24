package priv.zhou.common.tools;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zhou
 * @since 2019.03.11
 */
@SuppressWarnings("unused")
public class DateUtil {

    private DateUtil() {
    }

    /**
     * 时间格式
     */
    public final static String Y = "yyyy";

    public final static String YM = "yyyy-MM";

    public final static String YMD = "yyyy-MM-dd";

    public final static String HMS = "HH:mm:ss";

    public final static String YMDHMS = "yyyy-MM-dd HH:mm:ss";

    public final static String TIME_ZONE = "GMT+8";


    /**
     * 默认格式
     */
    private final static String DEFAULT_FORMAT = YMD;

    /**
     * 默认单位
     */
    private final static TimeUnit DEFAULT_UNIT = TimeUnit.SECONDS;


    public static String format() {
        return format(new Date());
    }

    public static String format(String format) {
        return format(new Date(), format);
    }

    /**
     * 格式化时间成字符串（年月日）
     */
    public static String format(Date date) {
        return format(date, DEFAULT_FORMAT);
    }


    /**
     * 格式化时间Long值成字符串（年月日）
     */
    public static String format(long date) {
        return format(new Date(date));
    }


    /**
     * 格式化时间Long值成字符串
     */
    public static String format(long date, String format) {
        return format(new Date(date), format);
    }


    /**
     * 格式化时间成字符串
     */
    public static String format(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }


    /**
     * 解析时间字符串成时间（年月日）
     */
    public static Date parse(String dateStr) {
        return parse(dateStr, DEFAULT_FORMAT);
    }


    /**
     * 解析时间字符串成时间
     */
    public static Date parse(String dateStr, String format) {
        try {
            return new SimpleDateFormat(format).parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException("DateUtil.getDateTime(dateStr = " + dateStr + ", format = " + format + ")");
        }
    }


    /**
     * 两个时间是否为同一天
     */
    public static Time getTime(Date date) {
        return Time.valueOf(format(date, HMS));
    }


    public static boolean inToday(Date date) {
        return isSameDay(date, new Date());
    }

    /**
     * 两个时间是否为同一天
     */
    public static boolean isSameDay(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return false;
        }
        final Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);
        final Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);

        return c1.get(Calendar.ERA) == c2.get(Calendar.ERA) &&
                c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
                c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 获取两个时间内相差的秒数
     */
    public static int differ(Date beginTime, Date endTime) {
        return differ(beginTime, endTime, DEFAULT_UNIT);
    }


    /**
     * 获取两个时间内相差的TimeUnit数
     * 结束时间减去开始时间
     */
    public static int differ(Date beginTime, Date endTime, TimeUnit timeUnit) {
        return differ(beginTime, endTime, timeUnit.toMillis(1));
    }


    private static int differ(Date beginTime, Date endTime, Long millis) {
        if (beginTime == null || endTime == null) {
            return 0;
        }
        return (int) ((endTime.getTime() - beginTime.getTime()) / millis);
    }


    /**
     * 剩余今天剩余的毫秒数
     */
    public static Integer restOfToday() {
        Date now = new Date();
        return differ(now, parse(format(now) + " 23:59:59", YMDHMS), DEFAULT_UNIT);
    }


    /**
     * 增加天数
     */
    public static Date add(Date date, int dayNum) {
        return add(date, Calendar.DAY_OF_MONTH, dayNum);
    }


    /**
     * 增加指定字段的时间
     */
    public static Date add(Date date, int calendarField, int num) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(calendarField, num);
        return cal.getTime();
    }


    /**
     * 获取指定日期月份最大天数
     */
    public static int maxDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    /**
     * 获取指定月份的日历信息
     *
     * @param ymStr 月，如(2014-05)
     */
    public static List<Date> getDates(String ymStr) {
        return getRemainDates(parse(ymStr, YM));
    }

    /**
     * 获取从入参时间到月末的时间数组
     */
    public static List<Date> getRemainDates(Date date) {
        Calendar counter = Calendar.getInstance();
        counter.setTime(date);
        int day = counter.get(Calendar.DAY_OF_MONTH);
        final int maxDay = counter.getActualMaximum(Calendar.DAY_OF_MONTH);

        List<Date> dates = new ArrayList<>(maxDay - day + 1);
        for (; day <= maxDay; day++, counter.add(Calendar.DAY_OF_MONTH, 1)) {
            dates.add(counter.getTime());
        }
        return dates;
    }


    public static String formatWeek(Date date) {
        return new SimpleDateFormat("E", Locale.CHINESE).format(date);
    }


    /**
     * 时间是否在一个区间内
     */
    public static boolean inRegion(Date targetTime, Date startTime, Date endTime) {
        return targetTime.getTime() > startTime.getTime() && targetTime.getTime() < endTime.getTime();
    }
}

