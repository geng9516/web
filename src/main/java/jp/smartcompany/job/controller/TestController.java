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
        System.out.println(referList.getJSONArrayForDivisionTree());
        System.out.println(referList.getJSONArrayForGroupList());
        System.out.println(referList.getJSONArrayForEmpList());
        System.out.println(referList.getJSONArrayForMemberList());
        return GlobalResponse.data(referList.getJSONArrayForOrgTree());
    }

}
