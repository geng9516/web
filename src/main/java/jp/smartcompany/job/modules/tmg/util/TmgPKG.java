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



}
