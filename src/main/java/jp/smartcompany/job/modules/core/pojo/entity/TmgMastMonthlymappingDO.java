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
 * 月次テーブルマッピングマスタ                月次集計処理において、tmg_monthly_totaliz
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
@TableName("tmg_mast_monthlymapping")
public class TmgMastMonthlymappingDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmmm_ccustomerid")
        private String tmmmCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmmm_ccompanyid")
        private String tmmmCcompanyid;

        /**
         * データ開始日
         */
    @TableField("tmmm_dstartdate")
        private Date tmmmDstartdate;

        /**
         * データ終了日
         */
    @TableField("tmmm_denddate")
        private Date tmmmDenddate;

        /**
         * 更新者
         */
    @TableField("tmmm_cmodifieruserid")
        private String tmmmCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmmm_dmodifieddate")
        private Date tmmmDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmmm_cmodifierprogramid")
        private String tmmmCmodifierprogramid;

        /**
         * 集計項目ｺｰﾄﾞ
         */
                @TableId(value = "tmmm_ctotalizationid", type = IdType.AUTO)
                private String tmmmCtotalizationid;

        /**
         * マッピング先カラム名
         */
    @TableField("tmmm_cmonthlycolumn")
        private String tmmmCmonthlycolumn;


        }