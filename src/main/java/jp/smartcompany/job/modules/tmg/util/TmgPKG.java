package jp.smartcompany.job.modules.tmg.util;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * @author Nie Wanqun
 */
public class TmgPKG {

    /** 名称マスタコード　平日 */
    public final static String CS_HOLFLG_0 = "TMG_HOLFLG|0";

    /** 名称マスタコード　休日 (祝日・振替休日) */
    public final static String CS_HOLFLG_2 = "TMG_HOLFLG|2";

    /** データ開始日 */
    public final static Date CD_MINDATE = DateUtil.parseDate("1900/01/01");

    /** データ終了日 */
    public final static Date CD_MAXDATE = DateUtil.parseDate("1900/01/01");

    /** 名称マスタコード　未入力 */
    public final static String CS_DATASTATUS_0 = "TMG_DATASTATUS|0";

    /** 名称マスタコード　なし */
    public final static String CS_BUSINESS_TRIP_00 = "TMG_BUSINESS_TRIP|00";

    /** 名称マスタコード　出勤 */
    public final static String CS_WORK_000 = "TMG_WORK|000";

    /** 予備数字1 */
    public final static int CN_SPARENUM_1 = 1;

    /** 予備数字2 */
    public final static int CN_SPARENUM_2 = 2;

    /** 予備数字3 */
    public final static int CN_SPARENUM_3 = 3;

    /** 予備数字4 */
    public final static int CN_SPARENUM_4 = 4;

    /** 予備数字5 */
    public final static int CN_SPARENUM_5 = 5;

    /** 日単位に取得 */
    public final static String CS_DATETYPEFLG_0 ="TMG_DATETYPEFLG|0";

    /** 月単位に取得*/
    public final static String CS_DATETYPEFLG_1 ="TMG_DATETYPEFLG|1";

}
