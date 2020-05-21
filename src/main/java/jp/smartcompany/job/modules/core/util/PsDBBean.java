package jp.smartcompany.job.modules.core.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.CalendarUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.job.modules.core.business.BaseSectionBusiness;
import jp.smartcompany.job.modules.core.pojo.bo.BaseSectionBO;
import jp.smartcompany.job.modules.core.service.IHistDesignationService;
import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import jp.smartcompany.job.modules.core.service.IMastOrganisationService;
import jp.smartcompany.job.util.SysUtil;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 直接通过Spring注入即可使用
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class PsDBBean {

    public static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd";

    protected String compCode;
    protected String employeeCode;
    protected String strUserID;
    protected String custID;
    protected String systemCode;
    protected String targetUser = "";
    protected String targetComp = "";
    protected String targetDept = "";
    protected String targetCust = "";
    protected String replacedCompCode = "";
    protected String replacedUserID = "";
    protected String iGroupID = null;
    protected boolean setSysFlg = false;
    protected Vector GsPostCode = null;
    protected Vector dept = null;
    protected Object ObjectQueries = null;
    protected String strGUID = null;

    private final BaseSectionBusiness baseSectionBusiness;
    private final IMastOrganisationService iMastOrganisationService;
    private final IMastEmployeesService iMastEmployeesService;
    private final IHistDesignationService iHistDesignationService;
    public final HttpSession session;
    private final HttpServletRequest request;

    // 日付フォーマット
    private final String DATE_FORMAT = "yyyy/MM/dd";
    /** 更新用エラーコード */
    private static final int UPDATE_ERROR_CODE = -2;

    /** システムコード */
    private static final String SYSTEM_CODE_01 = "01";

    /** データベースモード */
    private static final String DATABASE_MODE = "1";

    /** ドメインコード */
    private static final String DOMAIN_CODE = "01";

    public Hashtable requestHash;
    private String language;

    public void setGroupID(String param) {
        this.iGroupID = param;
    }

    public String getGroupID() {
        return this.iGroupID;
    }

    public void setPostID(Vector param) {
        this.GsPostCode = param;
    }

    public Vector getPostID() {
        return this.GsPostCode;
    }

    public void setDept(Vector value) {
        this.dept = value;
    }

    public Vector getDept() {
        return this.dept;
    }

    public String getReqParam(String sKey) {
        Object oVal = null;
        oVal = requestHash.get(sKey);
        if (oVal == null) {
            return null;
        }
        return oVal.toString();
    }

    public String getSysDate() {
        return getDataBaseDate();
    }

    private String getDataBaseDate() {
        try {
            return DateUtil.format(DateUtil.date(), DEFAULT_DATE_FORMAT);
        } catch (Exception e) {
        }
        return null;
    }

    public String getSiteId() {
        return (String)this.requestHash.get("SiteId");
    }

    public String getUserCode() {
        return this.strUserID;
    }

    public void setUserCode(String value) {
        this.strUserID = value;
    }

    public String escDBString(String sString) {
        return SysUtil.transStringNullToDB(
                SysUtil.escapeQuote(sString));
    }

    private String getCurrTimeStamp() {
        Calendar cal = CalendarUtil.calendar();
        int nYYYY = cal.get(1);
        int nMM = cal.get(2) + 1;
        int nDD = cal.get(5);
        int nHH = cal.get(11);
        int nMI = cal.get(12);
        int nSS = cal.get(13);
        int nNN = cal.get(14);

        String sYYYY = nYYYY + "";
        String sMM = nMM + "";
        if (nMM < 10) {
            sMM = "0" + nMM;
        }
        String sDD = nDD + "";
        if (nDD < 10) {
            sDD = "0" + nDD;
        }
        String sHH = nHH + "";
        if (nHH < 10) {
            sHH = "0" + nHH;
        }
        String sMI = nMI + "";
        if (nMI < 10) {
            sMI = "0" + nMI;
        }
        String sSS = nSS + "";
        if (nSS < 10) {
            sSS = "0" + nSS;
        }
        String sNN = nNN + "";
        if (nNN < 100) {
            sNN = "0" + nNN;
        }
        if (nNN < 10) {
            sNN = "00" + nNN;
        }
        String sTimeStamp = sYYYY + "/" + sMM + "/" + sDD + " " + sHH + ":"
                + sMI + ":" + sSS + "." + sNN;

        return sTimeStamp;
    }

    public void setSysControl(Hashtable<String,Object> requestHash){
        this.requestHash = requestHash;

        setCompCode((String)this.requestHash.get("CompCode"));
        setGroupID((String)this.requestHash.get("GroupCode"));
        setUserCode((String)this.requestHash.get("UserCode"));
        setCustID((String)requestHash.get("CustID"));
        setTargetUser((String)requestHash.get("EmployeeCode"));
        setSystemCode((String)requestHash.get("SystemCode"));

//        setPostID((Vector) this.requestHash.get("PostCode"));
//        setDept((Vector) this.requestHash.get("Dept"));

        if (this.requestHash.get("CreterialDate") != null) {
            setCreterialDate1((String) this.requestHash.get("CreterialDate"));
            setCreterialDate2((String) this.requestHash.get("CreterialDate"));
        }

//        setQueriesObject(this.requestHash.get("Queries"));

//        if (this.requestHash.containsKey("ReplacedCompCode")) {
//            setReplacedCompCode((String)this.requestHash
//                    .get("ReplacedCompCode"));
//        } else {
//            setReplacedCompCode("");
//        }
//        if (this.requestHash.containsKey("ReplacedUserCode")) {
//            setReplacedUserCode((String)this.requestHash
//                    .get("ReplacedUserCode"));
//        } else {
//            setReplacedUserCode("");
//        }

        if (this.requestHash.containsKey("EmployeeCode") && this.requestHash.get("EmployeeCode") != null) {
            setEmployeeCode((String)this.requestHash.get("EmployeeCode"));
        } else {
            setEmployeeCode((String)this.requestHash.get("UserCode"));
        }

        setLanguage((String)this.requestHash.get("Language"));

        if (this.requestHash.get("targetComp") != null) {
            setTargetComp((String)this.requestHash.get("targetComp"));
        }
        if (this.requestHash.get("sectionid") != null) {
            setTargetDept((String)this.requestHash.get("sectionid"));
        }
        if (this.requestHash.get("compid") != null) {
            setTargetComp((String)this.requestHash.get("compid"));
        }
        if (this.requestHash.get("custid") != null) {
            setTargetCust((String)this.requestHash.get("custid"));
        }
        if (this.targetComp == null) {
            this.targetComp = "";
            this.targetDept = "";
            this.targetCust = "";
        } else if (StrUtil.isBlank(targetComp)) {
            this.targetComp = "";
            this.targetDept = "";
            this.targetCust = "";
        }


//        Hashtable siteurls = (Hashtable) this.requestHash.get("SitePermission");
//        setDomObject((Document) siteurls.get(getCustID() + "_" + getCompCode()
//                + "_" + getSystemCode() + "_" + getGroupID()));
//
//        this.setSysFlg = true;
//        if (this.requestHash.get("PageName") != null) {
//            setStrBeanName((String) this.requestHash.get("PageName"));
//        }
    }

    private void setQueriesObject(Object objQueries) {
        this.ObjectQueries = objQueries;
    }

    public Queries getQueriesObject() {
        return (Queries) this.ObjectQueries;
    }


    /**
     ***======**
     */
    protected String getLowerSectionListUser(String psCustID, String psCompID,
                                             String psEmployeeID, String psCreterialDate) {
        return "";
    }

    /**
     *
     * 旧代码中以下两个方法都未做其他处理
     * 指定社員の下位組織リストをSQL用のカンマ区切りで取得します
     * 社員指定、仮想組織含むか
     * @param psCustID 顧客コード
     * @param psCompID 法人コード
     * @param psEmployeeID 社員番号
     * @param psCreterialDate 基準日
     * @param pbIncludeVirtual 仮想組織含むか
     * @return String 組織コードリスト
     */
    protected String getLowerSectionListUser(String psCustID, String psCompID,
                                             String psEmployeeID, String psCreterialDate,
                                             boolean pbIncludeVirtual) {
        return "";
    }
    /**======**/

    protected String getLowerSectionList(String psCustID, String psCompID,
                                         String psSection, String psCreterialDate) {
        String sLowerSectionList = "";
            List <String> lowerSectionSysInfoList =
                    iMastOrganisationService.selectLowerSection(psCustID, psCompID, psSection, DateUtil.parse(psCreterialDate,DATE_FORMAT));
            if (CollUtil.isEmpty(lowerSectionSysInfoList)) {
                return null;
            }
            List<String> sectionIdList = CollUtil.newArrayList();
            sectionIdList.addAll(lowerSectionSysInfoList);
            sLowerSectionList = sectionIdList.toString();
            return sLowerSectionList;
    }

    /**
     * 指定組織の上位組織リストをSQL用のカンマ区切りで取得します
     * @param psCustID 顧客コード
     * @param psCompID 法人コード
     * @param psSection 組織コード
     * @param psCreterialDate 基準日
     * @return String 組織コードリスト
     */
    protected String getUpperSectionList(String psCustID, String psCompID,
                                         String psSection, String psCreterialDate) {
        String sUpperSectionList = "";
        sUpperSectionList =
                iMastOrganisationService.selectHighSection(
                        psCustID, psCompID, psSection,
                        DateUtil.parse(psCreterialDate,DATE_FORMAT)).toString();
        return sUpperSectionList;
    }

    /**
     * 基点組織以下の組織リストをSQL用のカンマ区切りで取得します
     * @param psCustID	顧客コード
     * @param psCompID 法人コード
     * @param psEmployeeID 社員番号
     * @param psCreterialDate 基準日
     * @return String 基点組織またはそれ以下の組織のリスト（カンマ区切り）
     */
    protected String getBaseSectionList(
            String psCustID,  String psCompID,
            String psEmployeeID, String psCreterialDate) {
        String sBaseSection = "";
        Date date = DateUtil.parse(psCreterialDate, DATE_FORMAT);
        // ログインユーザの基点組織を取得
        BaseSectionBO bsi = baseSectionBusiness.getBaseSection(date);
        Map<String, Map<String, String>> hbSection = bsi.getHmCompany();
        // システムコード「01」の基点組織情報取得
        Map<String, String> hBaseSection =
                hbSection.get(SYSTEM_CODE_01);
        if (hBaseSection != null && hBaseSection.containsKey(psCompID)) {
            // 法人コードをキーに基点組織情報取得
            String sbSection = hBaseSection.get(psCompID);
            if (StrUtil.isNotBlank(sbSection)) {
                // 先頭の法人コードを削除
                int nCnt = sbSection.indexOf("#");
                sBaseSection = sbSection.substring(nCnt + 1, sbSection.length());
                // 区切り文字を変換:それぞれの組織コードにシングルクォーテーションを付加する
                sBaseSection = StrUtil.replace(sBaseSection, "!", "','");
                // 区切り文字を変換:前後にシングルクォーテーションを付加する
                sBaseSection = "'" + sBaseSection + "'";
            }
        }
        return sBaseSection;
    }

    /**
     * 基点組織以下の組織リストをSQL用のカンマ区切りで取得します（複数法人対応）
     * @param sCustID	顧客コード
     * @param sCompID 法人コード(未使用)
     * @param sEmployeeID 社員番号
     * @param sCreterialDate 基準日
     * @return String 法人区分をキーに、基点組織またはそれ以下の組織のリスト（カンマ区切り）
     */
    protected Map<String,String> getBaseSectionListMultiComp(
            String sCustID, String sCompID, String sEmployeeID, String sCreterialDate) {
        Map<String,String> mBaseSectionList = MapUtil.newHashMap();
        Date date = DateUtil.parse(sCreterialDate, DATE_FORMAT);
        BaseSectionBO bsi = baseSectionBusiness.getBaseSection(date);
        Map<String, String> hBaseSection = bsi.getHmCompany().get("01");
        if (hBaseSection != null) {
            for (Map.Entry<String, String> entry : hBaseSection.entrySet()) {
                String sComp = entry.getKey();
                String sbSection = entry.getValue();
                if (StrUtil.isNotBlank(sbSection)) {
                    String sBaseSection = "";
                    int nCnt = sbSection.indexOf("#");
                    sBaseSection = sbSection.substring(nCnt + 1,
                            sbSection.length());
                    sBaseSection = StrUtil.replace(sBaseSection, "!",
                            "','");
                    sBaseSection = "'" + sBaseSection + "'";
                    mBaseSectionList.put(sComp, sBaseSection);
                }
            }
        }
        return mBaseSectionList;
    }


    /**
     * V3の顧客コード、法人コード、社員番号からユーザコードを取得します
     * @param psCustid 顧客コード（V3）
     * @param psCompid 法人コード（V3）
     * @param psLoginUserId ログイン者の社員番号（V3）
     * @param psDate 検索基準日
     * @return String ユーザコード
     * @throws Exception システム例外
     */
    protected String getUseridForV4(String psCustid,
                                    String psCompid,
                                     String psLoginUserId,
                                     String psDate) {
        //2007/09/07 日付を「-」編集から「/」編集にして渡す by Konno
        List<String> lEmpList =
               iMastEmployeesService.selectUserIdList(psCustid, psCompid, psLoginUserId, DateUtil.parse(psDate, DATE_FORMAT));
        if (CollUtil.isNotEmpty(lEmpList)) {
            return lEmpList.get(0);
        }
        return null;
    }

    private void setReplacedUserCode(String value) {
        if ((value != null) && (!"".equals(value))) {
            this.replacedUserID = ("(" + value + ")");
        } else {
            this.replacedUserID = "";
        }
    }

    private String getReplacedUserCode() {
        return this.replacedUserID;
    }

    protected String creterialDateStr = null;
    protected String creterialDate = null;

    public void setCreterialDate1(String value) {
        if ("".equals(value)) {
            this.creterialDateStr = "";
        } else {
            this.creterialDateStr = (value.replace('/', '-') + " 00:00:00");
        }
    }

    public String getCreterialDate1() {
        return this.creterialDateStr;
    }

    public String getCreterialDate2() {
        Date curDate = new Date();
        String strPattern = "yyyy/MM/dd" ;
        if ((strPattern != null) && (strPattern.indexOf("Y") != -1)) {
            strPattern = strPattern.replace('Y', 'y');
        }
        if ((strPattern != null) && (strPattern.indexOf("m") != -1)) {
            strPattern = strPattern.replace('m', 'M');
        }
        if ((strPattern != null) && (strPattern.indexOf("D") != -1)) {
            strPattern = strPattern.replace('D', 'd');
        }
        if ((strPattern != null) && (strPattern.indexOf(" ") != -1)) {
            strPattern = strPattern.substring(0, strPattern.indexOf(" "))
                    + " HH:mm:ss";
        }
        return DateUtil.format(curDate,strPattern);
    }

    public String getCreterialDate3() {
        Date calendar = new Date("2222/12/31");
        String strPattern = "yyyy/MM/dd";
        if ((strPattern != null) && (strPattern.indexOf("Y") != -1)) {
            strPattern = strPattern.replace('Y', 'y');
        }
        if ((strPattern != null) && (strPattern.indexOf("m") != -1)) {
            strPattern = strPattern.replace('m', 'M');
        }
        if ((strPattern != null) && (strPattern.indexOf("D") != -1)) {
            strPattern = strPattern.replace('D', 'd');
        }
        DateFormat df = new SimpleDateFormat(strPattern);
        return df.format(calendar);
    }

    public String strSysDateFormat = null;

    public void setLocaleFormatDisp(String localeformat) {
        this.strSysDateFormat = localeformat;
    }

    public String getLocaleFormatDisp() {
        return this.strSysDateFormat;
    }

    public String getCorrectFormatDate(String entdate) {
        String format = getLocaleFormatDisp();
        String month = "";
        String year = "";
        String date = "";
        String separator = "/";
        if ((entdate != null) && (!"null".equalsIgnoreCase(entdate))
                && (entdate.length() > 0)) {
            if (entdate.contains(" ")) {
                entdate = entdate.substring(0, entdate.indexOf(" "));
            }
            if (entdate.contains("-")) {
                entdate = entdate.replace('-', '/');
            }
            int temp = entdate.indexOf(separator);
            int temp1 = entdate.lastIndexOf(separator);
            if ((format.indexOf("y") == 0) && (format.lastIndexOf("d") == 9)) {
                year = entdate.substring(0, temp);
                month = entdate.substring(temp + 1, temp1);
                date = entdate.substring(temp1 + 1, entdate.length());
            } else if (((format.indexOf("m") == 0) || (format.indexOf("M") == 0))
                    && (format.lastIndexOf("y") == 9)) {
                month = entdate.substring(0, temp);
                date = entdate.substring(temp + 1, temp1);
                year = entdate.substring(temp1 + 1, entdate.length());
            } else {
                date = entdate.substring(0, temp);
                month = entdate.substring(temp + 1, temp1);
                year = entdate.substring(temp1 + 1, entdate.length());
            }
            Date gnDate = new Date(year + "/" + month + "/" + date);
            String strPattern = "yyyy/MM/dd";
            if ((strPattern != null) && (strPattern.indexOf("Y") != -1)) {
                strPattern = strPattern.replace('Y', 'y');
            }
            if ((strPattern != null) && (strPattern.indexOf("m") != -1)) {
                strPattern = strPattern.replace('m', 'M');
            }
            if ((strPattern != null) && (strPattern.indexOf("D") != -1)) {
                strPattern = strPattern.replace('D', 'd');
            }
            if ((strPattern != null) && (strPattern.indexOf(" ") != -1)) {
                strPattern = strPattern.substring(0, strPattern.indexOf(" "))
                        + " HH:mm:ss";
            }
            SimpleDateFormat formatter = new SimpleDateFormat(strPattern);
            String strDate = formatter.format(gnDate);

            return strDate;
        }
        return null;
    }

    public void setCreterialDate2(String value) {
        if (value.equals("")) {
            this.creterialDate = "";
        } else {
            this.creterialDate = (value.replace('/', '-') + " 00:00:00");
        }
    }

}
