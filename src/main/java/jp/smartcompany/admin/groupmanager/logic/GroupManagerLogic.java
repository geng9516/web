package jp.smartcompany.admin.groupmanager.logic;

import jp.smartcompany.job.modules.core.util.PsDBBean;

import java.util.Date;
import java.util.Map;

public interface GroupManagerLogic {


    Map<String,Object> getManagerGroupList(PsDBBean psDBBean, Date searchDate, String systemId);

}
