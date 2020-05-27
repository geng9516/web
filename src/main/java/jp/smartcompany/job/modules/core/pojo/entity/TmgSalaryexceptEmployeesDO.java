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
 * 給与除外対象者ワークテーブル
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
@TableName("tmg_salaryexcept_employees")
public class TmgSalaryexceptEmployeesDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客コード
         */
    @TableId(value="tess_ccustomerid",type = IdType.INPUT)
        private String tessCcustomerid;

        /**
         * 法人コード
         */
    @TableField("tess_ccompanyid")
        private String tessCcompanyid;

        /**
         * 職員番号
         */
    @TableField("tess_cemployeeid")
        private String tessCemployeeid;

        /**
         * 開始日
         */
    @TableField("tess_dstartdate")
        private Date tessDstartdate;

        /**
         * 終了日
         */
    @TableField("tess_denddate")
        private Date tessDenddate;

        /**
         * マスタコード
         */
    @TableField("tess_cmastercode")
        private String tessCmastercode;

        /**
         * 更新者
         */
    @TableField("tess_cmodifieruserid")
        private String tessCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tess_dmodifieddate")
        private Date tessDmodifieddate;

        /**
         * 更新ﾌﾟﾛｸﾞﾗﾑid
         */
    @TableField("tess_cmodifierprogramid")
        private String tessCmodifierprogramid;


        }