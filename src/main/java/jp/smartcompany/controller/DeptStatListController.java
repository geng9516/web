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
    public String getObjWorkDate(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        //初期化対象
        deptStatListBean.execute(psDBBean);
        return deptStatListBean.getObjWorkDate();
    }

    /**
     * 勤怠トリガーテーブルへのインサート処理を実行します。
     *
     * @return
     */
    @GetMapping("executeInsertTmgTrigger")
    @ResponseBody
    public void executeInsertTmgTrigger(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        //初期化対象
        deptStatListBean.execute(psDBBean);
        deptStatListBean.executeInsertTmgTrigger(psDBBean);
    }

    /**
     * 前月と翌月リンクを取得
     *
     * @return
     */
    @GetMapping("buildSQLSelectLinkOfPreMonth")
    @ResponseBody
    public LinkOfMonthVO buildSQLSelectLinkOfMonth(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        //初期化対象
        deptStatListBean.execute(psDBBean);
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
    public Map executeDispStatList(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        //初期化対象
        deptStatListBean.execute(psDBBean);
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
    public void executeDownloadDownload(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        //初期化対象
        deptStatListBean.execute(psDBBean);
        deptStatListBean.executeDownloadDownload(psDBBean);

    }

}
