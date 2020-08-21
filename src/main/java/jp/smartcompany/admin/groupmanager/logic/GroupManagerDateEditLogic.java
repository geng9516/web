package jp.smartcompany.admin.groupmanager.logic;

import jp.smartcompany.admin.groupmanager.dto.GroupManagerDeleteDTO;

import java.util.Map;
import java.util.List;

/**
 * @author Xiao Wenpeng
 */
public interface GroupManagerDateEditLogic {

    Map<String,Object> editListHandler(String searchDate,String systemId);

    String deleteHandler(GroupManagerDeleteDTO dto);

    String sortHandler(List<String> groupIds,String systemId);

}
