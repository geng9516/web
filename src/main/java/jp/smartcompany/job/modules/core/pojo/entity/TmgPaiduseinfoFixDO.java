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
 * 年休取得修正情報
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
@TableName("tmg_paiduseinfo_fix")
public class TmgPaiduseinfoFixDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客コード
         */
                @TableId(value = "tpf_ccustomerid", type = IdType.INPUT)
                private String tpfCcustomerid;

        /**
         * 法人コード
         */
    @TableField("tpf_ccompanyid")
        private String tpfCcompanyid;

        /**
         * データ開始日 (1900/01/01)
         */
    @TableField("tpf_dstartdate")
        private Date tpfDstartdate;

        /**
         * データ終了日 (2222/12/31)
         */
    @TableField("tpf_denddate")
        private Date tpfDenddate;

        /**
         * 更新者
         */
    @TableField("tpf_cmodifieruserid")
        private String tpfCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tpf_dmodifierdate")
        private Date tpfDmodifierdate;

        /**
         * 更新プログラムｉｄ
         */
    @TableField("tpf_cmodifierprogramid")
        private String tpfCmodifierprogramid;

        /**
         * 職員番号
         */
    @TableField("tpf_cemployeeid")
        private String tpfCemployeeid;

        /**
         * 基準日
         */
    @TableField("tpf_dpaid_holiday")
        private Date tpfDpaidHoliday;

        /**
         * 基準日修正年
         */
    @TableField("tpf_nyyyy")
        private Long tpfNyyyy;

        /**
         * 修正基準日
         */
    @TableField("tpf_dpaid_holiday_fix")
        private Date tpfDpaidHolidayFix;

        /**
         * 修正チェック期間
         */
    @TableField("tpf_dkikanbi_fix")
        private Date tpfDkikanbiFix;

        /**
         * 修正取得日数
         */
    @TableField("tpf_nusedays_fix")
        private Long tpfNusedaysFix;

        /**
         * 修正必要日数
         */
    @TableField("tpf_nmustdays_fix")
        private Long tpfNmustdaysFix;

        /**
         * 調整取得日数
         */
    @TableField("tpf_nusedays_ajdust")
        private Long tpfNusedaysAjdust;

        /**
         * 調整取得時間数
         */
    @TableField("tpf_nusehours_ajdust")
        private Long tpfNusehoursAjdust;


        }