package jp.smartcompany.admin.groupappmanager.logic;

import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerGroupDTO;
import jp.smartcompany.admin.groupappmanager.form.GroupAppManagerUpdatePermsForm;
import jp.smartcompany.admin.groupappmanager.vo.GroupAppManagerTableLayout;
import jp.smartcompany.job.modules.core.pojo.entity.MastApptreeDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastCompanyDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastSystemDO;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 起動権限設定 メインLogicインターフェース
 * @author Xiao Wenpeng
 */
public interface GroupAppManagerMainLogic {

    GroupAppManagerTableLayout listPermsTable(
            String systemId, Date date,
            String groupId, String psSite,
            String psApp, String psLanguage,
            String customerId,String companyId,
            Boolean isAll
    );

    List<GroupAppManagerGroupDTO> getGroupList(String customerId, String systemId, String language,
                                                      Date searchDate, String companyId, boolean isAll);

    List<MastApptreeDO> getSiteList(String psSystemId, String psLanguage);

    List<MastApptreeDO> getAppList(String psSystemId, String psLanguage, String psSiteId);

    List<MastSystemDO> getSystemList(String language);

    List<MastCompanyDO> getCompanyList(String custId,Date searchDate);

    String executeUpdate(GroupAppManagerUpdatePermsForm updatePermList) throws ParseException, SQLException;
}
