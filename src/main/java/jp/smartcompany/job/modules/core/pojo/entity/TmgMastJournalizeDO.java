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
 * 仕訳項目マスタ
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
@TableName("tmg_mast_journalize")
public class TmgMastJournalizeDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmj_ccustomerid")
        private String tmjCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmj_ccompanyid")
        private String tmjCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmj_dstartdate")
        private Date tmjDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmj_denddate")
        private Date tmjDenddate;

        /**
         * 更新者
         */
    @TableField("tmj_cmodifieruserid")
        private String tmjCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmj_dmodifieddate")
        private Date tmjDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmj_cmodifierprogramid")
        private String tmjCmodifierprogramid;

        /**
         * 仕訳項目ｺｰﾄﾞ
         */
                @TableId(value = "tmj_cjournalizingid", type = IdType.AUTO)
                private String tmjCjournalizingid;

        /**
         * 仕訳項目名称
         */
    @TableField("tmj_cjournalizingnm")
        private String tmjCjournalizingnm;

        /**
         * tmj_novertime_flg
         */
    @TableField("tmj_novertime_flg")
        private Long tmjNovertimeFlg;


        }