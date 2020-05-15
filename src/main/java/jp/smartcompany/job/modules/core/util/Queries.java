package jp.smartcompany.job.modules.core.util;

import jp.smartcompany.job.util.SysUtil;

import java.util.Vector;

public class Queries {

    protected String SQLString = null;

    public String Jk_Section(Vector vParam, String strDateFormat1) {
        this.SQLString = ("select   mast_organisation.MO_CSECTIONNAME,mast_organisation.MO_CSECTIONID_CK   from mast_organisation where mast_organisation.MO_CCUSTOMERID_CK_FK = '"
                + vParam.elementAt(0)
                + "' and "
                + "mast_organisation.MO_CCOMPANYID_CK_FK  = '"
                + vParam.elementAt(1)
                + "' and "
                + "to_char(mast_organisation.MO_DSTART,'"
                + strDateFormat1
                + "') <= '"
                + vParam.elementAt(2)
                + "' and "
                + "to_char(mast_organisation.MO_DEND,'"
                + strDateFormat1
                + "') >= '" + vParam.elementAt(2) + "' ");

        return this.SQLString;
    }

    public String getAllSections(Vector vParam, String strDateFormat) {
        this.SQLString = (" SELECT MO_CSECTIONID_CK,MO_NHR FROM  MAST_ORGANISATION WHERE  mast_organisation.MO_CCUSTOMERID_CK_FK = '"
                + vParam.elementAt(0)
                + "' and "
                + " mast_organisation.MO_CCOMPANYID_CK_FK  = '"
                + vParam.elementAt(1)
                + "' and "
                + " (to_char(MAST_organisation.Mo_DSTART,'"
                + strDateFormat
                + "')  <= '"
                + vParam.elementAt(2)
                + "' ) AND "
                + " ( to_char(MAST_organisation.Mo_DEND,'"
                + strDateFormat
                + "')  >= '" + vParam.elementAt(2) + "' ) AND " + " MO_NHR <= 2 ORDER BY MO_NSEQ");

        return this.SQLString;
    }

    public String getSection(Vector vParam, String strDateFormat) {
        String sDate = (String) vParam.get(0);
        this.SQLString = (" SELECT MO_CCUSTOMERID_CK_FK,MO_CCOMPANYID_CK_FK,MO_CSECTIONID_CK,MO_NHR FROM   MAST_ORGANISATION WHERE  trunc(MAST_organisation.Mo_DSTART) <= trunc(TO_DATE('"
                + sDate
                + "','yyyy/mm/dd')) AND "
                + "  trunc(MAST_organisation.Mo_DEND) >= trunc(TO_DATE('"
                + sDate + "','yyyy/mm/dd'))");

        this.SQLString += " ORDER BY MO_NSEQ";

        return this.SQLString;
    }

    public String getAllDept(Vector vecParam, String strDateFormat) {
        this.SQLString = ("select hd_csectionid_fk from hist_designation where  hd_ccustomerid_ck='"
                + vecParam.elementAt(0)
                + "' and "
                + " hd_ccompanyid_ck = '"
                + vecParam.elementAt(1)
                + "' and "
                + " hd_cemployeeid_ck = '"
                + vecParam.elementAt(3)
                + "' and "
                + " to_char(hd_dstartdate_ck,'"
                + strDateFormat
                + "')  <= '"
                + vecParam.elementAt(2)
                + "' and "
                + " to_char(hd_denddate,'"
                + strDateFormat + "')  >= '" + vecParam.elementAt(2) + "' ");

        return this.SQLString;
    }

    public String Ps_ChangePassword_Insert(Vector vParam, String strDateFormat,
                                           String strDateFormat1) {
        this.SQLString = ("insert\tinto\tmast_password ( MAP_CCUSTOMERID_CK_FK, MAP_CCOMPANYID_CK_FK, MAP_CEMPLOYEEID_CK_FK, MAP_CPASSWORD, MAP_DPWDDATE, MAP_DSTART, MAP_DEND ) values ( '"
                + vParam.elementAt(0)
                + "', "
                + "'"
                + vParam.elementAt(2)
                + "', "
                + "'"
                + vParam.elementAt(1)
                + "', "
                + "'"
                + vParam.elementAt(4)
                + "', "
                + "to_date('"
                + vParam.elementAt(6)
                + "','"
                + strDateFormat
                + "'), "
                + "to_date('"
                + vParam.elementAt(6)
                + "','"
                + strDateFormat
                + "'), "
                + "to_date('"
                + vParam.elementAt(9)
                + "','"
                + strDateFormat1 + "') " + ")");

        return this.SQLString;
    }

    public String Ps_ChangePassword_Delete(Vector vParam, String strDateFormat) {
        this.SQLString = ("DELETE FROM  MAST_PASSWORD WHERE  MAP_CCUSTOMERID_CK_FK='"
                + vParam.elementAt(0)
                + "' AND "
                + " MAP_CEMPLOYEEID_CK_FK='"
                + vParam.elementAt(1)
                + "' AND "
                + " MAP_CCOMPANYID_CK_FK='"
                + vParam.elementAt(2)
                + "' AND "
                + " to_char(MAP_DSTART,'"
                + strDateFormat + "') = '" + vParam.elementAt(8) + "' ");

        return this.SQLString;
    }

    public String Ps_ChangePassword_Update(Vector vParam, String strDateFormat,
                                           String strDateFormat1) {
        this.SQLString = (" UPDATE  MAST_PASSWORD SET MAP_DEND= to_date('"
                + vParam.elementAt(5) + "','" + strDateFormat1 + "')  "
                + " WHERE " + " MAP_CCUSTOMERID_CK_FK='" + vParam.elementAt(0)
                + "' AND " + " MAP_CEMPLOYEEID_CK_FK='" + vParam.elementAt(1)
                + "' AND " + " MAP_CCOMPANYID_CK_FK='" + vParam.elementAt(2)
                + "' AND "
                + " MAP_DEND = (select max(MAP_DEND) from MAST_PASSWORD where "
                + " MAP_CCUSTOMERID_CK_FK='" + vParam.elementAt(0) + "' AND "
                + " MAP_CEMPLOYEEID_CK_FK='" + vParam.elementAt(1) + "' AND "
                + " MAP_CCOMPANYID_CK_FK='" + vParam.elementAt(2) + "')");

        return this.SQLString;
    }

    public String jk_saveListBean_Insert2(Vector vecParam) {
        vecParam = SysUtil.replaceEscape(vecParam);
        this.SQLString = ("INSERT INTO hist_crosssearch_where (HSW_CCUSTOMERID_CK,HSW_CCOMPANYID_CK ,  HSW_CEMPLOYEEID_CK,HSW_CFILENAME_CK ,HSW_NSEQ,HSW_CITEMSEQ ,HSW_CANDOR ,HSW_CLPARENTHESIS ,  HSW_COPERATOR ,HSW_CVALUE , HSW_CRPARENTHESIS , HSW_CIFPUBLIC ) VALUES ('"
                + vecParam.elementAt(0)
                + "','"
                + vecParam.elementAt(1)
                + "','"
                + vecParam.elementAt(2)
                + "',N'"
                + vecParam.elementAt(3)
                + "','"
                + vecParam.elementAt(4)
                + "','"
                + vecParam.elementAt(5)
                + "','"
                + vecParam.elementAt(6)
                + "','"
                + vecParam.elementAt(7)
                + "','"
                + vecParam.elementAt(8)
                + "',N'"
                + vecParam.elementAt(9)
                + "','"
                + vecParam.elementAt(10)
                + "','" + vecParam.elementAt(11) + "')");

        return this.SQLString;
    }

    public String jk_saveListBean_Insert1(Vector vecParam) {
        vecParam = SysUtil.replaceEscape(vecParam);
        this.SQLString = ("INSERT INTO hist_crosssearch_select (HICS_CCUSTOMERID_CK,HICS_CCOMPANYID_CK,HICS_CEMPLOYEEID_CK,  HICS_CFILENAME_CK, HICS_CXAXIS_ITEMSEQ1,HICS_CXAXIS_DATETYPE1,HICS_NXAXIS_PITCH1,HICS_NXAXIS_MIN1,   HICS_NXAXIS_MAX1,  HICS_NXAXIS_SUM,HICS_CXAXIS_ITEMSEQ2,HICS_CXAXIS_DATETYPE2,HICS_NXAXIS_PITCH2,  HICS_NXAXIS_MIN2,  HICS_NXAXIS_MAX2,  HICS_CYAXIS_ITEMSEQ1,HICS_CYAXIS_DATETYPE1,HICS_NYAXIS_PITCH1,  HICS_NYAXIS_MIN1,  HICS_NYAXIS_MAX1,  HICS_NYAXIS_SUM,   HICS_CYAXIS_ITEMSEQ2,HICS_CYAXIS_DATETYPE2,  HICS_NYAXIS_PITCH2,HICS_NYAXIS_MIN2,  HICS_NYAXIS_MAX2,   HICS_CSUM_ITEMSEQ, HICS_NCOUNT,  HICS_NAVG,  HICS_CIFPUBLIC ) VALUES ('"
                + vecParam.elementAt(0)
                + "','"
                + vecParam.elementAt(1)
                + "','"
                + vecParam.elementAt(2)
                + "',N'"
                + vecParam.elementAt(3)
                + "','"
                + vecParam.elementAt(4)
                + "','"
                + vecParam.elementAt(5)
                + "','"
                + vecParam.elementAt(6)
                + "','"
                + vecParam.elementAt(7)
                + "','"
                + vecParam.elementAt(8)
                + "','"
                + vecParam.elementAt(9)
                + "','"
                + vecParam.elementAt(10)
                + "','"
                + vecParam.elementAt(11)
                + "','"
                + vecParam.elementAt(12)
                + "','"
                + vecParam.elementAt(13)
                + "','"
                + vecParam.elementAt(14)
                + "','"
                + vecParam.elementAt(15)
                + "','"
                + vecParam.elementAt(16)
                + "','"
                + vecParam.elementAt(17)
                + "','"
                + vecParam.elementAt(18)
                + "','"
                + vecParam.elementAt(19)
                + "','"
                + vecParam.elementAt(20)
                + "','"
                + vecParam.elementAt(21)
                + "','"
                + vecParam.elementAt(22)
                + "','"
                + vecParam.elementAt(23)
                + "','"
                + vecParam.elementAt(24)
                + "','"
                + vecParam.elementAt(25)
                + "','"
                + vecParam.elementAt(26)
                + "','"
                + vecParam.elementAt(27)
                + "','"
                + vecParam.elementAt(28)
                + "','"
                + vecParam.elementAt(29) + "')");

        return this.SQLString;
    }

    public String jk_saveListBean_Insert(Vector vecParam) {
        vecParam = SysUtil.replaceEscape(vecParam);
        this.SQLString = ("INSERT INTO HIST_CROSSSEARCH (HCS_CCUSTOMERID_CK,HCS_CCOMPANYID_CK, HCS_CEMPLOYEEID_CK,HCS_CFILENAME, HCS_CIFPUBLIC,HCS_CIGNORECASE,HCS_CNODATANOOUTPUT) VALUES ('"
                + vecParam.elementAt(0)
                + "','"
                + vecParam.elementAt(1)
                + "','"
                + vecParam.elementAt(2)
                + "',N'"
                + vecParam.elementAt(3)
                + "','"
                + vecParam.elementAt(4)
                + "','"
                + vecParam.elementAt(5)
                + "','" + vecParam.elementAt(6) + "')");

        return this.SQLString;
    }

    public String jk_saveListBean_Delete2(Vector vecParam) {
        if (vecParam.elementAt(4).equals("0")) {
            this.SQLString = ("DELETE FROM HIST_CROSSSEARCH_WHERE WHERE HSW_CCUSTOMERID_CK = '"
                    + vecParam.elementAt(0)
                    + "' AND "
                    + " HSW_CCOMPANYID_CK  = '"
                    + vecParam.elementAt(1)
                    + "' AND HSW_CEMPLOYEEID_CK = '"
                    + vecParam.elementAt(2)
                    + "' AND "
                    + " HSW_CFILENAME_CK = N'"
                    + vecParam.elementAt(3)
                    + "' AND HSW_CIFPUBLIC = '"
                    + vecParam.elementAt(4) + "'");
        } else {
            this.SQLString = ("DELETE FROM HIST_CROSSSEARCH_WHERE WHERE HSW_CCUSTOMERID_CK = '"
                    + vecParam.elementAt(0)
                    + "' AND "
                    + " HSW_CCOMPANYID_CK  = '"
                    + vecParam.elementAt(1)
                    + "' AND "
                    + " HSW_CFILENAME_CK = N'"
                    + vecParam.elementAt(3)
                    + "' AND HSW_CIFPUBLIC = '"
                    + vecParam.elementAt(4) + "'");
        }
        return this.SQLString;
    }

    public String jk_saveListBean_Delete1(Vector vecParam) {
        if (vecParam.elementAt(4).equals("0")) {
            this.SQLString = ("DELETE FROM HIST_CROSSSEARCH_SELECT WHERE HICS_CCUSTOMERID_CK = '"
                    + vecParam.elementAt(0)
                    + "' AND "
                    + " HICS_CCOMPANYID_CK = '"
                    + vecParam.elementAt(1)
                    + "' AND HICS_CEMPLOYEEID_CK  = '"
                    + vecParam.elementAt(2)
                    + "' AND "
                    + " HICS_CFILENAME_CK = N'"
                    + vecParam.elementAt(3)
                    + "' AND HICS_CIFPUBLIC = '"
                    + vecParam.elementAt(4) + "'");
        } else {
            this.SQLString = ("DELETE FROM HIST_CROSSSEARCH_SELECT WHERE HICS_CCUSTOMERID_CK = '"
                    + vecParam.elementAt(0)
                    + "' AND "
                    + " HICS_CCOMPANYID_CK = '"
                    + vecParam.elementAt(1)
                    + "' AND "
                    + " HICS_CFILENAME_CK = N'"
                    + vecParam.elementAt(3)
                    + "' AND HICS_CIFPUBLIC = '"
                    + vecParam.elementAt(4) + "'");
        }
        return this.SQLString;
    }

    public String jk_saveListBean_Delete(Vector vecParam) {
        if (vecParam.elementAt(4).equals("0")) {
            this.SQLString = ("DELETE FROM HIST_CROSSSEARCH WHERE HCS_CCUSTOMERID_CK = '"
                    + vecParam.elementAt(0)
                    + "' AND "
                    + " HCS_CCOMPANYID_CK = '"
                    + vecParam.elementAt(1)
                    + "' AND HCS_CEMPLOYEEID_CK = '"
                    + vecParam.elementAt(2)
                    + "' AND "
                    + " HCS_CFILENAME = N'"
                    + vecParam.elementAt(3)
                    + "' AND HCS_CIFPUBLIC = '" + vecParam.elementAt(4) + "'");
        } else {
            this.SQLString = ("DELETE FROM HIST_CROSSSEARCH WHERE HCS_CCUSTOMERID_CK = '"
                    + vecParam.elementAt(0)
                    + "' AND "
                    + " HCS_CCOMPANYID_CK = '"
                    + vecParam.elementAt(1)
                    + "' AND "
                    + " HCS_CFILENAME = N'"
                    + vecParam.elementAt(3)
                    + "' AND HCS_CIFPUBLIC = '" + vecParam.elementAt(4) + "'");
        }
        return this.SQLString;
    }

    public String Ps_BulletinBoardBean_Update(Vector vParam,
                                              String strDateFormat, String strDateFormat1) {
        vParam = SysUtil.replaceEscape(vParam);
        this.SQLString = ("UPDATE hist_bulletienboard SET HD_NDEL= 1 WHERE HD_NSEQ= " + vParam
                .elementAt(1));
        return this.SQLString;
    }

    public String Hrm_bbs_UpdateNoteBean_Insert(Vector vParam,
                                                String strDateFormat, String strDateFormat1) {
        vParam = SysUtil.replaceEscape(vParam);
        this.SQLString = ("UPDATE  hist_bulletienboard SET HB_CCOMPANYID = '"
                + vParam.elementAt(0) + "'," + " HB_CTITLE = N'"
                + vParam.elementAt(3) + "',"
                + " HB_DATEOFANNOUNCEMENT = to_date('" + vParam.elementAt(4)
                + "','" + strDateFormat1 + "'),"
                + " HB_DATEOFEXPIRE = to_date('" + vParam.elementAt(11) + "','"
                + strDateFormat1 + "')," + " HB_CCONTENTS = N'"
                + vParam.elementAt(6) + "'," + " HB_CLINK = '"
                + vParam.elementAt(7) + "'," + " HB_CMNUSER= '"
                + vParam.elementAt(8) + "'," + " HB_CFILNAME = N'"
                + vParam.elementAt(10) + "', " + " HD_NDEL = "
                + vParam.elementAt(9) + ", " + " HB_CMNUSERNAME = '"
                + vParam.elementAt(12) + "'," + " HB_CFIX = '"
                + vParam.elementAt(13) + "' " + " WHERE " + "  HD_NSEQ = " + vParam
                .elementAt(2));

        return this.SQLString;
    }

    public String Hrm_bbs_SaveNewNoteBean_renew(Vector vParam,
                                                String strDateFormat, String strDateFormat1) {
        vParam = SysUtil.replaceEscape(vParam);

        this.SQLString = ("INSERT INTO HIST_BULLETIENBOARD ( HB_CCUSTOMERID, HB_CCOMPANYID, HB_DATEOFANNOUNCEMENT, HB_DATEOFEXPIRE, HB_CTITLE, HB_CCONTENTS, HB_CFILNAME, HB_CLINK, HB_CMNUSER, HD_NSEQ, HD_NDEL, HB_BATTACH, HB_CMNUSERNAME, HB_CFIX ) SELECT '"
                + vParam.elementAt(0)
                + "', "
                + "'"
                + vParam.elementAt(1)
                + "', "
                + "TO_DATE( '"
                + vParam.elementAt(2)
                + "','"
                + strDateFormat1
                + "'), "
                + "TO_DATE( '"
                + vParam.elementAt(3)
                + "','"
                + strDateFormat1
                + "'), "
                + "N'"
                + vParam.elementAt(4)
                + "', "
                + "N'"
                + vParam.elementAt(5)
                + "', "
                + "N'"
                + vParam.elementAt(6)
                + "', "
                + "'"
                + vParam.elementAt(7)
                + "', "
                + "'"
                + vParam.elementAt(8)
                + "', "
                + vParam.elementAt(9)
                + ", "
                + vParam.elementAt(10)
                + ", "
                + "HB_BATTACH, "
                + "'"
                + vParam.elementAt(11)
                + "', "
                + "'"
                + vParam.elementAt(12)
                + "' "
                + "FROM "
                + "HIST_BULLETIENBOARD " + "WHERE " + "HD_NSEQ = " + vParam
                .elementAt(13));

        return this.SQLString;
    }

    public String Hrm_bbs_UpdateNoteBean_Attach(Vector vParam) {
        this.SQLString = ("UPDATE HIST_BULLETIENBOARD SET HB_BATTACH = ? WHERE HD_NSEQ = " + vParam
                .elementAt(0));

        return this.SQLString;
    }

