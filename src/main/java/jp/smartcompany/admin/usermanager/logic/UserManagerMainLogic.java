package jp.smartcompany.admin.usermanager.logic;

import jp.smartcompany.admin.usermanager.form.UserManagerEditEndForm;
import jp.smartcompany.admin.usermanager.form.ShowLimitDateForm;
import jp.smartcompany.boot.util.PageUtil;
import jp.smartcompany.job.modules.core.pojo.entity.ConfSyscontrolDO;

import java.util.Map;
import java.util.List;

public interface UserManagerMainLogic {

    /**
        サイトID siteId
        法人コード companyId
        表示するページ page
        表示件数 limit
        排序字段 sidx
        排序方式 order
        是否需要返回数据总数 sum
        部署ID sectionId
        社員番号 empId
        氏名条件 empName
        部署条件(法人コード) sectionCompanyId
        絞り込み区分 searchType
     */
    PageUtil search(Map<String,Object> params);

    /**
     * 解锁用户
     * @param userIds
     */
    void unLock(List<String> userIds);

    /**
     * パスワードポリシーリスト　設定
     */
    List<ConfSyscontrolDO> passwordPolicy();

    void updatePolicy(List<ConfSyscontrolDO> controlList);

    Map<String,Object> showChangeEndDate(ShowLimitDateForm form);
    /**
     * 利用終了日 変更
     */
    void changeEndDate(UserManagerEditEndForm dto);

    Map<String,Object> showChangeStartDate(ShowLimitDateForm form);
}
