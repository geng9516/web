package jp.smartcompany.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.map.multi.ListValueMap;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.dialog.postselect.logic.PostGenericLogic;
import jp.smartcompany.framework.jsf.orgtree.dto.OrgTreeDTO;
import jp.smartcompany.framework.jsf.orgtree.logic.OrgTreeListLogic;
import jp.smartcompany.framework.jsf.orgtree.vo.OrgTreeVO;
import jp.smartcompany.job.modules.core.pojo.dto.TreeSearchConditionVO;
import jp.smartcompany.job.modules.core.pojo.entity.MastOrganisationDO;
import jp.smartcompany.job.modules.core.service.IMastOrganisationService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.core.util.PsSession;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 部門ツリー
 * @author Xiao Wenpeng
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("sys/tree")
public class TreeController {

    private final OrgTreeListLogic orgTreeListLogic;
    private final PostGenericLogic postGenericLogic;
    private final IMastOrganisationService organisationService;

    @GetMapping
    public GlobalResponse tree(@RequestAttribute("BeanName") PsDBBean psDBBean,
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
            if (StrUtil.equals(psDBBean.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_PERM)) {
                String jsonSectionStr = referList.getJSONArrayForSectionList();
                if (StrUtil.isNotBlank(jsonSectionStr)) {
                    JSONArray jsonOrg = JSONUtil.parseArray(jsonSectionStr);
                    globalResponse.put("sectionList", jsonOrg);
                } else {
                    globalResponse.put("sectionList", jsonSectionStr);
                }
            } else{
                String jsonOrgStr = referList.getJSONArrayForOrgTree();
                if (StrUtil.isNotBlank(jsonOrgStr)) {
                    JSONArray jsonOrg = JSONUtil.parseArray(jsonOrgStr);
                    globalResponse.put("orgList", jsonOrg);
                } else {
                    globalResponse.put("orgList", jsonOrgStr);
                }
            }
        } else if (type==TmgReferList.TREEVIEW_TYPE_EMP) {
            log.info("---TREEVIEW_TYPE_EMP---");
            if (StrUtil.equals(psDBBean.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_PERM)) {
                String memberListStr = referList.getJSONArrayForMemberList();
                if (StrUtil.isNotBlank(memberListStr)) {
                    JSONArray memberList = JSONUtil.parseArray(memberListStr);
                    globalResponse.put("memberList", memberList);
                } else {
                    globalResponse.put("memberList", memberListStr);
                }
                String sectionMemberListStr = referList.getJSONArrayForMemberListGroupBySection();
                if (StrUtil.isNotBlank(sectionMemberListStr)) {
                    JSONArray sectionMemberList = JSONUtil.parseArray(sectionMemberListStr);
                    globalResponse.put("sectionMemberList", sectionMemberList);
                } else {
                    globalResponse.put("sectionMemberList", sectionMemberListStr);
                }
                String groupMemberListStr = referList.getJSONArrayForMemberListGroupByGroup();
                if (StrUtil.isNotBlank(groupMemberListStr)) {
                    JSONArray groupMemberList = JSONUtil.parseArray(groupMemberListStr);
                    globalResponse.put("groupMemberList", groupMemberList);
                } else {
                    globalResponse.put("groupMemberList", groupMemberListStr);
                }
            } else if (StrUtil.equals(psDBBean.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_ADMIN)) {
                String jsonOrgStr = referList.getJSONArrayForOrgTree();
                if (StrUtil.isNotBlank(jsonOrgStr)) {
                    JSONArray jsonOrg = JSONUtil.parseArray(jsonOrgStr);
                    globalResponse.put("orgList", jsonOrg);
                } else {
                    globalResponse.put("orgList", jsonOrgStr);
                }
                String empListStr = referList.getJSONArrayForEmpList();
                if (StrUtil.isNotBlank(empListStr)){
                    JSONArray empList = JSONUtil.parseArray(empListStr);
                    globalResponse.put("empList",empList);
                } else {
                    globalResponse.put("empList",empListStr);
                }
            }
        } else if (type==TmgReferList.TREEVIEW_TYPE_LIST) {
            log.info("---TREEVIEW_TYPE_LIST---");
            if (StrUtil.equals(psDBBean.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_PERM)) {
                String groupListStr = referList.getJSONArrayForGroupList();
                if (StrUtil.isNotBlank(groupListStr)) {
                    JSONArray groupList = JSONUtil.parseArray(groupListStr);
                    globalResponse.put("groupList", groupList);
                } else {
                    globalResponse.put("groupList", groupListStr);
                }
                String sectionListStr = referList.getJSONArrayForSectionList();
                if (StrUtil.isNotBlank(sectionListStr)) {
                    JSONArray sectionList = JSONUtil.parseArray(sectionListStr);
                    globalResponse.put("sectionList", sectionList);
                } else {
                    globalResponse.put("sectionList", sectionListStr);
                }
            } else {
                String jsonOrgStr = referList.getJSONArrayForOrgTree();
                if (StrUtil.isNotBlank(jsonOrgStr)) {
                    JSONArray jsonOrg = JSONUtil.parseArray(jsonOrgStr);
                    globalResponse.put("orgList",jsonOrg);
                } else {
                    globalResponse.put("orgList",jsonOrgStr);
                }
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

    @GetMapping("conditions")
    public GlobalResponse searchTreeConditions() {
        Map<String,String> searchTypes = MapUtil.newHashMap();
        searchTypes.put(TmgUtil.Cs_TREE_VIEW_ITEMS_KANANAME,TmgUtil.getPropertyValue("TTV_PERM_MSG_KANA_NAME"));
        searchTypes.put(TmgUtil.Cs_TREE_VIEW_ITEMS_KANJINAME,TmgUtil.getPropertyValue("TTV_PERM_MSG_KANJI_NAME"));
        searchTypes.put(TmgUtil.Cs_TREE_VIEW_ITEMS_EMPLOYEEID,TmgUtil.getPropertyValue("TTV_PERM_MSG_EMPLOYEEID"));
        Map<String,String> searchConditions = MapUtil.newHashMap();
        searchConditions.put(TmgUtil.Cs_TREE_VIEW_CONDITION_BROADMATCH,TmgUtil.getPropertyValue("TTV_PERM_MSG_MATCH_BROAD"));
        searchConditions.put(TmgUtil.Cs_TREE_VIEW_CONDITION_PREFIXSEARCH,TmgUtil.getPropertyValue("TTV_PERM_MSG_MATCH_PREVIOUS"));
        searchConditions.put(TmgUtil.Cs_TREE_VIEW_CONDITION_BACKWARDMATCH,TmgUtil.getPropertyValue("TTV_PERM_MSG_MATCH_BACKWARD"));
        TreeSearchConditionVO treeSearchConditionVO = new TreeSearchConditionVO(TmgUtil.getPropertyValue("TTV_PERM_BTN_SEARCH"),searchTypes,searchConditions);
        return GlobalResponse.ok(treeSearchConditionVO);
    }

    @GetMapping("emplist")
    public GlobalResponse empTree(@RequestAttribute("BeanName") PsDBBean psDBBean,
                                  @RequestParam("type") Integer type,
                                  @RequestParam(value = "useRecordDate",required = false,defaultValue = "true") Boolean useRecordDate,
                                  @RequestParam(value="useManage",required = false,defaultValue = "false") Boolean useManage,
                                  @RequestParam(value="joinEmployees",required = false,defaultValue = "true") Boolean joinEmployees,
                                  @RequestParam(value="useSearch",required = false,defaultValue = "true") Boolean useSearch,
                                  @RequestParam(value="useTargetDate",required = false,defaultValue = "false")Boolean useTargetDate) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
//        psDBBean.getRequestHash().put("SiteId", TmgUtil.Cs_SITE_ID_TMG_ADMIN);
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
        String empListStr = referList.getJSONArrayForEmpList();
        if (StrUtil.isNotBlank(empListStr)){
            JSONArray empList = JSONUtil.parseArray(empListStr);
            globalResponse.put("empList",empList);
        } else {
            globalResponse.put("empList",empListStr);
        }
        return globalResponse;
    }

    // /sys/tree/search?hidSearchItems=EMPLOYEEID&hidSearchCondition=BROADMATCH&hidSearchData=464&type=11&psSite=TMG_PERM&psApp=AttendanceBook
    @GetMapping("search")
    public GlobalResponse search(@RequestAttribute("BeanName") PsDBBean psDBBean,
                                 @RequestParam("type") Integer type,
                                 @RequestParam(value = "useRecordDate",required = false,defaultValue = "true") Boolean useRecordDate,
                                 @RequestParam(value="useManage",required = false,defaultValue = "false") Boolean useManage,
                                 @RequestParam(value="joinEmployees",required = false,defaultValue = "true") Boolean joinEmployees,
                                 @RequestParam(value="useSearch",required = false,defaultValue = "true") Boolean useSearch,
                                 @RequestParam(value="useTargetDate",required = false,defaultValue = "false")Boolean useTargetDate
    ) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        psDBBean.getRequestHash().put(TmgReferList.TREEVIEW_OBJ_HIDSELECT,1);
//        psDBBean.getRequestHash().put("SiteId", TmgUtil.Cs_SITE_ID_TMG_ADMIN);
        TmgReferList referList = new TmgReferList(psDBBean, psDBBean.getAppId(), baseDate, type, joinEmployees,
                useRecordDate, useManage, useTargetDate, useSearch);
        String searchMemberList = referList.getJSONArrayForMemberListSearch();
        if (StrUtil.isBlank(searchMemberList)) {
            return GlobalResponse.ok(TmgUtil.getPropertyValue("MSG_ZERO_SEARCH_RESULTS"));
        }
        JSONArray jsonArray = JSONUtil.parseArray(searchMemberList);
        if (CollUtil.isNotEmpty(jsonArray)) {
            int size = jsonArray.size();
            String dispLimit4Tree = referList.getDispLimit4Tree();
            GlobalResponse r = GlobalResponse.ok(jsonArray);
            if (StrUtil.isNotBlank(dispLimit4Tree)){
                int limitCount = Integer.parseInt(dispLimit4Tree);
                if (size>limitCount) {
                    r.put("overTip",StrUtil.replace(TmgUtil.getPropertyValue("MSG_SEARCH_CAUTION"),"@1@",dispLimit4Tree));
                }
            }
            return r;
        }
        return GlobalResponse.ok();
    }

