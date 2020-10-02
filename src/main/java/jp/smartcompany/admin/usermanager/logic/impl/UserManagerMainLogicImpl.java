package jp.smartcompany.admin.usermanager.logic.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jp.smartcompany.admin.usermanager.dto.UserManagerListDTO;
import jp.smartcompany.admin.usermanager.logic.UserManagerMainLogic;
import jp.smartcompany.boot.util.PageQuery;
import jp.smartcompany.boot.util.PageUtil;
import jp.smartcompany.boot.util.SecurityUtil;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import jp.smartcompany.job.modules.core.service.IMastAccountService;
import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("userManagerMainLogic")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserManagerMainLogicImpl implements UserManagerMainLogic {

    private final PsSearchCompanyUtil searchCompanyUtil;
    private final IMastEmployeesService mastEmployeesService;
    private final IMastAccountService mastAccountService;

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
             case 1: // 全て
                 pageResult = mastEmployeesService.selectMainAllList(pageQuery,custId,language,companyId,companyList);
                 break;
             case 2: // ロックアウトされているユーザ
                 pageResult = mastEmployeesService.selectMainLockoutList(pageQuery,custId,language,companyId,companyList);
                 break;
             case 3: // 現在有効なユーザ
                 pageResult = mastEmployeesService.selectMainValidList(pageQuery,custId,language,companyId,companyList);
                 break;
             case 4: // 入社前
                 pageResult = mastEmployeesService.selectMainBeforeJoinList(pageQuery,custId,language,companyId,companyList);
                 break;
             case 5: //入社後未登録
                 pageResult = mastEmployeesService.selectMainAfterJoinList(pageQuery,custId,language,companyId,companyList);
                 break;
             case 6: // 退職後未削除
                 pageResult = mastEmployeesService.selectMainAfterRetireList(pageQuery,custId,language,companyId,companyList);
                 break;
             case 7: // 社員番号
                 pageResult = mastEmployeesService.selectMainEmpIdList(pageQuery,custId,language,companyId,companyList,empId);
                 break;
             case 8: // 部署
                 pageResult = mastEmployeesService.selectMainSectionList(pageQuery,custId,language,companyList,sectionCompanyId,sectionId);
                 break;
             case 9: // 氏名
                 pageResult = mastEmployeesService.selectMainEmpNameList(pageQuery,custId,language,companyId,companyList,empName);
                 break;
             case 10: // 管理ツールユーザ
                 pageResult = mastEmployeesService.selectMainAdminList(pageQuery,custId,language,companyId,companyList);
             default: // 默认查询ロックアウトされているユーザ
                 pageResult = mastEmployeesService.selectMainLockoutList(pageQuery,custId,language,companyId,companyList);
                 break;
         }
         return new PageUtil(pageResult);
     }

     @Override
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

}
