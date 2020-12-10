package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 基本情報
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
@TableName("mast_employees")
public class MastEmployeesDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "me_id", type = IdType.AUTO)
                private Long meId;

        /**
         * 顧客コード
         */
    @TableField("me_ccustomerid_ck")
        private String meCcustomeridCk;

        /**
         * 法人コード
         */
    @TableField("me_ccompanyid")
        private String meCcompanyid;

        /**
         * 職員番号
         */
    @TableField("me_cemployeeid_ck")
        private String meCemployeeidCk;

        /**
         * ユーザid
         */
    @TableField("me_cuserid")
        private String meCuserid;

        /**
         * データ開始日
         */
    @TableField("me_dstartdate")
        private Date meDstartdate;

        /**
         * データ終了日
         */
    @TableField("me_denddate")
        private Date meDenddate;

        /**
         * 最終更新者
         */
    @TableField("me_cmodifieruserid")
        private String meCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("me_dmodifieddate")
        private Date meDmodifieddate;

        /**
         * カナ氏名
         */
    @TableField("me_ckananame")
        private String meCkananame;

        /**
         * 漢字氏名
         */
    @TableField("me_ckanjiname")
        private String meCkanjiname;

        /**
         * 英語氏名
         */
    @TableField("me_cenglishname")
        private String meCenglishname;

        /**
         * 中国語氏名
         */
    @TableField("me_cemployeenamech")
        private String meCemployeenamech;

        /**
         * 予備1氏名
         */
    @TableField("me_cemployeename01")
        private String meCemployeename01;

        /**
         * 予備2氏名
         */
    @TableField("me_cemployeename02")
        private String meCemployeename02;

        /**
         * 旧姓（通称）
         */
    @TableField("me_coldsurnameinkanji")
        private String meColdsurnameinkanji;

        /**
         * 旧姓(カナ)
         */
    @TableField("me_coldsurnamekana")
        private String meColdsurnamekana;

        /**
         * 旧姓（英語）
         */
    @TableField("me_coldsurnameinenglish")
        private String meColdsurnameinenglish;

        /**
         * 改姓年月日
         */
    @TableField("me_dchangename")
        private Date meDchangename;

        /**
         * ﾒｰﾙｱﾄﾞﾚｽ
         */
    @TableField("me_cmail")
        private String meCmail;

        /**
         * 生年月日
         */
    @TableField("me_ddateofbirth")
        private Date meDdateofbirth;

        /**
         * 年齢
         */
    @TableField("me_nage")
        private Long meNage;

        /**
         * 標準年齢
         */
    @TableField("me_nstandardage")
        private Long meNstandardage;

        /**
         * 性別
         */
    @TableField("me_cgender")
        private String meCgender;

        /**
         * 性別コード
         */
    @TableField("me_cgendernm")
        private String meCgendernm;

        /**
         * 血液型
         */
    @TableField("me_cbloodgroup")
        private String meCbloodgroup;

        /**
         * 血液型コード
         */
    @TableField("me_cbloodgroupnm")
        private String meCbloodgroupnm;

        /**
         * 在職区分
         */
    @TableField("me_cifstillemployedid")
        private String meCifstillemployedid;

        /**
         * 在職区分コード
         */
    @TableField("me_cifstillemployednm")
        private String meCifstillemployednm;

        /**
         * 入社年月日
         */
    @TableField("me_ddateofemployement")
        private Date meDdateofemployement;

        /**
         * 入社時年齢
         */
    @TableField("me_nageatentrance")
        private Long meNageatentrance;

        /**
         * 勤続年数
         */
    @TableField("me_nyearofservice")
        private Long meNyearofservice;

        /**
         * 試用区分
         */
    @TableField("me_ctrialemployement")
        private String meCtrialemployement;

        /**
         * 試用区分コード
         */
    @TableField("me_ctrialemployementnm")
        private String meCtrialemployementnm;

        /**
         * 試用満了日
         */
    @TableField("me_dendoftrialemployment")
        private Date meDendoftrialemployment;

        /**
         * 退職年月日
         */
    @TableField("me_ddateofretirement")
        private Date meDdateofretirement;

        /**
         * 退職時年齢
         */
    @TableField("me_nageatresignation")
        private Long meNageatresignation;

        /**
         * 退職理由
         */
    @TableField("me_creasonofresignation")
        private String meCreasonofresignation;

        /**
         * 退職理由コード
         */
    @TableField("me_creasonofresignationnm")
        private String meCreasonofresignationnm;

        /**
         * 退職理由備考
         */
    @TableField("me_cremarksofresignation")
        private String meCremarksofresignation;

        /**
         * 定年到達日
         */
    @TableField("me_ddateofagelimit")
        private Date meDdateofagelimit;

        /**
         * 役職定年日
         */
    @TableField("me_ddateformanagerialposition")
        private Date meDdateformanagerialposition;

        /**
         * 職員区分
         */
    @TableField("me_ctypeofemployment")
        private String meCtypeofemployment;

        /**
         * 職員区分コード
         */
    @TableField("me_ctypeofemploymentnm")
        private String meCtypeofemploymentnm;

        /**
         * 採用形態区分
         */
    @TableField("me_ciffreshcandidatid")
        private String meCiffreshcandidatid;

        /**
         * 採用形態区分コード
         */
    @TableField("me_ciffreshcandidatnm")
        private String meCiffreshcandidatnm;

        /**
         * 入社形態区分
         */
    @TableField("me_cmodeofappointment")
        private String meCmodeofappointment;

        /**
         * 入社形態区分コード
         */
    @TableField("me_cmodeofappointmentnm")
        private String meCmodeofappointmentnm;

        /**
         * 勤務形態区分
         */
    @TableField("me_ctypeofservice")
        private String meCtypeofservice;

        /**
         * 勤務形態区分コード
         */
    @TableField("me_ctypeofservicenm")
        private String meCtypeofservicenm;

        /**
         * 居住区分
         */
    @TableField("me_ctypeofhabitation")
        private String meCtypeofhabitation;

        /**
         * 居住区分コード
         */
    @TableField("me_ctypeofhabitationnm")
        private String meCtypeofhabitationnm;

        /**
         * 既婚区分
         */
    @TableField("me_cmaritalstatus")
        private String meCmaritalstatus;

        /**
         * 既婚区分コード
         */
    @TableField("me_cmaritalstatusnm")
        private String meCmaritalstatusnm;

        /**
         * 直間区分
         */
    @TableField("me_cifcostcalcid")
        private String meCifcostcalcid;

        /**
         * 直間区分コード
         */
    @TableField("me_cifcostcalcnm")
        private String meCifcostcalcnm;

        /**
         * 給与区分
         */
    @TableField("me_ctypeofpayment")
        private String meCtypeofpayment;

        /**
         * 給与区分コード
         */
    @TableField("me_ctypeofpaymentnm")
        private String meCtypeofpaymentnm;

        /**
         * 国籍
         */
    @TableField("me_cnationality")
        private String meCnationality;

        /**
         * 国籍コード
         */
    @TableField("me_cnationalitynm")
        private String meCnationalitynm;

        /**
         * 本籍地
         */
    @TableField("me_cdomicileoforigin")
        private String meCdomicileoforigin;

        /**
         * 本籍地コード
         */
    @TableField("me_cdomicileoforiginnm")
        private String meCdomicileoforiginnm;

        /**
         * 予備内容1（別名用ダミー）
         */
    @TableField("me_csparedesc1")
        private String meCsparedesc1;

        /**
         * 予備内容1（日本語）
         */
    @TableField("me_csparedesc1ja")
        private String meCsparedesc1ja;

        /**
         * 予備内容1（英語）
         */
    @TableField("me_csparedesc1en")
        private String meCsparedesc1en;

        /**
         * 予備内容1（中国語）
         */
    @TableField("me_csparedesc1ch")
        private String meCsparedesc1ch;

        /**
         * 予備内容1（予備１）
         */
    @TableField("me_csparedesc101")
        private String meCsparedesc101;

        /**
         * 予備内容1（予備2）
         */
    @TableField("me_csparedesc102")
        private String meCsparedesc102;

        /**
         * 予備内容2（別名用ダミー）
         */
    @TableField("me_csparedesc2")
        private String meCsparedesc2;

        /**
         * 予備内容2（日本語）
         */
    @TableField("me_csparedesc2ja")
        private String meCsparedesc2ja;

        /**
         * 予備内容2（英語）
         */
    @TableField("me_csparedesc2en")
        private String meCsparedesc2en;

        /**
         * 予備内容2（中国語）
         */
    @TableField("me_csparedesc2ch")
        private String meCsparedesc2ch;

        /**
         * 予備内容2（予備１）
         */
    @TableField("me_csparedesc201")
        private String meCsparedesc201;

        /**
         * 予備内容2（予備2）
         */
    @TableField("me_csparedesc202")
        private String meCsparedesc202;

        /**
         * 予備内容3（別名用ダミー）
         */
    @TableField("me_csparedesc3")
        private String meCsparedesc3;

        /**
         * 予備内容3（日本語）
         */
    @TableField("me_csparedesc3ja")
        private String meCsparedesc3ja;

        /**
         * 予備内容3（英語）
         */
    @TableField("me_csparedesc3en")
        private String meCsparedesc3en;

        /**
         * 予備内容3（中国語）
         */
    @TableField("me_csparedesc3ch")
        private String meCsparedesc3ch;

        /**
         * 予備内容3（予備１）
         */
    @TableField("me_csparedesc301")
        private String meCsparedesc301;

        /**
         * 予備内容3（予備2）
         */
    @TableField("me_csparedesc302")
        private String meCsparedesc302;

        /**
         * 予備内容4（別名用ダミー）
         */
    @TableField("me_csparedesc4")
        private String meCsparedesc4;

        /**
         * 予備内容4（日本語）
         */
    @TableField("me_csparedesc4ja")
        private String meCsparedesc4ja;

        /**
         * 予備内容4（英語）
         */
    @TableField("me_csparedesc4en")
        private String meCsparedesc4en;

        /**
         * 予備内容4（中国語）
         */
    @TableField("me_csparedesc4ch")
        private String meCsparedesc4ch;

        /**
         * 予備内容4（予備１）
         */
    @TableField("me_csparedesc401")
        private String meCsparedesc401;

        /**
         * 予備内容4（予備2）
         */
    @TableField("me_csparedesc402")
        private String meCsparedesc402;

        /**
         * 予備内容5（別名用ダミー）
         */
    @TableField("me_csparedesc5")
        private String meCsparedesc5;

        /**
         * 予備内容5（日本語）
         */
    @TableField("me_csparedesc5ja")
        private String meCsparedesc5ja;

        /**
         * 予備内容5（英語）
         */
    @TableField("me_csparedesc5en")
        private String meCsparedesc5en;

        /**
         * 予備内容5（中国語）
         */
    @TableField("me_csparedesc5ch")
        private String meCsparedesc5ch;

        /**
         * 予備内容5（予備１）
         */
    @TableField("me_csparedesc501")
        private String meCsparedesc501;

        /**
         * 予備内容5（予備2）
         */
    @TableField("me_csparedesc502")
        private String meCsparedesc502;

        /**
         * 予備数値1
         */
    @TableField("me_nnumber01")
        private Long meNnumber01;

        /**
         * 予備数値2
         */
    @TableField("me_nnumber02")
        private Long meNnumber02;

        /**
         * 予備数値3
         */
    @TableField("me_nnumber03")
        private Long meNnumber03;

        /**
         * 予備数値4
         */
    @TableField("me_nnumber04")
        private Long meNnumber04;

        /**
         * 予備数値5
         */
    @TableField("me_nnumber05")
        private Long meNnumber05;

        /**
         * 予備文字列1
         */
    @TableField("me_cchar01")
        private String meCchar01;

        /**
         * 予備文字列2
         */
    @TableField("me_cchar02")
        private String meCchar02;

        /**
         * 予備文字列3
         */
    @TableField("me_cchar03")
        private String meCchar03;

        /**
         * 予備文字列4
         */
    @TableField("me_cchar04")
        private String meCchar04;

        /**
         * 予備文字列5
         */
    @TableField("me_cchar05")
        private String meCchar05;

        /**
         * 予備日付1
         */
    @TableField("me_ddate01")
        private Date meDdate01;

        /**
         * 予備日付2
         */
    @TableField("me_ddate02")
        private Date meDdate02;

        /**
         * 予備日付3
         */
    @TableField("me_ddate03")
        private Date meDdate03;

        /**
         * 予備日付4
         */
    @TableField("me_ddate04")
        private Date meDdate04;

        /**
         * 予備日付5
         */
    @TableField("me_ddate05")
        private Date meDdate05;

        /**
         * 予備コード1
         */
    @TableField("me_ccode01")
        private String meCcode01;

        /**
         * 予備コード1コード
         */
    @TableField("me_ccodenm01")
        private String meCcodenm01;

        /**
         * 予備コード2
         */
    @TableField("me_ccode02")
        private String meCcode02;

        /**
         * 予備コード2コード
         */
    @TableField("me_ccodenm02")
        private String meCcodenm02;

        /**
         * 予備コード3
         */
    @TableField("me_ccode03")
        private String meCcode03;

        /**
         * 予備コード3コード
         */
    @TableField("me_ccodenm03")
        private String meCcodenm03;

        /**
         * 予備コード4
         */
    @TableField("me_ccode04")
        private String meCcode04;

        /**
         * 予備コード4コード
         */
    @TableField("me_ccodenm04")
        private String meCcodenm04;

        /**
         * 予備コード5
         */
    @TableField("me_ccode05")
        private String meCcode05;

        /**
         * 予備コード5コード
         */
    @TableField("me_ccodenm05")
        private String meCcodenm05;

        /**
         * バージョンno
         */
    @Version
    @TableField("versionno")
        private Long versionno;


    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String meCname;

}