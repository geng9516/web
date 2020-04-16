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
 * 給与テーブルマッピングマスタ(シフト区分分割用)      給与データ出力処理において、tmg_daily_totali
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
@TableName("tmg_mast_salarymapping4shft")
public class TmgMastSalarymapping4shftDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmsm_ccustomerid")
        private String tmsmCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmsm_ccompanyid")
        private String tmsmCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmsm_dstartdate")
        private Date tmsmDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmsm_denddate")
        private Date tmsmDenddate;

        /**
         * 更新者
         */
    @TableField("tmsm_cmodifieruserid")
        private String tmsmCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmsm_dmodifieddate")
        private Date tmsmDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmsm_cmodifierprogramid")
        private String tmsmCmodifierprogramid;

        /**
         * 集計項目ｺｰﾄﾞ
         */
                @TableId(value = "tmsm_ctotalizationid", type = IdType.AUTO)
                private String tmsmCtotalizationid;

        /**
         * マッピング先カラム名
         */
    @TableField("tmsm_csalarycolumn")
        private String tmsmCsalarycolumn;


        }