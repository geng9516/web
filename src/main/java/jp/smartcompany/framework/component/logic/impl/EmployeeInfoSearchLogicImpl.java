package jp.smartcompany.framework.component.logic.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.component.behavior.EmployeeInfoSearchBehavior;
import jp.smartcompany.framework.component.dto.EmployInfoSearchDTO;
import jp.smartcompany.framework.component.entity.EmployeeInfoSearchEntity;
import jp.smartcompany.framework.util.EmployeeInfoSearchOrderLogic;
import jp.smartcompany.framework.util.PsBuildTargetSql;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import jp.smartcompany.job.modules.core.util.PsConst;
import jp.smartcompany.job.modules.core.util.PsSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 社員情報検索コンポーネントのLogicクラス<br>
 * @author Xiao Wenpeng
 *
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmployeeInfoSearchLogicImpl {

    private final ScCacheUtil scCacheUtil;
    private final PsSearchCompanyUtil searchCompanyUtil;
    private final PsBuildTargetSql psBuildTargetSql;
    private final IMastEmployeesService iMastEmployeesService;

    // システムプロパティ：法人略称を使用するか
    private static final String SYSTEM_PROPERTY_USE_COMPANY_ABBREVIATION = "UseCompanyAbbreviation";
    private String sysPropertyCacheComp;

    // システムプロパティ：所属略称を使用するか
    private static final String SYSTEM_PROPERTY_USE_SECTION_ABBREVIATION = "UseSectionAbbreviation";
    private String sysPropertyCacheSec;

    // システムプロパティ：役職略称を使用するか
    private static final String SYSTEM_PROPERTY_USE_POST_ABBREVIATION = "UsePostAbbreviation";
    private String sysPropertyCachePost;

    // システムプロパティ：社員検索結果が１件の時自動選択するか
    private static final String SYSTEM_PROPERTY_EMP_SEARCH_AUTO_SELECT = "EmpSearchAutoSelect";
    private String sysPropertyCacheEmpSearch;

    // システムプロパティ：在職・退職を表示するか(2009/10/26 K.Monden 追加)
    private static final String SYSTEM_PROPERTY_DISP_IF_STILL_EMPLOYEDID = "DispIfStillEmployedId";
    private String sysPropertyCacheDispIfStillEmployedId;

    /** システムプロパティキー */
    public static final String PROPERTY_KEY_SEARCH_ORDER = "EmployeeInfoSearchOrder";

    /** 比較用文字列 */
    private static final String STR_YES = "yes";
    private static final String STR_NO = "no";


    /** タグの属性psSearchFlgの判定値 在職者のみ */
    public static final String TAG_VALUE_ZAI = "zai";

    /** タグの属性psSearchFlgの判定値 退職者のみ */
    public static final String TAG_VALUE_TAI = "tai";

    /** タグの属性psSearchFlgの判定値 在職者、退職者両方 */
    public static final String TAG_VALUE_BOTH = "both";

    private String ignoreRetiredData;


    // リクエスト 検索対象範囲種別(1:法人絞込み検索範囲 それ以外:検索対象範囲)
    public List<EmployeeInfoSearchEntity> searchEmpList(String searchWord,
                              String searchWordConve,
                              String searchWordEnglish,
                              String searchRange,
                              String searchFlg,
                              String companyId,
                              String targetComp,
                              // hidden⇔requestとして保持し続けるオブジェクト (本務兼務区分)
                              String ifKeyorAdditionalRole,
                              String targetDept,
                              Integer type) {

        configSearchEnvs();

        PsSession session = (PsSession) ContextUtil.getHttpRequest().getSession().getAttribute(Constant.PS_SESSION);

        searchWord = escapeSearchWord(searchWord+"%");
        searchWordConve = escapeSearchWord(searchWordConve+"%");
        searchWordEnglish = escapeSearchWord(searchWordEnglish+"%");

        // 退職者非表示がyesの場合
        /** システムプロパティの設定値 退職者非表示 */
        String propValue;
        if (StrUtil.equalsIgnoreCase(ignoreRetiredData,STR_YES)) {
            propValue = STR_YES;
        } else {
            propValue = STR_NO;
        }
        // 検索対象者区分(デフォルトは在職者のみ)
        String sSearchFlg = "0";
        if (TAG_VALUE_TAI.equalsIgnoreCase(searchFlg)) {
            // 退職者のみ
            sSearchFlg = "1";
        } else if (TAG_VALUE_BOTH.equalsIgnoreCase(searchFlg)) {
            // 在職者、退職者両方
            sSearchFlg = null;
        }
        // 法人検索対象範囲情報取得(参照可能な法人のリストを取得)
        List<String> oCompanyValidList = null;
        String sExists = "";
        String sDesignaion = "";	// 検索対象範囲異動歴

        Date searchDate = DateUtil.date();
        String sSearchDate = SysUtil.transDateToString(searchDate);

        // 検索対象範囲種別の判定
        if (StrUtil.equalsIgnoreCase(searchRange,"1")) {
            // 検索対象範囲種別が"1"の場合は、法人の絞込みを行う
            oCompanyValidList = searchCompanyUtil.getCompList(searchDate);
            if (oCompanyValidList.size() == 0) {
                oCompanyValidList = null;
            }
            sDesignaion = "HIST_DESIGNATION";
        } else {
            // 検索対象範囲種別が"1"以外の場合は、検索対象範囲設定を行う
            sExists = psBuildTargetSql.getExistsQuery("ME_CUSERID");
            sDesignaion = psBuildTargetSql.getDesignationQuery();
        }

        // 准备查询参数
        EmployInfoSearchDTO searchDTO = new EmployInfoSearchDTO();
        searchDTO.setPropValue(propValue);
        searchDTO.setExists(sExists);
        searchDTO.setDesignation(sDesignaion);
        searchDTO.setLanguage("ja");
        searchDTO.setSearchDate(sSearchDate);
        searchDTO.setSearchWord(searchWord);
        searchDTO.setSearchWordConve(searchWordConve);
        searchDTO.setSearchWordEnglish(searchWordEnglish);
        searchDTO.setTargetComp(targetComp);
        searchDTO.setTargetDept(targetDept);
        searchDTO.setValidCompanies(oCompanyValidList);
        searchDTO.setSearchFlg(sSearchFlg);
        searchDTO.setLoginUser(session.getLoginUser());
        searchDTO.setCompanyCode(companyId);
        searchDTO.setSystemId(PsConst.DEFAULT_SYSTEM); // TODO 現状はデフォルト固定
        if (StrUtil.containsAny(ifKeyorAdditionalRole,"0","1")) {
            searchDTO.setIfKeyorAdditionalRole(ifKeyorAdditionalRole);
        }
        searchDTO.setOrderBy(
                EmployeeInfoSearchOrderLogic.orderBySelect(
                        scCacheUtil.getSystemProperty(PROPERTY_KEY_SEARCH_ORDER))
        );
        // 发起查询
        List<EmployeeInfoSearchEntity> lEmpInfoUserID =CollUtil.newArrayList();
        // 社員検索
        if (type == 1) {
            lEmpInfoUserID = iMastEmployeesService.selectEmployeeInfoUserIDList(searchDTO);
        // 所属一覧検索(兼務を含む)
        } else {
            lEmpInfoUserID = iMastEmployeesService.selectEmployeeInfoUserIDListAdd(searchDTO);
        }

        // ユーザID一覧生成
        List<String> sEmpInfoUserIDList = CollUtil.newArrayList();
        for (EmployeeInfoSearchEntity employeeInfoSearchEntity : lEmpInfoUserID) {
            sEmpInfoUserIDList.add(employeeInfoSearchEntity.getMeCuserid());
        }

        String sCompNick = "no";
        String sSectionNick = "no";
        String sPostNick = "no";

        if (sysPropertyCacheComp.equalsIgnoreCase(STR_YES)){
            sCompNick = "yes";
        }
        if (sysPropertyCacheSec.equalsIgnoreCase(STR_YES)){
            sSectionNick = "yes";
        }
        if (sysPropertyCachePost.equalsIgnoreCase(EmployeeInfoSearchLogicImpl.STR_YES)){
            sPostNick = "yes";
        }
        // 社員情報を取得
        List<EmployeeInfoSearchEntity> lEmpInfo = CollUtil.newArrayList();
        if (CollUtil.isNotEmpty(sEmpInfoUserIDList)) {

            lEmpInfo = iMastEmployeesService.selectEmployeeInfoList(
                    sSearchDate, session.getLanguage(), sDesignaion,
                    SysUtil.separateList("ME_CUSERID", sEmpInfoUserIDList),
                    sCompNick, sSectionNick, sPostNick,
                    session.getLoginUser(), PsConst.DEFAULT_SYSTEM);

        }
        // 社員情報をMapに格納。キーはユーザID + "_" + 異動歴ID(退職者はnull)
        Map<String, EmployeeInfoSearchEntity> mEmpInfo = MapUtil.newHashMap();
        for (EmployeeInfoSearchEntity ent : lEmpInfo) {
            mEmpInfo.put(ent.getMeCuserid() + "_" + ent.getHdId(), ent);
        }
        // lEmpInfoUserIDに社員情報を当てはめ
        for (int i = 0; i < lEmpInfoUserID.size(); i++) {
            EmployeeInfoSearchEntity ent = lEmpInfoUserID.get(i);
            lEmpInfoUserID.set(i, mEmpInfo.get(ent.getMeCuserid() + "_" + ent.getHdId()));
        }
        // ビヘイビア
        lEmpInfoUserID = applyBehavior(lEmpInfoUserID,session);
        return lEmpInfoUserID;
    }

    /**
     * 項目参照権限適用
     * @param plEntity 社員情報検索Dtoのリスト
     * @return List<EmployeeInfoSearchEntity>
     */
    private List<EmployeeInfoSearchEntity> applyBehavior(List<EmployeeInfoSearchEntity> plEntity,PsSession session) {
        // マスキング文字列取得
        String sMask = scCacheUtil.getPermissionString(session.getLoginCustomer(),
                session.getLoginCompany(),
                session.getLanguage());
        // リストのループ
        for (EmployeeInfoSearchEntity entity : plEntity) {
            // Behaviorクラス取得
            entity.setBehavior(new EmployeeInfoSearchBehavior());
            EmployeeInfoSearchBehavior behavior = entity.getBehavior();
            // マスキング処理
            // 社員番号
            entity.setMeCemployeeidCk(masking(entity.getMeCemployeeidCk(), sMask, behavior.isMeCemployeeidCk()));
            // カナ氏名
            entity.setMeCkananame(masking(entity.getMeCkananame(), sMask, behavior.isMeCkananame()));
            // 社員氏名
            entity.setEmpName(masking(entity.getEmpName(), sMask, behavior.isEmpName()));
            // 法人名称
            entity.setCompName(masking(entity.getCompName(), sMask, behavior.isCompName()));
            // 組織名称
            entity.setSectionName(masking(entity.getSectionName(), sMask, behavior.isSectionName()));
            // 役職名称
            entity.setPostName(masking(entity.getPostName(), sMask, behavior.isPostName()));
            // 在職・退職
            entity.setMeCifStillEmployedName(masking(entity.getMeCifStillEmployedName(), sMask, behavior.isMeCifstillemployedid()));
        }
        return plEntity;
    }

    private void configSearchEnvs() {
        //        ResourceBundle resourceBundle = ResourceBundle.getBundle(
//                "jp/smartcompany/framework/component/EmployeeSearch", Locale.JAPAN);
        // 法人名称を略称にするか判断
        sysPropertyCacheComp = scCacheUtil.getSystemProperty(SYSTEM_PROPERTY_USE_COMPANY_ABBREVIATION);
        // 所属名称を略称にするか判断
        sysPropertyCacheSec = scCacheUtil.getSystemProperty(SYSTEM_PROPERTY_USE_SECTION_ABBREVIATION);
        // 役職名称を略称にするか判断
        sysPropertyCachePost = scCacheUtil.getSystemProperty(SYSTEM_PROPERTY_USE_POST_ABBREVIATION);
        // 社員検索結果が１件の時自動選択するか判断
        sysPropertyCacheEmpSearch = scCacheUtil.getSystemProperty(SYSTEM_PROPERTY_EMP_SEARCH_AUTO_SELECT);
        // 在職・退職を表示するか判断(2009/10/26 K.Monden 追加)
        sysPropertyCacheDispIfStillEmployedId = scCacheUtil.getSystemProperty(SYSTEM_PROPERTY_DISP_IF_STILL_EMPLOYEDID);
        // #2638社員検索ダイアログＲ２対応チケット▼▼▼
        // システムプロパティ
        ignoreRetiredData = scCacheUtil.getSystemProperty("IgnoreRetiredData");
        if (StrUtil.isBlank(ignoreRetiredData)) {
            throw new GlobalException(EmployeeInfoSearchLogicImpl.class.getName()+":IgnoreRetiredData");
        }
    }

    /**
     * マスキング処理
     *
     * @param psBefore 元々の文字列
     * @param psMask マスキング文字列
     * @param isBehavior 参照権限
     * @return String
     */
    private String masking(String psBefore, String psMask, boolean isBehavior) {
        if (isBehavior) {
            // 参照権限ありならそのまま返す
            return psBefore;
        } else {
            // 参照権限なしならマスキング文字列を返す
            return psMask;
        }
    }

    /**
     * 検索文字列のエスケープ操作
     * @param psSearchWord
     * @return
     */
    private String escapeSearchWord(String psSearchWord) {
        // Oracleは全角の_%もメタキャラクタになるらしい
        String[] saEscapeWord = {"_", "%", "＿", "％", "\\"};
        StringBuilder sbSearchWord = new StringBuilder();
        for(int i = 0; i < psSearchWord.length(); i++) {
            String sWord = psSearchWord.substring(i, i + 1);
            int j;
            for(j = 0; j < saEscapeWord.length; j++) {
                // メタキャラクタのエスケープ
                if(sWord.equals(saEscapeWord[j])) {
                    sbSearchWord.append("\\").append(sWord);
                    break;
                }
            }
            // 一致しないときはそのまま
            if(j == saEscapeWord.length) {
                sbSearchWord.append(sWord);
            }
        }
        return sbSearchWord.toString();
    }

}
