package jp.smartcompany.admin.groupmanager.logic;

import java.util.Map;

public interface GroupManagerHistoryEditLogic {

    Map<String,Object> getGroupHistoryList(String systemId, String groupId);

}
