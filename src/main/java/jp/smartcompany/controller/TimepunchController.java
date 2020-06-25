package jp.smartcompany.controller;


import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.timepunch.TmgTimePunchBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}