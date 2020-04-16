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
 * public.hist_designation_bef
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
@TableName("hist_designation_bef")
public class HistDesignationBefDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * hd_id
         */
    @TableField("hd_id")
        private Long hdId;

        /**
         * hd_ccustomerid_ck
         */
    @TableField("hd_ccustomerid_ck")
        private String hdCcustomeridCk;

        /**
         * hd_ccompanyid_ck
         */
    @TableField("hd_ccompanyid_ck")
        private String hdCcompanyidCk;

        /**
         * hd_cemployeeid_ck
         */
    @TableField("hd_cemployeeid_ck")
        private String hdCemployeeidCk;

        /**
         * hd_cuserid
         */
    @TableField("hd_cuserid")
        private String hdCuserid;

        /**
         * hd_dstartdate_ck
         */
    @TableField("hd_dstartdate_ck")
        private Date hdDstartdateCk;

        /**
         * hd_denddate
         */
    @TableField("hd_denddate")
        private Date hdDenddate;

        /**
         * hd_cmodifieruserid
         */
    @TableField("hd_cmodifieruserid")
        private String hdCmodifieruserid;

        /**
         * hd_dmodifieddate
         */
    @TableField("hd_dmodifieddate")
        private Date hdDmodifieddate;

        /**
         * hd_ddateofannounce_section
         */
    @TableField("hd_ddateofannounce_section")
        private Date hdDdateofannounceSection;

        /**
         * hd_ddateofrelieval_section
         */
    @TableField("hd_ddateofrelieval_section")
        private Date hdDdateofrelievalSection;

        /**
         * hd_cifkeyoradditionalrole
         */
    @TableField("hd_cifkeyoradditionalrole")
        private String hdCifkeyoradditionalrole;

        /**
         * hd_csectionid_fk
         */
    @TableField("hd_csectionid_fk")
        private String hdCsectionidFk;

        /**
         * hd_csectionname
         */
    @TableField("hd_csectionname")
        private String hdCsectionname;

        /**
         * hd_csectionperiod
         */
    @TableField("hd_csectionperiod")
        private Long hdCsectionperiod;

        /**
         * hd_ddateofannounce_post
         */
    @TableField("hd_ddateofannounce_post")
        private Date hdDdateofannouncePost;

        /**
         * hd_ddateofrelieval_post
         */
    @TableField("hd_ddateofrelieval_post")
        private Date hdDdateofrelievalPost;

        /**
         * hd_cpostid_fk
         */
    @TableField("hd_cpostid_fk")
        private String hdCpostidFk;

        /**
         * hd_cpostname
         */
    @TableField("hd_cpostname")
        private String hdCpostname;

        /**
         * hd_npostweightage
         */
    @TableField("hd_npostweightage")
        private Long hdNpostweightage;

        /**
         * hd_cstatus
         */
    @TableField("hd_cstatus")
        private String hdCstatus;

        /**
         * hd_cpostperiod
         */
    @TableField("hd_cpostperiod")
        private Long hdCpostperiod;

        /**
         * hd_cjobresponsibilityid
         */
    @TableField("hd_cjobresponsibilityid")
        private String hdCjobresponsibilityid;

        /**
         * hd_cjobresponsibilitynm
         */
    @TableField("hd_cjobresponsibilitynm")
        private String hdCjobresponsibilitynm;

        /**
         * hd_noffcialornot
         */
    @TableField("hd_noffcialornot")
        private Long hdNoffcialornot;

        /**
         * hd_nofficialornot
         */
    @TableField("hd_nofficialornot")
        private Long hdNofficialornot;

        /**
         * hd_cbossornot
         */
    @TableField("hd_cbossornot")
        private String hdCbossornot;

        /**
         * hd_csparedesc1
         */
    @TableField("hd_csparedesc1")
        private String hdCsparedesc1;

        /**
         * hd_csparedesc1ja
         */
    @TableField("hd_csparedesc1ja")
        private String hdCsparedesc1ja;

        /**
         * hd_csparedesc1en
         */
    @TableField("hd_csparedesc1en")
        private String hdCsparedesc1en;

        /**
         * hd_csparedesc1ch
         */
    @TableField("hd_csparedesc1ch")
        private String hdCsparedesc1ch;

        /**
         * hd_csparedesc101
         */
    @TableField("hd_csparedesc101")
        private String hdCsparedesc101;

        /**
         * hd_csparedesc102
         */
    @TableField("hd_csparedesc102")
        private String hdCsparedesc102;

        /**
         * hd_csparedesc2
         */
    @TableField("hd_csparedesc2")
        private String hdCsparedesc2;

        /**
         * hd_csparedesc2ja
         */
    @TableField("hd_csparedesc2ja")
        private String hdCsparedesc2ja;

        /**
         * hd_csparedesc2en
         */
    @TableField("hd_csparedesc2en")
        private String hdCsparedesc2en;

        /**
         * hd_csparedesc2ch
         */
    @TableField("hd_csparedesc2ch")
        private String hdCsparedesc2ch;

        /**
         * hd_csparedesc201
         */
    @TableField("hd_csparedesc201")
        private String hdCsparedesc201;

        /**
         * hd_csparedesc202
         */
    @TableField("hd_csparedesc202")
        private String hdCsparedesc202;

        /**
         * hd_csparedesc3
         */
    @TableField("hd_csparedesc3")
        private String hdCsparedesc3;

        /**
         * hd_csparedesc3ja
         */
    @TableField("hd_csparedesc3ja")
        private String hdCsparedesc3ja;

        /**
         * hd_csparedesc3en
         */
    @TableField("hd_csparedesc3en")
        private String hdCsparedesc3en;

        /**
         * hd_csparedesc3ch
         */
    @TableField("hd_csparedesc3ch")
        private String hdCsparedesc3ch;

        /**
         * hd_csparedesc301
         */
    @TableField("hd_csparedesc301")
        private String hdCsparedesc301;

        /**
         * hd_csparedesc302
         */
    @TableField("hd_csparedesc302")
        private String hdCsparedesc302;

        /**
         * hd_nnumber01
         */
    @TableField("hd_nnumber01")
        private Long hdNnumber01;

        /**
         * hd_nnumber02
         */
    @TableField("hd_nnumber02")
        private Long hdNnumber02;

        /**
         * hd_nnumber03
         */
    @TableField("hd_nnumber03")
        private Long hdNnumber03;

        /**
         * hd_cchar01
         */
    @TableField("hd_cchar01")
        private String hdCchar01;

        /**
         * hd_cchar02
         */
    @TableField("hd_cchar02")
        private String hdCchar02;

        /**
         * hd_cchar03
         */
    @TableField("hd_cchar03")
        private String hdCchar03;

        /**
         * hd_ddate01
         */
    @TableField("hd_ddate01")
        private Date hdDdate01;

        /**
         * hd_ddate02
         */
    @TableField("hd_ddate02")
        private Date hdDdate02;

        /**
         * hd_ddate03
         */
    @TableField("hd_ddate03")
        private Date hdDdate03;

        /**
         * hd_ccode01
         */
    @TableField("hd_ccode01")
        private String hdCcode01;

        /**
         * hd_ccodenm01
         */
    @TableField("hd_ccodenm01")
        private String hdCcodenm01;

        /**
         * hd_ccode02
         */
    @TableField("hd_ccode02")
        private String hdCcode02;

        /**
         * hd_ccodenm02
         */
    @TableField("hd_ccodenm02")
        private String hdCcodenm02;

        /**
         * hd_ccode03
         */
    @TableField("hd_ccode03")
        private String hdCcode03;

        /**
         * hd_ccodenm03
         */
    @TableField("hd_ccodenm03")
        private String hdCcodenm03;

        /**
         * versionno
         */
    @TableField("versionno")
        private Long versionno;


        }