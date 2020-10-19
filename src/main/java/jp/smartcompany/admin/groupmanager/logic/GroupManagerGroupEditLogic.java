package jp.smartcompany.admin.groupmanager.logic;

import jp.smartcompany.admin.component.dto.SectionPostRowListDTO;
import jp.smartcompany.admin.groupmanager.dto.GroupManagerEditDTO;
import jp.smartcompany.framework.component.dto.QueryConditionSelectDTO;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.List;

public interface GroupManagerGroupEditLogic {

    Map<String,Object> detail(Date searchDate, String systemId, String groupId) throws ParseException;

    void update(GroupManagerEditDTO dto);

    List<QueryConditionSelectDTO> queryConditionList(String tableId);

    List<SectionPostRowListDTO> getBossCompSectionList(List<String> sectionList);

}
