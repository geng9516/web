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
 * 超勤手当支給対象判定マスタ                 管理職/非管理職ごとに、給与用項目として使用する仕訳ｺｰﾄﾞ
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
@TableName("tmg_mast_journal2manager")
public class TmgMastJournal2managerDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmjm_ccustomerid")
        private String tmjmCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmjm_ccompanyid")
        private String tmjmCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmjm_dstartdate")
        private Date tmjmDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmjm_denddate")
        private Date tmjmDenddate;

        /**
         * 更新者
         */
    @TableField("tmjm_cmodifieruserid")
        private String tmjmCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmjm_dmodifieddate")
        private Date tmjmDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmjm_cmodifierprogramid")
        private String tmjmCmodifierprogramid;

        /**
         * 給与用中間コード(変換前)
         */
                @TableId(value = "tmjm_cjournalizingid4salary_i", type = IdType.AUTO)
                private String tmjmCjournalizingid4salaryI;

        /**
         * 管理職ｺｰﾄﾞ
         */
    @TableField("tmjm_cmanagerid")
        private String tmjmCmanagerid;

        /**
         * 給与用中間コード(変換後)
         */
    @TableField("tmjm_cjournalizingid4salary_o")
        private String tmjmCjournalizingid4salaryO;


        }