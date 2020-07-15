package jp.smartcompany.admin.groupmanager.logic.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.admin.component.dto.*;
import jp.smartcompany.admin.groupmanager.dto.GroupManagerGroupListDTO;
import jp.smartcompany.admin.groupmanager.logic.GroupManagerGroupEditLogic;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.component.dto.QueryConditionDTO;
import jp.smartcompany.framework.component.dto.QueryConditionRowDTO;
import jp.smartcompany.framework.component.logic.QueryConditionValidatorLogic;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastGroupdefinitionsDO;
import jp.smartcompany.job.modules.core.service.*;
import jp.smartcompany.job.modules.core.util.Designation;
import jp.smartcompany.job.modules.core.util.PsConst;
import jp.smartcompany.job.modules.core.util.PsSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupManagerGroupEditLogicImpl implements GroupManagerGroupEditLogic {

    private final PsSearchCompanyUtil psSearchCompanyUtil;
    private final IMastGroupbasesectionService iMastGroupbasesectionService;
    private final IMastGroupService iMastGroupService;
    private final IMastGroupsectionpostmappingService iMastGroupsectionpostmappingService;
    private final IMastGroupdefinitionsService iMastGroupdefinitionsService;
    private final IHistGroupdefinitionsService iHistGroupdefinitionsService;
    private final ScCacheUtil scCacheUtil;
    private final QueryConditionValidatorLogic queryConditionValidatorLogic;

    /** 法人選択フラグ(全社区分) */
    private static final String COMPANY_FLG_ALL = "all";
    /** 法人選択フラグ(個別法人) */
    private static final String COMPANY_FLG_ONE = "one";

    /** 処理区分(法人) */
    public static final String FG_COMP             = "01";
    /** 処理区分(法人＆組織指定リスト) */
    public static final String FG_COMP_SEC         = "02";
    /** 処理区分(組織ごとの定義情報取得(法人＆組織＆役職リスト)) */
    public static final String FG_COMP_SEC_POST    = "03";
    /** 処理区分(組織ごとの定義情報取得(法人＆組織＆社員番号リスト)) */
    public static final String FG_COMP_SEC_EMP     = "04";
    /** 処理区分(法人＆組織＆所属長リスト) */
    public static final String FG_COMP_SEC_BOSS    = "05";
    /** 処理区分(法人＆役職リスト) */
    public static final String FG_COMP_POST        = "06";
    /** 処理区分(法人＆社員リスト) */
    public static final String FG_COMP_EMP         = "07";

    /** グループ定義種別フラグ(組織・役職定義) */
    private static final String BASE_FLG_SECPOST = "2";
    /** グループ定義種別フラグ(条件式定義) */
    private static final String BASE_FLG_DEF = "1";
    /* ▼2007/10/12 A.SUZUKI 社員選択による定義を追加 */
    /** グループ定義種別フラグ(社員選択定義) */
    private static final String BASE_FLG_EMPLOYEES = "0";
    /** 初期表示処理読み飛ばしフラグ */
    private static final String FLG_NO_INIT_ACTION = BASE_FLG_EMPLOYEES;
    /** 選択フラグ(特定法人のみの選択を有効) */
    private static final String FLG_CERTAIN_COMMANY = BASE_FLG_DEF;
    /* ▲2007/10/12 A.SUZUKI 社員選択による定義を追加 */

    @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
    public Map<String,Object> detail(Date searchDate, String systemId, String groupId) throws ParseException {
        PsSession psSession = (PsSession) ContextUtil.getHttpRequest().getSession().getAttribute(Constant.PS_SESSION);
        Date maxDate = SysUtil.transStringToDate(PsConst.MAXDATE);
        String companyId;
        String companyName;
        // 法人検索対象範囲情報取得(参照可能な法人のリストを取得)
        List<String> oCompanyValidList = psSearchCompanyUtil.getCompList(searchDate);
        // 画面初期表示情報 取得処理
        GroupManagerGroupListDTO groupDTO = getGroupInfoDispInfo(psSession.getLoginCustomer(),
                systemId, groupId, psSession.getLoginDesignation().get(0),
                searchDate, maxDate, null, psSession.getLanguage(),oCompanyValidList);
        companyId = groupDTO.getMgCcompanyid();
        companyName = groupDTO.getCompanyName();
        // 該当条件(基点組織選択)情報を取得
        // 基点組織編集 - 定義情報取得(法人リスト)
        List<BaseSectionRowDTO> oBaseSectionRowList = iMastGroupbasesectionService.
                selectGroupBaseSectionCompanyList(psSession.getLoginCustomer(),systemId,
                        groupId, psSession.getLanguage(), searchDate, oCompanyValidList);
        String sBelowSingle = dispBaseSectionInfo(psSession.getLoginCustomer(), searchDate, oBaseSectionRowList,systemId,groupId,psSession.getLanguage());
        // 該当条件(組織・役職選択)情報を取得
        SectionPostDTO orgJobConditions = getSectionPostDispInfo(
                psSession.getLoginCustomer(), searchDate, companyId, companyName,psSession.getLanguage(),systemId,groupId
        );
        SelectEmployeesEditDTO empList = getEmpList(psSession.getLoginCustomer(),companyId,systemId,groupId,searchDate,psSession.getLanguage());

        /**
         * 該当条件(条件式選択)情報を取得
         */
        // 条件式編集 - 定義情報取得
        List<QueryConditionRowDTO> oQueryConditionRowList = null;
//                iHistGroupdefinitionsService.selectGroupDefinitions(psSession.getLoginCustomer(), companyId,
//                        systemId, groupId, searchDate, null);
        QueryConditionDTO queryConditionDTO=dispQueryConditionInfo(companyId, searchDate, oQueryConditionRowList,psSession.getLoginCustomer());

        /**
         * 定義設定画面の初期表示状態を取得する
         */
        // グループ定義条件情報を取得
        List<MastGroupdefinitionsDO> oList =
                iMastGroupdefinitionsService.selectGroupDefinitions(
                        searchDate, psSession.getLoginCustomer(),systemId, groupId);
        groupDTO.setGsBaseFlg(getGroupDefinitionsDispInfo(oList,groupDTO));
        // 全社区分を含むか否かを取得する
        groupDTO.setAllCompaniesFlg(psSearchCompanyUtil.isAllCompaniesFlg());


        Map<String,Object> json = MapUtil.<String,Object>builder()
                .put("groupInfo",groupDTO)
                .put("queryConditions",queryConditionDTO)
                .put("belowSingle",sBelowSingle)
                .put("baseSectionList",oBaseSectionRowList)
                .put("companyId",companyId)
                .put("companyName",companyName)
                .put("orgJobConditions",orgJobConditions)
                .put("empList",empList)
                .build();
        return json;
    }

    /**
     * 初期処理<br>
     * 基点組織選択画面にて表示する情報を取得します。
     *
     * @param psCustomerId          顧客コード
     * @param dSearchDate           今回改定日
     * @param poDtoList             基点組織編集 - 定義情報取得(法人リスト)
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public String dispBaseSectionInfo(String psCustomerId, Date dSearchDate,
                                     List<BaseSectionRowDTO> poDtoList,String systemId,String groupId,String language) {
        // 基点組織以下・のみフラグ
        String sBelowSingle = null;
        // 法人情報配下の情報を取得する
        for (BaseSectionRowDTO rowDTO : poDtoList) {
            String sCompanyId = rowDTO.getMgbsCcompanyid();
            // 組織ごとの定義情報取得(法人＆組織＆役職リスト)
            List<BaseSectionRowListDTO> sectionList = iMastGroupbasesectionService.selectGroupBaseSectionList(
                    psCustomerId, sCompanyId, systemId, groupId, language, dSearchDate);
            rowDTO.setGlSectionList(sectionList);
            // 現在の保持している組織リストの件数を保持する
            rowDTO.setGnSelectedSectionCnt(sectionList.size());
            // 1件目の基点組織以下・のみフラグをセット(全件同じフラグなので)
            if (CollUtil.isNotEmpty(sectionList)) {
                sBelowSingle = sectionList.get(0).getMgbsCbeloworsingle();
            }
        }
        // 基点組織以下・のみフラグ nullなら以下とする
        if (sBelowSingle == null) { sBelowSingle = "0"; }
        return sBelowSingle;
    }

    /**
     * 初期処理<br>
     * 該当条件設定画面にて表示する情報を取得します。
     *
     * @param customerId  顧客コード
     * @param systemId    システムコード
     * @param groupId     グループコード
     * @param designation 異動歴情報
     * @param startDate   開始日(今回改定日)
     * @param endDate     終了日
     * @param weightage   優先順位
     * @param language    言語区分
     *
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public GroupManagerGroupListDTO getGroupInfoDispInfo(String customerId, String systemId,
                                     String groupId, Designation designation, Date startDate,
                                     Date endDate, String weightage, String language,List<String> companyList) {
        GroupManagerGroupListDTO groupListDTO = new GroupManagerGroupListDTO();
        if (StrUtil.isBlank(groupId)) {

        } else {
            // 指定グループ情報取得
            List<GroupManagerGroupListDTO> oGroupInfo  = iMastGroupService.selectGroupHistoryList(
                    customerId, systemId, language, groupId, startDate, companyList
            );
            GroupManagerGroupListDTO groupInfo = oGroupInfo.get(0);
            if (CollUtil.isNotEmpty(oGroupInfo)) {
                BeanUtil.copyProperties(groupInfo,groupListDTO);
            }
        }
        return groupListDTO;
    }

    /**
     * 初期処理<br>
     * 組織・役職選択画面にて表示する情報を取得します。
     *
     * @param psCustomerId  顧客コード
     * @param pdSearchDate  今回改定日
     * @param psCompanyId   法人コード
     * @param psCompanyName 法人名称
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public SectionPostDTO getSectionPostDispInfo(String psCustomerId, Date pdSearchDate,
                                                 String psCompanyId, String psCompanyName,String language,String systemId,String groupId) {
        SectionPostDTO sectionPostDTO = new SectionPostDTO();
        /**
         * 該当条件(組織・役職選択)情報を取得
         */
        // 引数を組織・役職コンポーネントへ格納
        sectionPostDTO.setGsApplyDate(SysUtil.transDateToString(pdSearchDate));
        sectionPostDTO.setGsSelectedCompanyId(psCompanyId);
        sectionPostDTO.setGsSelectedCompanyName(psCompanyName);
        // 該当条件編集 - 定義情報取得(法人情報リスト)
        List<SectionPostCompanyRowListDTO> oCompanyList = CollUtil.newArrayList();
        SectionPostCompanyRowListDTO oCompanyDto = new SectionPostCompanyRowListDTO();
        oCompanyDto.setGsCompanyid(psCompanyId);
        oCompanyDto.setGsCompanyName(psCompanyName);
        oCompanyList.add(oCompanyDto);
        sectionPostDTO.setGlCompanyList(oCompanyList);
        List<SectionPostCompanyRowListDTO> companyList = sectionPostDTO.getGlCompanyList();
        // 該当条件編集 - 定義情報取得(法人＆組織指定リスト)
        SectionPostCompanyRowListDTO company = companyList.get(0);
        company.setGlSectionList(
                iMastGroupsectionpostmappingService.selectGroupSection(psCustomerId, psCompanyId,
                        systemId, groupId, pdSearchDate,
                        FG_COMP_SEC, language)
        );
        // 組織情報配下の情報を取得する
        int nSecCnt = company.getGlSectionList().size();
        for (int i = 0; i < nSecCnt; i++) {
            List<SectionPostRowListDTO> glSectionList = company.getGlSectionList();
            String sSectionId = glSectionList.get(i).getSectionId();

            // 組織ごとの定義情報取得(法人＆組織＆役職リスト)
            glSectionList.get(i).setGlPostCompList(
                    iMastGroupsectionpostmappingService.selectWholeSectionInfo(psCustomerId, psCompanyId,
                            systemId, groupId, pdSearchDate, FG_COMP_SEC_POST, sSectionId, language));
            // 組織ごとの定義情報取得(法人＆組織＆社員番号リスト)
            glSectionList.get(i).setGlEmpoyeesCompList(
            iMastGroupsectionpostmappingService.selectWholeSectionInfo(psCustomerId, psCompanyId,
                            systemId, groupId, pdSearchDate,FG_COMP_SEC_EMP, sSectionId, language));
            // 現在の保持している役職リスト(法人＆組織)の件数を保持する
            glSectionList.get(i).setGnSelectedPostCompCnt(glSectionList.get(i).getGlPostCompList().size());
            // 現在の保持している社員番号リスト(法人＆組織)の件数を保持する
            glSectionList.get(i).setGnSelectedEmpoyeesCompCnt(glSectionList.get(i).getGlEmpoyeesCompList().size());
        }
        // 該当条件編集 - 定義情報取得(法人＆組織＆所属長リスト)
        company.setGlBossCompSectionList(
                iMastGroupsectionpostmappingService.selectGroupSectionPost(psCustomerId, psCompanyId,
                        systemId, groupId, pdSearchDate, FG_COMP_SEC_BOSS, language));
        // 該当条件編集 - 定義情報取得(法人＆役職リスト)
        company.setGlPostCompList(
                iMastGroupsectionpostmappingService.selectGroupSectionPost(psCustomerId, psCompanyId,
                        systemId, groupId, pdSearchDate, FG_COMP_POST,
                        language));
        // 更新用のDtoを初期化しておく(法人＆社員リスト)
        company.setGlEmpoyeesCompList(CollUtil.newArrayList());
        // 組織・役職編集情報をJSONにセット
        sectionPostDTO.setGnDispFlg(0);

        return sectionPostDTO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public SelectEmployeesEditDTO getEmpList(String customerId,String companyId,String systemId,String groupId,Date searchDate,String language) {
        SelectEmployeesEditDTO employeesEditDTO = new SelectEmployeesEditDTO();
        // 該当条件編集 - 定義情報取得(法人＆社員リスト)
        List<SectionPostRowDTO> selectingEmployeesDtoList = iMastGroupsectionpostmappingService.selectGroupSectionPost(
                customerId,
                companyId,
                systemId,
                groupId,
                searchDate,
                FG_COMP_EMP,
                language);
        /* 2007/10/22 A.SUZUKI 法人の選択出来ないモードを仕様追加(特定法人のみの選択を有効とする) */
        employeesEditDTO.setGsPsSelectedComp(companyId);
        employeesEditDTO.setGsPsFixedComp(FLG_CERTAIN_COMMANY);
        /* ▲2007/10/12 A.SUZUKI 社員選択による定義を追加 */
        employeesEditDTO.setEmpList(selectingEmployeesDtoList);
        return employeesEditDTO;
    }

    /**
     * 初期処理<br>
     * 初期表示情報(グループ定義条件 設定状況)を取得します。
     *
     * @param plDefinitionList  グループ定義条件情報
     * @return String           設定状況フラグ
     */
    public String getGroupDefinitionsDispInfo(
            List <MastGroupdefinitionsDO> plDefinitionList,GroupManagerGroupListDTO groupDTO) {
        String sResult;
        // 初期表示情報(設定状況)を取得
        MastGroupdefinitionsDO definition = plDefinitionList.get(0);
        if (CollUtil.isNotEmpty(plDefinitionList)) {
            sResult = definition.getMgpBaseflag();
            // IDとVersion番号も取得
            groupDTO.setMgpId(definition.getMgpId());
            groupDTO.setMgpversionNo(definition.getVersionno());
        } else {
            // 定義情報が取得できなかった場合は、初期値(0:社員指定による定義)を設定
            sResult = "0";
            groupDTO.setMgpId(null);
            groupDTO.setMgpversionNo(null);
        }
        return sResult;
    }

    /**
     * 初期処理<br>
     * 条件式選択画面にて表示する情報を取得します。
     *
     * @param psCompanyid   法人コード
     * @param pdSearchDate  今回改定日
     * @param poDtoList     該当条件編集画面(条件式定義(行単位)用Dto
     */
    private QueryConditionDTO dispQueryConditionInfo(String psCompanyid, Date pdSearchDate,
                                        List<QueryConditionRowDTO> poDtoList,String customerId) {
        QueryConditionDTO queryConditionDto = new QueryConditionDTO();

        // 引数を条件式コンポーネントへ格納
        queryConditionDto.setGsApplyDate(SysUtil.transDateToString(pdSearchDate));
        queryConditionDto.setGsSelectedCompanyId(psCompanyid);

        // 該当条件編集 - 定義情報取得(マスタコード＆表示演算子)
        for (QueryConditionRowDTO queryConditionRowDTO : poDtoList) {
            String skey = customerId + "_" + queryConditionRowDTO.getColumnid();
            // データディクショナリより、マスターテーブル区分を取得
            // キー："顧客コード" + _ + "カラム名"
            queryConditionRowDTO.setMastertablename(scCacheUtil.getDataDictionary(skey).getMdCmastertblname());

            queryConditionRowDTO.setDisplayoperator(
                    queryConditionValidatorLogic.getDispOperator(
                            queryConditionRowDTO.getMastertablename(), queryConditionRowDTO.getOperator()));
        }

        // 該当条件編集 - 定義情報取得(条件式リスト)
        queryConditionDto.setRowList(poDtoList);

        // 条件式編集情報をJSONにセット
//        String sJSONData = this.psJSONUtilLogic.encodeJSON(this.queryConditionDto.getRowList());
//        this.queryConditionDto.setJSONData(sJSONData);
        // 更新用のEntityを初期化しておく
//        queryConditionDto.setDeleteDefinitio(new ArrayList<HistGroupDefinitionsEntity>());
        // 表示フラグをセット
        queryConditionDto.setGnValueDisp(0);
        return queryConditionDto;
    }

}
