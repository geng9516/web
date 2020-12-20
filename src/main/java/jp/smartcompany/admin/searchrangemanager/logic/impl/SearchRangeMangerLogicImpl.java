package jp.smartcompany.admin.searchrangemanager.logic.impl;

import cn.hutool.cache.impl.LRUCache;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerGroupDTO;
import jp.smartcompany.admin.groupappmanager.logic.GroupAppManagerMainLogic;
import jp.smartcompany.admin.searchrangemanager.dto.SearchRangeManagerChangeDateDTO;
import jp.smartcompany.admin.searchrangemanager.dto.SearchRangeManagerDataDTO;
import jp.smartcompany.admin.searchrangemanager.logic.SearchRangeManagerLogic;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SecurityUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.HistGroupdatapermissionDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastDatapermissionDO;
import jp.smartcompany.job.modules.core.service.IHistGroupdatapermissionService;
import jp.smartcompany.job.modules.core.service.IMastDatapermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SearchRangeMangerLogicImpl implements SearchRangeManagerLogic {

    private final GroupAppManagerMainLogic groupAppManagerMainLogic;
    private final IHistGroupdatapermissionService histGroupdatapermissionService;
    private final IMastDatapermissionService mastDatapermissionService;
    private final LRUCache<Object,Object> lruCache;

    public static final String REQ_SCOPE_NAME = "searchRangeTableDtoList";

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
        Date now = DateUtil.date();
        List<SearchRangeManagerDataDTO> dataList = histGroupdatapermissionService.getSearchRangeTableData(systemId,siteId,appId,searchDate,language,permGroupIds);
        // 在java中实现旧代码的createHistory逻辑
        dataList.forEach(item -> {
            if (item.getHgpDstartdate() == null){
                item.setCreateHistory(1);
            } else if (DateUtil.isSameDay(item.getHgpDstartdate(),now )) {
                item.setCreateHistory(2);
            }  else {
                item.setCreateHistory(0);
            }
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

    @Override
    public List<MastDatapermissionDO> listConditions() {
        QueryWrapper<MastDatapermissionDO> qw = SysUtil.query();
        qw.ne("MDP_CPERMISSIONID","0001").orderByAsc("MDP_CPERMISSIONID");
        return mastDatapermissionService.list(qw);
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public void executeUpdate(List<SearchRangeManagerDataDTO> updateList) {

        Date startDate = SysUtil.transStringToDate(SysUtil.transDateToString(DateUtil.date()));
        String userId = SecurityUtil.getUserId();

        Date now = DateUtil.date();
        // 更新数据库
        for (SearchRangeManagerDataDTO searchRangeManagerDataDTO : updateList) {
            HistGroupdatapermissionDO histDO = new HistGroupdatapermissionDO();
            searchRangeManagerDataDTO.setHgpDstartdate(startDate);
            BeanUtil.copyProperties(searchRangeManagerDataDTO,histDO);
            histDO.setHgpCmodifieruserid(userId);
            histDO.setHgpDmodifieddate(now);
            if (searchRangeManagerDataDTO.getCreateHistory() == 0) {
                histGroupdatapermissionService.updateFinishHistory(histDO.getHgpId(),startDate);
                histDO.setHgpId(null);
                histGroupdatapermissionService.save(histDO);
            } else if (searchRangeManagerDataDTO.getCreateHistory() == 1) {
                histGroupdatapermissionService.save(histDO);
            } else if (searchRangeManagerDataDTO.getCreateHistory() == 2) {
                histGroupdatapermissionService.updateById(histDO);
            }
        }

        lruCache.remove(ScCacheUtil.APP_SEARCH_RANGE_INFO);
        lruCache.remove(ScCacheUtil.GHM_DATA_PERMISSION_DEFS);
        lruCache.remove(ScCacheUtil.GHM_DATA_SECTION_POST);

    }


}
