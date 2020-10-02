package jp.smartcompany.admin.usermanager.logic.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jp.smartcompany.admin.usermanager.dto.UserManagerListDTO;
import jp.smartcompany.admin.usermanager.logic.UserManagerMainLogic;
import jp.smartcompany.boot.util.PageQuery;
import jp.smartcompany.boot.util.PageUtil;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
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

         Integer searchType = (Integer)conditions.get("searchType");
         String companyId = (String)conditions.get("companyId");
         if (searchType == null) {
            searchType = 1;
         }
         if (StrUtil.isBlank(companyId)) {
             companyId = "01";
         }
         IPage<UserManagerListDTO> pageResult=null;
         switch(searchType) {
             /**
              * 全て
              */
             case 1:
                 pageResult = mastEmployeesService.selectMainAllList(new PageQuery<UserManagerListDTO>().getPage(conditions),custId,language,companyId,companyList);
                 break;
             default:
                 break;
         }
         return new PageUtil(pageResult);
     }

}
