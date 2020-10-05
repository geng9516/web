package jp.smartcompany.admin.groupmanager.logic.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.admin.groupappmanager.logic.GroupAppManagerMainLogic;
import jp.smartcompany.admin.groupmanager.dto.GroupManagerGroupListDTO;
import jp.smartcompany.admin.groupmanager.dto.GroupManagerModifiedDateDTO;
import jp.smartcompany.admin.groupmanager.logic.GroupManagerLogic;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.appcontrol.business.AppAuthInfoBusiness;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastSystemDO;
import jp.smartcompany.job.modules.core.service.IMastGroupService;
import jp.smartcompany.job.modules.core.util.PsConst;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.core.util.PsSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupManagerLogicImpl implements GroupManagerLogic {

    private final IMastGroupService iMastGroupService;
    private final PsSearchCompanyUtil psSearchCompanyUtil;
    private final AppAuthInfoBusiness appAuthInfoBusiness;
    private final GroupAppManagerMainLogic groupAppManagerMainLogic;

    // 有効なグループリスト情報取得
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String,Object> getManagerGroupList(PsDBBean psDBBean, Date searchDate, String systemId){
        if (StrUtil.isBlank(systemId)){
            systemId = appAuthInfoBusiness.getSystemCode(psDBBean.getSiteId(), psDBBean.getAppId());
        }
        if (searchDate==null) {
            searchDate = DateUtil.date();
        }
        List<String> companyList = psSearchCompanyUtil.getCompList(searchDate);
        String searchDateStr = SysUtil.transDateToString(searchDate);
        PsSession session = (PsSession) ContextUtil.getSession().getAttribute(Constant.PS_SESSION);
        // 获取当前系统正在被使用的grouplist
        List<GroupManagerGroupListDTO> validGroupList= iMastGroupService.selectValidGroup(session.getLoginCustomer(),systemId,session.getLanguage(),searchDateStr,companyList);
        validGroupList.forEach(item->{
            if (StrUtil.equals(item.getMgCcompanyid(), PsConst.CODE_ALL_COMPANIES)){
                item.setCompanyName("全社区分");
            }
        });
        // 获取当前系统未被使用的groupList
        List<GroupManagerGroupListDTO> inValidGroup =
                iMastGroupService.selectInvalidGroupList(
                        session.getLoginCustomer(),
                        systemId,
                        session.getLanguage(),
                        validGroupList.stream().map(GroupManagerGroupListDTO::getMgCgroupidPk).collect(Collectors.toList()),
                        companyList
                );
        List<GroupManagerModifiedDateDTO> modifiedDateList = iMastGroupService.selectHistoryDate(session.getLoginCustomer(), systemId, companyList,searchDateStr);
        List<MastSystemDO> systemList =  groupAppManagerMainLogic.getSystemList(psDBBean.getLanguage());
        return MapUtil.<String,Object>builder()
                .put("validGroupList",validGroupList)
                .put("invalidGroupList",inValidGroup)
                .put("modifiedDateList",modifiedDateList)
                .put("systemList",systemList)
                .build();
    }

}
