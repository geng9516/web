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
 * 集計項目マッピングマスタ                  日次集計処理において、tmg_dailyおよびtmg_dai
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
@TableName("tmg_mast_totalmapping")
public class TmgMastTotalmappingDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
                @TableId(value = "tmtm_ccustomerid", type = IdType.AUTO)
                private String tmtmCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmtm_ccompanyid")
        private String tmtmCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmtm_dstartdate")
        private Date tmtmDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmtm_denddate")
        private Date tmtmDenddate;

        /**
         * 更新者
         */
    @TableField("tmtm_cmodifieruserid")
        private String tmtmCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmtm_dmodifieddate")
        private Date tmtmDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmtm_cmodifierprogramid")
        private String tmtmCmodifierprogramid;

        /**
         * 勤怠種別     mgd:tmg_worktype
         */
    @TableField("tmtm_cworktypeid")
        private String tmtmCworktypeid;

        /**
         * 変換元マスタコード
         */
    @TableField("tmtm_cmaster4source")
        private String tmtmCmaster4source;

        /**
         * 集計単位ｺｰﾄﾞ
         */
    @TableField("tmtm_cunitid")
        private String tmtmCunitid;

        /**
         * 変換先マスタコード
         */
    @TableField("tmtm_cmaster4dest")
        private String tmtmCmaster4dest;

        /**
         * tmtm_cphase
         */
    @TableField("tmtm_cphase")
        private String tmtmCphase;

        /**
         * 管理職コード     tmg_mast_manager4salary(tmg_hist_manager)
         */
    @TableField("tmtm_cmanagerid")
        private String tmtmCmanagerid;


        }