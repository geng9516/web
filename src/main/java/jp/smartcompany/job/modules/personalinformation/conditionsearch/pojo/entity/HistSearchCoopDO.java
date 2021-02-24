package jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * コンテンツ横断自由条件検索連携テーブル
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("HIST_SEARCH_COOP")
@KeySequence("HIST_SEARCH_COOP_SEQ")
public class HistSearchCoopDO implements Serializable {

    /**
     * IDカラム
     */
    @TableId
    private Long hscId;
    /**
     * データID
     */
    private Long hscNdataId;
    /**
     * 顧客コード
     */
    private String hscCcustomerid;
    /**
     * ユーザID
     */
    private String hscCuserid;
    /**
     * データ名称
     */
    private String hscCdataname;
    /**
     * 共有有無
     */
    private String hscCpublic;
    /**
     * 備考
     */
    private String hscCcomment;
    /**
     * 最終更新者
     */
    private String hscCmodifieruserid;
    /**
     * 最終更新日
     */
    private Date hscDmodifieddate;
    /**
     * バージョンNo
     */
    private Long versionno;

}
