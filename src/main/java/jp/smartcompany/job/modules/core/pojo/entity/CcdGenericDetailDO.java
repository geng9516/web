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
 * public.ccd_generic_detail
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
@TableName("ccd_generic_detail")
public class CcdGenericDetailDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * mgd_id
         */
    @TableId(value="mgd_id",type = IdType.INPUT)
        private Long mgdId;

        /**
         * mgd_ccustomerid
         */
    @TableField("mgd_ccustomerid")
        private String mgdCcustomerid;

        /**
         * mgd_ccompanyid_ck_fk
         */
    @TableField("mgd_ccompanyid_ck_fk")
        private String mgdCcompanyidCkFk;

        /**
         * mgd_cgenericgroupid
         */
    @TableField("mgd_cgenericgroupid")
        private String mgdCgenericgroupid;

        /**
         * mgd_cgenericdetailid_ck
         */
    @TableField("mgd_cgenericdetailid_ck")
        private String mgdCgenericdetailidCk;

        /**
         * mgd_cmastercode
         */
    @TableField("mgd_cmastercode")
        private String mgdCmastercode;

        /**
         * mgd_clanguage_ck
         */
    @TableField("mgd_clanguage_ck")
        private String mgdClanguageCk;

        /**
         * mgd_dstart_ck
         */
    @TableField("mgd_dstart_ck")
        private Date mgdDstartCk;

        /**
         * mgd_dend
         */
    @TableField("mgd_dend")
        private Date mgdDend;

        /**
         * mgd_cmodifieruserid
         */
    @TableField("mgd_cmodifieruserid")
        private String mgdCmodifieruserid;

        /**
         * mgd_dmodifieddate
         */
    @TableField("mgd_dmodifieddate")
        private Date mgdDmodifieddate;

        /**
         * mgd_cgenericdetaildesc
         */
    @TableField("mgd_cgenericdetaildesc")
        private String mgdCgenericdetaildesc;

        /**
         * mgd_cgenericdetaildescja
         */
    @TableField("mgd_cgenericdetaildescja")
        private String mgdCgenericdetaildescja;

        /**
         * mgd_cgenericdetaildescen
         */
    @TableField("mgd_cgenericdetaildescen")
        private String mgdCgenericdetaildescen;

        /**
         * mgd_cgenericdetaildescch
         */
    @TableField("mgd_cgenericdetaildescch")
        private String mgdCgenericdetaildescch;

        /**
         * mgd_cgenericdetaildesc01
         */
    @TableField("mgd_cgenericdetaildesc01")
        private String mgdCgenericdetaildesc01;

        /**
         * mgd_cgenericdetaildesc02
         */
    @TableField("mgd_cgenericdetaildesc02")
        private String mgdCgenericdetaildesc02;

        /**
         * mgd_csparedesc1ja
         */
    @TableField("mgd_csparedesc1ja")
        private String mgdCsparedesc1ja;

        /**
         * mgd_csparedesc1
         */
    @TableField("mgd_csparedesc1")
        private String mgdCsparedesc1;

        /**
         * mgd_csparedesc1en
         */
    @TableField("mgd_csparedesc1en")
        private String mgdCsparedesc1en;

        /**
         * mgd_csparedesc1ch
         */
    @TableField("mgd_csparedesc1ch")
        private String mgdCsparedesc1ch;

        /**
         * mgd_csparedesc101
         */
    @TableField("mgd_csparedesc101")
        private String mgdCsparedesc101;

        /**
         * mgd_csparedesc102
         */
    @TableField("mgd_csparedesc102")
        private String mgdCsparedesc102;

        /**
         * mgd_csparedesc2
         */
    @TableField("mgd_csparedesc2")
        private String mgdCsparedesc2;

        /**
         * mgd_csparedesc2ja
         */
    @TableField("mgd_csparedesc2ja")
        private String mgdCsparedesc2ja;

        /**
         * mgd_csparedesc2en
         */
    @TableField("mgd_csparedesc2en")
        private String mgdCsparedesc2en;

        /**
         * mgd_csparedesc2ch
         */
    @TableField("mgd_csparedesc2ch")
        private String mgdCsparedesc2ch;

        /**
         * mgd_csparedesc201
         */
    @TableField("mgd_csparedesc201")
        private String mgdCsparedesc201;

        /**
         * mgd_csparedesc202
         */
    @TableField("mgd_csparedesc202")
        private String mgdCsparedesc202;

        /**
         * mgd_csparedesc3
         */
    @TableField("mgd_csparedesc3")
        private String mgdCsparedesc3;

        /**
         * mgd_csparedesc3ja
         */
    @TableField("mgd_csparedesc3ja")
        private String mgdCsparedesc3ja;

        /**
         * mgd_csparedesc3en
         */
    @TableField("mgd_csparedesc3en")
        private String mgdCsparedesc3en;

        /**
         * mgd_csparedesc3ch
         */
    @TableField("mgd_csparedesc3ch")
        private String mgdCsparedesc3ch;

        /**
         * mgd_csparedesc301
         */
    @TableField("mgd_csparedesc301")
        private String mgdCsparedesc301;

        /**
         * mgd_csparedesc302
         */
    @TableField("mgd_csparedesc302")
        private String mgdCsparedesc302;

        /**
         * mgd_csparedesc4
         */
    @TableField("mgd_csparedesc4")
        private String mgdCsparedesc4;

        /**
         * mgd_csparedesc4ja
         */
    @TableField("mgd_csparedesc4ja")
        private String mgdCsparedesc4ja;

        /**
         * mgd_csparedesc4en
         */
    @TableField("mgd_csparedesc4en")
        private String mgdCsparedesc4en;

        /**
         * mgd_csparedesc4ch
         */
    @TableField("mgd_csparedesc4ch")
        private String mgdCsparedesc4ch;

        /**
         * mgd_csparedesc401
         */
    @TableField("mgd_csparedesc401")
        private String mgdCsparedesc401;

        /**
         * mgd_csparedesc402
         */
    @TableField("mgd_csparedesc402")
        private String mgdCsparedesc402;

        /**
         * mgd_csparedesc5
         */
    @TableField("mgd_csparedesc5")
        private String mgdCsparedesc5;

        /**
         * mgd_csparedesc5ja
         */
    @TableField("mgd_csparedesc5ja")
        private String mgdCsparedesc5ja;

        /**
         * mgd_csparedesc5en
         */
    @TableField("mgd_csparedesc5en")
        private String mgdCsparedesc5en;

        /**
         * mgd_csparedesc5ch
         */
    @TableField("mgd_csparedesc5ch")
        private String mgdCsparedesc5ch;

        /**
         * mgd_csparedesc501
         */
    @TableField("mgd_csparedesc501")
        private String mgdCsparedesc501;

        /**
         * mgd_csparedesc502
         */
    @TableField("mgd_csparedesc502")
        private String mgdCsparedesc502;

        /**
         * mgd_csparechar1
         */
    @TableField("mgd_csparechar1")
        private String mgdCsparechar1;

        /**
         * mgd_csparechar2
         */
    @TableField("mgd_csparechar2")
        private String mgdCsparechar2;

        /**
         * mgd_csparechar3
         */
    @TableField("mgd_csparechar3")
        private String mgdCsparechar3;

        /**
         * mgd_csparechar4
         */
    @TableField("mgd_csparechar4")
        private String mgdCsparechar4;

        /**
         * mgd_csparechar5
         */
    @TableField("mgd_csparechar5")
        private String mgdCsparechar5;

        /**
         * mgd_nsparenum1
         */
    @TableField("mgd_nsparenum1")
        private Long mgdNsparenum1;

        /**
         * mgd_nsparenum2
         */
    @TableField("mgd_nsparenum2")
        private Long mgdNsparenum2;

        /**
         * mgd_nsparenum3
         */
    @TableField("mgd_nsparenum3")
        private Long mgdNsparenum3;

        /**
         * mgd_nsparenum4
         */
    @TableField("mgd_nsparenum4")
        private Long mgdNsparenum4;

        /**
         * mgd_nsparenum5
         */
    @TableField("mgd_nsparenum5")
        private Long mgdNsparenum5;

        /**
         * mgd_dsparedate1
         */
    @TableField("mgd_dsparedate1")
        private Date mgdDsparedate1;

        /**
         * mgd_dsparedate2
         */
    @TableField("mgd_dsparedate2")
        private Date mgdDsparedate2;

        /**
         * mgd_dsparedate3
         */
    @TableField("mgd_dsparedate3")
        private Date mgdDsparedate3;

        /**
         * mgd_dsparedate4
         */
    @TableField("mgd_dsparedate4")
        private Date mgdDsparedate4;

        /**
         * mgd_dsparedate5
         */
    @TableField("mgd_dsparedate5")
        private Date mgdDsparedate5;

        /**
         * versionno
         */
    @TableField("versionno")
        private Long versionno;


        }