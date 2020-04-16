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
 * public.tmg_employees_bef
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
@TableName("tmg_employees_bef")
public class TmgEmployeesBefDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * tem_ccustomerid
         */
    @TableField("tem_ccustomerid")
        private String temCcustomerid;

        /**
         * tem_ccompanyid
         */
    @TableField("tem_ccompanyid")
        private String temCcompanyid;

        /**
         * tem_cemployeeid
         */
    @TableField("tem_cemployeeid")
        private String temCemployeeid;

        /**
         * tem_dstartdate
         */
    @TableField("tem_dstartdate")
        private Date temDstartdate;

        /**
         * tem_denddate
         */
    @TableField("tem_denddate")
        private Date temDenddate;

        /**
         * tem_cmodifieruserid
         */
    @TableField("tem_cmodifieruserid")
        private String temCmodifieruserid;

        /**
         * tem_dmodifieddate
         */
    @TableField("tem_dmodifieddate")
        private Date temDmodifieddate;

        /**
         * tem_cmodifierprogramid
         */
    @TableField("tem_cmodifierprogramid")
        private String temCmodifierprogramid;

        /**
         * tem_cworktypeid
         */
    @TableField("tem_cworktypeid")
        private String temCworktypeid;

        /**
         * tem_cworktypename
         */
    @TableField("tem_cworktypename")
        private String temCworktypename;

        /**
         * tem_csparechar1
         */
    @TableField("tem_csparechar1")
        private String temCsparechar1;

        /**
         * tem_csparechar2
         */
    @TableField("tem_csparechar2")
        private String temCsparechar2;

        /**
         * tem_csparechar3
         */
    @TableField("tem_csparechar3")
        private String temCsparechar3;

        /**
         * tem_csparechar4
         */
    @TableField("tem_csparechar4")
        private String temCsparechar4;

        /**
         * tem_csparechar5
         */
    @TableField("tem_csparechar5")
        private String temCsparechar5;

        /**
         * tem_nsparenum1
         */
    @TableField("tem_nsparenum1")
        private Long temNsparenum1;

        /**
         * tem_nsparenum2
         */
    @TableField("tem_nsparenum2")
        private Long temNsparenum2;

        /**
         * tem_nsparenum3
         */
    @TableField("tem_nsparenum3")
        private Long temNsparenum3;

        /**
         * tem_nsparenum4
         */
    @TableField("tem_nsparenum4")
        private Long temNsparenum4;

        /**
         * tem_nsparenum5
         */
    @TableField("tem_nsparenum5")
        private Long temNsparenum5;

        /**
         * tem_dsparedate1
         */
    @TableField("tem_dsparedate1")
        private Date temDsparedate1;

        /**
         * tem_dsparedate2
         */
    @TableField("tem_dsparedate2")
        private Date temDsparedate2;

        /**
         * tem_dsparedate3
         */
    @TableField("tem_dsparedate3")
        private Date temDsparedate3;

        /**
         * tem_dsparedate4
         */
    @TableField("tem_dsparedate4")
        private Date temDsparedate4;

        /**
         * tem_dsparedate5
         */
    @TableField("tem_dsparedate5")
        private Date temDsparedate5;

        /**
         * tem_cmanageflg
         */
    @TableField("tem_cmanageflg")
        private String temCmanageflg;

        /**
         * tem_cday_duty_flg
         */
    @TableField("tem_cday_duty_flg")
        private String temCdayDutyFlg;

        /**
         * tem_cnight_duty_flg
         */
    @TableField("tem_cnight_duty_flg")
        private String temCnightDutyFlg;


        }