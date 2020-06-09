package jp.smartcompany.controller;

import jp.smartcompany.job.modules.tmg.schedule.TmgScheduleBean;
import jp.smartcompany.job.modules.tmg.schedule.dto.TmgWeekPatternDTO;
import jp.smartcompany.job.modules.tmg.schedule.vo.TmgWeekPatternVO;
import jp.smartcompany.job.modules.tmg.schedule.vo.ScheduleInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

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
     * 　http://localhost:6879/sys/schedule/selectScheduleInfo?employeeId=29042924&year=2020&month=04
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
     * http://localhost:6879/sys/schedule/defaultDate
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
     * 　http://localhost:6879/sys/schedule/selectLinkOfNextMonth?employeeId=29042924&year=2020&month=04
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
     * 　http://localhost:6879/sys/schedule/selectLinkOfPreMonth?employeeId=29042924&year=2020&month=04
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
     * 　http://localhost:6879/sys/schedule/selectIkkaInfo?employeeId=29042924&year=2020&month=04
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
     * 　http://localhost:6879/sys/schedule/executeEditMonthlyUSchedule?employeeId=29042924&year=2020&month=04
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

    /**
     * 週勤務パターンを取得する
     * http://localhost:6879/sys/schedule/selectWeekPatternInfo?employeeId=29042924&year=2020&month=04&twp_nid=663
     *
     * @param year
     * @param month
     * @param employeeId
     * @param twp_nid
     * @param modelMap
     * @return
     */
    @GetMapping("selectWeekPatternInfo")
    @ResponseBody
    public TmgWeekPatternVO selectCsvReference(@RequestParam("year") String year,
                                               @RequestParam("month") String month,
                                               @RequestParam("employeeId") String employeeId,
                                               @RequestParam("twp_nid") int twp_nid,
                                               ModelMap modelMap) {
        //初期化
        tmgScheduleBean.setExecuteParameters(year, month, employeeId, modelMap);
        return tmgScheduleBean.selectCsvReference(employeeId, twp_nid);
    }


    /**
     * 週勤務パターンlistを取得する
     * http://localhost:6879/sys/schedule/selectWeekPatternInfoList?employeeId=29042924&year=2020&month=04
     *
     * @param year
     * @param month
     * @param employeeId
     * @param modelMap
     * @return
     */
    @GetMapping("selectWeekPatternInfoList")
    @ResponseBody
    public List<TmgWeekPatternVO> selectCsvReferenceList(@RequestParam("year") String year,
                                                         @RequestParam("month") String month,
                                                         @RequestParam("employeeId") String employeeId,
                                                         ModelMap modelMap) {
        //初期化
        tmgScheduleBean.setExecuteParameters(year, month, employeeId, modelMap);
        return tmgScheduleBean.selectCsvReference(employeeId);
    }


    /**
     * 週勤務パターンを取得する
     * http://localhost:6879/sys/schedule/selectTmgWeekPatternList?employeeId=29042924&year=2020&month=04
     *
     * @param year
     * @param month
     * @param employeeId
     * @param modelMap
     * @return
     */
    @GetMapping("selectTmgWeekPatternList")
    @ResponseBody
    public List<TmgWeekPatternDTO> selectTmgWeekPattern(@RequestParam("year") String year,
                                                        @RequestParam("month") String month,
                                                        @RequestParam("employeeId") String employeeId,
                                                        ModelMap modelMap) {
        //初期化
        tmgScheduleBean.setExecuteParameters(year, month, employeeId, modelMap);
        return tmgScheduleBean.selectTmgWeekPattern(year, month);
    }


    /**
     * 週勤務パターン登録画面　登録処理
     * http://localhost:6879/sys/schedule/executeMakeWeekPattern?employeeId=29042924&year=2020&month=04
     *
     * @param year
     * @param month
     * @param employeeId
     * @param modelMap
     */
    @GetMapping("executeMakeWeekPattern")
    @ResponseBody
    public void executeMakeWeekPattern_UWPtn(@RequestParam("year") String year,
                                             @RequestParam("month") String month,
                                             @RequestParam("employeeId") String employeeId,
                                             ModelMap modelMap) {
        //初期化
        tmgScheduleBean.setExecuteParameters(year, month, employeeId, modelMap);
        tmgScheduleBean.executeMakeWeekPattern_UWPtn();
    }


}