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
 * 超勤命令マスタ
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
@TableName("tmg_mast_instructiontype")
public class TmgMastInstructiontypeDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmit_ccustomerid")
        private String tmitCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmit_ccompanyid")
        private String tmitCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmit_dstartdate")
        private Date tmitDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmit_denddate")
        private Date tmitDenddate;

        /**
         * 更新者
         */
    @TableField("tmit_cmodifieruserid")
        private String tmitCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmit_dmodifieddate")
        private Date tmitDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmit_cmodifierprogramid")
        private String tmitCmodifierprogramid;

        /**
         * 入力項目ｺｰﾄﾞ
         */
                @TableId(value = "tmit_cmaster", type = IdType.AUTO)
                private String tmitCmaster;

        /**
         * 超勤命令ｺｰﾄﾞ
         */
    @TableField("tmit_cinstructiontypeid")
        private String tmitCinstructiontypeid;


        }