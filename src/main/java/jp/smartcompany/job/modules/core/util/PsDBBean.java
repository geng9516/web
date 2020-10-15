package jp.smartcompany.job.modules.core.util;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SpringUtil;
import lombok.*;
import java.util.*;

/**
 * 直接通过Spring注入即可使用
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
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
    protected String strGUID = null;

    private ScCacheUtil scCacheUtil;

    public PsDBBean() {
        scCacheUtil = SpringUtil.getBean(ScCacheUtil.class);
    }

    /**
     * システムプロパティ情報を取得します<br>
     * @return プロパティ値
     */
    public String getSystemProperty(String sSysPropertyKey) {
        return scCacheUtil.getSystemProperty(sSysPropertyKey);
    }


//    private BaseSectionBusiness baseSectionBusiness;
//    private IMastOrganisationService iMastOrganisationService;
//    private IMastEmployeesService iMastEmployeesService;
//    private IHistDesignationService iHistDesignationService;
//    private ScCacheUtil scCacheUtil;

//    public PsDBBean() {
//        baseSectionBusiness = SpringUtil.getBean(BaseSectionBusiness.class);
//        iMastOrganisationService = SpringUtil.getBean(IMastOrganisationService.class);
//        iMastEmployeesService = SpringUtil.getBean(IMastEmployeesService.class);
//        iHistDesignationService =SpringUtil.getBean(IHistDesignationService.class);
//        scCacheUtil = SpringUtil.getBean(ScCacheUtil.class);
//    }

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

    private Map<String,Object> requestHash;
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
        Object oVal;
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

    public String getAppId() {
      return (String)this.requestHash.get("AppId");
    }

    public String getUserCode() {
        return this.strUserID;
    }

    public void setUserCode(String value) {
        this.strUserID = value;
    }


//    /**
//     *
//     * 旧代码中以下两个方法都未做其他处理
//     * 指定職員の下位組織リストをSQL用のカンマ区切りで取得します
//     * 職員指定、仮想組織含むか
//     * @param psCustID 顧客コード
//     * @param psCompID 法人コード
//     * @param psEmployeeID 職員番号
//     * @param psCreterialDate 基準日
//     * @param pbIncludeVirtual 仮想組織含むか
//     * @return String 組織コードリスト
//     */
//    protected String getLowerSectionListUser(String psCustID, String psCompID,
//                                             String psEmployeeID, String psCreterialDate,
//                                             boolean pbIncludeVirtual) {
//        return "";
//    }

//
//    protected String getLowerSectionList(String psCustID, String psCompID,
//                                         String psSection, String psCreterialDate) {
//        String sLowerSectionList = "";
//            List <String> lowerSectionSysInfoList =
//                    iMastOrganisationService.selectLowerSection(psCustID, psCompID, psSection, DateUtil.parse(psCreterialDate,DATE_FORMAT));
//            if (CollUtil.isEmpty(lowerSectionSysInfoList)) {
//                return null;
//            }
//            List<String> sectionIdList = CollUtil.newArrayList();
//            sectionIdList.addAll(lowerSectionSysInfoList);
//            sLowerSectionList = sectionIdList.toString();
//            return sLowerSectionList;
//    }

//    /**
//     * 指定組織の上位組織リストをSQL用のカンマ区切りで取得します
//     * @param psCustID 顧客コード
//     * @param psCompID 法人コード
//     * @param psSection 組織コード
//     * @param psCreterialDate 基準日
//     * @return String 組織コードリスト
//     */
//    protected String getUpperSectionList(String psCustID, String psCompID,
//                                         String psSection, String psCreterialDate) {
//        String sUpperSectionList = "";
//        sUpperSectionList =
//                iMastOrganisationService.selectHighSection(
//                        psCustID, psCompID, psSection,
//                        DateUtil.parse(psCreterialDate,DATE_FORMAT)).toString();
//        return sUpperSectionList;
//    }

