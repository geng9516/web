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
 * プラガブル種別マスタ
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
@TableName("tmg_pluggable_root")
public class TmgPluggableRootDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客コード
         */
                @TableId(value = "tplr_ccustomerid", type = IdType.AUTO)
                private String tplrCcustomerid;

        /**
         * 法人コード
         */
    @TableField("tplr_ccompanyid")
        private String tplrCcompanyid;

        /**
         * 処理フェーズ
         */
    @TableField("tplr_cphase")
        private String tplrCphase;

        /**
         * データ開始日
         */
    @TableField("tplr_dstartdate")
        private Date tplrDstartdate;

        /**
         * データ終了日
         */
    @TableField("tplr_denddate")
        private Date tplrDenddate;

        /**
         * 更新者
         */
    @TableField("tplr_cmodifieruserid")
        private String tplrCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tplr_dmodifieddate")
        private Date tplrDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tplr_cmodifierprogramid")
        private String tplrCmodifierprogramid;

        /**
         * カラム「tpl_cworktypeid」の扱い 「*(一律)」「対象者の身分」「コード直接指定」
         */
    @TableField("tplr_cpluggabletype")
        private String tplrCpluggabletype;


        }