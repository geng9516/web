package jp.smartcompany.admin.searchrangemanager.logic.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerGroupDTO;
import jp.smartcompany.admin.groupappmanager.logic.GroupAppManagerMainLogic;
import jp.smartcompany.admin.searchrangemanager.dto.SearchRangeManagerChangeDateDTO;
import jp.smartcompany.admin.searchrangemanager.dto.SearchRangeManagerDataDTO;
import jp.smartcompany.admin.searchrangemanager.logic.SearchRangeManagerLogic;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.service.IHistGroupdatapermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SearchRangeMangerLogicImpl implements SearchRangeManagerLogic {

    private final GroupAppManagerMainLogic groupAppManagerMainLogic;
    private final IHistGroupdatapermissionService histGroupdatapermissionService;

    @Override
    public Map<String,Object> listRangeTable(String systemId, Date searchDate, String groupId,
                                             String siteId, String appId,
                                             String language, String custId, String companyId, boolean isAll) {
        List<GroupAppManagerGroupDTO> groupList = groupAppManagerMainLogic.getGroupList(custId, systemId, language,
                searchDate, companyId,isAll);
        if (StrUtil.isNotBlank(groupId)) {
            groupList = groupList.stream().filter(item -> StrUtil.equals(groupId,item.getMgCgroupidPk())).collect(Collectors.toList());
        }
        List<String> permGroupIds = groupList.stream().map(GroupAppManagerGroupDTO::getMgCgroupidPk).collect(Collectors.toList());
        if (CollUtil.isEmpty(permGroupIds)){
            return null;
        }
        List<SearchRangeManagerDataDTO> dataList = histGroupdatapermissionService.getSearchRangeTableData(systemId,siteId,appId,searchDate,language,permGroupIds);
        dataList.forEach(item -> {
            item.setHgpDstartdate(searchDate);
        });
        Map<String,Object> map = MapUtil.newHashMap();
        SearchRangeManagerChangeDateDTO changeDateDTO = histGroupdatapermissionService.selectHistoryDate(custId, systemId,groupId, searchDate);
        map.put("dataList",dataList);
        String beforeDate = SysUtil.transDateToString(changeDateDTO.getBeforeDate());
        String afterDate = SysUtil.transDateToString(changeDateDTO.getAfterDate());
        String nowDate = SysUtil.transDateToString(changeDateDTO.getNowDate());
        String latestDate = SysUtil.transDateToString(changeDateDTO.getLatestDate());
        map.put("beforeDate",beforeDate);
        map.put("afterDate",afterDate);
        map.put("nowDate",nowDate);
        map.put("latestDate",latestDate);
        return map;
    }


}
