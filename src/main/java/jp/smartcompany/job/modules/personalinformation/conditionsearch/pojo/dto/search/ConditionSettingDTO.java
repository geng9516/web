package jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import jp.smartcompany.framework.component.dto.QueryConditionRowDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class ConditionSettingDTO {

    // ページング条件
    /** 発生イベント **/
    private String pagingEvent;
    /**
     * ページング条件
     */
    private PagerLinkDTO pagerLinkDTO;

    /**
     * 出力モード： SCREEN 画面 CSV CSV出力 TABLE テーブル
     */
    private String mode;
    /**
     * 法人コード
     */
    private String companyId = "01";
    /**
     * 基準日関連
     */
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date standardDate;
    /**
      マスタ参照項目を表示するときはコードも併せて表示する
     */
    private Boolean showMastCode;
    /**
     * 検索結果データ読込
     */
    private Boolean useCooperation;
    /**
     * 基準日モード
     * 1: 指定日付時点のデータを検索する（通常はこちらを使用してください）
     * 0: 履歴も全て検索する
     */
    private Integer standardDateType;
    /**
     * 表示項目条件
     */
    private List<ConditionSelectDTO> selectDtoList;
    /**
     * 条件項目
     */
    private List<ConditionWhereDTO> whereDtoList;
    private List<QueryConditionRowDTO> queryConditionDtoList;
    /**
     * ソート項目
     */
    private List<ConditionOrderDTO> orderDtoList;
    /**
     * 共有範囲
     */
    private List<ConditionSettingTargetDTO> targetDtoList;
    /**
     * コンテンツ横断自由条件検索連携テーブル
     */
    private CooperationDTO coopDTO;
    /**
     * true=利用条件式检索 false=利用条件项目检索
     */
    private Boolean useQueryDefinition;

/**
 * ====================================
 *  以下为共有修改时需要的参数
 *  ====================================
  */

    /**
     * 連携データ名称
     */
    private String dataName;
    /**
     * IDカラム
     */
    private Long hseId;
    /**
     * 設定ID
     */
    private Long hseNsettingid;
    /**
     * 顧客コード
     */
    private String hseCcustomerid;
    /**
     * ユーザID
     */
    private String hseCuserid;
    /**
     * 設定名称
     */
    private String hseCsettingname;
    /**
     * 法人コード
     */
    private String hseCcompanyidCk;
    /**
     * 法人選択区分
     * 1: 全法人共通
     */
    private String hseCcompanyselect;
    /**
     * 共有有無
     */
    private String hseCpublic;
    /**
     * マスタコード表示フラグ
     */
    private String hseCmastercodeflg;
    /**
     * 備考
     */
    private Long hseCcomment;
    /**
     *  連携データID
     */
    private Long hseNdataId;
    /**
     *  連携使用有無
     */
    private String hseCusecooperation;
    /**
     *  基準日設定使用有無
     */
    private String hseCusedate;
    /** 上書フラグ */
    private Boolean overwrite;
}