    public String Hrm_bbs_SaveNewNoteBean_Insert(Vector vParam,
                                                 String strDateFormat, String strDateFormat1) {
        vParam = SysUtil.replaceEscape(vParam);

        this.SQLString = ("INSERT INTO  hist_bulletienboard (HB_CCOMPANYID,HB_CCUSTOMERID,HD_NSEQ, HB_CTITLE ,HB_DATEOFANNOUNCEMENT ,HB_CCONTENTS ,HB_CLINK, HB_CMNUSER, HD_NDEL, HB_DATEOFEXPIRE,HB_CFILNAME, HB_BATTACH, HB_CMNUSERNAME, HB_CFIX ) values ('"
                + vParam.elementAt(0)
                + "','"
                + vParam.elementAt(1)
                + "',"
                + vParam.elementAt(2)
                + ",N'"
                + vParam.elementAt(3)
                + "',"
                + "to_date('"
                + vParam.elementAt(4)
                + "','"
                + strDateFormat1
                + "'),N'"
                + vParam.elementAt(5)
                + "','"
                + vParam.elementAt(6)
                + "','"
                + vParam.elementAt(7)
                + "',"
                + vParam.elementAt(8)
                + ","
                + "to_date('"
                + vParam.elementAt(9)
                + "','"
                + strDateFormat1
                + "')"
                + ",N'"
                + vParam.elementAt(10)
                + "',"
                + "?,"
                + "'"
                + vParam.elementAt(11)
                + "',"
                + "'"
                + vParam.elementAt(12) + "' " + ")");

        return this.SQLString;
    }

    public String Hrm_bbs_DispNewNoteBean_Select(Vector vParam,
                                                 String strDateFormat) {
        String sDate = "TO_DATE( '" + vParam.elementAt(3) + "', '"
                + strDateFormat + "' )";

        this.SQLString = ("SELECT HIST_DESIGNATION.HD_CSECTIONID_FK as HD_CSECTIONID_FK, HIST_DESIGNATION.HD_CPOSTID_FK as HD_CPOSTID_FK, HIST_DESIGNATION.HD_CCOMPANYID_CK as HD_CCOMPANYID_CK, MAST_EMPLOYEES.ME_CEMPLOYEEID_CK as ME_CEMPLOYEEID_CK, MAST_EMPLOYEES.ME_CKANJINAME as ME_CKANJINAME, MAST_ORGANISATION.MO_CSECTIONNAME as MO_CSECTIONNAME FROM MAST_EMPLOYEES,HIST_DESIGNATION,MAST_ORGANISATION WHERE MAST_EMPLOYEES.ME_CCUSTOMERID_CK\t= '"
                + vParam.elementAt(5)
                + "' "
                + "AND\tMAST_EMPLOYEES.ME_CCOMPANYID\t\t= '"
                + vParam.elementAt(0)
                + "' "
                + "AND\tMAST_EMPLOYEES.ME_CEMPLOYEEID_CK\t= '"
                + vParam.elementAt(2)
                + "' "
                + "AND\tMAST_EMPLOYEES.ME_DSTARTDATE\t\t<= "
                + sDate
                + " "
                + "AND\tMAST_EMPLOYEES.ME_DENDDATE\t\t\t>= "
                + sDate
                + " "
                + "AND\tHIST_DESIGNATION.HD_CCUSTOMERID_CK\t= MAST_EMPLOYEES.ME_CCUSTOMERID_CK "
                + "AND\tHIST_DESIGNATION.HD_CCOMPANYID_CK\t= MAST_EMPLOYEES.ME_CCOMPANYID "
                + "AND\tHIST_DESIGNATION.HD_CEMPLOYEEID_CK\t= MAST_EMPLOYEES.ME_CEMPLOYEEID_CK "
                + "AND\tHIST_DESIGNATION.HD_DSTARTDATE_CK\t<= "
                + sDate
                + " "
                + "AND\tHIST_DESIGNATION.HD_DENDDATE\t\t>= "
                + sDate
                + " "
                + "AND\tHIST_DESIGNATION.HD_CIFKEYORADDITIONALROLE = '0' "
                + "AND\tMAST_ORGANISATION.MO_CCUSTOMERID_CK_FK\t= HIST_DESIGNATION.HD_CCUSTOMERID_CK "
                + "AND\tMAST_ORGANISATION.MO_CCOMPANYID_CK_FK\t= HIST_DESIGNATION.HD_CCOMPANYID_CK "
                + "AND\tMAST_ORGANISATION.MO_CSECTIONID_CK\t= HIST_DESIGNATION.HD_CSECTIONID_FK "
                + "AND\tMAST_ORGANISATION.MO_DSTART\t\t\t<= "
                + sDate
                + " "
                + "AND\tMAST_ORGANISATION.MO_DEND\t\t\t>= "
                + sDate
                + " "
                + "AND\tMAST_ORGANISATION.MO_CLANGUAGE\t\t= '"
                + vParam.elementAt(4) + "' ");

        return this.SQLString;
    }

    public String Hrm_bbs_DispNewNoteBean_Select1() {
        this.SQLString = "SELECT NVL( MAX( HD_NSEQ ), 0 ) FROM HIST_BULLETIENBOARD ";

        return this.SQLString;
    }

    public String Cr_startBean_Select(Vector vParam) {
        this.SQLString = ("SELECT MD_CTABLENAME as D_CTABLENAME,MDC_CTABLEDESC as MDC_CTABLEDESC,MD_CCOLUMNNAME as MD_CCOLUMNNAME,MD_CTYPEOFCOLUMN as MD_CTYPEOFCOLUMN, MDC_CCOLUMNDESC as MDC_CCOLUMNDESC,MD_CID as MD_CID,MD_CMASTERTBLNAME as MD_CMASTERTBLNAME, MD_CCALCULATECOLUMN as MD_CCALCULATECOLUMN ,MD_CMATCHESWITH as MD_CMATCHESWITH  FROM Mast_DATADICTIONARY ,MAST_DATACUST WHERE MD_CID = MDC_CID_CK_FK And mdc_clanguage_ck='"
                + vParam.elementAt(0)
                + "' AND "
                + "MD_CCUSTOMERID ='"
                + vParam.elementAt(1)
                + "' AND "
                + "MD_CCOMPANYID ='"
                + vParam.elementAt(2)
                + "' AND "
                + "MD_CAVLFORAXESINCR IN (0,1) " + "ORDER BY " + "MD_NTABLESEQ,MD_CTABLENAME,MD_NSEQ ");

        return this.SQLString;
    }

    public String Cr_startBean_Select1(Vector vParam) {
        this.SQLString = ("SELECT MD_CTABLENAME as MD_CTABLENAME,MDC_CTABLEDESC as MDC_CTABLEDESC,MD_CCOLUMNNAME as MD_CCOLUMNNAME,MD_CTYPEOFCOLUMN as MD_CTYPEOFCOLUMN, MDC_CCOLUMNDESC as MDC_CCOLUMNDESC,MD_CID as MD_CID,MD_CMASTERTBLNAME as MD_CMASTERTBLNAME, MD_CCALCULATECOLUMN as MD_CCALCULATECOLUMN ,MD_CMATCHESWITH as MD_CMATCHESWITH  FROM Mast_DATADICTIONARY ,MAST_DATACUST WHERE MD_CID = MDC_CID_CK_FK And mdc_clanguage_ck='"
                + vParam.elementAt(0)
                + "' AND "
                + " MD_CCUSTOMERID ='"
                + vParam.elementAt(1)
                + "' AND "
                + " MD_CCOMPANYID ='"
                + vParam.elementAt(2)
                + "' AND "
                + " MD_CAVLFORAXESINCR IN (0,2) " + "ORDER BY " + "MD_NTABLESEQ,MD_CTABLENAME,MD_NSEQ ");

        return this.SQLString;
    }

    public String Cr_startBean_Select2(Vector vParam) {
        this.SQLString = ("SELECT MD_CTABLENAME as MD_CTABLENAME ,MDC_CTABLEDESC as MDC_CTABLEDESC,MD_CCOLUMNNAME as MD_CCOLUMNNAME ,MD_CTYPEOFCOLUMN as MD_CTYPEOFCOLUMN, MDC_CCOLUMNDESC as MDC_CCOLUMNDESC,MD_CID as MD_CID,MD_CMASTERTBLNAME as MD_CMASTERTBLNAME, MD_CCALCULATECOLUMN as MD_CCALCULATECOLUMN,  MD_NSEQ as MD_NSEQ,  MD_CAVLGROUPS as MD_CAVLGROUPS FROM Mast_DATADICTIONARY ,MAST_DATACUST WHERE MD_CID = MDC_CID_CK_FK And mdc_clanguage_ck = '"
                + vParam.elementAt(0)
                + "' AND "
                + "MD_CCUSTOMERID = '"
                + vParam.elementAt(1)
                + "' AND "
                + "MD_CCOMPANYID = '"
                + vParam.elementAt(2)
                + "' AND "
                + "MD_CAVLFORCTNINCR = '1' "
                + "ORDER BY " + "MD_NTABLESEQ,MD_CTABLENAME,MD_NSEQ ");

        return this.SQLString;
    }

    public String Cr_startBean_Select3(Vector vParam) {
        this.SQLString = ("SELECT MD_CTABLENAME as MD_CTABLENAME,MDC_CTABLEDESC as MDC_CTABLEDESC,MD_CCOLUMNNAME as MD_CCOLUMNNAME,MD_CTYPEOFCOLUMN as MD_CTYPEOFCOLUMN, MDC_CCOLUMNDESC as MDC_CCOLUMNDESC,MD_CID as MD_CID,MD_CMASTERTBLNAME as MD_CMASTERTBLNAME, MD_CCALCULATECOLUMN as MD_CCALCULATECOLUMN, MD_CMATCHESWITH as MD_CMATCHESWITH,  MD_NSEQ as MD_NSEQ,  MD_CAVLGROUPS as MD_CAVLGROUPS FROM Mast_DATADICTIONARY ,MAST_DATACUST WHERE MD_CID = MDC_CID_CK_FK And mdc_clanguage_ck = '"
                + vParam.elementAt(0)
                + "' AND "
                + "MD_CCUSTOMERID = '"
                + vParam.elementAt(1)
                + "' AND "
                + "MD_CCOMPANYID = '"
                + vParam.elementAt(2)
                + "' AND "
                + "MD_CAVLFORCONDITIONINCR = '1'" + "ORDER BY " + "MD_NTABLESEQ,MD_CTABLENAME,MD_NSEQ ");

        return this.SQLString;
    }

    public String Cr_startBean_Select4(Vector vParam) {
        this.SQLString = ("SELECT MDC_CCOLUMNDESC as MDC_CCOLUMNDESC, DECODE(MD_CCOLUMNNAME, 'ME_CEMPLOYEEID_CK', '1', 'ME_CENGLISHNAME', '2', 'ME_CKANJINAME', '3', 'ME_CMAIL', '4') as MD_NSEQ FROM Mast_DATADICTIONARY ,MAST_DATACUST WHERE MD_CID = MDC_CID_CK_FK And mdc_clanguage_ck = '"
                + vParam.elementAt(0)
                + "' AND "
                + "MD_CCUSTOMERID = '"
                + vParam.elementAt(1)
                + "' AND "
                + "MD_CCOMPANYID = '"
                + vParam.elementAt(2)
                + "' AND "
                + "MD_CTABLENAME = 'MAST_EMPLOYEES' AND "
                + "(MD_CCOLUMNNAME = 'ME_CEMPLOYEEID_CK' OR "
                + "MD_CCOLUMNNAME = 'ME_CENGLISHNAME' OR "
                + "MD_CCOLUMNNAME = 'ME_CKANJINAME' OR "
                + "MD_CCOLUMNNAME = 'ME_CMAIL')" + "ORDER BY " + "MD_NSEQ");

        return this.SQLString;
    }

    public String Jk_loadSettings_Master(Vector param, String strDateFormat1) {
        String query = " SELECT HSE_SEARCH_ID,HSE_DDATE, HSE_CIGNORECASE, HSE_GRANDTOTAL,HSE_WHERECLAUSE,HSE_ORDERCLAUSE,HSE_AGGCLAUSE, HSE_GROUPCLAUSE,  HSE_COL_FOR_AGG,HSE_NSHOWRECORDS FROM HIST_SEARCH WHERE HSE_CFILENAME_CK='"
                + param.elementAt(0)
                + "'  AND  HSE_CIFPUBLIC ='"
                + param.elementAt(1) + "'";

        return query;
    }

    public String Jk_loadSettings_Select(Vector param, String strDateFormat1) {
        String query2 = "  SELECT MD_NSEQ,MD_CTYPEOFCOLUMN,MD_CMASTERTBLNAME,MD_CCOLUMNNAME,MD_CID,MD_CAVLGROUPS,MD_CTABLENAME ,MDC_CCOLUMNDESC FROM Mast_DATADICTIONARY ,MAST_DATACUST ,HIST_SEARCH_SELECT, HIST_SEARCH where MD_CID = MDC_CID_CK_FK  And  MD_CID = HISS_CITEMSEQ AND mdc_clanguage_ck='"
                + param.elementAt(3)
                + "' AND "
                + " MD_CCUSTOMERID ='"
                + param.elementAt(2)
                + "' "
                + " And  HISS_SEARCH_ID=HSE_SEARCH_ID AND HSE_CFILENAME_CK = '"
                + param.elementAt(0)
                + "' AND HSE_CIFPUBLIC = '"
                + param.elementAt(1) + "' order by  HISS_NSEQ ";

        return query2;
    }

    public String Jk_loadSettings_Order(Vector param, String strDateFormat1) {
        String query2 = "  SELECT MD_NSEQ,MD_CTYPEOFCOLUMN,MD_CMASTERTBLNAME,MD_CCOLUMNNAME,MD_CID,MD_CAVLGROUPS, '0' as MD_NSEQ,MD_CCALCULATECOLUMN, '0' as MD_NSEQ, MD_CTABLENAME , MDC_CCOLUMNDESC, HISO_CIFDESCENDING   FROM Mast_DATADICTIONARY ,MAST_DATACUST ,HIST_SEARCH_ORDER, HIST_SEARCH where MD_CID = MDC_CID_CK_FK  And  MD_CID = HISO_CITEMSEQ AND mdc_clanguage_ck='"
                + param.elementAt(3)
                + "' AND "
                + " MD_CCUSTOMERID ='"
                + param.elementAt(2)
                + "' "
                + " And HISO_SEARCH_ID= HSE_SEARCH_ID AND HSE_CFILENAME_CK = '"
                + param.elementAt(0)
                + "' AND HSE_CIFPUBLIC = '"
                + param.elementAt(1) + "' order by  HISO_NSEQ_CK";

        return query2;
    }

    public String Jk_loadSettings_Where(Vector param, String strDateFormat1) {
        String query = " SELECT HISW_CANDOR, HISW_CLPARENTHESIS,translate(MDC_CCOLUMNDESC using char_cs) || '$' || MD_CTABLENAME || '.' || MD_CCOLUMNNAME  as MD_CCOLUMNNAME ,  HISW_COPERATOR, HISW_CVALUE, HISW_CRPARENTHESIS   FROM Mast_DATADICTIONARY, HIST_SELECT_WHERE, HIST_SEARCH,MAST_DATACUST  where  MD_CID = HISW_CITEMSEQ AND  MD_CID = MDC_CID_CK_FK AND   MDC_CLANGUAGE_CK = '"
                + param.elementAt(3)
                + "' and "
                + " HISW_SEARCH_ID=HSE_SEARCH_ID AND HSE_CFILENAME_CK = '"
                + param.elementAt(0)
                + "' AND HSE_CIFPUBLIC = '"
                + param.elementAt(1) + "' order by HISW_NSEQ";

        return query;
    }

    public String Jk_loadSettings_Group(Vector param, String strDateFormat1) {
        String query = " SELECT  MD_CCOLUMNNAME  FROM Mast_DATADICTIONARY, HIST_SEARCH_GROUP, HIST_SEARCH where  MD_CID = HISG_CITEMSEQ AND  HISG_SEARCH_ID=HSE_SEARCH_ID AND HSE_CFILENAME_CK = '"
                + param.elementAt(0)
                + "' AND HSE_CIFPUBLIC = '"
                + param.elementAt(1) + "' order by HISG_NSEQ_CK";

        return query;
    }

    public String Jk_loadSettings_Aggregate(Vector param, String strDateFormat1) {
        String query = " SELECT  MD_CCOLUMNNAME, HIST_CAVG, HIST_CSUBTOTAL, HIST_CMAX, HIST_CMIN, HIST_CGRANDTOTAL FROM Mast_DATADICTIONARY, HIST_SEARCH_TOTAL, HIST_SEARCH where  MD_CID = HIST_CITEMSEQ AND  HIST_SEARCH_ID=HSE_SEARCH_ID AND HSE_CFILENAME_CK = '"
                + param.elementAt(0)
                + "' AND HSE_CIFPUBLIC = '"
                + param.elementAt(1) + "'  order by HIST_NSEQ_CK";

        return query;
    }

    public String Jk_loadTable_Order(Vector param, String strDateFormat1) {
        String query2 = "  SELECT   HISO_CITEMSEQ, HISO_CIFDESCENDING FROM HIST_SEARCH_ORDER, HIST_SEARCH where HISO_SEARCH_ID= HSE_SEARCH_ID AND HSE_CFILENAME_CK = '"
                + param.elementAt(0)
                + "' AND HSE_CIFPUBLIC = '"
                + param.elementAt(1) + "' order by  HISO_NSEQ_CK";

        return query2;
    }

    public String Jk_loadTable_Where(Vector param, String strDateFormat1) {
        String query = "SELECT    HISW_CANDOR, HISW_CLPARENTHESIS,  HISW_CITEMSEQ, HISW_COPERATOR, HISW_CVALUE, HISW_CRPARENTHESIS from HIST_SELECT_WHERE, HIST_SEARCH where  HISW_SEARCH_ID=HSE_SEARCH_ID AND HSE_CFILENAME_CK = '"
                + param.elementAt(0)
                + "' AND HSE_CIFPUBLIC = '"
                + param.elementAt(1) + "' order by HISW_NSEQ";

        return query;
    }

    public String Jk_loadTable_Group(Vector param, String strDateFormat1) {
        String query = " SELECT  HISG_CITEMSEQ  FROM HIST_SEARCH_GROUP,HIST_SEARCH where  HISG_SEARCH_ID=HSE_SEARCH_ID AND HSE_CFILENAME_CK = '"
                + param.elementAt(0)
                + "' AND HSE_CIFPUBLIC = '"
                + param.elementAt(1) + "' order by HISG_NSEQ_CK";

        return query;
    }

    public String Jk_loadTable_Aggregate(Vector param, String strDateFormat1) {
        String query = " SELECT  HIST_CITEMSEQ, HIST_CAVG,  HIST_CSUBTOTAL, HIST_CMAX, HIST_CMIN, HIST_CGRANDTOTAL from HIST_SEARCH_TOTAL,HIST_SEARCH where  HIST_SEARCH_ID=HSE_SEARCH_ID AND HSE_CFILENAME_CK = '"
                + param.elementAt(0)
                + "' AND HSE_CIFPUBLIC = '"
                + param.elementAt(1) + "' order by HIST_NSEQ_CK";

        return query;
    }

