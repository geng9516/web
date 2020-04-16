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
 * 宿日直用所属ｺｰﾄﾞ変換マスタ               所属ｺｰﾄﾞを宿日直用の中間コードに変換する
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
@TableName("tmg_mast_section4duty")
public class TmgMastSection4dutyDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmsd_ccustomerid")
        private String tmsdCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmsd_ccompanyid")
        private String tmsdCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmsd_dstartdate")
        private Date tmsdDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmsd_denddate")
        private Date tmsdDenddate;

        /**
         * 更新者
         */
    @TableField("tmsd_cmodifieruserid")
        private String tmsdCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmsd_dmodifieddate")
        private Date tmsdDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmsd_cmodifierprogramid")
        private String tmsdCmodifierprogramid;

        /**
         * 所属ｺｰﾄﾞ
         */
                @TableId(value = "tmsd_csectionid", type = IdType.AUTO)
                private String tmsdCsectionid;

        /**
         * 中間所属ｺｰﾄﾞ
         */
    @TableField("tmsd_cmiddlesectionid")
        private String tmsdCmiddlesectionid;


        }