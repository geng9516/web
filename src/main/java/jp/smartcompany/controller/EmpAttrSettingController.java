package jp.smartcompany.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.calendar.CalendarBean;
import jp.smartcompany.job.modules.tmg.empattrsetting.EmpAttrSettingBean;
import jp.smartcompany.job.modules.tmg.empattrsetting.dto.AvgTimeForUpdateDto;
import jp.smartcompany.job.modules.tmg.empattrsetting.dto.BeginDateForUpdateDto;
import jp.smartcompany.job.modules.tmg.empattrsetting.dto.UpdateCheckInfoDto;
import jp.smartcompany.job.modules.tmg.empattrsetting.dto.UpdateGroupInfoDto;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.BeginDateEditDispVo;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.EditDispVo;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.EmpAttsetDispVo;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.EmpDispVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.ParamNotificationListDto;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DispMonthlyVO;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("sys/userprofile")
public class EmpAttrSettingController {

    @Autowired
    private EmpAttrSettingBean empAttrSettingBean;



    /**
     * 個人属性一覧
     *
     * @param psDBBean
     * @return
     */
    @GetMapping("screenDisp")
    public EmpDispVo actionScreenDisp(@RequestParam("empId") String empId,
            @RequestParam("baseDate") String baseDate,
            @RequestParam(value = "page" ,required = false) String page,
            @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        //referlist 新规
        TmgReferList referList= new TmgReferList(psDBBean, "EmpAttrSetting", baseDate, TmgReferList.TREEVIEW_TYPE_LIST_SEC, true,
                true, true, false, true);
        if (StrUtil.hasEmpty(page)){
            page="1";
        }
        return empAttrSettingBean.actionScreenDisp(empId,baseDate,page,psDBBean,referList);
    }


    /**
     * 表头获取
     *
     * @param psDBBean
     * @return
     */
    @GetMapping("tableHeader")
    public List<EmpAttsetDispVo> getTableHeader(@RequestParam("baseDate") String baseDate,
                                                @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {

        return empAttrSettingBean.getTableHeader(baseDate,psDBBean);
    }




    /**
     * 管理対象者一括編集_更新処理
     *
     * @param psDBBean
     * @return
     */
    @PostMapping("manageFlagUpdate")
    @ResponseBody
    public GlobalResponse actionModifyManageFlag(
            @RequestParam("ManageList")String ManageList ,
            @RequestParam("OverTimesList")String OverTimesList ,
            @RequestParam("baseDate") String baseDate,
            @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {

        ArrayList<UpdateGroupInfoDto> updateGroupInfoDtoWorkPlaceList=new ArrayList<UpdateGroupInfoDto>();
        List<UpdateCheckInfoDto> updateCheckInfoDtoManageList=JSONUtil.parseArray(ManageList).toList(UpdateCheckInfoDto.class);
        List<UpdateCheckInfoDto> updateCheckInfoDtoOverTimesList=JSONUtil.parseArray(OverTimesList).toList(UpdateCheckInfoDto.class);
        return empAttrSettingBean.actionModifyManageFlag(updateCheckInfoDtoManageList,updateCheckInfoDtoOverTimesList
                ,updateGroupInfoDtoWorkPlaceList,baseDate,psDBBean);
    }


    /**
     * 平均勤務時間表示処理
     *
     * @param psDBBean
     * @return
     */
    @GetMapping("avgWorkTimeEditDisp")
    public EditDispVo actionScreenDispEditAvgWorktime(@RequestParam("empId") String empId,
                                                      @RequestParam("baseDate") String baseDate,
                                                      @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {

        return empAttrSettingBean.actionScreenDispEditAvgWorktime(empId,baseDate,psDBBean);
    }



    /**
     * 平均勤務時間編集_登録処理
     *
     * @param psDBBean
     * @return
     */
    @PostMapping("avgWorkTimeUpload")
    public GlobalResponse actionModifyAvgWorktime(@RequestParam("empId") String empId,
                                                  @RequestParam("baseDate") String baseDate,
                                                  @RequestParam("params") String params,
                                                  @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        AvgTimeForUpdateDto param = JSONUtil.parse(params).toBean(AvgTimeForUpdateDto.class);
        return empAttrSettingBean.actionModifyAvgWorktime(empId,baseDate,param,psDBBean);
    }

    /**
     * 平均勤務時間編集_削除処理
     *
     * @param psDBBean
     * @return
     */
    @PostMapping("avgWorkTimeDelete")
    public GlobalResponse actionDeleteAvgWorktime(@RequestParam("empId") String empId,
                                                  @RequestParam("baseDate") String baseDate,
                                                  @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        return empAttrSettingBean.actionDeleteAvgWorktime(empId,baseDate,psDBBean);
    }







    /**
     * 勤務开始時間表示処理
     *
     * @param psDBBean
     * @return
     */
    @GetMapping("beginDateEditDisp")
    public BeginDateEditDispVo actionScreenDispEditBeginDate(@RequestParam("empId") String empId,
                                                             @RequestParam("baseDate") String baseDate,
                                                             @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {

        return empAttrSettingBean.actionScreenDispEditBeginDate(empId,baseDate,psDBBean);
    }

    /**
     * 勤務开始時間新規処理
     *
     * @param psDBBean
     * @return
     */
    @PostMapping("beginDateUpload")
    public GlobalResponse actionInsertBeginDate(@RequestParam("empId") String empId,
                                                @RequestParam("baseDate") String baseDate,
                                                @RequestParam("params") String params,
                                                @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        BeginDateForUpdateDto param = JSONUtil.parse(params).toBean(BeginDateForUpdateDto.class);
        return empAttrSettingBean.actionInsertBeginDate(empId,baseDate,psDBBean,param);
    }


    /**
     * 勤務開始日設定状況_更新処理
     *
     * @param psDBBean
     * @return
     */
    @PostMapping("beginDateUpdate")
    public GlobalResponse actionUpdateBeginDate(@RequestParam("empId") String empId,
                                                @RequestParam("params") String params,
                                                @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        BeginDateForUpdateDto param = JSONUtil.parse(params).toBean(BeginDateForUpdateDto.class);
        return empAttrSettingBean.actionUpdateBeginDate(empId,psDBBean,param);
    }

    /**
     * 勤務開始日設定状況_削除処理
     *
     * @param psDBBean
     * @return
     */
    @PostMapping("beginDateDelete")
    public GlobalResponse actionDeleteBeginDate(@RequestParam("empId") String empId,
                                                @RequestParam("params") String params,
                                                @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        BeginDateForUpdateDto param = JSONUtil.parse(params).toBean(BeginDateForUpdateDto.class);
        return empAttrSettingBean.actionDeleteBeginDate(empId,psDBBean,param);
    }
}
