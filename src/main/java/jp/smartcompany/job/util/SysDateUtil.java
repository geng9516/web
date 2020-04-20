package jp.smartcompany.job.util;

import cn.hutool.core.date.DateUtil;

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

}
