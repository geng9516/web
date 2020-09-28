package jp.smartcompany.admin.groupmanager.logic.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import jp.smartcompany.admin.groupmanager.dto.GroupManagerGroupListDTO;
import jp.smartcompany.admin.groupmanager.logic.GroupManagerHistoryEditLogic;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastSystemDO;
import jp.smartcompany.job.modules.core.service.IMastGroupService;
import jp.smartcompany.job.modules.core.service.IMastSystemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupManagerHistoryEditLogicImpl implements GroupManagerHistoryEditLogic {

    private final IMastGroupService mastGroupService;
    private final IMastSystemService mastSystemService;
    private final PsSearchCompanyUtil searchCompanyUtil;

    @Override
    public Map<String,Object> getGroupHistoryList(String systemId, String groupId) {
        // 法人検索対象範囲情報取得(参照可能な法人のリストを取得)
        List<String> oCompanyValidList = searchCompanyUtil.getCompList(DateUtil.date());
        List<GroupManagerGroupListDTO> groupHistories = mastGroupService.selectGroupHistoryList("01",systemId,"ja",groupId,null,oCompanyValidList);
        List<MastSystemDO> systemList = mastSystemService.selectSystemList("ja");
        Map<String,Object> map = MapUtil.newHashMap();
        map.put("systemList",systemList);
        map.put("groupHistories",groupHistories);
        return map;
    }

}
