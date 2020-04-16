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
 * [勤怠]法人情報                      2007/01/31項目追加｢年度の開始月」
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
@TableName("tmg_company")
public class TmgCompanyDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tco_ccustomerid")
        private String tcoCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tco_ccompanyid")
        private String tcoCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日                       固定：1900/01/01
         */
                @TableId(value = "tco_dstartdate", type = IdType.AUTO)
                private Date tcoDstartdate;

        /**
         * ﾃﾞｰﾀ終了日                       固定：2222/12/31
         */
    @TableField("tco_denddate")
        private Date tcoDenddate;

        /**
         * 更新者
         */
    @TableField("tco_cmodifieruserid")
        private String tcoCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tco_dmodifieddate")
        private Date tcoDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tco_cmodifierprogramid")
        private String tcoCmodifierprogramid;

        /**
         * 開始時刻                          300
         */
    @TableField("tco_nopen")
        private Long tcoNopen;

        /**
         * 終了時刻                          1740
         */
    @TableField("tco_nclose")
        private Long tcoNclose;

        /**
         * 深夜勤務開始時刻                      1320
         */
    @TableField("tco_nmidnightopen")
        private Long tcoNmidnightopen;

        /**
         * 深夜勤務終了時刻                      1740
         */
    @TableField("tco_nmidnightclose")
        private Long tcoNmidnightclose;

        /**
         * 打刻サービス開始時刻                    420
         */
    @TableField("tco_ntimepunchopen")
        private Long tcoNtimepunchopen;

        /**
         * 打刻サービス終了時刻                    1320
         */
    @TableField("tco_ntimepunchclose")
        private Long tcoNtimepunchclose;

        /**
         * 年度の開始月
         */
    @TableField("tco_nbeginmonth")
        private Long tcoNbeginmonth;


        }