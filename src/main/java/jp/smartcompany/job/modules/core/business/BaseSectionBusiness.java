package jp.smartcompany.job.modules.core.business;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.common.Constant;
import jp.smartcompany.job.common.GlobalException;
import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.CoreError;
import jp.smartcompany.job.modules.core.pojo.bo.BaseSectionBO;
import jp.smartcompany.job.modules.core.pojo.bo.GroupBaseSectionBO;
import jp.smartcompany.job.modules.core.pojo.bo.LoginGroupBO;
import jp.smartcompany.job.modules.core.service.IMastGroupbasesectionService;
import jp.smartcompany.job.modules.core.util.PsSession;
import jp.smartcompany.job.util.ShiroUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Xiao Wenpeng
 */
@Service(CoreBean.Business.BASE_SECTION)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BaseSectionBusiness {

    private final HttpSession httpSession;
    private final IMastGroupbasesectionService iMastGroupbasesectionService;

    public void getBaseSectionList() {
        BaseSectionBO baseSection = getBaseSection(DateUtil.date());
    }

    public BaseSectionBO getBaseSection(Date date) {
        String userId = ShiroUtil.getUserId();
        PsSession psSession = (PsSession)httpSession.getAttribute(Constant.LOGIN_INFO);
        Map<String, List<LoginGroupBO>> hmGroups = psSession.getLoginGroups();
        if (CollUtil.isEmpty(hmGroups)){
            throw new GlobalException(CoreError.LOGIN_GROUP_NOT_FOUND);
        }
        Map<String, Map<String, String>> hashMapComp = MapUtil.newHashMap(true);
        Map<String, Map<String, String>> hashMapGroup = MapUtil.newHashMap(true);
        hmGroups.forEach((key,value)->{
            queryBaseSection(userId, key, date,
                    hashMapComp, hashMapGroup);
        });
        BaseSectionBO bsi = new BaseSectionBO();
        bsi.setHmCompany(hashMapComp);
        bsi.setHmGroup(hashMapGroup);
        return bsi;
    }

    private void queryBaseSection(String sCustomer, String sSystem, Date date,
                                  Map<String, Map<String, String>> hashMapComp,
                                  Map<String, Map<String, String>> hashMapGroup) {
        Map<String, List<GroupBaseSectionBO>> lhmComp = MapUtil.newHashMap(true);
        Map<String, List<GroupBaseSectionBO>> lhmGroup = MapUtil.newHashMap(true);
        queryBaseSectionMaster(sCustomer, sSystem, date, lhmComp, lhmGroup);
//        hashMapComp.put(sSystem,
//                createBaseSectionInfoByCompany(sCustomer, date, lhmComp));
//        hashMapGroup.put(sSystem,
//                createBaseSectionInfoByGroup(sCustomer, date, lhmGroup));
    }

    private void queryBaseSectionMaster(String sCustomer,
                                        String sSystem,
                                        Date date,
                                        Map<String, List<GroupBaseSectionBO>> lhmComp,
                                        Map<String, List<GroupBaseSectionBO>> lhmGroup) {
        PsSession psSession = (PsSession)httpSession.getAttribute(Constant.LOGIN_INFO);
        List<LoginGroupBO> hmGroups = psSession.getLoginGroups().get(sSystem);
        for (LoginGroupBO hmGroup : hmGroups) {
            String groupCode = hmGroup.getGroupCode();
            List<GroupBaseSectionBO> mastBaseSectionList =
                    iMastGroupbasesectionService.getBaseSectionByGroupCode(sCustomer, sSystem, groupCode, date);
        }
    }

}
