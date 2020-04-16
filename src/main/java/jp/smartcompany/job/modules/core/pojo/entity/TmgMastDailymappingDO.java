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
 * 日次テーブルマッピングマスタ                日次集計処理において、tmg_daily_totalizat
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
@TableName("tmg_mast_dailymapping")
public class TmgMastDailymappingDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmdm_ccustomerid")
        private String tmdmCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmdm_ccompanyid")
        private String tmdmCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmdm_dstartdate")
        private Date tmdmDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmdm_denddate")
        private Date tmdmDenddate;

        /**
         * 更新者
         */
    @TableField("tmdm_cmodifieruserid")
        private String tmdmCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmdm_dmodifieddate")
        private Date tmdmDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmdm_cmodifierprogramid")
        private String tmdmCmodifierprogramid;

        /**
         * 集計項目ｺｰﾄﾞ
         */
                @TableId(value = "tmdm_ctotalizationid", type = IdType.AUTO)
                private String tmdmCtotalizationid;

        /**
         * 集計先勤務年月日（オフセット値）
         */
    @TableField("tmdm_noffset")
        private Long tmdmNoffset;

        /**
         * マッピング先カラム名
         */
    @TableField("tmdm_cdailycolumn")
        private String tmdmCdailycolumn;


        }