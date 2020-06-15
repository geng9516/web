package jp.smartcompany.controller;

import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.job.modules.core.util.PsDBBean;

import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.CalenderVo;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.OneMonthDetailVo;
import jp.smartcompany.job.modules.tmg.permStatList.PermStatListBean;
import jp.smartcompany.job.modules.tmg.permStatList.vo.TmgMonthlyInfoVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DispMonthlyVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author 陳毅力
 * @description test controller
 * @objectSource
 * @date 2020/05/18
 **/
@RestController
@RequestMapping("sys/permStatList")
public class PermStatListController {

    @Autowired
    private PermStatListBean permStatListBean;

    /**
     * 年月を取得する
     *
     * @param psDBBean PsDBBean
     *                 txtAction アクション (ACT_DISP_MONTHLY)
     *                 txtDYYYYMM　対象月
     *                 txtDYYYYMMDD　対象日
     *                 txtCEMPLOYEEID　職員ID
     *                 txtExecuteEmpId　チェックした対象者
     * @return List<DispMonthlyVO>
     * @throws Exception
     */
    @GetMapping("dispTmgMonthlyList")
    @ResponseBody
    public List<DispMonthlyVO> dispMonthlyList(
            @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        permStatListBean.execute(psDBBean);
        List<DispMonthlyVO> dispMonthlyList = permStatListBean.dispMonthlyList(psDBBean);
        return dispMonthlyList;
    }


    /**
     * 表示対象月の前月データを持つ職員数

     * @param psDBBean PsDBBean
     *                 txtAction アクション(ACT_DISP_MONTHLY)
     *                 txtDYYYYMM　対象月
     *                 txtDYYYYMMDD　対象日
     *                 txtCEMPLOYEEID　職員ID
     *                 txtExecuteEmpId　チェックした対象者
     * @return int
     * @throws Exception
     */
    @GetMapping("dispMonthlyPrev")
    @ResponseBody
    public int dispMonthlyPrev(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        permStatListBean.execute(psDBBean);
        int dispMonthlyPrev = permStatListBean.dispMonthlyPrev();
        return dispMonthlyPrev;
    }


    /**
     * 表示対象月の翌月データを持つ職員数
     *
     * @param psDBBean PsDBBean
     *                 txtAction アクション(ACT_DISP_MONTHLY)
     *                 txtDYYYYMM　対象月
     *                 txtDYYYYMMDD　対象日
     *                 txtCEMPLOYEEID　職員ID
     *                 txtExecuteEmpId　チェックした対象者
     * @return int
     * @throws Exception
     */
    @GetMapping("dispMonthlyNext")
    @ResponseBody
    public int dispMonthlyNext(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        permStatListBean.execute(psDBBean);
        int dispMonthlyNext = permStatListBean.dispMonthlyNext();
        return dispMonthlyNext;
    }

    /**
     * 表示月情報の取得
     * @param psDBBean PsDBBean
     *                 txtAction アクション(ACT_DISP_MONTHLY)
     *                 txtDYYYYMM　対象月
     *                 txtDYYYYMMDD　対象日
     *                 txtCEMPLOYEEID　職員ID
     *                 txtExecuteEmpId　チェックした対象者
     * @return List<TmgMonthlyInfoVO>
     * @throws Exception
     */
    @GetMapping("getTmgMonthlyInfoVOList")
    @ResponseBody
    public List<TmgMonthlyInfoVO> getTmgMonthlyInfoVOList(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        permStatListBean.execute(psDBBean);
        List<TmgMonthlyInfoVO> tmgMonthlyInfoVOList = permStatListBean.getTmgMonthlyInfoVOList(psDBBean);
        return tmgMonthlyInfoVOList;
    }


    /**
     * カレンダー情報の取得
     * @param psDBBean PsDBBean
     *                 txtAction アクション(ACT_DISP_MONTHLY)
     *                 txtDYYYYMM　対象月
     *                 txtDYYYYMMDD　対象日
     *                 txtCEMPLOYEEID　職員ID
     *                 txtExecuteEmpId　チェックした対象者
     * @return CalenderVo
     * @throws Exception
     */
    @GetMapping("selectGetCalendarList")
    @ResponseBody
    public CalenderVo selectGetCalendarList(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        permStatListBean.execute(psDBBean);
        CalenderVo calenderVo = permStatListBean.selectGetCalendarList(psDBBean);
        return calenderVo;
    }


