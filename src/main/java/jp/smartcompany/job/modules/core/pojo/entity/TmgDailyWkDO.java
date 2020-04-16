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
 * public.tmg_daily_wk
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
@TableName("tmg_daily_wk")
public class TmgDailyWkDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * tda_ccustomerid
         */
    @TableField("tda_ccustomerid")
        private String tdaCcustomerid;

        /**
         * tda_ccompanyid
         */
    @TableField("tda_ccompanyid")
        private String tdaCcompanyid;

        /**
         * tda_cemployeeid
         */
    @TableField("tda_cemployeeid")
        private String tdaCemployeeid;

        /**
         * tda_dstartdate
         */
    @TableField("tda_dstartdate")
        private Date tdaDstartdate;

        /**
         * tda_denddate
         */
    @TableField("tda_denddate")
        private Date tdaDenddate;

        /**
         * tda_cmodifieruserid
         */
    @TableField("tda_cmodifieruserid")
        private String tdaCmodifieruserid;

        /**
         * tda_dmodifieddate
         */
    @TableField("tda_dmodifieddate")
        private Date tdaDmodifieddate;

        /**
         * tda_cmodifierprogramid
         */
    @TableField("tda_cmodifierprogramid")
        private String tdaCmodifierprogramid;

        /**
         * tda_nyyyy
         */
    @TableField("tda_nyyyy")
        private Long tdaNyyyy;

        /**
         * tda_dyyyymm
         */
    @TableField("tda_dyyyymm")
        private Date tdaDyyyymm;

        /**
         * tda_dyyyymmdd
         */
    @TableField("tda_dyyyymmdd")
        private Date tdaDyyyymmdd;

        /**
         * tda_cstatusflg
         */
    @TableField("tda_cstatusflg")
        private String tdaCstatusflg;

        /**
         * tda_cntfstatusflg
         */
    @TableField("tda_cntfstatusflg")
        private String tdaCntfstatusflg;

        /**
         * tda_cerrcode
         */
    @TableField("tda_cerrcode")
        private String tdaCerrcode;

        /**
         * tda_cerrmsg
         */
    @TableField("tda_cerrmsg")
        private String tdaCerrmsg;

        /**
         * tda_nopen_tp
         */
    @TableField("tda_nopen_tp")
        private Long tdaNopenTp;

        /**
         * tda_nclose_tp
         */
    @TableField("tda_nclose_tp")
        private Long tdaNcloseTp;

        /**
         * tda_cholflg
         */
    @TableField("tda_cholflg")
        private String tdaCholflg;

        /**
         * tda_cworkingid_p
         */
    @TableField("tda_cworkingid_p")
        private String tdaCworkingidP;

        /**
         * tda_cworkingname_p
         */
    @TableField("tda_cworkingname_p")
        private String tdaCworkingnameP;

        /**
         * tda_nopen_p
         */
    @TableField("tda_nopen_p")
        private Long tdaNopenP;

        /**
         * tda_nclose_p
         */
    @TableField("tda_nclose_p")
        private Long tdaNcloseP;

        /**
         * tda_nrestopen_p
         */
    @TableField("tda_nrestopen_p")
        private Long tdaNrestopenP;

        /**
         * tda_nrestclose_p
         */
    @TableField("tda_nrestclose_p")
        private Long tdaNrestcloseP;

        /**
         * tda_cmodifieruserid_p
         */
    @TableField("tda_cmodifieruserid_p")
        private String tdaCmodifieruseridP;

        /**
         * tda_dmodifieddate_p
         */
    @TableField("tda_dmodifieddate_p")
        private Date tdaDmodifieddateP;

        /**
         * tda_nlock_p
         */
    @TableField("tda_nlock_p")
        private Long tdaNlockP;

        /**
         * tda_nrest45_p
         */
    @TableField("tda_nrest45_p")
        private Long tdaNrest45P;

        /**
         * tda_nopen_n
         */
    @TableField("tda_nopen_n")
        private Long tdaNopenN;

        /**
         * tda_nclose_n
         */
    @TableField("tda_nclose_n")
        private Long tdaNcloseN;

        /**
         * tda_nrestopen_n
         */
    @TableField("tda_nrestopen_n")
        private Long tdaNrestopenN;

        /**
         * tda_nrestclose_n
         */
    @TableField("tda_nrestclose_n")
        private Long tdaNrestcloseN;

        /**
         * tda_cmodifieruserid_n
         */
    @TableField("tda_cmodifieruserid_n")
        private String tdaCmodifieruseridN;

        /**
         * tda_dmodifieddate_n
         */
    @TableField("tda_dmodifieddate_n")
        private Date tdaDmodifieddateN;

        /**
         * tda_nopen_o
         */
    @TableField("tda_nopen_o")
        private Long tdaNopenO;

        /**
         * tda_nclose_o
         */
    @TableField("tda_nclose_o")
        private Long tdaNcloseO;

        /**
         * tda_ccomment_o
         */
    @TableField("tda_ccomment_o")
        private String tdaCcommentO;

        /**
         * tda_cmodifieruserid_o
         */
    @TableField("tda_cmodifieruserid_o")
        private String tdaCmodifieruseridO;

        /**
         * tda_dmodifieddate_o
         */
    @TableField("tda_dmodifieddate_o")
        private Date tdaDmodifieddateO;

        /**
         * tda_cworkingid_r
         */
    @TableField("tda_cworkingid_r")
        private String tdaCworkingidR;

        /**
         * tda_cworkingname_r
         */
    @TableField("tda_cworkingname_r")
        private String tdaCworkingnameR;

        /**
         * tda_nopen_r
         */
    @TableField("tda_nopen_r")
        private Long tdaNopenR;

        /**
         * tda_nclose_r
         */
    @TableField("tda_nclose_r")
        private Long tdaNcloseR;

        /**
         * tda_nrestopen_r
         */
    @TableField("tda_nrestopen_r")
        private Long tdaNrestopenR;

        /**
         * tda_nrestclose_r
         */
    @TableField("tda_nrestclose_r")
        private Long tdaNrestcloseR;

        /**
         * tda_cowncomment_r
         */
    @TableField("tda_cowncomment_r")
        private String tdaCowncommentR;

        /**
         * tda_cbosscomment_r
         */
    @TableField("tda_cbosscomment_r")
        private String tdaCbosscommentR;

        /**
         * tda_cmodifieruserid_r
         */
    @TableField("tda_cmodifieruserid_r")
        private String tdaCmodifieruseridR;

        /**
         * tda_dmodifieddate_r
         */
    @TableField("tda_dmodifieddate_r")
        private Date tdaDmodifieddateR;

        /**
         * tda_cmessage
         */
    @TableField("tda_cmessage")
        private String tdaCmessage;

        /**
         * tda_ncalc01
         */
    @TableField("tda_ncalc01")
        private Long tdaNcalc01;

        /**
         * tda_ncalc02
         */
    @TableField("tda_ncalc02")
        private Long tdaNcalc02;

        /**
         * tda_ncalc03
         */
    @TableField("tda_ncalc03")
        private Long tdaNcalc03;

        /**
         * tda_ncalc04
         */
    @TableField("tda_ncalc04")
        private Long tdaNcalc04;

        /**
         * tda_ncalc05
         */
    @TableField("tda_ncalc05")
        private Long tdaNcalc05;

        /**
         * tda_ncalc06
         */
    @TableField("tda_ncalc06")
        private Long tdaNcalc06;

        /**
         * tda_ncalc07
         */
    @TableField("tda_ncalc07")
        private Long tdaNcalc07;

        /**
         * tda_ncalc08
         */
    @TableField("tda_ncalc08")
        private Long tdaNcalc08;

        /**
         * tda_ncalc09
         */
    @TableField("tda_ncalc09")
        private Long tdaNcalc09;

        /**
         * tda_ncalc10
         */
    @TableField("tda_ncalc10")
        private Long tdaNcalc10;

        /**
         * tda_ncalc11
         */
    @TableField("tda_ncalc11")
        private Long tdaNcalc11;

        /**
         * tda_ncalc12
         */
    @TableField("tda_ncalc12")
        private Long tdaNcalc12;

        /**
         * tda_ncalc13
         */
    @TableField("tda_ncalc13")
        private Long tdaNcalc13;

        /**
         * tda_ncalc14
         */
    @TableField("tda_ncalc14")
        private Long tdaNcalc14;

        /**
         * tda_ncalc15
         */
    @TableField("tda_ncalc15")
        private Long tdaNcalc15;

        /**
         * tda_ncalc16
         */
    @TableField("tda_ncalc16")
        private Long tdaNcalc16;

        /**
         * tda_ncalc17
         */
    @TableField("tda_ncalc17")
        private Long tdaNcalc17;

        /**
         * tda_ncalc18
         */
    @TableField("tda_ncalc18")
        private Long tdaNcalc18;

        /**
         * tda_ncalc19
         */
    @TableField("tda_ncalc19")
        private Long tdaNcalc19;

        /**
         * tda_ncalc20
         */
    @TableField("tda_ncalc20")
        private Long tdaNcalc20;

        /**
         * tda_ncalc21
         */
    @TableField("tda_ncalc21")
        private Long tdaNcalc21;

        /**
         * tda_ncalc22
         */
    @TableField("tda_ncalc22")
        private Long tdaNcalc22;

        /**
         * tda_ncalc23
         */
    @TableField("tda_ncalc23")
        private Long tdaNcalc23;

        /**
         * tda_ncalc24
         */
    @TableField("tda_ncalc24")
        private Long tdaNcalc24;

        /**
         * tda_ncalc25
         */
    @TableField("tda_ncalc25")
        private Long tdaNcalc25;

        /**
         * tda_ncalc26
         */
    @TableField("tda_ncalc26")
        private Long tdaNcalc26;

        /**
         * tda_ncalc27
         */
    @TableField("tda_ncalc27")
        private Long tdaNcalc27;

        /**
         * tda_ncalc28
         */
    @TableField("tda_ncalc28")
        private Long tdaNcalc28;

        /**
         * tda_ncalc29
         */
    @TableField("tda_ncalc29")
        private Long tdaNcalc29;

        /**
         * tda_ncalc30
         */
    @TableField("tda_ncalc30")
        private Long tdaNcalc30;

        /**
         * tda_cbusinesstripid_p
         */
    @TableField("tda_cbusinesstripid_p")
        private String tdaCbusinesstripidP;

        /**
         * tda_cbusinesstripid_r
         */
    @TableField("tda_cbusinesstripid_r")
        private String tdaCbusinesstripidR;

        /**
         * tda_ccomment_p
         */
    @TableField("tda_ccomment_p")
        private String tdaCcommentP;

        /**
         * tda_cpatternid
         */
    @TableField("tda_cpatternid")
        private String tdaCpatternid;

        /**
         * tda_ncalc31
         */
    @TableField("tda_ncalc31")
        private Long tdaNcalc31;

        /**
         * tda_ncalc32
         */
    @TableField("tda_ncalc32")
        private Long tdaNcalc32;

        /**
         * tda_ncalc33
         */
    @TableField("tda_ncalc33")
        private Long tdaNcalc33;

        /**
         * tda_ncalc34
         */
    @TableField("tda_ncalc34")
        private Long tdaNcalc34;

        /**
         * tda_ncalc35
         */
    @TableField("tda_ncalc35")
        private Long tdaNcalc35;

        /**
         * tda_ncalc36
         */
    @TableField("tda_ncalc36")
        private Long tdaNcalc36;

        /**
         * tda_ncalc37
         */
    @TableField("tda_ncalc37")
        private Long tdaNcalc37;

        /**
         * tda_ncalc38
         */
    @TableField("tda_ncalc38")
        private Long tdaNcalc38;

        /**
         * tda_ncalc39
         */
    @TableField("tda_ncalc39")
        private Long tdaNcalc39;

        /**
         * tda_ncalc40
         */
    @TableField("tda_ncalc40")
        private Long tdaNcalc40;

        /**
         * tda_ncalc41
         */
    @TableField("tda_ncalc41")
        private Long tdaNcalc41;

        /**
         * tda_ncalc42
         */
    @TableField("tda_ncalc42")
        private Long tdaNcalc42;

        /**
         * tda_ncalc43
         */
    @TableField("tda_ncalc43")
        private Long tdaNcalc43;

        /**
         * tda_ncalc44
         */
    @TableField("tda_ncalc44")
        private Long tdaNcalc44;

        /**
         * tda_ncalc45
         */
    @TableField("tda_ncalc45")
        private Long tdaNcalc45;

        /**
         * tda_ncalc46
         */
    @TableField("tda_ncalc46")
        private Long tdaNcalc46;

        /**
         * tda_ncalc47
         */
    @TableField("tda_ncalc47")
        private Long tdaNcalc47;

        /**
         * tda_ncalc48
         */
    @TableField("tda_ncalc48")
        private Long tdaNcalc48;

        /**
         * tda_ncalc49
         */
    @TableField("tda_ncalc49")
        private Long tdaNcalc49;

        /**
         * tda_ncalc50
         */
    @TableField("tda_ncalc50")
        private Long tdaNcalc50;

        /**
         * tda_ncalc51
         */
    @TableField("tda_ncalc51")
        private Long tdaNcalc51;

        /**
         * tda_ncalc52
         */
    @TableField("tda_ncalc52")
        private Long tdaNcalc52;

        /**
         * tda_ncalc53
         */
    @TableField("tda_ncalc53")
        private Long tdaNcalc53;

        /**
         * tda_ncalc54
         */
    @TableField("tda_ncalc54")
        private Long tdaNcalc54;

        /**
         * tda_ncalc55
         */
    @TableField("tda_ncalc55")
        private Long tdaNcalc55;

        /**
         * tda_ncalc56
         */
    @TableField("tda_ncalc56")
        private Long tdaNcalc56;

        /**
         * tda_ncalc57
         */
    @TableField("tda_ncalc57")
        private Long tdaNcalc57;

        /**
         * tda_ncalc58
         */
    @TableField("tda_ncalc58")
        private Long tdaNcalc58;

        /**
         * tda_ncalc59
         */
    @TableField("tda_ncalc59")
        private Long tdaNcalc59;

        /**
         * tda_ncalc60
         */
    @TableField("tda_ncalc60")
        private Long tdaNcalc60;

        /**
         * tda_ncalc61
         */
    @TableField("tda_ncalc61")
        private Long tdaNcalc61;

        /**
         * tda_ncalc62
         */
    @TableField("tda_ncalc62")
        private Long tdaNcalc62;

        /**
         * tda_ncalc63
         */
    @TableField("tda_ncalc63")
        private Long tdaNcalc63;

        /**
         * tda_ncalc64
         */
    @TableField("tda_ncalc64")
        private Long tdaNcalc64;

        /**
         * tda_ncalc65
         */
    @TableField("tda_ncalc65")
        private Long tdaNcalc65;

        /**
         * tda_ncalc66
         */
    @TableField("tda_ncalc66")
        private Long tdaNcalc66;

        /**
         * tda_ncalc67
         */
    @TableField("tda_ncalc67")
        private Long tdaNcalc67;

        /**
         * tda_ncalc68
         */
    @TableField("tda_ncalc68")
        private Long tdaNcalc68;

        /**
         * tda_ncalc69
         */
    @TableField("tda_ncalc69")
        private Long tdaNcalc69;

        /**
         * tda_ncalc70
         */
    @TableField("tda_ncalc70")
        private Long tdaNcalc70;

        /**
         * tda_ncalc71
         */
    @TableField("tda_ncalc71")
        private Long tdaNcalc71;

        /**
         * tda_ncalc72
         */
    @TableField("tda_ncalc72")
        private Long tdaNcalc72;

        /**
         * tda_ncalc73
         */
    @TableField("tda_ncalc73")
        private Long tdaNcalc73;

        /**
         * tda_ncalc74
         */
    @TableField("tda_ncalc74")
        private Long tdaNcalc74;

        /**
         * tda_ncalc75
         */
    @TableField("tda_ncalc75")
        private Long tdaNcalc75;

        /**
         * tda_ncalc76
         */
    @TableField("tda_ncalc76")
        private Long tdaNcalc76;

        /**
         * tda_ncalc77
         */
    @TableField("tda_ncalc77")
        private Long tdaNcalc77;

        /**
         * tda_ncalc78
         */
    @TableField("tda_ncalc78")
        private Long tdaNcalc78;

        /**
         * tda_ncalc79
         */
    @TableField("tda_ncalc79")
        private Long tdaNcalc79;

        /**
         * tda_ncalc80
         */
    @TableField("tda_ncalc80")
        private Long tdaNcalc80;

        /**
         * tda_ncalc81
         */
    @TableField("tda_ncalc81")
        private Long tdaNcalc81;

        /**
         * tda_ncalc82
         */
    @TableField("tda_ncalc82")
        private Long tdaNcalc82;

        /**
         * tda_ncalc83
         */
    @TableField("tda_ncalc83")
        private Long tdaNcalc83;

        /**
         * tda_ncalc84
         */
    @TableField("tda_ncalc84")
        private Long tdaNcalc84;

        /**
         * tda_ncalc85
         */
    @TableField("tda_ncalc85")
        private Long tdaNcalc85;

        /**
         * tda_ncalc86
         */
    @TableField("tda_ncalc86")
        private Long tdaNcalc86;

        /**
         * tda_ncalc87
         */
    @TableField("tda_ncalc87")
        private Long tdaNcalc87;

        /**
         * tda_ncalc88
         */
    @TableField("tda_ncalc88")
        private Long tdaNcalc88;

        /**
         * tda_ncalc89
         */
    @TableField("tda_ncalc89")
        private Long tdaNcalc89;

        /**
         * tda_ncalc90
         */
    @TableField("tda_ncalc90")
        private Long tdaNcalc90;

        /**
         * tda_ncalc91
         */
    @TableField("tda_ncalc91")
        private Long tdaNcalc91;

        /**
         * tda_ncalc92
         */
    @TableField("tda_ncalc92")
        private Long tdaNcalc92;

        /**
         * tda_ncalc93
         */
    @TableField("tda_ncalc93")
        private Long tdaNcalc93;

        /**
         * tda_ncalc94
         */
    @TableField("tda_ncalc94")
        private Long tdaNcalc94;

        /**
         * tda_ncalc95
         */
    @TableField("tda_ncalc95")
        private Long tdaNcalc95;

        /**
         * tda_ncalc96
         */
    @TableField("tda_ncalc96")
        private Long tdaNcalc96;

        /**
         * tda_ncalc97
         */
    @TableField("tda_ncalc97")
        private Long tdaNcalc97;

        /**
         * tda_ncalc98
         */
    @TableField("tda_ncalc98")
        private Long tdaNcalc98;

        /**
         * tda_ncalc99
         */
    @TableField("tda_ncalc99")
        private Long tdaNcalc99;

        /**
         * tda_ncalc100
         */
    @TableField("tda_ncalc100")
        private Long tdaNcalc100;

        /**
         * tda_ncalc101
         */
    @TableField("tda_ncalc101")
        private Long tdaNcalc101;

        /**
         * tda_ncalc102
         */
    @TableField("tda_ncalc102")
        private Long tdaNcalc102;

        /**
         * tda_ncalc103
         */
    @TableField("tda_ncalc103")
        private Long tdaNcalc103;

        /**
         * tda_ncalc104
         */
    @TableField("tda_ncalc104")
        private Long tdaNcalc104;

        /**
         * tda_ncalc105
         */
    @TableField("tda_ncalc105")
        private Long tdaNcalc105;

        /**
         * tda_ncalc106
         */
    @TableField("tda_ncalc106")
        private Long tdaNcalc106;

        /**
         * tda_ncalc107
         */
    @TableField("tda_ncalc107")
        private Long tdaNcalc107;

        /**
         * tda_ncalc108
         */
    @TableField("tda_ncalc108")
        private Long tdaNcalc108;

        /**
         * tda_ncalc109
         */
    @TableField("tda_ncalc109")
        private Long tdaNcalc109;

        /**
         * tda_ncalc110
         */
    @TableField("tda_ncalc110")
        private Long tdaNcalc110;

        /**
         * tda_ncalc111
         */
    @TableField("tda_ncalc111")
        private Long tdaNcalc111;

        /**
         * tda_ncalc112
         */
    @TableField("tda_ncalc112")
        private Long tdaNcalc112;

        /**
         * tda_ncalc113
         */
    @TableField("tda_ncalc113")
        private Long tdaNcalc113;

        /**
         * tda_ncalc114
         */
    @TableField("tda_ncalc114")
        private Long tdaNcalc114;

        /**
         * tda_ncalc115
         */
    @TableField("tda_ncalc115")
        private Long tdaNcalc115;

        /**
         * tda_ncalc116
         */
    @TableField("tda_ncalc116")
        private Long tdaNcalc116;

        /**
         * tda_ncalc117
         */
    @TableField("tda_ncalc117")
        private Long tdaNcalc117;

        /**
         * tda_ncalc118
         */
    @TableField("tda_ncalc118")
        private Long tdaNcalc118;

        /**
         * tda_ncalc119
         */
    @TableField("tda_ncalc119")
        private Long tdaNcalc119;

        /**
         * tda_ncalc120
         */
    @TableField("tda_ncalc120")
        private Long tdaNcalc120;

        /**
         * tda_ncalc121
         */
    @TableField("tda_ncalc121")
        private Long tdaNcalc121;

        /**
         * tda_ncalc122
         */
    @TableField("tda_ncalc122")
        private Long tdaNcalc122;

        /**
         * tda_ncalc123
         */
    @TableField("tda_ncalc123")
        private Long tdaNcalc123;

        /**
         * tda_ncalc124
         */
    @TableField("tda_ncalc124")
        private Long tdaNcalc124;

        /**
         * tda_ncalc125
         */
    @TableField("tda_ncalc125")
        private Long tdaNcalc125;

        /**
         * tda_ncalc126
         */
    @TableField("tda_ncalc126")
        private Long tdaNcalc126;

        /**
         * tda_ncalc127
         */
    @TableField("tda_ncalc127")
        private Long tdaNcalc127;

        /**
         * tda_ncalc128
         */
    @TableField("tda_ncalc128")
        private Long tdaNcalc128;

        /**
         * tda_ncalc129
         */
    @TableField("tda_ncalc129")
        private Long tdaNcalc129;

        /**
         * tda_ncalc130
         */
    @TableField("tda_ncalc130")
        private Long tdaNcalc130;

        /**
         * tda_ncalc131
         */
    @TableField("tda_ncalc131")
        private Long tdaNcalc131;

        /**
         * tda_ncalc132
         */
    @TableField("tda_ncalc132")
        private Long tdaNcalc132;

        /**
         * tda_ncalc133
         */
    @TableField("tda_ncalc133")
        private Long tdaNcalc133;

        /**
         * tda_ncalc134
         */
    @TableField("tda_ncalc134")
        private Long tdaNcalc134;

        /**
         * tda_ncalc135
         */
    @TableField("tda_ncalc135")
        private Long tdaNcalc135;

        /**
         * tda_ncalc136
         */
    @TableField("tda_ncalc136")
        private Long tdaNcalc136;

        /**
         * tda_ncalc137
         */
    @TableField("tda_ncalc137")
        private Long tdaNcalc137;

        /**
         * tda_ncalc138
         */
    @TableField("tda_ncalc138")
        private Long tdaNcalc138;

        /**
         * tda_ncalc139
         */
    @TableField("tda_ncalc139")
        private Long tdaNcalc139;

        /**
         * tda_ncalc140
         */
    @TableField("tda_ncalc140")
        private Long tdaNcalc140;

        /**
         * tda_ncalc141
         */
    @TableField("tda_ncalc141")
        private Long tdaNcalc141;

        /**
         * tda_ncalc142
         */
    @TableField("tda_ncalc142")
        private Long tdaNcalc142;

        /**
         * tda_ncalc143
         */
    @TableField("tda_ncalc143")
        private Long tdaNcalc143;

        /**
         * tda_ncalc144
         */
    @TableField("tda_ncalc144")
        private Long tdaNcalc144;

        /**
         * tda_ncalc145
         */
    @TableField("tda_ncalc145")
        private Long tdaNcalc145;

        /**
         * tda_ncalc146
         */
    @TableField("tda_ncalc146")
        private Long tdaNcalc146;

        /**
         * tda_ncalc147
         */
    @TableField("tda_ncalc147")
        private Long tdaNcalc147;

        /**
         * tda_ncalc148
         */
    @TableField("tda_ncalc148")
        private Long tdaNcalc148;

        /**
         * tda_ncalc149
         */
    @TableField("tda_ncalc149")
        private Long tdaNcalc149;

        /**
         * tda_ncalc150
         */
    @TableField("tda_ncalc150")
        private Long tdaNcalc150;

        /**
         * tda_crefntfno
         */
    @TableField("tda_crefntfno")
        private String tdaCrefntfno;

        /**
         * tda_cworkingid_p_pre
         */
    @TableField("tda_cworkingid_p_pre")
        private String tdaCworkingidPPre;

        /**
         * tda_dsubstituted
         */
    @TableField("tda_dsubstituted")
        private Date tdaDsubstituted;

        /**
         * tda_nopen_f
         */
    @TableField("tda_nopen_f")
        private Long tdaNopenF;

        /**
         * tda_nclose_f
         */
    @TableField("tda_nclose_f")
        private Long tdaNcloseF;


        }