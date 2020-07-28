package jp.smartcompany.admin.groupmanager.logic;

import jp.smartcompany.admin.groupmanager.dto.GroupManagerEditDTO;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

public interface GroupManagerGroupEditLogic {

    Map<String,Object> detail(Date searchDate, String systemId, String groupId) throws ParseException;

    void update(GroupManagerEditDTO dto);

}
