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
 * 遅刻早退判定対象マスタ                   勤怠種別、出張区分から遅刻・早退を判定する対象を設定するマス
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
@TableName("tmg_mast_target4latecheck")
public class TmgMastTarget4latecheckDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmtl_ccustomerid")
        private String tmtlCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmtl_ccompanyid")
        private String tmtlCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmtl_dstartdate")
        private Date tmtlDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmtl_denddate")
        private Date tmtlDenddate;

        /**
         * 更新者
         */
    @TableField("tmtl_cmodifieruserid")
        private String tmtlCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmtl_dmodifieddate")
        private Date tmtlDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmtl_cmodifierprogramid")
        private String tmtlCmodifierprogramid;

        /**
         * 出張区分
         */
    @TableField("tmtl_cbusinesstripid_r")
        private String tmtlCbusinesstripidR;

        /**
         * 勤怠種別
         */
                @TableId(value = "tmtl_cworktypeid", type = IdType.AUTO)
                private String tmtlCworktypeid;


        }