    /**
      * ================== 弹窗相关json =================
      *
      */


    // http://localhost:6879/sys/tree/orgs?psSite=Admin
    // 要渲染具有层数的树结构时不能用这两个参数：companyCode=01&sectionCode=204000000000
    @GetMapping("orgs")
    public List<OrgTreeVO> orgTree(
//            @RequestParam(value="sectionCode",required = false) String sectionCode,
//            @RequestParam(value="companyCode",required = false) String companyCode,
            @RequestParam(value="useSearchRange",required=false,defaultValue = "true") Boolean useSearchRange,
            @RequestParam(value="searchRange",required = false) String searchRange) {
        String searchDate = SysUtil.transDateToString(DateUtil.date());
        PsSession session = (PsSession) ContextUtil.getSession().getAttribute(Constant.PS_SESSION);
        List<OrgTreeDTO> treeDTOList = orgTreeListLogic.getOrgTreeList(session.getLoginCustomer(),
                session.getLanguage(),
                session.getLoginCompany(),
                searchDate,
                null,
                null,
                useSearchRange,
                searchRange);
        List<OrgTreeVO> orgTreeVoList = CollUtil.newArrayList();
        boolean hasRoot = false;
        for (OrgTreeDTO item : treeDTOList) {
            if (StrUtil.equals(item.getMoClayeredsectionid(),",01,,|,000000,")) {
                item.setMoClayeredsectionid("0");
                item.setMoCparentid("0");
                hasRoot = true;
            } else {
                String tmpSectionLayerId = item.getMoClayeredsectionid()
                        .replaceAll(",01,,\\|,","0,");
                String tmpSectionLayerId2 = tmpSectionLayerId.substring(0,tmpSectionLayerId.length()-1);
                int lastSectionIdIdx = tmpSectionLayerId2.lastIndexOf(",");
                String tmpSectionLayerId3 = tmpSectionLayerId2.substring(0,lastSectionIdIdx);
                item.setMoClayeredsectionid(tmpSectionLayerId3);
            }
            OrgTreeVO vo = OrgTreeVO.build(item);
            orgTreeVoList.add(vo);
        }
        if (!hasRoot) {
            Date now = DateUtil.date();
            MastOrganisationDO orgDO = organisationService.list(SysUtil.<MastOrganisationDO>query().eq("mo_clayeredsectionid",",01,,|,000000,").le("MO_DSTART",now)
            .ge("MO_DEND",now)).get(0);
            OrgTreeDTO item  = new OrgTreeDTO();
            BeanUtil.copyProperties(orgDO,item);
            item.setMoClayeredsectionid("0");
            item.setMoCparentid("0");
            OrgTreeVO vo = OrgTreeVO.build(item);
            orgTreeVoList.add(vo);
        }
        return generateTreeList(orgTreeVoList,"0");
    }

