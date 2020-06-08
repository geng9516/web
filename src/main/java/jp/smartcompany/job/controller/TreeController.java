package jp.smartcompany.job.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import jp.smartcompany.job.common.GlobalResponse;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 部門ツリー
 * @author Xiao Wenpeng
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("sys/tree")
public class TreeController {

    @GetMapping("admin/orglist")
    public GlobalResponse orgTree(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        psDBBean.requestHash.put("SiteId", TmgUtil.Cs_SITE_ID_TMG_ADMIN);
        TmgReferList referList = new TmgReferList(psDBBean, "TmgSample", baseDate, TmgReferList.TREEVIEW_TYPE_LIST_SEC, true,
                true, false, false, true);
        return GlobalResponse.data(referList.getJSONArrayForOrgTree());
    }

    @GetMapping("admin/emplist")
    public GlobalResponse empTree(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        psDBBean.requestHash.put("SiteId", TmgUtil.Cs_SITE_ID_TMG_ADMIN);
        TmgReferList referList = new TmgReferList(psDBBean, "TmgSample", baseDate, TmgReferList.TREEVIEW_TYPE_LIST_SEC, true,
                true, false, false, true);
        return GlobalResponse.data(referList.getJSONArrayForEmpList());
    }

}
