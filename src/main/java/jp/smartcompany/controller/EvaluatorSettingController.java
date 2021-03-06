package jp.smartcompany.controller;

import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.evaluatersetting.EvaluatorSettingBean;
import jp.smartcompany.job.modules.tmg.evaluatersetting.dto.AddEvaluatorDTO;
import jp.smartcompany.job.modules.tmg.evaluatersetting.dto.EditAuthorityDTO;
import jp.smartcompany.job.modules.tmg.evaluatersetting.dto.EditGroupDTO;
import jp.smartcompany.job.modules.tmg.evaluatersetting.dto.EditMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Map;
import java.util.List;

/**
 * @author Xiao Wenpeng
 */
@RestController
@RequestMapping("sys/evaluatorsetting")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Validated
public class EvaluatorSettingController {

    private final EvaluatorSettingBean evaluatorSettingBean;

    // 権限設定状況表示
    // 测试用url:http://localhost:6879/sys/evaluatorsetting/disp?psSite=TMG_PERM&psApp=EvaluaterSetting&txtTmgReferListTreeViewRecordDate=2020/07/30
    // &txtTmgReferListTreeViewPermSelectedView=section&txtTmgReferListTreeViewPermTargetSection=201000000000&txtDYYYYMMDD=2020/07/01
    // 原有数据：
    // [[[100000000000|000000, 役員等, 40010001 氏名, 役員等, 理事長, TMG_ONOFF|1, TMG_ONOFF|0, TMG_ONOFF|1, TMG_ONOFF|0, TMG_ONOFF|0, TMG_ONOFF|0, TMG_ONOFF|1, TMG_ONOFF|1, TMG_ONOFF|0, 1, 2017/04/01, 2018/12/01, 0役員等, null, null, 1, 40010001, 2017-04-01 00:00:00.0], [100000000000|000001, abc, 40010001 氏名, 役員等, 理事長, TMG_ONOFF|1, TMG_ONOFF|0, TMG_ONOFF|1, TMG_ONOFF|1, TMG_ONOFF|0, TMG_ONOFF|0, TMG_ONOFF|1, TMG_ONOFF|1, TMG_ONOFF|0, 1, 2013/04/01, 2222/12/31, 1abc, TMG_ONOFF|1, TMG_ONOFF|1, 1, 40010001, 2013-04-01 00:00:00.0], [100000000000|000002, gggc1, null, null, null, null, null, null, null, null, null, null, null, TMG_ONOFF|0, null, null, null, 1gggc1, null, null, null, null, null]], [[100000000000|000000, 1], [100000000000|000001, 1], [100000000000|000002, 1]], [[2019/04/01, null]], [[100000000000|000002, null, 0], [100000000000|000000, 40010001, 1], [100000000000|000001, 40010001, 1]]]
    // [[[200000000000|000000, （本部）, null, null, null, null, null, null, null, null, null, null, null, TMG_ONOFF|0, null, null, null, 0（本部）, null, null, null, null, null]], [[200000000000|000000, 1]], [[2018/10/25, null]], [[200000000000|000000, null, 0]]]
    @PostMapping("disp")
    public Map<String,Object> disp(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        return evaluatorSettingBean.dispHandler(psDBBean);
    }

    // グループ作成処理
    @PostMapping("makegroup")
    // 创建组参数url: http://localhost:6879/sys/evaluatorsetting/makegroup?txtDYYYYMMDD=2019/08/02&psSite=TMG_ADMIN&targetSectionId=201000000000&psApp=EvaluaterSetting&groupName=testgroup06
    // 更新组名称url: http://localhost:6879/sys/evaluatorsetting/makegroup?txtAction=ACT_EditGroup_UGrpName&targetGroupId=201000000000|%7C000004&txtDYYYYMMDD=2019/08/02&psSite=TMG_ADMIN&targetSectionId=201000000000&psApp=EvaluaterSetting&groupName=testgroup06
    public GlobalResponse makeGroup(@RequestAttribute("BeanName") PsDBBean psDBBean,
                                    @RequestParam(value="targetSectionId",required = false) String targetSectionId,
                                    @RequestParam(value="targetGroupId",required = false) String targetGroupId,
                                    @RequestParam(value="lastTargetGroupId",required = false) String lastTargetGroupId,
                                    @RequestParam(value="groupName",required = false) String groupName,
                                    @RequestParam(value="empId",required = false) String empId) {
        return evaluatorSettingBean.makeGroupHandler(psDBBean,targetSectionId,targetGroupId,lastTargetGroupId,groupName,empId);
    }

