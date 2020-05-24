package jp.smartcompany.job.modules.tmg.tmgresults.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 日別詳細情報（超過勤務）VO
 *
 * @author Nie Wanqun
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class DetailOverhoursVO {
    /**
     * URL
     */
    private String attributeUrl;
    /**
     * 非勤務区分
     */
    private String tdadCnotworkid;
    /**
     * 開始時刻
     */
    private String tdadNopen;
    /**
     * 終了時刻
     */
    private String tdadNclose;
    /**
     * 削除フラグ
     */
    private String tdadNdeleteflg;
    /**
     * SEQ
     */
    private String tdadSeq;
    /**
     * 予備文字列1
     */
    private String tdadCsparechar1;
    /**
     * 予備数値1
     */
    private String tdadNsparenum1;
    /**
     * 予備コード1
     */
    private String tdadCcode01;
    /**
     * 予備コード1名
     */
    private String tdadCcode01Name;
    /**
     * マスタ名称
     */
    private String itemName;

    /**
     * 予備文字列2(申請ステータス)
     */
    private String tdadCsparechar2;
    /**
     * 申請ステータス名称
     */
    private String tdadCsparechar2Name;
}
