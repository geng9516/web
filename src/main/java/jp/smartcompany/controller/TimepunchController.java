package jp.smartcompany.controller;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.timepunch.TmgTimePunchBean;
import jp.smartcompany.job.modules.tmg.timepunch.dto.DutyAndRelaxDateDTO;
import jp.smartcompany.job.modules.tmg.timepunch.dto.ScheduleInfoDTO;
import jp.smartcompany.job.modules.tmg.timepunch.vo.ClockInfoVO;
import jp.smartcompany.job.modules.tmg.timepunch.vo.ClockResultVO;
import jp.smartcompany.job.modules.tmg.timepunch.vo.SystemTimerVO;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author 陳毅力
 * @description 打刻
 * @objectSource ps.c01.tmg.TmgTimePunch.TmgTimePunchBean
 * @date 2020/06/25
 **/
@RestController
@RequestMapping("sys/timePunch")
public class TimepunchController {

    @Autowired
    private TmgTimePunchBean tmgTimePunchBean;

    /**
     * http://localhost:6879/sys/timePunch/execTimePunch
     *
     * @param employeeId
     * @param compId
     * @param custId
     * @param psAction   ACT_EXEC_OPEN    ACT_EXEC_CLOSE
     * @param psDBBean
     * @return
     */
    @PostMapping("execTimePunch")
    public ClockResultVO execTimePunch(@RequestParam("employeeId") String employeeId,
                                       @RequestParam("custId") String custId,
                                       @RequestParam("compId") String compId,
                                       @RequestParam("psAction") String psAction,
                                       @RequestAttribute("BeanName") PsDBBean psDBBean) {
        tmgTimePunchBean.setExecuteParameters(null, psDBBean);
        return tmgTimePunchBean.execTimePunch(employeeId, custId, compId, psAction);
    }

    /**
     * http://localhost:6879/sys/timePunch/isNotTimePunch?employeeId=46402406
     *
     * @param employeeId
     * @param psDBBean
     */
    @PostMapping("isNotTimePunch")
    public boolean isNotTimePunch(@RequestParam("employeeId") String employeeId,
                                  @RequestParam("custId") String custId,
                                  @RequestParam("compId") String compId,
                                  @RequestAttribute("BeanName") PsDBBean psDBBean) {
        // tmgTimePunchBean.setExecuteParameters(null, psDBBean);
        return tmgTimePunchBean.isNotTimePunch(custId, compId, employeeId);
    }

    /**
     * http://localhost:6879/sys/timePunch/selectScheduleInfo?targetDate=2020/04/30
     *
     * @param targetDate
     * @param psDBBean
     * @return
     */
    @PostMapping("selectScheduleInfo")
    public Object[] selectScheduleInfo(@RequestParam("targetDate") String targetDate,
                                       @RequestAttribute("BeanName") PsDBBean psDBBean) {
        tmgTimePunchBean.setExecuteParameters(targetDate, psDBBean);
        ScheduleInfoDTO scheduleInfoDTO = tmgTimePunchBean.selectScheduleInfo(targetDate);
        Object[] scheduleData = tmgTimePunchBean.scheduleInfoConverse(scheduleInfoDTO);
        return scheduleData;
    }

    /**
     * http://localhost:6879/sys/timePunch/selectDutyAndRelaxDate?targetDate=2020/04/30
     *
     * @param targetDate
     * @param psDBBean
     * @return
     */
    @PostMapping("selectDutyAndRelaxDate")
    public DutyAndRelaxDateDTO getDutyAndRelaxDate(@RequestParam("targetDate") String targetDate,
                                                   @RequestAttribute("BeanName") PsDBBean psDBBean) {
        tmgTimePunchBean.setExecuteParameters(targetDate, psDBBean);
        return tmgTimePunchBean.getDutyAndRelaxDate(targetDate);
    }

    /**
     * http://localhost:6879/sys/timePunch/serverTime
     * サーバー時間
     *
     * @return
     */
    @GetMapping("serverTime")
    public SystemTimerVO serverTime() {
        SystemTimerVO systemTimerVO = new SystemTimerVO();
        systemTimerVO.setSysdate(DateUtil.format(new Date(), "yyyy/MM/dd HH:mm:ss"));
        return systemTimerVO;
    }

    /**
     * 打刻
     *
     * @return
     */
    @PostMapping("stamping")
    @ResponseBody
    public ClockResultVO stamping(@RequestParam("pAction") String pAction,
                                  @RequestAttribute("BeanName") PsDBBean psDBBean) {
        tmgTimePunchBean.setExecuteParameters(null, psDBBean);
        ClockResultVO clockResultVO = new ClockResultVO();
        ClockInfoVO clockInfoVO = tmgTimePunchBean.selectClockInfo(psDBBean.getCustID(), psDBBean.getCompCode(), psDBBean.getUserCode());
        if (null == clockInfoVO.getTda_nopen_p() || "".equals(clockInfoVO.getTda_nopen_p())) {
            //予定データがない場合、打刻しない
            clockResultVO.setResultCode("20");
            clockResultVO.setResultMsg("今日は出勤しない日です");
        }
        if ("ACT_EXEC_OPEN".equals(pAction) && null != clockInfoVO.getNopen() && !"".equals(clockInfoVO.getNopen())) {
            //出勤打刻データがある場合、画面へ返却する
            clockResultVO.setResultCode("0");
            clockResultVO.setClockTime(clockInfoVO.getNopen());
            clockResultVO.setResultMsg("今日はもう出勤打刻しました");
            return clockResultVO;
        }
        //打刻
        clockResultVO = tmgTimePunchBean.execTimePunch(psDBBean.getUserCode(), psDBBean.getCustID(), psDBBean.getCompCode(), pAction);
        if ("ACT_EXEC_OPEN".equals(pAction) && "".equals(clockResultVO.getResultMsg())) {
            clockResultVO.setResultMsg("今日も一日頑張りましょう");
        }
        if ("ACT_EXEC_CLOSE".equals(pAction) && "".equals(clockResultVO.getResultMsg())) {
            clockResultVO.setResultMsg("今日も一日お疲れ様でした");
        }
        return clockResultVO;
    }

    /**
     * 打刻情報
     *
     * @return
     */
    @PostMapping("clockInfo")
    @ResponseBody
    public ClockInfoVO clockInfo(@RequestAttribute("BeanName") PsDBBean psDBBean) {
        ClockInfoVO clockInfoVO = tmgTimePunchBean.selectClockInfo(psDBBean.getCustID(), psDBBean.getCompCode(), psDBBean.getUserCode());
        return clockInfoVO;
    }

    /**
     * 裁量労働対象者かどうかを判定
     * 1:裁量労働制
     * 0:　その他
     *
     * @return
     */
    @PostMapping("isDiscretion")
    @ResponseBody
    public boolean isDiscretion(@RequestAttribute("BeanName") PsDBBean psDBBean) {
        return TmgUtil.isDiscretion(psDBBean);
    }


    /**
     * 超過勤務時間を取得する
     *
     * @return
     */
    @PostMapping("queryOverWorkTime")
    @ResponseBody
    @Cacheable(cacheNames = "queryOverWorkTime")
    public String[] queryOverWorkTime(@RequestAttribute("BeanName") PsDBBean psDBBean) {
        String[] overWorkTime = tmgTimePunchBean.selectOverWorkTime(psDBBean.getCustID(), psDBBean.getCompCode(), psDBBean.getUserCode());
        return overWorkTime;
    }


}