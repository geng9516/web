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
 * 年5日時季指定取得情報
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
@TableName("tmg_acquired5daysholiday")
public class TmgAcquired5daysholidayDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客コード
         */
                @TableId(value = "ta_ccustomerid", type = IdType.AUTO)
                private String taCcustomerid;

        /**
         * 法人コード
         */
    @TableField("ta_ccompanyid")
        private String taCcompanyid;

        /**
         * データ開始日 (1900/01/01)
         */
    @TableField("ta_dstartdate")
        private Date taDstartdate;

        /**
         * データ終了日 (2222/12/31)
         */
    @TableField("ta_denddate")
        private Date taDenddate;

        /**
         * 更新者
         */
    @TableField("ta_cmodifieruserid")
        private String taCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("ta_dmodifierdate")
        private Date taDmodifierdate;

        /**
         * 更新プログラムｉｄ
         */
    @TableField("ta_cmodifierprogramid")
        private String taCmodifierprogramid;

        /**
         * 職員番号
         */
    @TableField("ta_cemployeeid")
        private String taCemployeeid;

        /**
         * ユーザid
         */
    @TableField("ta_cuserid")
        private String taCuserid;

        /**
         * 付与日
         */
    @TableField("ta_dpaid_holiday")
        private Date taDpaidHoliday;

        /**
         * 付与日数
         */
    @TableField("ta_nholiday_days")
        private Long taNholidayDays;

        /**
         * 基準日期間（開始日）
         */
    @TableField("ta_dbasedate_start")
        private Date taDbasedateStart;

        /**
         * 基準日期間（終了日）
         */
    @TableField("ta_dbasedate_end")
        private Date taDbasedateEnd;

        /**
         * 取得必要日数
         */
    @TableField("ta_nnecessary_days")
        private Long taNnecessaryDays;

        /**
         * 重複flg
         */
    @TableField("ta_cduplicateflg")
        private String taCduplicateflg;


        }