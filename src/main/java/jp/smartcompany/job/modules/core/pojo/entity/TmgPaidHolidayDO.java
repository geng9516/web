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
 * [勤怠]年次休暇情報
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
@TableName("tmg_paid_holiday")
public class TmgPaidHolidayDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tph_ccustomerid")
        private String tphCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tph_ccompanyid")
        private String tphCcompanyid;

        /**
         * 社員番号
         */
                @TableId
                private String tphCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日                      固定：1900/01/01
         */
    @TableField("tph_dstartdate")
        private Date tphDstartdate;

        /**
         * ﾃﾞｰﾀ終了日                      固定：2222/12/31
         */
    @TableField("tph_denddate")
        private Date tphDenddate;

        /**
         * 更新者
         */
    @TableField("tph_cmodifieruserid")
        private String tphCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tph_dmodifieddate")
        private Date tphDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tph_cmodifierprogramid")
        private String tphCmodifierprogramid;

        /**
         * 付与年月                        yyyy/mm/01
         */
    @TableField("tph_dyyyymm")
        private Date tphDyyyymm;

        /**
         * 付与年月日                      yyyy/mm/dd
         */
    @TableField("tph_dyyyymmdd")
        private Date tphDyyyymmdd;

        /**
         * 付与区分
         */
    @TableField("tph_cinvesttype")
        private String tphCinvesttype;

        /**
         * 付与日数
         */
    @TableField("tph_ninvest")
        private Long tphNinvest;

        /**
         * 繰越日数
         */
    @TableField("tph_nthroughout")
        private Long tphNthroughout;

        /**
         * 繰越時間
         */
    @TableField("tph_nthroughout_hours")
        private Long tphNthroughoutHours;

        /**
         * 調整日数(付与)
         */
    @TableField("tph_nadjust")
        private Long tphNadjust;

        /**
         * 調整時間(付与)
         */
    @TableField("tph_nadjust_hours")
        private Long tphNadjustHours;

        /**
         * 調整日数(繰越)
         */
    @TableField("tph_nadjust_to")
        private Long tphNadjustTo;

        /**
         * 調整時間(繰越)
         */
    @TableField("tph_nadjust_hours_to")
        private Long tphNadjustHoursTo;

        /**
         * 予定出勤日数
         */
    @TableField("tph_nplanworkday")
        private Long tphNplanworkday;

        /**
         * 予定の8割日数
         */
    @TableField("tph_nplanworkday_limit")
        private Long tphNplanworkdayLimit;

        /**
         * 認定出勤日数(計算)
         */
    @TableField("tph_nconfirm")
        private Long tphNconfirm;

        /**
         * 付与の有効期限
         */
    @TableField("tph_dexpire_invest")
        private Date tphDexpireInvest;

        /**
         * 調整付与の有効期限
         */
    @TableField("tph_dexpire_adjust")
        private Date tphDexpireAdjust;

        /**
         * 調整繰越の有効期限
         */
    @TableField("tph_dexpire_adjust_to")
        private Date tphDexpireAdjustTo;

        /**
         * 喪失日数
         */
    @TableField("tph_nlose_days")
        private Long tphNloseDays;

        /**
         * 喪失時間数
         */
    @TableField("tph_nlose_hours")
        private Long tphNloseHours;

        /**
         * 変更者コメント欄
         */
    @TableField("tph_ccomment")
        private String tphCcomment;


        }