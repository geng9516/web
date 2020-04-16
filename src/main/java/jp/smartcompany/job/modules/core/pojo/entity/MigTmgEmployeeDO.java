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
 * [初期移行]年次休暇情報個人テーブル
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
@TableName("mig_tmg_employee")
public class MigTmgEmployeeDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("mte_ccustomerid")
        private String mteCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("mte_ccompanyid")
        private String mteCcompanyid;

        /**
         * 個人番号
         */
    @TableField("mte_cemployeeid")
        private String mteCemployeeid;

        /**
         * 開始日                           1900-01-01 固定
         */
    @TableField("mte_dstartdate")
        private Date mteDstartdate;

        /**
         * 終了日                           2222-12-31 固定
         */
    @TableField("mte_denddate")
        private Date mteDenddate;

        /**
         * 更新ユーザ
         */
    @TableField("mte_cmodifieruserid")
        private String mteCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("mte_dmodifieddate")
        private Date mteDmodifieddate;

        /**
         * 更新ﾌﾟﾛｸﾞﾗﾑ
         */
    @TableField("mte_cmodifierprogramid")
        private String mteCmodifierprogramid;

        /**
         * 勤務形態
         */
    @TableField("mte_cworkertype")
        private String mteCworkertype;

        /**
         * 週勤務日数
         */
    @TableField("mte_nweekpettern")
        private Long mteNweekpettern;

        /**
         * 採用年月日
         */
    @TableField("mte_ddateofemployment")
        private Date mteDdateofemployment;

        /**
         * 繰越日数
         */
    @TableField("cph_nthroughout")
        private Long cphNthroughout;

        /**
         * 繰越時間数
         */
    @TableField("cph_nadjust")
        private Long cphNadjust;

        /**
         * 週所定労働時間
         */
    @TableField("mte_nweekhours")
        private Long mteNweekhours;


        }