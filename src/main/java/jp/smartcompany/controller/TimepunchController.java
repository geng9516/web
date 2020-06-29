package jp.smartcompany.controller;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.timepunch.TmgTimePunchBean;
import jp.smartcompany.job.modules.tmg.timepunch.dto.DutyAndRelaxDateDTO;
import jp.smartcompany.job.modules.tmg.timepunch.dto.ScheduleInfoDTO;
import jp.smartcompany.job.modules.tmg.timepunch.vo.SystemTimerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author 陳毅力
 * @description 打刻
 * @objectSource null
 * @date 2020/06/25
 **/
@RestController
@RequestMapping("sys/timePunch")
public class TimepunchController {

    @Autowired
    private TmgTimePunchBean tmgTimePunchBean;

    /**
     * http://localhost:6879/sys/timePunch/execTimePunch?employeeId=46402406&psAction=ACT_EXEC_OPEN
     *
     * @param employeeId
     * @param psAction   ACT_EXEC_OPEN    ACT_EXEC_CLOSE
     * @param psDBBean
     * @return TRUE 打刻成功　　FALSE　打刻失敗
     */
    @PostMapping("execTimePunch")
    public boolean execTimePunch(@RequestParam("employeeId") String employeeId,
                                 @RequestParam("psAction") String psAction,
                                 @RequestAttribute("BeanName") PsDBBean psDBBean) {
        tmgTimePunchBean.setExecuteParameters(null, psDBBean);
        return tmgTimePunchBean.execTimePunch(employeeId, psAction);
    }

    /**
     * http://localhost:6879/sys/timePunch/isNotTimePunch?employeeId=46402406
     *
     * @param employeeId
     * @param psDBBean
     */
    @PostMapping("isNotTimePunch")
    public boolean isNotTimePunch(@RequestParam("employeeId") String employeeId,
                                  @RequestAttribute("BeanName") PsDBBean psDBBean) {
        tmgTimePunchBean.setExecuteParameters(null, psDBBean);
        return tmgTimePunchBean.isNotTimePunch(employeeId);
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