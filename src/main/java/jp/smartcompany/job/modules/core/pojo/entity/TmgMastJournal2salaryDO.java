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
 * 手当給与項目用マスタ                    仕訳情報から給与項目単位への変換マスタ
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
@TableName("tmg_mast_journal2salary")
public class TmgMastJournal2salaryDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmjs_ccustomerid")
        private String tmjsCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmjs_ccompanyid")
        private String tmjsCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmjs_dstartdate")
        private Date tmjsDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmjs_denddate")
        private Date tmjsDenddate;

        /**
         * 更新者
         */
    @TableField("tmjs_cmodifieruserid")
        private String tmjsCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmjs_dmodifieddate")
        private Date tmjsDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmjs_cmodifierprogramid")
        private String tmjsCmodifierprogramid;

        /**
         * 仕訳項目ｺｰﾄﾞ
         */
                @TableId(value = "tmjs_cjournalizingid", type = IdType.AUTO)
                private String tmjsCjournalizingid;

        /**
         * 中間ｺｰﾄﾞ
         */
    @TableField("tmjs_cmiddleid")
        private String tmjsCmiddleid;


        }