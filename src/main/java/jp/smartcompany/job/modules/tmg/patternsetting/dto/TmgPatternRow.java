package jp.smartcompany.job.modules.tmg.patternsetting.dto;


import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgTimeRange;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author Wang Ziyue
 */
@Getter
@Setter
@ToString
public class TmgPatternRow {

     /**顧客コード*/
     private String  cCustomerId;
     /**法人コード*/
     private String  cCompanyId;
     /**部局コード*/
     private String  cSectionId;
     /**部局名称*/
     private String  cSectionName;
     /**部局略称*/
     private String  cSectionNick;
     /**グループコード*/
     private String  cGroupId;
     /**グループ名称*/
     private String  cGroupName;
     /**データ開始日*/
     private Date dStartDate;
     /**データ終了日*/
     private Date  dEndDate;
     /**更新者*/
     private String  cModifierUserId;
     /**更新日*/
     private Date  dModifiedDate;
     /**更新プログラム*/
     private String  cModifierProgramId;
     /**勤務パターンid*/
     private String  cPatternId;
     /**勤務パターン名称*/
     private String  cPatternName;
     /**デフォルトフラグ*/
     private String  cDefaultFlg;

     private List<TmgTimeRange> timeRange;

     /**所定労働時間(TIMERANGEの示す勤務時間)*/
     private int  nTime;
     /**判定タイプ*/
     private String  cType;
     /**seq*/
     private int  nSeq;
     /**2暦日勤務フラグ*/
     private String  c2CalDays;
     /**翌日の勤務パターン*/
     private String  cNextPtn;
     /**日付変更時刻*/
     private int  nDateChangeTime;
     /**休日フラグ*/
     private String  cHolFlg;

}
