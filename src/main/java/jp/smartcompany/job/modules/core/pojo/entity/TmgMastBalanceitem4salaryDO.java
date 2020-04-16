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
 * 差引優先項目マスタ                     給与データ出力時に超勤項目から時間を差引く際の、差し引く対象
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
@TableName("tmg_mast_balanceitem4salary")
public class TmgMastBalanceitem4salaryDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmbi_ccustomerid")
        private String tmbiCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmbi_ccompanyid")
        private String tmbiCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmbi_dstartdate")
        private Date tmbiDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmbi_denddate")
        private Date tmbiDenddate;

        /**
         * 更新者
         */
    @TableField("tmbi_cmodifieruserid")
        private String tmbiCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmbi_dmodifieddate")
        private Date tmbiDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmbi_cmodifierprogramid")
        private String tmbiCmodifierprogramid;

        /**
         * 差引対象項目
         */
                @TableId(value = "tmbi_cmaster", type = IdType.AUTO)
                private String tmbiCmaster;

        /**
         * 優先順位
         */
    @TableField("tmbi_norder")
        private Long tmbiNorder;


        }