    public String Gw_bbs_DetailofNoteBean_Select(Vector vParam,
                                                 String strDateFormat) {
        this.SQLString = (" SELECT mast_employees.ME_CKANJINAME as ME_CKANJINAME,mast_organisation.Mo_CSECTIONNAME as MO_CSECTIONNAME,  hist_bulletienboard.HB_CTITLE as HB_CTITLE ,hist_bulletienboard.HB_CCONTENTS as HB_CCONTENTS ,  hist_bulletienboard.HB_DATEOFANNOUNCEMENT as HB_DATEOFANNOUNCEMENT,  hist_bulletienboard.HB_CFILNAME as HB_CFILNAME ,  hist_bulletienboard.HB_CLINK as HB_CLINK  FROM   hist_bulletienboard,mast_employees , hist_designation,mast_organisation   where  hist_bulletienboard.HD_NDEL= 0  AND  hist_bulletienboard.HD_NSEQ = '"
                + vParam.elementAt(2)
                + "' AND   "
                + " mast_employees.ME_CEMPLOYEEID_CK=hist_bulletienboard.HB_CMNUSER AND  "
                + " mast_employees.ME_CCUSTOMERID_CK=hist_bulletienboard.HB_CCUSTOMERID AND  "
                + " mast_employees.ME_CCOMPANYID=hist_bulletienboard.HB_CCOMPANYID AND  "
                + " to_char(mast_employees.ME_DSTARTDATE,'"
                + strDateFormat
                + "') <= '"
                + vParam.elementAt(3)
                + "' AND  "
                + " to_char(mast_employees.ME_DENDDATE,'"
                + strDateFormat
                + "') >= '"
                + vParam.elementAt(3)
                + "' AND  "
                + " hist_designation.HD_CCOMPANYID_CK= MAST_EMPLOYEES.ME_CCOMPANYID AND  "
                + " hist_designation.HD_CEMPLOYEEID_CK= mast_employees.ME_CEMPLOYEEID_CK AND    "
                + " hist_designation.HD_CCUSTOMERID_CK=mast_employees.me_ccustomerid_ck and  "
                + " HIST_DESIGNATION.HD_CIFKEYORADDITIONALROLE = '0'  AND    "
                + " to_char(hist_designation.HD_DSTARTDATE_CK,'"
                + strDateFormat
                + "') <= '"
                + vParam.elementAt(3)
                + "' AND   "
                + " to_char(hist_designation.HD_DENDDATE,'"
                + strDateFormat
                + "') >= '"
                + vParam.elementAt(3)
                + "' AND  "
                + " hist_designation.HD_CSECTIONID_FK=mast_organisation.Mo_CSECTIONID_CK and  "
                + " hist_designation.HD_CCOMPANYID_CK=mast_organisation.Mo_CCOMPANYID_CK_FK and  "
                + " hist_designation.HD_CCUSTOMERID_CK=mast_organisation.Mo_CCUSTOMERID_CK_FK and  "
                + " (to_char(MAST_organisation.Mo_DSTART,'"
                + strDateFormat
                + "')  <= '"
                + vParam.elementAt(3)
                + "' ) AND "
                + " ( to_char(MAST_organisation.Mo_DEND,'"
                + strDateFormat
                + "')  >= '"
                + vParam.elementAt(3)
                + "' ) AND "
                + " mast_organisation.mo_clanguage='"
                + vParam.elementAt(4)
                + "' and  "
                + " mast_employees.me_ccustomerid_ck='"
                + vParam.elementAt(5)
                + "' and  "
                + " mast_employees.me_ccompanyid='" + vParam.elementAt(0) + "'");

        return this.SQLString;
    }

    public String Hrm_bbs_EditNoteBean_Select(Vector vParam,
                                              String strDateFormat) {
        this.SQLString = ("SELECT hist_bulletienboard.HB_CCOMPANYID as  HB_CCOMPANYID, hist_bulletienboard.HD_NSEQ as HD_NSEQ,  hist_bulletienboard.HB_CTITLE as HB_CTITLE, hist_bulletienboard.HB_DATEOFANNOUNCEMENT as HB_DATEOFANNOUNCEMENT,  hist_bulletienboard.HB_CCONTENTS as HB_CCONTENTS, hist_bulletienboard.HB_CLINK as HB_CLINK , hist_bulletienboard.HB_CFILNAME as HB_CFILNAME , hist_bulletienboard.HB_CMNUSER as HB_CMNUSER, hist_bulletienboard.HB_DATEOFEXPIRE as HB_DATEOFEXPIRE,hist_bulletienboard.HD_NDEL,   mast_employees.ME_CKANJINAME as ME_CKANJINAME,mast_organisation.Mo_CSECTIONNAME as MO_CSECTIONNAME, hist_bulletienboard.HB_CMNUSERNAME as HB_CMNUSERNAME,  hist_bulletienboard.HB_CFIX as HB_CFIX   FROM hist_bulletienboard,mast_employees,hist_designation,mast_organisation   WHERE  hist_bulletienboard.HD_NDEL= 0  AND  hist_bulletienboard.HD_NSEQ = '"
                + vParam.elementAt(3)
                + "' AND  "
                + " mast_employees.ME_CCUSTOMERID_CK=hist_bulletienboard.HB_CCUSTOMERID AND "
                + " mast_employees.ME_CCOMPANYID=hist_bulletienboard.HB_CCOMPANYID AND "
                + " to_char(mast_employees.ME_DSTARTDATE,'"
                + strDateFormat
                + "') <= '"
                + vParam.elementAt(4)
                + "' AND "
                + " to_char(mast_employees.ME_DENDDATE,'"
                + strDateFormat
                + "') >= '"
                + vParam.elementAt(4)
                + "' AND "
                + " mast_employees.ME_CEMPLOYEEID_CK= '"
                + vParam.elementAt(2)
                + "' AND   "
                + " hist_designation.HD_CCOMPANYID_CK= MAST_EMPLOYEES.ME_CCOMPANYID AND "
                + " hist_designation.HD_CEMPLOYEEID_CK= mast_employees.ME_CEMPLOYEEID_CK AND   "
                + " hist_designation.HD_CCUSTOMERID_CK=mast_employees.me_ccustomerid_ck and "
                + " HIST_DESIGNATION.HD_CIFKEYORADDITIONALROLE = '0'  AND   "
                + " to_char(hist_designation.HD_DSTARTDATE_CK,'"
                + strDateFormat
                + "') <= '"
                + vParam.elementAt(4)
                + "' AND  "
                + " to_char(hist_designation.HD_DENDDATE,'"
                + strDateFormat
                + "') >= '"
                + vParam.elementAt(4)
                + "' AND "
                + " hist_designation.HD_CSECTIONID_FK=mast_organisation.Mo_CSECTIONID_CK and "
                + " hist_designation.HD_CCOMPANYID_CK=mast_organisation.Mo_CCOMPANYID_CK_FK and "
                + " hist_designation.HD_CCUSTOMERID_CK=mast_organisation.Mo_CCUSTOMERID_CK_FK and "
                + " (to_char(MAST_organisation.Mo_DSTART,'"
                + strDateFormat
                + "')  <= '"
                + vParam.elementAt(4)
                + "' ) AND "
                + " ( to_char(MAST_organisation.Mo_DEND,'"
                + strDateFormat
                + "')  >= '"
                + vParam.elementAt(4)
                + "' ) AND  "
                + " mast_organisation.mo_clanguage='"
                + vParam.elementAt(6)
                + "' and "
                + " mast_employees.me_ccustomerid_ck='"
                + vParam.elementAt(5)
                + "' and "
                + " mast_employees.me_ccompanyid='" + vParam.elementAt(0) + "'");

        return this.SQLString;
    }

    public String Ps_BulletinBoardBean_Select(Vector vParam,
                                              String strDateFormat) {
        this.SQLString = ("SELECT  HIST_BULLETIENBOARD.HB_CTITLE AS HB_CTITLE, HIST_BULLETIENBOARD.HD_NSEQ AS HD_NSEQ, (SELECT \tME_CKANJINAME  FROM  \tMAST_EMPLOYEES  WHERE  ME_CCUSTOMERID_CK = HB_CCUSTOMERID AND ME_CCOMPANYID \t= HB_CCOMPANYID AND ME_CEMPLOYEEID_CK = HB_CMNUSER AND ME_DSTARTDATE \t<= TO_DATE('"
                + vParam.elementAt(2)
                + "','"
                + strDateFormat
                + "') AND"
                + " ME_DENDDATE \t\t>= TO_DATE('"
                + vParam.elementAt(2)
                + "','"
                + strDateFormat
                + "') "
                + " ) AS ME_CKANJINAME,"
                + " (SELECT MO_CSECTIONNAME FROM MAST_ORGANISATION WHERE"
                + "  MO_CCUSTOMERID_CK_FK = '"
                + vParam.elementAt(4)
                + "' AND"
                + "  MO_CCOMPANYID_CK_FK = '"
                + vParam.elementAt(0)
                + "' AND"
                + "  MO_CSECTIONID_CK = "
                + " (SELECT"
                + " \tHD_CSECTIONID_FK"
                + " FROM "
                + " \tHIST_DESIGNATION "
                + " WHERE "
                + " HD_CCUSTOMERID_CK = HB_CCUSTOMERID AND"
                + " HD_CCOMPANYID_CK \t= HB_CCOMPANYID AND"
                + " HD_CEMPLOYEEID_CK = HB_CMNUSER AND"
                + " HD_DSTARTDATE_CK \t<= TO_DATE('"
                + vParam.elementAt(2)
                + "','"
                + strDateFormat
                + "') AND"
                + " HD_DENDDATE \t\t>= TO_DATE('"
                + vParam.elementAt(2)
                + "','"
                + strDateFormat
                + "') AND"
                + " HD_CIFKEYORADDITIONALROLE = '0' AND"
                + " HD_NOFFCIALORNOT \t= 0"
                + " ) AND "
                + "  MO_DSTART <= TO_DATE('"
                + vParam.elementAt(2)
                + "','"
                + strDateFormat
                + "') AND"
                + "  MO_DEND >= TO_DATE('"
                + vParam.elementAt(2)
                + "','"
                + strDateFormat
                + "') AND"
                + "  MO_CLANGUAGE = '"
                + vParam.elementAt(3)
                + "'"
                + " ) AS HD_CSECTIONID_FK,"
                + " HIST_BULLETIENBOARD.HB_CMNUSER AS HB_CMNUSER,"
                + " HIST_BULLETIENBOARD.HB_DATEOFANNOUNCEMENT AS HB_DATEOFANNOUNCEMENT,"
                + " HIST_BULLETIENBOARD.HB_DATEOFEXPIRE AS HB_DATEOFEXPIRE,"
                + " HIST_BULLETIENBOARD.HB_CFILNAME AS HB_CFILNAME,"
                + " HIST_BULLETIENBOARD.HB_CMNUSERNAME AS HB_CMNUSERNAME, "
                + " HIST_BULLETIENBOARD.HB_CFIX AS HB_CFIX "
                + " FROM"
                + " HIST_BULLETIENBOARD "
                + " WHERE"
                + " HIST_BULLETIENBOARD.HB_CCUSTOMERID = '"
                + vParam.elementAt(4)
                + "' AND"
                + " HIST_BULLETIENBOARD.HB_CCOMPANYID = '"
                + vParam.elementAt(0)
                + "' AND"
                + "(\t( "
                + "HIST_BULLETIENBOARD.HB_CFIX = '1' "
                + "AND HIST_BULLETIENBOARD.HB_DATEOFANNOUNCEMENT <= TO_DATE('"
                + vParam.elementAt(2)
                + "','"
                + strDateFormat
                + "') "
                + "AND HIST_BULLETIENBOARD.HB_DATEOFEXPIRE >= TO_DATE('"
                + vParam.elementAt(2)
                + "','"
                + strDateFormat
                + "') "
                + ") OR ( "
                + "HIST_BULLETIENBOARD.HB_CFIX = '0' "
                + "AND HIST_BULLETIENBOARD.HB_CMNUSER\t= '"
                + vParam.elementAt(5)
                + "' "
                + ") OR ( "
                + "HIST_BULLETIENBOARD.HB_CMNUSER\t= '"
                + vParam.elementAt(5)
                + "' "
                + "AND HIST_BULLETIENBOARD.HB_DATEOFANNOUNCEMENT >= TO_DATE('"
                + vParam.elementAt(2)
                + "','"
                + strDateFormat
                + "') "
                + ") "
                + ") AND "
                + " HIST_BULLETIENBOARD.HD_NDEL = 0 "
                + " ORDER BY "
                + " HIST_BULLETIENBOARD.HB_DATEOFANNOUNCEMENT DESC,"
                + " HIST_BULLETIENBOARD.HD_NSEQ DESC," + " HIST_BULLETIENBOARD.HB_CMNUSER ASC");

        return this.SQLString;
    }

    public String Ps_BulletinBoardBean_SelectPast(Vector vParam,
                                                  String strDateFormat) {
        this.SQLString = ("SELECT HIST_BULLETIENBOARD.HB_CTITLE AS HB_CTITLE,HIST_BULLETIENBOARD.HD_NSEQ AS HD_NSEQ,(\tSELECT ME_CKANJINAME FROM MAST_EMPLOYEES WHERE ME_CCUSTOMERID_CK\t= HB_CCUSTOMERID AND ME_CCOMPANYID \t\t= HB_CCOMPANYID AND ME_CEMPLOYEEID_CK\t= HB_CMNUSER AND ME_DSTARTDATE \t\t<= HB_DATEOFANNOUNCEMENT AND ME_DENDDATE\t\t>= HB_DATEOFANNOUNCEMENT ) AS ME_CKANJINAME, (\tSELECT MO_CSECTIONNAME FROM MAST_ORGANISATION WHERE MO_CCUSTOMERID_CK_FK\t= '"
                + vParam.elementAt(4)
                + "' "
                + "AND MO_CCOMPANYID_CK_FK\t= '"
                + vParam.elementAt(0)
                + "' "
                + "AND MO_CSECTIONID_CK\t\t= "
                + "(\tSELECT "
                + "HD_CSECTIONID_FK "
                + "FROM "
                + "HIST_DESIGNATION "
                + "WHERE "
                + "HD_CCUSTOMERID_CK\t= HB_CCUSTOMERID "
                + "AND HD_CCOMPANYID_CK\t= HB_CCOMPANYID "
                + "AND HD_CEMPLOYEEID_CK\t= HB_CMNUSER "
                + "AND HD_DSTARTDATE_CK\t<= HB_DATEOFANNOUNCEMENT "
                + "AND HD_DENDDATE\t\t>= HB_DATEOFANNOUNCEMENT "
                + "AND HD_CIFKEYORADDITIONALROLE = '0' "
                + "AND HD_NOFFCIALORNOT \t= 0 "
                + ") "
                + "AND MO_DSTART\t<= HB_DATEOFANNOUNCEMENT "
                + "AND MO_DEND\t>= HB_DATEOFANNOUNCEMENT "
                + "AND MO_CLANGUAGE = '"
                + vParam.elementAt(3)
                + "' "
                + ") AS HD_CSECTIONID_FK, "
                + "HIST_BULLETIENBOARD.HB_CMNUSER AS HB_CMNUSER, "
                + "HIST_BULLETIENBOARD.HB_DATEOFANNOUNCEMENT AS HB_DATEOFANNOUNCEMENT, "
                + "HIST_BULLETIENBOARD.HB_DATEOFEXPIRE AS HB_DATEOFEXPIRE, "
                + "HIST_BULLETIENBOARD.HB_CFILNAME AS HB_CFILNAME, "
                + "HIST_BULLETIENBOARD.HB_CMNUSERNAME AS HB_CMNUSERNAME, "
                + "HIST_BULLETIENBOARD.HB_CFIX AS HB_CFIX "
                + "FROM "
                + "HIST_BULLETIENBOARD "
                + "WHERE "
                + "HIST_BULLETIENBOARD.HB_CCUSTOMERID\t\t= '"
                + vParam.elementAt(4)
                + "' "
                + "AND HIST_BULLETIENBOARD.HB_CCOMPANYID\t\t= '"
                + vParam.elementAt(0)
                + "' "
                + "AND (\t( "
                + "HIST_BULLETIENBOARD.HB_CFIX = '1' "
                + "AND HIST_BULLETIENBOARD.HB_DATEOFANNOUNCEMENT <= TO_DATE('"
                + vParam.elementAt(2)
                + "','"
                + strDateFormat
                + "') "
                + ") OR ( "
                + "HIST_BULLETIENBOARD.HB_CFIX = '0' "
                + "AND HIST_BULLETIENBOARD.HB_CMNUSER\t= '"
                + vParam.elementAt(5)
                + "' "
                + ") OR ( "
                + "HIST_BULLETIENBOARD.HB_CMNUSER\t= '"
                + vParam.elementAt(5)
                + "' "
                + "AND HIST_BULLETIENBOARD.HB_DATEOFANNOUNCEMENT >= TO_DATE('"
                + vParam.elementAt(2)
                + "','"
                + strDateFormat
                + "') "
                + ") "
                + ") "
                + "AND HIST_BULLETIENBOARD.HD_NDEL\t\t\t= 0 "
                + "ORDER BY "
                + "HIST_BULLETIENBOARD.HB_DATEOFANNOUNCEMENT DESC, "
                + "HIST_BULLETIENBOARD.HD_NSEQ DESC, " + "HIST_BULLETIENBOARD.HB_CMNUSER ASC ");

        return this.SQLString;
    }

    public String Admin_getSupervisor(Vector vParam, String strDateFormat1) {
        this.SQLString = (" Select   HIST_DESIGNATION.HD_CEMPLOYEEID_CK  as  HD_CEMPLOYEEID_CK, MAST_EMPLOYEES.ME_CKANJINAME  as  ME_CKANJINAME, MAST_ORGANISATION.MO_CSECTIONNAME  as  MO_CSECTIONNAME, MAST_POST.MAP_CPOSTNAME  as  MAP_CPOSTNAME,  HIST_DESIGNATION.HD_DSTARTDATE_CK as HD_DSTARTDATE_CK, HIST_DESIGNATION.HD_NOFFCIALORNOT  as  HD_NOFFCIALORNOT  , HIST_DESIGNATION.HD_CSECTIONID_FK  as  HD_CSECTIONID_FK  , '' as HD_CSTATUS,  HIST_DESIGNATION.HD_CPOSTID_FK as HD_CPOSTID_FK, MAST_POST.MAP_NWEIGHTAGE  as  MAP_NWEIGHTAGE   from  MAST_EMPLOYEES,HIST_DESIGNATION,mast_organisation ,MAST_POST   where   MAST_EMPLOYEES.ME_CCUSTOMERID_CK = '"
                + vParam.elementAt(0)
                + "'  and  "
                + " MAST_EMPLOYEES.ME_CCOMPANYID = '"
                + vParam.elementAt(1)
                + "'   and  "
                + " MAST_EMPLOYEES.ME_CEMPLOYEEID_CK = HIST_DESIGNATION.HD_CEMPLOYEEID_CK   and  "
                + " MAST_EMPLOYEES.ME_CCUSTOMERID_CK = HIST_DESIGNATION.HD_CCUSTOMERID_CK  and  "
                + " MAST_EMPLOYEES.ME_CCOMPANYID = HIST_DESIGNATION.HD_CCOMPANYID_CK  and "
                + " mast_organisation.MO_CCUSTOMERID_CK_FK = HIST_DESIGNATION.HD_CCUSTOMERID_CK  and  "
                + " mast_organisation.MO_CCOMPANYID_CK_FK = HIST_DESIGNATION.HD_CCOMPANYID_CK  and  "
                + " MAST_POST.MAP_CCUSTOMERID_CK_FK = HIST_DESIGNATION.HD_CCUSTOMERID_CK  and  "
                + " MAST_POST.MAP_CCOMPANYID_CK_FK = HIST_DESIGNATION.HD_CCOMPANYID_CK  and  "
                + " mast_organisation.MO_CLANGUAGE = '"
                + vParam.elementAt(2)
                + "'  and  "
                + " mast_organisation.MO_CLANGUAGE =MAST_POST.MAP_CLANGUAGE   and  "
                + " mast_organisation.MO_CSECTIONID_CK = HIST_DESIGNATION.HD_CSECTIONID_FK   and  "
                + " MAST_POST.MAP_CPOSTID_CK = HIST_DESIGNATION.HD_CPOSTID_FK  and   "
                + " to_char(MO_DEND,'"
                + strDateFormat1
                + "') >=  '"
                + vParam.elementAt(3)
                + "' and   "
                + " to_char(MO_DSTART,'"
                + strDateFormat1
                + "') <=  '"
                + vParam.elementAt(3)
                + "' and   "
                + " to_char(MAP_DEND,'"
                + strDateFormat1
                + "')  >= '"
                + vParam.elementAt(3)
                + "' and  "
                + " to_char(MAP_DSTART,'"
                + strDateFormat1
                + "')  <= '"
                + vParam.elementAt(3)
                + "' and  "
                + " HIST_DESIGNATION.HD_DSTARTDATE_CK  <=  "
                + " to_date('"
                + vParam.elementAt(3)
                + "','"
                + strDateFormat1
                + "')  AND  "
                + " HIST_DESIGNATION.HD_DENDDATE   >=    "
                + " to_date('"
                + vParam.elementAt(3)
                + "','"
                + strDateFormat1
                + "')  AND  "
                + " MAST_EMPLOYEES.ME_DSTARTDATE  <=  "
                + " to_date('"
                + vParam.elementAt(3)
                + "','"
                + strDateFormat1
                + "')  AND  "
                + " MAST_EMPLOYEES.ME_DENDDATE  >= to_date('"
                + vParam.elementAt(3)
                + "','"
                + strDateFormat1
                + "')  and  "
                + " HIST_DESIGNATION.HD_NOFFCIALORNOT != '0' " + " order by MO_NSEQ , MAP_NWEIGHTAGE , HD_CEMPLOYEEID_CK ");

        return this.SQLString;
    }