    /**
     * 対象勤務年月の1ヶ月間の日付・曜日を取得
     * @param psDBBean PsDBBean
     *                 txtAction アクション(ACT_DISP_MONTHLY)
     *                 txtDYYYYMM　対象月
     *                 txtDYYYYMMDD　対象日
     *                 txtCEMPLOYEEID　職員ID
     *                 txtExecuteEmpId　チェックした対象者
     * @return List<OneMonthDetailVo>
     * @throws Exception
     */
    @GetMapping("selectDayCount")
    @ResponseBody
    public List<OneMonthDetailVo> selectDayCount(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        permStatListBean.execute(psDBBean);
        List<OneMonthDetailVo> oneMonthDetailVoList = permStatListBean.selectDayCount();
        return oneMonthDetailVoList;
    }


//
//    /**
//     * ディフォルト表示時間を取得する
//     *
//     * @param employeeId
//     * @return {"msg":"リクエスト成功","code":0,"data":{"mgd_ndefault_month":"4","dispterm_start":"2020/04/01","dispterm_end":"2021/03/01"}}
//     */
//    @GetMapping("defaultDate")
//    public AttendanceDateInfoDTO defaultDate(@RequestParam("employeeId") String employeeId,  @RequestAttribute("BeanName") PsDBBean psDBBean) {
//        //初期化対象
//        attendanceBookBean.setExecuteParameters(null, null, employeeId,psDBBean);
//        return attendanceBookBean.selectDateInfo(employeeId);
//
//    }
//
//    /**
//     * 年次休暇付与日数, 年次休暇付与日, 摘要info
//     *
//     * @param employeeId
//     * @param year
//     * @param month
//     * @return {"msg":"リクエスト成功","code":0,"data":{"employeeId":"34370889","endueDate":"01月01日","endueDaysHours":"33 5","tmy_comment":"this is a test 20200519","dataTime":"2020/04","queryTime":"2020-05-22 16:21:21"}}
//     */
//    @GetMapping("queryHolidayInfo")
//    @ResponseBody
//    public AttendanceBookHolidayInfoVO queryHolidayInfo(@RequestParam("employeeId") String employeeId,
//                                                        @RequestParam("year") String year,
//                                                        @RequestParam("month") String month,  @RequestAttribute("BeanName") PsDBBean psDBBean) {
//        //初期化対象
//        attendanceBookBean.setExecuteParameters(year, month, employeeId,psDBBean);
//        return attendanceBookBean.queryHolidayInfo(employeeId, year, month);
//    }
//
//    /**
//     * 出勤簿リスト
//     *
//     * @param employeeId
//     * @param year
//     * @param month
//     * @return
//     */
//    @GetMapping("attendanceBookList")
//    @ResponseBody
//    public List<LinkedHashMap<String, String>> attendanceBookList(@RequestParam("employeeId") String employeeId,
//                                                                  @RequestParam("year") String year,
//                                                                  @RequestParam("month") String month,  @RequestAttribute("BeanName") PsDBBean psDBBean) {
//        //初期化対象
//        attendanceBookBean.setExecuteParameters(year, month, employeeId,psDBBean);
//        return attendanceBookBean.selectAttendanceBookList(employeeId, year, month);
//    }
//
//
//    /**
//     * 摘要編集
//     *
//     * @param employeeId
//     * @param modifieruserId
//     * @param year
//     * @param comment
//     * @return {"msg":"リクエスト成功","code":0,"data":true}
//     */
//    @GetMapping("updateComment")
//    @ResponseBody
//    public boolean updateComment(@RequestParam("employeeId") String employeeId,
//                                 @RequestParam("modifieruserId") String modifieruserId,
//                                 @RequestParam("year") String year,
//                                 @RequestParam("comment") String comment,  @RequestAttribute("BeanName") PsDBBean psDBBean) {
//        //初期化対象
//        attendanceBookBean.setExecuteParameters(year, null, employeeId,psDBBean);
//        return attendanceBookBean.updateComment(employeeId, modifieruserId, year, comment);
//    }
//
//    /**
//     * 権限処理　（コメント更新）
//     * http://localhost:6879/sys/attendanceBook/isEnableEditField?employeeId=46402406&year=2019
//     * @param employeeId targetUser
//     * @param year
//     * @return true:権限があり    false:権限がない
//     */
//    @GetMapping("isEnableEditField")
//    @ResponseBody
//    public boolean isEnableEditField(@RequestParam("employeeId") String employeeId,
//                                     @RequestParam("year") String year,
//                                      @RequestAttribute("BeanName") PsDBBean psDBBean) {
//        //初期化対象
//        attendanceBookBean.setExecuteParameters(year, null, employeeId,psDBBean);
//        return attendanceBookBean.isEnableEditField(year);
//    }


}
