package jp.smartcompany.framework.compatible.business.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.DbUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.compatible.business.Version3CompatibleLogic;
import jp.smartcompany.framework.compatible.entity.V3CompatiblePostEntity;
import jp.smartcompany.framework.dbaccess.BehaviorApplyLogic;
import jp.smartcompany.framework.dbaccess.DbControllerLogic;
import jp.smartcompany.framework.relation.PsEmpRelation;
import jp.smartcompany.framework.relation.PsOrgRelation;
import jp.smartcompany.framework.sysboot.dto.SystemPropertyDTO;
import jp.smartcompany.job.modules.core.business.BaseSectionBusiness;
import jp.smartcompany.job.modules.core.business.SectionChiefBusiness;
import jp.smartcompany.job.modules.core.pojo.bo.BaseSectionBO;
import jp.smartcompany.job.modules.core.pojo.bo.DesignationBO;
import jp.smartcompany.job.modules.core.pojo.bo.EvaluatorBO;
import jp.smartcompany.job.modules.core.pojo.entity.HistDesignationDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastCompanyDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastEmployeesDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDetailDO;
import jp.smartcompany.job.modules.core.service.*;
import jp.smartcompany.job.modules.core.util.PsConst;
import jp.smartcompany.job.modules.core.util.PsResult;
import jp.smartcompany.job.modules.core.util.PsSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Xiao Wenpeng
 * V3互換(API)クラス
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class Version3CompatibleLogicImpl implements Version3CompatibleLogic {

    private final ScCacheUtil scCacheUtil;
    private final SectionChiefBusiness sectionChiefBusiness;
    private final IHistDesignationService iHistDesignationService;
    private final IMastOrganisationService iMastOrganisationService;
    private final IMastEmployeesService iMastEmployeesService;
    private final BaseSectionBusiness baseSectionBusiness;
    private final DbControllerLogic dbControllerLogic;
    private final IMastCompanyService iMastCompanyService;
    private final PsEmpRelation psEmpRelation;
    private final PsOrgRelation psOrgRelation;
    private final BehaviorApplyLogic behaviorApplyLogic;
    private final IMastGenericDetailService iMastGenericDetailService;
    private final DataSource dataSource;

    /** 更新用エラーコード */
    private static final int UPDATE_ERROR_CODE = -2;
    /** システムコード */
    private static final String SYSTEM_CODE_01 = "01";
    /** データベースモード*/
    private static final String DATABASE_MODE = "1";
    /** ドメインコード */
    private static final String DOMAIN_CODE = "01";
    /** 日付フォーマット */
    private static final String DATE_FORMAT = "yyyy/MM/dd";
    /** ROWIDの正規表現パターン */
    private static final Pattern REGEXP_ROWID = Pattern.compile("(^|[ ]|[\t]|[^\\w\\.])rowid", Pattern.CASE_INSENSITIVE);
    private static final Pattern REGEXP_ROWID_ALIAS = Pattern.compile("((?>(^|[ ]|[\t])\\w+)\\.)rowid", Pattern.CASE_INSENSITIVE);

    @Override
    public String getSystemCode() {
        return null;
    }

    @Override
    public PsResult SelectQuerywithPermission(Vector pvQuery, String sUserid, Vector vPostid, String sGroupid, String sBeandesc, boolean bApplypermission, String sTargetuser, Vector vDept, String sCompid, String sCustid, String sSystemCode, String strGUID, String sDomainid, String sDate, Vector vSectionid, String sTarcomp, String sTarsection, HttpSession httpSession) throws Exception {
        // ログインユーザコード取得
        PsSession psSession = (PsSession) httpSession.getAttribute(Constant.PS_SESSION);
        String sLoginUserCode = psSession.getLoginUser();
        // リレーションID取得
        int nRelationId = this.getRelationIdForMask(
                sCustid, sCompid, sUserid, sTarcomp, sTargetuser, sDate,
                sLoginUserCode, sTarcomp, sTarsection, sSystemCode, sDomainid,
                PsConst.REPORTLINE_TYPE_DEF,
                httpSession, sGroupid, strGUID);

//		return this.SelectQuerywithPermissionAndQueryBatchAndTargetUser(
//						pvQuery, nRelationId, psSystemCode, psDomainid);
        PsResult result = this.SelectQuerywithPermissionAndQueryBatchAndTargetUser(	//暫定の修正
                pvQuery, nRelationId, sSystemCode, sDomainid);
        result.setResult((Vector)result.getResult().get(0));
        return result;
    }

    @Override
    public PsResult SelectSpecificComboQuerywithoutPermission(String sCustid, String sCompid, String sLanguage, String sDate, String sCode) {
        PsResult psResult = new PsResult();
        Vector<Vector<Object>> vResult = new Vector<Vector<Object>>();
        Vector<String> vecException = new Vector<String>();
        try {
            // 名称マスタ取得（名称マスタ明細データより取得）
            //2007/09/07 日付を「-」編集から「/」編集にして渡す by Konno
            // sCustid, sCompid, sLanguage, fmtchgDate(sDate), sCode
            List<MastGenericDetailDO> lGenericDetailList =
                    iMastGenericDetailService.list(
                        SysUtil.<MastGenericDetailDO>query()
                        .eq("MGD_CCUSTOMERID",sCustid)
                        .eq("MGD_CCOMPANYID_CK_FK",sCompid)
                        .eq("MGD_CLANGUAGE_CK","ja")
                        .le("MGD_DSTART_CK",SysUtil.transDateNullToDB(sDate))
                            .ge("MGD_DEND",SysUtil.transDateNullToDB(sDate))
                            .orderByAsc("MGD_CMASTERCODE")
                    );
            Vector<Object> vColumn;
            for (MastGenericDetailDO mastGenericDetailEntity : lGenericDetailList) {
                vColumn = new Vector<>();
                vColumn.add(mastGenericDetailEntity.getMgdCcustomerid());
                vColumn.add(mastGenericDetailEntity.getMgdCcompanyidCkFk());
                vColumn.add(mastGenericDetailEntity.getMgdCgenericgroupid());
                vColumn.add(mastGenericDetailEntity.getMgdClanguageCk());
                vColumn.add(mastGenericDetailEntity.getMgdCmastercode());
                vColumn.add(mastGenericDetailEntity.getMgdCgenericdetaildesc());
                vResult.add(vColumn);
            }

            // setExceptionに空を設定
            vecException.add("");
        } catch (Exception e) {
            // e.getMessageをセット
            vecException.add(e.getMessage());
        }

        // psResultへ結果セット
        psResult.setException(vecException);
        psResult.setResult(vResult);
        return psResult;
    }

    @Override
    public int setInsertValues(Vector vecQuery, String sUserid, String sBeandesc, String sCompid, String sCustid, String sSystemCode, String strGUID) {
        int nCount = 0;
        if (vecQuery != null && vecQuery.size() > 0) {
            // SQL実行
            Vector vUpdateCnt;
            try {
                vUpdateCnt = dbControllerLogic.executeUpdate(vecQuery);
                if (vUpdateCnt.size() > 0) {
                    // 最後のSQL文の更新件数をかえす
                    nCount = (Integer) vUpdateCnt.get(vUpdateCnt.size() - 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
                nCount = UPDATE_ERROR_CODE; // Exception発生時 -2をかえす
            }
        }
        return nCount;
    }

    @Override
    public int setInsertValuesForBlob(Vector vecQuery, Vector vecParams, String sUserId, String sBeanDesc, String sCompId, String sCustId, String sSystemCode, String sGUID) {
        int nCount = 0;
        if (vecQuery != null && vecQuery.size() > 0) {

            // SQL実行
            Vector vUpdateCnt = new Vector();
            try {
                vUpdateCnt = dbControllerLogic.executeUpdate(vecQuery, vecParams);
                if (vUpdateCnt.size() > 0) {

                    // 最後のSQL文の更新件数をかえす
                    nCount = (Integer) vUpdateCnt.get(vUpdateCnt.size() - 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
                nCount = Version3CompatibleLogicImpl.UPDATE_ERROR_CODE; // Exception発生時 -2をかえす
            }
        }
        return nCount;
    }

    /**
     * 複数SELECT文を実行します
     * @param vecQuery クエリ Vector
     * @param sUserid ユーザコード
     * @param vPostid 役職コード Vector
     * @param sGroupid グループコード
     * @param vPostweightage 役職重み Vector
     * @param sBeandesc ログ出力で用いる説明文字列
     * @param bApplypermission データアクセス制御の適用フラグ
     * @param sTargetuser 検索対象ユーザコード
     * @param vDept 検索対象組織コード Vector
     * @param sCompid 法人コード
     * @param sCustid 顧客コード
     * @param sSystemCode システムコード
     * @param strGUID GUID
     * @return PsResult 結果セット
     * @throws Exception システム例外
     */
    @Override
    public PsResult executeSelectQueryBatch(Vector vecQuery, String sUserid, Vector vPostid, String sGroupid, Vector vPostweightage, String sBeandesc, boolean bApplypermission, String sTargetuser, Vector vDept, String sCompid, String sCustid, String sSystemCode, String strGUID) throws Exception {
        // リレーションID取得
        int nRelationId = getRelation(sCustid, sCompid, sUserid,
                sCompid, sTargetuser, this.getDataBaseDate());
        return SelectQuerywithPermissionAndQueryBatchAndTargetUser(
                vecQuery, nRelationId, sSystemCode,DOMAIN_CODE);
    }

    /**
     * SELECT文実行（一意）
     * @param vecQuery クエリVector
     * @param sCustid 顧客コード
     * @param sCompid 検索者の法人コード
     * @param sUserid ユーザコード
     * @param sGroupid グループコード
     * @param sTargetcustid 検索対象者の顧客コード
     * @param sTargetcompid 検索対象者の法人コード
     * @param sTargetuserorsection 検索対象のユーザコードまたは組織コード
     * @param sBeandesc ログ出力で用いる説明文字列
     * @param sSystemCode システムコード
     * @param sDomainid ドメインコード
     * @param strGUID GUID
     * @param sDate 検索基準日
     * @param httpSession セッション情報
     * @return Vector 結果セット
     * @throws Exception システム例外
     */
    @Override
    public PsResult SelectQuerywithTargetUser(Vector vecQuery, String sCustid, String sCompid, String sUserid, String sGroupid, String sTargetcustid, String sTargetcompid, String sTargetuserorsection, String sBeandesc, String sSystemCode, String sDomainid, String strGUID, String sDate, HttpSession httpSession) throws Exception {
        // ログインユーザコード取得
        PsSession psSession = (PsSession) httpSession.getAttribute(Constant.PS_SESSION);
        String sLoginUserCode = psSession.getLoginUser();
        // リレーションID取得
        int nRelationId = this.getRelationIdForMask(
                sCustid, sCompid, sUserid, sTargetcompid, sTargetuserorsection, sDate,
                sLoginUserCode, sTargetcompid, sTargetuserorsection, sSystemCode, sDomainid,
                PsConst.REPORTLINE_TYPE_DEF, httpSession, sGroupid, strGUID);
        return SelectQuerywithPermissionAndQueryBatchAndTargetUser(
                vecQuery, nRelationId, sSystemCode, sDomainid);
    }

    /**
     * SELECT文（複数対象）を実行します
     * @param pvQuery クエリVector
     * @param sUserid ユーザコード
     * @param vPostid 役職コード Vector
     * @param sGroupid グループコード
     * @param sBeandesc ログ出力で用いる説明文字列
     * @param sTargetuser 検索対象ユーザコード
     * @param vDept 検索者の組織コード Vector
     * @param sCompid 検索者の法人コード
     * @param sCustid 顧客コード
     * @param sSystemCode システムコード
     * @param strGUID GUID
     * @param sDomainid ドメインコード
     * @param sDate 検索基準日
     * @param vSectionid 検索者組織コード（組織用）
     * @param sTarcomp 検索対象者の法人コード
     * @param sTarsection 検索対象者の組織コード
     * @param httpSession セッション情報
     * @return PsResult 結果セット
     * @throws Exception システム例外
     */
    @Override
    public PsResult SelectMultiQuerywithPermission(Vector pvQuery, String sUserid, Vector vPostid, String sGroupid, String sBeandesc, String sTargetuser, Vector vDept, String sCompid, String sCustid, String sSystemCode, String strGUID, String sDomainid, String sDate, Vector vSectionid, String sTarcomp, String sTarsection, HttpSession httpSession)
    throws SQLException{
        return this.SelectMultiQuerywithPermissionwithRangeLine(
                pvQuery,    sUserid,     vPostid,
                sGroupid,    sBeandesc,   sTargetuser,
                vDept,       sCompid,     sCustid,
                sSystemCode, strGUID,     sDomainid,
                sDate,       vSectionid,  sTarcomp,
                sTarsection, null, null, httpSession);
    }

    /**
     * セキュリティ判定なしでSELECT文を実行しPsResultとして返します。
     * @param pvSQL クエリVector
     * @param psCustid 顧客コード
     * @param psCompid 検索者の法人コード
     * @param sUserid ユーザコード
     * @param sGroupid グループコード
     * @param sBeandesc ログ出力で用いる説明文字列
     * @param psSystemCode システムコード
     * @param psGUID GUID
     * @param pbDesigSW	 true:SQL先頭にDesig4項目あり/false:Desig4項目なし
     * @return	PsResult 結果セット
     * @throws Exception システム例外
     */
    @Override
    public PsResult executeMultiQuery(Vector pvSQL, String psCustid, String psCompid, String sUserid, String sGroupid, String sBeandesc, String psSystemCode, String psGUID, boolean pbDesigSW) throws Exception {
        String sSql;
        PsResult psResult = new PsResult();
        Vector<String> vecException = new Vector<String>();
        Vector<Vector<Vector<Object>>> vectorResult = new Vector<Vector<Vector<Object>>>();
        Vector<Vector<Object>> vsqlResult;
        Vector<Vector<Object>> vecResult;

        Connection conn = dataSource.getConnection();
        conn.setReadOnly(true);
        // SQLの数分処理
        for (int nSqlCnt = 0; nSqlCnt < pvSQL.size(); nSqlCnt++) {
            sSql = pvSQL.elementAt(nSqlCnt).toString();
            if (pbDesigSW) {	// Desig4項目の場合に正規区分を付加
                sSql = this.addVirtualRole(sSql);
            }
            vecResult = new Vector<>(); // 取得できなかった場合、初期化のまま設定
            try {
                log.info("【executeMultiQuery：{}】",sSql);
                // SQL実行
                //2007/11/21 SQL内にROWIDが含まれる場合、rowidtocharで文字列に変換する処理を追加
                vsqlResult =dbControllerLogic.executeQuery(addRowidConvert(sSql),conn);
                //vsqlResult = this.gDBControllerLogic.executeQuery(sSql);
                if (vsqlResult != null && vsqlResult.size() > 1) {

                    // カラム名の１行目は設定しない
                    for (int i = 1; i < vsqlResult.size(); i++) {
                        // 検索結果をセット
                        vecResult.add(vsqlResult.get(i));
                    }
                }

                // setExceptionにnew String()を設定
                vecException.add("");
            } catch (Exception e) {

                // e.getMessageをセット
                vecException.add(nSqlCnt, e.getMessage());
            }

            // 1個のSQL分の結果セット
            vectorResult.add(vecResult);
        }
        DbUtil.close(conn);
        psResult.setException(vecException);
        psResult.setResult(vectorResult);
        return psResult;
    }

    /**
     * ストアドプロシージャ実行
     * @param sCompid 検索者の法人コード
     * @param sUserid ユーザコード
     * @param sSystemCode システムコード
     * @param sGUID GUID
     * @param vSQL SQL文のVector
     * @param vParams パラメータのVector
     * @return boolean 更新結果
     * @throws Exception システム例外
     */
    @Override
    public boolean executeProcedure(String sCompid, String sUserid, String sSystemCode, String sGUID, Vector vSQL, Vector vParams) throws Exception {
        try {
            // プロシージャ実行
            return dbControllerLogic.executeProcedure(vSQL, vParams);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 所属長を取得します
     * @param sCustid 検索対象顧客コード
     * @param sCompid 検索対象法人コード
     * @param sDeptid 検索対象組織コード
     * @param sDate 検索基準日
     * @param bIncludeActual 仮想兼務の適用可否フラグ
     * @return PsResult 所属長情報を含む結果セット
     */
    @Override
    public PsResult checkforchief(String sCustid, String sCompid, String sDeptid, String sDate, boolean bIncludeActual) {
        PsResult psResult = new PsResult();
        Vector<Vector<Object>> vResult = new Vector<Vector<Object>>();
        Vector<String> vecException = new Vector<String>();
        try {

            // TODO 仮想対応のできないか（getEvaluator）
            // 所属長役職コードの取得（異動歴、基本情報、役職マスタを結合し取得）
            String sPostId = null;
            //2007/09/07 日付を「-」編集から「/」編集にして渡す by Konno
            List<V3CompatiblePostEntity> lPostList =
                    iMastEmployeesService.getVersion3SectionChief(sCustid, sCompid, sDeptid, fmtchgDate(sDate), null, true);
            if (lPostList.size() > 0) {
                V3CompatiblePostEntity postEntity = lPostList.get(0);
                sPostId = postEntity.getHdCpostidFk();
                if ("".equals(sPostId)) {
                    sPostId = null;
                }
            }
            // 所属長情報取得(SQLファイルで役職コード、仮想兼務の適用可否を判定してSQL実行）
            //2007/09/07 日付を「-」編集から「/」編集にして渡す by Konno
            List<V3CompatiblePostEntity> lPostEntityList =
                    iMastEmployeesService.getVersion3SectionChief(sCustid, sCompid, sDeptid, fmtchgDate(sDate), sPostId, bIncludeActual);
            Vector<Object> vColumn;
            for (V3CompatiblePostEntity v3CompatiblePostEntity : lPostEntityList) {
                vColumn = new Vector<>();
                vColumn.add(v3CompatiblePostEntity.getHdCpostidFk());
                vColumn.add(v3CompatiblePostEntity.getMeCemployeeidCk());
                vColumn.add(v3CompatiblePostEntity.getMapNweightage());
                vColumn.add(v3CompatiblePostEntity.getMapCpostname());
                vResult.add(vColumn);
            }

            // 取得できた場合、setExceptionに空を設定
            vecException.add("");
        } catch (Exception e) {
            // e.getMessageをセット
            vecException.add(e.getMessage());
        }

        // psResultへ結果セット
        psResult.setException(vecException);
        psResult.setResult(vResult);
        return psResult;
    }

    @Override
    public PsResult getCompanyInfo(String sCustid, String sLanguage, String sDate) {
        PsResult psResult = new PsResult();
        Vector<Vector<Object>> vResult = new Vector<>();
        Vector<String> vecException = new Vector<>();
        try {
            // 法人一覧取得（法人ツリーマスタよりコードと名称を取得）
            //2007/09/07 日付を「-」編集から「/」編集にして渡す by Konno
            List<MastCompanyDO> lMastCompanyList =
                    iMastCompanyService.getCompanyInfo(sCustid,sLanguage,fmtchgDate(sDate));
            Vector<Object> vColumn;
            for (int i = 0; i < lMastCompanyList.size(); i++) {
                vColumn = new Vector<>();
                MastCompanyDO mastCompanyEntity = lMastCompanyList.get(i);
                vColumn.add(mastCompanyEntity.getMacCcompanyidCk());
                vColumn.add(mastCompanyEntity.getMacCcompanyname());
                vResult.add(vColumn);
            }
            // 取得できた場合、setExceptionに空を設定
            vecException.add("");
        } catch (Exception e) {
            e.printStackTrace();
            // e.getMessageをセット
            vecException.add(e.getMessage());
        }
        // psResultへ結果セット
        psResult.setException(vecException);
        psResult.setResult(vResult);
        return psResult;
    }

    @Override
    public String getDataBaseDate() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            return sdf.format(new Date());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PsResult SelectMultiQuerywithPermission(Vector vecQuery, String sUserid, Vector vPostid, String sGroupid, String sBeandesc, String sTargetuser, Vector vDept, String sCompid, String sCustid, String sSystemCode, String strGUID, String sDomainid, String sDate, Vector vSectionid, String sTarcomp, String sTarsection, String sPageStart, String sPageEnd, HttpSession httpSession) throws Exception {
        return SelectMultiQuerywithPermissionwithRangeLine(
                vecQuery,    sUserid,    vPostid,
                sGroupid,    sBeandesc,  sTargetuser,
                vDept,       sCompid,    sCustid,
                sSystemCode, strGUID,   sDomainid,
                sDate,       vSectionid, sTarcomp,
                sTarsection, Integer.valueOf(sPageStart),
                Integer.valueOf(sPageEnd), httpSession);
    }

    @Override
    public PsResult SelectMultiQuerywithPermissionForJk(Vector pvQuery, String sUserid, Vector vPostid, String sGroupid, String sBeandesc, String sTargetuser, Vector vDept, String sCompid, String sCustid, String sSystemCode, String strGUID, String sDomainid, String sDate, Vector vSectionid, String sTarcomp, String sTarsection, String sPageStart, String sPageEnd, HttpSession httpSession) throws Exception {
        return SelectMultiQuerywithPermissionwithRangeLine(
                pvQuery, sUserid, vPostid, sGroupid, sBeandesc, sTargetuser,
                vDept, sCompid, sCustid, sSystemCode, strGUID, sDomainid,
                sDate, vSectionid, sTarcomp, sTarsection,
                Integer.valueOf(sPageStart), Integer.valueOf(sPageEnd), httpSession);
    }

    @Override
    public boolean isDebugModeEnabled() {
        boolean bDebugflag = false;
        String sDebugMode = this.getSystemProperty("DebugMode");
        if ("yes".equals(sDebugMode)) {
            bDebugflag = true;
        }
        return bDebugflag;
    }

    @Override
    public String getDatabaseMode() {
        return DATABASE_MODE;
    }

    @Override
    public int getRelation(String sCustomerID, String sLoginCompanyID, String sLoginUserID, String sTargetCompanyID, String sTargetUserID, String sDate)  {
        HttpSession session = Objects.requireNonNull(ContextUtil.getHttpRequest()).getSession();
        PsSession psSession = (PsSession) session.getAttribute(Constant.PS_SESSION);
        // ログインユーザコード取得
        String sLoginUserCode = psSession.getLoginUser();
        // TODO 検索対象ユーザコード取得できないときの処理未実装
        // 検索対象ユーザコード取得
        String sTargetUserCode =
                this.getUseridForV4(sCustomerID, sTargetCompanyID, sTargetUserID, sDate);
        //2007/09/07 日付を「-」編集から「/」編集にして渡す by Konno
        //2007/09/07 日付を「-」編集から「/」編集にして渡す by Konno
        return psEmpRelation.getRelationId(
                sCustomerID, sLoginUserCode, sTargetUserCode,
                this.getSystemCode(), fmtchgDate(sDate));
    }

    /**
     * リレーション情報を取得します
     * @param sCustomerID 顧客コード
     * @param sLoginCompanyID ログイン法人コード
     * @param sLoginUserID ログイン社員番号
     * @param sTargetCompanyID 検索対象法人コード
     * @param sTargetUserID 検索対象社員番号
     * @param sDate 検索基準日
     * @param sReportLine レポートラインタイプ
     * @param httpSession セッション
     * @param sGroupID グループコード
     * @param sSystemCode システムコード
     * @param sGUID GUID
     * @param bFixed true:確定済み情報を参照, false:未確定情報を参照
     * @return Vector リレーション情報 - 0:リレーションID,
     *                                    1:適用開始日(自動判定の場合はnull),
     *                                    2:適用終了日(自動判定の場合はnull),
     *                                    3:判別区分(0:自動判定, 1:役割関係定義)
     */
    @Override
    public Vector<Integer> getRelation(String sCustomerID, String sLoginCompanyID, String sLoginUserID, String sTargetCompanyID, String sTargetUserID, String sDate, String sReportLine, HttpSession httpSession, String sGroupID, String sSystemCode, String sGUID, boolean bFixed) throws Exception {
        return new Vector<>();
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
        BaseSectionBO baseSectionBO = baseSectionBusiness.getBaseSection(sCreterialDate);
        Map<String, String> hBaseSection = baseSectionBO.getHmCompany().get(SYSTEM_CODE_01);
        String sBaseSection = "";
        if (MapUtil.isNotEmpty(hBaseSection) && hBaseSection.containsKey(sCompID)) {
            // 法人コードをキーに基点組織情報取得
            String sbSection = hBaseSection.get(sCompID);
            if (!this.isEmptyString(sbSection)) {
                // 先頭の法人コードを削除
                int nCnt = sbSection.indexOf("#");
                sBaseSection = sbSection.substring(nCnt + 1, sbSection.length());
                // 区切り文字を変換:それぞれの組織コードにシングルクォーテーションを付加する
                sBaseSection = SysUtil.replaceString(sBaseSection, "!", "','");
                // 区切り文字を変換:前後にシングルクォーテーションを付加する
                sBaseSection = "'" + sBaseSection + "'";
            }
        }
        return sBaseSection;
    }

    @Override
    public Map<String,String> getBaseSectionListMultiCompForSQL(String sCustID, String sCompID, String sEmployeeID, String sCreterialDate) {
        Map<String,String> mBaseSectionList = MapUtil.newHashMap();
        BaseSectionBO baseSectionBO = baseSectionBusiness.getBaseSection(sCreterialDate);
        Map<String, String> hBaseSection = baseSectionBO.getHmCompany().get(SYSTEM_CODE_01);
        if (hBaseSection != null) {
            // 法人コード毎に基点組織情報取得
            for (Map.Entry<String, String> entry : hBaseSection.entrySet()) {
                String sComp = entry.getKey();
                String sbSection = entry.getValue();
                if (!isEmptyString(sbSection)) {
                    String sBaseSection = "";
                    // 先頭の法人コードを削除
                    int nCnt = sbSection.indexOf("#");
                    sBaseSection = sbSection.substring(nCnt + 1);
                    // 区切り文字を変換:それぞれの組織コードにシングルクォーテーションを付加する
                    sBaseSection = SysUtil.replaceString(sBaseSection, "!", "','");
                    // 区切り文字を変換:前後にシングルクォーテーションを付加する
                    sBaseSection = "'" + sBaseSection + "'";
                    // Mapに追加
                    mBaseSectionList.put(sComp, sBaseSection);
                }
            }
        }
        return mBaseSectionList;
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
            return iMastOrganisationService.getSubSection(sCustID,
                    sCompID, sSection,
                    SysUtil.transStringToDate(sCreterialDate)).toString();
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
        return iMastOrganisationService.selectHighSection(
                            sCustID, sCompID, sSection,
                            SysUtil.transStringToDate(sCreterialDate)).toString();
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
            HttpSession session = Objects.requireNonNull(ContextUtil.getHttpRequest()).getSession();
            PsSession psSession = (PsSession) session.getAttribute(Constant.PS_SESSION);
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
                        .le("HD_DSTARTDATE_CK",date)
                        .ge("HD_DENDDATE",date)
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
     * SQL文実行、結果セットを行います
     * SelectQuerywithPermissionとexecuteSelectQueryBatchとSelectQuerywithTargetUser用
     * @param pvQuery クエリ Vector
     * @param pnRelationId リレーションID
     * @param psSystemCode システムコード
     * @param psDomainid ドメインコード
     * @return PsResult 結果セット
     */
    protected PsResult SelectQuerywithPermissionAndQueryBatchAndTargetUser(
           Vector pvQuery, int pnRelationId,
           String psSystemCode,String psDomainid) throws SQLException {
        String sSql;
        PsResult psResult = new PsResult();
        Vector<String> vecException = new Vector<>();
        Vector<Vector<Vector<Object>>> vbResult = new Vector<>();
        Vector<Vector<Object>> vsqlResult;
        Vector<Boolean> vBehav;
        Vector<Vector<Object>> vResult;

        Connection conn = dataSource.getConnection();
        // SQLの数分処理
        for (int nSqlCnt = 0; nSqlCnt < pvQuery.size(); nSqlCnt++) {
            sSql = pvQuery.elementAt(nSqlCnt).toString();
            vResult = new Vector<>(); // 取得できなかった場合、初期化のまま設定
            try {

                // SQL実行
                //2007/11/21 SQL内にROWIDが含まれる場合、rowidtocharで文字列に変換する処理を追加
                vsqlResult = dbControllerLogic.executeQuery(addRowidConvert(sSql),conn);
                //vsqlResult = this.gDBControllerLogic.executeQuery(sSql);
                if (vsqlResult != null && vsqlResult.size() > 0) {
                    // ビヘイビア適用
                    vBehav = setBehaviorApply(vsqlResult.get(0), psSystemCode, psDomainid, pnRelationId);
                    // マスキング判定結果により結果セット
                    vResult = setColumnDataAndMask(
                            vsqlResult, vBehav, vResult);
                }
                // setExceptionにnew String()を設定
                vecException.add("");
            } catch (Exception e) {

                // e.getMessageをセット
                vecException.add(nSqlCnt, e.getMessage());
            }

            // 1個のSQL分の結果セット
            vbResult.add(vResult);
        }
        if (conn!=null){
            conn.close();
        }
        psResult.setException(vecException);
        psResult.setResult(vbResult);
        return psResult;
    }

    /**
     * マスキング判定結果によりSQL実行結果セット
     * SelectQuerywithPermissionとexecuteSelectQueryBatchとSelectQuerywithTargetUser用
     * @param pvsqlResult SQL実行結果
     * @param pvBehav マスキング判定結果
     * @param pvResult 結果セット
     * @return Vector 結果セット（マスキング判定結果適用）
     */
    protected Vector<Vector<Object>> setColumnDataAndMask(
            final Vector<Vector<Object>> pvsqlResult,
            final Vector<Boolean> pvBehav,
            final Vector<Vector<Object>> pvResult) {

        // 検索結果の取得数分処理（先頭のカラム情報は除く）
        for (int nRcnt = 1; nRcnt < pvsqlResult.size(); nRcnt++) {
            Vector<Object> vColmData = new Vector<Object>();
            Vector<Object> vDataOneLine = (Vector<Object>) pvsqlResult.get(nRcnt); // 1行分のデータ

            // カラム数分処理
            for (int nCnt = 0; nCnt < vDataOneLine.size(); nCnt++) {
                if ((boolean) pvBehav.get(nCnt)) {

                    // 権限あり
                    vColmData.add((Object) vDataOneLine.get(nCnt));
                } else {

                    // 2013/06/19	matsukawa.y
                    // V4と同じ仕組を利用
                    vColmData.add(SysUtil.getPermissionString());
                }
            }
            pvResult.add(vColmData);
        }
        return pvResult;
    }


    /**
     * マスキング判定結果取得(ビヘイビア適用)：リレーションID（一意）
     * @param pvColumn カラム
     * @param psSystemCode システムコード
     * @param psDomainid ドメインコード
     * @param pnRelationId リレーションID
     * @return Vector マスキング判定結果取得
     */
    protected Vector<Boolean> setBehaviorApply(final Vector pvColumn,
                                               final String psSystemCode,
                                               final String psDomainid,
                                               final int pnRelationId) {
        HttpSession session = Objects.requireNonNull(ContextUtil.getHttpRequest()).getSession();
        PsSession psSession = (PsSession) session.getAttribute(Constant.PS_SESSION);
        Vector<Boolean> vBehav = new Vector<Boolean>();
        for (Object o : pvColumn) {
            // ビヘイビアを適用しない場合
            if (!psSession.isUseBehaviorForV3Compatible()) {    // V3LoadAppAction#initializeでセットされます
                vBehav.add(true);
                // ビヘイビアを適用する
            } else {
                vBehav.add(behaviorApplyLogic.isMask(
                        psSystemCode, psDomainid,
                        (Integer.valueOf(pnRelationId)).toString(),
                        (String) o));
            }
        }
        return vBehav;
    }

    protected int getRelationIdForMask(String psCustid, String psCompid,
                                       String psUserid, String psTarcomp, String psTargetuser,
                                       String psDate, String psLoginUser, String psTarcompForSection,
                                       String psTarsectionForSection, String psSystemCode,
                                       String psDomainid, String psReportLine, HttpSession pHttpSession,
                                       String psGroupID, String psGUID) throws Exception {
        int nRelationId;
        if ("01".equals(psDomainid)) {
            nRelationId = getRelation(psCustid, psCompid, psUserid, psTarcomp,
                    psTargetuser, psDate);
        } else {
            nRelationId = psOrgRelation.getRelationId(psCustid,
                    psLoginUser, psTarcompForSection, psTarsectionForSection,
                    psSystemCode, fmtchgDate(psDate));
        }
        return nRelationId;
    }


    /**
     * SELECT文（複数対象・複数対象行範囲あり・条件検索用SELECT文）を実行します
     * @param pvQuery クエリVector
     * @param psUserid ユーザコード
     * @param pvPostid 役職コード Vector
     * @param psGroupid グループコード
     * @param psBeandesc ログ出力で用いる説明文字列
     * @param psTargetuser 検索対象ユーザコード
     * @param pvDept 検索者の組織コード Vector
     * @param psCompid 検索者の法人コード
     * @param psCustid 顧客コード
     * @param psSystemCode システムコード
     * @param psGUID GUID
     * @param psDomainid ドメインコード
     * @param psDate 検索基準日
     * @param pvSectionid 検索者組織コード（組織用）
     * @param psTarcomp 検索対象者の法人コード
     * @param psTargetDept 検索対象者の組織コード
     * @param pnStartLine 開始行
     * @param pnEndLine 終了行
     * @param pHttpSession セッション情報
     * @return PsResult 結果セット
     * @throws Exception システム例外
     */
    protected PsResult SelectMultiQuerywithPermissionwithRangeLine(
            final Vector pvQuery,    final String psUserid,     final Vector pvPostid,
            final String psGroupid,    final String psBeandesc,   final String psTargetuser,
            final Vector pvDept,       final String psCompid,     final String psCustid,
            final String psSystemCode, final String psGUID,     final String psDomainid,
            final String psDate,       final Vector pvSectionid,  final String psTarcomp,
            final String psTargetDept,  final Integer pnStartLine, final Integer pnEndLine,
            final HttpSession pHttpSession
    ) throws SQLException {
        String sSql;
        PsResult psResult = new PsResult();
        Vector<String> vecException = new Vector<>();
        Vector<Vector<Vector<Object>>> vectorResult = new Vector<>();
        Vector<Vector<Object>> vsqlResult;
        Vector<Vector<Object>> vResult;
        Connection conn = dataSource.getConnection();
        // SQLの数分処理
        for (int nSqlCnt = 0; nSqlCnt < pvQuery.size(); nSqlCnt++) {
            // SQL文のカラム名4個目の次に正規区分のカラム"HD_NOFFCIALORNOT"追加
            sSql = this.addVirtualRole(pvQuery.elementAt(nSqlCnt).toString());
            vResult = new Vector<>(); // 取得できなかった場合、初期化のまま設定
            try {
                // SQL実行
                //2007/11/21 SQL内にROWIDが含まれる場合、rowidtocharで文字列に変換する処理を追加
                vsqlResult = dbControllerLogic.executeQuery(addRowidConvert(sSql),conn);
                //vsqlResult = this.gDBControllerLogic.executeQuery(sSql);
                if (vsqlResult != null && vsqlResult.size() > 0) {
                    // マスキング判定(ビヘイビア適用)・結果セット
                    vResult = this.setColumnDataAndBehaviorApplyPlural(
                            psCustid, psCompid, psUserid, psDate, psTarcomp, psTargetDept,
                            vsqlResult,vsqlResult.get(0),
                            psSystemCode, psDomainid, pnStartLine, pnEndLine,
                            PsConst.REPORTLINE_TYPE_DEF,
                            pHttpSession, psGroupid, psGUID);
                }
                // setExceptionにnew String()を設定
                vecException.add(new String());
            } catch (Exception e) {
                // e.getMessageをセット
                vecException.add(nSqlCnt, e.getMessage());
            }
            // 1個のSQL分の結果セット
            vectorResult.add(vResult);
        }
        if (conn!=null){
            conn.close();
        }
        psResult.setException(vecException);
        psResult.setResult(vectorResult);
        return psResult;
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
     * データ設定・マスキング判定結果取得(ビヘイビア適用)：対象者複数
     * @param psCustid 顧客コード
     * @param psCompid 検索者の法人コード
     * @param psUserid ユーザコード
     * @param psDate 検索基準日
     * @param psTarcomp 検索対象者の法人コード
     * @param psTarsection 検索対象者の組織コード
     * @param pvsqlResult SQL実行結果
     * @param pvColumn カラム
     * @param psSystemCode システムコード
     * @param psDomainid ドメインコード
     * @param pnStartLine 開始行
     * @param pnEndLine 終了行
     * @param psReportLine レポートラインタイプ
     * @param pHttpSession  セッション情報
     * @param psGroupID グループコード
     * @param psGUID GUID
     * @return Vector マスキング判定結果取得
     * @throws Exception システム例外
     */
    protected Vector<Vector<Object>> setColumnDataAndBehaviorApplyPlural(
            String psCustid, String psCompid, String psUserid,
            String psDate, String psTarcomp, String psTarsection,
            Vector<Vector<Object>> pvsqlResult,
            Vector<Object> pvColumn,
            String psSystemCode,String psDomainid,
            Integer pnStartLine,Integer pnEndLine,
            String psReportLine,
            HttpSession pHttpSession,
            String psGroupID,
            String psGUID
    ) throws Exception {
        // 開始行、終了行が設定されている場合
        int nSline = 1;
        int nEline = pvsqlResult.size();
        if (pnStartLine != null && pnEndLine != null && pnStartLine <= pnEndLine) {
            if (pnEndLine < pvsqlResult.size() - 1) {
                nEline = pnEndLine + 1;
            }
            if (pnStartLine > nSline) {
                nSline = pnStartLine;
            }
        }

        // SQL実行結果の行数分処理
        Vector<Vector<Object>> vResult = new Vector<Vector<Object>>();
        HttpSession session = Objects.requireNonNull(ContextUtil.getHttpRequest()).getSession();
        PsSession psSession = (PsSession) session.getAttribute(Constant.PS_SESSION);
        for (int nRcnt = nSline; nRcnt < nEline; nRcnt++) {

            // 取得した対象者法人コード
            String sTargetcomp = (String) (pvsqlResult.get(nRcnt)).get(2);

            // 取得した対象者ユーザコード
            String sTargetUser = (String) (pvsqlResult.get(nRcnt)).get(3);

            // ログインユーザコード取得
            String sLoginUserCode = psSession.getLoginUser();

            // リレーションID取得
            int nRelationId = getRelationIdForMask(
                    psCustid, psCompid, psUserid, sTargetcomp, sTargetUser, psDate,
                    sLoginUserCode, psTarcomp, psTarsection, psSystemCode, psDomainid,
                    psReportLine, pHttpSession, psGroupID, psGUID);

            Vector<Object> vColmData = new Vector<>();
            Vector<Object> vDataOneLine = pvsqlResult.get(nRcnt); // 1行分のデータ

            // カラム数分処理
            for (int nCnt = 0; nCnt < pvColumn.size(); nCnt++) {

                boolean bBehav;

                // ビヘイビアを適用しない場合
                if (!psSession.isUseBehaviorForV3Compatible()) {	// V3LoadAppAction#initializeでセットされます
                    bBehav = true;
                } else {
                    // マスキング判定結果取得
                    bBehav = behaviorApplyLogic.isMask(
                            psSystemCode, psDomainid,
                            (Integer.valueOf(nRelationId)).toString(),
                            (String) pvColumn.get(nCnt));
                }
                if (bBehav) {

                    // 権限あり
                    vColmData.add(vDataOneLine.get(nCnt));
                } else {

                    // 2013/06/19	matsukawa.y
                    // V4と同じ仕組を利用
                    vColmData.add(SysUtil.getPermissionString());
                }
            }
            vResult.add(vColmData);
        }
        return vResult;
    }

    /**
     * SQL文内にROWIDが含まれるときrowidtochar関数を追加
     * 2007/11/21追加
     * @param psSQL
     * @return
     */
    private String addRowidConvert( String psSQL ){
        if (StrUtil.isBlank(psSQL)) {
            return "";
        }
        // ROWID単体で使っているとき
        Matcher matcher = REGEXP_ROWID.matcher(psSQL).reset();
        if (matcher.find()) {
            // カッコスタートの場合はカッコを含めて置換
            if (matcher.group().startsWith("(")) {
                psSQL = matcher.replaceAll("( rowidtochar(ROWID)");
            } else {
                psSQL = matcher.replaceAll(" rowidtochar(ROWID)");
            }
        }
        // ROWIDをテーブル別名付きで使っているとき
        matcher = REGEXP_ROWID_ALIAS.matcher(psSQL).reset();
        while (matcher.find()) {
            if (matcher.group().startsWith("(")) {
                // カッコスタートの場合はカッコを含めて置換
                psSQL = matcher.replaceFirst("( rowidtochar(" + matcher.group().trim() + ")");
            } else {
                psSQL = matcher.replaceFirst(" rowidtochar(" + matcher.group().trim() + ")");
            }
            matcher = REGEXP_ROWID_ALIAS.matcher(psSQL).reset();
        }
        return psSQL;
    }

    /**
     * SQL文のカラム名4個目の次に
     * 正規区分のカラム"HD_NOFFCIALORNOT"を追加します。
     * @param psSql SQL
     * @return	String 	SQL（HD_NOFFCIALORNOT 追加）
     */
    protected String addVirtualRole(String psSql) {
        String temp;
        if (!psSql.contains("hd.")) {
            temp = "  HD_NOFFCIALORNOT as HD_NOFFCIALORNOT, ";
        } else {
            temp = "  hd.HD_NOFFCIALORNOT as HD_NOFFCIALORNOT, ";
        }
        String sRetvalue = "";
        StringBuilder sQuery = new StringBuilder("");
        int nCnt = 0;
        StringTokenizer spart = new StringTokenizer(psSql, ",");
        while (spart.hasMoreElements()) {
            sQuery.append(spart.nextToken()).append(",");
            nCnt++;
            if (nCnt == 4) {
                sQuery.append(temp);
            }
        }
        sRetvalue = sQuery.toString();
        return sRetvalue.substring(0, sRetvalue.length() - 1);
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
