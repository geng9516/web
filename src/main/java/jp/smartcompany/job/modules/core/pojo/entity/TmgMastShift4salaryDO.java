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
 * シフト区分マスタ                      給与データ出力に使用するシフト区分を管理します。
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
@TableName("tmg_mast_shift4salary")
public class TmgMastShift4salaryDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmss_ccustomerid")
        private String tmssCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmss_ccompanyid")
        private String tmssCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmss_dstartdate")
        private Date tmssDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmss_denddate")
        private Date tmssDenddate;

        /**
         * 更新者
         */
    @TableField("tmss_cmodifieruserid")
        private String tmssCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmss_dmodifieddate")
        private Date tmssDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmss_cmodifierprogramid")
        private String tmssCmodifierprogramid;

        /**
         * 集計項目ｺｰﾄﾞ
         */
                @TableId(value = "tmss_ctotalizationid", type = IdType.AUTO)
                private String tmssCtotalizationid;

        /**
         * シフト区分
         */
    @TableField("tmss_cshiftid")
        private String tmssCshiftid;


        }