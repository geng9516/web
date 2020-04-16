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
 * [勤怠]年休固定残日数情報
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
@TableName("tmg_fixed_paid_holiday_rest")
public class TmgFixedPaidHolidayRestDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
                @TableId(value = "tfph_ccustomerid", type = IdType.AUTO)
                private String tfphCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tfph_ccompanyid")
        private String tfphCcompanyid;

        /**
         * 社員番号
         */
    @TableField("tfph_cemployeeid")
        private String tfphCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日                      固定：1900/01/01
         */
    @TableField("tfph_dstartdate")
        private Date tfphDstartdate;

        /**
         * ﾃﾞｰﾀ終了日                      固定：2222/12/31
         */
    @TableField("tfph_denddate")
        private Date tfphDenddate;

        /**
         * 更新者
         */
    @TableField("tfph_cmodifieruserid")
        private String tfphCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tfph_dmodifieddate")
        private Date tfphDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tfph_cmodifierprogramid")
        private String tfphCmodifierprogramid;

        /**
         * 基準日                          yyyy/mm/dd
         */
    @TableField("tfph_dyyyymmdd")
        private Date tfphDyyyymmdd;

        /**
         * 残日数
         */
    @TableField("tfph_nrest_days")
        private Long tfphNrestDays;

        /**
         * 残時間数
         */
    @TableField("tfph_nrest_hours")
        private Long tfphNrestHours;

        /**
         * 備考
         */
    @TableField("tfph_cnotes")
        private String tfphCnotes;

        /**
         * 次回付与時繰越上限日数
         */
    @TableField("tfph_nnext_throughoutlmt_days")
        private Long tfphNnextThroughoutlmtDays;

        /**
         * 次回付与時繰越上限時間数
         */
    @TableField("tfph_nnext_throughoutlmt_hours")
        private Long tfphNnextThroughoutlmtHours;


        }