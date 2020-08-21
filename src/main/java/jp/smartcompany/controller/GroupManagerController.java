package jp.smartcompany.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.admin.groupmanager.dto.GroupManagerDeleteDTO;
import jp.smartcompany.admin.groupmanager.dto.GroupManagerEditDTO;
import jp.smartcompany.admin.groupmanager.logic.GroupManagerDateEditLogic;
import jp.smartcompany.admin.groupmanager.logic.GroupManagerGroupEditLogic;
import jp.smartcompany.admin.groupmanager.logic.GroupManagerLogic;
import jp.smartcompany.framework.component.entity.EmployeeInfoSearchEntity;
import jp.smartcompany.framework.component.logic.EmployeeInfoSearchLogic;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * システム管理menu-グループ定義
 * @author Xiao Wenpeng
 */
@RestController
@RequestMapping("sys/groupmanager")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupManagerController {

    private final GroupManagerLogic groupManagerLogic;
    private final GroupManagerGroupEditLogic groupManagerGroupEditLogic;
    private final EmployeeInfoSearchLogic employeeInfoSearchLogic;
    private final GroupManagerDateEditLogic groupManagerDateEditLogic;

    // http://localhost:6879/sys/groupmanager/groups?psSite=Admin
    // グループ定義:閲覧画面リスト
    @GetMapping("groups")
    public Map<String,Object> getValidGroupList(
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            @RequestParam(value="searchDate",required = false) Date searchDate,
            @RequestParam(value="systemId",required = false) String systemId) {
        return groupManagerLogic.getManagerGroupList(psDBBean,searchDate,systemId);
    }

    @GetMapping("detail")
    // http://localhost:6879/sys/groupmanager/detail?groupId=010000&psSite=Admin
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

    /*******弹窗相关路由*********/
    // http://localhost:6879/sys/groupmanager/empsearch?searchWord=464&psSite=Admin
    @GetMapping("empsearch")
    public List<EmployeeInfoSearchEntity> searchEmpList(
      @RequestParam(value="searchWord",required = false) String searchWord,
      @RequestParam(value="searchWordConve",required = false) String searchWordConve,
      @RequestParam(value="searchWordEnglish",required = false) String searchWordEnglish,
      @RequestParam(value="serchRange",required = false,defaultValue = "0") String searchRange,
      @RequestParam(value = "searchFlg",required = false,defaultValue = "zai") String searchFlg,
      @RequestParam(value="companyId",required = false,defaultValue = "01") String companyId,
      @RequestParam(value="targetComp",required = false,defaultValue = "01") String targetComp,
      // hidden⇔requestとして保持し続けるオブジェクト (本務兼務区分)
      @RequestParam(value="additionalRole",required = false,defaultValue = "0") String ifKeyorAdditionalRole,
      @RequestParam(value="targetDept",required = false) String targetDept,
      @RequestParam(value="type",required = false, defaultValue = "1") Integer type) {
      return employeeInfoSearchLogic.searchEmpList(searchWord,searchWordConve,searchWordEnglish,searchRange,searchFlg,companyId,targetComp,ifKeyorAdditionalRole,targetDept,type);
    }

    @PostMapping("update")
    public String executeUpdate(@RequestBody GroupManagerEditDTO dto) {
        groupManagerGroupEditLogic.update(dto);
        return "変更成功";
    }

    /**
     * ===========================================
     * GroupManagerDateEditAction 整理接口
     * ===========================================
      */

    // グループ定義 一覧(指定基準日)画面にて表示する情報を取得します。
    // http:localhost:6879/sys/groupmanager/editlist?psSite=Admin
    @GetMapping("editlist")
    public Map<String,Object> editList(
            @RequestParam(value="searchDate",required = false) String searchDate,
            @RequestParam(value="systemId",required = false) String systemId) {
        return groupManagerDateEditLogic.editListHandler(searchDate,systemId);
    }

    // 指定されたグループを削除
    @PostMapping("delete")
    public String delete(@Valid @RequestBody GroupManagerDeleteDTO dto) {
        return groupManagerDateEditLogic.deleteHandler(dto);
    }

    // グループ全体の優先順位を更新
    @PostMapping("update")
    public String changeSort(@Valid @NotEmpty @RequestBody List<String> groupIds) {
        return groupManagerDateEditLogic.sortHandler(groupIds,null);
    }

}
