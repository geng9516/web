package jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 自由条件検索設定
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("HIST_SEARCH_SETTING")
@KeySequence("HIST_SEARCH_SETTING_SEQ")
public class HistSearchSettingDO {

    /**
     * IDカラム
      */
    @TableId
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
    private String hseCcomment;
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
    /**
     *  最終更新者
     */
    private String hseCmodifieruserid;
    /**
     * 最終更新日
     */
    private Date hseDmodifieddate;
    /**
     * バージョンNo
     */
    @Version
    private Long versionno;

}