    public String Admin_getSupervisorCount(Vector vParam, String strDateFormat1) {
        this.SQLString = ("Select   count(*) as ME_CCOMPANYID   from  MAST_EMPLOYEES,HIST_DESIGNATION,mast_organisation ,MAST_POST   where   MAST_EMPLOYEES.ME_CCUSTOMERID_CK = '"
                + vParam.elementAt(0)
                + "'  and  "
                + " MAST_EMPLOYEES.ME_CCOMPANYID = '"
                + vParam.elementAt(1)
                + "'   and  "
                + " MAST_EMPLOYEES.ME_CEMPLOYEEID_CK = HIST_DESIGNATION.HD_CEMPLOYEEID_CK   and  "
                + " MAST_EMPLOYEES.ME_CCUSTOMERID_CK = HIST_DESIGNATION.HD_CCUSTOMERID_CK  and  "
                + " MAST_EMPLOYEES.ME_CCOMPANYID = HIST_DESIGNATION.HD_CCOMPANYID_CK  and "
                + " mast_organisation.MO_CCUSTOMERID_CK_FK = HIST_DESIGNATION.HD_CCUSTOMERID_CK  and  "
                + " mast_organisation.MO_CCOMPANYID_CK_FK = HIST_DESIGNATION.HD_CCOMPANYID_CK  and  "
                + " MAST_POST.MAP_CCUSTOMERID_CK_FK = HIST_DESIGNATION.HD_CCUSTOMERID_CK  and  "
                + " MAST_POST.MAP_CCOMPANYID_CK_FK = HIST_DESIGNATION.HD_CCOMPANYID_CK  and  "
                + " mast_organisation.MO_CLANGUAGE = '"
                + vParam.elementAt(2)
                + "'  and  "
                + " mast_organisation.MO_CLANGUAGE =MAST_POST.MAP_CLANGUAGE   and  "
                + " mast_organisation.MO_CSECTIONID_CK = HIST_DESIGNATION.HD_CSECTIONID_FK   and  "
                + " MAST_POST.MAP_CPOSTID_CK = HIST_DESIGNATION.HD_CPOSTID_FK  and   "
                + " to_char(MO_DEND,'"
                + strDateFormat1
                + "') >=  '"
                + vParam.elementAt(3)
                + "' and   "
                + " to_char(MO_DSTART,'"
                + strDateFormat1
                + "') <=  '"
                + vParam.elementAt(3)
                + "' and   "
                + " to_char(MAP_DEND,'"
                + strDateFormat1
                + "')  >= '"
                + vParam.elementAt(3)
                + "' and  "
                + " to_char(MAP_DSTART,'"
                + strDateFormat1
                + "')  <= '"
                + vParam.elementAt(3)
                + "' and  "
                + " HIST_DESIGNATION.HD_DSTARTDATE_CK  <=  "
                + " to_date('"
                + vParam.elementAt(3)
                + "','"
                + strDateFormat1
                + "')  AND  "
                + " HIST_DESIGNATION.HD_DENDDATE   >=    "
                + " to_date('"
                + vParam.elementAt(3)
                + "','"
                + strDateFormat1
                + "')  AND  "
                + " MAST_EMPLOYEES.ME_DSTARTDATE  <=  "
                + " to_date('"
                + vParam.elementAt(3)
                + "','"
                + strDateFormat1
                + "')  AND  "
                + " MAST_EMPLOYEES.ME_DENDDATE  >= to_date('"
                + vParam.elementAt(3)
                + "','"
                + strDateFormat1
                + "')  and  "
                + " HIST_DESIGNATION.HD_NOFFCIALORNOT != '0' " + " order by MO_NSEQ , MAP_NWEIGHTAGE , HD_CEMPLOYEEID_CK ");

        return this.SQLString;
    }

    public String Admin_PostTreeBean_Select(Vector vParam, String strDateFormat1) {
        this.SQLString = ("Select MAST_EMPLOYEES.ME_CEMPLOYEEID_CK as ME_CEMPLOYEEID_CK, hist_designation.HD_CPOSTID_FK as HD_CPOSTID_FK,    mast_employees.ME_CKANJINAME as ME_CKANJINAME,  mast_employees.ME_CEMPLOYEEID_CK as ME_CEMPLOYEEID_CK,  mast_post.MAP_NWEIGHTAGE as MAP_NWEIGHTAGE,  mast_post.MAP_CPOSTNAME as MAP_CPOSTNAME  from  MAST_EMPLOYEES, HIST_DESIGNATION,MAST_POST where  HIST_DESIGNATION.HD_CCUSTOMERID_CK  = MAST_POST.MAP_CCUSTOMERID_CK_FK and  HIST_DESIGNATION.HD_CCOMPANYID_CK = MAP_CCOMPANYID_CK_FK and  hist_designation.HD_CPOSTID_FK = mast_post.MAP_CPOSTID_CK and  trunc(mast_post.MAP_DSTART) <= trunc(sysdate) and trunc(mast_post.MAP_DEND) >= trunc(sysdate) and  mast_post.MAP_CLANGUAGE = '"
                + vParam.elementAt(3)
                + "' and "
                + " HIST_DESIGNATION.HD_CIFKEYORADDITIONALROLE = '0' AND "
                + " HIST_DESIGNATION.HD_CCUSTOMERID_CK  = '"
                + vParam.elementAt(0)
                + "' and "
                + " HIST_DESIGNATION.HD_CCOMPANYID_CK = '"
                + vParam.elementAt(1)
                + "' and "
                + " HIST_DESIGNATION.HD_CSECTIONID_FK  = '"
                + vParam.elementAt(2)
                + "' and "
                + " MAST_EMPLOYEES.ME_CCUSTOMERID_CK = HIST_DESIGNATION.HD_CCUSTOMERID_CK AND "
                + " MAST_EMPLOYEES.ME_CEMPLOYEEID_CK  = HIST_DESIGNATION.HD_CEMPLOYEEID_CK AND "
                + " trunc(hist_designation.HD_DSTARTDATE_CK) <= trunc(SYSDATE) AND "
                + " trunc(hist_designation.HD_DENDDATE) >= trunc(SYSDATE) and trunc(ME_DSTARTDATE) <=trunc(SYSDATE) AND trunc(ME_DENDDATE) >=trunc(SYSDATE) " + " ORDER BY  hist_designation.HD_CPOSTID_FK ");

        return this.SQLString;
    }

    public String Admin_getSupervisor1(Vector vParam, String strDateFormat1) {
        this.SQLString = ("Select  HIST_DESIGNATION.HD_CEMPLOYEEID_CK  as  HD_CEMPLOYEEID_CK,MAST_EMPLOYEES.ME_CKANJINAME  as  ME_CKANJINAME,mast_organisation .Mo_CSECTIONNAME  as  MO_CSECTIONNAME,MAST_POST.MAP_Cpostname  as  MAP_CPOSTNAME, HIST_DESIGNATION.HD_DSTARTDATE_CK as HD_DSTARTDATE_CK, HIST_DESIGNATION.HD_NOFFCIALORNOT  as  HD_NOFFCIALORNOT, HIST_DESIGNATION.HD_CSECTIONID_FK  as  HD_CSECTIONID_FK, ''  as HD_CSTATUS, HIST_DESIGNATION.HD_CPOSTID_FK as HD_CPOSTID_FK,   MAST_POST.MAP_NWEIGHTAGE  as  MAP_NWEIGHTAGE    from  MAST_EMPLOYEES,HIST_DESIGNATION,mast_organisation ,MAST_POST   where   MAST_EMPLOYEES.ME_CCUSTOMERID_CK = '"
                + vParam.elementAt(0)
                + "'  and  "
                + " MAST_EMPLOYEES.ME_CCOMPANYID = '"
                + vParam.elementAt(1)
                + "'   and  "
                + " MAST_EMPLOYEES.ME_CEMPLOYEEID_CK = HIST_DESIGNATION.HD_CEMPLOYEEID_CK   and  "
                + " MAST_EMPLOYEES.ME_CCUSTOMERID_CK = HIST_DESIGNATION.HD_CCUSTOMERID_CK  and  "
                + " MAST_EMPLOYEES.ME_CCOMPANYID = HIST_DESIGNATION.HD_CCOMPANYID_CK  and "
                + " mast_organisation.MO_CCUSTOMERID_CK_FK = HIST_DESIGNATION.HD_CCUSTOMERID_CK  and  "
                + " mast_organisation.MO_CCOMPANYID_CK_FK = HIST_DESIGNATION.HD_CCOMPANYID_CK  and  "
                + " MAST_POST.MAP_CCUSTOMERID_CK_FK = HIST_DESIGNATION.HD_CCUSTOMERID_CK  and  "
                + " MAST_POST.MAP_CCOMPANYID_CK_FK = HIST_DESIGNATION.HD_CCOMPANYID_CK  and  "
                + " mast_organisation.MO_CLANGUAGE = '"
                + vParam.elementAt(2)
                + "'  and  "
                + " mast_organisation.MO_CLANGUAGE =MAST_POST.MAP_CLANGUAGE   and  "
                + " mast_organisation.MO_CSECTIONID_CK = HIST_DESIGNATION.HD_CSECTIONID_FK   and  "
                + " MAST_POST.MAP_CPOSTID_CK = HIST_DESIGNATION.HD_CPOSTID_FK  and   "
                + "to_char(MO_DEND,'"
                + strDateFormat1
                + "') >=  '"
                + vParam.elementAt(3)
                + "' and   "
                + "to_char(MO_DSTART,'"
                + strDateFormat1
                + "') <=  '"
                + vParam.elementAt(3)
                + "' and   "
                + "to_char(MAP_DEND,'"
                + strDateFormat1
                + "')  >= '"
                + vParam.elementAt(3)
                + "' and  "
                + "to_char(MAP_DSTART,'"
                + strDateFormat1
                + "')  <= '"
                + vParam.elementAt(3)
                + "' and  "
                + "  HIST_DESIGNATION.HD_DSTARTDATE_CK  <=  "
                + " to_date('"
                + vParam.elementAt(3)
                + "','"
                + strDateFormat1
                + "')  AND  "
                + " HIST_DESIGNATION.HD_DENDDATE   >=    "
                + " to_date('"
                + vParam.elementAt(3)
                + "','"
                + strDateFormat1
                + "')  AND  "
                + " MAST_EMPLOYEES.ME_DSTARTDATE  <=  "
                + " to_date('"
                + vParam.elementAt(3)
                + "','"
                + strDateFormat1
                + "')  AND  "
                + " MAST_EMPLOYEES.ME_DENDDATE  >= to_date('"
                + vParam.elementAt(3)
                + "','"
                + strDateFormat1
                + "')  and  "
                + " HIST_DESIGNATION.HD_NOFFCIALORNOT = '1' and mast_employees.ME_CEMPLOYEEID_CK='"
                + vParam.elementAt(6) + "' " + " order by MO_NSEQ , MAP_NWEIGHTAGE ");

        return this.SQLString;
    }

    public String Insert_Supervisor(Vector vParam, String strDateFormat1,
                                    String strDateFormat2) {
        this.SQLString = ("INSERT INTO HIST_DESIGNATION ( HIST_DESIGNATION.HD_CCUSTOMERID_CK, HIST_DESIGNATION.HD_CCOMPANYID_CK, HIST_DESIGNATION.HD_CEMPLOYEEID_CK, HIST_DESIGNATION.HD_DSTARTDATE_CK, HIST_DESIGNATION.HD_DENDDATE, HIST_DESIGNATION.HD_CMODIFIERUSERID, HIST_DESIGNATION.HD_DMODIFIEDDATE, HIST_DESIGNATION.HD_CIFKEYORADDITIONALROLE, HIST_DESIGNATION.HD_CSECTIONID_FK, HIST_DESIGNATION.HD_CPOSTID_FK, HIST_DESIGNATION.HD_NOFFCIALORNOT ) VALUES ( '"
                + vParam.elementAt(0)
                + "', "
                + "'"
                + vParam.elementAt(1)
                + "', "
                + "'"
                + vParam.elementAt(2)
                + "', "
                + "to_date('"
                + vParam.elementAt(6)
                + "','"
                + strDateFormat2
                + "'), "
                + "to_date('"
                + vParam.elementAt(4)
                + "','"
                + strDateFormat2
                + "'), "
                + "'"
                + vParam.elementAt(5)
                + "', "
                + "to_date('"
                + vParam.elementAt(3)
                + "','"
                + strDateFormat1
                + "'), "
                + "'1', "
                + "'"
                + vParam.elementAt(9)
                + "', "
                + "'"
                + vParam.elementAt(10)
                + "', "
                + "'"
                + vParam.elementAt(11)
                + "' " + ") ");

        return this.SQLString;
    }

    public String Delete_Supervisor(Vector vParam, String strDateFormat1,
                                    String strDateFormat2) {
        this.SQLString = ("DELETE FROM HIST_DESIGNATION WHERE HIST_DESIGNATION.HD_CCUSTOMERID_CK =  '"
                + vParam.elementAt(0)
                + "' "
                + "AND HIST_DESIGNATION.HD_CEMPLOYEEID_CK\t=  '"
                + vParam.elementAt(2)
                + "' "
                + "AND HIST_DESIGNATION.HD_CCOMPANYID_CK\t=  '"
                + vParam.elementAt(1)
                + "' "
                + "AND to_char( HIST_DESIGNATION.HD_DSTARTDATE_CK,'"
                + strDateFormat2
                + "' ) <= '"
                + vParam.elementAt(5)
                + "' "
                + "AND to_char( HIST_DESIGNATION.HD_DENDDATE,\t\t'"
                + strDateFormat2
                + "' ) >= '"
                + vParam.elementAt(5)
                + "' "
                + "AND HIST_DESIGNATION.HD_CSECTIONID_FK\t=  '"
                + vParam.elementAt(3)
                + "' "
                + "AND HIST_DESIGNATION.HD_CPOSTID_FK\t\t=  '"
                + vParam.elementAt(4)
                + "' "
                + "AND HD_CIFKEYORADDITIONALROLE\t\t\t=  '1' " + "AND HD_NOFFCIALORNOT\t\t\t\t\t!= '0'");

        return this.SQLString;
    }

    public String Release_Supervisor(Vector vParam, String strDateFormat1,
                                     String strDateFormat2) {
        this.SQLString = ("UPDATE HIST_DESIGNATION SET HIST_DESIGNATION.HD_DENDDATE\t\t= TO_DATE('"
                + vParam.elementAt(5)
                + "','"
                + strDateFormat2
                + "') "
                + "WHERE "
                + "HIST_DESIGNATION.HD_CCUSTOMERID_CK\t= '"
                + vParam.elementAt(0)
                + "' "
                + "AND HIST_DESIGNATION.HD_CCOMPANYID_CK\t= '"
                + vParam.elementAt(1)
                + "' "
                + "AND HIST_DESIGNATION.HD_CEMPLOYEEID_CK\t= '"
                + vParam.elementAt(2)
                + "' "
                + "AND HIST_DESIGNATION.HD_CSECTIONID_FK\t= '"
                + vParam.elementAt(3)
                + "' "
                + "AND HIST_DESIGNATION.HD_CPOSTID_FK\t\t= '"
                + vParam.elementAt(4)
                + "' "
                + "AND to_char(HIST_DESIGNATION.HD_DSTARTDATE_CK,\t'"
                + strDateFormat2
                + "') <= '"
                + vParam.elementAt(5)
                + "' "
                + "AND to_char(HIST_DESIGNATION.HD_DENDDATE,\t\t'"
                + strDateFormat2
                + "') >= '"
                + vParam.elementAt(5)
                + "' "
                + "AND HIST_DESIGNATION.HD_CIFKEYORADDITIONALROLE = '1' " + "AND HD_NOFFCIALORNOT\t\t\t\t\t!= '0'");

        return this.SQLString;
    }

    public String Relation_getEmployeeByCode(Vector vParam,
                                             String strDateFormat1, String strDateFormat2) {
        this.SQLString = ("SELECT MAST_EMPLOYEES.ME_CEMPLOYEEID_CK AS ME_CEMPLOYEEID_CK, MAST_EMPLOYEES.ME_CKANANAME AS ME_CKANANAME, ( SELECT MAST_ORGANISATION.MO_CSECTIONNAME FROM  MAST_ORGANISATION WHERE MO_CCUSTOMERID_CK_FK\t= '"
                + vParam.elementAt(0)
                + "' "
                + "AND MO_CLANGUAGE\t\t\t= '"
                + vParam.elementAt(4)
                + "' "
                + "AND MO_DEND\t\t\t\t>= TO_DATE( '"
                + vParam.elementAt(3)
                + "', '"
                + strDateFormat1
                + "' ) "
                + "AND MO_DSTART\t\t\t\t<= TO_DATE( '"
                + vParam.elementAt(3)
                + "', '"
                + strDateFormat1
                + "' ) "
                + "AND MO_CSECTIONID_CK\t\t= HD_CSECTIONID_FK "
                + "AND MO_CCOMPANYID_CK_FK\t= '"
                + vParam.elementAt(1)
                + "' "
                + ") AS MO_CSECTIONNAME, "
                + "( "
                + "SELECT "
                + "MAP_CPOSTNAME "
                + "from "
                + "MAST_POST "
                + "WHERE "
                + "MAP_CCUSTOMERID_CK_FK\t= '"
                + vParam.elementAt(0)
                + "' "
                + "AND MAP_CLANGUAGE\t\t\t= '"
                + vParam.elementAt(4)
                + "' "
                + "AND MAP_DEND\t\t\t\t>= TO_DATE( '"
                + vParam.elementAt(3)
                + "', '"
                + strDateFormat1
                + "' ) "
                + "AND MAP_DSTART\t\t\t\t<= TO_DATE( '"
                + vParam.elementAt(3)
                + "', '"
                + strDateFormat1
                + "' ) "
                + "AND MAP_CPOSTID_CK\t\t\t= HD_CPOSTID_FK "
                + "AND MAP_CCOMPANYID_CK_FK\t= '"
                + vParam.elementAt(1)
                + "' "
                + ") AS MAP_CPOSTNAME, "
                + "HIST_DESIGNATION.HD_CSECTIONID_FK AS HD_CSECTIONID_FK, "
                + "HIST_DESIGNATION.HD_CPOSTID_FK AS HD_CPOSTID_FK, "
                + "HIST_DESIGNATION.HD_NOFFCIALORNOT, "
                + "HIST_DESIGNATION.HD_CCOMPANYID_CK AS HD_CCOMPANYID_CK "
                + "FROM "
                + "MAST_EMPLOYEES, "
                + "HIST_DESIGNATION "
                + "WHERE "
                + "MAST_EMPLOYEES.ME_CEMPLOYEEID_CK\t=  '"
                + vParam.elementAt(2)
                + "' "
                + "AND MAST_EMPLOYEES.ME_CCUSTOMERID_CK\t= '"
                + vParam.elementAt(0)
                + "' "
                + "AND MAST_EMPLOYEES.ME_CCOMPANYID\t\t= '"
                + vParam.elementAt(1)
                + "' "
                + "AND HIST_DESIGNATION.HD_CCUSTOMERID_CK\t= MAST_EMPLOYEES.ME_CCUSTOMERID_CK "
                + "AND HIST_DESIGNATION.HD_CCOMPANYID_CK\t= MAST_EMPLOYEES.ME_CCOMPANYID "
                + "AND HIST_DESIGNATION.HD_CEMPLOYEEID_CK\t= MAST_EMPLOYEES.ME_CEMPLOYEEID_CK "
                + "AND HIST_DESIGNATION.HD_DSTARTDATE_CK\t<= TO_DATE( '"
                + vParam.elementAt(3)
                + "', '"
                + strDateFormat1
                + "' ) "
                + "AND HIST_DESIGNATION.HD_DENDDATE\t\t>= TO_DATE( '"
                + vParam.elementAt(3)
                + "', '"
                + strDateFormat1
                + "' ) "
                + "AND HIST_DESIGNATION.HD_CIFKEYORADDITIONALROLE = '0' "
                + "AND MAST_EMPLOYEES.ME_DSTARTDATE\t\t<= TO_DATE( '"
                + vParam.elementAt(3)
                + "', '"
                + strDateFormat1
                + "' ) "
                + "AND MAST_EMPLOYEES.ME_DENDDATE\t\t\t>= TO_DATE( '"
                + vParam.elementAt(3) + "', '" + strDateFormat1 + "' ) ");

        return this.SQLString;
    }

