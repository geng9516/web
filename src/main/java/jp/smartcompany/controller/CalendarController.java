package jp.smartcompany.controller;


import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.calendar.CalendarBean;
import jp.smartcompany.job.modules.tmg.calendar.dto.CalendarYearDto;
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
    public CalendarVo getCalendar(@RequestParam(value="year",required = false) String year,
            @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        if(StrUtil.hasEmpty(year)){
            year= TmgUtil.getSysdate().substring(0,4);
        }
        TmgReferList referList= new TmgReferList(psDBBean,"Calendar",
                year+"/01/01",TmgReferList.TREEVIEW_TYPE_LIST, true);
        return calendarBean.getCalendar(year,psDBBean,referList);
    }


    /**
     * 新規カレンダーを作成
     * @throws Exception
     */
    @PostMapping("insertCalendar")
    @ResponseBody
    public GlobalResponse insertCalendar(@RequestBody CalendarYearDto CalendarYearDto,
            @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        TmgReferList referList= new TmgReferList(psDBBean,"Calendar",
                CalendarYearDto.getMonthlist().get(0).getMonth()
                ,TmgReferList.TREEVIEW_TYPE_LIST, true);
        return calendarBean.insertCalendar(CalendarYearDto,psDBBean,referList);
    }


    /**
     * カレンダーを更新
     * @throws Exception
     */
    @PostMapping("updateCalendar")
    @ResponseBody
    public GlobalResponse updateCalendar(@RequestBody CalendarYearDto CalendarYearDto,
                                         @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        if(CalendarYearDto.getMonthlist().size()>0){
            TmgReferList referList= new TmgReferList(psDBBean,"Calendar",
                    CalendarYearDto.getMonthlist().get(0).getMonth()
                    ,TmgReferList.TREEVIEW_TYPE_LIST, true);
            return calendarBean.updateCalendar(CalendarYearDto,psDBBean,referList);
        }else{
            return GlobalResponse.ok("更新なし");
        }


    }

    /**
     * カレンダーを削除
     * @throws Exception
     */
    @PostMapping("deleteCalendar")
    @ResponseBody
    public GlobalResponse deleteCalendar(@RequestParam(value="year") String year,
                                         @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        TmgReferList referList= new TmgReferList(psDBBean,"Calendar",
                year+"/01/01"
                ,TmgReferList.TREEVIEW_TYPE_LIST, true);
        return calendarBean.deleteCalendar(year,psDBBean,referList);
    }

}
