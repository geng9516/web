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

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("HIST_SEARCH_SETTING_TARGET")
@KeySequence("HIST_SEARCH_SETTING_TARGET_SEQ")
public class HistSearchSettingTargetDO implements Serializable {

    /**
      * 設定ID
      */
    @TableId
    private Long hstId;
    /**
     * 設定ID
     */
    private Long hstNsettingid;
    /**
     * 対象システム
     * */
    private String hstCtargetsystem;
    /**
     * 対象グループ
     */
    private String hstCtargetgroup;
    /**
     * 最終更新者
     */
    private String hstCmodifieruserid;
    /**
     * 最終更新日
     */
    private Date hstDmodifieddate;
    /**
     * バージョンNo
     */
    @Version
    private Integer versionNo;

}