    public String Relation_getEmployeeByName(Vector vParam,
                                             String strDateFormat1, String strDateFormat2,
                                             String psIgnoreRetiredData) {
        String sIgnoreRetiredData = "";
        if (psIgnoreRetiredData.equalsIgnoreCase("yes")) {
            sIgnoreRetiredData = "AND MAST_EMPLOYEES.ME_CIFSTILLEMPLOYEDID = '0' ";
        }
        String sSQL = "SELECT MAST_EMPLOYEES.ME_CEMPLOYEEID_CK as ME_CEMPLOYEEID_CK,MAST_EMPLOYEES.ME_CKANANAME as ME_CKANANAME, (SELECT MAST_ORGANISATION.MO_CSECTIONNAME FROM MAST_ORGANISATION WHERE MO_CCUSTOMERID_CK_FK = '"
                + vParam.elementAt(0)
                + "' "
                + "AND MO_CLANGUAGE = '"
                + vParam.elementAt(4)
                + "' "
                + "AND TO_CHAR(MO_DEND, '"
                + strDateFormat2
                + "') >= '"
                + vParam.elementAt(3)
                + "' "
                + "AND TO_CHAR(MO_DSTART, '"
                + strDateFormat2
                + "') <= '"
                + vParam.elementAt(3)
                + "' "
                + "AND MO_CSECTIONID_CK = HD_CSECTIONID_FK "
                + "AND MO_CCOMPANYID_CK_FK = '"
                + vParam.elementAt(1)
                + "'"
                + ") as MO_CSECTIONNAME,"
                + "(SELECT "
                + "MAP_CPOSTNAME "
                + "FROM "
                + "MAST_POST "
                + "WHERE "
                + "MAP_CCUSTOMERID_CK_FK = '"
                + vParam.elementAt(0)
                + "' "
                + "AND MAP_CLANGUAGE = '"
                + vParam.elementAt(4)
                + "' "
                + "AND TO_CHAR(MAP_DEND, '"
                + strDateFormat2
                + "') >= '"
                + vParam.elementAt(3)
                + "' "
                + "AND TO_CHAR(MAP_DSTART,'"
                + strDateFormat2
                + "')  <= '"
                + vParam.elementAt(3)
                + "' "
                + "AND MAP_CPOSTID_CK = HD_CPOSTID_FK "
                + "AND MAP_CCOMPANYID_CK_FK = '"
                + vParam.elementAt(1)
                + "' "
                + ") as MAP_CPOSTNAME, "
                + "HIST_DESIGNATION.HD_CSECTIONID_FK as HD_CSECTIONID_FK, "
                + "HIST_DESIGNATION.HD_CPOSTID_FK as HD_CPOSTID_FK,HIST_DESIGNATION.HD_NOFFCIALORNOT ,  "
                + "HIST_DESIGNATION.HD_CCOMPANYID_CK as HD_CCOMPANYID_CK ,  "
                + "MAST_EMPLOYEES.ME_CKANJINAME as ME_CKANJINAME , "
                + "MAST_EMPLOYEES.ME_CIFSTILLEMPLOYEDID as ME_CIFSTILLEMPLOYEDID "
                + "FROM "
                + "MAST_EMPLOYEES,"
                + "HIST_DESIGNATION "
                + "WHERE "
                + "MAST_EMPLOYEES.ME_CKANANAME like N'"
                + vParam.elementAt(2)
                + "%' "
                + "AND MAST_EMPLOYEES.ME_CCUSTOMERID_CK = '"
                + vParam.elementAt(0)
                + "' "
                + "AND MAST_EMPLOYEES.ME_CCOMPANYID = '"
                + vParam.elementAt(1)
                + "' "
                + "AND HIST_DESIGNATION.HD_CCUSTOMERID_CK = MAST_EMPLOYEES.ME_CCUSTOMERID_CK "
                + "AND HIST_DESIGNATION.HD_CCOMPANYID_CK = MAST_EMPLOYEES.ME_CCOMPANYID "
                + "AND HIST_DESIGNATION.HD_CEMPLOYEEID_CK = MAST_EMPLOYEES.ME_CEMPLOYEEID_CK "
                + "AND TO_CHAR(HIST_DESIGNATION.HD_DSTARTDATE_CK, '"
                + strDateFormat2
                + "') <= '"
                + vParam.elementAt(3)
                + "' "
                + "AND TO_CHAR(HIST_DESIGNATION.HD_DENDDATE,'"
                + strDateFormat2
                + "') >= '"
                + vParam.elementAt(3)
                + "' "
                + "AND HIST_DESIGNATION.HD_CIFKEYORADDITIONALROLE = '0' "
                + "AND TO_CHAR(MAST_EMPLOYEES.ME_DSTARTDATE, '"
                + strDateFormat2
                + "') <= '"
                + vParam.elementAt(3)
                + "' "
                + "AND TO_CHAR(MAST_EMPLOYEES.ME_DENDDATE, '"
                + strDateFormat2
                + "') >= '"
                + vParam.elementAt(3)
                + "' "
                + sIgnoreRetiredData
                + "ORDER BY " + "MAST_EMPLOYEES.ME_CKANANAME";

        return sSQL;
    }

    public String Relation_getEmployeeByName(Vector vParam,
                                             String strDateFormat1, String strDateFormat2) {
        String sSQL = "SELECT MAST_EMPLOYEES.ME_CEMPLOYEEID_CK as ME_CEMPLOYEEID_CK,MAST_EMPLOYEES.ME_CKANANAME as ME_CKANANAME, (SELECT MAST_ORGANISATION.MO_CSECTIONNAME FROM MAST_ORGANISATION WHERE MO_CCUSTOMERID_CK_FK = '"
                + vParam.elementAt(0)
                + "' "
                + "AND MO_CLANGUAGE = '"
                + vParam.elementAt(4)
                + "' "
                + "AND TO_CHAR(MO_DEND, '"
                + strDateFormat2
                + "') >= '"
                + vParam.elementAt(3)
                + "' "
                + "AND TO_CHAR(MO_DSTART, '"
                + strDateFormat2
                + "') <= '"
                + vParam.elementAt(3)
                + "' "
                + "AND MO_CSECTIONID_CK = HD_CSECTIONID_FK "
                + "AND MO_CCOMPANYID_CK_FK = '"
                + vParam.elementAt(1)
                + "'"
                + ") as MO_CSECTIONNAME,"
                + "(SELECT "
                + "MAP_CPOSTNAME "
                + "FROM "
                + "MAST_POST "
                + "WHERE "
                + "MAP_CCUSTOMERID_CK_FK = '"
                + vParam.elementAt(0)
                + "' "
                + "AND MAP_CLANGUAGE = '"
                + vParam.elementAt(4)
                + "' "
                + "AND TO_CHAR(MAP_DEND, '"
                + strDateFormat2
                + "') >= '"
                + vParam.elementAt(3)
                + "' "
                + "AND TO_CHAR(MAP_DSTART,'"
                + strDateFormat2
                + "')  <= '"
                + vParam.elementAt(3)
                + "' "
                + "AND MAP_CPOSTID_CK = HD_CPOSTID_FK "
                + "AND MAP_CCOMPANYID_CK_FK = '"
                + vParam.elementAt(1)
                + "' "
                + ") as MAP_CPOSTNAME, "
                + "HIST_DESIGNATION.HD_CSECTIONID_FK as HD_CSECTIONID_FK, "
                + "HIST_DESIGNATION.HD_CPOSTID_FK as HD_CPOSTID_FK,HIST_DESIGNATION.HD_NOFFCIALORNOT ,  "
                + "HIST_DESIGNATION.HD_CCOMPANYID_CK as HD_CCOMPANYID_CK ,  "
                + "MAST_EMPLOYEES.ME_CKANJINAME as ME_CKANJINAME , "
                + "MAST_EMPLOYEES.ME_CIFSTILLEMPLOYEDID as ME_CIFSTILLEMPLOYEDID "
                + "FROM "
                + "MAST_EMPLOYEES,"
                + "HIST_DESIGNATION "
                + "WHERE "
                + "MAST_EMPLOYEES.ME_CKANANAME like N'"
                + vParam.elementAt(2)
                + "%' "
                + "AND MAST_EMPLOYEES.ME_CCUSTOMERID_CK = '"
                + vParam.elementAt(0)
                + "' "
                + "AND MAST_EMPLOYEES.ME_CCOMPANYID = '"
                + vParam.elementAt(1)
                + "' "
                + "AND HIST_DESIGNATION.HD_CCUSTOMERID_CK = MAST_EMPLOYEES.ME_CCUSTOMERID_CK "
                + "AND HIST_DESIGNATION.HD_CCOMPANYID_CK = MAST_EMPLOYEES.ME_CCOMPANYID "
                + "AND HIST_DESIGNATION.HD_CEMPLOYEEID_CK = MAST_EMPLOYEES.ME_CEMPLOYEEID_CK "
                + "AND TO_CHAR(HIST_DESIGNATION.HD_DSTARTDATE_CK, '"
                + strDateFormat2
                + "') <= '"
                + vParam.elementAt(3)
                + "' "
                + "AND TO_CHAR(HIST_DESIGNATION.HD_DENDDATE,'"
                + strDateFormat2
                + "') >= '"
                + vParam.elementAt(3)
                + "' "
                + "AND HIST_DESIGNATION.HD_CIFKEYORADDITIONALROLE = '0' "
                + "AND TO_CHAR(MAST_EMPLOYEES.ME_DSTARTDATE, '"
                + strDateFormat2
                + "') <= '"
                + vParam.elementAt(3)
                + "' "
                + "AND TO_CHAR(MAST_EMPLOYEES.ME_DENDDATE, '"
                + strDateFormat2
                + "') >= '"
                + vParam.elementAt(3)
                + "' "
                + "ORDER BY "
                + "MAST_EMPLOYEES.ME_CKANANAME";

        return sSQL;
    }

    public String Admin_getSupervisorforedit(Vector vParam,
                                             String strDateFormat1) {
        this.SQLString = ("Select  HIST_DESIGNATION.HD_CEMPLOYEEID_CK  as  HD_CEMPLOYEEID_CK,MAST_EMPLOYEES.ME_CKANJINAME  as  ME_CKANJINAME,mast_organisation.Mo_CSECTIONNAME  as  MAS_CSECTIONNAME,MAST_POST.MAP_postname as  MAP_CPOSTNAME, MAST_EMPLOYEES.ME_DDATEOFEMPLOYEMENT  as  ME_DDATEOFEMPLOYEMENT    from  MAST_EMPLOYEES,HIST_DESIGNATION,MAST_SECTION,MAST_POST   where   MAST_EMPLOYEES.ME_CCUSTOMERID_CK = '"
                + vParam.elementAt(0)
                + "'  and  "
                + " MAST_EMPLOYEES.ME_CCOMPANYID = '"
                + vParam.elementAt(1)
                + "'   and  "
                + " MAST_EMPLOYEES.ME_CEMPLOYEEID_CK = HIST_DESIGNATION.HD_CEMPLOYEEID_CK   and  "
                + " MAST_EMPLOYEES.ME_CCUSTOMERID_CK = HIST_DESIGNATION.HD_CCUSTOMERID_CK  and  "
                + " MAST_EMPLOYEES.ME_CCOMPANYID = HIST_DESIGNATION.HD_CCOMPANYID_CK  and "
                + " mast_organisation.MAS_CCUSTOMERID_CK_FK = HIST_DESIGNATION.HD_CCUSTOMERID_CK  and  "
                + " mast_organisation.MAS_CCOMPANYID_CK_FK = HIST_DESIGNATION.HD_CCOMPANYID_CK  and  "
                + " MAST_POST.MAP_CCUSTOMERID_CK_FK = HIST_DESIGNATION.HD_CCUSTOMERID_CK  and  "
                + " MAST_POST.MAP_CCOMPANYID_CK_FK = HIST_DESIGNATION.HD_CCOMPANYID_CK  and  "
                + " mast_organisation.MAS_CLANGUAGE = '"
                + vParam.elementAt(2)
                + "'  and  "
                + " mast_organisation.MAS_CLANGUAGE =MAST_POST.MAP_CLANGUAGE   and  "
                + " mast_organisation.MAS_CSECTIONID_CK = HIST_DESIGNATION.HD_CSECTIONID_FK   and  "
                + " MAST_POST.MAP_CPOSTID_CK = HIST_DESIGNATION.HD_CPOSTID_FK  and  "
                + "  HIST_DESIGNATION.HD_DSTARTDATE_CK  <=  "
                + " to_date('"
                + vParam.elementAt(3)
                + "','"
                + strDateFormat1
                + "')  AND  "
                + " HIST_DESIGNATION.HD_DENDDATE   >=    "
                + " to_date('"
                + vParam.elementAt(3)
                + "','"
                + strDateFormat1
                + "')  AND  "
                + " MAST_EMPLOYEES.ME_DSTARTDATE  <=  "
                + " to_date('"
                + vParam.elementAt(3)
                + "','"
                + strDateFormat1
                + "')  AND  "
                + " MAST_EMPLOYEES.ME_DENDDATE  >= to_date('"
                + vParam.elementAt(3)
                + "','"
                + strDateFormat1
                + "')  and  "
                + " HIST_DESIGNATION.HD_CIFKEYORADDITIONALROLE = '0'  and  "
                + " HIST_DESIGNATION.HD_NOFFCIALORNOT != 0  and  "
                + "  HIST_DESIGNATION.HD_CEMPLOYEEID_CK = '"
                + vParam.elementAt(7) + "' ");

        return this.SQLString;
    }

    public String Relation_getAllPost(Vector vParam, String strDateFormat1,
                                      String strDateFormat2) {
        this.SQLString = ("SELECT \tMAP_CPOSTNAME, MAP_CPOSTID_CK FROM \tMAST_POST WHERE \tMAP_CCUSTOMERID_CK_FK = '"
                + vParam.elementAt(0)
                + "' AND "
                + "\tMAP_CCOMPANYID_CK_FK = '"
                + vParam.elementAt(1)
                + "' AND "
                + "\tMAP_DSTART <= TO_DATE('"
                + vParam.elementAt(3)
                + "','"
                + strDateFormat2
                + "') AND "
                + "\tMAP_DEND >= TO_DATE('"
                + vParam.elementAt(3)
                + "','"
                + strDateFormat2
                + "') AND "
                + "\tMAP_CLANGUAGE = '"
                + vParam.elementAt(4) + "' " + "ORDER BY " + "\tMAP_NWEIGHTAGE, MAP_CPOSTID_CK ");

        return this.SQLString;
    }

    public String Update_Supervisor(Vector vParam, String strDateFormat,
                                    String strDateFormat1) {
        this.SQLString = ("UPDATE HIST_DESIGNATION SET HD_CSECTIONID_FK\t=\t'"
                + vParam.elementAt(0) + "', " + "HD_CPOSTID_FK\t\t=\t'"
                + vParam.elementAt(1) + "', " + "HD_CMODIFIERUSERID\t=\t'"
                + vParam.elementAt(3) + "', "
                + "HD_DMODIFIEDDATE\t=\tto_date('" + vParam.elementAt(9)
                + "','" + strDateFormat + "') " + "WHERE "
                + "HD_CCUSTOMERID_CK\t=\t'" + vParam.elementAt(4) + "' "
                + "AND HD_CCOMPANYID_CK\t=\t'" + vParam.elementAt(5) + "' "
                + "AND HD_CEMPLOYEEID_CK\t=\t'" + vParam.elementAt(6) + "' "
                + "AND HD_CSECTIONID_FK\t=\t'" + vParam.elementAt(7) + "' "
                + "AND HD_CPOSTID_FK\t\t=\t'" + vParam.elementAt(8) + "' "
                + "AND HD_NOFFCIALORNOT\t!=\t'0' "
                + "AND HD_DSTARTDATE_CK\t<=\tto_date('" + vParam.elementAt(2)
                + "','" + strDateFormat1 + "') "
                + "AND HD_DENDDATE\t\t>=\tto_date('" + vParam.elementAt(2)
                + "','" + strDateFormat1 + "')");

        return this.SQLString;
    }

    public String Relation_getEmployeeByPost(Vector vParam,
                                             String strDateFormat1, String strDateFormat2) {
        this.SQLString = ("Select MAST_EMPLOYEES.ME_CEMPLOYEEID_CK as ME_CEMPLOYEEID_CK,MAST_EMPLOYEES.ME_CKANANAME as ME_CKANANAME, (select mast_organisation.MO_CSECTIONNAME from mast_organisation  where MO_CCUSTOMERID_CK_FK = '"
                + vParam.elementAt(0)
                + "' and "
                + "MO_CLANGUAGE ='"
                + vParam.elementAt(4)
                + "' and "
                + "to_char(MO_DSTART,'"
                + strDateFormat2
                + "') <=  '"
                + vParam.elementAt(3)
                + "' and "
                + "to_char(MO_DEND,'"
                + strDateFormat2
                + "') >=  '"
                + vParam.elementAt(3)
                + "' and "
                + "MO_CSECTIONID_CK = HD_CSECTIONID_FK and "
                + "MO_CCOMPANYID_CK_FK   ='"
                + vParam.elementAt(1)
                + "') as MO_CSECTIONNAME,"
                + "(select  MAP_CPOSTNAME from mast_post where "
                + "MAP_CCUSTOMERID_CK_FK = '"
                + vParam.elementAt(0)
                + "'  and "
                + "map_clanguage='"
                + vParam.elementAt(4)
                + "' and "
                + "to_char(MAP_DSTART,'"
                + strDateFormat2
                + "')  <= '"
                + vParam.elementAt(3)
                + "' and "
                + "to_char(MAP_DEND,'"
                + strDateFormat2
                + "')  >= '"
                + vParam.elementAt(3)
                + "' "
                + "and MAP_CPOSTID_CK = HD_CPOSTID_FK and MAP_CCOMPANYID_CK_FK ='"
                + vParam.elementAt(1)
                + "' "
                + ") as MAP_CPOSTNAME, "
                + "HIST_DESIGNATION.HD_CSECTIONID_FK as HD_CSECTIONID_FK, "
                + "HIST_DESIGNATION.HD_CPOSTID_FK as HD_CPOSTID_FK,HIST_DESIGNATION.HD_NOFFCIALORNOT , '1'  as HD_CSTATUS    "
                + " from MAST_EMPLOYEES,HIST_DESIGNATION "
                + "where HIST_DESIGNATION.HD_CSECTIONID_FK = '"
                + vParam.elementAt(2)
                + "' AND "
                + "MAST_EMPLOYEES.ME_CCUSTOMERID_CK = '"
                + vParam.elementAt(0)
                + "' and "
                + "MAST_EMPLOYEES.ME_CCOMPANYID = '"
                + vParam.elementAt(1)
                + "' and "
                + "HIST_DESIGNATION.HD_CCUSTOMERID_CK = MAST_EMPLOYEES.ME_CCUSTOMERID_CK AND "
                + "HIST_DESIGNATION.HD_CCOMPANYID_CK = MAST_EMPLOYEES.ME_CCOMPANYID AND "
                + "HIST_DESIGNATION.HD_CEMPLOYEEID_CK = MAST_EMPLOYEES.ME_CEMPLOYEEID_CK AND "
                + "to_char(HIST_DESIGNATION.HD_DSTARTDATE_CK,'"
                + strDateFormat2
                + "') <=  "
                + "'"
                + vParam.elementAt(3)
                + "' and "
                + "to_char(HIST_DESIGNATION.HD_DENDDATE,'"
                + strDateFormat2
                + "') >= "
                + "'"
                + vParam.elementAt(3)
                + "' and HIST_DESIGNATION.HD_CIFKEYORADDITIONALROLE = '0' "
                + "AND to_char(MAST_EMPLOYEES.ME_DSTARTDATE,'"
                + strDateFormat2
                + "') <=  "
                + "'"
                + vParam.elementAt(3)
                + "' AND "
                + " hist_designation.HD_CPOSTID_FK ='"
                + vParam.elementAt(5)
                + "' and  "
                + "to_char(MAST_EMPLOYEES.ME_DENDDATE,'"
                + strDateFormat2 + "') >= '" + vParam.elementAt(3) + "' ");

        return this.SQLString;
    }

