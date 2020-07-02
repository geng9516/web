package jp.smartcompany.controller;


import cn.hutool.core.util.StrUtil;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.calendar.CalendarBean;
import jp.smartcompany.job.modules.tmg.calendar.vo.CalendarVo;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DispMonthlyVO;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("sys/calendar")
public class CalendarController {


    @Autowired
    private CalendarBean calendarBean;


    /**
     * 表示月遷移リスト情報を取得する
     * @throws Exception
     */
    @GetMapping("getCalendar")
    @ResponseBody
    public CalendarVo getCalendar(@RequestParam(value="year",required = false) String year,
            @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        if(StrUtil.hasEmpty(year)){
            year= TmgUtil.getSysdate().substring(0,4);
        }
        TmgReferList referList= new TmgReferList(psDBBean,"Calendar",
                year+"/01/01",TmgReferList.TREEVIEW_TYPE_LIST, true);
        return calendarBean.getCalendar(year,psDBBean,referList);
    }
}
