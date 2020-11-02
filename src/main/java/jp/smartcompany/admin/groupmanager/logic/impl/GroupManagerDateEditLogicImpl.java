package jp.smartcompany.admin.groupmanager.logic.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.admin.groupmanager.dto.GroupManagerDeleteDTO;
import jp.smartcompany.admin.groupmanager.dto.GroupManagerGroupListDTO;
import jp.smartcompany.admin.groupmanager.dto.GroupManagerModifiedDateDTO;
import jp.smartcompany.admin.groupmanager.dto.GroupManagerSortDTO;
import jp.smartcompany.admin.groupmanager.logic.GroupManagerDateEditLogic;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
import jp.smartcompany.job.modules.core.pojo.entity.*;
import jp.smartcompany.job.modules.core.service.*;
import jp.smartcompany.job.modules.core.util.PsConst;
import jp.smartcompany.job.modules.core.util.PsSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Xiao Wenpeng
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupManagerDateEditLogicImpl implements GroupManagerDateEditLogic {

    private final ScCacheUtil cacheUtil;
    private final PsSearchCompanyUtil searchCompanyUtil;
    private final IMastGroupService iMastGroupService;
    private final IMastGroupdefinitionsService iMastGroupdefinitionsService;
    private final IHistGroupdefinitionsService iHistGroupdefinitionsService;
    private final IMastGroupsectionpostmappingService iMastGroupsectionpostmappingService;
    private final IMastGroupbasesectionService iMastGroupbasesectionService;
    private final IHistGroupdatapermissionService iHistGroupdatapermissionService;
    private final IMastGroupapppermissionService iMastGroupapppermissionService;
    private final IMastPermissionService iMastPermissionService;

    /** システムプロパティ(グループ判定モード) */
    private static final String KEY_GROUP_CHECK_MODE = "GroupCheckMode";

    @Override
    public Map<String,Object> editListHandler(String searchDate,String systemId) {
        Date now = DateUtil.date();
        if (searchDate == null) {
            searchDate = SysUtil.transDateToString(now);
        }
        // システムプロパティより、グループ判定モードを取得
        String checkMode = cacheUtil.getSystemProperty(KEY_GROUP_CHECK_MODE);
        // システムプロパティに設定されていなかった場合
        if (StrUtil.isBlank(checkMode)) {
            // 必須常駐変数未設定例外
            throw new GlobalException(KEY_GROUP_CHECK_MODE);
        }
        // 法人検索対象範囲情報取得(参照可能な法人のリストを取得)
        List<String> companyValidList = searchCompanyUtil.getCompList(SysUtil.transStringToDate(searchDate));
        PsSession session = (PsSession) ContextUtil.getSession().getAttribute(Constant.PS_SESSION);
        // 获取当前系统正在被使用的grouplist
        List<GroupManagerGroupListDTO> validGroupList= iMastGroupService.selectValidGroup(session.getLoginCustomer(),systemId,session.getLanguage(),searchDate,companyValidList);
        validGroupList.forEach(item->{
            if (StrUtil.equals(item.getMgCcompanyid(), PsConst.CODE_ALL_COMPANIES)){
                item.setCompanyName("全社区分");
            }
        });
        // 歴日付情報取得
        List<GroupManagerModifiedDateDTO> historyDate =
                iMastGroupService.selectHistoryDate(session.getLoginCustomer(), systemId, companyValidList,searchDate);

        Map<String,Object> map = MapUtil.newHashMap();
        map.put("historyDate",historyDate);
        map.put("groupList",validGroupList);
        return map;
    }

    @Transactional(rollbackFor = GlobalException.class)
    @Override
    public String deleteHandler(GroupManagerDeleteDTO dto) {
        QueryWrapper<MastGroupDO> qw = SysUtil.query();
        String date = SysUtil.transDateToString(DateUtil.date());
        PsSession session = (PsSession) ContextUtil.getSession().getAttribute(Constant.PS_SESSION);
        if (StrUtil.isBlank(dto.getSystemId())){
            dto.setSystemId("01");
        }
        qw.eq("MG_CCUSTOMERID",session.getLoginCustomer())
          .eq("MG_CSYSTEMID_CK_FK",dto.getSystemId())
          .in("MG_CGROUPID_PK",dto.getGroupIds())
          .le("MG_DSTARTDATE",date)
          .ge("MG_DENDDATE",date);
        iMastGroupService.remove(qw);
        nestedMastDelete(dto.getGroupIds(),dto.getSystemId(),session.getLoginCustomer(),session.getLoginCompany());
        iMastGroupService.updateGroupPrionityLevel(date,session.getLoginCustomer(),dto.getSystemId());
        return "グループを削除しました";
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public String sortHandler(List<String> groupIds,String systemId) {
        if (StrUtil.isBlank(systemId)) {
            systemId = "01";
        }
        PsSession session = (PsSession) ContextUtil.getSession().getAttribute(Constant.PS_SESSION);
        List<GroupManagerSortDTO> sortList = CollUtil.newArrayList();
        String now = SysUtil.transDateToString(DateUtil.date());

        for (int i = 0; i < groupIds.size(); i++) {
            MastGroupDO mastGroupDO = new MastGroupDO();
            mastGroupDO.setMgNweightage((long)i+1);
             GroupManagerSortDTO sortDTO=new GroupManagerSortDTO();
             sortDTO.setGroupId(groupIds.get(i));
             sortDTO.setCustId(session.getLoginCustomer());
             sortDTO.setSearchDate(now);
             sortDTO.setSort(i);
             sortDTO.setSystemId(systemId);
             sortList.add(sortDTO);

           iMastGroupService.update(mastGroupDO,
                   SysUtil.<MastGroupDO>query().eq("MG_CCUSTOMERID","01")
                                               .eq("MG_CSYSTEMID_CK_FK","01")
                                               .eq("MG_ID",groupIds.get(i))
                                               .eq("MG_CLANGUAGE","ja")
           .le("MG_DSTARTDATE",now).ge("MG_DENDDATE",now));
        }
        return "順位を変更しました";
    }

    public void nestedMastDelete(List<String> groupIds,String systemId,String custId,String companyId) {
        // グループ条件定義マスタ削除
        List<MastGroupdefinitionsDO> definitionList = iMastGroupdefinitionsService
                .list(
                        SysUtil.<MastGroupdefinitionsDO>query()
                        .eq("MGP_CCUSTOMERID_CK_FK",custId)
                        .eq("MGP_CSYSTEMID_CK",systemId)
                        .in("MGP_CGROUPID_CK_FK",groupIds)
                );
        iMastGroupdefinitionsService.removeByIds(definitionList);
        // グループ条件定義マスタ(条件式)削除
        List<HistGroupdefinitionsDO> histGroupdefinitionsList = iHistGroupdefinitionsService
                .list(
                        SysUtil.<HistGroupdefinitionsDO>query()
                                .eq("HGD_CCUSTOMERID",custId)
                                .eq("MHGD_CSYSTEMID",systemId)
                                .in("HGD_CGROUPID",groupIds)
                );
       iHistGroupdefinitionsService.removeByIds(histGroupdefinitionsList);
        // グループ条件定義マスタ(組織,役職)削除
       List<MastGroupsectionpostmappingDO> mastGroupsectionpostmappingList =iMastGroupsectionpostmappingService
               .list(
                       SysUtil.<MastGroupsectionpostmappingDO>query()
                               .eq("MAG_CCUSTOMERID_CK_FK",custId)
                               .eq("MAG_CSYSTEMID_CK",systemId)
                               .in("MAG_CGROUPID_FK",groupIds)
               );
       iMastGroupsectionpostmappingService.removeByIds(mastGroupsectionpostmappingList);
        // グループ別基点組織定義マスタ削除
       List<MastGroupbasesectionDO> mastGroupbasesectionList = iMastGroupbasesectionService
               .list(
                       SysUtil.<MastGroupbasesectionDO>query()
                               .eq("MGBS_CCUSTOMERID",custId)
                               .eq("MGBS_CSYSTEMID",systemId)
                               .in("MGBS_CGROUPID",groupIds)
               );
       iMastGroupbasesectionService.removeByIds(mastGroupbasesectionList);
        // グループ別アプリケーション検索対象範囲設定マスタ削除
        List<HistGroupdatapermissionDO> groupdatapermissionList = iHistGroupdatapermissionService
                .list(
                        SysUtil.<HistGroupdatapermissionDO>query()
                                .eq("HGP_CCUSTOMERID",custId)
                                .eq("HGP_CSYSTEMID",systemId)
                                .in("HGP_CGROUPID",groupIds)
                );
        iHistGroupdatapermissionService.removeByIds(groupdatapermissionList);
        // グループ別アプリケーション権限マスタ削除
        List<MastGroupapppermissionDO> appPermissionList = iMastGroupapppermissionService.list(
                SysUtil.<MastGroupapppermissionDO>query()
                        .eq("MGP_CCOMPANYID",companyId)
                        .eq("MGP_CSYSTEMID",systemId)
                        .in("MGP_CGROUPID",groupIds)
        );
        iMastGroupapppermissionService.removeByIds(appPermissionList);
        // 機密設定マスタ削除
        List<MastPermissionDO> permissionList = iMastPermissionService.list(
                SysUtil.<MastPermissionDO>query()
                        .eq("MP_CCUSTOMERID",companyId)
                        .eq("MP_CSYSTEMID",systemId)
                        .in("MP_CGROUPID",groupIds)
        );
        iMastPermissionService.removeByIds(permissionList);
    }

}