//    /**
//     * 基点組織以下の組織リストをSQL用のカンマ区切りで取得します
//     * @param psCustID	顧客コード
//     * @param psCompID 法人コード
//     * @param psEmployeeID 職員番号
//     * @param psCreterialDate 基準日
//     * @return String 基点組織またはそれ以下の組織のリスト（カンマ区切り）
//     */
//    protected String getBaseSectionList(
//            String psCustID,  String psCompID,
//            String psEmployeeID, String psCreterialDate) {
//        String sBaseSection = "";
//        Date tdate = DateUtil.parse(psCreterialDate,DATE_FORMAT);
//        String date = DateUtil.format(tdate, DATE_FORMAT);
//        // ログインユーザの基点組織を取得
//        BaseSectionBO bsi = baseSectionBusiness.getBaseSection(date,ContextUtil.getSession());
//        Map<String, Map<String, String>> hbSection = bsi.getHmCompany();
//        // システムコード「01」の基点組織情報取得
//        Map<String, String> hBaseSection =
//                hbSection.get(SYSTEM_CODE_01);
//        if (hBaseSection != null && hBaseSection.containsKey(psCompID)) {
//            // 法人コードをキーに基点組織情報取得
//            String sbSection = hBaseSection.get(psCompID);
//            if (StrUtil.isNotBlank(sbSection)) {
//                // 先頭の法人コードを削除
//                int nCnt = sbSection.indexOf("#");
//                sBaseSection = sbSection.substring(nCnt + 1, sbSection.length());
//                // 区切り文字を変換:それぞれの組織コードにシングルクォーテーションを付加する
//                sBaseSection = StrUtil.replace(sBaseSection, "!", "','");
//                // 区切り文字を変換:前後にシングルクォーテーションを付加する
//                sBaseSection = "'" + sBaseSection + "'";
//            }
//        }
//        return sBaseSection;
//    }

//    /**
//     * 基点組織以下の組織リストをSQL用のカンマ区切りで取得します（複数法人対応）
//     * @param sCustID	顧客コード
//     * @param sCompID 法人コード(未使用)
//     * @param sEmployeeID 職員番号
//     * @param sCreterialDate 基準日
//     * @return String 法人区分をキーに、基点組織またはそれ以下の組織のリスト（カンマ区切り）
//     */
//    protected Map<String,String> getBaseSectionListMultiComp(
//            String sCustID, String sCompID, String sEmployeeID, String sCreterialDate) {
//        Map<String,String> mBaseSectionList = MapUtil.newHashMap();
//        Date date = DateUtil.parse(sCreterialDate, DATE_FORMAT);
//        String tdate = DateUtil.format(date,DATE_FORMAT);
//        BaseSectionBO bsi = baseSectionBusiness.getBaseSection(tdate,ContextUtil.getHttpRequest().getSession());
//        Map<String, String> hBaseSection = bsi.getHmCompany().get("01");
//        if (hBaseSection != null) {
//            for (Map.Entry<String, String> entry : hBaseSection.entrySet()) {
//                String sComp = entry.getKey();
//                String sbSection = entry.getValue();
//                if (StrUtil.isNotBlank(sbSection)) {
//                    String sBaseSection = "";
//                    int nCnt = sbSection.indexOf("#");
//                    sBaseSection = sbSection.substring(nCnt + 1,
//                            sbSection.length());
//                    sBaseSection = StrUtil.replace(sBaseSection, "!",
//                            "','");
//                    sBaseSection = "'" + sBaseSection + "'";
//                    mBaseSectionList.put(sComp, sBaseSection);
//                }
//            }
//        }
//        return mBaseSectionList;
//    }


//    /**
//     * V3の顧客コード、法人コード、職員番号からユーザコードを取得します
//     * @param psCustid 顧客コード（V3）
//     * @param psCompid 法人コード（V3）
//     * @param psLoginUserId ログイン者の職員番号（V3）
//     * @param psDate 検索基準日
//     * @return String ユーザコード
//     * @throws Exception システム例外
//     */
//    protected String getUseridForV4(String psCustid,
//                                    String psCompid,
//                                     String psLoginUserId,
//                                     String psDate) {
//        //2007/09/07 日付を「-」編集から「/」編集にして渡す by Konno
//        List<String> lEmpList =
//               iMastEmployeesService.selectUserIdList(psCustid, psCompid, psLoginUserId, DateUtil.parse(psDate, DATE_FORMAT));
//        if (CollUtil.isNotEmpty(lEmpList)) {
//            return lEmpList.get(0);
//        }
//        return null;
//    }

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