    public String Sms_BasicData_Select(Vector vParam, String strDateFormat,
                                       Vector vecParam) {
        this.SQLString = ("SELECT  MAST_EMPLOYEES.ME_CEMPLOYEEID_CK  as ME_CEMPLOYEEID_CK  ,  MAST_EMPLOYEES.ME_CKANANAME as ME_CKANANAME,  MAST_EMPLOYEES.ME_CKANJINAME as ME_CKANJINAME ,  (SELECT  MAST_ORGANISATION.MO_CSECTIONNAME  FROM  MAST_ORGANISATION  WHERE  MO_CCUSTOMERID_CK_FK = '"
                + vecParam.elementAt(0)
                + "'"
                + " AND MO_CLANGUAGE = '"
                + vecParam.elementAt(1)
                + "'"
                + " AND (TO_CHAR(MAST_ORGANISATION.MO_DSTART,'"
                + strDateFormat
                + "')  <= '"
                + vParam.elementAt(1)
                + "' ) "
                + " AND (TO_CHAR(MAST_ORGANISATION.MO_DEND,'"
                + strDateFormat
                + "')  >= '"
                + vParam.elementAt(1)
                + "' ) "
                + " AND MO_CSECTIONID_CK = HD_CSECTIONID_FK "
                + " AND MO_CCOMPANYID_CK_FK = '"
                + vecParam.elementAt(3)
                + "') as HD_CSECTIONID_FK, "
                + " (SELECT "
                + " MAP_CPOSTNAME "
                + " FROM "
                + " MAST_POST "
                + " WHERE "
                + " MAP_CCUSTOMERID_CK_FK = '"
                + vecParam.elementAt(0)
                + "' "
                + " AND  MAP_CLANGUAGE = '"
                + vecParam.elementAt(1)
                + "' "
                + " AND TO_CHAR(MAST_POST.MAP_DSTART,'"
                + strDateFormat
                + "') <=  '"
                + vecParam.elementAt(2)
                + "' "
                + " AND TO_CHAR(MAST_POST.MAP_DEND,'"
                + strDateFormat
                + "') >=  '"
                + vecParam.elementAt(2)
                + "' "
                + " AND MAP_CPOSTID_CK = HD_CPOSTID_FK "
                + " AND MAP_CCOMPANYID_CK_FK = '"
                + vecParam.elementAt(3)
                + "' ) as HD_CPOSTID_FK, "
                + " MAST_EMPLOYEES.ME_CMAIL as ME_CMAIL, "
                + " TO_CHAR(MAST_EMPLOYEES.ME_DDATEOFEMPLOYEMENT, '"
                + strDateFormat
                + "') as ME_DDATEOFEMPLOYEMENT, "
                + " TO_CHAR(MAST_EMPLOYEES.ME_DDATEOFRETIREMENT,'"
                + strDateFormat
                + "') as ME_DDATEOFRETIREMENT ,"
                + " (SELECT "
                + " MAST_COMPANY.MAC_CCOMPANYNAME "
                + " FROM "
                + " MAST_COMPANY "
                + " WHERE "
                + " MAST_COMPANY.MAC_CCUSTOMERID_CK_FK = '"
                + vecParam.elementAt(0)
                + "' "
                + " AND MAST_COMPANY.MAC_CLANGUAGE = '"
                + vecParam.elementAt(1)
                + "' "
                + " AND TO_CHAR(MAC_DSTART,'"
                + strDateFormat
                + "') <= '"
                + vParam.elementAt(1)
                + "' "
                + " AND TO_CHAR(MAC_DEND,'"
                + strDateFormat
                + "') >= '"
                + vParam.elementAt(1)
                + "' "
                + " AND MAC_CCOMPANYID_CK = '"
                + vecParam.elementAt(3)
                + "') as MAC_CCOMPANYNAME "
                + " FROM "
                + " MAST_EMPLOYEES, "
                + " HIST_DESIGNATION "
                + " WHERE "
                + " MAST_EMPLOYEES.ME_CCUSTOMERID_CK = '"
                + vecParam.elementAt(0)
                + "' "
                + " AND MAST_EMPLOYEES.ME_CCOMPANYID = '"
                + vecParam.elementAt(3)
                + "' "
                + " AND MAST_EMPLOYEES.ME_CEMPLOYEEID_CK =  '"
                + vParam.elementAt(0)
                + "' "
                + " AND TO_CHAR(MAST_EMPLOYEES.ME_DSTARTDATE,'"
                + strDateFormat
                + "') <=  '"
                + vParam.elementAt(1)
                + "' "
                + " AND TO_CHAR(MAST_EMPLOYEES.ME_DENDDATE,'"
                + strDateFormat
                + "') >=  '"
                + vParam.elementAt(1)
                + "' "
                + " AND MAST_EMPLOYEES.ME_CCUSTOMERID_CK = HIST_DESIGNATION.HD_CCUSTOMERID_CK "
                + " AND MAST_EMPLOYEES.ME_CCOMPANYID = HIST_DESIGNATION.HD_CCOMPANYID_CK "
                + " AND MAST_EMPLOYEES.ME_CEMPLOYEEID_CK = HIST_DESIGNATION.HD_CEMPLOYEEID_CK "
                + " AND TO_CHAR(HIST_DESIGNATION.HD_DSTARTDATE_CK,'"
                + strDateFormat
                + "') <=  '"
                + vParam.elementAt(1)
                + "' "
                + " AND TO_CHAR(HIST_DESIGNATION.HD_DENDDATE,'"
                + strDateFormat
                + "') >= '" + vParam.elementAt(1) + "' " + " AND HIST_DESIGNATION.HD_CIFKEYORADDITIONALROLE = '0' ");

        return this.SQLString;
    }

    public String Hrm_pi_EmployeeDelSelect(Vector vParam,
                                           String strDateFormat1, String strDateFormat2) {
        this.SQLString = ("select HIST_DESIGNATION.HD_CSECTIONID_FK as HD_CSECTIONID_FK ,HIST_DESIGNATION.HD_CPOSTID_FK as HD_CPOSTID_FK,HIST_DESIGNATION.HD_CCOMPANYID_CK as HD_CCOMPANYID_CK,MAST_EMPLOYEES.ME_CEMPLOYEEID_CK as ME_CEMPLOYEEID_CK,MAST_EMPLOYEES.ME_CKANJINAME as ME_CKANJINAME,MAST_EMPLOYEES.ME_CKANANAME as ME_CKANANAME,MAST_EMPLOYEES.ME_CENGLISHNAME as ME_CENGLISHNAME,MAST_COMPANY.MAC_CCOMPANYNAME as MAC_CCOMPANYNAME,MAST_organisation.Mo_CSECTIONNAME as MO_CSECTIONNAME,MAST_POST.MAP_Cpostname as MAP_CPOSTNAME ,MAST_EMPLOYEES.ME_DSTARTDATE as ME_DSTARTDATE from HIST_DESIGNATION,MAST_EMPLOYEES,MAST_organisation,MAST_POST,MAST_COMPANY  where MAST_EMPLOYEES.ME_CEMPLOYEEID_CK = '"
                + vParam.elementAt(0)
                + "' and "
                + "MAST_EMPLOYEES.ME_CCUSTOMERID_CK = '"
                + vParam.elementAt(2)
                + "' and "
                + "MAST_EMPLOYEES.ME_CCOMPANYID = '"
                + vParam.elementAt(1)
                + "' and "
                + "MAST_POST.MAP_CPOSTID_CK = '"
                + vParam.elementAt(4)
                + "' and "
                + "MAST_organisation.Mo_CSECTIONID_CK = '"
                + vParam.elementAt(5)
                + "' and "
                + "HIST_DESIGNATION.HD_DENDDATE >= to_date('"
                + vParam.elementAt(3)
                + "','"
                + strDateFormat2
                + "') and "
                + "mast_employees.me_ccustomerid_ck = hist_designation.hd_ccustomerid_ck and "
                + "mast_employees.me_cemployeeid_ck = hist_designation.hd_cemployeeid_ck and "
                + "MAST_EMPLOYEES.ME_CCOMPANYID = HIST_DESIGNATION.HD_CCOMPANYID_CK and "
                + "MAST_COMPANY.MAC_CCOMPANYID_CK = MAST_EMPLOYEES.ME_CCOMPANYID and "
                + "MAST_COMPANY.MAC_CCUSTOMERID_CK_FK = MAST_EMPLOYEES.ME_CCUSTOMERID_CK and "
                + "MAST_COMPANY.MAC_CLANGUAGE = '"
                + vParam.elementAt(6)
                + "' and "
                + "MAST_organisation.Mo_CCUSTOMERID_CK_FK = MAST_EMPLOYEES.ME_CCUSTOMERID_CK and "
                + "MAST_organisation.Mo_CCOMPANYID_CK_FK = MAST_EMPLOYEES.ME_CCOMPANYID and "
                + "HIST_DESIGNATION.HD_CIFKEYORADDITIONALROLE = '0' and "
                + "MAST_organisation.Mo_CLANGUAGE = '"
                + vParam.elementAt(6)
                + "' and  "
                + "MAST_POST.MAP_CCUSTOMERID_CK_FK = MAST_EMPLOYEES.ME_CCUSTOMERID_CK  and "
                + "MAST_POST.MAP_CCOMPANYID_CK_FK = MAST_EMPLOYEES.ME_CCOMPANYID and "
                + "MAST_POST.MAP_CLANGUAGE = '"
                + vParam.elementAt(6)
                + "' and "
                + "MAST_POST.MAP_CPOSTID_CK = hist_designation.HD_CPOSTID_FK and "
                + "MAST_organisation.Mo_CSECTIONID_CK = hist_designation.HD_CSECTIONID_FK and "
                + "to_char(MAST_EMPLOYEES.ME_DSTARTDATE,'"
                + strDateFormat2
                + "') <= '"
                + vParam.elementAt(3)
                + "' and "
                + "to_char(MAST_EMPLOYEES.ME_DENDDATE,'"
                + strDateFormat2
                + "') >= '"
                + vParam.elementAt(3)
                + "' and "
                + "to_char(HIST_DESIGNATION.HD_DSTARTDATE_CK,'"
                + strDateFormat2
                + "') <= '"
                + vParam.elementAt(3)
                + "' and "
                + "to_char(HIST_DESIGNATION.HD_DENDDATE,'"
                + strDateFormat2
                + "') >= '"
                + vParam.elementAt(3)
                + "' and "
                + "to_char(MAP_DSTART,'"
                + strDateFormat2
                + "')   <= '"
                + vParam.elementAt(3)
                + "' and "
                + "to_char(MAP_DEND,'"
                + strDateFormat2
                + "')   >= '"
                + vParam.elementAt(3)
                + "' and "
                + "(to_char(MAST_organisation.Mo_DSTART,'"
                + strDateFormat2
                + "')  <= '"
                + vParam.elementAt(3)
                + "' ) AND "
                + "( to_char(MAST_organisation.Mo_DEND,'"
                + strDateFormat2
                + "')  >= '"
                + vParam.elementAt(3)
                + "' ) and "
                + "( to_char(MAST_COMPANY.MAC_DSTART,'"
                + strDateFormat2
                + "')  <= '"
                + vParam.elementAt(3)
                + "' ) and "
                + "( to_char(MAST_COMPANY.MAC_DEND,'"
                + strDateFormat2
                + "')  >= '" + vParam.elementAt(3) + "' ) ");

        return this.SQLString;
    }

    public String Hrm_pi_EmployeeDelete1(Vector vParam, String strDateFormat1,
                                         String strDateFormat2) {
        this.SQLString = (" Delete from MAST_EMPLOYEES  where   MAST_EMPLOYEES.ME_CCUSTOMERID_CK = '"
                + vParam.elementAt(3)
                + "' and MAST_EMPLOYEES.ME_CEMPLOYEEID_CK = '"
                + vParam.elementAt(1)
                + "' "
                + "AND ME_CCOMPANYID = '"
                + vParam.elementAt(2) + "'");

        return this.SQLString;
    }

    public String Hrm_pi_EmployeeDelete2(Vector vParam, String strDateFormat1,
                                         String strDateFormat2) {
        this.SQLString = ("Delete from  HIST_DESIGNATION   where HIST_DESIGNATION.HD_CCUSTOMERID_CK = '"
                + vParam.elementAt(3)
                + "' and HIST_DESIGNATION.HD_CEMPLOYEEID_CK = '"
                + vParam.elementAt(1)
                + "' "
                + "and HIST_DESIGNATION.HD_CCOMPANYID_CK = '"
                + vParam.elementAt(2) + "'");

        return this.SQLString;
    }

    public String Hrm_pi_EmployeeDelete3(Vector vParam, String strDateFormat1,
                                         String strDateFormat2) {
        this.SQLString = ("Delete from  MAST_PASSWORD   where MAST_PASSWORD.MAP_CCUSTOMERID_CK_FK = '"
                + vParam.elementAt(3)
                + "' and MAST_PASSWORD.MAP_CEMPLOYEEID_CK_FK = '"
                + vParam.elementAt(1)
                + "' "
                + "and MAST_PASSWORD.MAP_CCOMPANYID_CK_FK = '"
                + vParam.elementAt(2) + "'");

        return this.SQLString;
    }

    public String Hrm_pi_EmployeeDelete4(Vector vParam, String strDateFormat1,
                                         String strDateFormat2) {
        this.SQLString = ("Delete from  MAST_SSCSYSTEMCODE  where MAST_SSCSYSTEMCODE.MS_CCUSTOMERID_CK_FK = '"
                + vParam.elementAt(3)
                + "' and MAST_SSCSYSTEMCODE.MS_CEMPLOYEEID_CK_FK = '"
                + vParam.elementAt(1)
                + "' "
                + "and MAST_SSCSYSTEMCODE.MS_CCOMPANYID_CK_FK = '"
                + vParam.elementAt(2) + "'");

        return this.SQLString;
    }

    public String Hrm_pi_EmployeeDelete5(Vector vParam, String strDateFormat1,
                                         String strDateFormat2) {
        this.SQLString = ("Delete from  HIST_BULLETIENBOARD  where HIST_BULLETIENBOARD.HB_CCUSTOMERID  = '"
                + vParam.elementAt(3)
                + "' and HIST_BULLETIENBOARD.HB_CMNUSER = '"
                + vParam.elementAt(1)
                + "' "
                + "and HIST_BULLETIENBOARD.HB_CCOMPANYID = '"
                + vParam.elementAt(2) + "'");

        return this.SQLString;
    }

    public String Hrm_pi_complement_searchOrg(Vector vParam,
                                              String strDateFormat, String strDateFormat1,
                                              String psIgnoreRetiredData) {
        String sIgnoreRetiredData = "";
        if (psIgnoreRetiredData.equalsIgnoreCase("yes")) {
            sIgnoreRetiredData = "AND MAST_EMPLOYEES.ME_CIFSTILLEMPLOYEDID = '0' ";
        }
        this.SQLString = ("SELECT     MAST_EMPLOYEES.ME_CEMPLOYEEID_CK AS ME_CEMPLOYEEID_CK,    MAST_EMPLOYEES.ME_CKANJINAME AS ME_CKANJINAME,    MAST_EMPLOYEES.ME_CKANANAME AS ME_CKANANAME,    MAST_POST.MAP_CPOSTNAME AS MAP_CPOSTNAME,    MAST_ORGANISATION.MO_CSECTIONNAME AS MO_CSECTIONNAME,    MAST_EMPLOYEES.ME_CIFSTILLEMPLOYEDID AS ME_CIFSTILLEMPLOYEDID FROM     MAST_EMPLOYEES,    HIST_DESIGNATION,    MAST_POST,    MAST_ORGANISATION WHERE     MAST_EMPLOYEES.ME_CCUSTOMERID_CK = '"
                + vParam.elementAt(5)
                + "' AND"
                + "    MAST_EMPLOYEES.ME_CCOMPANYID = '"
                + vParam.elementAt(3)
                + "' AND"
                + "    MAST_EMPLOYEES.ME_DSTARTDATE <= TO_DATE('"
                + vParam.elementAt(1)
                + "','"
                + strDateFormat
                + "') AND"
                + "    MAST_EMPLOYEES.ME_DENDDATE >= TO_DATE('"
                + vParam.elementAt(1)
                + "','"
                + strDateFormat
                + "') AND"
                + "    HIST_DESIGNATION.HD_CCUSTOMERID_CK = MAST_EMPLOYEES.ME_CCUSTOMERID_CK AND"
                + "    HIST_DESIGNATION.HD_CCOMPANYID_CK = MAST_EMPLOYEES.ME_CCOMPANYID AND"
                + "    HIST_DESIGNATION.HD_CEMPLOYEEID_CK = MAST_EMPLOYEES.ME_CEMPLOYEEID_CK AND"
                + "    HIST_DESIGNATION.HD_DSTARTDATE_CK <= TO_DATE('"
                + vParam.elementAt(1)
                + "','"
                + strDateFormat
                + "') AND"
                + "    HIST_DESIGNATION.HD_DENDDATE >= TO_DATE('"
                + vParam.elementAt(1)
                + "','"
                + strDateFormat
                + "') AND"
                + "    HIST_DESIGNATION.HD_NOFFCIALORNOT = 0 AND"
                + "    HIST_DESIGNATION.HD_CSECTIONID_FK = '"
                + vParam.elementAt(6)
                + "' AND"
                + "    MAST_POST.MAP_CCUSTOMERID_CK_FK =  HIST_DESIGNATION.HD_CCUSTOMERID_CK  AND"
                + "    MAST_POST.MAP_CCOMPANYID_CK_FK =  HIST_DESIGNATION.HD_CCOMPANYID_CK AND"
                + "    MAST_POST.MAP_DSTART <= TO_DATE('"
                + vParam.elementAt(1)
                + "','"
                + strDateFormat
                + "') AND"
                + "    MAST_POST.MAP_DEND >= TO_DATE('"
                + vParam.elementAt(1)
                + "','"
                + strDateFormat
                + "') AND"
                + "    MAST_POST.MAP_CPOSTID_CK = HIST_DESIGNATION.HD_CPOSTID_FK AND"
                + "    MAST_POST.MAP_CLANGUAGE = '"
                + vParam.elementAt(4)
                + "' AND"
                + "    MAST_ORGANISATION.MO_CCUSTOMERID_CK_FK =  HIST_DESIGNATION.HD_CCUSTOMERID_CK  AND"
                + "    MAST_ORGANISATION.MO_CCOMPANYID_CK_FK =  HIST_DESIGNATION.HD_CCOMPANYID_CK AND"
                + "    MAST_ORGANISATION.MO_CSECTIONID_CK = HIST_DESIGNATION.HD_CSECTIONID_FK AND"
                + "    MAST_ORGANISATION.MO_DSTART <= TO_DATE('"
                + vParam.elementAt(1)
                + "','"
                + strDateFormat
                + "') AND"
                + "    MAST_ORGANISATION.MO_DEND >= TO_DATE('"
                + vParam.elementAt(1)
                + "','"
                + strDateFormat
                + "') AND"
                + "    MAST_ORGANISATION.MO_CLANGUAGE = '"
                + vParam.elementAt(4)
                + "' "
                + sIgnoreRetiredData
                + "ORDER BY"
                + "    MAST_POST.MAP_NWEIGHTAGE,"
                + "    HIST_DESIGNATION.HD_DSTARTDATE_CK," + "    MAST_EMPLOYEES.ME_CEMPLOYEEID_CK");

        return this.SQLString;
    }

