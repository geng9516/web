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
 * グループ事前判定データ
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
@TableName("hist_employeegroups")
public class HistEmployeegroupsDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "he_id", type = IdType.AUTO)
                private Long heId;

        /**
         * 顧客コード
         */
    @TableField("he_ccustomerid_ck")
        private String heCcustomeridCk;

        /**
         * 法人コード
         */
    @TableField("he_ccompanyid_ck")
        private String heCcompanyidCk;

        /**
         * 職員番号
         */
    @TableField("he_cemployeeid_ck")
        private String heCemployeeidCk;

        /**
         * ユーザid
         */
    @TableField("he_cuserid")
        private String heCuserid;

        /**
         * システムコード
         */
    @TableField("he_csystemid_ck")
        private String heCsystemidCk;

        /**
         * 開始日
         */
    @TableField("he_dstartdate_ck")
        private Date heDstartdateCk;

        /**
         * 終了日
         */
    @TableField("he_denddate")
        private Date heDenddate;

        /**
         * グループコード
         */
    @TableField("he_cgroupid")
        private String heCgroupid;

        /**
         * 優先順
         */
    @TableField("he_nweightage")
        private Long heNweightage;

        /**
         * 最終更新者
         */
    @TableField("he_cmodifieruserid")
        private String heCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("he_dmodifieddate")
        private Date heDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }