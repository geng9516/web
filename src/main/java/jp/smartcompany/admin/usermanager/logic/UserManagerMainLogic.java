package jp.smartcompany.admin.usermanager.logic;

import jp.smartcompany.boot.util.PageUtil;

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

}
