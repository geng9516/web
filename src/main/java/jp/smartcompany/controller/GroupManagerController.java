package jp.smartcompany.controller;

import jp.smartcompany.admin.groupmanager.logic.GroupManagerLogic;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("groups")
    public Map<String,Object> getValidGroupList(
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            @RequestParam(value="searchDate",required = false) Date searchDate,
            @RequestParam(value="systemId",required = false) String systemId) {
        return groupManagerLogic.getManagerGroupList(psDBBean,searchDate,systemId);
    }

}
