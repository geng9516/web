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
 * [勤怠]勤務パターン                    制約：月中に歴が切れないこと、デフォルトフラグがonの行は同
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
@TableName("tmg_pattern")
public class TmgPatternDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tpa_ccustomerid")
        private String tpaCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tpa_ccompanyid")
        private String tpaCcompanyid;

        /**
         * 部局ｺｰﾄﾞ
         */
    @TableField("tpa_csectionid")
        private String tpaCsectionid;

        /**
         * グループコード
         */
    @TableField("tpa_cgroupid")
        private String tpaCgroupid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tpa_dstartdate")
        private Date tpaDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tpa_denddate")
        private Date tpaDenddate;

        /**
         * 更新者
         */
    @TableField("tpa_cmodifieruserid")
        private String tpaCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tpa_dmodifieddate")
        private Date tpaDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tpa_cmodifierprogramid")
        private String tpaCmodifierprogramid;

        /**
         * 勤務パターンid                      京大では1パターンのみ                   mgd:tmg_pattern
         */
                @TableId(value = "tpa_cpatternid", type = IdType.AUTO)
                private String tpaCpatternid;

        /**
         * 勤務パターン名称
         */
    @TableField("tpa_cpatternname")
        private String tpaCpatternname;

        /**
         * デフォルトフラグ                                                    mgd:tmg_onoff
         */
    @TableField("tpa_cdefaultflg")
        private String tpaCdefaultflg;

        /**
         * 始業時刻
         */
    @TableField("tpa_nopen")
        private Long tpaNopen;

        /**
         * 終業時刻
         */
    @TableField("tpa_nclose")
        private Long tpaNclose;

        /**
         * 2暦日勤務フラグ
         */
    @TableField("tpa_c2caldays")
        private String tpaC2caldays;

        /**
         * 翌日の勤務パターン
         */
    @TableField("tpa_cnextptn")
        private String tpaCnextptn;

        /**
         * tpa_ndate_change_time
         */
    @TableField("tpa_ndate_change_time")
        private Long tpaNdateChangeTime;


        }