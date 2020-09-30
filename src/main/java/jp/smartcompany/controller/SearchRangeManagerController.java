package jp.smartcompany.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.admin.searchrangemanager.logic.SearchRangeManagerLogic;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * 检索对象范围controller
 * @author Xiao Wenpeng
 */
@RestController
@RequestMapping("sys/searchrangemanager")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SearchRangeManagerController {

    private final SearchRangeManagerLogic searchRangeManagerLogic;

    // http://localhost:6879/sys/searchrangemanager?isAll=true&psSite=Admin&psSecurityDate=2020/09/29&date=2020/06/29&siteId=TMG_PERM
    @GetMapping
    public Map<String,Object> list(
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            @RequestParam Map<String,Object> params
    ) {
        String dateStr = (String)params.get("date");
        Date date;
        if (StrUtil.isBlank(dateStr)){
            date = DateUtil.date();
        }else {
            date = SysUtil.transStringToDate(dateStr);
        }
        String groupId = (String)params.get("groupId");
        String isAllStr = (String)params.get("isALl");
        if (StrUtil.isBlank(isAllStr)) {
            isAllStr = "false";
        }
        boolean isAll = Boolean.parseBoolean(isAllStr);
        String psSiteId = (String)params.get("siteId");
        String psAppId = (String)params.get("appId");
        return searchRangeManagerLogic.listRangeTable(
                psDBBean.getSystemCode(),
                date,
                groupId,
                psSiteId,
                psAppId,
                psDBBean.getLanguage(),
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                isAll
        );
    }
}
