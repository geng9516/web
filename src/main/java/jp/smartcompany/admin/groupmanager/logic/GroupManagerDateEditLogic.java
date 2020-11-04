package jp.smartcompany.admin.groupmanager.logic;

import java.util.List;
import java.util.Map;

/**
 * @author Xiao Wenpeng
 */
public interface GroupManagerDateEditLogic {

    Map<String,Object> editListHandler(String searchDate,String systemId);

    String deleteHandler(List<String> groupIds);

    String sortHandler(List<String> groupIds,String systemId);

}
