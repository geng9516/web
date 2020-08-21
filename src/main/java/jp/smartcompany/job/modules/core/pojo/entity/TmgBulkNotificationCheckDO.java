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
@TableName("tmg_bulk_notification_check")
public class TmgBulkNotificationCheckDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * id
         */
                @TableId("tbn_ntbnid")
                private Long tbnNtbnid;

        /**
         * 顧客ｺｰﾄﾞ
         */
    @TableField("tbn_ccustomerid")
        private String tbnCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tbn_ccompanyid")
        private String tbnCcompanyid;

        /**
         * 対象組織ｺｰﾄﾞ
         */
    @TableField("tbn_csectionid")
        private String tbnCsectionid;

        /**
         * データ開始日
         */
    @TableField("tbn_dstartdate")
        private Date tbnDstartdate;

        /**
         * データ終了日
         */
    @TableField("tbn_denddate")
        private Date tbnDenddate;

        /**
         * 更新者
         */
    @TableField("tbn_cmodifieruserid")
        private String tbnCmodifieruserid;

        /**
         * 更新日時
         */
    @TableField("tbn_dmodifieddate")
        private Date tbnDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tbn_cmodifierprogramid")
        private String tbnCmodifierprogramid;

        /**
         * 一括登録種類
         */
    @TableField("tbn_cbulkntftype")
        private String tbnCbulkntftype;

        /**
         * 対象期間：開始日
         */
    @TableField("tbn_dbegin")
        private Date tbnDbegin;

        /**
         * 対象期間：終了日
         */
    @TableField("tbn_dend")
        private Date tbnDend;

        /**
         * ｽﾃｰﾀｽｺｰﾄﾞ
         */
    @TableField("tbn_cstatus")
        private String tbnCstatus;

        /**
         * ジョブid
         */
    @TableField("tbn_njobid")
        private Long tbnNjobid;

        /**
         * 一括登録日時
         */
    @TableField("tbn_dcreatedate")
        private Date tbnDcreatedate;

        /**
         * 一括登録ユーザーid
         */
    @TableField("tbn_ccreateuserid")
        private String tbnCcreateuserid;

        /**
         * 一括取消日時
         */
    @TableField("tbn_dcanceldate")
        private Date tbnDcanceldate;

        /**
         * 一括取消ユーザーid
         */
    @TableField("tbn_ccanceluserid")
        private String tbnCcanceluserid;

        /**
         * 対象人数
         */
    @TableField("tbn_ncount")
        private Long tbnNcount;


        }