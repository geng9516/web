package jp.smartcompany.admin.groupmanager.logic.impl;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.DbUtil;
import jp.smartcompany.admin.component.dto.*;
import jp.smartcompany.admin.component.logic.BaseSectionLogic;
import jp.smartcompany.admin.component.logic.QueryConditionLogic;
import jp.smartcompany.admin.groupmanager.dto.GroupManagerEditDTO;
import jp.smartcompany.admin.groupmanager.dto.GroupManagerGroupListDTO;
import jp.smartcompany.admin.groupmanager.logic.GroupManagerGroupEditLogic;
import jp.smartcompany.admin.groupmanager.logic.SectionPostLogic;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.configuration.security.dto.SmartUserDetails;
import jp.smartcompany.boot.util.*;
import jp.smartcompany.framework.component.dto.QueryConditionDTO;
import jp.smartcompany.framework.component.dto.QueryConditionRowDTO;
import jp.smartcompany.framework.component.dto.QueryConditionSelectDTO;
import jp.smartcompany.framework.component.logic.QueryConditionValidatorLogic;
import jp.smartcompany.framework.dbaccess.DbControllerLogic;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
import jp.smartcompany.job.modules.core.enums.MailType;
import jp.smartcompany.job.modules.core.pojo.bo.SendMailBO;
import jp.smartcompany.job.modules.core.pojo.entity.*;
import jp.smartcompany.job.modules.core.service.*;
import jp.smartcompany.job.modules.core.util.Designation;
import jp.smartcompany.job.modules.core.util.PsConst;
import jp.smartcompany.job.modules.core.util.PsSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupManagerGroupEditLogicImpl implements GroupManagerGroupEditLogic {

    private final PsSearchCompanyUtil psSearchCompanyUtil;
    private final IMastGroupbasesectionService iMastGroupbasesectionService;
    private final IMastGroupService iMastGroupService;
    private final IMastGroupsectionpostmappingService iMastGroupsectionpostmappingService;
    private final IMastGroupdefinitionsService iMastGroupdefinitionsService;
    private final IHistGroupdefinitionsService iHistGroupdefinitionsService;
    private final IMastDatadictionaryService mastDatadictionaryService;
    private final IHistDesignationService histDesignationService;
    private final IMastEmployeesService employeesService;
    private final IMastMailInfoService mailService;

    private final ScCacheUtil scCacheUtil;
    private final DataSource dataSource;
    private final DbControllerLogic dbControllerLogic;
    private final TimedCache<String,Object> timedCache;

    private final QueryConditionValidatorLogic queryConditionValidatorLogic;
    private final QueryConditionLogic queryConditionLogic;
    private final BaseSectionLogic baseSectionLogic;
    private final SectionPostLogic sectionPostLogic;

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
    /** 処理区分(組織ごとの定義情報取得(法人＆組織＆職員番号リスト))(已弃用) */
    public static final String FG_COMP_SEC_EMP     = "04";
    /** 処理区分(法人＆組織＆所属長リスト) */
    public static final String FG_COMP_SEC_BOSS    = "05";
    /** 処理区分(法人＆役職リスト) */
    public static final String FG_COMP_POST        = "06";
    /** 処理区分(法人＆職員リスト) */
    public static final String FG_COMP_EMP         = "07";

    /** グループ定義種別フラグ(組織・役職定義) */
    private static final String BASE_FLG_SECPOST = "2";
    /** グループ定義種別フラグ(条件式定義) */
    private static final String BASE_FLG_DEF = "1";
    /* ▼2007/10/12 A.SUZUKI 職員選択による定義を追加 */
    /** グループ定義種別フラグ(職員選択定義) */
    private static final String BASE_FLG_EMPLOYEES = "0";
    /** 初期表示処理読み飛ばしフラグ */
    private static final String FLG_NO_INIT_ACTION = BASE_FLG_EMPLOYEES;
    /** 選択フラグ(特定法人のみの選択を有効) */
    private static final String FLG_CERTAIN_COMMANY = BASE_FLG_DEF;
    /* ▲2007/10/12 A.SUZUKI 職員選択による定義を追加 */


    @Override
    public Map<String,Object> detail(Date searchDate, String systemId, String groupId) {
        PsSession psSession = (PsSession) ContextUtil.getSession().getAttribute(Constant.PS_SESSION);
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

        QueryConditionDTO queryConditionDTO = null;
        SelectEmployeesEditDTO empList = null;
        SectionPostDTO orgJobConditions =null;
        List<BaseSectionRowDTO> oBaseSectionRowList=null;
        String sBelowSingle = "0";
        if (StrUtil.isNotBlank(groupId)) {
            // 該当条件(基点組織選択)情報を取得
            // 基点組織編集 - 定義情報取得(法人リスト)
            oBaseSectionRowList = iMastGroupbasesectionService.
                    selectGroupBaseSectionCompanyList(psSession.getLoginCustomer(), systemId,
                            groupId, psSession.getLanguage(), searchDate, oCompanyValidList);
            if (CollUtil.isNotEmpty(oBaseSectionRowList) && StrUtil.isBlank(companyName)) {
                companyName = oBaseSectionRowList.get(0).getCompanyName();
            }
            sBelowSingle = dispBaseSectionInfo(psSession.getLoginCustomer(), searchDate, oBaseSectionRowList, systemId, groupId, psSession.getLanguage());

            // 該当条件(組織・役職選択)情報を取得
            orgJobConditions = getSectionPostDispInfo(
                    psSession.getLoginCustomer(), searchDate, companyId, companyName, psSession.getLanguage(), systemId, groupId
            );
            empList = getEmpList(psSession.getLoginCustomer(), companyId, systemId, groupId, searchDate, psSession.getLanguage());

            /**
             * 該当条件(条件式選択)情報を取得
             */
            // 条件式編集 - 定義情報取得
            List<QueryConditionRowDTO> oQueryConditionRowList = iHistGroupdefinitionsService.selectGroupDefinitions(psSession.getLoginCustomer(), companyId,
                    systemId, groupId, searchDate, null);
            queryConditionDTO = dispQueryConditionInfo(companyId, searchDate, oQueryConditionRowList, psSession.getLoginCustomer());

            /**
             * 定義設定画面の初期表示状態を取得する
             */
            // グループ定義条件情報を取得
            List<MastGroupdefinitionsDO> oList = iMastGroupdefinitionsService.selectGroupDefinitions(
                    searchDate, psSession.getLoginCustomer(), systemId, groupId);
            getGroupDefinitionsDispInfo(oList,groupDTO);
        }
        // 全社区分を含むか否かを取得する
        groupDTO.setAllCompaniesFlg(psSearchCompanyUtil.isAllCompaniesFlg());

        return MapUtil.<String,Object>builder()
                .put("groupInfo",groupDTO)
                .put("queryConditions",queryConditionDTO)
                .put("belowSingle",sBelowSingle)
                .put("baseSectionList",oBaseSectionRowList)
                .put("companyId",companyId)
                .put("companyName",companyName)
                .put("orgJobConditions",orgJobConditions)
                .put("empList",empList)
                .build();
    }

    /**
     * 初期処理<br>
     * 基点組織選択画面にて表示する情報を取得します。
     *
     * @param psCustomerId          顧客コード
     * @param dSearchDate           今回改定日
     * @param poDtoList             基点組織編集 - 定義情報取得(法人リスト)
     */
    public String dispBaseSectionInfo(String psCustomerId, Date dSearchDate,
                                     List<BaseSectionRowDTO> poDtoList,String systemId,String groupId,String language) {
        // 基点組織以下・のみフラグ
        String sBelowSingle = null;
        // 法人情報配下の情報を取得する
        for (BaseSectionRowDTO rowDTO : poDtoList) {
            String sCompanyId = rowDTO.getMgbsCcompanyid();
            // 組織ごとの定義情報取得(法人＆組織＆役職リスト)
            List<BaseSectionRowListDTO> sectionList = iMastGroupbasesectionService.selectGroupBaseSectionList(
                    psCustomerId, sCompanyId, systemId, language,groupId, dSearchDate);
            rowDTO.setSectionList(sectionList);
            // 現在の保持している組織リストの件数を保持する
//            rowDTO.setGnSelectedSectionCnt(sectionList.size());
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
    public GroupManagerGroupListDTO getGroupInfoDispInfo(String customerId, String systemId,
                                     String groupId, Designation designation, Date startDate,
                                     Date endDate, String weightage, String language,List<String> companyList) {
        GroupManagerGroupListDTO groupListDTO = new GroupManagerGroupListDTO();
        if (StrUtil.isBlank(groupId)) {
            // 優先順位(初期値は固定で"999")
           Long bWeightage = 999L;
            // 優先順位(現在の最大優先順位)が指定されていた場合
            if (weightage != null) {
                bWeightage = Long.parseLong(weightage);
            }

            // 異動歴より、法人情報を取得する
            String sCompanyId   = designation.getCompanyCode();
            String sCompanyName = designation.getCompanyName();

            // 表示用Dtoに初期表示値を格納
            groupListDTO.setMgId(null);
            groupListDTO.setMgDstartdate(startDate);
            groupListDTO.setMgDenddate(endDate);
            groupListDTO.setMgCgroupidPk("");
            groupListDTO.setMgCgroupdescription("");
            groupListDTO.setMgCgroupdescriptionja("");
            groupListDTO.setMgCgroupdescriptionen("");
            groupListDTO.setMgCgroupdescriptionch("");
            groupListDTO.setMgCgroupdescription01("");
            groupListDTO.setMgCgroupdescription02("");
            groupListDTO.setMgClanguage(language);
            groupListDTO.setMgNpartinentnumber(null);
            groupListDTO.setMgNweightage(bWeightage);
            groupListDTO.setMgCtext("");
            groupListDTO.setMgCcompanyid(sCompanyId);
            groupListDTO.setCompanyName(sCompanyName);


            // 法人情報を格納
//            this.setCompanyId(sCompanyId);
//            this.setCompanyName(sCompanyName);

            // 法人選択フラグを格納(個別法人選択)
//            groupListDTO.setCompanySelectedFlg(COMPANY_FLG_ONE);
        } else {
            // 指定グループ情報取得
            List<GroupManagerGroupListDTO> oGroupInfo  = iMastGroupService.selectGroupHistoryList(
                    customerId, systemId, language, groupId, startDate, companyList
            );
            if (CollUtil.isNotEmpty(oGroupInfo)) {
                GroupManagerGroupListDTO groupInfo = oGroupInfo.get(0);
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
    public SectionPostDTO getSectionPostDispInfo(String psCustomerId, Date pdSearchDate,
                                                 String psCompanyId, String psCompanyName,String language,String systemId,String groupId) {
        SectionPostDTO sectionPostDTO = new SectionPostDTO();
        /**
         * 該当条件(組織・役職選択)情報を取得
         */
        // 引数を組織・役職コンポーネントへ格納
        sectionPostDTO.setApplyDate(SysUtil.transDateToString(pdSearchDate));
        sectionPostDTO.setSelectedCompanyId(psCompanyId);
        sectionPostDTO.setSelectedCompanyName(psCompanyName);
        // 該当条件編集 - 定義情報取得(法人情報リスト)
        List<SectionPostListDTO> oCompanyList = CollUtil.newArrayList();
        SectionPostListDTO oCompanyDto = new SectionPostListDTO();
        oCompanyDto.setCompanyId(psCompanyId);
        oCompanyDto.setCompanyName(psCompanyName);
        oCompanyList.add(oCompanyDto);
        sectionPostDTO.setCompanyList(oCompanyList);
        List<SectionPostListDTO> companyList = sectionPostDTO.getCompanyList();
        // 該当条件編集 - 定義情報取得(法人＆組織指定リスト)
        SectionPostListDTO company = companyList.get(0);
        company.setSectionList(iMastGroupsectionpostmappingService.selectGroupSection(psCustomerId, psCompanyId, systemId, groupId, pdSearchDate, FG_COMP_SEC, language));
        // 組織情報配下の情報を取得する
        int nSecCnt = company.getSectionList().size();
        for (int i = 0; i < nSecCnt; i++) {
            List<SectionPostRowListDTO> glSectionList = company.getSectionList();
            String sSectionId = glSectionList.get(i).getSectionId();

            // 組織ごとの定義情報取得(法人＆組織＆役職リスト)
            glSectionList.get(i).setPostList(
                    iMastGroupsectionpostmappingService.selectWholeSectionInfo(psCustomerId, psCompanyId,
                            systemId, groupId, pdSearchDate, FG_COMP_SEC_POST, sSectionId, language));
            // 組織ごとの定義情報取得(法人＆組織＆職員番号リスト)
            glSectionList.get(i).setEmployList(
            iMastGroupsectionpostmappingService.selectWholeSectionInfo(psCustomerId, psCompanyId,
                            systemId, groupId, pdSearchDate,FG_COMP_SEC_EMP, sSectionId, language));
            // 現在の保持している役職リスト(法人＆組織)の件数を保持する
            glSectionList.get(i).setSelectedPostCnt(glSectionList.get(i).getPostList().size());
            // 現在の保持している職員番号リスト(法人＆組織)の件数を保持する
            glSectionList.get(i).setSelectedEmpoyeesCompCnt(glSectionList.get(i).getEmployList().size());
        }
        // 該当条件編集 - 定義情報取得(法人＆組織＆所属長リスト)
        company.setBossSectionList(
                iMastGroupsectionpostmappingService.selectGroupSectionPost(psCustomerId, psCompanyId,
                        systemId, groupId, pdSearchDate, FG_COMP_SEC_BOSS, language));
        // 該当条件編集 - 定義情報取得(法人＆役職リスト)
        company.setPostList(
                iMastGroupsectionpostmappingService.selectGroupSectionPost(psCustomerId, psCompanyId,
                        systemId, groupId, pdSearchDate, FG_COMP_POST,
                        language));
        // 更新用のDtoを初期化しておく(法人＆職員リスト)
        company.setEmployList(CollUtil.newArrayList());
        // 組織・役職編集情報をJSONにセット
        sectionPostDTO.setDispFlg(0);

        return sectionPostDTO;
    }

    public SelectEmployeesEditDTO getEmpList(String customerId,String companyId,String systemId,String groupId,Date searchDate,String language) {
        SelectEmployeesEditDTO employeesEditDTO = new SelectEmployeesEditDTO();
        // 該当条件編集 - 定義情報取得(法人＆職員リスト)
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
        /* ▲2007/10/12 A.SUZUKI 職員選択による定義を追加 */
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
    public void getGroupDefinitionsDispInfo(
            List <MastGroupdefinitionsDO> plDefinitionList,GroupManagerGroupListDTO groupDTO) {
        String sResult;
        // 初期表示情報(設定状況)を取得
        MastGroupdefinitionsDO definition = plDefinitionList.get(0);
        if (CollUtil.isNotEmpty(plDefinitionList)) {
            sResult = definition.getMgpBaseflag();
            // IDとVersion番号も取得
            groupDTO.setMgpId(definition.getMgpId());
        } else {
            // 定義情報が取得できなかった場合は、初期値(0:職員指定による定義)を設定
            sResult = "0";
            groupDTO.setMgpId(null);
        }
        groupDTO.setGsBaseFlg(sResult);
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

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public void update(GroupManagerEditDTO dto) {
        SmartUserDetails userInfo = SecurityUtil.getLoginUser();

        String customerId = "01";
        String companyId = "01";
        String systemId = "01";
        String groupId = dto.getGroupId();
        DateTime psStartDate = DateUtil.date(dto.getStartDate());
        DateTime psEndDate = DateUtil.date(dto.getEndDate());
        String baseFlag = dto.getBaseFlag();
        String belowSingle = dto.getBelowSingle();
        Date startDate = SysDateUtil.of(psStartDate.year(),psStartDate.month()+1,psStartDate.dayOfMonth());
        Date endDate = SysDateUtil.of(psEndDate.year(),psEndDate.month()+1,psEndDate.dayOfMonth());


        SectionPostDTO sectionPostDTO = new SectionPostDTO();
        QueryConditionDTO queryConditionDTO = new QueryConditionDTO();
        // 是否是新增group动作
        boolean isAdd = dto.getMgId()==null;
        // 組織・役職による定義の情報取得
        if (StrUtil.equals(baseFlag,BASE_FLG_SECPOST)) {
            assembleGroupSectionPost(sectionPostDTO,dto.getSectionPostList());
        }
        // 条件式による定義の情報取得
        if (StrUtil.equals(baseFlag,BASE_FLG_DEF)) {
            assembleGroupDefinitions(queryConditionDTO,dto.getQueryConditionList());
        }
        // 職員による定義の情報取得
        if (StrUtil.equals(baseFlag,BASE_FLG_EMPLOYEES)) {
            assembleEmploy(sectionPostDTO,dto.getEmployList());
        }

        // 基点組織による定義の情報取得
        BaseSectionDTO baseSectionDTO = new BaseSectionDTO();
        assembleBaseSectionList(baseSectionDTO,dto.getBaseSectionList(),startDate,endDate,groupId,belowSingle);

        // 条件式妥当性チェック(条件式設定のみ)
        boolean checkFlag = isQueryCondition(baseFlag,queryConditionDTO.getRowList());
        int checkResult=0;
        // グループID重複チェック(新規登録のみ)
        if (isAdd  && iMastGroupService.selectGroupExists(customerId,systemId,groupId) > 0 && checkFlag) {
            checkFlag = false;
            checkResult = 1;
        }
        List<SectionPostListDTO> companyList = sectionPostDTO.getCompanyList();
        List<SectionPostListDTO> sectionPostList = CollUtil.newArrayList();
        if (CollUtil.isNotEmpty(companyList)) {
            companyList.forEach(companyData -> {
               SectionPostListDTO sectionPostListDTO = new SectionPostListDTO();
               sectionPostListDTO.setEmployList(companyData.getEmployList());
               sectionPostListDTO.setPostList(companyData.getPostList());
               sectionPostListDTO.setBossSectionList(companyData.getBossSectionList());
               sectionPostListDTO.setSectionList(companyData.getSectionList());
               sectionPostListDTO.setCompanyId("01");
               sectionPostListDTO.setDelete(false);
               sectionPostList.add(sectionPostListDTO);
            });
        }
        String groupCheckQuery = getGroupCheckQuery(baseFlag,customerId, systemId, groupId, startDate, endDate,sectionPostList,companyId,dto.getQueryConditionList());

        // グループ判定クエリ妥当性チェック
        if (checkFlag) {
            if (isCheckValidQuery(groupCheckQuery) < 0) {
                // エラーの場合は、登録／更新処理を行わない
                checkResult = 2;
                checkFlag = false;
            }
        }
        // チェック判定
        if (!checkFlag) {
            throw new GlobalException(400,"変更できません、エーラ:"+checkResult);
        }

        // グループ定義情報を更新
        // 対象テーブル(MAST_GROUP)
        updateGroup(insertGroup(
                customerId, systemId, startDate, endDate, dto));
        // 組織・役職選択・職員選択情報を更新
        // 対象テーブル(MAST_GROUPSECTIONPOSTMAPPING)
        deleteGroupSectionPost(sectionPostDTO.getDeleteSectionPost());
        // 新規追加対象データをデータベースへ反映
        if (StrUtil.equals(baseFlag,BASE_FLG_SECPOST) || StrUtil.equals(baseFlag,BASE_FLG_EMPLOYEES)) {
            insertSectionPost(customerId, systemId, groupId, startDate, endDate,sectionPostDTO.getCompanyList(),isAdd);
        }
        // 条件式選択情報を更新
        if (StrUtil.equals(baseFlag,BASE_FLG_DEF)) {
            insertHistGroupDefinitions(customerId,systemId,groupId, startDate, endDate, companyId,dto.getQueryConditionList());
        }
        // 両方定義されていたら、片方を削除する
        deleteAllSectionPostDefinitions(customerId, companyId, systemId, groupId, startDate, baseFlag);
        // 基点組織選択情報を更新
        // 対象テーブル(MAST_GROUPBASESECTION)
        deleteBaseSection(baseSectionDTO.getDeleteBaseSection());
        // 新規追加対象データをデータベースへ反映
        insertBaseSection(baseSectionDTO.getRowList());
        // グループ定義条件情報を更新
        // 対象テーブル(MAST_GROUPDEFINITIONS)
        updateMastGroupDefinitions(
                insertGroupDefinitions(customerId, systemId, groupId, startDate, endDate, baseFlag),
                groupCheckQuery,dto);
        HttpSession session = ContextUtil.getSession();
        session.removeAttribute(Constant.IS_APPROVER);
        timedCache.remove(Constant.getSessionMenuId(session.getId()));
        session.removeAttribute(Constant.PS_SESSION);

        SendMailBO sendMailBO = new SendMailBO();
        sendMailBO.setEmpId(userInfo.getHdCemployeeidCk());
        Optional<MastEmployeesDO> optEmploy = employeesService.getEmployInfo(userInfo.getHdCuserid());
        optEmploy.ifPresent(employ -> {
            if (StrUtil.isNotBlank(employ.getMeCmail())) {
                sendMailBO.setToAddress(employ.getMeCmail());
                sendMailBO.setEmpName(employ.getMeCkanjiname());
                mailService.sendMail(MailType.GROUP_CHECK, sendMailBO);
            }
        });
    }


    public void assembleBaseSectionList(BaseSectionDTO baseSectionDTO,List<BaseSectionRowDTO> baseSectionRowList,
                                         Date startDate,Date endDate,String groupId,String belowSingle) {
          if (CollUtil.isEmpty(baseSectionRowList)) {
              return;
          }
          List<BaseSectionRowDTO> addSectionList = CollUtil.newArrayList();
          List<MastGroupbasesectionDO> deleteSectionList = CollUtil.newArrayList();

          for (BaseSectionRowDTO baseCompanyRowDTO : baseSectionRowList) {
             List<BaseSectionRowListDTO> orgList = CollUtil.newArrayList();
             List<BaseSectionRowListDTO> sectionList = baseCompanyRowDTO.getSectionList();

             if (CollUtil.isNotEmpty(sectionList)) {
                 for (BaseSectionRowListDTO baseSectionRowListDTO : sectionList) {
                   if (baseSectionRowListDTO.getDelete()) {
                       MastGroupbasesectionDO eh = new MastGroupbasesectionDO();
                       eh.setMgbsId(baseSectionRowListDTO.getMgbsId());
                       deleteSectionList.add(eh);
                   } else {
                       baseSectionRowListDTO.setMgbsCcustomerid("01");
                       baseSectionRowListDTO.setMgbsCsystemid("01");
                       baseSectionRowListDTO.setMgbsCgroupid(groupId);
                       baseSectionRowListDTO.setMgbsDstartdate(startDate);
                       baseSectionRowListDTO.setMgbsDenddate(endDate);
                       baseSectionRowListDTO.setMgbsCbeloworsingle(belowSingle);
                       orgList.add(baseSectionRowListDTO);
                   }
                 }
                 baseCompanyRowDTO.setSectionList(orgList);
             }
             // 削除フラグのあるデータは、削除用リストへ
              if (baseCompanyRowDTO.getDelete()) {
                  if (baseCompanyRowDTO.getMgbsId() != null) {
                      MastGroupbasesectionDO eh = new MastGroupbasesectionDO();
                      eh.setMgbsId(baseCompanyRowDTO.getMgbsId());
                      deleteSectionList.add(eh);
                  }
              } else {
                  // 値をセット
                  baseCompanyRowDTO.setMgbsCcustomerid("01");
                  baseCompanyRowDTO.setMgbsCsystemid("01");
                  baseCompanyRowDTO.setMgbsCgroupid(groupId);
                  baseCompanyRowDTO.setMgbsDstartdate(startDate);
                  baseCompanyRowDTO.setMgbsDenddate(endDate);
                  baseCompanyRowDTO.setMgbsCbeloworsingle(belowSingle);
                  addSectionList.add(baseCompanyRowDTO);
              }
          }
          baseSectionDTO.setRowList(addSectionList);
          baseSectionDTO.setDeleteBaseSection(deleteSectionList);
    }

    public void assembleEmploy(SectionPostDTO sectionPostDTO,List<SectionPostRowDTO> rowList) {
        List<MastGroupsectionpostmappingDO> lDelTemp = CollUtil.newArrayList();
        List<SectionPostRowDTO> addEmpList = CollUtil.newArrayList();
        if (CollUtil.isNotEmpty(rowList)) {
            List<SectionPostListDTO> companyList = sectionPostDTO.getCompanyList();
            if (CollUtil.isEmpty(companyList)){
                companyList = CollUtil.newArrayList();
                SectionPostListDTO sectionPostListDTO = new SectionPostListDTO();
                sectionPostListDTO.setDelete(false);
                sectionPostListDTO.setCompanyId("01");
                companyList.add(sectionPostListDTO);
                sectionPostDTO.setCompanyList(companyList);
            }
            for (SectionPostRowDTO employDTO : rowList) {
                // 如果是要删除的项
                if (employDTO.getDelete()) {
                    MastGroupsectionpostmappingDO egp = new MastGroupsectionpostmappingDO();
                    egp.setMagId(employDTO.getId());
                    lDelTemp.add(egp);
                } else {
                    addEmpList.add(employDTO);
                }
            }
            sectionPostDTO.setDeleteEmployList(lDelTemp);
            companyList.get(0).setEmployList(addEmpList);
        }
    }

    private void assembleGroupSectionPost(SectionPostDTO sectionPostDTO,SectionPostListDTO sectionPostListDTO) {
          // 組織リスト
          List<SectionPostRowListDTO> sectionList = sectionPostListDTO.getSectionList();
          // 所属長リスト
          List<SectionPostRowDTO> bossSectionList = sectionPostListDTO.getBossSectionList();
          // 役職リスト
          List<SectionPostRowDTO> postList = sectionPostListDTO.getPostList();

          List<MastGroupsectionpostmappingDO> lDelTemp = CollUtil.newArrayList();
          List<SectionPostListDTO> lAddTemp = CollUtil.newArrayList();

          List<SectionPostRowListDTO> lAddTempOrg = CollUtil.newArrayList();
          // 組織リスト
          if (CollUtil.isNotEmpty(sectionList)) {
              for (SectionPostRowListDTO sectionPostRowDTO : sectionList) {
                  // 組織配下の役職
                  List<SectionPostRowDTO> addTempOrgPost = CollUtil.newArrayList();
                  // 获取新增的役职列表
                  List<SectionPostRowDTO> belowSectionPostList = sectionPostRowDTO.getPostList();
                  if (CollUtil.isNotEmpty(belowSectionPostList)) {
                      for (SectionPostRowDTO postRowDTO : belowSectionPostList) {
                          // 如果是要删除的项
                          if (postRowDTO.getDelete()) {
                              MastGroupsectionpostmappingDO egp = new MastGroupsectionpostmappingDO();
                              egp.setMagId(postRowDTO.getId());
                              lDelTemp.add(egp);
                          } else {
                              addTempOrgPost.add(postRowDTO);
                          }
                      }
                      sectionPostListDTO.setEmployList(CollUtil.newArrayList());
                      sectionPostListDTO.setPostList(addTempOrgPost);
                  }
                  // 削除フラグのあるデータは、削除用リストへ
                  if (sectionPostRowDTO.getDelete()) {
                          MastGroupsectionpostmappingDO egp = new MastGroupsectionpostmappingDO();
                          egp.setMagId(sectionPostRowDTO.getId());
                          lDelTemp.add(egp);
                  } else {
                      // 値をセット
                      lAddTempOrg.add(sectionPostRowDTO);
                  }
              }
              sectionPostListDTO.setSectionList(lAddTempOrg);
          }

          // 所属長
          List<SectionPostRowDTO> lAddTempList = CollUtil.newArrayList();

          if (CollUtil.isNotEmpty(bossSectionList)) {
              for (SectionPostRowDTO sectionPostRowDTO : bossSectionList) {
                  if (sectionPostRowDTO.getDelete()) {
                      MastGroupsectionpostmappingDO egp = new MastGroupsectionpostmappingDO();
                      egp.setMagId(sectionPostRowDTO.getId());
                      lDelTemp.add(egp);
                  } else {
                      // 値をセット
                      lAddTempList.add(sectionPostRowDTO);
                  }
              }
              sectionPostListDTO.setSectionList(lAddTempOrg);
          }

          // 役職
          lAddTempList = CollUtil.newArrayList();
          if (CollUtil.isNotEmpty(postList)) {
              for (SectionPostRowDTO sectionPostRowDTO : postList) {
                if (sectionPostRowDTO.getDelete()) {
                    MastGroupsectionpostmappingDO egp = new MastGroupsectionpostmappingDO();
                    egp.setMagId(sectionPostRowDTO.getId());
                    lDelTemp.add(egp);
                } else {
                    lAddTempList.add(sectionPostRowDTO);
                }
              }
              sectionPostListDTO.setPostList(lAddTempList);
          }

          // 削除フラグのあるデータは、削除用リストへ
          if (sectionPostListDTO.getDelete()) {
                MastGroupsectionpostmappingDO eh = new MastGroupsectionpostmappingDO();
                eh.setMagId(sectionPostListDTO.getId());
                lDelTemp.add(eh);
          } else {
            // 値をセット
            lAddTemp.add(sectionPostListDTO);
          }

          sectionPostDTO.setCompanyList(lAddTemp);
          sectionPostDTO.setDeleteSectionPost(lDelTemp);
    }

    private void assembleGroupDefinitions(QueryConditionDTO queryConditionDTO,List<QueryConditionRowDTO> qwRows) {
        List<QueryConditionRowDTO> lAddTemp = CollUtil.newArrayList();
        List<HistGroupdefinitionsDO> lDelTemp = CollUtil.newArrayList();
        for (QueryConditionRowDTO qwRow : qwRows) {
            if (StrUtil.isNotBlank(qwRow.getTableid()) && StrUtil.isNotBlank(qwRow.getColumnid())) {
                if (qwRow.getDelete()) {
                    HistGroupdefinitionsDO eh = new HistGroupdefinitionsDO();
                    eh.setHgdId(qwRow.getId());
                    lDelTemp.add(eh);
                } else {
                    lAddTemp.add(qwRow);
                }
            }
        }
        queryConditionDTO.setRowList(lAddTemp);
        queryConditionDTO.setDeleteDefinitions(lDelTemp);
    }

    @Override
    public List<QueryConditionSelectDTO> queryConditionList(String tableId) {
        List<QueryConditionSelectDTO> fieldList;
        if (StrUtil.isBlank(tableId)) {
            fieldList = mastDatadictionaryService.selectTableInfo("01", "ja", null);
        } else {
            fieldList = mastDatadictionaryService.selectColumnInfo("01", "ja", tableId, null);
        }
        return fieldList;
    }

    @Override
    public List<SectionPostRowListDTO> getBossCompSectionList(List<String> sectionList) {
        return histDesignationService.selectBossComSectionList(sectionList);
    }

    /**
     * DB削除処理(組織・役職選択・職員選択 または 条件式選択 全削除)<br>
     * 定義情報をDBへ反映させる(物理更新)
     * 指定されたフラグとグループIDの情報を全削除します。
     *
     * @param psCustomerId  顧客コード
     * @param psSystem      システムコード
     * @param psCompany     法人コード
     * @param psGroup       グループID
     * @param ptStartDate   開始日(今回改定日)
     * @param baseFlag     グループ定義種別フラグ
     */
    public void deleteAllSectionPostDefinitions(String psCustomerId, String psCompany,
                                                 String psSystem, String psGroup, Date ptStartDate, String baseFlag) {

        if (StrUtil.equals(baseFlag,BASE_FLG_DEF)) {
            // 組織・役職選択 全削除対象セット
            iMastGroupsectionpostmappingService.deleteGroupSectionPostAll(
                    psCustomerId, psCompany, psSystem, psGroup, ptStartDate);
        } else if (StrUtil.equals(baseFlag,BASE_FLG_SECPOST)
                || StrUtil.equals(baseFlag,BASE_FLG_EMPLOYEES)) {
            // 条件式選択 全削除
//            queryConditionLogic.setDeleteAllDefinitions(psCompany);
//            deleteGroupDefinitions();
            if (StrUtil.equals(baseFlag,BASE_FLG_EMPLOYEES)) {
                // 組織・役職選択の定義区分削除
                List<String> typeIdList = CollUtil.newArrayList();
                typeIdList.add(FG_COMP_SEC);
                typeIdList.add(FG_COMP_SEC_POST);
                typeIdList.add(FG_COMP_SEC_EMP);
                typeIdList.add(FG_COMP_SEC_BOSS);
                typeIdList.add(FG_COMP_POST);
                // 組織・役職選択 対象セット(職員番号以外）
                iMastGroupsectionpostmappingService.deleteSectionPostTypeList(
                        psCustomerId, psSystem, psGroup, typeIdList, ptStartDate);
            }
        }

    }

    /**
     * DB削除処理(基点組織選択)<br>
     * 定義情報をDBへ反映させる(物理更新)
     *
     */
    public void deleteBaseSection(List<MastGroupbasesectionDO> baseSectionList) {
        // データベースへ更新(削除)
        List<Long> ids = CollUtil.newArrayList();
        if (CollUtil.isNotEmpty(baseSectionList)) {
            for (MastGroupbasesectionDO mastGroupbasesectionDO : baseSectionList) {
                // IDの有るもの(DBに存在するデータ)のみ、更新する
                if (mastGroupbasesectionDO.getMgbsId() != null) {
                    ids.add(mastGroupbasesectionDO.getMgbsId());
                }
            }
            iMastGroupbasesectionService.removeByIds(ids);
        }
    }

    /**
     * DB削除処理(条件式選択)<br>
     * 定義情報をDBへ反映させる(物理更新)
     *
     */
    public void deleteGroupDefinitions(List<HistGroupdefinitionsDO> deleteDefinitions) {
        // 削除件数
        List<Long> ids = CollUtil.newArrayList();
        // データベースへ更新(削除)
        if (CollUtil.isNotEmpty(deleteDefinitions)) {
            for (HistGroupdefinitionsDO deleteDefinition : deleteDefinitions) {
                // IDの有るもの(DBに存在するデータ)のみ、更新する
                Long id = deleteDefinition.getHgdId();
                if (id != null) {
                    ids.add(id);
                }
            }
            iHistGroupdefinitionsService.removeByIds(ids);
        }
    }

    /**
     * DB削除処理(組織・役職選択)<br>
     * 定義情報をDBへ反映させる(物理更新)
     *
     */
    public void deleteGroupSectionPost(List<MastGroupsectionpostmappingDO> deleteSectionPost) {
        List<Long> ids = CollUtil.newArrayList();
        // データベースへ更新(削除)
        if (CollUtil.isNotEmpty(deleteSectionPost)) {
            for (MastGroupsectionpostmappingDO mastGroupsectionpostmappingDO : deleteSectionPost) {
                // IDの有るもの(DBに存在するデータ)のみ、更新する
                Long id = mastGroupsectionpostmappingDO.getMagId();
                if (id != null) {
                    ids.add(id);
                }
            }
            iMastGroupsectionpostmappingService.removeByIds(ids);
        }
    }

    /**
     * DB新規登録処理(グループ定義条件)<br>
     * 定義情報をDBへ反映させる(物理更新)
     * @param peInsertData  登録／更新データ
     */
    public void updateMastGroupDefinitions(MastGroupdefinitionsDO peInsertData,String groupCheckQuery,GroupManagerEditDTO dto) {
        // 削除対象データをデータベースへ反映
        if (dto.getMgpId() != null) {
            // 入れ替え用Entity
            MastGroupdefinitionsDO oDelete = new MastGroupdefinitionsDO();
            // IDの有るもの(DBに存在するデータ)のみ、更新する
            // 削除対象のIDを格納
            oDelete.setMgpId(dto.getMgpId());
            iMastGroupdefinitionsService.removeById(dto.getMgpId());
        }
        // 作成されたグループ判定クエリを設定
        peInsertData.setMgpCquery(groupCheckQuery);
        peInsertData.setMgpDmodifieddate(DateUtil.date());
        peInsertData.setMgpCmodifieruserid(SecurityUtil.getUserId());
        if (peInsertData.getMgpDenddate()==null) {
            peInsertData.setMgpDenddate(SysUtil.getMaxDateObject());
        }
        // 新規追加対象データをデータベースへ反映
        if (peInsertData.getMgpId() == null) {
            iMastGroupdefinitionsService.save(peInsertData);
        }
    }

    /**
     * DB新規登録処理(条件式選択)<br>
     * 定義情報をDBへ反映させる(物理更新)
     *
     * @param psCustomerId  顧客コード
     * @param psSystem      システムコード
     * @param psGroup       グループコード
     * @param ptStartDate   開始日
     * @param ptEndDate     終了日
     */
    public void insertHistGroupDefinitions(String psCustomerId, String psSystem, String psGroup,Date ptStartDate,Date ptEndDate, String psCompanyId,List<QueryConditionRowDTO> queryConditionList) {
        // 入れ替え処理(表示用DTO ⇒ 更新用Entity)を行い、登録
        List<Long> removeIds = CollUtil.newArrayList();
        List<QueryConditionRowDTO> saveQueryConditions = CollUtil.newArrayList();
        List<QueryConditionRowDTO> updateQueryConditions = CollUtil.newArrayList();
        for (QueryConditionRowDTO queryConditionRowDTO : queryConditionList) {
            Long id = queryConditionRowDTO.getId();
            if (id == null) {
                saveQueryConditions.add(queryConditionRowDTO);
            } else {
                if (queryConditionRowDTO.getDelete()) {
                    removeIds.add(id);
                } else {
                    updateQueryConditions.add(queryConditionRowDTO);
                }
            }
        }
        if (CollUtil.isNotEmpty(removeIds)) {
            iHistGroupdefinitionsService.removeByIds(removeIds);
        }
        // DELETEの後にINSERT and UPDATE実行
        List<HistGroupdefinitionsDO> insertDefinitionsDOList = CollUtil.newArrayList();
        for (int j = 0; j < saveQueryConditions.size(); j++) {
            insertDefinitionsDOList.add(
                    queryConditionLogic.insertGroupDefinitions(
                            psCustomerId, psSystem, psGroup, ptStartDate,
                            ptEndDate, saveQueryConditions.get(j), j, psCompanyId));
        }
        if (CollUtil.isNotEmpty(insertDefinitionsDOList)){
            iHistGroupdefinitionsService.saveBatch(insertDefinitionsDOList);
        }
        List<HistGroupdefinitionsDO> updateDefinitionsDOList = CollUtil.newArrayList();
        for (int i = 0; i < updateQueryConditions.size(); i++) {
            updateDefinitionsDOList.add(
                    queryConditionLogic.insertGroupDefinitions(
                            psCustomerId, psSystem, psGroup, ptStartDate,
                            ptEndDate, updateQueryConditions.get(i), i, psCompanyId)
            );
        }
        if (CollUtil.isNotEmpty(updateDefinitionsDOList)) {
            iHistGroupdefinitionsService.updateBatchById(updateDefinitionsDOList);
        }
    }

    /**
     * DB新規登録処理(組織・役職選択・職員選択)<br>
     * 定義情報をDBへ反映させる(物理更新)
     *
     * @param psCustomerId  顧客コード
     * @param psSystem      システムID
     * @param psGroup       グループID
     * @param ptStartDate   開始日
     * @param ptEndDate     終了日
     */
    public void insertSectionPost(String psCustomerId, String psSystem, String psGroup,
                                   Date ptStartDate, Date ptEndDate,List<SectionPostListDTO> companyList,Boolean isAdd) {
        if (CollUtil.isNotEmpty(companyList)) {
            SectionPostListDTO companyRowListDTO = companyList.get(0);
            List<SectionPostRowListDTO> sectionList = companyRowListDTO.getSectionList();

            /** 定義情報取得(法人＆組織指定リスト(登録更新用))　*/
            insertSectionList(sectionList, psCustomerId, psSystem, psGroup, ptStartDate, ptEndDate,isAdd);

            /** 定義情報取得(法人＆組織＆役職リスト(登録更新用))　*/
            if (CollUtil.isNotEmpty(sectionList)) {
                for (SectionPostRowListDTO sectionPostRowListDTO : sectionList) {
                    /** 定義情報取得(法人＆組織＆役職リスト(登録更新用))　*/
                    insertList(sectionPostRowListDTO.getPostList(),
                            FG_COMP_SEC_POST, psCustomerId, psSystem,
                            psGroup, ptStartDate, ptEndDate,isAdd);
                }
            }

            /** 定義情報取得(法人＆組織＆所属長リスト(登録更新用))　*/
            insertList(companyRowListDTO.getBossSectionList(),
                    FG_COMP_SEC_BOSS, psCustomerId, psSystem,
                    psGroup, ptStartDate, ptEndDate,isAdd);

            /** 定義情報取得(法人＆役職リスト(登録更新用))　*/
            insertList(companyRowListDTO.getPostList(),
                    FG_COMP_POST, psCustomerId, psSystem,
                    psGroup, ptStartDate, ptEndDate,isAdd);

            /** 定義情報取得(法人＆職員リスト(登録更新用))　*/
            insertSelectEmpList(companyRowListDTO.getEmployList(),
                    FG_COMP_EMP, psCustomerId, psSystem,
                    psGroup, ptStartDate, ptEndDate);
        }
    }

    /**
     * DB新規登録(更新)処理(職員選択)用のEntity作成処理(グループ条件定義マスタ(組織・役職))<br>
     * 定義情報をDBへ反映させるためのEntityを作成する。
     *
     * @param poList        更新対象リスト
     * @param psTypeId      定義ID
     * @param psCustomerId  顧客コード
     * @param psSystem      システムID
     * @param psGroup       グループID
     * @param ptStartDate   開始日
     * @param ptEndDate     終了日
     * @return int 登録件数
     */
    public void insertSelectEmpList(List<SectionPostRowDTO> poList, String psTypeId,
                                    String psCustomerId, String psSystem, String psGroup,
                                    Date ptStartDate, Date ptEndDate) {
        // ▼#2869 「職員選択による定義」にて一旦データ登録後に削除すると、データが削除されていない
        // 職員検索のときのみ、DELETE + INSERTにする
        // DBに登録されている、職員選択情報を一旦クリアする
        iMastGroupsectionpostmappingService.deleteSectionPostType(
                psCustomerId, psSystem, psGroup, psTypeId, ptStartDate);
        List<MastGroupsectionpostmappingDO> saveList = CollUtil.newArrayList();
        // 入れ替え処理(表示用DTO ⇒ 更新用Entity)を行い、登録
        if (CollUtil.isNotEmpty(poList)) {
            for (int i = 0; i < poList.size(); i++) {
                // データベースへ更新(新規登録)
                SectionPostRowDTO tempItem = poList.get(i);
                if (iMastGroupsectionpostmappingService.getById(tempItem.getId()) ==null) {
                    saveList.add(sectionPostLogic.insertSectionPost(poList, psTypeId,
                            psCustomerId, psSystem, psGroup, ptStartDate, ptEndDate, i));
                }
            }
        }
        if (CollUtil.isNotEmpty(saveList)){
            iMastGroupsectionpostmappingService.saveBatch(saveList);
        }
    }

    /**
     * DB新規登録(更新)処理用のEntity作成処理(グループ条件定義マスタ(組織・役職))<br>
     * 定義情報をDBへ反映させるためのEntityを作成する。(組織登録用)
     *
     * @param poList        更新対象リスト
     * @param psCustomerId  顧客コード
     * @param psSystem      システムID
     * @param psGroup       グループID
     * @param ptStartDate   開始日
     * @param ptEndDate     終了日
     * @return int 登録件数
     */
    public void insertSectionList(List<SectionPostRowListDTO> poList,
                                  String psCustomerId, String psSystem, String psGroup,
                                  Date ptStartDate, Date ptEndDate,boolean isAdd) {
        // 入れ替え処理(表示用DTO ⇒ 更新用Entity)を行い、登録
        List<MastGroupsectionpostmappingDO> saveList = CollUtil.newArrayList();
        if (CollUtil.isNotEmpty(poList)) {
            for (int i = 0; i < poList.size(); i++) {
                // IDの無いもの(DBに存在しないデータ)のみ、更新する
                SectionPostRowListDTO tempItem = poList.get(i);
                if (isAdd || !tempItem.getDelete()) {
                    if (iMastGroupsectionpostmappingService.getById(tempItem.getId()) ==null) {
                        // データベースへ更新(新規登録)
                        saveList.add(sectionPostLogic.insertSection(poList, psCustomerId,
                                psSystem, psGroup, ptStartDate, ptEndDate, i));
                    }
                }
            }
        }
        if (CollUtil.isNotEmpty(saveList)){
            iMastGroupsectionpostmappingService.saveBatch(saveList);
        }
    }

    /**
     * DB新規登録(更新)処理用のEntity作成処理(グループ条件定義マスタ(組織・役職))<br>
     * 定義情報をDBへ反映させるためのEntityを作成する。
     *
     * @param poList        更新対象リスト
     * @param psTypeId      定義ID
     * @param psCustomerId  顧客コード
     * @param psSystem      システムID
     * @param psGroup       グループID
     * @param ptStartDate   開始日
     * @param ptEndDate     終了日
     */
    public void insertList(List<SectionPostRowDTO> poList, String psTypeId,
                           String psCustomerId, String psSystem, String psGroup,
                            Date ptStartDate, Date ptEndDate,boolean isAdd) {
        // 入れ替え処理(表示用DTO ⇒ 更新用Entity)を行い、登録
        List<MastGroupsectionpostmappingDO> saveList = CollUtil.newArrayList();
        if (CollUtil.isNotEmpty(poList)) {
            for (int i = 0; i < poList.size(); i++) {
                // IDの無いもの(DBに存在しないデータ)のみ、更新する
                SectionPostRowDTO tempItem = poList.get(i);
                // 如果group是新增的或者此项不是删除项
                if (isAdd || !tempItem.getDelete()) {
                    // 如果还未添加过才添加
                    if (iMastGroupsectionpostmappingService.getById(tempItem.getId()) ==null) {
                        saveList.add(sectionPostLogic.insertSectionPost(poList, psTypeId,
                                psCustomerId, psSystem, psGroup, ptStartDate, ptEndDate, i));
                    }
                }
            }
        }
        if (CollUtil.isNotEmpty(saveList)){
            iMastGroupsectionpostmappingService.saveBatch(saveList);
        }
    }

    /**
     * DB新規登録(更新)処理用のEntity作成処理(グループ条件定義マスタ)<br>
     * 定義情報をDBへ反映させるためのEntityを作成する。
     *
     * @param psCustomerId  顧客コード
     * @param psSystemId    システムID
     * @param psGroupId     グループID
     * @param ptStartDate   開始日
     * @param ptEndDate     終了日
     * @param psBaseFlg     グループ定義種別フラグ
     * @return MastGroupDefinitionsEntity  更新用Entity
     */
    public MastGroupdefinitionsDO insertGroupDefinitions(String psCustomerId, String psSystemId,
                                                             String psGroupId,Date ptStartDate,Date ptEndDate, String psBaseFlg) {
        // 入れ替え用Entity
        MastGroupdefinitionsDO oInsert = new MastGroupdefinitionsDO();
        // 入れ替え処理(表示用DTO ⇒ 更新用Entity)を行い、登録
        oInsert.setMgpCcustomeridCkFk(psCustomerId);
        oInsert.setMgpCsystemidCk(psSystemId);
        oInsert.setMgpCgroupidCkFk(psGroupId);
        oInsert.setMgpDstartdate(ptStartDate);
        oInsert.setMgpDenddate(ptEndDate);
        oInsert.setMgpBaseflag(psBaseFlg);
        return oInsert;
    }

    /**
     * DB新規登録処理(グループ定義)<br>
     * 定義情報をDBへ反映させる(物理更新)
     *
     * @param peInsertData  登録／更新データ
     */
    public void updateGroup(MastGroupDO peInsertData) {
        if (peInsertData.getMgDenddate()==null) {
            peInsertData.setMgDenddate(SysUtil.getMaxDateObject());
        }
        peInsertData.setMgDmodifieddate(DateUtil.date());
        peInsertData.setMgCmodifieruserid(SecurityUtil.getUserId());
        // 更新処理
        if (peInsertData.getMgId() == null) {
            // IDの無いもの(DBに存在しないデータ)は、新規登録処理
            iMastGroupService.save(peInsertData);
        } else {
            // IDの有るもの(DBに存在するデータ)は、更新処理
            iMastGroupService.updateById(peInsertData);
        }
    }

    /**
     * DB新規登録(更新)処理用のEntity作成処理(グループ定義マスタ)<br>
     * 定義情報をDBへ反映させるためのEntityを作成する。
     *
     * @param psCustomerId  顧客コード
     * @param psSystemId    システムコード
     * @param ptStartDate   開始日
     * @param ptEndDate     終了日
     * @param poDto         グループ定義用Dto
     * @return MastGroupEntity  更新用Entity
     */
    public MastGroupDO insertGroup(String psCustomerId, String psSystemId,
                                   Date ptStartDate, Date ptEndDate, GroupManagerEditDTO poDto) {
        // 入れ替え用Entity
        MastGroupDO oInsert = new MastGroupDO();
        // 画面に入力されていた項目をセットする
        oInsert.setMgCcompanyid("01");
        oInsert.setMgNpartinentnumber(poDto.getPeopleCount());
        oInsert.setMgNweightage(poDto.getWeightAge());
        oInsert.setMgCtext(poDto.getRemark());
        oInsert.setMgCgroupidPk(poDto.getGroupId());
        oInsert.setMgClanguage("ja");
        oInsert.setMgCgroupdescription(poDto.getGroupName());
        oInsert.setMgCgroupdescriptionja(poDto.getGroupName());
//        oInsert.setMgCgroupdescriptionen(poDto.getMgCgroupdescriptionen());
//        oInsert.setMgCgroupdescriptionch(poDto.getMgCgroupdescriptionch());
//        oInsert.setMgCgroupdescription01(poDto.getMgCgroupdescription01());
//        oInsert.setMgCgroupdescription02(poDto.getMgCgroupdescription02());

        // 引数より渡された値を格納する
        oInsert.setMgCcustomerid(psCustomerId);
        oInsert.setMgCsystemidCkFk(psSystemId);
        oInsert.setMgDstartdate(ptStartDate);
        if (ptEndDate == null) {
            oInsert.setMgDenddate(SysUtil.getMaxDateObject());
        } else {
            oInsert.setMgDenddate(ptEndDate);
        }
        // 更新処理のみIDとVersionNoを格納
        if (poDto.getMgId() != null) {
            oInsert.setMgId(poDto.getMgId());
        }
        return oInsert;
    }

    /**
     * DB更新処理(基点組織選択)<br>
     * 定義情報をDBへ反映させる(物理更新)
     *
     */
    public void insertBaseSection(List<BaseSectionRowDTO> baseSectionList) {

        PsSession session = (PsSession) ContextUtil.getSession().getAttribute(Constant.PS_SESSION);

        List<MastGroupbasesectionDO> saveRows = CollUtil.newArrayList();
        List<MastGroupbasesectionDO> updateRows = CollUtil.newArrayList();
        // 入れ替え処理(表示用DTO ⇒ 更新用Entity)を行い、登録
        Date now = DateUtil.date();
        if (CollUtil.isNotEmpty(baseSectionList)) {
            for (BaseSectionRowDTO baseSectionRowDTO : baseSectionList) {
                for (BaseSectionRowListDTO rowListDTO : baseSectionRowDTO.getSectionList()) {
                    MastGroupbasesectionDO ent = baseSectionLogic.insertBaseSection(rowListDTO);
                    ent.setMgbsCmodifieruserid(session.getLoginUser());    // 更新者
                    ent.setMgbsDmodifieddate(now);
                    // IDの無いもの(DBに存在しないデータ)は追加する
                    if (ent.getMgbsId() == null || (ent.getMgbsId()!=null && iMastGroupbasesectionService.getById(ent.getMgbsId()) == null)) {
                        // データベースへ更新(新規登録)
//                    ent.setVersionno(1L);
                        saveRows.add(ent);
                        // IDがあるものは更新する
                    } else {
                        // データベースへ更新(更新)
                        updateRows.add(ent);
                    }
                }
            }
        }
        // 加入默认法人code为前缀，兼容检索对象范围查询
        String prefixSectionId = ",01,,|,";
        saveRows.forEach(row -> {
            String layeredSectionId = row.getMgbsClayeredsectionid();
            layeredSectionId = layeredSectionId.substring(2);
            row.setMgbsClayeredsectionid(prefixSectionId+layeredSectionId);
        });
        updateRows.forEach(row-> {
            String layeredSectionId = row.getMgbsClayeredsectionid();
            layeredSectionId = layeredSectionId.substring(2);
            row.setMgbsClayeredsectionid(prefixSectionId+layeredSectionId);
        });
        if (CollUtil.isNotEmpty(saveRows)) {
            iMastGroupbasesectionService.saveBatch(saveRows);
        }
        if (CollUtil.isNotEmpty(updateRows)) {
            iMastGroupbasesectionService.updateBatchById(updateRows);
        }
    }

    /**
     * 条件式妥当性チェック
     *
     * @return  boolean チェック結果
     */
    public boolean isQueryCondition(String baseFlg,List<QueryConditionRowDTO> queryConditionList) {

        boolean bResult = true;

        // グループ定義種別フラグが"1"(条件式定義)の場合
        if (StrUtil.equals(baseFlg,BASE_FLG_DEF)) {
            // 条件式妥当性チェック処理判定
            if (queryConditionValidatorLogic.checkQueryCondition(queryConditionList) != 0) {
                bResult = false;
            }
        }
        return bResult;
    }

    /**
     * グループ判定クエリ妥当性チェック
     * グループ判定SQLを組み立てて実行し、クエリが妥当なものであるかどうかチェックする。
     *
     * @param  psQuery      グループ判定クエリ
     * @return nQueryCount  グループ判定検索結果(正常：0以上, 異常：-1)
     * @throws Exception
     * @throws SQLException
     */
    public int isCheckValidQuery(String psQuery) {
        // 汎用DBアクセス処理にてSQL実行
        int nQueryCount;
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            nQueryCount = dbControllerLogic.executeQuery(psQuery,conn).size();
        } catch (SQLException e) {
            nQueryCount = -1;
        } finally {
            DbUtil.close(conn);
        }
        return nQueryCount;
    }

    /**
     * 登録対象のグループ判定クエリを返却する
     *
     * @return String   登録対象グループ判定クエリ
     */
    public String getGroupCheckQuery(String baseFlg, String customerId, String systemId, String groupId, Date startDate, Date endDate,
                                      List<SectionPostListDTO> sectionPostCompanyList,
                                      String companyId,
                                      List<QueryConditionRowDTO> queryConditionList) {
        String sQuery = "";
        // クエリ作成処理判定
        if (StrUtil.equals(baseFlg,BASE_FLG_SECPOST) || StrUtil.equals(baseFlg,BASE_FLG_EMPLOYEES)) {
            // グループ定義種別フラグが"0"(組織・役職定義)の場合
            sQuery = sectionPostLogic.createQuery(customerId, systemId, groupId,startDate, endDate,sectionPostCompanyList,companyId);
        } else if (StrUtil.equals(baseFlg,BASE_FLG_DEF)) {
            // グループ定義種別フラグが"1"(条件式定義)の場合
            sQuery = queryConditionLogic.createQueryCondition(customerId, companyId,
                    systemId, groupId, startDate, queryConditionList);
        }
        return sQuery;
    }

}