    // グループ属性編集画面表示
    @GetMapping("editgroup")
    // http://localhost:6879/sys/evaluatorsetting/editgroup?psSite=TMG_ADMIN&psApp=EvaluaterSetting&targetGroupId=201000000000%7C000000&sectionId=201000000000&txtDYYYYMMDD=2019/08/02
    public Map<String,Object> editGroup(@RequestAttribute("BeanName") PsDBBean psDBBean,
                                        @RequestParam(value="targetGroupId",required = false) String groupId,
                                        @RequestParam(value="sectionId",required = false) String sectionId) {
        return evaluatorSettingBean.showEditGroupHandler(psDBBean,sectionId,groupId);
    }

    // グループ属性登録処理（夜間連携項目（など））
    @PostMapping("editgroup")
    // http://localhost:6879/sys/evaluatorsetting/editgroup?psSite=TMG_ADMIN&psApp=EvaluaterSetting&txtDYYYYMMDD=2019/08/02
    /* json参数：
     * {
         "sectionId":"",
         "autoStart":"",
         "dailyOverTime":"",
         "monthlyOverTimeAvg":"",
         "monthlyOverTimeCount":"",
         "monthlyOverTimeYellow":"",
         "monthlyOverTimeOrange":"",
         "monthlyOverTimePink":"",
         "monthlyOverTimeRed":"",
         "monthlyOverTimeBackUp":"",
         "yearlyOverTimeYellow":"",
         "yearlyOverTimeOrange":"",
         "yearlyOverTimePink":"",
         "yearlyOverTimeRed":"",
         "yearlyOverTimeBackUp":"",
         "monthlyHolidayTimeLevel1":"",
         "monthlyHolidayTimeLevel2":"",
         "monthlyHolidayTimeLevel3":"",
         "monthlyHolidayTimeLevel4":"",
         "monthlyHolidayTimeLevel5":""
     * }
     * */
    public GlobalResponse editGroup(@RequestAttribute("BeanName") PsDBBean psDBBean,
                                    @Valid @RequestBody EditGroupDTO dto) {
        return evaluatorSettingBean.editGroupNameProcHandler(psDBBean,dto);
    }

    // グループ削除処理
    @GetMapping("deletegroup")
    // http://localhost:6879/sys/evaluatorsetting/deletegroup?psSite=TMG_ADMIN&psApp=EvaluaterSetting&targetGroupId=201000000000%7C000005&sectionId=201000000000&txtDYYYYMMDD=2019/08/02
    public GlobalResponse deleteGroup(@RequestAttribute("BeanName") PsDBBean psDBBean,
                                      @RequestParam(value="targetGroupId") String groupId,
                                      @RequestParam(value="sectionId") String sectionId) {
        return evaluatorSettingBean.deleteGroupHandler(psDBBean,sectionId,groupId);
    }

    // 承認者追加画面表示
    @GetMapping("addeval")
    // http://localhost:6879/sys/evaluatorsetting/addeval?psSite=TMG_ADMIN&psApp=EvaluaterSetting&sectionId=201000000000&txtDYYYYMMDD=2019/08/02
    public Map<String,Object> showAddEval(@RequestAttribute("BeanName") PsDBBean bean,@RequestParam("sectionId") String sectionId) {
        return evaluatorSettingBean.showAddEvalHandler(bean,sectionId);
    }

    // 承認者追加
    // http://localhost:6879/sys/evaluatorsetting/addeval?psSite=TMG_ADMIN&psApp=EvaluaterSetting&txtDYYYYMMDD=2019/08/02&txtAction=ACT_MakeGroup_UGroup
    /**
     *  {
     *    "empId":"",
     *    "sectionId":"",
     *    "groupId":"",
     *    "approvalLevel":"",
     *    "dailyResult": true, 勤怠承認
     *    "monthlyResult": true, 月次承認  // 需要根据返回值来确定是否允许拥有月次承认权限
     *    "overTime":true, 超過勤務命令
     *    "schedule": true, 予定作成
     *    "authority": true, 权限设定
     *    "notification": true 休暇・休出承認 这个checkbox为true时才能选择承认级别
     *    "startDate":"",
     *    "endDate":""
     *  }
     */
    @PostMapping("addeval")
    public GlobalResponse addEval(@RequestAttribute("BeanName") PsDBBean bean,@RequestBody @Valid AddEvaluatorDTO dto) {
        dto.validate();
        return evaluatorSettingBean.addEvalHandler(bean,dto);
    }

