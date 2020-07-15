package jp.smartcompany.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.admin.groupmanager.logic.GroupManagerGroupEditLogic;
import jp.smartcompany.admin.groupmanager.logic.GroupManagerLogic;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * グループ定義
 * @author Xiao Wenpeng
 */
@RestController
@RequestMapping("sys/groupmanager")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupManagerController {

    private final GroupManagerLogic groupManagerLogic;
    private final GroupManagerGroupEditLogic groupManagerGroupEditLogic;

    // グループ定義:閲覧画面リスト
    @GetMapping("groups")
    public Map<String,Object> getValidGroupList(
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            @RequestParam(value="searchDate",required = false) Date searchDate,
            @RequestParam(value="systemId",required = false) String systemId) {
        return groupManagerLogic.getManagerGroupList(psDBBean,searchDate,systemId);
    }

    @GetMapping("detail")
    public Map<String,Object> getGroupDetail(
            @RequestParam(value="searchDate",required = false) Date searchDate,
            @RequestParam(value="systemId",required = false) String systemId,
            @RequestParam(value="groupId") String groupId
    ) throws ParseException {
        if (searchDate == null) {
            searchDate = DateUtil.date();
        }
        if (StrUtil.isBlank(systemId)){
            systemId="01";
        }
        return groupManagerGroupEditLogic.detail(searchDate,systemId, groupId);
    }

}
