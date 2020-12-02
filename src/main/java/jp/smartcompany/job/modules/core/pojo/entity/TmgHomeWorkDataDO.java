package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author 顧成斌
 * @description 在宅勤務登録
 * @date 2020/11/25
 **/
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tmg_homeworkdata")
public class TmgHomeWorkDataDO implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 顧客ｺｰﾄﾞ
     */
    @TableId
    private String hw_ccustomerid;

    /**
     * 法人ｺｰﾄﾞ
     */
    @TableField("hw_ccompanyid")
    private String hw_ccompanyid;

    /**
     *　職員番号
     */
    @TableField("hw_cemployeeid")
    private String hw_cemployeeid;

    /**
     *　申請日
     */
    @TableField("hw_applicationdate")
    private String hw_applicationdate;

    /**
     * 申請状態
     */
    @TableField("hw_status")
    private String hw_status;

    /**
     * 在宅勤務状態
     */
    @TableField("hw_homework")
    private String hw_homework;

    /**
     * 開始時間
     */
    @TableField("hw_start")
    private String hw_start;

    /**
     * 終了時間
     */
    @TableField("hw_end")
    private String hw_end;

    /**
     * 通勤FLG
     */
    @TableField("hw_commute")
    private String hw_commute;

    /**
     * 申請コメント
     */
    @TableField("hw_applicationcomment")
    private String hw_applicationcomment;

    /**
     * 承認コメント
     */
    @TableField("hw_approvalcomment")
    private String hw_approvalcomment;

    /**
     * 承認者番号
     */
    @TableField("hw_approvalempid")
    private String hw_approvalempid;

    /**
     * 承認日
     */
    @TableField("hw_approvaledate")
    private String hw_approvaledate;

}