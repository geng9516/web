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
 * 年休取得情報
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
@TableName("tmg_paiduseinfo")
public class TmgPaiduseinfoDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客コード
         */
                @TableId(value = "tp_ccustomerid", type = IdType.AUTO)
                private String tpCcustomerid;

        /**
         * 法人コード
         */
    @TableField("tp_ccompanyid")
        private String tpCcompanyid;

        /**
         * データ開始日 (1900/01/01)
         */
    @TableField("tp_dstartdate")
        private Date tpDstartdate;

        /**
         * データ終了日 (2222/12/31)
         */
    @TableField("tp_denddate")
        private Date tpDenddate;

        /**
         * 更新者
         */
    @TableField("tp_cmodifieruserid")
        private String tpCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tp_dmodifierdate")
        private Date tpDmodifierdate;

        /**
         * 更新プログラムｉｄ
         */
    @TableField("tp_cmodifierprogramid")
        private String tpCmodifierprogramid;

        /**
         * 職員番号
         */
    @TableField("tp_cemployeeid")
        private String tpCemployeeid;

        /**
         * ユーザid
         */
    @TableField("tp_cuserid")
        private String tpCuserid;

        /**
         * 基準日期間（開始日）
         */
    @TableField("tp_dbasedate_start")
        private Date tpDbasedateStart;

        /**
         * 年休調査期間（開始日）
         */
    @TableField("tp_dntf_survey_start")
        private Date tpDntfSurveyStart;

        /**
         * 年休調査期間（終了日）
         */
    @TableField("tp_dntf_survey_end")
        private Date tpDntfSurveyEnd;

        /**
         * 年休取得日数
         */
    @TableField("tp_nnecessary_days")
        private Long tpNnecessaryDays;


        }