//    public String getCreterialDate2() {
//        Date curDate = new Date();
//        String strPattern = "yyyy/MM/dd" ;
//        if ((strPattern != null) && (strPattern.indexOf("Y") != -1)) {
//            strPattern = strPattern.replace('Y', 'y');
//        }
//        if ((strPattern != null) && (strPattern.indexOf("m") != -1)) {
//            strPattern = strPattern.replace('m', 'M');
//        }
//        if ((strPattern != null) && (strPattern.indexOf("D") != -1)) {
//            strPattern = strPattern.replace('D', 'd');
//        }
//        if ((strPattern != null) && (strPattern.indexOf(" ") != -1)) {
//            strPattern = strPattern.substring(0, strPattern.indexOf(" "))
//                    + " HH:mm:ss";
//        }
//        return DateUtil.format(curDate,strPattern);
//    }

//    public String getCreterialDate3() {
//        Date calendar = new Date("2222/12/31");
//        String strPattern = "yyyy/MM/dd";
//        if ((strPattern != null) && (strPattern.indexOf("Y") != -1)) {
//            strPattern = strPattern.replace('Y', 'y');
//        }
//        if ((strPattern != null) && (strPattern.indexOf("m") != -1)) {
//            strPattern = strPattern.replace('m', 'M');
//        }
//        if ((strPattern != null) && (strPattern.indexOf("D") != -1)) {
//            strPattern = strPattern.replace('D', 'd');
//        }
//        DateFormat df = new SimpleDateFormat(strPattern);
//        return df.format(calendar);
//    }

//    public String strSysDateFormat = null;
//
//    public void setLocaleFormatDisp(String localeformat) {
//        this.strSysDateFormat = localeformat;
//    }
//
//    public String getLocaleFormatDisp() {
//        return this.strSysDateFormat;
//    }

//    public String getCorrectFormatDate(String entdate) {
//        String format = getLocaleFormatDisp();
//        String month = "";
//        String year = "";
//        String date = "";
//        String separator = "/";
//        if ((entdate != null) && (!"null".equalsIgnoreCase(entdate))
//                && (entdate.length() > 0)) {
//            if (entdate.contains(" ")) {
//                entdate = entdate.substring(0, entdate.indexOf(" "));
//            }
//            if (entdate.contains("-")) {
//                entdate = entdate.replace('-', '/');
//            }
//            int temp = entdate.indexOf(separator);
//            int temp1 = entdate.lastIndexOf(separator);
//            if ((format.indexOf("y") == 0) && (format.lastIndexOf("d") == 9)) {
//                year = entdate.substring(0, temp);
//                month = entdate.substring(temp + 1, temp1);
//                date = entdate.substring(temp1 + 1, entdate.length());
//            } else if (((format.indexOf("m") == 0) || (format.indexOf("M") == 0))
//                    && (format.lastIndexOf("y") == 9)) {
//                month = entdate.substring(0, temp);
//                date = entdate.substring(temp + 1, temp1);
//                year = entdate.substring(temp1 + 1, entdate.length());
//            } else {
//                date = entdate.substring(0, temp);
//                month = entdate.substring(temp + 1, temp1);
//                year = entdate.substring(temp1 + 1, entdate.length());
//            }
//            Date gnDate = new Date(year + "/" + month + "/" + date);
//            String strPattern = "yyyy/MM/dd";
//            if ((strPattern != null) && (strPattern.indexOf("Y") != -1)) {
//                strPattern = strPattern.replace('Y', 'y');
//            }
//            if ((strPattern != null) && (strPattern.indexOf("m") != -1)) {
//                strPattern = strPattern.replace('m', 'M');
//            }
//            if ((strPattern != null) && (strPattern.indexOf("D") != -1)) {
//                strPattern = strPattern.replace('D', 'd');
//            }
//            if ((strPattern != null) && (strPattern.indexOf(" ") != -1)) {
//                strPattern = strPattern.substring(0, strPattern.indexOf(" "))
//                        + " HH:mm:ss";
//            }
//            SimpleDateFormat formatter = new SimpleDateFormat(strPattern);
//            String strDate = formatter.format(gnDate);
//
//            return strDate;
//        }
//        return null;
//    }

    public void setCreterialDate2(String value) {
        if (value.equals("")) {
            this.creterialDate = "";
        } else {
            this.creterialDate = (value.replace('/', '-') + " 00:00:00");
        }
    }

