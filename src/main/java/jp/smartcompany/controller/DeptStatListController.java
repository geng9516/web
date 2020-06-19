package jp.smartcompany.controller;

import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.attendanceBook.AttendanceBookBean;
import jp.smartcompany.job.modules.tmg.attendanceBook.dto.AttendanceDateInfoDTO;
import jp.smartcompany.job.modules.tmg.attendanceBook.vo.AttendanceBookHolidayInfoVO;
import jp.smartcompany.job.modules.tmg.attendanceBook.vo.AttendanceExistsVO;
import jp.smartcompany.job.modules.tmg.deptstatlist.DeptStatListBean;
import jp.smartcompany.job.modules.tmg.deptstatlist.vo.LinkOfMonthVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nie Wanqun
 * @description test controller
 * @objectSource
 * @date 2020/05/18
 **/
@RestController
@RequestMapping("sys/deptStatList")
public class DeptStatListController {

    @Autowired
    private DeptStatListBean deptStatListBean;

    /**
     * 勤務年月を返却します
     *
     * @param psDBBean
     * @return
     */
    @GetMapping("getObjWorkDate")
    @ResponseBody
    public String getObjWorkDate(@RequestAttribute("txtAction") String txtAction,
                                 @RequestAttribute("txtDYYYYMM") String txtDYYYYMM,
                                 @RequestAttribute("page") String page,
                                 @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        //初期化対象
        deptStatListBean.execute(txtAction, txtDYYYYMM, page, psDBBean);
        return deptStatListBean.getObjWorkDate();
    }

    /**
     * 勤怠トリガーテーブルへのインサート処理を実行します。
     *
     * @return
     */
    @GetMapping("executeInsertTmgTrigger")
    @ResponseBody
    public void executeInsertTmgTrigger(@RequestAttribute("txtAction") String txtAction,
                                        @RequestAttribute("txtDYYYYMM") String txtDYYYYMM,
                                        @RequestAttribute("page") String page,
                                        @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        //初期化対象
        deptStatListBean.execute(txtAction, txtDYYYYMM, page, psDBBean);
        deptStatListBean.executeInsertTmgTrigger(psDBBean);
    }

    /**
     * 前月と翌月リンクを取得
     *
     * @return
     */
    @GetMapping("buildSQLSelectLinkOfPreMonth")
    @ResponseBody
    public LinkOfMonthVO buildSQLSelectLinkOfMonth(@RequestAttribute("txtAction") String txtAction,
                                                   @RequestAttribute("txtDYYYYMM") String txtDYYYYMM,
                                                   @RequestAttribute("page") String page,
                                                   @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        //初期化対象
        deptStatListBean.execute(txtAction, txtDYYYYMM, page, psDBBean);
        LinkOfMonthVO linkOfMonthVO = deptStatListBean.buildSQLSelectLinkOfMonth();
        return linkOfMonthVO;
    }

    /**
     * 参照画面表示処理を実行します。
     *
     * @return
     */
    @GetMapping("executeDispStatList")
    @ResponseBody
    public Map executeDispStatList(@RequestAttribute("txtAction") String txtAction,
                                   @RequestAttribute("txtDYYYYMM") String txtDYYYYMM,
                                   @RequestAttribute("page") String page,
                                   @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        //初期化対象
        deptStatListBean.execute(txtAction, txtDYYYYMM, page, psDBBean);
        Map resultMap = deptStatListBean.executeDispStatList(psDBBean);
        return resultMap;
    }

    /**
     * 部署別統計情報データCSV出力処理
     *
     * @return
     */
    @GetMapping("executeDownloadDownload")
    @ResponseBody
    public void executeDownloadDownload(@RequestAttribute("txtAction") String txtAction,
                                        @RequestAttribute("txtDYYYYMM") String txtDYYYYMM,
                                        @RequestAttribute("page") String page,
                                        @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        //初期化対象
        deptStatListBean.execute(txtAction, txtDYYYYMM, page, psDBBean);
        deptStatListBean.executeDownloadDownload(psDBBean);

    }

}
