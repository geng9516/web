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
 * 除外項目マスタ                       集計処理カテゴリごとに、重複している場合に重複部分を除外する
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
@TableName("tmg_mast_exclusionitem")
public class TmgMastExclusionitemDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmei_ccustomerid")
        private String tmeiCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmei_ccompanyid")
        private String tmeiCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmei_dstartdate")
        private Date tmeiDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmei_denddate")
        private Date tmeiDenddate;

        /**
         * 更新者
         */
    @TableField("tmei_cmodifieruserid")
        private String tmeiCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmei_dmodifieddate")
        private Date tmeiDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmei_cmodifierprogramid")
        private String tmeiCmodifierprogramid;

        /**
         * 集計カテゴリｺｰﾄﾞ
         */
                @TableId(value = "tmei_ccategoryid", type = IdType.AUTO)
                private String tmeiCcategoryid;

        /**
         * 入力項目ｺｰﾄﾞ
         */
    @TableField("tmei_cmaster")
        private String tmeiCmaster;


        }