//    public boolean bDownload = false;
//    public String gsAttachmentType = null;
//    public void setDownload(boolean bDownload) {
//        this.bDownload = bDownload;
//
//        this.gsAttachmentType = null;
//    }
//    public String sDownloadContentType = null;
//    public void setDownloadContentType(String sDownloadContentType) {
//        this.sDownloadContentType = sDownloadContentType;
//    }
//    public byte[] bytDownload = null;
//    public void setDownloadStream(byte[] bytDownload) {
//        this.bytDownload = bytDownload;
//    }
//    protected String sDownloadFileName = null;
//    public void setDownloadFileName(String sDownloadFileName) {
//        this.sDownloadFileName = sDownloadFileName;
//    }

//    /**
//     * システムプロパティ情報を取得します<br>
//     * @return プロパティ値
//     */
//    public String getSystemProperty(String sSysPropertyKey) {
//        return scCacheUtil.getSystemProperty(sSysPropertyKey);
//    }
//
//    /**
//     * Version3CompatibleLogicの取得
//     * @return gV3CompatibleLogic Version3CompatibleLogic
//     */
//    public Version3CompatibleLogic getV3Logic() {
//        return SpringUtil.getBean(Version3CompatibleLogic.class);
//    }
//
//    public int setInsertValues(Vector vecQuery, String beandesc) {
//        try {
//            return getV3Logic().setInsertValues(vecQuery, getUserCode(),
//                    beandesc, getCompCode(), getCustID(), getSystemCode(),
//                    getStrGUID());
//        } catch (Exception e) {
//        }
//        return 0;
//    }
//
//    /**
//     * １つ以上のSELECT文をセキュリティ判定なしで実行します。
//     * @param vecQuery	   SELECT文のVector
//     * @param strBeanDesc ログ出力用Bean識別子
//     * @return PsResult   SQLの実行結果
//     * @throws Exception  システム例外
//     */
//    public PsResult getValuesforMultiquery(Vector vecQuery, String strBeanDesc)
//            throws Exception {
//        try {
//            if (vecQuery != null && strBeanDesc != null) {
//                return getV3Logic().executeMultiQuery(
//                        vecQuery, getCustID(),
//                        getCompCode(), getUserCode(),
//                        getGroupID(), 	strBeanDesc,
//                        getSystemCode(), getStrGUID(),
//                        false);
//            } else {
//                throw new Exception(
//                        SysUtil.getpropertyvalue(
//                                getLanguage(), "ErrorCode_25"));
//            }
//        } catch (Exception e) {
//            throw e;
//        }
//    }
//
//    public Object valueAtColumnRow(Vector result,int column,int row ) throws ArrayIndexOutOfBoundsException
//    {
//        int vnest = 0;
//        Object odummy = result;
//        while(true){
//            if(odummy instanceof Vector == true){
//                vnest++;
//                if(((Vector)odummy).size() > 0){
//                    odummy = ((Vector)odummy).get(0);
//                }else{
//                    break;
//                }
//            }else{
//                break;
//            }
//        }
//        if(vnest == 3){
//            //result = (Vector)result.get(0);
//        }
//        int realRow = row + 1;
//        if (result == null) {
//            throw new ArrayIndexOutOfBoundsException ("Result set is empty.");
//        }
//
//        if (realRow > result.size()) {
//            throw new ArrayIndexOutOfBoundsException ("Row is out of bounds.");
//        }
//        try
//        {
//            return ((Vector)result.get(row)).get(column);
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return null;
//    }//end of valueAtColumnRow()................
//
//    public String valueAtColumnRow(PsResult psResult,int queryindex,int column,int row ) {
//        Vector retvec = psResult.getException();
//        try
//        {
//            if (retvec.size() != 0 && !retvec.get(queryindex).equals(""))
//            {
//                String errmsg = (String)retvec.get(queryindex);
//                throw new Exception(errmsg);
//            }
//            retvec = psResult.getResult();
//            Object val = ((Vector)((Vector)retvec.get(queryindex)).get(row)).get(column);
//            return val+"";
//        }
//        catch(Exception e)
//        {
//        }
//        return null;
//    }
//
//    public int getCount(PsResult psResult,int queryindex) throws Exception {
//        Vector retvec = psResult.getResult();
//        Vector errvec = psResult.getException();
//        if (errvec != null) {
//            if ((errvec.size() != 0) && (queryindex < errvec.size())
//                    && (!errvec.get(queryindex).equals(""))) {
//                String errmsg = (String) errvec.get(queryindex);
//                throw new Exception(errmsg);
//            }
//        }
//        if ((retvec != null) && (queryindex < retvec.size())) {
//            return ((Vector) retvec.get(queryindex)).size();
//        }
//        return 0;
//    }


}
