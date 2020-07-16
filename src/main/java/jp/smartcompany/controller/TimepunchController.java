package jp.smartcompany.controller;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.timepunch.TmgTimePunchBean;
import jp.smartcompany.job.modules.tmg.timepunch.dto.DutyAndRelaxDateDTO;
import jp.smartcompany.job.modules.tmg.timepunch.dto.ScheduleInfoDTO;
import jp.smartcompany.job.modules.tmg.timepunch.vo.ClockResultVO;
import jp.smartcompany.job.modules.tmg.timepunch.vo.SystemTimerVO;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("selectScheduleInfo")
    public ScheduleInfoDTO selectScheduleInfo(@RequestParam("targetDate") String targetDate,
                                              @RequestAttribute("BeanName") PsDBBean psDBBean) {
        tmgTimePunchBean.setExecuteParameters(targetDate, psDBBean);
        return tmgTimePunchBean.selectScheduleInfo(targetDate);
    }

    /**
     * http://localhost:6879/sys/timePunch/selectDutyAndRelaxDate?targetDate=2020/04/30
     *
     * @param targetDate
     * @param psDBBean
     * @return
     */
    @GetMapping("selectDutyAndRelaxDate")
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

}