    // 職員検索画面表示
    @GetMapping("searchemp")
    // http://localhost:6879/sys/evaluatorsetting/searchemp?psSite=TMG_ADMIN&psApp=EvaluaterSetting&sectionId=201000000000&txtDYYYYMMDD=2019/08/02
    public Map<String,Object> showSearchEmp(@RequestAttribute("BeanName") PsDBBean bean,@RequestParam(value="sectionId",required = false) String sectionId) {
        return evaluatorSettingBean.showSearchEmpHandler(bean,sectionId);
    }

    // メンバー割付画面
    // http://localhost:6879/sys/evaluatorsetting/editmember?psSite=TMG_ADMIN&psApp=EvaluaterSetting&sectionId=201000000000&txtDYYYYMMDD=2019/08/02
    @GetMapping("editmember")
    public Map<String,Object> showEditMember(@RequestAttribute("BeanName") PsDBBean bean,@RequestParam(value="sectionId") String sectionId) {
        return evaluatorSettingBean.showEditMemberHandler(bean,sectionId);
    }

    // ンバー割付処理をするメソッド
    // http://localhost:6879/sys/evaluatorsetting/editmember?psSite=TMG_ADMIN&psApp=EvaluaterSetting&txtDYYYYMMDD=2019/08/02
    /**
     *   提交的json体：
     *   [{
     *      "sectionId":"",
     *      "groupId":"",
     *      "empId":"",
     *      "originalGroupId":""
     *   }]
     */
    @PostMapping("editmember")
    @Validated
    public Map<String,Object> editMember(@RequestAttribute("BeanName") PsDBBean bean,@Valid @RequestBody @NotEmpty List<EditMemberDTO> dtoList) {
        return evaluatorSettingBean.editMemberHandler(bean,dtoList);
    }

    // 承認者編集画面表示
    // http://localhost:6879/sys/evaluatorsetting/editeval?psSite=TMG_ADMIN&psApp=EvaluaterSetting&txtDYYYYMMDD=2019/08/02&sectionId=201000000000&empId=46402406&groupId=201000000000%7C000000
    @GetMapping("editeval")
    public Map<String,Object> showEditEval(@RequestAttribute("BeanName") PsDBBean bean,
                                           @RequestParam("sectionId") String sectionId,
                                           @RequestParam("groupId") String groupId,
                                           @RequestParam("empId") String empId) {
        return evaluatorSettingBean.showEditEvalHandler(bean,sectionId,groupId,empId);
    }

    // 承認者編集
    // http://localhost:6879/sys/evaluatorsetting/editeval?psSite=TMG_ADMIN&psApp=EvaluaterSetting
    /**
     *  提交的json体：
     *  {
     *      "groupId":"",
     *      "sectionId":"",
     *      "empId":"",
     *      "list": [
     *           {
     *               "delete":true,
     *               "newLine":true,
     *               "approvalLevel":"",
     *               "dailyResult":true,
     *               "monthlyResult":true,
     *               "overTime":true,
     *               "schedule":true,
     *               "authority":true,
     *               "notification":true,
     *               "startDate":"",
     *               "endDate":"",
     *               // 更新操作以下两个字段需要传入
     *               "beforeStartDate":"",
     *               "beforeEndDate":""
     *           }
     *      ]
     *  }
     */
    @PostMapping("editEval")
    @Validated
    public GlobalResponse editEvalHandler(@RequestAttribute("BeanName") PsDBBean bean, @Valid @RequestBody EditAuthorityDTO authorityDTO) {
        authorityDTO.validate();
        return evaluatorSettingBean.editEvalHandler(bean,authorityDTO);
    }

    // http://localhost:6879/sys/evaluatorsetting/emplevel?empId=A0000001&txtDYYYYMMDD=2020/10/09
    @GetMapping("emplevel")
    public Map<String,Object> getEmpApprovalLevel(@RequestAttribute("BeanName") PsDBBean bean,@RequestParam("empId") String empId) {
        return evaluatorSettingBean.defaultLevelHandler(bean,empId);
    }

}
