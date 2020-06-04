package jp.smartcompany.job.controller;

import jp.smartcompany.job.modules.tmg.schedule.TmgScheduleBean;
import jp.smartcompany.job.modules.tmg.schedule.vo.ScheduleInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author 陳毅力
 * @description 予定作成コントロール
 * @objectSource
 * @date 2020/05/29
 **/
@RestController
@RequestMapping("sys/schedule")
public class ScheduleController {

    @Autowired
    private TmgScheduleBean tmgScheduleBean;

    /**
     * 公休日数	基準日数	基準時間	年次休暇残　と　　勤務予定 リスト
     *
     * @param employeeId
     * @param year
     * @param month
     * @param modelMap
     * @return
     * @throws Exception
     */
    @GetMapping("selectScheduleInfo")
    @ResponseBody
    public ScheduleInfoVO selectScheduleInfo(@RequestParam("employeeId") String employeeId,
                                             @RequestParam("year") String year,
                                             @RequestParam("month") String month, ModelMap modelMap) {
        //初期化
        tmgScheduleBean.setExecuteParameters(year, month, employeeId, modelMap);
        return tmgScheduleBean.selectPaidHolidayInfo(year, month, employeeId);

    }

    /**
     * ディフォルト時間
     *
     * @return
     */
    @GetMapping("defaultDate")
    @ResponseBody
    public HashMap<String, Object> getDefaultDate() {
        return tmgScheduleBean.getDefaultDate();
    }

    /**
     * 翌月リンクを取得
     *
     * @param employeeId
     * @param year
     * @param month
     * @param modelMap
     * @return
     */
    @GetMapping("selectLinkOfNextMonth")
    @ResponseBody
    public HashMap<String, Object> selectLinkOfNextMonth(@RequestParam("employeeId") String employeeId,
                                                         @RequestParam("year") String year,
                                                         @RequestParam("month") String month, ModelMap modelMap) {
        //初期化
        tmgScheduleBean.setExecuteParameters(year, month, employeeId, modelMap);
        return tmgScheduleBean.selectLinkOfNextMonth(employeeId);
    }

    /**
     * 前月リンクを取得
     *
     * @param employeeId
     * @param year
     * @param month
     * @param modelMap
     * @return
     */
    @GetMapping("selectLinkOfPreMonth")
    @ResponseBody
    public HashMap<String, Object> selectLinkOfPreMonth(@RequestParam("employeeId") String employeeId,
                                                        @RequestParam("year") String year,
                                                        @RequestParam("month") String month, ModelMap modelMap) {
        //初期化
        tmgScheduleBean.setExecuteParameters(year, month, employeeId, modelMap);
        return tmgScheduleBean.selectLinkOfPreMonth(employeeId);
    }

    /**
     * [区分][出張][勤務パターン]
     *
     * @param employeeId
     * @param year
     * @param month
     * @param modelMap
     * @return
     */
    @GetMapping("selectIkkaInfo")
    @ResponseBody
    public HashMap<String, Object> selectIkkaInfo(@RequestParam("employeeId") String employeeId,
                                                  @RequestParam("year") String year,
                                                  @RequestParam("month") String month,
                                                  ModelMap modelMap) {
        //初期化
        tmgScheduleBean.setExecuteParameters(year, month, employeeId, modelMap);
        String sectionid = "201000201010";
        String groupid = "201000201010|000000";
        return tmgScheduleBean.selectIkkaInfo(sectionid, groupid);
    }

    /**
     * 予定作成更新処理を行います
     *
     * @param employeeId
     * @param year
     * @param month
     * @param modelMap
     * @return
     */
    @GetMapping("executeEditMonthlyUSchedule")
    @ResponseBody
    public HashMap<String, Object> executeEditMonthlyUSchedule(@RequestParam("employeeId") String employeeId,
                                                               @RequestParam("year") String year,
                                                               @RequestParam("month") String month,
                                                               ModelMap modelMap) {
        //初期化
        tmgScheduleBean.setExecuteParameters(year, month, employeeId, modelMap);
        tmgScheduleBean.executeEditMonthlyUSchedule();
        return null;
    }




}