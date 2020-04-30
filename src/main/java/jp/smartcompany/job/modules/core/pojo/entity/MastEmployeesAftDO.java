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
 * public.mast_employees_aft
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
@TableName("mast_employees_aft")
public class MastEmployeesAftDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * me_id
         */
    @TableId(value="me_id",type=IdType.INPUT)
        private Long meId;

        /**
         * me_ccustomerid_ck
         */
    @TableField("me_ccustomerid_ck")
        private String meCcustomeridCk;

        /**
         * me_ccompanyid
         */
    @TableField("me_ccompanyid")
        private String meCcompanyid;

        /**
         * me_cemployeeid_ck
         */
    @TableField("me_cemployeeid_ck")
        private String meCemployeeidCk;

        /**
         * me_cuserid
         */
    @TableField("me_cuserid")
        private String meCuserid;

        /**
         * me_dstartdate
         */
    @TableField("me_dstartdate")
        private Date meDstartdate;

        /**
         * me_denddate
         */
    @TableField("me_denddate")
        private Date meDenddate;

        /**
         * me_cmodifieruserid
         */
    @TableField("me_cmodifieruserid")
        private String meCmodifieruserid;

        /**
         * me_dmodifieddate
         */
    @TableField("me_dmodifieddate")
        private Date meDmodifieddate;

        /**
         * me_ckananame
         */
    @TableField("me_ckananame")
        private String meCkananame;

        /**
         * me_ckanjiname
         */
    @TableField("me_ckanjiname")
        private String meCkanjiname;

        /**
         * me_cenglishname
         */
    @TableField("me_cenglishname")
        private String meCenglishname;

        /**
         * me_cemployeenamech
         */
    @TableField("me_cemployeenamech")
        private String meCemployeenamech;

        /**
         * me_cemployeename01
         */
    @TableField("me_cemployeename01")
        private String meCemployeename01;

        /**
         * me_cemployeename02
         */
    @TableField("me_cemployeename02")
        private String meCemployeename02;

        /**
         * me_coldsurnameinkanji
         */
    @TableField("me_coldsurnameinkanji")
        private String meColdsurnameinkanji;

        /**
         * me_coldsurnamekana
         */
    @TableField("me_coldsurnamekana")
        private String meColdsurnamekana;

        /**
         * me_coldsurnameinenglish
         */
    @TableField("me_coldsurnameinenglish")
        private String meColdsurnameinenglish;

        /**
         * me_dchangename
         */
    @TableField("me_dchangename")
        private Date meDchangename;

        /**
         * me_cmail
         */
    @TableField("me_cmail")
        private String meCmail;

        /**
         * me_ddateofbirth
         */
    @TableField("me_ddateofbirth")
        private Date meDdateofbirth;

        /**
         * me_nage
         */
    @TableField("me_nage")
        private Long meNage;

        /**
         * me_nstandardage
         */
    @TableField("me_nstandardage")
        private Long meNstandardage;

        /**
         * me_cgender
         */
    @TableField("me_cgender")
        private String meCgender;

        /**
         * me_cgendernm
         */
    @TableField("me_cgendernm")
        private String meCgendernm;

        /**
         * me_cbloodgroup
         */
    @TableField("me_cbloodgroup")
        private String meCbloodgroup;

        /**
         * me_cbloodgroupnm
         */
    @TableField("me_cbloodgroupnm")
        private String meCbloodgroupnm;

        /**
         * me_cifstillemployedid
         */
    @TableField("me_cifstillemployedid")
        private String meCifstillemployedid;

        /**
         * me_cifstillemployednm
         */
    @TableField("me_cifstillemployednm")
        private String meCifstillemployednm;

        /**
         * me_ddateofemployement
         */
    @TableField("me_ddateofemployement")
        private Date meDdateofemployement;

        /**
         * me_nageatentrance
         */
    @TableField("me_nageatentrance")
        private Long meNageatentrance;

        /**
         * me_nyearofservice
         */
    @TableField("me_nyearofservice")
        private Long meNyearofservice;

        /**
         * me_ctrialemployement
         */
    @TableField("me_ctrialemployement")
        private String meCtrialemployement;

        /**
         * me_ctrialemployementnm
         */
    @TableField("me_ctrialemployementnm")
        private String meCtrialemployementnm;

        /**
         * me_dendoftrialemployment
         */
    @TableField("me_dendoftrialemployment")
        private Date meDendoftrialemployment;

        /**
         * me_ddateofretirement
         */
    @TableField("me_ddateofretirement")
        private Date meDdateofretirement;

        /**
         * me_nageatresignation
         */
    @TableField("me_nageatresignation")
        private Long meNageatresignation;

        /**
         * me_creasonofresignation
         */
    @TableField("me_creasonofresignation")
        private String meCreasonofresignation;

        /**
         * me_creasonofresignationnm
         */
    @TableField("me_creasonofresignationnm")
        private String meCreasonofresignationnm;

        /**
         * me_cremarksofresignation
         */
    @TableField("me_cremarksofresignation")
        private String meCremarksofresignation;

        /**
         * me_ddateofagelimit
         */
    @TableField("me_ddateofagelimit")
        private Date meDdateofagelimit;

        /**
         * me_ddateformanagerialposition
         */
    @TableField("me_ddateformanagerialposition")
        private Date meDdateformanagerialposition;

        /**
         * me_ctypeofemployment
         */
    @TableField("me_ctypeofemployment")
        private String meCtypeofemployment;

        /**
         * me_ctypeofemploymentnm
         */
    @TableField("me_ctypeofemploymentnm")
        private String meCtypeofemploymentnm;

        /**
         * me_ciffreshcandidatid
         */
    @TableField("me_ciffreshcandidatid")
        private String meCiffreshcandidatid;

        /**
         * me_ciffreshcandidatnm
         */
    @TableField("me_ciffreshcandidatnm")
        private String meCiffreshcandidatnm;

        /**
         * me_cmodeofappointment
         */
    @TableField("me_cmodeofappointment")
        private String meCmodeofappointment;

        /**
         * me_cmodeofappointmentnm
         */
    @TableField("me_cmodeofappointmentnm")
        private String meCmodeofappointmentnm;

        /**
         * me_ctypeofservice
         */
    @TableField("me_ctypeofservice")
        private String meCtypeofservice;

        /**
         * me_ctypeofservicenm
         */
    @TableField("me_ctypeofservicenm")
        private String meCtypeofservicenm;

        /**
         * me_ctypeofhabitation
         */
    @TableField("me_ctypeofhabitation")
        private String meCtypeofhabitation;

        /**
         * me_ctypeofhabitationnm
         */
    @TableField("me_ctypeofhabitationnm")
        private String meCtypeofhabitationnm;

        /**
         * me_cmaritalstatus
         */
    @TableField("me_cmaritalstatus")
        private String meCmaritalstatus;

        /**
         * me_cmaritalstatusnm
         */
    @TableField("me_cmaritalstatusnm")
        private String meCmaritalstatusnm;

        /**
         * me_cifcostcalcid
         */
    @TableField("me_cifcostcalcid")
        private String meCifcostcalcid;

        /**
         * me_cifcostcalcnm
         */
    @TableField("me_cifcostcalcnm")
        private String meCifcostcalcnm;

        /**
         * me_ctypeofpayment
         */
    @TableField("me_ctypeofpayment")
        private String meCtypeofpayment;

        /**
         * me_ctypeofpaymentnm
         */
    @TableField("me_ctypeofpaymentnm")
        private String meCtypeofpaymentnm;

        /**
         * me_cnationality
         */
    @TableField("me_cnationality")
        private String meCnationality;

        /**
         * me_cnationalitynm
         */
    @TableField("me_cnationalitynm")
        private String meCnationalitynm;

        /**
         * me_cdomicileoforigin
         */
    @TableField("me_cdomicileoforigin")
        private String meCdomicileoforigin;

        /**
         * me_cdomicileoforiginnm
         */
    @TableField("me_cdomicileoforiginnm")
        private String meCdomicileoforiginnm;

        /**
         * me_csparedesc1
         */
    @TableField("me_csparedesc1")
        private String meCsparedesc1;

        /**
         * me_csparedesc1ja
         */
    @TableField("me_csparedesc1ja")
        private String meCsparedesc1ja;

        /**
         * me_csparedesc1en
         */
    @TableField("me_csparedesc1en")
        private String meCsparedesc1en;

        /**
         * me_csparedesc1ch
         */
    @TableField("me_csparedesc1ch")
        private String meCsparedesc1ch;

        /**
         * me_csparedesc101
         */
    @TableField("me_csparedesc101")
        private String meCsparedesc101;

        /**
         * me_csparedesc102
         */
    @TableField("me_csparedesc102")
        private String meCsparedesc102;

        /**
         * me_csparedesc2
         */
    @TableField("me_csparedesc2")
        private String meCsparedesc2;

        /**
         * me_csparedesc2ja
         */
    @TableField("me_csparedesc2ja")
        private String meCsparedesc2ja;

        /**
         * me_csparedesc2en
         */
    @TableField("me_csparedesc2en")
        private String meCsparedesc2en;

        /**
         * me_csparedesc2ch
         */
    @TableField("me_csparedesc2ch")
        private String meCsparedesc2ch;

        /**
         * me_csparedesc201
         */
    @TableField("me_csparedesc201")
        private String meCsparedesc201;

        /**
         * me_csparedesc202
         */
    @TableField("me_csparedesc202")
        private String meCsparedesc202;

        /**
         * me_csparedesc3
         */
    @TableField("me_csparedesc3")
        private String meCsparedesc3;

        /**
         * me_csparedesc3ja
         */
    @TableField("me_csparedesc3ja")
        private String meCsparedesc3ja;

        /**
         * me_csparedesc3en
         */
    @TableField("me_csparedesc3en")
        private String meCsparedesc3en;

        /**
         * me_csparedesc3ch
         */
    @TableField("me_csparedesc3ch")
        private String meCsparedesc3ch;

        /**
         * me_csparedesc301
         */
    @TableField("me_csparedesc301")
        private String meCsparedesc301;

        /**
         * me_csparedesc302
         */
    @TableField("me_csparedesc302")
        private String meCsparedesc302;

        /**
         * me_csparedesc4
         */
    @TableField("me_csparedesc4")
        private String meCsparedesc4;

        /**
         * me_csparedesc4ja
         */
    @TableField("me_csparedesc4ja")
        private String meCsparedesc4ja;

        /**
         * me_csparedesc4en
         */
    @TableField("me_csparedesc4en")
        private String meCsparedesc4en;

        /**
         * me_csparedesc4ch
         */
    @TableField("me_csparedesc4ch")
        private String meCsparedesc4ch;

        /**
         * me_csparedesc401
         */
    @TableField("me_csparedesc401")
        private String meCsparedesc401;

        /**
         * me_csparedesc402
         */
    @TableField("me_csparedesc402")
        private String meCsparedesc402;

        /**
         * me_csparedesc5
         */
    @TableField("me_csparedesc5")
        private String meCsparedesc5;

        /**
         * me_csparedesc5ja
         */
    @TableField("me_csparedesc5ja")
        private String meCsparedesc5ja;

        /**
         * me_csparedesc5en
         */
    @TableField("me_csparedesc5en")
        private String meCsparedesc5en;

        /**
         * me_csparedesc5ch
         */
    @TableField("me_csparedesc5ch")
        private String meCsparedesc5ch;

        /**
         * me_csparedesc501
         */
    @TableField("me_csparedesc501")
        private String meCsparedesc501;

        /**
         * me_csparedesc502
         */
    @TableField("me_csparedesc502")
        private String meCsparedesc502;

        /**
         * me_nnumber01
         */
    @TableField("me_nnumber01")
        private Long meNnumber01;

        /**
         * me_nnumber02
         */
    @TableField("me_nnumber02")
        private Long meNnumber02;

        /**
         * me_nnumber03
         */
    @TableField("me_nnumber03")
        private Long meNnumber03;

        /**
         * me_nnumber04
         */
    @TableField("me_nnumber04")
        private Long meNnumber04;

        /**
         * me_nnumber05
         */
    @TableField("me_nnumber05")
        private Long meNnumber05;

        /**
         * me_cchar01
         */
    @TableField("me_cchar01")
        private String meCchar01;

        /**
         * me_cchar02
         */
    @TableField("me_cchar02")
        private String meCchar02;

        /**
         * me_cchar03
         */
    @TableField("me_cchar03")
        private String meCchar03;

        /**
         * me_cchar04
         */
    @TableField("me_cchar04")
        private String meCchar04;

        /**
         * me_cchar05
         */
    @TableField("me_cchar05")
        private String meCchar05;

        /**
         * me_ddate01
         */
    @TableField("me_ddate01")
        private Date meDdate01;

        /**
         * me_ddate02
         */
    @TableField("me_ddate02")
        private Date meDdate02;

        /**
         * me_ddate03
         */
    @TableField("me_ddate03")
        private Date meDdate03;

        /**
         * me_ddate04
         */
    @TableField("me_ddate04")
        private Date meDdate04;

        /**
         * me_ddate05
         */
    @TableField("me_ddate05")
        private Date meDdate05;

        /**
         * me_ccode01
         */
    @TableField("me_ccode01")
        private String meCcode01;

        /**
         * me_ccodenm01
         */
    @TableField("me_ccodenm01")
        private String meCcodenm01;

        /**
         * me_ccode02
         */
    @TableField("me_ccode02")
        private String meCcode02;

        /**
         * me_ccodenm02
         */
    @TableField("me_ccodenm02")
        private String meCcodenm02;

        /**
         * me_ccode03
         */
    @TableField("me_ccode03")
        private String meCcode03;

        /**
         * me_ccodenm03
         */
    @TableField("me_ccodenm03")
        private String meCcodenm03;

        /**
         * me_ccode04
         */
    @TableField("me_ccode04")
        private String meCcode04;

        /**
         * me_ccodenm04
         */
    @TableField("me_ccodenm04")
        private String meCcodenm04;

        /**
         * me_ccode05
         */
    @TableField("me_ccode05")
        private String meCcode05;

        /**
         * me_ccodenm05
         */
    @TableField("me_ccodenm05")
        private String meCcodenm05;

        /**
         * versionno
         */
    @TableField("versionno")
        private Long versionno;


        }