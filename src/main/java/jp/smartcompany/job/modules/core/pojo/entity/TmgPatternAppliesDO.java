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
 * [就業]勤務パターン適用情報(部署)
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
@TableName("tmg_pattern_applies")
public class TmgPatternAppliesDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ
         */
    @TableField("tpaa_ccustomerid")
        private String tpaaCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tpaa_ccompanyid")
        private String tpaaCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tpaa_dstartdate")
        private Date tpaaDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tpaa_denddate")
        private Date tpaaDenddate;

        /**
         * 社員番号
         */
    @TableField("tpaa_cemployeeid")
        private String tpaaCemployeeid;

        /**
         * 部局ｺｰﾄﾞ
         */
    @TableField("tpaa_csectionid")
        private String tpaaCsectionid;

        /**
         * 更新者
         */
    @TableField("tpaa_cmodifieruserid")
        private String tpaaCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tpaa_dmodifieddate")
        private Date tpaaDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tpaa_cmodifierprogramid")
        private String tpaaCmodifierprogramid;

        /**
         * グループコード
         */
    @TableField("tpaa_cgroupid")
        private String tpaaCgroupid;

        /**
         * 勤務パターンid
         */
    @TableField("tpaa_cpatternid")
        private String tpaaCpatternid;


        }