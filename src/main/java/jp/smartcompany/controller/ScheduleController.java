package jp.smartcompany.controller;

import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.schedule.TmgScheduleBean;
import jp.smartcompany.job.modules.tmg.schedule.dto.TargetUserDetailDTO;
import jp.smartcompany.job.modules.tmg.schedule.dto.TmgWeekPatternDTO;
import jp.smartcompany.job.modules.tmg.schedule.vo.ModifiedDateVO;
import jp.smartcompany.job.modules.tmg.schedule.vo.TmgWeekPatternVO;
import jp.smartcompany.job.modules.tmg.schedule.vo.ScheduleInfoVO;
import jp.smartcompany.job.modules.tmg.schedule.vo.WeekWorkType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author 陳毅力
 * @description 予定作成コントロール
 * @objectSource null
 * @date 2020/05/29
 **/
@RestController
@RequestMapping("sys/schedule")
public class ScheduleController {

    @Autowired
    private TmgScheduleBean tmgScheduleBean;

    /**
     * 前月時間 と　翌月時間
     * 公休日数	基準日数	基準時間	年次休暇残
     * 勤務予定 リスト
     * http://localhost:6879/sys/schedule/selectScheduleInfo?employeeId=46402406&txtBaseDate=2020/03/15&txtEndDate=2020/04/11
     * http://localhost:6879/sys/schedule/selectScheduleInfo?employeeId=C1000015&txtBaseDate=2020/03/15&txtEndDate=2020/04/11 (変形労働制)
     * txtBaseDateとtxtEndDateは初めてのアクセスの場合、空値可能です
     *
     * @param txtBaseDate 2020/03/15　　または　空値
     * @param txtEndDate  2020/04/11　　または　空値
     * @param psDBBean
     * @return ScheduleInfoVO
     */
    @GetMapping("selectScheduleInfo")
    @ResponseBody
    public ScheduleInfoVO selectScheduleInfo(@RequestParam("txtBaseDate") String txtBaseDate,
                                             @RequestParam("txtEndDate") String txtEndDate,
                                             @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化
        tmgScheduleBean.setExecuteParameters(txtBaseDate, txtEndDate, psDBBean);
        return tmgScheduleBean.selectPaidHolidayInfo(txtBaseDate, txtEndDate);

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
     * [区分][出張][勤務パターン]
     * http://localhost:6879/sys/schedule/selectIkkaInfo?employeeId=46402406&txtBaseDate=&txtEndDate=
     * http://localhost:6879/sys/schedule/selectIkkaInfo?employeeId=C1000015&txtBaseDate=&txtEndDate= (変形労働制)
     *
     * @param txtBaseDate
     * @param txtEndDate
     * @param psDBBean
     * @return
     */
    @GetMapping("selectIkkaInfo")
    @ResponseBody
    public HashMap<String, Object> selectIkkaInfo(@RequestParam("txtBaseDate") String txtBaseDate,
                                                  @RequestParam("txtEndDate") String txtEndDate,
                                                  @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化
        tmgScheduleBean.setExecuteParameters(txtBaseDate, txtEndDate, psDBBean);
        return tmgScheduleBean.selectIkkaInfo();
    }

    /**
     * 予定作成更新処理を行います
     * <p>
     * http://localhost:6879/sys/schedule/executeEditMonthlyUSchedule?employeeId=46402406&txtBaseDate=&txtEndDate=
     * http://localhost:6879/sys/schedule/executeEditMonthlyUSchedule?employeeId=C1000015&txtBaseDate=&txtEndDate= (変形労働制)
     *
     * @param txtBaseDate
     * @param txtEndDate
     * @param psDBBean
     * @return
     */
    @GetMapping("executeEditMonthlyUScheduleTest")
    @ResponseBody
    public HashMap<String, Object> executeEditMonthlyUSchedule_test(@RequestParam("txtBaseDate") String txtBaseDate,
                                                                    @RequestParam("txtEndDate") String txtEndDate,
                                                                    @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化
        tmgScheduleBean.setExecuteParameters(txtBaseDate, txtEndDate, psDBBean);
        // tmgScheduleBean.executeEditMonthlyUSchedule();
        return null;
    }

    /**
     * 予定作成更新処理を行います
     * <p>
     * http://localhost:6879/sys/schedule/executeEditMonthlyUSchedule?employeeId=46402406&txtBaseDate=&txtEndDate=
     * http://localhost:6879/sys/schedule/executeEditMonthlyUSchedule?employeeId=C1000015&txtBaseDate=&txtEndDate= (変形労働制)
     *
     * @param txtBaseDate
     * @param txtEndDate
     * @param content
     * @param psDBBean
     * @return
     */
    @PostMapping("executeEditMonthlyUSchedule")
    @ResponseBody
    public HashMap<String, Object> executeEditMonthlyUSchedule(@RequestParam("txtBaseDate") String txtBaseDate,
                                                               @RequestParam("txtEndDate") String txtEndDate,
                                                               @RequestParam("content") String content,
                                                               @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化
        tmgScheduleBean.setExecuteParameters(txtBaseDate, txtEndDate, psDBBean);
        tmgScheduleBean.executeEditMonthlyUSchedule(content);
        return null;
    }

    /**
     * 週勤務パターン詳しい情報を取得する
     * http://localhost:6879/sys/schedule/selectWeekPatternInfo?employeeId=40010001&twp_nid=641&txtBaseDate=&txtEndDate=
     * http://localhost:6879/sys/schedule/selectWeekPatternInfo?employeeId=C1000015&twp_nid=641&txtBaseDate=&txtEndDate=
     *
     * @param txtBaseDate
     * @param txtEndDate
     * @param twp_nid
     * @param psDBBean
     * @return
     */
    @GetMapping("selectWeekPatternInfo")
    @ResponseBody
    public TmgWeekPatternVO selectCsvReference(@RequestParam("txtBaseDate") String txtBaseDate,
                                               @RequestParam("txtEndDate") String txtEndDate,
                                               @RequestParam("twp_nid") int twp_nid,
                                               @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化
        tmgScheduleBean.setExecuteParameters(txtBaseDate, txtEndDate, psDBBean);
        return tmgScheduleBean.selectCsvReference(twp_nid);
    }

    /**
     * 週勤務パターンlistを取得する
     * http://localhost:6879/sys/schedule/selectWeekPatternInfoList?employeeId=40010001&txtBaseDate=&txtEndDate=
     *
     * @param txtBaseDate
     * @param txtEndDate
     * @param psDBBean
     * @return
     */
    @GetMapping("selectWeekPatternInfoList")
    @ResponseBody
    public List<TmgWeekPatternVO> selectCsvReferenceList(@RequestParam("txtBaseDate") String txtBaseDate,
                                                         @RequestParam("txtEndDate") String txtEndDate,
                                                         @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化
        tmgScheduleBean.setExecuteParameters(txtBaseDate, txtEndDate, psDBBean);
        return tmgScheduleBean.selectCsvReference();
    }

    /**
     * 週勤務パターンを取得する
     * http://localhost:6879/sys/schedule/selectTmgWeekPatternList?employeeId=29042924&txtBaseDate=&txtEndDate=
     *
     * @param txtBaseDate
     * @param txtEndDate
     * @param psDBBean
     * @return
     */
    @GetMapping("selectTmgWeekPatternList")
    @ResponseBody
    public List<TmgWeekPatternDTO> selectTmgWeekPattern(@RequestParam("txtBaseDate") String txtBaseDate,
                                                        @RequestParam("txtEndDate") String txtEndDate,
                                                        @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化
        tmgScheduleBean.setExecuteParameters(txtBaseDate, txtEndDate, psDBBean);
        return tmgScheduleBean.selectTmgWeekPattern();
    }

    /**
     * 週勤務パターン登録画面　登録処理
     * http://localhost:6879/sys/schedule/executeMakeWeekPattern?employeeId=29042924&txtBaseDate=&txtEndDate=
     *
     * @param txtBaseDate
     * @param txtEndDate
     * @param psDBBean
     */
    @GetMapping("executeMakeWeekPattern")
    @ResponseBody
    public void executeMakeWeekPattern_UWPtn(@RequestParam("txtBaseDate") String txtBaseDate,
                                             @RequestParam("txtEndDate") String txtEndDate,
                                             @RequestParam("content") String content,
                                             @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化
        tmgScheduleBean.setExecuteParameters(txtBaseDate, txtEndDate, psDBBean);
        tmgScheduleBean.executeMakeWeekPattern_UWPtn(content);
    }

    /**
     * 週勤務パターン登録画面　登録処理
     * http://localhost:6879/sys/schedule/selectTargetUserDetail
     *
     * @param psDBBean
     */
    @GetMapping("selectTargetUserDetail")
    @ResponseBody
    public TargetUserDetailDTO selectTargetUserDetail(@RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化
        tmgScheduleBean.setExecuteParameters(null, null, psDBBean);
        return tmgScheduleBean.selectTargetUserDetail();
    }

    /**
     * TmgMonthlyの更新日取得(予定確認画面)
     * http://localhost:6879/sys/schedule/selectMonthlyModifiedDate?txtBaseDate=2020/07/01
     *
     * @param txtBaseDate
     * @param psDBBean
     */
    @GetMapping("selectMonthlyModifiedDate")
    @ResponseBody
    public ModifiedDateVO selectMonthlyModifiedDate(@RequestParam("txtBaseDate") String txtBaseDate,
                                                    @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化
        tmgScheduleBean.setExecuteParameters(txtBaseDate, null, psDBBean);
        ModifiedDateVO modifiedDateVO = new ModifiedDateVO();
        modifiedDateVO.setModifiedDate(tmgScheduleBean.selectMonthlyModifiedDate());
        return modifiedDateVO;
    }

    /**
     * 予定確認フラグのレコードを挿入します。
     * http://localhost:6879/sys/schedule/updateSchedulePermStatus?txtBaseDate=2020/07/01
     *
     * @param txtBaseDate
     * @param psDBBean
     */
    @GetMapping("updateSchedulePermStatus")
    @ResponseBody
    public boolean updateSchedulePermStatus(@RequestParam("txtBaseDate") String txtBaseDate,
                                            @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化
        tmgScheduleBean.setExecuteParameters(txtBaseDate, null, psDBBean);
        return tmgScheduleBean.updateSchedulePermStatus();
    }

    /**
     * 週勤務パターン画面に勤務区分リスト
     * http://localhost:6879/sys/schedule/selectWeekPtn
     *
     * @param psDBBean
     * @return
     */
    @GetMapping("selectWeekPtn")
    @ResponseBody
    public List<WeekWorkType> selectWeekPtn(@RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化
        tmgScheduleBean.setExecuteParameters(null, null, psDBBean);
        return tmgScheduleBean.selectWeekPtn();
    }

    /**
     * 週勤務パターン画面に週勤務パターンを削除する
     * http://localhost:6879/sys/schedule/deleteWeekPtn?twp_nid=641&txtBaseDate=&txtEndDate=
     *
     * @param txtBaseDate
     * @param txtEndDate
     * @param twp_nid
     * @param psDBBean
     * @return
     */
    @GetMapping("deleteWeekPtn")
    @ResponseBody
    public List<WeekWorkType> deleteWeekPtn(@RequestParam("txtBaseDate") String txtBaseDate,
                                            @RequestParam("txtEndDate") String txtEndDate,
                                            @RequestParam("twp_nid") int twp_nid, @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化
        tmgScheduleBean.setExecuteParameters(null, null, psDBBean);
        return tmgScheduleBean.selectWeekPtn();
    }

}