    // http://localhost:6879/sys/tree/posts?psSite=Admin
    @GetMapping("posts")
    public Map<String,Object> postTree(
        @RequestParam(value = "useSearchRange",defaultValue = "true",required = false) Boolean useSearchRange,
        @RequestParam(value="companyCode",defaultValue = "01",required = false) String companyCode) throws ParseException {
        String searchDate = SysUtil.transDateToString(DateUtil.date());
        return postGenericLogic.dispPost(
                companyCode,
                searchDate,
                useSearchRange
        );
    }

    private List<OrgTreeVO> generateTreeList(List<OrgTreeVO> treeVoList, String level) {
        if (CollectionUtils.isEmpty(treeVoList)) {
            return CollUtil.newArrayList();
        }
        ListValueMap<String, OrgTreeVO> deptTreeMap = new ListValueMap<>();
        List<OrgTreeVO> rootList = CollUtil.newArrayList();
        for (OrgTreeVO treeVo : treeVoList) {
            deptTreeMap.putValue(treeVo.getLayeredSectionId(), treeVo);
            if (StrUtil.equals(level,treeVo.getLayeredSectionId())) {
                rootList.add(treeVo);
            }
        }
        rootList.sort(Comparator.comparing(OrgTreeVO::getSectionId));
        // 递归生成树
        transformTreeList(rootList, level, deptTreeMap);
        return rootList;
    }

    private void transformTreeList(List<OrgTreeVO> treeList, String level,
                                   ListValueMap<String, OrgTreeVO> deptTreeMap) {
        treeList.forEach(treeItem -> {
            // 处理当前层级的数据
            String nextLevel = calculateLevel(level, treeItem.getSectionId());
            // 处理下一层
            List<OrgTreeVO> tempDeptList = deptTreeMap.get(nextLevel);
            if (CollUtil.isNotEmpty(tempDeptList)) {
                // 排序
                tempDeptList.sort(Comparator.comparing(OrgTreeVO::getSectionId));
                // 设置下一层部门
                if(CollUtil.isNotEmpty(tempDeptList)){
                    treeItem.setChildren(tempDeptList);
                }
                // 进入到下一层处理
                transformTreeList(tempDeptList, nextLevel, deptTreeMap);
            }
        });
    }

    private final static String SEPARATOR = ",";

    private final static String ROOT = "0";

    private static String calculateLevel(String parentLevel, String parentId) {
        if (StrUtil.isBlank(parentLevel)) {
            return ROOT;
        } else {
            return StrUtil.join(SEPARATOR,parentLevel, parentId);
        }
    }

}
