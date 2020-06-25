package jp.smartcompany.admin.groupappmanager.logic;

import jp.smartcompany.admin.groupappmanager.vo.GroupAppManagerTableLayout;

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

}
