package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 休暇休業一括登録
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tmg_bulk_notification_log")
public class TmgBulkNotificationLogDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 一括登録id
         */
    @TableField("tbnl_ntbnid")
        private Long tbnlNtbnid;

        /**
         * 顧客ｺｰﾄﾞ
         */
    @TableField("tbnl_ccustomerid")
        private String tbnlCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tbnl_ccompanyid")
        private String tbnlCcompanyid;

        /**
         * 社員番号
         */
    @TableField("tbnl_cemployeeid")
        private String tbnlCemployeeid;

        /**
         * 申請番号
         */
    @TableField("tbnl_cntfno")
        private String tbnlCntfno;

        /**
         * ｽﾃｰﾀｽid(反映済/取消済)
         */
    @TableField("tbnl_cstatus")
        private String tbnlCstatus;

        /**
         * ｴﾗｰｺｰﾄﾞ
         */
    @TableField("tbnl_cerrcode")
        private String tbnlCerrcode;

        /**
         * 更新日時
         */
    @TableField("tbnl_dmodifieddate")
        private Date tbnlDmodifieddate;


        }