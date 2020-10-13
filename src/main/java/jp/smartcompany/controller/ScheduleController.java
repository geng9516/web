package jp.smartcompany.controller;

import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.schedule.TmgScheduleBean;
import jp.smartcompany.job.modules.tmg.schedule.dto.TargetUserDetailDTO;
import jp.smartcompany.job.modules.tmg.schedule.dto.TmgWeekPatternDTO;
import jp.smartcompany.job.modules.tmg.schedule.vo.*;
import jp.smartcompany.job.modules.tmg.tmgresults.TmgResultsBean;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.HatuReiVo;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
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
    @Autowired
    private TmgResultsBean tmgResultsBean;

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
        if (null == txtBaseDate || null == txtEndDate || "".equals(txtBaseDate) || "".equals(txtEndDate)) {
            //基準時間を制限
            return new ScheduleInfoVO();
        }
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
     * @param sectionid
     * @param groupid
     * @param baseDate
     * @param custId
     * @param compId
     * @param language
     * @return
     */
    @GetMapping("selectIkkaInfo2")
    @ResponseBody
    public HashMap<String, Object> selectIkkaInfo2(
            @RequestParam("sectionid") String sectionid,
            @RequestParam("groupid") String groupid,
            @RequestParam("baseDate") String baseDate,
            @RequestParam("custId") String custId,
            @RequestParam("compId") String compId,
            @RequestParam("language") String language) {
        return tmgScheduleBean.selectIkkaInfo(sectionid, groupid, baseDate, custId, compId, language);
    }


    /**
     * [区分][出張][勤務パターン]
     * http://localhost:6879/sys/schedule/selectIkkaInfo?employeeId=46402406&txtBaseDate=&txtEndDate=
     * http://localhost:6879/sys/schedule/selectIkkaInfo?employeeId=C1000015&txtBaseDate=&txtEndDate= (変形労働制)
     *
     * @param baseDateIkka
     * @return
     */
    @GetMapping("selectIkkaInfo")
    @ResponseBody
    public HashMap<String, Object> selectIkkaInfo(
            @RequestParam("baseDateIkka") String baseDateIkka,
            @RequestAttribute("BeanName") PsDBBean psDBBean) {
        return tmgScheduleBean.selectIkkaInfo(baseDateIkka, psDBBean);
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
        tmgScheduleBean.executeEditMonthlyUSchedule(content, psDBBean);
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
        return tmgScheduleBean.selectCsvReference(twp_nid, psDBBean);
    }

    /**
     * 週勤務パターンlistを取得する
     * http://localhost:6879/sys/schedule/selectWeekPatternInfoList?employeeId=40010001&txtBaseDate=&txtEndDate=
     *
     * @param psDBBean
     * @return
     */
    @GetMapping("selectWeekPatternInfoList")
    @ResponseBody
    public List<TmgWeekPatternVO> selectCsvReferenceList(@RequestParam("twp_nid") int twp_nid,
                                                         @RequestAttribute("BeanName") PsDBBean psDBBean) {
        return tmgScheduleBean.selectCsvReferenceList(twp_nid, psDBBean);
    }

    /**
     * 週勤務パターンを取得する
     * http://localhost:6879/sys/schedule/selectTmgWeekPatternList?employeeId=29042924&txtBaseDate=&txtEndDate=
     *
     * @param txtBaseDate
     * @param psDBBean
     * @return
     */
    @GetMapping("selectTmgWeekPatternList")
    @ResponseBody
    public List<TmgWeekPatternDTO> selectTmgWeekPattern(@RequestParam("txtBaseDate") String txtBaseDate,
                                                        @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //   System.out.println("controller-->selectTmgWeekPattern");
        return tmgScheduleBean.selectTmgWeekPattern(txtBaseDate, psDBBean);
    }

    /**
     * 週勤務パターン登録画面　登録処理
     * http://localhost:6879/sys/schedule/executeMakeWeekPattern?employeeId=29042924&txtBaseDate=&txtEndDate=
     *
     * @param txtBaseDate
     * @param txtEndDate
     * @param psDBBean
     */
    @PostMapping("executeMakeWeekPattern")
    @ResponseBody
    public GlobalResponse executeMakeWeekPattern_UWPtn(@RequestParam("txtBaseDate") String txtBaseDate,
                                                       @RequestParam("txtEndDate") String txtEndDate,
                                                       @RequestParam("content") String content,
                                                       @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //   System.out.println("controller-->executeMakeWeekPattern_UWPtn");
        return tmgScheduleBean.executeMakeWeekPattern_UWPtn(content, psDBBean);
    }

    /**
     * 週勤務パターンの適用時間を更新する
     *
     * @param twp_dstartdate 　--> 適用開始時間
     * @param twp_denddate   　-->　適用終了時間
     * @param twp_nid        　-->　週勤務パターン　ユニークなid
     *                       //* @param psDBBean       　-->　psDBBean　txtACTIONが必要です
     * @return
     */
    @PostMapping("executeUpdateWeekPattern")
    @ResponseBody
    public GlobalResponse executeUpdateWeekPattern(@RequestParam("twp_dstartdate") String twp_dstartdate,
                                                   @RequestParam("twp_denddate") String twp_denddate,
                                                   @RequestParam("twp_nid") String twp_nid,
                                                   @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //    System.out.println("controller-->executeUpdateWeekPattern");
        return tmgScheduleBean.executeMakeWeekPattern_UWPtn(twp_dstartdate, twp_denddate, twp_nid, psDBBean);
    }


    /**
     * 対象ユーザー情報 (暫く　使ってない)
     * http://localhost:6879/sys/schedule/selectTargetUserDetail
     *
     * @param psDBBean
     */
    @GetMapping("selectTargetUserDetail")
    @ResponseBody
    public TargetUserDetailDTO selectTargetUserDetail(@RequestAttribute("BeanName") PsDBBean psDBBean) {
        System.out.println("controller-->selectTargetUserDetail");
        //初期化
        tmgScheduleBean.setExecuteParameters(psDBBean);
        return tmgScheduleBean.selectTargetUserDetail();
    }

    /**
     * TmgMonthlyの更新日取得(予定確認画面)　 (暫く　使ってない)
     * http://localhost:6879/sys/schedule/selectMonthlyModifiedDate?txtBaseDate=2020/07/01
     *
     * @param txtBaseDate
     * @param psDBBean
     */
    @GetMapping("selectMonthlyModifiedDate")
    @ResponseBody
    public ModifiedDateVO selectMonthlyModifiedDate(@RequestParam("txtBaseDate") String txtBaseDate,
                                                    @RequestAttribute("BeanName") PsDBBean psDBBean) {

        //  System.out.println("controller-->selectMonthlyModifiedDate");
        ModifiedDateVO modifiedDateVO = new ModifiedDateVO();
        modifiedDateVO.setModifiedDate(tmgScheduleBean.selectMonthlyModifiedDate(psDBBean, txtBaseDate));
        return modifiedDateVO;
    }

    /**
     * 予定確認フラグのレコードを挿入します。  　 (暫く　使ってない)
     * http://localhost:6879/sys/schedule/updateSchedulePermStatus?txtBaseDate=2020/07/01
     *
     * @param txtBaseDate
     * @param psDBBean
     */
    @GetMapping("updateSchedulePermStatus")
    @ResponseBody
    public boolean updateSchedulePermStatus(@RequestParam("txtBaseDate") String txtBaseDate,
                                            @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //    System.out.println("controller-->updateSchedulePermStatus");
        return tmgScheduleBean.updateSchedulePermStatus(psDBBean, txtBaseDate);
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
        //    System.out.println("controller-->selectWeekPtn");
        return tmgScheduleBean.selectWeekPtn(psDBBean);
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
    public boolean deleteWeekPtn(@RequestParam("txtBaseDate") String txtBaseDate,
                                 @RequestParam("txtEndDate") String txtEndDate,
                                 @RequestParam("twp_nid") String twp_nid, @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //   System.out.println("controller-->deleteWeekPtn");
        return tmgScheduleBean.deleteWeekPtn(twp_nid, psDBBean);
    }

    /**
     * 発令日表示処理
     * http://localhost:6879/sys/schedule/hatuRei
     *
     * @param psDBBean
     * @return
     */
    @GetMapping("hatuRei")
    @ResponseBody
    public HatuReiVo getHatuReiVoInfo(@RequestParam("txtBaseDate") String txtBaseDate, @RequestAttribute("BeanName") PsDBBean psDBBean) {

        tmgResultsBean.setThisMonth(txtBaseDate);
        tmgResultsBean.setReferList(TmgReferList.TREEVIEW_TYPE_EMP, psDBBean);
        return tmgResultsBean.getHatuReiVoInfo(tmgResultsBean.getReferList().getRecordDate(), psDBBean);
    }

    /**
     * 勤務予定時間リスト
     * http://localhost:6879/sys/schedule/selectScheduleDateList
     *
     * @param psDBBean
     * @param baseDate
     * @return
     */
    @GetMapping("selectScheduleDateList")
    @ResponseBody
    public List<ScheduleDateListVO> selectScheduleDateList(@RequestAttribute("BeanName") PsDBBean psDBBean, @RequestParam("baseDate") String baseDate) {
        return tmgScheduleBean.selectScheduleDateList(psDBBean, baseDate);
    }

    /**
     * 「編集ボタン」の使用可否設定を取得します
     *
     * @param psDBBean
     * @return
     */
    @GetMapping("isEditableSchedule")
    @ResponseBody
    public boolean isEditableSchedule(@RequestAttribute("BeanName") PsDBBean psDBBean) {
        return tmgScheduleBean.isEditableSchedule(psDBBean);
    }

    /**
     * 編集権限を制御する
     *
     * @param baseDate
     * @param psDBBean
     * @return
     */
    @GetMapping("isEditable")
    @ResponseBody
    public GlobalResponse isEditable(@RequestParam("baseDate") String baseDate, @RequestAttribute("BeanName") PsDBBean psDBBean) {
        return tmgScheduleBean.isEditable(psDBBean, baseDate);
    }

}