    public String SearchByOrgDBBean_Select(Vector vParam, String strDateFormat) {
        this.SQLString = (" select distinct MAST_EMPLOYEES.ME_CEMPLOYEEID_CK as ME_CEMPLOYEEID_CK,  MAST_EMPLOYEES.ME_CKANANAME as ME_CKANANAME,  MAST_EMPLOYEES.ME_CKANJINAME as ME_CKANJINAME, mast_post.map_cpostname as map_cpostname, mast_organisation.mo_csectionname as mo_csectionname, (select mast_generic_detail.mgd_cmastercode from mast_generic_detail where  mgd_clanguage_ck = '"
                + vParam.elementAt(3)
                + "'  and mast_generic_detail.mgd_cmastercode =MAST_EMPLOYEES.ME_CIFSTILLEMPLOYEDID and "
                + " to_char(mgd_dstart_ck,'"
                + strDateFormat
                + "') <=  '"
                + vParam.elementAt(1)
                + "' and to_char(mgd_dend,'"
                + strDateFormat
                + "') >=  '"
                + vParam.elementAt(1)
                + "' and MAST_GENERIC_DETAIL.MGD_CCUSTOMERID = '"
                + vParam.elementAt(4)
                + "'  "
                + " and MAST_GENERIC_DETAIL.MGD_CCOMPANYID_CK_FK = '"
                + vParam.elementAt(0)
                + "' ) as ME_CIFSTILLEMPLOYEDID "
                + " FROM MAST_EMPLOYEES, HIST_DESIGNATION,mast_post,mast_organisation WHERE  "
                + " to_char(MAST_EMPLOYEES.ME_DSTARTDATE,'"
                + strDateFormat
                + "') <=  '"
                + vParam.elementAt(1)
                + "' AND  "
                + " to_char(MAST_EMPLOYEES.ME_DENDDATE,'"
                + strDateFormat
                + "') >=  '"
                + vParam.elementAt(1)
                + "' AND "
                + " HIST_DESIGNATION.HD_CCUSTOMERID_CK=MAST_EMPLOYEES.ME_CCUSTOMERID_CK AND "
                + " HIST_DESIGNATION.HD_CCOMPANYID_CK=MAST_EMPLOYEES.ME_CCOMPANYID AND "
                + " HIST_DESIGNATION.HD_CEMPLOYEEID_CK=MAST_EMPLOYEES.ME_CEMPLOYEEID_CK AND "
                + " HIST_DESIGNATION.HD_CIFKEYORADDITIONALROLE = '0' AND   "
                + " ( to_char(HIST_DESIGNATION.HD_DSTARTDATE_CK,'"
                + strDateFormat
                + "')  <= '"
                + vParam.elementAt(1)
                + "' ) AND "
                + " ( to_char(HIST_DESIGNATION.HD_DENDDATE,'"
                + strDateFormat
                + "')  >= '"
                + vParam.elementAt(1)
                + "' ) AND "
                + " MAST_POST.MAP_CCUSTOMERID_CK_FK =  HIST_DESIGNATION.HD_CCUSTOMERID_CK  and "
                + " MAST_POST.MAP_CCOMPANYID_CK_FK =  HIST_DESIGNATION.HD_CCOMPANYID_CK and "
                + " ( to_char(MAST_POST.MAP_DSTART,'"
                + strDateFormat
                + "')  <= '"
                + vParam.elementAt(1)
                + "' ) AND "
                + " ( to_char(MAST_POST.MAP_DEND,'"
                + strDateFormat
                + "')  >= '"
                + vParam.elementAt(1)
                + "' ) AND "
                + " MAST_ORGANISATION.MO_CCUSTOMERID_CK_FK =  HIST_DESIGNATION.HD_CCUSTOMERID_CK  and "
                + " MAST_ORGANISATION.MO_CCOMPANYID_CK_FK =  HIST_DESIGNATION.HD_CCOMPANYID_CK and "
                + " MAST_ORGANISATION.MO_CSECTIONID_CK = HIST_DESIGNATION.HD_CSECTIONID_FK and "
                + " ( to_char(MAST_ORGANISATION.MO_DSTART,'"
                + strDateFormat
                + "')  <= '"
                + vParam.elementAt(1)
                + "' ) AND "
                + " ( to_char(MAST_ORGANISATION.MO_DEND,'"
                + strDateFormat
                + "')  >= '"
                + vParam.elementAt(1)
                + "' ) AND "
                + " hist_designation.HD_CPOSTID_FK=mast_post.MAP_CPOSTID_CK and "
                + " mast_post.MAP_CLANGUAGE = '"
                + vParam.elementAt(3)
                + "' AND "
                + " HIST_DESIGNATION.HD_CCUSTOMERID_CK='"
                + vParam.elementAt(4)
                + "' AND "
                + " HIST_DESIGNATION.HD_CCOMPANYID_CK='"
                + vParam.elementAt(0)
                + "' and MAST_EMPLOYEES.ME_CKANANAME LIKE N'%"
                + vParam.elementAt(5) + "%'");

        return this.SQLString;
    }

    public String CrStartBeanList_Select(Vector vParam) {
        String SQLString = "  SELECT MD_CTABLENAME,MD_CCOLUMNNAME FROM Mast_DATADICTIONARY ,MAST_DATACUST  where MD_CID = MDC_CID_CK_FK And mdc_clanguage_ck='"
                + vParam.elementAt(0)
                + "' AND "
                + "  MD_CCUSTOMERID ='"
                + vParam.elementAt(1)
                + "' AND MD_CCOMPANYID = '"
                + vParam.elementAt(2) + "'" + " order by  MD_CTABLENAME ";

        return SQLString;
    }

    public String cr_loadJoken_Select(Vector vParam) {
        this.SQLString = ("SELECT distinct HICS_CXAXIS_ITEMSEQ1 as HICS_CXAXIS_ITEMSEQ1,HICS_CXAXIS_DATETYPE1 as  HICS_CXAXIS_DATETYPE1,HICS_NXAXIS_PITCH1 as HICS_NXAXIS_PITCH1,HICS_NXAXIS_MIN1 as HICS_NXAXIS_MIN1,  HICS_NXAXIS_MAX1 as HICS_NXAXIS_MAX1,  HICS_NXAXIS_SUM as HICS_NXAXIS_SUM,  ( SELECT md_ctablename FROM mast_datadictionary WHERE md_cid=hist_crosssearch_select.HICS_CXAXIS_ITEMSEQ1) as HICS_CXAXIS_ITEMSEQ1,  ( SELECT md_ccolumnname FROM mast_datadictionary WHERE md_cid=hist_crosssearch_select.HICS_CXAXIS_ITEMSEQ1) as HICS_CXAXIS_ITEMSEQ1,  ( SELECT md_cmastertblname FROM mast_datadictionary WHERE md_cid=hist_crosssearch_select.HICS_CXAXIS_ITEMSEQ1) as HICS_CXAXIS_ITEMSEQ1,  ( SELECT md_ccalculatecolumn FROM mast_datadictionary WHERE md_cid=hist_crosssearch_select.HICS_CXAXIS_ITEMSEQ1) as HICS_CXAXIS_ITEMSEQ1,  (SELECT mdc_ccolumndesc FROM mast_datacust,mast_datadictionary WHERE mast_datadictionary.md_cid=hist_crosssearch_select.HICS_CXAXIS_ITEMSEQ1  and MD_CID=MDC_CID_CK_FK  and MDC_CLANGUAGE_CK='"
                + vParam.elementAt(4)
                + "') as HICS_CXAXIS_ITEMSEQ1, "
                + " HICS_CXAXIS_ITEMSEQ2 as HICS_CXAXIS_ITEMSEQ2,HICS_CXAXIS_DATETYPE2 as HICS_CXAXIS_DATETYPE2, HICS_NXAXIS_PITCH2 as HICS_NXAXIS_PITCH2, "
                + " HICS_NXAXIS_MIN2 as HICS_NXAXIS_MIN2, HICS_NXAXIS_MAX2 as HICS_NXAXIS_MAX2,"
                + " ( SELECT md_ctablename FROM mast_datadictionary WHERE md_cid=hist_crosssearch_select.HICS_CXAXIS_ITEMSEQ2) as HICS_CXAXIS_ITEMSEQ2, "
                + " ( SELECT md_ccolumnname FROM mast_datadictionary WHERE md_cid=hist_crosssearch_select.HICS_CXAXIS_ITEMSEQ2) as HICS_CXAXIS_ITEMSEQ2, "
                + " ( SELECT md_cmastertblname FROM mast_datadictionary WHERE md_cid=hist_crosssearch_select.HICS_CXAXIS_ITEMSEQ2) as HICS_CXAXIS_ITEMSEQ2, "
                + " ( SELECT md_ccalculatecolumn FROM mast_datadictionary WHERE md_cid=hist_crosssearch_select.HICS_CXAXIS_ITEMSEQ2) as HICS_CXAXIS_ITEMSEQ2, "
                + " (SELECT mdc_ccolumndesc FROM mast_datacust,mast_datadictionary WHERE mast_datadictionary.md_cid=hist_crosssearch_select.HICS_CXAXIS_ITEMSEQ2 "
                + " and MD_CID=MDC_CID_CK_FK  and MDC_CLANGUAGE_CK='"
                + vParam.elementAt(4)
                + "') as HICS_CXAXIS_ITEMSEQ2, "
                + " HICS_CYAXIS_ITEMSEQ1 as HICS_CYAXIS_ITEMSEQ1,"
                + " HICS_CYAXIS_DATETYPE1 as HICS_CYAXIS_DATETYPE1,HICS_NYAXIS_PITCH1 as HICS_NYAXIS_PITCH1,HICS_NYAXIS_MIN1 as HICS_NYAXIS_MIN1,"
                + " HICS_NYAXIS_MAX1 as HICS_NYAXIS_MAX1,HICS_NYAXIS_SUM as HICS_NYAXIS_SUM,"
                + " ( SELECT md_ctablename FROM mast_datadictionary WHERE md_cid=hist_crosssearch_select.HICS_CYAXIS_ITEMSEQ1) as HICS_CYAXIS_ITEMSEQ1, "
                + " ( SELECT md_ccolumnname FROM mast_datadictionary WHERE md_cid=hist_crosssearch_select.HICS_CYAXIS_ITEMSEQ1)  as HICS_CYAXIS_ITEMSEQ1, "
                + " ( SELECT md_cmastertblname FROM mast_datadictionary WHERE md_cid=hist_crosssearch_select.HICS_CYAXIS_ITEMSEQ1) as HICS_CYAXIS_ITEMSEQ1, "
                + " ( SELECT md_ccalculatecolumn FROM mast_datadictionary WHERE md_cid=hist_crosssearch_select.HICS_CYAXIS_ITEMSEQ1) as HICS_CYAXIS_ITEMSEQ1, "
                + " (SELECT mdc_ccolumndesc FROM mast_datacust,mast_datadictionary WHERE mast_datadictionary.md_cid=hist_crosssearch_select.HICS_CYAXIS_ITEMSEQ1 "
                + " and MD_CID=MDC_CID_CK_FK  and MDC_CLANGUAGE_CK='"
                + vParam.elementAt(4)
                + "') as HICS_CYAXIS_ITEMSEQ1, "
                + " HICS_CYAXIS_ITEMSEQ2 as HICS_CYAXIS_ITEMSEQ2,"
                + " HICS_CYAXIS_DATETYPE2 as HICS_CYAXIS_DATETYPE2, HICS_NYAXIS_PITCH2 as HICS_NYAXIS_PITCH2,HICS_NYAXIS_MIN2 as HICS_NYAXIS_MIN2,"
                + " HICS_NYAXIS_MAX2 as HICS_NYAXIS_MAX2, "
                + " ( SELECT md_ctablename FROM mast_datadictionary WHERE md_cid=hist_crosssearch_select.HICS_CYAXIS_ITEMSEQ2) as HICS_CYAXIS_ITEMSEQ2, "
                + " ( SELECT md_ccolumnname FROM mast_datadictionary WHERE md_cid=hist_crosssearch_select.HICS_CYAXIS_ITEMSEQ2) as HICS_CYAXIS_ITEMSEQ2, "
                + " ( SELECT md_cmastertblname FROM mast_datadictionary WHERE md_cid=hist_crosssearch_select.HICS_CYAXIS_ITEMSEQ2) as HICS_CYAXIS_ITEMSEQ2, "
                + " ( SELECT md_ccalculatecolumn FROM mast_datadictionary WHERE md_cid=hist_crosssearch_select.HICS_CYAXIS_ITEMSEQ2) as HICS_CYAXIS_ITEMSEQ2, "
                + " (SELECT mdc_ccolumndesc FROM mast_datacust,mast_datadictionary WHERE mast_datadictionary.md_cid=hist_crosssearch_select.HICS_CYAXIS_ITEMSEQ2 "
                + " and MD_CID=MDC_CID_CK_FK  and MDC_CLANGUAGE_CK='"
                + vParam.elementAt(4)
                + "') as HICS_CYAXIS_ITEMSEQ2, "
                + " HICS_CSUM_ITEMSEQ as HICS_CSUM_ITEMSEQ,HICS_NCOUNT as HICS_NCOUNT, HICS_NAVG as HICS_NAVG, "
                + " ( SELECT md_ctablename FROM mast_datadictionary WHERE md_cid=hist_crosssearch_select.HICS_CSUM_ITEMSEQ) as HICS_CSUM_ITEMSEQ, "
                + " ( SELECT md_ccolumnname FROM mast_datadictionary WHERE md_cid=hist_crosssearch_select.HICS_CSUM_ITEMSEQ) as HICS_CSUM_ITEMSEQ, "
                + " ( SELECT md_cexcepteddatatype FROM mast_datadictionary WHERE md_cid=hist_crosssearch_select.HICS_CSUM_ITEMSEQ) as HICS_CSUM_ITEMSEQ, "
                + " ( SELECT md_cmastertblname FROM mast_datadictionary WHERE md_cid=hist_crosssearch_select.HICS_CSUM_ITEMSEQ) as HICS_CSUM_ITEMSEQ, "
                + " ( SELECT md_ccalculatecolumn FROM mast_datadictionary WHERE md_cid=hist_crosssearch_select.HICS_CSUM_ITEMSEQ) as HICS_CSUM_ITEMSEQ, "
                + " (SELECT mdc_ccolumndesc FROM mast_datacust,mast_datadictionary WHERE mast_datadictionary.md_cid=hist_crosssearch_select.HICS_CSUM_ITEMSEQ"
                + " and MD_CID=MDC_CID_CK_FK  and MDC_CLANGUAGE_CK='"
                + vParam.elementAt(4)
                + "') as HICS_CSUM_ITEMSEQ,  "
                + " ( SELECT md_cexcepteddatatype FROM mast_datadictionary WHERE md_cid=hist_crosssearch_select.HICS_CXAXIS_ITEMSEQ1) as HICS_CXAXIS_ITEMSEQ1, "
                + " ( SELECT md_cexcepteddatatype FROM mast_datadictionary WHERE md_cid=hist_crosssearch_select.HICS_CXAXIS_ITEMSEQ2) as HICS_CXAXIS_ITEMSEQ2, "
                + " ( SELECT md_cexcepteddatatype FROM mast_datadictionary WHERE md_cid=hist_crosssearch_select.HICS_CYAXIS_ITEMSEQ1) as HICS_CYAXIS_ITEMSEQ1, "
                + " ( SELECT md_cexcepteddatatype FROM mast_datadictionary WHERE md_cid=hist_crosssearch_select.HICS_CYAXIS_ITEMSEQ2) as HICS_CYAXIS_ITEMSEQ2, "
                + " HCS_CIGNORECASE as HCS_CIGNORECASE, "
                + " HCS_CNODATANOOUTPUT as HCS_CNODATANOOUTPUT "
                + " FROM "
                + " HIST_CROSSSEARCH_SELECT , HIST_CROSSSEARCH "
                + " WHERE "
                + " HCS_CCUSTOMERID_CK = '"
                + vParam.elementAt(0)
                + "' "
                + " AND HCS_CCOMPANYID_CK = '"
                + vParam.elementAt(1)
                + "' "
                + " AND HCS_CFILENAME = N'"
                + vParam.elementAt(3)
                + "' "
                + " AND HCS_CIFPUBLIC = '"
                + vParam.elementAt(4)
                + "' "
                + " AND HCS_CCUSTOMERID_CK = HICS_CCUSTOMERID_CK "
                + " AND HCS_CCOMPANYID_CK = HICS_CCOMPANYID_CK "
                + " AND HCS_CEMPLOYEEID_CK = HICS_CEMPLOYEEID_CK "
                + " AND HCS_CFILENAME = HICS_CFILENAME_CK " + " AND HCS_CIFPUBLIC = HICS_CIFPUBLIC ");

        return this.SQLString;
    }

    public String cr_loadJoken_Select1(Vector vParam) {
        this.SQLString = ("SELECT distinct HSW_CITEMSEQ as HSW_CITEMSEQ,  ( SELECT md_ctablename FROM mast_datadictionary WHERE md_cid=hist_crosssearch_where.HSW_CITEMSEQ) as HSW_CITEMSEQ,  ( SELECT md_ccolumnname FROM mast_datadictionary WHERE md_cid=hist_crosssearch_where.HSW_CITEMSEQ) as HSW_CITEMSEQ,  (SELECT MDC_CCOLUMNDESC  FROM mast_datacust,mast_datadictionary WHERE mast_datadictionary.md_cid=hist_crosssearch_where.HSW_CITEMSEQ  and MD_CID=MDC_CID_CK_FK  and MDC_CLANGUAGE_CK='"
                + vParam.elementAt(5)
                + "') as HSW_CITEMSEQ, "
                + " ( SELECT md_cmastertblname FROM mast_datadictionary WHERE md_cid=hist_crosssearch_where.HSW_CITEMSEQ) as HSW_CITEMSEQ, "
                + " ( SELECT  md_ccalculatecolumn  FROM mast_datadictionary WHERE md_cid=hist_crosssearch_where.HSW_CITEMSEQ) as HSW_CITEMSEQ, "
                + " ( SELECT MD_CEXCEPTEDDATATYPE  FROM mast_datadictionary WHERE md_cid=hist_crosssearch_where.HSW_CITEMSEQ) as HSW_CITEMSEQ, "
                + " HSW_CANDOR as HSW_CANDOR,HSW_CLPARENTHESIS as HSW_CLPARENTHESIS,HSW_COPERATOR as HSW_COPERATOR, "
                + " HSW_CVALUE as HSW_CVALUE,HSW_CRPARENTHESIS as HSW_CRPARENTHESIS,HSW_NSEQ as HSW_NSEQ "
                + " FROM "
                + " HIST_CROSSSEARCH_WHERE , HIST_CROSSSEARCH "
                + " WHERE "
                + " HCS_CCUSTOMERID_CK = '"
                + vParam.elementAt(0)
                + "' "
                + " AND HCS_CCOMPANYID_CK = '"
                + vParam.elementAt(1)
                + "' "
                + " AND HCS_CFILENAME = N'"
                + vParam.elementAt(3)
                + "' "
                + " AND HCS_CIFPUBLIC = '"
                + vParam.elementAt(4)
                + "' "
                + " AND HCS_CCUSTOMERID_CK = HSW_CCUSTOMERID_CK "
                + " AND HCS_CCOMPANYID_CK = HSW_CCOMPANYID_CK "
                + " AND HCS_CEMPLOYEEID_CK = HSW_CEMPLOYEEID_CK "
                + " AND HCS_CFILENAME = HSW_CFILENAME_CK "
                + " AND HCS_CIFPUBLIC = HSW_CIFPUBLIC " + " ORDER BY " + " HSW_NSEQ");

        return this.SQLString;
    }

