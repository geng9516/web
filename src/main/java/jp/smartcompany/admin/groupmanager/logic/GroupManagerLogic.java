package jp.smartcompany.admin.groupmanager.logic;

import jp.smartcompany.admin.groupmanager.dto.GroupManagerGroupListDTO;
import jp.smartcompany.job.modules.core.util.PsDBBean;

import java.util.Date;
import java.util.List;

public interface GroupManagerLogic {


    List<GroupManagerGroupListDTO> getValidGroupList(PsDBBean psDBBean, Date searchDate, String systemId);

}
