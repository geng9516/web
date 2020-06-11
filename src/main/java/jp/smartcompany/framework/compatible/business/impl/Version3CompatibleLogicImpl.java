package jp.smartcompany.framework.compatible.business.impl;

import cn.hutool.core.collection.CollUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.compatible.business.Version3CompatibleLogic;
import jp.smartcompany.framework.sysboot.dto.SystemPropertyDTO;
import jp.smartcompany.job.modules.core.business.SectionChiefBusiness;
import jp.smartcompany.job.modules.core.pojo.bo.DesignationBO;
import jp.smartcompany.job.modules.core.pojo.bo.EvaluatorBO;
import jp.smartcompany.job.modules.core.pojo.entity.HistDesignationDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastEmployeesDO;
import jp.smartcompany.job.modules.core.service.IHistDesignationService;
import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import jp.smartcompany.job.modules.core.service.IMastOrganisationService;
import jp.smartcompany.job.modules.core.util.PsResult;
import jp.smartcompany.job.modules.core.util.PsSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Xiao Wenpeng
 * V3互換(API)クラス
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class Version3CompatibleLogicImpl implements Version3CompatibleLogic {

    private final ScCacheUtil scCacheUtil;
    private final SectionChiefBusiness sectionChiefBusiness;
    private final IHistDesignationService iHistDesignationService;
    private final IMastOrganisationService iMastOrganisationService;
    private final IMastEmployeesService iMastEmployeesService;
    private PsSession psSession;

    @Override
    public String getSystemCode() {
        return null;
    }

    @Override
    public PsResult SelectQuerywithPermission(Vector vecQuery, String sUserid, Vector vPostid, String sGroupid, String sBeandesc, boolean bApplypermission, String sTargetuser, Vector vDept, String sCompid, String sCustid, String sSystemCode, String strGUID, String sDomainid, String sDate, Vector vSectionid, String sTarcomp, String sTarsection, HttpSession httpSession) throws Exception {
        return null;
    }

    @Override
    public PsResult SelectSpecificComboQuerywithoutPermission(String sCustid, String sCompid, String sLanguage, String sDate, String sCode) {
        return null;
    }

    @Override
    public int setInsertValues(Vector vecQuery, String sUserid, String sBeandesc, String sCompid, String sCustid, String sSystemCode, String strGUID) {
        return 0;
    }

    @Override
    public int setInsertValuesForBlob(Vector vecQuery, Vector vecParams, String sUserId, String sBeanDesc, String sCompId, String sCustId, String sSystemCode, String sGUID) {
        return 0;
    }

    @Override
    public PsResult executeSelectQueryBatch(Vector vecQuery, String sUserid, Vector vPostid, String sGroupid, Vector vPostweightage, String sBeandesc, boolean bApplypermission, String sTargetuser, Vector vDept, String sCompid, String sCustid, String sSystemCode, String strGUID) throws Exception {
        return null;
    }

    @Override
    public PsResult SelectQuerywithTargetUser(Vector vecQuery, String sCustid, String sCompid, String sUserid, String sGroupid, String sTargetcustid, String sTargetcompid, String sTargetuserorsection, String sBeandesc, String sSystemCode, String sDomainid, String strGUID, String sDate, HttpSession httpSession) throws Exception {
        return null;
    }

    @Override
    public PsResult SelectMultiQuerywithPermission(Vector vecQuery, String sUserid, Vector vPostid, String sGroupid, String sBeandesc, String sTargetuser, Vector vDept, String sCompid, String sCustid, String sSystemCode, String strGUID, String sDomainid, String sDate, Vector vSectionid, String sTarcomp, String sTarsection, HttpSession httpSession) throws Exception {
        return null;
    }

    @Override
    public PsResult executeMultiQuery(Vector pvSQL, String psCustid, String psCompid, String sUserid, String sGroupid, String sBeandesc, String psSystemCode, String psGUID, boolean pbDesigSW) throws Exception {
        return null;
    }

    @Override
    public boolean executeProcedure(String sCompid, String sUserid, String sSystemCode, String sGUID, Vector vSQL, Vector vParams) throws Exception {
        return false;
    }

    @Override
    public PsResult checkforchief(String sCustid, String sCompid, String sDeptid, String sDate, boolean bIncludeActual) {
        return null;
    }

    @Override
    public PsResult getCompanyInfo(String sCustid, String sLanguage, String sDate) {
        return null;
    }

    @Override
    public String getDataBaseDate() {
        return null;
    }

    @Override
    public PsResult SelectMultiQuerywithPermission(Vector vecQuery, String sUserid, Vector vPostid, String sGroupid, String sBeandesc, String sTargetuser, Vector vDept, String sCompid, String sCustid, String sSystemCode, String strGUID, String sDomainid, String sDate, Vector vSectionid, String sTarcomp, String sTarsection, String sPageStart, String sPageEnd, HttpSession httpSession) throws Exception {
        return null;
    }

    @Override
    public PsResult SelectMultiQuerywithPermissionForJk(Vector vecQuery, String sUserid, Vector vPostid, String sGroupid, String sBeandesc, String sTargetuser, Vector vDept, String sCompid, String sCustid, String sSystemCode, String strGUID, String sDomainid, String sDate, Vector vSectionid, String sTarcomp, String sTarsection, String sPageStart, String sPageEnd, HttpSession httpSession) throws Exception {
        return null;
    }

    @Override
    public boolean isDebugModeEnabled() {
        return false;
    }

    @Override
    public String getDatabaseMode() {
        return null;
    }

    @Override
    public int getRelation(String sCustomerID, String sLoginCompanyID, String sLoginUserID, String sTargetCompanyID, String sTargetUserID, String sDate, String sReportLine, HttpSession httpSession, String sGroupID, String sSystemCode, String sGUID) throws Exception {
        return 0;
    }

    @Override
    public Vector<Integer> getRelation(String sCustomerID, String sLoginCompanyID, String sLoginUserID, String sTargetCompanyID, String sTargetUserID, String sDate, String sReportLine, HttpSession httpSession, String sGroupID, String sSystemCode, String sGUID, boolean bFixed) throws Exception {
        return null;
    }

    @Override
    public Vector<Vector<Object>> getEvaluatee(String sCustomerID, String sLoginCompanyID, String sLoginUserID, String sCreterialDate, int nEvaluationLevel, String sReportLine, String sGroupID, String sSystemCode, String sGUID, String sLanguage) throws Exception {
        return null;
    }

    @Override
    public boolean isMatchEvaluationLevel(String sCustomerID, String sLoginCompanyID, String sLoginUserID, String sTargetCompanyID, String sTargetUserID, String sDate, int nEvaluationLevel, String sReportLine, String sGroupID, String sSystemCode, String sGUID, String[] sLoginChiefId) throws Exception {
        return false;
    }

    @Override
    public int getMaxEvaluationLevel(String sCustomerID, String sCompanyID, String sCreterialDate) throws Exception {
        return 0;
    }

    @Override
    public Vector<Vector<Object>> getEvaluator(String sCustomerID, String sLoginCompanyID, String sLoginUserID, String sTargetCompanyID, String sTargetUserID, String sCreterialDate, int nEvaluationLevel, String sReportLine, boolean bIgnoreRelationDefinitions, String sGroupID, String sSystemCode, String sGUID) throws Exception {
        return null;
    }

    @Override
    public String getBaseSectionListForSQL(String sCustID, String sCompID, String sEmployeeID, String sCreterialDate) {
        return null;
    }

    @Override
    public Map getBaseSectionListMultiCompForSQL(String sCustID, String sCompID, String sEmployeeID, String sCreterialDate) {
        return null;
    }

    /**
     * 指定社員の下位組織リストをSQL用のカンマ区切りで取得します
     * 社員指定、仮想組織含むか
     * @param sCustID 顧客コード
     * @param sCompID 法人コード
     * @param sEmployeeID 社員番号
     * @param sCreterialDate 基準日
     * @param bIncludeVirtual 仮想組織含むか
     * @return String 組織コードリスト
     */
    @Override
    public String getLowerSectionListUserForSQL(String sCustID, String sCompID, String sEmployeeID, String sCreterialDate, boolean bIncludeVirtual) {
        String sLowerSectionList = "";
        try {
            // TODO 検索対象ユーザコード取得できないときの処理未実装
            // 検索対象ユーザコード取得
            String sTargetUserCode = getUseridForV4(sCustID, sCompID, sEmployeeID, sCreterialDate);
            Map<String,List<String>> hLowerSectionList = iMastOrganisationService.getSubSectionEmp(
                    sTargetUserCode,SysUtil.transStringToDate(sCreterialDate),
                    bIncludeVirtual);
//            if (hLowerSectionList != null || hLowerSectionList.size() > 0) {
//                sTargetUserCode = hLowerSectionList.get(psCompID).toString();
//            }
            return hLowerSectionList.toString();
        } catch (Exception e) {
            return sLowerSectionList;
        }
    }

    /**
     * 指定社員の下位組織リストをSQL用のカンマ区切りで取得します
     * 社員指定
     * @param sCustID 顧客コード
     * @param sCompID 法人コード
     * @param sEmployeeID 社員番号
     * @param sCreterialDate 基準日
     * @return String 組織コードリスト
     */
    @Override
    public String getLowerSectionListUserForSQL(String sCustID, String sCompID, String sEmployeeID, String sCreterialDate) {
        String sLowerSectionList = "";
        try {
            // TODO 検索対象ユーザコード取得できないときの処理未実装
            // 検索対象ユーザコード取得
            String sTargetUserCode = getUseridForV4(sCustID, sCompID, sEmployeeID, sCreterialDate);
            Map<String,List<String>> hLowerSectionList = iMastOrganisationService.getSubSectionEmp(
                    sTargetUserCode,SysUtil.transStringToDate(sCreterialDate), false);
//            if (CollUtil.isNotEmpty(hLowerSectionList)) {
//                sTargetUserCode = hLowerSectionList.get(sCompID).toString();
//            }
            return hLowerSectionList.toString();
        } catch (Exception e) {
            return sLowerSectionList;
        }
    }

    /**
     * 指定組織の下位組織リストをSQL用のカンマ区切りで取得します
     * 組織指定
     * @param sCustID 顧客コード
     * @param sCompID 法人コード
     * @param sSection 組織コード
     * @param sCreterialDate 基準日
     * @return String 組織コードリスト
     */
    @Override
    public String getLowerSectionListForSQL(String sCustID, String sCompID, String sSection, String sCreterialDate) {
        String sLowerSectionList = "";
        try {
            return iMastOrganisationService.getSubSection(sCustID,
                    sCompID, sSection,
                    SysUtil.transStringToDate(sCreterialDate)).toString();
        } catch (ParseException e) {
        }
        return sLowerSectionList;
    }

    /**
     * 指定組織の上位組織リストをSQL用のカンマ区切りで取得します
     * @param sCustID 顧客コード
     * @param sCompID 法人コード
     * @param sSection 組織コード
     * @param sCreterialDate 基準日
     * @return String 組織コードリスト
     */
    @Override
    public String getUpperSectionListForSQL(String sCustID, String sCompID, String sSection, String sCreterialDate) {
        String sUpperSectionList = "";
        try {
            sUpperSectionList = iMastOrganisationService.selectHighSection(
                            sCustID, sCompID, sSection,
                            SysUtil.transStringToDate(sCreterialDate)).toString();
            return sUpperSectionList;
        } catch (ParseException e) {
            return sUpperSectionList;
        }
    }

    /**
     * システムプロパティ情報を取得します<br>
     * @param sKey プロパティ名
     * @return プロパティ値
     */
    @Override
    public String getSystemProperty(String sKey) {
        return scCacheUtil.getSystemProperty(sKey);
    }

    /**
     * 所属長役職を取得します
     * @param sCustid 検索対象顧客コード
     * @param sCompid 検索対象法人コード
     * @param sDeptid 検索対象組織コード
     * @param sDate 検索基準日
     * @return String 所属長の役職コード
     */
    @Override
    public PsResult getChiefPostOfDesignation(String sCustid, String sCompid, String sDeptid, String sDate) {
        PsResult oPsResult = new PsResult();
        Vector<String> vResult = new Vector<>();
        Vector<String> vecException = new Vector<>();
        List<EvaluatorBO> lSectionChief;
        try {
            // ログインユーザコード取得
            String sLoginUserCode = psSession.getLoginUser();
//            String sLoginUserCode = "46402406";
            // TODO 所属長情報取得のメソッド空実装
            // 所属長情報取得
            lSectionChief = sectionChiefBusiness.getSectionChief(
                    sLoginUserCode, SysUtil.transStringToDate(sDate));
            String seCompid = "";
            String seUserid = "";
            String seDeptid = "";
            if (lSectionChief.size() > 0) {
                DesignationBO designation = lSectionChief.get(0);
                seCompid = designation.getCompanyCode(); // 評価者の法人コード
                seUserid = designation.getEmployee();    // 評価者の社員番号
                seDeptid = designation.getSection();     // 評価者の組織コード
            }
            if (!isEmptyString(seCompid)
                    && !isEmptyString(seUserid)
                    && !isEmptyString(seDeptid)) {
                // 異動歴検索
                //2007/09/07 日付を「-」編集から「/」編集にして渡す by Konno
                String date = fmtchgDate(sDate);
                List<HistDesignationDO> lDesignationList = iHistDesignationService.list(
                        SysUtil.<HistDesignationDO>query()
                        .eq("HD_CCUSTOMERID_CK",sCustid)
                        .eq("HD_CCOMPANYID_CK",seCompid)
                        .eq("HD_CEMPLOYEEID_CK",seUserid)
                        .eq("HD_CSECTIONID_FK",seDeptid)
                        .lt("HD_DSTARTDATE_CK",date)
                        .gt("HD_DENDDATE",date)
                );
                if (lDesignationList.size() > 0) {
                    HistDesignationDO designationEntity = lDesignationList.get(0);
                    vResult.add(designationEntity.getHdCpostidFk());
                }
            }
            oPsResult.setResult(vResult);
            vecException.add("");
            oPsResult.setException(vecException);
            return oPsResult;
        } catch (Exception e) {
            oPsResult.setResult(vResult);
            vecException.add(e.getMessage());
            oPsResult.setException(vecException);
            return oPsResult;
        }
    }

    /**
     * SQL実行結果セット（Desigあり）
     * @param vecResult SQL実行結果
     */
    @Override
    public Vector<Object> setV3ColumnDataContDesig(Vector<Object> vecResult) {
        Vector<Object> vCol = new Vector();
        for (int nCnt = 5; nCnt < vecResult.size(); nCnt++) {
            vCol.add(vecResult.get(nCnt));
        }
        return vCol;
    }

    /**
     * 全てのシステムプロパティ情報を取得します
     * @return プロパティ値
     */
    @Override
    public Map<String, String> getLookAndFeelSettings() {
        Map<String,SystemPropertyDTO> hmSystemProperties = scCacheUtil.getAllSystemProperties();
        Hashtable<String, String> htSystemProperties = new Hashtable<>();
        String sKey;
        SystemPropertyDTO oSystemProperty;
        String sCust;
        String sValue;
        Set<Map.Entry<String,SystemPropertyDTO>> set = hmSystemProperties.entrySet();
        for (Map.Entry<String,SystemPropertyDTO> mEntry : set) {
            sKey = mEntry.getKey();
            oSystemProperty = mEntry.getValue();
            sCust = oSystemProperty.getCustomerId();
            sValue = (String) oSystemProperty.getPropValue();
            if (sValue == null) {
                sValue = "";
            }
            htSystemProperties.put(sCust + sKey, sValue);
        }
        return htSystemProperties;
    }

    /**
     * V3の顧客コード、法人コード、社員番号からユーザコードを取得します
     * @param sCustid 顧客コード（V3）
     * @param sCompid 法人コード（V3）
     * @param sLoginUserId ログイン者の社員番号（V3）
     * @param sDate 検索基準日
     * @return String ユーザコード
     * @throws Exception システム例外
     */
    @Override
    public String getUseridForV4(String sCustid,String sCompid,String sLoginUserId,String sDate) {
        // 基本情報検索：ユーザID取得
        String sUserId = null;
        //2007/09/07 日付を「-」編集から「/」編集にして渡す by Konno
        List<MastEmployeesDO> lEmpList =
                iMastEmployeesService.selectEmployByLoginUserId(sCustid, sCompid, sLoginUserId, fmtchgDate(sDate));
        if (CollUtil.isNotEmpty(lEmpList)) {
            MastEmployeesDO mastEmpEntity = lEmpList.get(0);
            sUserId = mastEmpEntity.getMeCuserid();
        }
        return sUserId;
    }

    /**
     * 文字列チェック.
     * @param psString 文字列
     * @return true：null または 空文字
     */
    protected boolean isEmptyString(final String psString) {

        // 文字列がnull または 空文字かチェック
        if ("".equals(psString) || psString == null) {
            return true;
        }
        return false;
    }

    /**
     * 日付を「-」編集から「/」編集にして年月日のみを切り出し返す.
     * by Konno
     * @param psDate
     * @return
     */
    private String fmtchgDate(String psDate) {
        String sDate = null;
        try {
            SimpleDateFormat from1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat from2 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat from3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            SimpleDateFormat to = new SimpleDateFormat("yyyy/MM/dd");
            if (psDate.contains("-")) {
                if (psDate.length() > 10) {
                    sDate = to.format(from1.parse(psDate)); // 時刻は削除する
                } else {
                    sDate = to.format(from2.parse(psDate));
                }
            } else if (psDate.length() > 10) {
                sDate = to.format(from3.parse(psDate)); // 時刻は削除する
            } else {
                sDate = to.format(to.parse(psDate));
            }
        } catch (Exception e) {
            sDate = psDate;
        }
        return sDate;
    }

}
