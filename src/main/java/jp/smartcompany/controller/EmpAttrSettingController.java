package jp.smartcompany.controller;


import cn.hutool.core.util.StrUtil;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.calendar.CalendarBean;
import jp.smartcompany.job.modules.tmg.empattrsetting.EmpAttrSettingBean;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.BeginDateEditDispVo;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.EditDispVo;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.EmpAttsetDispVo;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.EmpDispVo;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DispMonthlyVO;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("sys/userprofile")
public class EmpAttrSettingController {

    @Autowired
    private EmpAttrSettingBean empAttrSettingBean;



    /**
     * 個人属性一覧
     *
     * @param psDBBean
     * @return
     */
    @GetMapping("screenDisp")
    public EmpDispVo actionScreenDisp(@RequestParam("baseDate") String baseDate,
            @RequestParam(value = "page" ,required = false) String page,
            @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        TmgReferList referList  = new TmgReferList();
        if (StrUtil.hasEmpty(page)){
            page="1";
        }
        return empAttrSettingBean.actionScreenDisp(baseDate,page,psDBBean,referList);
    }


    /**
     * 表头获取
     *
     * @param psDBBean
     * @return
     */
    @GetMapping("tableHeader")
    public List<EmpAttsetDispVo> getTableHeader(@RequestParam("baseDate") String baseDate,
                                                @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {

        return empAttrSettingBean.getTableHeader(baseDate,psDBBean);
    }



    /**
     * 平均勤務時間表示処理
     *
     * @param psDBBean
     * @return
     */
    @GetMapping("avgWorkTimeEditDisp")
    public EditDispVo actionScreenDispEditAvgWorktime(@RequestParam("empId") String empId,
                                                      @RequestParam("baseDate") String baseDate,
                                                      @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {

        return empAttrSettingBean.actionScreenDispEditAvgWorktime(empId,baseDate,psDBBean);
    }


    /**
     * 勤務开始時間表示処理
     *
     * @param psDBBean
     * @return
     */
    @GetMapping("beginDateEditDisp")
    public BeginDateEditDispVo actionScreenDispEditBeginDate(@RequestParam("empId") String empId,
                                                             @RequestParam("baseDate") String baseDate,
                                                             @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {

        return empAttrSettingBean.actionScreenDispEditBeginDate(empId,baseDate,psDBBean);
    }

}
