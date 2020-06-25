package jp.smartcompany.job.modules.tmg.schedule.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description 日別情報より予定データ
 * @objectSource
 * @date 2020/05/26
 **/
@Getter
@Setter
@ToString
public class ScheduleDataDTO {
    /**
     * 該当日
     */
    private String tda_nmmdd;
    /**
     * 　曜日
     */
    private String tda_nmmday;
    /**
     * 区分
     */
    private String tda_cworkingid_mm;

    /**
     * 始業
     */
    private String tda_nopen_p;

    /**
     * 終業
     */
    private String tda_nclose_p;

    /**
     * 休憩開始時刻
     */
    private String tda_nrestopen_p;

    /**
     * 休憩終了時刻
     */
    private String tda_nrestclose_p;

    /**
     * 部分休業メッセージ
     */
    private String tda_cmessage;

    /**
     * [予定]予定作成ロック
     */
    private Integer tda_nlock_p;

    /**
     * 　[予定]就業区分MGD:TMG_WORK
     */
    private String tda_cworkingid_p;

    /**
     * 　顧客ｺｰﾄﾞ固定：01
     */
    private String tda_ccustomerid;

    /**
     * 　ステータスフラグMGD:TMG_DATASTATUS
     */
    private String tda_cstatusflg;

    /**
     * 　[予定]出張区分
     */
    private String tda_cbusinesstripid_p;

    /**
     *
     */
    private String tda_cbusinesstripid_mm;

    /**
     * [予定]コメント
     */
    private String tda_ccomment_p;

    /**
     *
     */
    private String tda_dmmdd;

    /**
     * 　平均勤務時間
     */
    private String avgworktime;

    /**
     * 　 出勤日区分(出勤簿表示)
     */
    private String mgd_csparechar4;

    /**
     * 　タイプのオブジェクトをArrayに変換する
     */
    private Object[] timerange_arr;

    /**
     * 　タイプのオブジェクトをJSONに変換する
     */
    private String timerange;

    /**
     * 　宿日直名称
     */
    private String duty;

    /**
     * 　勤怠種別
     */
    private String astem_cworktypeid;

    /**
     * 　EタイプのオブジェクトをArrayに変換する
     */
    private Object[] json_arr;

    /**
     * 　EタイプのオブジェクトをJSONに変換する
     */
    private String json;

    /**
     * 　休日フラグ
     */
    private String holflgCalendar;

}