    public String Jk_loadDelListBean(Vector vParam) {
        this.SQLString = ("SELECT distinct HCS_CCUSTOMERID_CK as HCS_CCUSTOMERID_CK,HCS_CCOMPANYID_CK as  HCS_CCOMPANYID_CK,HCS_CEMPLOYEEID_CK as HCS_CEMPLOYEEID_CK,  HCS_CFILENAME as HCS_CFILENAME, HCS_CIFPUBLIC as HCS_CIFPUBLIC  FROM  HIST_CROSSSEARCH  WHERE HCS_CCUSTOMERID_CK = '"
                + vParam.elementAt(0)
                + "' AND HCS_CCOMPANYID_CK = '"
                + vParam.elementAt(1)
                + "'"
                + " AND (HCS_CEMPLOYEEID_CK ='"
                + vParam.elementAt(2) + "'" + " OR (HCS_CIFPUBLIC = '1') )" + " ORDER BY HCS_CCUSTOMERID_CK,HCS_CCOMPANYID_CK,HCS_CFILENAME ");

        return this.SQLString;
    }

    public String Jk_loadListBean(Vector vParam) {
        this.SQLString = ("SELECT distinct HCS_CCUSTOMERID_CK as HCS_CCUSTOMERID_CK,HCS_CCOMPANYID_CK as  HCS_CCOMPANYID_CK,HCS_CEMPLOYEEID_CK as HCS_CEMPLOYEEID_CK,  HCS_CFILENAME as HCS_CFILENAME, HCS_CIFPUBLIC as HCS_CIFPUBLIC  FROM  HIST_CROSSSEARCH  WHERE (HCS_CCUSTOMERID_CK = '"
                + vParam.elementAt(0)
                + "' AND HCS_CCOMPANYID_CK = '"
                + vParam.elementAt(1)
                + "'"
                + " AND HCS_CEMPLOYEEID_CK ='"
                + vParam.elementAt(2) + "'" + " OR (HCS_CIFPUBLIC = '1') )" + " ORDER BY HCS_CCUSTOMERID_CK,HCS_CCOMPANYID_CK,HCS_CFILENAME ");

        return this.SQLString;
    }

    public String Cr_Jyoken_mailId_Select(Vector vParam, String strDateFormat1) {
        this.SQLString = ("SELECT distinct  MAST_EMPLOYEES.ME_CEMPLOYEEID_CK as ME_CEMPLOYEEID_CK ,  MAST_EMPLOYEES.ME_CMAIL as ME_CMAIL,   MAST_EMPLOYEES.ME_CENGLISHNAME as ME_CENGLISHNAME,  MAST_EMPLOYEES.ME_CKANJINAME as ME_CKANJINAME,  hist_designation.HD_CCOMPANYID_CK as HD_CCOMPANYID_CK  from hist_designation, MAST_EMPLOYEES  where  hist_designation.HD_CCUSTOMERID_CK = '"
                + vParam.elementAt(0)
                + "' and "
                + " HIST_DESIGNATION.HD_CCOMPANYID_CK  = '"
                + vParam.elementAt(1)
                + "' and  "
                + " hist_designation.HD_CCUSTOMERID_CK =  MAST_EMPLOYEES.ME_CCUSTOMERID_CK and "
                + " hist_designation.HD_CEMPLOYEEID_CK = MAST_EMPLOYEES.ME_CEMPLOYEEID_CK and "
                + " HIST_DESIGNATION.HD_CIFKEYORADDITIONALROLE = '0' AND "
                + " trunc(HIST_DESIGNATION.HD_DSTARTDATE_CK)  <= trunc(sysdate) AND "
                + " trunc(HIST_DESIGNATION.HD_DENDDATE)     >=   trunc(sysdate) AND "
                + " trunc(MAST_EMPLOYEES.ME_DSTARTDATE)  <= trunc(sysdate) AND " + " trunc(MAST_EMPLOYEES.ME_DENDDATE)     >=   trunc(sysdate)  ");

        return this.SQLString;
    }

    public String Jk_searchID_delete(Vector param, String strDateFormat1) {
        String query = " SELECT HSE_SEARCH_ID FROM HIST_SEARCH  WHERE HSE_CFILENAME_CK='"
                + param.elementAt(0)
                + "'  AND  HSE_CIFPUBLIC ='"
                + param.elementAt(1) + "'";

        return query;
    }

    public String Jk_listSettings_Select(Vector param, String strDateFormat1) {
        String query = " SELECT  HSE_CIFPUBLIC, HSE_CFILENAME_CK FROM HIST_SEARCH WHERE (HSE_CCUSTOMERID_CK='"
                + param.elementAt(0)
                + "' and HSE_CCOMPANYID_CK='"
                + param.elementAt(1)
                + "' and HSE_CEMPLOYEEID_CK='"
                + param.elementAt(2)
                + "') OR (HSE_CIFPUBLIC='on') ORDER BY HSE_CFILENAME_CK";

        return query;
    }

    public String Jk_deleteSettings_Master(Vector param) {
        String query = " DELETE FROM HIST_SEARCH  WHERE HSE_SEARCH_ID='"
                + param.elementAt(0) + "' ";
        return query;
    }

    public String Jk_deleteSettings_Select(Vector param) {
        String query = " DELETE from HIST_SEARCH_SELECT where HISS_SEARCH_ID= '"
                + param.elementAt(0) + "'";
        return query;
    }

    public String Jk_deleteSettings_Order(Vector param) {
        String query = " DELETE FROM HIST_SEARCH_ORDER  WHERE HISO_SEARCH_ID= '"
                + param.elementAt(0) + "' ";
        return query;
    }

    public String Jk_deleteSettings_Where(Vector param) {
        String query = " DELETE  FROM HIST_SELECT_WHERE WHERE HISW_SEARCH_ID='"
                + param.elementAt(0) + "'";
        return query;
    }

    public String Jk_deleteSettings_Aggregate(Vector param) {
        String query = " DELETE FROM HIST_SEARCH_TOTAL  WHERE HIST_SEARCH_ID= '"
                + param.elementAt(0) + "' ";
        return query;
    }

    public String Jk_deleteSettings_Group(Vector param) {
        String query = " DELETE FROM HIST_SEARCH_GROUP  WHERE HISG_SEARCH_ID= '"
                + param.elementAt(0) + "' ";
        return query;
    }

    public String Jk_Settings_Count(Vector param, String strDateFormat1) {
        String query = " select nvl(max(to_number(HSE_SEARCH_ID)),0)+1 as HSE_SEARCH_ID from hist_search ";

        return query;
    }

    public String Jk_saveSettings_Master(Vector param) {
        param = SysUtil.replaceEscape(param);

        String query = " INSERT INTO HIST_SEARCH(HSE_SEARCH_ID, HSE_CCUSTOMERID_CK, HSE_CCOMPANYID_CK, HSE_CEMPLOYEEID_CK, HSE_CFILENAME_CK, HSE_CIFPUBLIC, HSE_DDATE, HSE_CIGNORECASE, HSE_GRANDTOTAL ,HSE_WHERECLAUSE,HSE_ORDERCLAUSE,HSE_AGGCLAUSE,HSE_GROUPCLAUSE,HSE_COL_FOR_AGG,HSE_NSHOWRECORDS) values  ('"
                + param.elementAt(0)
                + "' , '"
                + param.elementAt(1)
                + "' , '"
                + param.elementAt(2)
                + "'  , '"
                + param.elementAt(3)
                + "'  , '"
                + param.elementAt(4)
                + "'  , '"
                + param.elementAt(5)
                + "' ,null , '"
                + param.elementAt(7)
                + "' , '"
                + param.elementAt(8)
                + "',N'"
                + param.elementAt(9)
                + "' ,N'"
                + param.elementAt(10)
                + "',N'"
                + param.elementAt(11)
                + "',N'"
                + param.elementAt(12)
                + "' ,N'"
                + param.elementAt(13)
                + "',"
                + param.elementAt(14) + ")";

        return query;
    }

    public String Jk_saveSettings_Select(Vector param) {
        param = SysUtil.replaceEscape(param);
        String query = " INSERT INTO HIST_SEARCH_SELECT (HISS_SEARCH_ID, HISS_NSEQ, HISS_CITEMSEQ) values  ('"
                + param.elementAt(0)
                + "' , "
                + param.elementAt(1)
                + " , '"
                + param.elementAt(2) + "' )";

        return query;
    }

    public String Jk_saveSettings_Where(Vector param) {
        param = SysUtil.replaceEscape(param);
        String query = " INSERT INTO HIST_SELECT_WHERE (HISW_SEARCH_ID, HISW_NSEQ,  HISW_CANDOR, HISW_CLPARENTHESIS, HISW_CITEMSEQ, HISW_COPERATOR, HISW_CVALUE, HISW_CRPARENTHESIS) values  ('"
                + param.elementAt(0)
                + "' , "
                + param.elementAt(1)
                + " , '"
                + param.elementAt(2)
                + "'  , '"
                + param.elementAt(3)
                + "'  , '"
                + param.elementAt(4)
                + "'  , '"
                + param.elementAt(5)
                + "'  , N'"
                + param.elementAt(6)
                + "'  , '"
                + param.elementAt(7) + "' ) ";

        return query;
    }

    public String Jk_saveSettings_Order(Vector param) {
        param = SysUtil.replaceEscape(param);
        String query = " INSERT INTO HIST_SEARCH_ORDER ( HISO_SEARCH_ID, HISO_NSEQ_CK, HISO_CITEMSEQ, HISO_CIFDESCENDING) values  ('"
                + param.elementAt(0)
                + "' , "
                + param.elementAt(1)
                + " , '"
                + param.elementAt(2) + "'  , '" + param.elementAt(3) + "' ) ";

        return query;
    }

    public String Jk_saveSettings_Aggregate(Vector param) {
        param = SysUtil.replaceEscape(param);
        String query = " INSERT INTO HIST_SEARCH_TOTAL( HIST_CITEMSEQ, HIST_CAVG, HIST_CSUBTOTAL, HIST_CMAX, HIST_CMIN, HIST_CGRANDTOTAL, HIST_NSEQ_CK, HIST_SEARCH_ID) VALUES  ('"
                + param.elementAt(0)
                + "' , '"
                + param.elementAt(1)
                + "' , '"
                + param.elementAt(2)
                + "'  , '"
                + param.elementAt(3)
                + "'  , '"
                + param.elementAt(4)
                + "'  , '"
                + param.elementAt(5)
                + "'  , "
                + param.elementAt(6) + "  , '" + param.elementAt(7) + "' )";

        return query;
    }

    public String Jk_saveSettings_Group(Vector param) {
        param = SysUtil.replaceEscape(param);
        String query = " INSERT INTO HIST_SEARCH_GROUP(HISG_NSEQ_CK, HISG_CITEMSEQ, HISG_SEARCH_ID) VALUES  ('"
                + param.elementAt(0)
                + "' , '"
                + param.elementAt(1)
                + "' , '"
                + param.elementAt(2) + "')";

        return query;
    }

    public String JkStart_mailId_Select(Vector vParam, String strDateFormat1) {
        this.SQLString = ("SELECT distinct  MAST_EMPLOYEES.ME_CEMPLOYEEID_CK as ME_CEMPLOYEEID_CK ,  MAST_EMPLOYEES.ME_CMAIL as ME_CMAIL,   MAST_EMPLOYEES.ME_CENGLISHNAME as ME_CENGLISHNAME,  MAST_EMPLOYEES.ME_CKANJINAME as ME_CKANJINAME,  hist_designation.HD_CCOMPANYID_CK as HD_CCOMPANYID_CK  from hist_designation, MAST_EMPLOYEES  where  hist_designation.HD_CCUSTOMERID_CK = '"
                + vParam.elementAt(0)
                + "' and "
                + " HIST_DESIGNATION.HD_CCOMPANYID_CK  = '"
                + vParam.elementAt(1)
                + "' and  "
                + " hist_designation.HD_CCUSTOMERID_CK =  MAST_EMPLOYEES.ME_CCUSTOMERID_CK and "
                + " HIST_DESIGNATION.HD_CCOMPANYID_CK  = MAST_EMPLOYEES.ME_CCOMPANYID and "
                + " hist_designation.HD_CEMPLOYEEID_CK = MAST_EMPLOYEES.ME_CEMPLOYEEID_CK and "
                + " HIST_DESIGNATION.HD_CIFKEYORADDITIONALROLE = '0' AND "
                + " trunc(HIST_DESIGNATION.HD_DSTARTDATE_CK)  <= trunc(sysdate) AND "
                + " trunc(HIST_DESIGNATION.HD_DENDDATE)     >=   trunc(sysdate) AND "
                + " trunc(MAST_EMPLOYEES.ME_DSTARTDATE)  <= trunc(sysdate) AND " + " trunc(MAST_EMPLOYEES.ME_DENDDATE)     >=   trunc(sysdate) ");
        if (vParam.size() > 2) {
            this.SQLString += " and  ME_CEMPLOYEEID_CK in ( ";
            for (int i = 2; i < vParam.size() - 1; i++) {
                this.SQLString = (this.SQLString + "'" + vParam.elementAt(i) + "' , ");
            }
            this.SQLString = (this.SQLString + "'"
                    + vParam.elementAt(vParam.size() - 1) + "' ) ");
        }
        return this.SQLString;
    }

    public String Ps_ChangePassword_Select(Vector vParam) {
        this.SQLString = ("SELECT '"
                + vParam.elementAt(3)
                + "' as HD_CSECTIONID_FK,  '"
                + vParam.elementAt(4)
                + "' as HD_CPOSTID_FK, "
                + " '"
                + vParam.elementAt(2)
                + "' as HD_CCOMPANYID_CK,  '"
                + vParam.elementAt(1)
                + "' as HD_CEMPLOYEEID_CK, "
                + " MAP_CPASSWORD FROM MAST_PASSWORD , Hist_designation WHERE "
                + " MAP_CCUSTOMERID_CK_FK = '"
                + vParam.elementAt(0)
                + "' AND "
                + " MAP_CEMPLOYEEID_CK_FK='"
                + vParam.elementAt(1)
                + "' AND "
                + " MAP_CCOMPANYID_CK_FK='"
                + vParam.elementAt(2)
                + "' and "
                + " Hist_designation.HD_CCUSTOMERID_CK = '"
                + vParam.elementAt(0)
                + "' AND "
                + " Hist_designation.HD_CCOMPANYID_CK = '"
                + vParam.elementAt(2)
                + "' AND "
                + " Hist_designation.HD_CEMPLOYEEID_CK = '"
                + vParam.elementAt(1)
                + "' AND "
                + " Hist_designation.HD_CIFKEYORADDITIONALROLE = '0' AND "
                + " trunc(Hist_designation.HD_DSTARTDATE_CK) <= trunc(sysdate) and "
                + " trunc(Hist_designation.HD_DENDDATE) >= trunc(sysdate) " + " ORDER BY MAP_DSTART DESC");

        return this.SQLString;
    }

    public String JkStart_mailIdLogged_Select(Vector vParam,
                                              String strDateFormat1) {
        this.SQLString = ("SELECT DISTINCT MAST_EMPLOYEES.ME_CEMPLOYEEID_CK as ME_CEMPLOYEEID_CK, MAST_EMPLOYEES.ME_CMAIL as ME_CMAIL FROM MAST_EMPLOYEES, HIST_DESIGNATION WHERE MAST_EMPLOYEES.ME_CCUSTOMERID_CK\t= '"
                + vParam.elementAt(1)
                + "' "
                + "AND MAST_EMPLOYEES.ME_CCOMPANYID\t\t= '"
                + vParam.elementAt(2)
                + "' "
                + "AND MAST_EMPLOYEES.ME_CEMPLOYEEID_CK\t= '"
                + vParam.elementAt(0)
                + "' "
                + "AND MAST_EMPLOYEES.ME_DSTARTDATE\t\t<= TO_DATE( '"
                + vParam.elementAt(3)
                + "', '"
                + strDateFormat1
                + "' ) "
                + "AND MAST_EMPLOYEES.ME_DENDDATE\t\t\t>= TO_DATE( '"
                + vParam.elementAt(3)
                + "', '"
                + strDateFormat1
                + "' ) "
                + "AND HIST_DESIGNATION.HD_CCUSTOMERID_CK\t= MAST_EMPLOYEES.ME_CCUSTOMERID_CK "
                + "AND HIST_DESIGNATION.HD_CCOMPANYID_CK\t= MAST_EMPLOYEES.ME_CCOMPANYID "
                + "AND HIST_DESIGNATION.HD_CEMPLOYEEID_CK\t= MAST_EMPLOYEES.ME_CEMPLOYEEID_CK "
                + "AND HIST_DESIGNATION.HD_DSTARTDATE_CK\t<= TO_DATE( '"
                + vParam.elementAt(3)
                + "', '"
                + strDateFormat1
                + "' ) "
                + "AND HIST_DESIGNATION.HD_DENDDATE\t\t>= TO_DATE( '"
                + vParam.elementAt(3) + "', '" + strDateFormat1 + "' ) " + "AND HIST_DESIGNATION.HD_CIFKEYORADDITIONALROLE = '0'");

        return this.SQLString;
    }

    public String getTreeFileName(Vector vParam, String strDateFormat) {
        this.SQLString = ("\tSELECT  HOT_CFILENAME , '01', HOT_CCUSTOMERID_CK , HOT_CCOMPANYID_CK  FROM  HIST_ORGANISATION_TREEFILES  WHERE  HOT_CCUSTOMERID_CK ='"
                + vParam.elementAt(0)
                + "' AND   HOT_CCOMPANYID_CK = '"
                + vParam.elementAt(1)
                + "'  AND   to_char(HOT_DSTARTDATE_CK,'"
                + strDateFormat
                + "') <=  '"
                + vParam.elementAt(2)
                + "'  AND  "
                + " to_char(HOT_DENDDATE,'"
                + strDateFormat
                + "') >= '"
                + vParam.elementAt(2) + "' ");

        return this.SQLString;
    }

    public String Cr_Start_PostList(Vector vParam, String strDateFormat1) {
        this.SQLString = ("select MAP_CPOSTID_CK AS MAP_CPOSTID_CK, MAP_CPOSTNAME as MAP_CPOSTNAME from mast_post where MAP_CCUSTOMERID_CK_FK = '"
                + vParam.elementAt(0)
                + "'"
                + "  and "
                + " to_char(MAP_DSTART,'"
                + strDateFormat1
                + "')   <= '"
                + vParam.elementAt(2)
                + "' and "
                + " to_char(MAP_DEND,'"
                + strDateFormat1
                + "')   >= '"
                + vParam.elementAt(2)
                + "' and "
                + " MAP_CLANGUAGE = '"
                + vParam.elementAt(1)
                + "' and "
                + " MAP_CCOMPANYID_CK_FK = '" + vParam.elementAt(3) + "' order by MAP_NWEIGHTAGE ");

        return this.SQLString;
    }
}

