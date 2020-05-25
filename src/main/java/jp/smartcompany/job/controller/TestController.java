package jp.smartcompany.job.controller;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.job.common.GlobalResponse;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("test")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestController {

    private final PsDBBean psDBBean;
    private String baseDate = DateUtil.format(DateUtil.date(),TmgReferList.DEFAULT_DATE_FORMAT);

    @GetMapping("rf1")
    @ResponseBody
    public GlobalResponse setReferList1() throws Exception {
        psDBBean.requestHash.put("SiteId", TmgUtil.Cs_SITE_ID_TMG_ADMIN);
        TmgReferList referList = new TmgReferList(psDBBean, "TmgSample", baseDate, TmgReferList.TREEVIEW_TYPE_LIST_SEC, true,
                true, false, false, true);
        String orgTree = referList.getJSONArrayForOrgTree();
        String divisionTree = referList.getJSONArrayForDivisionTree();
        String empList = referList.getJSONArrayForEmpList();
        String memberList = referList.getJSONArrayForMemberList();
        String groupBySection = referList.getJSONArrayForMemberListGroupBySection();
        String groupByGroup = referList.getJSONArrayForMemberListGroupByGroup();
        String groupList = referList.getJSONArrayForGroupList();
        String sectionList = referList.getJSONArrayForSectionList();
        System.out.println(orgTree);
        System.out.println(divisionTree);
        System.out.println(empList);
        System.out.println(memberList);
        System.out.println(groupBySection);
        System.out.println(groupByGroup);
        System.out.println(groupList);
        System.out.println(sectionList);
        return GlobalResponse.ok();
    }

}
