package jp.smartcompany.job.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Date;

/**
 * @author Xiao Wenpeng
 * 基于hutool扩展的日期比较方法
 */
public class SysDateUtil {

    /**
     * 判断两日期大小
     * @param date1
     * @param date2
     * @return 比较结果: date1 > date2 : true | date1 <= date2 : false
     */
    public static boolean isGreater(Date date1,
                                    Date date2) {
        return DateUtil.compare(date1,date2) > 0;
    }

    /**
     * 判断两日期大小
     * @param date1
     * @param date2
     * @return 比较结果: date1 < date2 : true | date1 >= date2 : false
     */
    public static boolean isLess(Date date1,Date date2) {
        return DateUtil.compare(date1,date2) < 0;
    }

    /**
     * 根据年月日时分秒获取一个日期对象
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Date of(int year, int month, int day, int hour, int minute, int second) {
        return new DateTime(StrUtil.format("{}-{}-{} {}:{}:{}",
                year,month,day,hour,minute,second), DatePattern.NORM_DATETIME_PATTERN);
    }

    /**
     * 根据年月日获取一个日期对象
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static Date of(int year, int month, int day) {
        return new DateTime(StrUtil.format("{}-{}-{}",
                year,month,day), DatePattern.NORM_DATE_PATTERN);
    }

}
