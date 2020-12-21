package jp.smartcompany.job.modules.tmg.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.util.BuildTargetSql;
import jp.smartcompany.framework.util.PsBuildTargetSql;
import jp.smartcompany.job.modules.core.pojo.bo.LoginGroupBO;
import jp.smartcompany.job.modules.core.service.IHistGroupdatapermissionService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.core.util.PsDBBeanUtil;
import jp.smartcompany.job.modules.core.util.PsResult;
import jp.smartcompany.job.modules.core.util.PsSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * @author Xiao Wenpeng
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class TmgSearchRangeUtil  {

    private final ScCacheUtil scCacheUtil;
    private final PsBuildTargetSql psBuildTargetSql;
    private final PsDBBeanUtil psDBBeanUtil;
    private final IHistGroupdatapermissionService histGroupdatapermissionService;

    /**
     * LOG出力用ディスクリプタ
      */
    private final String BEAN_DESC = "TmgSearchRangeUtil";
    /**
     * 自所属以下を示すグループ
     */
    public static final String SEARCH_RANGE_USER_SECTION_DEFS ="'0008'";
    /**
     * 自所属のみ示すグループ
     */
    public static final String SEARCH_RANGE_SELF_USER_SECTION_DEFS ="'0004'";
    /**
     * 自社のみ示すグループ
     */
    public static final String SEARCH_RANGE_SELF_COMP_DEFS ="'0002'";
    /**
     * 自社以下示すグループ
     */
    public static final String SEARCH_RANGE_USER_COMP_DEFS ="'0003'";
    /**ONLY_SELF_
     * 前所属を示すグループ
     */
    public static final String SEARCH_RANGE_ALL_SECTION_DEFS ="'0001'";

    private String necessity;


    /**
     * 検索対象範囲EXISTS句取得処理
     * @param pSession
     * @param psCompanyColumn
     * @param psEmployeeColumn
     * @return
     * @throws Exception
     */
    public String getExistsQuery(PsDBBean psDBBean, HttpSession pSession, String psCompanyColumn, String psEmployeeColumn) throws Exception {
        // 動作フレームワーク判定
        // V3の処理
        if (getFrameVersion().equals("3")) {
            // 検索対象範囲情報を取得してEXISTS句を組み立てる
            return createExistsQuery(psDBBean, psCompanyColumn, psEmployeeColumn);
        }
        // V4の処理
        else {
            // パラメータの法人、職員番号カラムからユーザIDカラムを取得する
            String sUserIdColumn = getUserIdColumn(psDBBean,psEmployeeColumn);
            // 検索対象範囲APIを呼び出してEXISTS句を取得する
            String sExists = psBuildTargetSql.getExistsQuery(sUserIdColumn);
            // 再取得判定用にEXISTS句を保存
            pSession.setAttribute(TmgReferList.SESSION_KEY_SEARCHRANGE, sExists);
            return sExists;
        }
    }

    /**
     * 検索対象範囲EXISTS句取得処理
     * @param psDBBean
     * @param pSession
     * @param psSectionColumn:ここでは組織階層コードを用いる
     * @return
     * @throws Exception
     */
    public String getExistsQueryOrganisation(PsDBBean psDBBean, HttpSession pSession,String psSectionColumn) throws Exception {
        // 初期化
        String sSiteId = psDBBean.getSiteId();
        String sAppId = psDBBean.getAppId();

        StringBuilder sb =new StringBuilder();
        String frameVersion = getFrameVersion();
        // 動作フレームワーク判定
        // V3の処理
        if (frameVersion.equals("3")) {
            // 検索対象範囲情報を取得してEXISTS句を組み立てる
            //return createExistsQuery(pRequestHash, pSession, psCompanyColumn, psEmployeeColumn);
            return " and 1 = 0 ";//V3のときは適当
        }
        // V4の処理
        else {
            // 検索対象範囲APIを呼び出してEXISTS句を取得する
            // ログインユーザーのグループをin句に挿入する形で取得
            String userGroupCodeListString = getUserGroupCodeListString(pSession);
            // 全て参照可能の場合は絞り込む必要がないので空文字を返却
            int nAllSectionCount = getAllSectionCount(psDBBean,sSiteId, sAppId, userGroupCodeListString);
            if ( nAllSectionCount > 0 ){
                return "";
            }
            // 組織階層コードのリスト
            List<String> sectionLayerList = CollUtil.newArrayList();

            // 自所属を取得
            int nUserSectionCount = getUserSectionCount(psDBBean,sSiteId, sAppId, userGroupCodeListString);
            if ( nUserSectionCount > 0 ){
                addUserSectionLayerList(psDBBean,sectionLayerList);
            }
            log.info("自所属:{}",sectionLayerList);

            // 基点組織を取得
            if (StrUtil.equalsAny("'"+necessity+"'",SEARCH_RANGE_USER_SECTION_DEFS,SEARCH_RANGE_SELF_COMP_DEFS,SEARCH_RANGE_USER_COMP_DEFS)) {
                int nBaseSectionCount = getBaseSectionCount(psDBBean, sSiteId, sAppId, userGroupCodeListString);
                if (nBaseSectionCount > 0) {
                    addBaseSectionLayerList(psDBBean, sSiteId, sAppId, userGroupCodeListString, sectionLayerList);
                }
            }
            log.info("基点组织:{}",sectionLayerList);

            // SQL付加用文字列の作成
            // 自所属以下および起点組織の一覧をとりだす
            int nSectionCount = sectionLayerList.size();
            // 自所属以下も起点組織もない場合はツリーを出さない
            if ( nSectionCount == 0 ){
                return "AND ( 1 = 0 )";
            }
            sb.append(" AND (");
            for(int i = 0 ; i < nSectionCount ; i++){
                if( i != 0){
                    sb.append(" OR ");
                }
                sb.append(psSectionColumn);
                sb.append(" LIKE ");
                sb.append(SysUtil.escDBString(sectionLayerList.get(i) + "%"));
            }
            sb.append(") ");

            String sExists = sb.toString();
            // 再取得判定用にEXISTS句を保存
            pSession.setAttribute(TmgReferList.SESSION_KEY_SEARCHRANGE, sExists);

            return sExists;
        }
    }

    /**
     * ログインユーザの所属グループコード一覧を取得します
     * @return ログインユーザの所属グループコード一覧(カンマ区切りin句形式)
     */
    public String getUserGroupCodeListString(HttpSession pSession) {
        // セッションからPsSessionを取得
        PsSession psSession =  (PsSession) pSession.getAttribute(Constant.PS_SESSION);
        Map<String, List<LoginGroupBO>> loginGroups = psSession.getLoginGroups();
        if(loginGroups == null) {
            return "''";
        }
        StringBuilder groups = new StringBuilder();
        boolean flg = false;
        // 法人ごとシステムごとにグループのセットを取り出す
        Iterator<Map.Entry<String,List<LoginGroupBO>>> i = loginGroups.entrySet().iterator();
        groups.append("'");
        while (i.hasNext()) {
            for (LoginGroupBO entity : i.next().getValue()) {
                groups.append(flg ? "','" + entity.getGroupCode() : entity.getGroupCode());
                flg = true;
            }
        }
        groups.append("'");
        return groups.toString();
    }

    /**
     * 現在実行中のアプリケーションIDの取得
     * @param psDBBean
     * @return
     */
    public String getCurrentApp(PsDBBean psDBBean) {
        return (String)psDBBean.getRequestHash().get("PageName");
    }

//    /**
//     * 組織ツリーデータのセッションキーを作成
//     * @param psDate
//     * @return
//     */
//    public String getOrgTreeCondition(String psDate, PsDBBean psDBBean) {
//        // V3の処理
//        if (getFrameVersion().equals("3")) {
//            return psDate;
//        }
//        // V4の処理
//        else {
//            return psDate + "_" + getCurrentApp(psDBBean);
//        }
//    }
//
//    /**
//     * 部局ツリーデータのセッションキーを作成
//     * @param psDate
//     * @return
//     */
//    public String getDivisionTreeCondition(String psDate, PsDBBean psDBBean) {
//        // V3の処理
//        if (getFrameVersion().equals("3")) {
//            return psDate;
//        }
//        // V4の処理
//        else {
//            return psDate + "_" + getCurrentApp(psDBBean);
//        }
//    }

    /**
     * 職員リストデータのセッションキーを作成
     * @param psDate
     * @param psSection
     * @param pbEmployee
     * @return
     */
    public String getEmpListCondition(String psDate, String psSection, boolean pbEmployee, PsDBBean psDBBean) {
        // V3の処理
        if (getFrameVersion().equals("3")) {
            return psDate + "_" + psSection + "_" + pbEmployee;
        }
        else {
            return psDate + "_" + psSection + "_" + pbEmployee + "_" + getCurrentApp(psDBBean);
        }
    }

    /**
     * 検索対象範囲EXISTS句取得処理（基点組織の一覧だけを返す）
     * @param pSession
     * @param psSectionColumn:MO_CSECTIONID_CKを想定
     * @return
     * @throws Exception
     */
    public String getExistsQueryBaseSection(PsDBBean psDBBean, HttpSession pSession,String psSectionColumn) throws Exception {
        // 初期化
        String sSiteId = psDBBean.getSiteId();
        String sAppId = psDBBean.getAppId();

        StringBuilder sb =new StringBuilder();
        // 動作フレームワーク判定
        // V3の処理
        if (getFrameVersion().equals("3")) {
            // 検索対象範囲情報を取得してEXISTS句を組み立てる
            //return createExistsQuery(pRequestHash, pSession, psCompanyColumn, psEmployeeColumn);
            return " and 1 = 0 ";//V3のときは適当
        }
        // V4の処理
        else {
            // 検索対象範囲APIを呼び出してEXISTS句を取得する
            // ログインユーザーのグループをin句に挿入する形で取得
            String userGroupCodeListString = getUserGroupCodeListString(pSession);
            // 全て参照可能の場合は絞り込む必要がないので空文字を返却
            int nAllSectionCount = getAllSectionCount(psDBBean,sSiteId, sAppId, userGroupCodeListString);
            if ( nAllSectionCount > 0 ){
                return "";
            }
            // 組織階層コードのリスト
            List<String> sectionLayerList = CollUtil.newArrayList();

            // 基点組織を取得
            int nBaseSectionCount = getBaseSectionCount(psDBBean,sSiteId, sAppId, userGroupCodeListString);
            if ( nBaseSectionCount > 0 ){
                addBaseSectionList(psDBBean,sSiteId, sAppId, userGroupCodeListString, sectionLayerList);
            }
            // SQL付加用文字列の作成
            // 自所属以下および起点組織の一覧をとりだす
            int nSectionCount = sectionLayerList.size();
            // 自所属以下も起点組織もない場合はツリーを出さない
            if ( nSectionCount == 0 ){
                return "AND ( 1 = 0 )";
            }
            sb.append(" AND ").append(psSectionColumn).append(" in (");
            for(int i = 0 ; i < nSectionCount ; i++){
                if( i != 0){
                    sb.append(",");
                }
                sb.append(SysUtil.escDBString(sectionLayerList.get(i)));
            }
            sb.append(") ");

            String sExists = sb.toString();
            // 再取得判定用にEXISTS句を保存
            pSession.setAttribute(TmgReferList.SESSION_KEY_SEARCHRANGE, sExists);
            return sExists;
        }
    }

    private void addBaseSectionList(PsDBBean psDBBean,String sSiteId, String sAppId, String userGroupCodeListString, List<String> sectionLayerList) throws Exception {
        Vector vecQuery = new Vector();
        vecQuery.add(buildSQLForSelectBaseSectionList(psDBBean,sSiteId,sAppId,userGroupCodeListString));
        PsResult psResult = psDBBeanUtil.getValuesforMultiquery(vecQuery, BEAN_DESC,psDBBean);
        for(int i = 0 ;i < psDBBeanUtil.getCount(psResult,0); i++){
            sectionLayerList.add(psDBBeanUtil.valueAtColumnRow(psResult,0, 1, i));
        }
    }

    private int getBaseSectionCount(PsDBBean psDBBean,String sSiteId, String sAppId, String userGroupCodeListString) throws Exception {
        Vector vecQuery = new Vector();
        vecQuery.add(buildSQLForSelectBaseSectionCheck(psDBBean,sSiteId,sAppId,userGroupCodeListString));
        log.info("【getBaseSectionCount：{}】",vecQuery);
        PsResult psResult = psDBBeanUtil.getValuesforMultiquery(vecQuery, BEAN_DESC,psDBBean);
        String sConut = psDBBeanUtil.valueAtColumnRow(psResult,0, 0, 0);
        return Integer.parseInt(sConut);
    }

    /**
     * ユーザーが所属するグループが基点組織を持つかを検索するSQLを返します
     * @param sSiteId
     * @param sAppId
     * @return ユーザーが所属するグループが基点組織を持つかを検索するSQL
     */
    private String buildSQLForSelectBaseSectionCheck(PsDBBean psDBBean,String sSiteId,String sAppId,String userGroupCodeListString) {
        return "select COUNT(1) " +
                "from HIST_GROUPDATAPERMISSION a " +
                "where " +
                "	a.HGP_CSITEID = " + SysUtil.escDBString(sSiteId) + " " +
                "and a.HGP_CAPPID = " + SysUtil.escDBString(sAppId) + " " +
                "and a.HGP_CCUSTOMERID = " + SysUtil.escDBString(psDBBean.getCustID()) +"  " +
                "and a.HGP_CSYSTEMID = '01' " +
                "and a.HGP_CGROUPID in (" + userGroupCodeListString + ") " +
                "and a.HGP_DSTARTDATE <= TRUNC(SYSDATE) " +
                "and a.HGP_DENDDATE >= TRUNC(SYSDATE) " +
                "and a.HGP_CBASESECTION_FLAG_NEED = 1 ";
    }

    private void addUserSectionLayerList(PsDBBean psDBBean,List<String> sectionLayerList) throws Exception {
        Vector vecQuery = new Vector();
        vecQuery.add(buildSQLForSelectUserSectionLayerList(psDBBean));
        log.info("【addUserSectionLayerList：{}】",vecQuery);
        PsResult psResult = psDBBeanUtil.getValuesforMultiquery(vecQuery, BEAN_DESC,psDBBean);
        for(int i = 0 ;i < psDBBeanUtil.getCount(psResult,0); i++){
            sectionLayerList.add(psDBBeanUtil.valueAtColumnRow(psResult,0, 0, i));
        }
    }

    /**
     * ユーザーの「自所属以下」の組織階層コードを検索するSQLを返します
     * @return ユーザーの「自所属以下」の組織階層コードを検索するSQL
     */
    private String buildSQLForSelectUserSectionLayerList(PsDBBean psDBBean) {
        return "select  " +
                "	b.MO_CLAYEREDSECTIONID  " +
                "from HIST_DESIGNATION a , MAST_ORGANISATION b  " +
                "where  " +
                "	a.HD_CCUSTOMERID_CK = " + SysUtil.escDBString(psDBBean.getCustID()) +" " +
                "and a.HD_CCOMPANYID_CK = " + SysUtil.escDBString(psDBBean.getCompCode()) +" " +
                "and a.HD_CEMPLOYEEID_CK = " + SysUtil.escDBString(psDBBean.getUserCode()) +" " +
                "and a.HD_DSTARTDATE_CK <= TRUNC(SYSDATE)  " +
                "and a.HD_DENDDATE >= TRUNC(SYSDATE)  " +
                "and b.MO_CCUSTOMERID_CK_FK=a.HD_CCUSTOMERID_CK  " +
                "and b.MO_CCOMPANYID_CK_FK=a.HD_CCOMPANYID_CK  " +
                "and b.MO_CSECTIONID_CK=a.HD_CSECTIONID_FK  " +
                "and b.MO_DSTART <= TRUNC(SYSDATE)  " +
                "and b.MO_DEND >=  TRUNC(SYSDATE)  ";
    }

    private int getUserSectionCount(PsDBBean psDBBean,String sSiteId, String sAppId, String userGroupCodeListString) throws Exception {
        Vector vecQuery = new Vector();
        vecQuery.add(buildSQLForSelectUserSectionCheck(psDBBean,sSiteId,sAppId,userGroupCodeListString,"1"));
        log.info("【getUserSectionCount：{}】",vecQuery);
        PsResult psResult = psDBBeanUtil.getValuesforMultiquery(vecQuery, BEAN_DESC,psDBBean);
        String sConut = psDBBeanUtil.valueAtColumnRow(psResult,0, 0, 0);
        return Integer.parseInt(sConut);
    }

    /**
     * ユーザーが所属するグループが「自所属以下」相当の条件式を持つかを検索するSQLを返します
     * @param sSiteId サイトID
     * @param sAppId　アプリID
     * @return ユーザーが所属するグループが「自所属以下」相当の条件式を持つかを検索するSQL
     */
    private String buildSQLForSelectUserSectionCheck(PsDBBean psDBBean,String sSiteId,String sAppId,String userGroupCodeListString,String useBaseSection) {
        necessity = histGroupdatapermissionService.getSearchRangeBySiteIdAndAppIdAndGroupCodeListStr(sSiteId,sAppId,userGroupCodeListString,useBaseSection);
//         fix: 未查询到数据默认使用自所属以下的规矩进行查询
        if (StrUtil.isBlank(necessity)) {
            necessity =SEARCH_RANGE_USER_SECTION_DEFS;
        }
        return "select COUNT(1) " +
                "from HIST_GROUPDATAPERMISSION a " +
                "where " +
                "	a.HGP_CSITEID = " + SysUtil.escDBString(sSiteId) + " " +
                "and a.HGP_CAPPID = " + SysUtil.escDBString(sAppId) + " " +
                "and a.HGP_CCUSTOMERID = " + SysUtil.escDBString(psDBBean.getCustID()) +"  " +
                "and a.HGP_CSYSTEMID = '01' " +
                "and a.HGP_CGROUPID in (" + userGroupCodeListString + ") " +
                "and a.HGP_DSTARTDATE <= TRUNC(SYSDATE) " +
                "and a.HGP_DENDDATE >= TRUNC(SYSDATE) " +
                "and a.HGP_CPERMNECESSITY in (" + necessity + ") ";
    }

    private int getAllSectionCount(PsDBBean psDBBean,String sSiteId, String sAppId, String userGroupCodeListString) throws Exception {
        Vector vecQuery = new Vector();
        vecQuery.add(buildSQLForSelectALLSectionCheck(psDBBean,sSiteId, sAppId, userGroupCodeListString));
        log.info("【getAllSectionCount，query：{}】",vecQuery);
        PsResult psResult = psDBBeanUtil.getValuesforMultiquery(vecQuery, BEAN_DESC,psDBBean);
        log.info("【getAllSectionCount，psResult：{}】",psResult);
        // PsResult(result=[[[0]]], exception=[])
        String sConut = psDBBeanUtil.valueAtColumnRow(psResult,0, 0, 0);
        return Integer.parseInt(sConut);
    }

    /**
     * ユーザーが所属するグループが「全所属」相当の条件式を持つかを検索するSQLを返します
     * @param sSiteId
     * @param sAppId
     * @return ユーザーが所属するグループが「全所属」相当の条件式を持つかを検索するSQL
     */
    private String buildSQLForSelectALLSectionCheck(PsDBBean psDBBean,String sSiteId,String sAppId,String userGroupCodeListString) {
        return "select COUNT(1) " +
                "from HIST_GROUPDATAPERMISSION a " +
                "where " +
                "	a.HGP_CSITEID = " + SysUtil.escDBString(sSiteId) + " " +
                "and a.HGP_CAPPID = " + SysUtil.escDBString(sAppId) + " " +
                "and a.HGP_CCUSTOMERID = " + SysUtil.escDBString(psDBBean.getCustID()) +"  " +
                "and a.HGP_CSYSTEMID = '01' " +
                "and a.HGP_CGROUPID in (" + userGroupCodeListString + ") " +
                "and a.HGP_DSTARTDATE <= TRUNC(SYSDATE) " +
                "and a.HGP_DENDDATE >= TRUNC(SYSDATE) " +
                "and a.HGP_CPERMNECESSITY in (" + SEARCH_RANGE_ALL_SECTION_DEFS + ") ";
    }

    /**
     * V3用検索対象範囲組み立て処理
     * @param psCompanyColumn
     * @param psEmployeeColumn
     * @return
     * @throws Exception
     */
    private String createExistsQuery(PsDBBean psDBBean, String psCompanyColumn, String psEmployeeColumn) throws Exception {
        // 検索対象範囲設定情報を取得
        BuildTargetSql btsSql = new BuildTargetSql(psDBBean);
        btsSql.execute();
        String sWhere = btsSql.getTargetSqlWhere();
        sWhere = SysUtil.replaceString(sWhere, "##", "");
        Vector vecFroms = btsSql.getTargetSqlFroms();
        Vector vecWheres = btsSql.getTargetSqlWheres();
        return buildSQLForSelectExists(sWhere, vecFroms, vecWheres, psCompanyColumn, psEmployeeColumn,psDBBean);
    }

    private void addBaseSectionLayerList(PsDBBean psDBBean,String sSiteId, String sAppId, String userGroupCodeListString, List<String> sectionLayerList) throws Exception {
        Vector vecQuery = new Vector();
        vecQuery.add(buildSQLForSelectBaseSectionList(psDBBean,sSiteId,sAppId,userGroupCodeListString));
        PsResult psResult = psDBBeanUtil.getValuesforMultiquery(vecQuery, BEAN_DESC,psDBBean);
        for(int i = 0 ;i < psDBBeanUtil.getCount(psResult,0); i++){
            sectionLayerList.add(psDBBeanUtil.valueAtColumnRow(psResult,0, 0, i));
        }
    }

    /**
     * ユーザーが所属するグループが基点組織を持つかを検索するSQLを返します
     * @param sSiteId
     * @param sAppId
     * @return ユーザーが所属するグループが基点組織を持つかを検索するSQL
     */
    private String buildSQLForSelectBaseSectionList(PsDBBean psDBBean,String sSiteId,String sAppId,String userGroupCodeListString) {
        return "select " +
                "	b.MGBS_CLAYEREDSECTIONID " +
                "  ,b.MGBS_CSECTIONID " +
                "from HIST_GROUPDATAPERMISSION a , MAST_GROUPBASESECTION b " +
                "where " +
                "	a.HGP_CSITEID = " + SysUtil.escDBString(sSiteId) + " " +
                "and a.HGP_CAPPID = " + SysUtil.escDBString(sAppId) + " " +
                "and a.HGP_CCUSTOMERID = " + SysUtil.escDBString(psDBBean.getCustID()) +"  " +
                "and a.HGP_CSYSTEMID = '01' " +
                "and a.HGP_CGROUPID in (" + userGroupCodeListString + ") " +
                "and a.HGP_DSTARTDATE <= TRUNC(SYSDATE) " +
                "and a.HGP_DENDDATE >= TRUNC(SYSDATE) " +
                "and a.HGP_CBASESECTION_FLAG_NEED = 1 " +
                "and b.MGBS_CCUSTOMERID = a.HGP_CCUSTOMERID " +
                "and b.MGBS_CSYSTEMID = a.HGP_CSYSTEMID " +
                "and b.MGBS_CGROUPID = a.HGP_CGROUPID " +
                "and b.MGBS_DSTARTDATE <= TRUNC(SYSDATE) " +
                "and b.MGBS_DENDDATE >= TRUNC(SYSDATE) ";
    }

    /**
     * V3用検索対象範囲EXISTS句の作成
     * @param psWhere
     * @param pVecFroms
     * @param pVecWheres
     * @param psCompanyColumn
     * @param psEmployeeColumn
     * @return
     */
    private String buildSQLForSelectExists(String psWhere, Vector pVecFroms, Vector pVecWheres, String psCompanyColumn, String psEmployeeColumn,PsDBBean psDBBean) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(
                "AND EXISTS ("	+
                        "SELECT "	+
                        "1 "	+
                        "FROM (" 	+
                        "SELECT "	+
                        "SR_0.HD_CCOMPANYID_CK AS EX_HD_CCOMPANYID_CK, "	+
                        "SR_0.HD_CEMPLOYEEID_CK AS EX_HD_CEMPLOYEEID_CK "	+
                        "FROM "	+
                        "HIST_DESIGNATION SR_0 "
        );
        // from句の作成
        for(int i = 0; i < pVecFroms.size(); i++) {
            // 追加テーブルを１つ取り出す
            String sFromVec = (String)pVecFroms.elementAt(i);
            // 異動歴以外なら追加
            if (!sFromVec.equalsIgnoreCase("HIST_DESIGNATION")) {
                sbSql.append("," + sFromVec + " SR_" + (i + 1));
            }
        }
        // where句の作成
        sbSql.append(" WHERE " + "SR_0.HD_DSTARTDATE_CK <= TO_DATE('").append(psDBBean.getSysDate()).append("', 'yyyy/MM/dd')").append(" AND ").append("SR_0.HD_DENDDATE >= TO_DATE('").append(psDBBean.getSysDate()).append("', 'yyyy/MM/dd') ");
        for(int i = 0; i < pVecWheres.size(); i++) {
            String sFromVec = (String)pVecFroms.elementAt(i);
            // 異動歴以外なら追加
            if (!sFromVec.equalsIgnoreCase("HIST_DESIGNATION")) {
                String sWhereVec = (String)pVecWheres.elementAt(i);
                // 別名定義の置換処理
                if (sWhereVec.length() > 0) {
                    sWhereVec = SysUtil.replaceString(sWhereVec, "##HD##", "SR_0");
                    sWhereVec = SysUtil.replaceString(sWhereVec, "##HD2##", "SR_0");
                    sWhereVec = SysUtil.replaceString(sWhereVec, "##" + (i + 1) + "##.", "SR_" + (i + 1) + ".");
                    sbSql.append(" AND " + sWhereVec);
                }
            }
        }
        // 絞込条件の作成
        if (psWhere.length() > 0 && !psWhere.equals("()")) {
            for(int i = 0; i < pVecFroms.size(); i++) {
                String sFromVec = (String)pVecFroms.elementAt(i);
                if (sFromVec.equalsIgnoreCase("HIST_DESIGNATION")) {
                    psWhere = SysUtil.replaceString(psWhere, sFromVec, "SR_0");
                }
                else {
                    psWhere = SysUtil.replaceString(psWhere, sFromVec, "SR_" + (i + 1));
                }
            }
            sbSql.append(" AND " + psWhere);
        }
        // EXISTS内部のSQLのクローズ
        sbSql.append(" ) EXISTSB " + "WHERE " + "EXISTSB.EX_HD_CCOMPANYID_CK = ").append(psCompanyColumn).append(" AND ").append("EXISTSB.EX_HD_CEMPLOYEEID_CK = ").append(psEmployeeColumn).append(")");
        return sbSql.toString();
    }

    private String buildSQLForSelectUserIdColumn(String psEmployee) {
        String sbSql = "SELECT MD_CCOLUMNNAME" +
                " FROM MAST_DATADICTIONARY m1,(SELECT MD_ID,MD_CTABLENAME" +
                " FROM MAST_DATADICTIONARY" +
                " WHERE MD_CCOLUMNNAME = '"+psEmployee+"') m2" +
                " WHERE m1.MD_CTABLENAME = m2.MD_CTABLENAME" +
                " AND m1.MD_CCOLUMNNAME LIKE '%_CUSERID'";
        return sbSql;
    }

    private String getUserIdColumn(PsDBBean psDBBean,String psEmployeeColumn) throws Exception {
        // パラメータを別名とカラム名に分離
        String[] sColumn = psEmployeeColumn.split("\\.");
        // カラム名からユーザIDカラム名を取得
        Vector vSql = new Vector();
        vSql.add(buildSQLForSelectUserIdColumn(sColumn[1]));
        PsResult psResult = psDBBeanUtil.getValuesforMultiquery(vSql, "",psDBBean);
        log.info("getUserIdColumn:{}",psResult);
        String sUserId = (String)psDBBeanUtil.valueAtColumnRow((Vector)(psResult.getResult()).elementAt(0), 0, 0);
        // 別名とユーザIDカラム名を結合
        return sColumn[0] + "." + sUserId;
    }

    /**
     * フレームバージョンの取得
     * @return
     */
    private String getFrameVersion() {
        String sFrameVersion = scCacheUtil.getSystemProperty("FrameVersion");
        if (sFrameVersion == null || sFrameVersion.startsWith("3")) {
            return "3";
        }
        else {
            return "4";
        }
    }

}
