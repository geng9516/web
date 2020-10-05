package jp.smartcompany.admin.searchrangemanager.logic.impl;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerGroupDTO;
import jp.smartcompany.admin.groupappmanager.logic.GroupAppManagerMainLogic;
import jp.smartcompany.admin.searchrangemanager.dto.SearchRangeManagerChangeDateDTO;
import jp.smartcompany.admin.searchrangemanager.dto.SearchRangeManagerDataDTO;
import jp.smartcompany.admin.searchrangemanager.logic.SearchRangeManagerLogic;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.HistGroupdatapermissionDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastDatapermissionDO;
import jp.smartcompany.job.modules.core.service.IHistGroupdatapermissionService;
import jp.smartcompany.job.modules.core.service.IMastDatapermissionService;
import jp.smartcompany.job.modules.core.util.PsConst;
import jp.smartcompany.job.modules.core.util.PsSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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
    private final TimedCache<String,Object> timedCache;

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
        timedCache.put(REQ_SCOPE_NAME+"_"+ ContextUtil.getHttpRequest().getSession().getId(),dataList);
        return map;
    }

    @Override
    public List<MastDatapermissionDO> listConditions() {
        QueryWrapper<MastDatapermissionDO> qw = SysUtil.query();
        qw.orderByAsc("MDP_CPERMISSIONID");
        return mastDatapermissionService.list(qw);
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public void executeUpdate(List<SearchRangeManagerDataDTO> updateList) {
        HttpServletRequest request = ContextUtil.getHttpRequest();
        PsSession psSession = (PsSession)request.getSession().getAttribute(Constant.PS_SESSION);
        String sessionId = request.getSession().getId();
        Date startDate = DateUtil.date();
        Date endDate  = SysUtil.transStringToDate(PsConst.MAXDATE);
        // 画面表示のためのイレモノを取得
        List<SearchRangeManagerDataDTO> dataList = (List<SearchRangeManagerDataDTO>) timedCache.get(REQ_SCOPE_NAME+"_"+sessionId,false);
        if (CollUtil.isEmpty(dataList)) {
            throw new GlobalException("リストの有効期限が切れています。更新してもう一度お試しください");
        }
        // 将本次更改过的数据覆盖到原先的缓存list中
        for (SearchRangeManagerDataDTO searchRangeManagerDataDTO : updateList) {
            for (SearchRangeManagerDataDTO rangeManagerDataDTO : dataList) {
                if (rangeManagerDataDTO.getHgpId().equals(searchRangeManagerDataDTO.getHgpId())) {
                    BeanUtil.copyProperties(searchRangeManagerDataDTO,rangeManagerDataDTO);
                    System.out.println(rangeManagerDataDTO);
                    break;
                }
            }
            searchRangeManagerDataDTO.setHgpDstartdate(startDate);
            searchRangeManagerDataDTO.setHgpDenddate(endDate);
        }

        // 更新数据库
        for (SearchRangeManagerDataDTO searchRangeManagerDataDTO : dataList) {
            HistGroupdatapermissionDO histDO = new HistGroupdatapermissionDO();
            BeanUtil.copyProperties(searchRangeManagerDataDTO,histDO);
            histDO.setHgpCmodifieruserid(psSession.getLoginUser());
            histDO.setHgpDmodifieddate(startDate);

            if (searchRangeManagerDataDTO.getCreateHistory() == 0) {
                histDO.setHgpDenddate(DateUtil.offset(startDate, DateField.DAY_OF_MONTH,-1));
                histGroupdatapermissionService.updateById(histDO);
                histDO.setHgpId(null);
                histGroupdatapermissionService.save(histDO);
            } else if (searchRangeManagerDataDTO.getCreateHistory() == 1) {
                histGroupdatapermissionService.save(histDO);
            } else if (searchRangeManagerDataDTO.getCreateHistory() == 2) {
                histGroupdatapermissionService.updateById(histDO);
            }
        }

        timedCache.remove(REQ_SCOPE_NAME+"_"+sessionId);
    }

}
