package jp.smartcompany.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerPermissionDTO;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.job.modules.core.service.IMastGroupapppermissionService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("sys/test")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestController {

    private String baseDate = DateUtil.format(DateUtil.date(),TmgReferList.DEFAULT_DATE_FORMAT);
    private final IMastGroupapppermissionService iMastGroupapppermissionService;

    // 勤怠管理サイト Test  11
    // TmgTreeViewForAdminEmp.jsp
    @GetMapping("rf1")
    @ResponseBody
    public GlobalResponse setReferList1(@RequestAttribute("BeanName") PsDBBean psDBBean, @RequestParam("type") Integer type,@RequestParam("site") String siteId) throws Exception {
        psDBBean.getRequestHash().put("SiteId", siteId);
        psDBBean.getRequestHash().put("AppId","AttendanceBook");
        TmgReferList referList = new TmgReferList(psDBBean, psDBBean.getAppId(), baseDate, type, true,
                                 true, false, false, true);
        if(type == TmgReferList.TREEVIEW_TYPE_LIST_SEC){
            log.info("---TREEVIEW_TYPE_LIST_SEC---");
            String jsonOrgStr = referList.getJSONArrayForOrgTree();
            if (StrUtil.isNotBlank(jsonOrgStr)) {
                JSONArray jsonOrg = JSONUtil.parseArray(jsonOrgStr);
                System.out.println(jsonOrg);
            } else {

            }
        } else if (type==TmgReferList.TREEVIEW_TYPE_EMP) {
            log.info("---TREEVIEW_TYPE_EMP---");
            String memberListStr = referList.getJSONArrayForMemberList();
            if (StrUtil.isNotBlank(memberListStr)) {
                JSONArray memberList = JSONUtil.parseArray(memberListStr);
                System.out.println(memberList);
            } else {
                System.out.println("---");
            }
            String sectionMemberListStr = referList.getJSONArrayForMemberListGroupBySection();
            if (StrUtil.isNotBlank(sectionMemberListStr)) {
                JSONArray sectionMemberList =  JSONUtil.parseArray(sectionMemberListStr);
                System.out.println(sectionMemberList);
            } else {
            }
            String groupMemberListStr = referList.getJSONArrayForMemberListGroupByGroup();
            if (StrUtil.isNotBlank(groupMemberListStr)) {
                JSONArray groupMemberList = JSONUtil.parseArray(groupMemberListStr);
                System.out.println(groupMemberList);
            } else {
            }
        } else if (type==TmgReferList.TREEVIEW_TYPE_LIST) {
            System.out.println("---TREEVIEW_TYPE_LIST---");
            String groupListStr = referList.getJSONArrayForGroupList();
            if (StrUtil.isNotBlank(groupListStr)) {
                JSONArray groupList = JSONUtil.parseArray(groupListStr);
                System.out.println(groupList);
            } else {
            }
            String sectionListStr = referList.getJSONArrayForSectionList();
            if (StrUtil.isNotBlank(sectionListStr)) {
                JSONArray sectionList = JSONUtil.parseArray(sectionListStr);
                System.out.println(sectionList);
            } else {
            }
        } else if (type == TmgReferList.TREEVIEW_TYPE_DIVLIST) {
            String divisionTreeStr = referList.getJSONArrayForDivisionTree();
            if (StrUtil.isNotBlank(divisionTreeStr)) {
                JSONArray divisionTree = JSONUtil.parseArray(divisionTreeStr);
                System.out.println(divisionTree);
            } else {
            }
        }
        return GlobalResponse.ok();
    }

    @GetMapping("perm")
    @ResponseBody
    public List<GroupAppManagerPermissionDTO> getUserPerms() {
        return iMastGroupapppermissionService.selectPermissionList("01",DateUtil.date(), CollUtil.newArrayList("1","2","4","7"),null,null,"ja");
    }

}
