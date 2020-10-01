package jp.smartcompany.admin.searchrangemanager.logic;

import jp.smartcompany.job.modules.core.pojo.entity.MastDatapermissionDO;

import java.util.Date;
import java.util.Map;
import java.util.List;

public interface SearchRangeManagerLogic {

   Map<String,Object> listRangeTable(String systemId, Date searchDate, String groupId,
                                     String siteId, String appId,
                                     String language, String custId, String companyId, boolean isAll);

   List<MastDatapermissionDO> listConditions();

}
