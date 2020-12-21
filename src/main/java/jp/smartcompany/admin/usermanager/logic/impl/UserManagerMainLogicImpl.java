package jp.smartcompany.admin.usermanager.logic.impl;

import cn.hutool.cache.impl.LRUCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jp.smartcompany.admin.usermanager.dto.UserManagerListDTO;
import jp.smartcompany.admin.usermanager.logic.UserManagerEditCommonLogic;
import jp.smartcompany.admin.usermanager.logic.UserManagerMainLogic;
import jp.smartcompany.boot.util.*;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
import jp.smartcompany.job.modules.core.pojo.entity.ConfSyscontrolDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import jp.smartcompany.job.modules.core.service.IConfSyscontrolService;
import jp.smartcompany.job.modules.core.service.IMastAccountService;
import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("userManagerMainLogic")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserManagerMainLogicImpl implements UserManagerMainLogic {

    private final PsSearchCompanyUtil searchCompanyUtil;
    private final IMastEmployeesService mastEmployeesService;
    private final IMastAccountService mastAccountService;
    private final IConfSyscontrolService confSyscontrolService;
    private final UserManagerEditCommonLogic userManagerEditCommonLogic;
    private final LRUCache<Object,Object> lruCache;
    /**
     * 法人リスト取得
     * @param conditions 検索条件
     * @return PageUtil 検索結果
     */
    @Override
     public PageUtil search(Map<String,Object> conditions) {
         Date date = DateUtil.date();
         String custId = "01";
         String language = "ja";
         List<String> companyList = searchCompanyUtil.getCompList(date);

         // 设置默认查询参数
         String sSearchType = (String)conditions.get("searchType");
         String companyId = (String)conditions.get("companyId");
         String empId = (String)conditions.get("empId");
         String sectionId = (String)conditions.get("sectionId");
         String sectionCompanyId = (String)conditions.get("sectionCompanyId");
         String empName = (String)conditions.get("empName");
         int searchType = 0;
         if (StrUtil.isNotBlank(sSearchType)) {
            searchType = Integer.parseInt(sSearchType);
         }
         if (StrUtil.isBlank(companyId)) {
             companyId = "01";
         }

         IPage<UserManagerListDTO> pageResult;
         IPage<UserManagerListDTO> pageQuery = new PageQuery<UserManagerListDTO>().getPage(conditions);
         switch(searchType) {
             // http://localhost:6879/sys/usermanager/search?psSite=Admin&searchType=1
             case 1: // 全て
                 pageResult = mastEmployeesService.selectMainAllList(pageQuery,custId,language,companyId,companyList);
                 break;
             // http://localhost:6879/sys/usermanager/search?psSite=Admin&searchType=2
             case 2: // ロックアウトされているユーザ
                 pageResult = mastEmployeesService.selectMainLockoutList(pageQuery,custId,language,companyId,companyList);
                 break;
             // http://localhost:6879/sys/usermanager/search?psSite=Admin&searchType=3
             case 3: // 現在有効なユーザ
                 pageResult = mastEmployeesService.selectMainValidList(pageQuery,custId,language,companyId,companyList);
                 break;
             // http://localhost:6879/sys/usermanager/search?psSite=Admin&searchType=4
             case 4: // 入社前
                 pageResult = mastEmployeesService.selectMainBeforeJoinList(pageQuery,custId,language,companyId,companyList);
                 break;
             // http://localhost:6879/sys/usermanager/search?psSite=Admin&searchType=5&companyId=01
             case 5: //入社後未登録
                 pageResult = mastEmployeesService.selectMainAfterJoinList(pageQuery,custId,language,companyId,companyList);
                 break;
             // http://localhost:6879/sys/usermanager/search?psSite=Admin&searchType=6&companyId=01
             case 6: // 退職後未削除
                 pageResult = mastEmployeesService.selectMainAfterRetireList(pageQuery,custId,language,companyId,companyList);
                 break;
             // http://localhost:6879/sys/usermanager/search?psSite=Admin&searchType=7&companyId=01&empId=4640
             case 7: // 職員番号
                 pageResult = mastEmployeesService.selectMainEmpIdList(pageQuery,custId,language,companyId,companyList,empId);
                 break;
             // http://localhost:6879/sys/usermanager/search?psSite=Admin&searchType=8&sectionCompanyId=01&sectionId=201000000000
             case 8: // 部署
                 pageResult = mastEmployeesService.selectMainSectionList(pageQuery,custId,language,companyList,sectionCompanyId,sectionId);
                 break;
             // http://localhost:6879/sys/usermanager/search?psSite=Admin&searchType=9&companyId=01&empName=464
             case 9: // 氏名
                 pageResult = mastEmployeesService.selectMainEmpNameList(pageQuery,custId,language,companyId,companyList,empName);
                 break;
             // http://localhost:6879/sys/usermanager/search?psSite=Admin&searchType=10&companyId=01
             case 10: // 管理ツールユーザ
                 pageResult = mastEmployeesService.selectMainAdminList(pageQuery,custId,language,companyId,companyList);
                 break;
             default: // 默认查询ロックアウトされているユーザ
                 pageResult = mastEmployeesService.selectMainLockoutList(pageQuery,custId,language,companyId,companyList);
                 break;
         }
         pageResult.getRecords().forEach(item -> item.setStatus(userManagerEditCommonLogic.getStatus(item)));
         return new PageUtil(pageResult);
     }

     @Override
     @Transactional
     public void unLock(List<String> userIds) {
        List<MastAccountDO> accountList = mastAccountService.list(new QueryWrapper<MastAccountDO>().in("MA_CUSERID",userIds));
        String userId = SecurityUtil.getUserId();
        Date date = DateUtil.date();
        accountList.forEach(account-> {
            account.setMaNpasswordlock(0);
            account.setMaNretrycounter(0);
            account.setMaCmodifieruserid(userId);
            account.setMaDmodifieddate(date);
        });
        mastAccountService.updateBatchById(accountList);
    }

    @Override
    public List<ConfSyscontrolDO> passwordPolicy() {
        List<String> props = CollUtil.newArrayList(UserManagerEditCommonLogic.PROP_PW_VALID_PERIOD,
                UserManagerEditCommonLogic.PROP_PW_MAX_LEN,
                UserManagerEditCommonLogic.PROP_PW_MIN_LEN,
                UserManagerEditCommonLogic.PROP_LOGIN_RETRY,
                UserManagerEditCommonLogic.PROP_CHANGE_PW_LIMITATION_COUNT);
        return confSyscontrolService.list(new QueryWrapper<ConfSyscontrolDO>().in("CS_CPROPERTYNAME", props).orderByDesc("CS_CPROPERTYNAME"));
    }

    @Override
    @Transactional
    public void updatePolicy(List<ConfSyscontrolDO> controlList) {
        confSyscontrolService.updateBatchById(controlList);
        lruCache.remove(ScCacheUtil.SYSTEM_PROPERTY_MAP);
    }


}
