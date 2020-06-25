package jp.smartcompany.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerPermissionDTO;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.job.modules.core.pojo.bo.MenuGroupBO;
import jp.smartcompany.job.modules.core.pojo.bo.MenuBO;
import jp.smartcompany.job.modules.core.service.IMastGroupapppermissionService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public GlobalResponse getUserPerms() {
        List<GroupAppManagerPermissionDTO> tmgPermList = iMastGroupapppermissionService.selectPermissionList("01",DateUtil.date(), CollUtil.newArrayList("1","2","4","7"), TmgUtil.Cs_SITE_ID_TMG_PERM,null,"ja");
        List<GroupAppManagerPermissionDTO> tmgAdminList = iMastGroupapppermissionService.selectPermissionList("01",DateUtil.date(), CollUtil.newArrayList("1","2","4","7"), TmgUtil.Cs_SITE_ID_TMG_ADMIN,null,"ja");
        List<GroupAppManagerPermissionDTO> tmgInpList = iMastGroupapppermissionService.selectPermissionList("01",DateUtil.date(), CollUtil.newArrayList("1","2","4","7"), TmgUtil.Cs_SITE_ID_TMG_INP,null,"ja");
        List<GroupAppManagerPermissionDTO> adminList = iMastGroupapppermissionService.selectPermissionList("01",DateUtil.date(),CollUtil.newArrayList("1","2","4","7"),"Admin",null,"ja");

        // 加载topMenu Start
        List<GroupAppManagerPermissionDTO> topMenus = CollUtil.newArrayList();

        if (CollUtil.isNotEmpty(tmgPermList)) {
            List<GroupAppManagerPermissionDTO> permPermissionList = CollUtil.newArrayList();
            for (GroupAppManagerPermissionDTO groupAppManagerPermissionDTO : tmgPermList) {
                if (StrUtil.equals(groupAppManagerPermissionDTO.getType(),"1") && StrUtil.equals(groupAppManagerPermissionDTO.getMgpCobjectid(),TmgUtil.Cs_SITE_ID_TMG_PERM)) {
                       permPermissionList.add(groupAppManagerPermissionDTO);
                }
            }
            GroupAppManagerPermissionDTO permissionItem = null;
            for (GroupAppManagerPermissionDTO groupAppManagerPermissionDTO : permPermissionList) {
                if (StrUtil.equals(groupAppManagerPermissionDTO.getPermission(),"2")) {
                    permissionItem = null;
                    break;
                }
                if (StrUtil.equals(groupAppManagerPermissionDTO.getPermission(),"1") && permissionItem==null){
                     permissionItem = groupAppManagerPermissionDTO;
                }
            }
            if (permissionItem!=null) {
                topMenus.add(permissionItem);
            }
        }

        if (CollUtil.isNotEmpty(tmgAdminList)) {
            List<GroupAppManagerPermissionDTO> tmgAdminPermissionList = CollUtil.newArrayList();
            for (GroupAppManagerPermissionDTO groupAppManagerPermissionDTO : tmgAdminList) {
                if (StrUtil.equals(groupAppManagerPermissionDTO.getType(),"1") && StrUtil.equals(groupAppManagerPermissionDTO.getMgpCobjectid(),TmgUtil.Cs_SITE_ID_TMG_ADMIN)) {
                    tmgAdminPermissionList.add(groupAppManagerPermissionDTO);
                }
            }
            GroupAppManagerPermissionDTO permissionItem = null;
            for (GroupAppManagerPermissionDTO groupAppManagerPermissionDTO : tmgAdminPermissionList) {
                if (StrUtil.equals(groupAppManagerPermissionDTO.getPermission(),"2")) {
                    permissionItem = null;
                    break;
                }
                if (StrUtil.equals(groupAppManagerPermissionDTO.getPermission(),"1") && permissionItem==null){
                    permissionItem = groupAppManagerPermissionDTO;
                }
            }
            if (permissionItem!=null) {
                topMenus.add(permissionItem);
            }
        }

        if (CollUtil.isNotEmpty(adminList)) {
            List<GroupAppManagerPermissionDTO> adminPermissionList = CollUtil.newArrayList();
            for (GroupAppManagerPermissionDTO groupAppManagerPermissionDTO : adminList) {
                if (StrUtil.equals(groupAppManagerPermissionDTO.getType(),"1") && StrUtil.equals(groupAppManagerPermissionDTO.getMgpCobjectid(),"Admin")) {
                    adminPermissionList.add(groupAppManagerPermissionDTO);
                }
            }
            GroupAppManagerPermissionDTO permissionItem = null;
            for (GroupAppManagerPermissionDTO groupAppManagerPermissionDTO : adminPermissionList) {
                if (StrUtil.equals(groupAppManagerPermissionDTO.getPermission(),"2")) {
                    permissionItem = null;
                    break;
                }
                if (StrUtil.equals(groupAppManagerPermissionDTO.getPermission(),"1") && permissionItem==null){
                    permissionItem = groupAppManagerPermissionDTO;
                }
            }
            if (permissionItem!=null) {
                topMenus.add(permissionItem);
            }
        }

        if (CollUtil.isNotEmpty(tmgInpList)) {
            List<GroupAppManagerPermissionDTO> tmgInpPermissionList = CollUtil.newArrayList();
            for (GroupAppManagerPermissionDTO groupAppManagerPermissionDTO : tmgInpList) {
                if (StrUtil.equals(groupAppManagerPermissionDTO.getType(),"1") && StrUtil.equals(groupAppManagerPermissionDTO.getMgpCobjectid(),TmgUtil.Cs_SITE_ID_TMG_INP)) {
                    tmgInpPermissionList.add(groupAppManagerPermissionDTO);
                }
            }
            GroupAppManagerPermissionDTO permissionItem = null;
            for (GroupAppManagerPermissionDTO groupAppManagerPermissionDTO : tmgInpPermissionList) {
                if (StrUtil.equals(groupAppManagerPermissionDTO.getPermission(),"2")) {
                    permissionItem = null;
                    break;
                }
                if (StrUtil.equals(groupAppManagerPermissionDTO.getPermission(),"1") && permissionItem==null){
                    permissionItem = groupAppManagerPermissionDTO;
                }
            }
            if (permissionItem!=null) {
                topMenus.add(permissionItem);
            }
        }
        // 加载topMenu End
        List<MenuGroupBO> menuGroupList = CollUtil.newArrayList();
        for (GroupAppManagerPermissionDTO topMenu : topMenus) {
            // 加载主菜单
            MenuGroupBO menuGroupBO = new MenuGroupBO();
            MenuBO topMenuDO = new MenuBO();
            topMenuDO.setPageId(topMenu.getMgpCsite());
            topMenuDO.setPerms(topMenu.getMgpCobjectid());
            topMenuDO.setOrderNum(topMenu.getMtrNseq());
            topMenuDO.setUrl(topMenu.getMtrCurl2());
            topMenuDO.setJaName(topMenu.getObjectName());
            topMenuDO.setIcon(topMenu.getMtrIcon());
            topMenuDO.setType(topMenu.getType());
            menuGroupBO.setMenu(topMenuDO);
            // 加载二级导航
            List<GroupAppManagerPermissionDTO> appList = CollUtil.newArrayList();
            if (StrUtil.equals(topMenu.getMgpCsite(), TmgUtil.Cs_SITE_ID_TMG_INP)) {
                appList = tmgInpList;
            }
            if (StrUtil.equals(topMenu.getMgpCsite(), TmgUtil.Cs_SITE_ID_TMG_ADMIN)) {
                appList = tmgAdminList;
            }
            if (StrUtil.equals(topMenu.getMgpCsite(), TmgUtil.Cs_SITE_ID_TMG_PERM)) {
                appList = tmgPermList;
            }
            if (StrUtil.equals(topMenu.getMgpCsite(), "Admin")) {
                appList = adminList;
            }
            List<MenuBO> secondMenuList = CollUtil.newArrayList();
            List<GroupAppManagerPermissionDTO> secondAppList = appList.stream().filter(item -> StrUtil.equals(item.getType(), "3")).collect(Collectors.toList());
            secondAppList.forEach(secondApp -> {
                MenuBO secondMenuDO = new MenuBO();
                secondMenuDO.setPageId(secondApp.getMgpCsite());
                secondMenuDO.setPerms(secondApp.getMgpCobjectid());
                secondMenuDO.setOrderNum(secondApp.getMtrNseq());
                secondMenuDO.setUrl(secondApp.getMtrCurl2());
                secondMenuDO.setJaName(secondApp.getObjectName());
                secondMenuDO.setIcon(secondApp.getMtrIcon());
                secondMenuDO.setType(secondApp.getType());
                secondMenuList.add(secondMenuDO);
            });
            menuGroupBO.setSecondMenuList(secondMenuList);

            menuGroupList.add(menuGroupBO);
        }
        return GlobalResponse.ok(menuGroupList);
    }

}
