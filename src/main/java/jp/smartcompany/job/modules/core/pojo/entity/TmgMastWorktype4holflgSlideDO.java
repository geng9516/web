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
 * 休日給支給対象フラグスライドマスタ
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
@TableName("tmg_mast_worktype4holflg_slide")
public class TmgMastWorktype4holflgSlideDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmhs_ccustomerid")
        private String tmhsCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmhs_ccompanyid")
        private String tmhsCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmhs_dstartdate")
        private Date tmhsDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmhs_denddate")
        private Date tmhsDenddate;

        /**
         * 更新者
         */
    @TableField("tmhs_cmodifieruserid")
        private String tmhsCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmhs_dmodifieddate")
        private Date tmhsDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmhs_cmodifierprogramid")
        private String tmhsCmodifierprogramid;

        /**
         * 就業区分ｺｰﾄﾞ
         */
                @TableId(value = "tmhs_cworkingid", type = IdType.AUTO)
                private String tmhsCworkingid;


        }