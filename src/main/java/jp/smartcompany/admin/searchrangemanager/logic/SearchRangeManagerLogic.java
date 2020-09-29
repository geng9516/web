package jp.smartcompany.admin.searchrangemanager.logic;

import java.util.Date;
import java.util.Map;

public interface SearchRangeManagerLogic {

   Map<String,Object> listRangeTable(String systemId, Date searchDate, String groupId,
                                     String siteId, String appId,
                                     String language, String custId, String companyId, boolean isAll);
}
