package jp.smartcompany.controller.admin;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.admin.component.dto.SectionPostRowListDTO;
import jp.smartcompany.admin.groupmanager.dto.GroupManagerEditDTO;
import jp.smartcompany.admin.groupmanager.logic.GroupManagerDateEditLogic;
import jp.smartcompany.admin.groupmanager.logic.GroupManagerGroupEditLogic;
import jp.smartcompany.admin.groupmanager.logic.GroupManagerLogic;
import jp.smartcompany.framework.component.dto.QueryConditionSelectDTO;
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

    /**
     * ===========================================
     * 原SmartCompany GroupManagerMainAction 整理接口
     * ===========================================
     */

    // http://localhost:6879/sys/groupmanager/groups?psSite=Admin
    // グループ定義:閲覧画面リスト
    @GetMapping("groups")
    public Map<String,Object> getValidGroupList(
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            @RequestParam(value="searchDate",required = false) Date searchDate,
            @RequestParam(value="systemId",required = false) String systemId) {
        return groupManagerLogic.getManagerGroupList(psDBBean,searchDate,systemId);
    }

    /**
     * ===========================================
     * 原SmartCompany GroupManagerGroupEditAction 整理接口
     * ===========================================
     */
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

    /**
     *  更新或新增group的操作：发送的json数据格式为：
     *  {
     *    "mgpId":     mast_groupdefinitions表的数据库自增id，从上面的detail接口里获取，更新group时才需要
     *    "mgId":       更新和新增都需要，mast_group表的数据库自增id，从上面的detail接口里获取，更新group时才需要
     *    "mgPublishing" 是否拥有发布揭示板权限
     *    "groupId":          group表的自定义id，更新和新增都需要，会进行groupId重复check
     *    "startDate":   有效期开始时间
     *    "endDate": 有效期结束时间
     *    "baseFlag":   "2"为組織・役職による定義の情報取得 "1"为条件式による定義の情報取得 "0"为職員による定義の情報取得
     *    "weightAge": group排序字段
     *    "groupName": group名称
     *    "peopleCount":  能够加入此group的最大人数
     *    "remark": 备注
     *    "belowSingle": 基点组织的可查看范围 0 以下   1 のみ
     *    組織・役職条件選択情報(如果为更新则要根据上面的detail接口返回的orgJobConditions字段来进行初始数据填充)
     *    "sectionPostList": {
     *        "companyId": "",
     *        "companyName": "",
     *        "sectionList": [                    ------------typeId 02的情况  組織
     *             {
     *                "companyId": "01"
     *                "sectionId":  組織コード
     *                "id":  mast_groupsectionpostmapping表的自增id，如果已经有传了id但delete字段为false，则表示保持原样
     *                "delete": 是否是要删除的部门，如果是要删除的数据，则只需要传入id（对应detail接口里的mgbsId）和delete设置为true
     *                "postList": [              ------------typeId 03的情况  組織役職
     *                  "sectionId": 組織コード
     *                  "postId": 役職コード,
     *                  "companyId": "01",
     *                  "delete": 是否是要删除的役職，则只需要传入id（对应detail接口里的mgbsId）和delete设置为true
     *                  "id":  mast_groupsectionpostmapping表的自增id，如果已经有传了id但delete字段为false，则表示保持原样
     *                ]
     *             }
     *        ],
     *        "postList": [   ------------typeId 06的情况  役職
     *             {
     *                "id": mast_groupsectionpostmapping表的自增id，如果已经有传了id但delete字段为false，则表示保持原样
     *                "postId": 役職コード,
     *                "companyId": "01",
     *                "delete": 是否要删除的部门，如果是要删除的数据，则只需要传入id（对应detail接口里的mgbsId）和delete设置为true
     *             }
     *        ],
     *        "bossSectionList": [    ------------typeId 05的情况  所属長
     *             {
     *                 "id": mast_groupsectionpostmapping表的自增id，如果已经有传了id但delete字段为false，则表示保持原样
     *                 "companyId": "01",
     *                 "delete": 是否要删除的部门，如果是要删除的数据，则只需要传入id（对应detail接口里的mgbsId）和delete设置为true
     *                 "sectionId": 組織コード
     *             }
     *        ]
     *    },
     *    "employList": [  ------------typeId 07的情况  所属長
     *       {
     *           "companyId": "01",
     *           "sectionId": "201000201020",
     *           "postId": "ZZL",
     *           "employeeId": "65494392",
     *           "id": 1101,
     *           "delete": true
     *       }
     *    ],
     *    "queryConditionList": [
     *        "value":"1",
     *        "id":"243",
     *        "columnid":"TIE_ISEVALUATOR",
     *        "operator":"=",
     *        "typeofcolumn":"NUMBER",
     *        "displayvalue":"1",
     *        "columnname":"TMG_ISEVALUATOR－TIE_ISEVALUATOR",
     *        "tableid":"TMG_ISEVALUATOR",
     *        "joinquery":"",
     *        "andor":"OR",
     *        "seq":"0",
     *        "closedparenthsis":")",
     *        "mastertablename":"",
     *        "openedparenthsis":"(",
     *        "displayoperator":"＝",
     *        "delete": true,
     *        "customerid":"01",
     *        "groupid":"2"
     *    ],
     *    "baseSectionList": [  基点组织的删除和添加
     *        {
     *            "sectionList": [
     *                 {
     *                    "mgbsCgroupid":
     *                    "mgbsCcompanyid":
     *                    "mgbsCbeloworsingle":
     *                    "mgbsCsectionid":
     *                    "mgbsClayeredsectionid"
     *                    "sectionName":
     *                    "companyName":
     *                    "mgbsId":  mast_groupbasesection表的自增id，如果已经有传了id但delete字段为false，则表示更新而不是新增
     *                    "mgbsCcustomerid":
     *                    "mgbsCsystemid":
     *                    "delete":  是否要删除的基点组织，如果是要删除的数据，则只需要传入id（对应detail接口里的mgbsId）和delete设置为true
     *                 }
     *            ]
     *        }
     *    ]
     *  }
     */
    @PostMapping("update")
    public String executeUpdate(@Valid @RequestBody GroupManagerEditDTO dto) {
        groupManagerGroupEditLogic.update(dto);
        return "変更しました";
    }

    /**
     * ===========================================
     * 原SmartCompany GroupManagerMainAction 整理接口
     * ===========================================
     */

    // http://localhost:6879/sys/groupmanager/empsearch?searchWord=464&psSite=Admin
    @GetMapping("empsearch")
    public List<EmployeeInfoSearchEntity> searchEmpList(
      @RequestParam(value="searchWord",required = false) String searchWord,
      @RequestParam(value="searchWordConve",required = false) String searchWordConve,
      @RequestParam(value="searchWordEnglish",required = false) String searchWordEnglish,
      @RequestParam(value="searchRange",required = false,defaultValue = "0") String searchRange,
      @RequestParam(value = "searchFlg",required = false,defaultValue = "zai") String searchFlg,
      @RequestParam(value="companyId",required = false,defaultValue = "01") String companyId,
      @RequestParam(value="targetComp",required = false,defaultValue = "01") String targetComp,
      // hidden⇔requestとして保持し続けるオブジェクト (本務兼務区分)
      @RequestParam(value="additionalRole",required = false,defaultValue = "0") String ifKeyorAdditionalRole,
      @RequestParam(value="targetDept",required = false) String targetDept,
      @RequestParam(value="type",required = false, defaultValue = "1") Integer type) {
      return employeeInfoSearchLogic.searchEmpList(searchWord,searchWordConve,searchWordEnglish,searchRange,searchFlg,companyId,targetComp,ifKeyorAdditionalRole,targetDept,type);
    }

    /**
     * 条件式選択一覧(指定テーブル一覧) 表示画面用初期処理<br>
     * 選択一覧(テーブル一覧)にて表示する情報を取得します。
     */
    // http://localhost:6879/sys/groupmanager/queryconditions
    @GetMapping("queryconditions")
    public List<QueryConditionSelectDTO> queryConditionList(@RequestParam(value="tableId",required = false) String tableId) {
        return groupManagerGroupEditLogic.queryConditionList(tableId);
    }

    // http://localhost:6879/sys/groupmanager/bosslist?sectionId=201000000000&sectionId=201000201010
    @GetMapping("bosslist")
    public List<SectionPostRowListDTO> getBossCompSectionList(@Valid @NotEmpty @RequestParam List<String> sectionId) {
        return groupManagerGroupEditLogic.getBossCompSectionList(sectionId);
    }


    /**
     * ===========================================
     * 原SmartCompany GroupManagerDateEditAction 整理接口
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
    public String delete(@Valid @RequestBody List<String> groupIds) {
        return groupManagerDateEditLogic.deleteHandler(groupIds);
    }

    // グループ全体の優先順位を更新
    // 因为现在只针对一个系统，所以暂时先不传入systemId，让systemId默认为01
    @PostMapping("sort")
    public String changeSort(@Valid @NotEmpty @RequestBody List<String> groupIds) {
        return groupManagerDateEditLogic.sortHandler(groupIds,null);
    }


}

