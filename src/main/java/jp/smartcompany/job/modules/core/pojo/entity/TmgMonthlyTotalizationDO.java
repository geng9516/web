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
 * [勤怠]月次集計情報
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
@TableName("tmg_monthly_totalization")
public class TmgMonthlyTotalizationDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmt_ccustomerid")
        private String tmtCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmt_ccompanyid")
        private String tmtCcompanyid;

        /**
         * 社員番号
         */
    @TableField("tmt_cemployeeid")
        private String tmtCemployeeid;

        /**
         * データ開始日                        固定：1900/01/01
         */
    @TableField("tmt_dstartdate")
        private Date tmtDstartdate;

        /**
         * データ終了日                        固定：2222/12/31
         */
    @TableField("tmt_denddate")
        private Date tmtDenddate;

        /**
         * 更新者
         */
    @TableField("tmt_cmodifieruserid")
        private String tmtCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmt_dmodifieddate")
        private Date tmtDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmt_cmodifierprogramid")
        private String tmtCmodifierprogramid;

        /**
         * 該当年月                          yyyy/mm/01
         */
    @TableField("tmt_dyyyymm")
        private Date tmtDyyyymm;

        /**
         * 集計項目ｺｰﾄﾞ
         */
    @TableField("tmt_ctotalizationid")
        private String tmtCtotalizationid;

        /**
         * 仕訳項目ｺｰﾄﾞ
         */
    @TableField("tmt_cjournalizingid")
        private String tmtCjournalizingid;

        /**
         * 値
         */
    @TableField("tmt_nvalue")
        private Long tmtNvalue;


        }