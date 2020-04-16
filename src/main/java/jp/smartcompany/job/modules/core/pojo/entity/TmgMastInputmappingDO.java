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
 * 入力項目マッピングマスタ                  日次集計処理において、tmg_dailyおよびtmg_dai
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
@TableName("tmg_mast_inputmapping")
public class TmgMastInputmappingDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmim_ccustomerid")
        private String tmimCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmim_ccompanyid")
        private String tmimCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmim_dstartdate")
        private Date tmimDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmim_denddate")
        private Date tmimDenddate;

        /**
         * 更新者
         */
    @TableField("tmim_cmodifieruserid")
        private String tmimCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmim_dmodifieddate")
        private Date tmimDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmim_cmodifierprogramid")
        private String tmimCmodifierprogramid;

        /**
         * 参照テーブル
         */
    @TableField("tmim_ctable")
        private String tmimCtable;

        /**
         * 参照カラム
         */
    @TableField("tmim_ccolumn")
        private String tmimCcolumn;

        /**
         * 入力項目ｺｰﾄﾞ
         */
                @TableId(value = "tmim_cmaster", type = IdType.AUTO)
                private String tmimCmaster;

        /**
         * 集計カテゴリｺｰﾄﾞ
         */
    @TableField("tmim_ccategoryid")
        private String tmimCcategoryid;

        /**
         * 集計タイプｺｰﾄﾞ                     ﾊﾟｯｹｰｼﾞ定数
         */
    @TableField("tmim_ctotaltypeid")
        private String tmimCtotaltypeid;


        }