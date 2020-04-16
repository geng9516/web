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
 * public.tmg_employee_attribute
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
@TableName("tmg_employee_attribute")
public class TmgEmployeeAttributeDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客コード
         */
    @TableField("tes_ccustomerid")
        private String tesCcustomerid;

        /**
         * 法人コード
         */
    @TableField("tes_ccompanyid")
        private String tesCcompanyid;

        /**
         * 職員番号
         */
                @TableId(value = "tes_cemployeeid", type = IdType.AUTO)
                private String tesCemployeeid;

        /**
         * 開始日付
         */
    @TableField("tes_dstartdate")
        private Date tesDstartdate;

        /**
         * 終了日付
         */
    @TableField("tes_denddate")
        private Date tesDenddate;

        /**
         * 更新者
         */
    @TableField("tes_cmodifieruserid")
        private String tesCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tes_dmodifieddate")
        private Date tesDmodifieddate;

        /**
         * 更新プログラム
         */
    @TableField("tes_cmodifierprogramid")
        private String tesCmodifierprogramid;

        /**
         * 項目区分：[tmg_items|effort][tmg_items|dutyhours][tmg_items|daynight]
         */
    @TableField("tes_ctype")
        private String tesCtype;

        /**
         * 項目属性：[tmg_onoff|0][tmg_onoff|1]
         */
    @TableField("tes_cattribute")
        private String tesCattribute;

        /**
         * csparechar1
         */
    @TableField("csparechar1")
        private String csparechar1;

        /**
         * csparechar2
         */
    @TableField("csparechar2")
        private String csparechar2;

        /**
         * csparechar3
         */
    @TableField("csparechar3")
        private String csparechar3;

        /**
         * csparechar4
         */
    @TableField("csparechar4")
        private String csparechar4;

        /**
         * csparechar5
         */
    @TableField("csparechar5")
        private String csparechar5;

        /**
         * nsparenum1
         */
    @TableField("nsparenum1")
        private Long nsparenum1;

        /**
         * nsparenum2
         */
    @TableField("nsparenum2")
        private Long nsparenum2;

        /**
         * nsparenum3
         */
    @TableField("nsparenum3")
        private Long nsparenum3;

        /**
         * nsparenum4
         */
    @TableField("nsparenum4")
        private Long nsparenum4;

        /**
         * nsparenum5
         */
    @TableField("nsparenum5")
        private Long nsparenum5;

        /**
         * dsparedate1
         */
    @TableField("dsparedate1")
        private Date dsparedate1;

        /**
         * dsparedate2
         */
    @TableField("dsparedate2")
        private Date dsparedate2;

        /**
         * dsparedate3
         */
    @TableField("dsparedate3")
        private Date dsparedate3;

        /**
         * dsparedate4
         */
    @TableField("dsparedate4")
        private Date dsparedate4;

        /**
         * dsparedate5
         */
    @TableField("dsparedate5")
        private Date dsparedate5;

        /**
         * ccode01
         */
    @TableField("ccode01")
        private String ccode01;

        /**
         * ccodenm01
         */
    @TableField("ccodenm01")
        private String ccodenm01;

        /**
         * ccode02
         */
    @TableField("ccode02")
        private String ccode02;

        /**
         * ccodenm02
         */
    @TableField("ccodenm02")
        private String ccodenm02;

        /**
         * ccode03
         */
    @TableField("ccode03")
        private String ccode03;

        /**
         * ccodenm03
         */
    @TableField("ccodenm03")
        private String ccodenm03;

        /**
         * ccode04
         */
    @TableField("ccode04")
        private String ccode04;

        /**
         * ccodenm04
         */
    @TableField("ccodenm04")
        private String ccodenm04;

        /**
         * ccode05
         */
    @TableField("ccode05")
        private String ccode05;

        /**
         * ccodenm05
         */
    @TableField("ccodenm05")
        private String ccodenm05;


        }