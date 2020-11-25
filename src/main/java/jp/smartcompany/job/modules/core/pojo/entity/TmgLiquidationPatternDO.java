package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

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
@TableName("tmg_liquidation_pattern")
public class TmgLiquidationPatternDO implements Serializable {

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
    @TableField("tpa_cemployeeid")
        private String tpaCemployeeid;

    /**
     * 部局ｺｰﾄﾞ
     */
    @TableField("tpa_cworktypeid")
    private String tpaCworktypeid;

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
                @TableField("tpa_cpatternid")
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


        }