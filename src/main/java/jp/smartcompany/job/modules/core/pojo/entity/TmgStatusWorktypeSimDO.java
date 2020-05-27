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
 * 段階導入シュミレーション登録
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
@TableName("tmg_status_worktype_sim")
public class TmgStatusWorktypeSimDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ
         */
    @TableId(value="tsws_ccustomerid",type = IdType.INPUT)
        private String tswsCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tsws_ccompanyid")
        private String tswsCcompanyid;

        /**
         * データ開始日
         */
    @TableField("tsws_dstartdate")
        private Date tswsDstartdate;

        /**
         * データ終了日
         */
    @TableField("tsws_denddate")
        private Date tswsDenddate;

        /**
         * 更新者
         */
    @TableField("tsws_cmodifieruserid")
        private String tswsCmodifieruserid;

        /**
         * 更新日時
         */
    @TableField("tsws_dmodifieddate")
        private Date tswsDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tsws_cmodifierprogramid")
        private String tswsCmodifierprogramid;

        /**
         * ｽﾃｰﾀｽｺｰﾄﾞ
         */
    @TableField("tsws_cstatus")
        private String tswsCstatus;

        /**
         * ジョブid
         */
    @TableField("tsws_njobid")
        private Long tswsNjobid;

        /**
         * 登録日時
         */
    @TableField("tsws_dcreatedate")
        private Date tswsDcreatedate;

        /**
         * 登録ユーザーid
         */
    @TableField("tsws_ccreateuserid")
        private String tswsCcreateuserid;

        /**
         * 取消日時
         */
    @TableField("tsws_dcanceldate")
        private Date tswsDcanceldate;

        /**
         * 取消ユーザーid
         */
    @TableField("tsws_ccanceluserid")
        private String tswsCcanceluserid;


        }