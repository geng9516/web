package jp.smartcompany.admin.groupmanager.logic.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.admin.groupmanager.dto.GroupManagerGroupListDTO;
import jp.smartcompany.admin.groupmanager.logic.GroupManagerLogic;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.appcontrol.business.AppAuthInfoBusiness;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
import jp.smartcompany.job.modules.core.service.IMastGroupService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.core.util.PsSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupManagerLogicImpl implements GroupManagerLogic {

    private final IMastGroupService iMastGroupService;
    private final PsSearchCompanyUtil psSearchCompanyUtil;
    private final AppAuthInfoBusiness appAuthInfoBusiness;

    // 有効なグループリスト情報取得
    public List<GroupManagerGroupListDTO> getValidGroupList(PsDBBean psDBBean,Date searchDate, String systemId){
        if (StrUtil.isBlank(systemId)){
            systemId = appAuthInfoBusiness.getSystemCode(psDBBean.getSiteId(), psDBBean.getAppId());
        }
        if (searchDate==null) {
            searchDate = DateUtil.date();
        }
        List<String> companyList = psSearchCompanyUtil.getCompList(searchDate);
        String searchDateStr = SysUtil.transDateToString(searchDate);
        PsSession session = (PsSession) ContextUtil.getHttpRequest().getSession().getAttribute(Constant.PS_SESSION);
        return iMastGroupService.selectValidGroup(session.getLoginCustomer(),systemId,session.getLanguage(),searchDateStr,companyList);
    }

}
