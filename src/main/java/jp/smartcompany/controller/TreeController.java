package jp.smartcompany.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 部門ツリー
 * @author Xiao Wenpeng
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@Slf4j
@RequestMapping("sys/tree")
public class TreeController {

    @GetMapping
    public GlobalResponse orgTree(@RequestAttribute("BeanName") PsDBBean psDBBean,
                                  @RequestParam("type") Integer type,
                                  @RequestParam(value = "useRecordDate",required = false,defaultValue = "true") Boolean useRecordDate,
                                  @RequestParam(value="useManage",required = false,defaultValue = "false") Boolean useManage,
                                  @RequestParam(value="joinEmployees",required = false,defaultValue = "true") Boolean joinEmployees,
                                  @RequestParam(value="useSearch",required = false,defaultValue = "true") Boolean useSearch,
                                  @RequestParam(value="useTargetDate",required = false,defaultValue = "false")Boolean useTargetDate
                                  ) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
//        psDBBean.getRequestHash().put("SiteId", TmgUtil.Cs_SITE_ID_TMG_ADMIN);
        log.info("【初始化referList参数:{},{},{}】",psDBBean.getAppId(),type,psDBBean.getSiteId());
        TmgReferList referList = new TmgReferList(psDBBean, psDBBean.getAppId(), baseDate, type, joinEmployees,
                useRecordDate, useManage, useTargetDate, useSearch);
        GlobalResponse globalResponse = GlobalResponse.ok();
        globalResponse.put("type",type);
        if (useRecordDate) {
            globalResponse.put("recordDate", referList.getRecordDate());
        }
        if (useTargetDate){
            globalResponse.put("targetDate",referList.getTargetDate());
        }
        if(type == TmgReferList.TREEVIEW_TYPE_LIST_SEC){
            log.info("---TREEVIEW_TYPE_LIST_SEC---");
            String jsonOrgStr = referList.getJSONArrayForOrgTree();
            if (StrUtil.isNotBlank(jsonOrgStr)) {
                JSONArray jsonOrg = JSONUtil.parseArray(jsonOrgStr);
                globalResponse.put("orgList",jsonOrg);
            } else {
                globalResponse.put("orgList",jsonOrgStr);
            }
        } else if (type==TmgReferList.TREEVIEW_TYPE_EMP) {
            log.info("---TREEVIEW_TYPE_EMP---");
            String memberListStr = referList.getJSONArrayForMemberList();
            if (StrUtil.isNotBlank(memberListStr)) {
                JSONArray memberList = JSONUtil.parseArray(memberListStr);
                globalResponse.put("memberList",memberList);
            } else {
                globalResponse.put("memberList",memberListStr);
            }
            String sectionMemberListStr = referList.getJSONArrayForMemberListGroupBySection();
            if (StrUtil.isNotBlank(sectionMemberListStr)) {
                JSONArray sectionMemberList =  JSONUtil.parseArray(sectionMemberListStr);
                globalResponse.put("sectionMemberList",sectionMemberList);
            } else {
                globalResponse.put("sectionMemberList",sectionMemberListStr);
            }
            String groupMemberListStr = referList.getJSONArrayForMemberListGroupByGroup();
            if (StrUtil.isNotBlank(groupMemberListStr)) {
                JSONArray groupMemberList = JSONUtil.parseArray(groupMemberListStr);
                globalResponse.put("groupMemberList",groupMemberList);
            } else {
                globalResponse.put("groupMemberList",groupMemberListStr);
            }
        } else if (type==TmgReferList.TREEVIEW_TYPE_LIST) {
            System.out.println("---TREEVIEW_TYPE_LIST---");
            String groupListStr = referList.getJSONArrayForGroupList();
            if (StrUtil.isNotBlank(groupListStr)) {
                JSONArray groupList = JSONUtil.parseArray(groupListStr);
                globalResponse.put("groupList",groupList);
            } else {
                globalResponse.put("groupList", groupListStr);
            }
            String sectionListStr = referList.getJSONArrayForSectionList();
            if (StrUtil.isNotBlank(sectionListStr)) {
                JSONArray sectionList = JSONUtil.parseArray(sectionListStr);
                globalResponse.put("sectionList",sectionList);
            } else {
                globalResponse.put("sectionList",sectionListStr);
            }
        } else if (type == TmgReferList.TREEVIEW_TYPE_DIVLIST) {
            String divisionTreeStr = referList.getJSONArrayForDivisionTree();
            if (StrUtil.isNotBlank(divisionTreeStr)) {
                JSONArray divisionTree = JSONUtil.parseArray(divisionTreeStr);
                globalResponse.put("divisionTree", divisionTree);
            } else {
                globalResponse.put("divisionTree",divisionTreeStr);
            }
        }
        return globalResponse;
    }

    @GetMapping("emplist")
    public GlobalResponse empTree(@RequestAttribute("BeanName") PsDBBean psDBBean,
                                  @RequestParam("type") Integer type,
                                  @RequestParam(value = "useRecordDate",required = false,defaultValue = "true") Boolean useRecordDate,
                                  @RequestParam(value="useManage",required = false,defaultValue = "false") Boolean useManage,
                                  @RequestParam(value="joinEmployees",required = false,defaultValue = "true") Boolean joinEmployees,
                                  @RequestParam(value="useTargetDate",required = false,defaultValue = "false")Boolean useTargetDate) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
//        psDBBean.getRequestHash().put("SiteId", TmgUtil.Cs_SITE_ID_TMG_ADMIN);
        TmgReferList referList = new TmgReferList(psDBBean, psDBBean.getAppId(), baseDate, type, joinEmployees,
                useRecordDate, useManage, useTargetDate, joinEmployees);
        GlobalResponse globalResponse = GlobalResponse.ok();
        globalResponse.put("type",type);
        if (useRecordDate) {
            globalResponse.put("recordDate", referList.getRecordDate());
        }
        if (useTargetDate){
            globalResponse.put("targetDate",referList.getTargetDate());
        }
        String empListStr = referList.getJSONArrayForEmpList();
        if (StrUtil.isNotBlank(empListStr)){
            JSONArray empList = JSONUtil.parseArray(empListStr);
            globalResponse.put("empList",empList);
        } else {
            globalResponse.put("empList",empListStr);
        }
        return globalResponse;
    }

}
