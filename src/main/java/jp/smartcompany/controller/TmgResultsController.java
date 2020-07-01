package jp.smartcompany.controller;


import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.tmgresults.TmgResultsBean;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DispMonthlyVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.GenericDetailVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.TodayThisMonthVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Nie Wanqun
 * @description test controller
 * @objectSource
 * @date 2020/06/11
 **/
@RestController
@RequestMapping("sys/tmgResults")
public class TmgResultsController {

    @Autowired
    private TmgResultsBean tmgResultsBean;

    /**
     * 勤務年月一覧を返却します
     *
     * @param psDBBean
     * @return
     */
    @GetMapping("getToday")
    @ResponseBody
    public TodayThisMonthVO getToday(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {

        //初期化対象
        return tmgResultsBean.getToday(psDBBean);
    }

    /**
     * 勤務年月一覧を返却します
     *
     * @param psDBBean
     * @return
     */
    @GetMapping("workDateList")
    @ResponseBody
    public Map getWorkDateList(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {

        //初期化対象
        TodayThisMonthVO today = tmgResultsBean.getToday(psDBBean);
        List<DispMonthlyVO> monthLy =tmgResultsBean.tmgInpInit(psDBBean);
        Map<String, Object> todayMonthLy = MapUtil.newHashMap();
        todayMonthLy.put("today",today);
        todayMonthLy.put("monthLy",monthLy);
        return todayMonthLy;
    }

    /**
     * 月次実績を返却します
     *
     * @param psDBBean
     * @return
     */
    @GetMapping("getMonthlyData")
    @ResponseBody
    public Map<String, Object> getMonthlyData(@RequestAttribute("BeanName") PsDBBean psDBBean,
                                              @RequestParam("txtAction") String txtAction,
                                              @RequestParam("txtDYYYYMM") String txtDYYYYMM,
                                              @RequestParam("txtDYYYYMMDD") String txtDYYYYMMDD) throws Exception {

        //初期化対象
        tmgResultsBean.setMonth(txtDYYYYMM);
        psDBBean.setTargetUser(psDBBean.getUserCode());
        tmgResultsBean.setDay(txtDYYYYMMDD);
        tmgResultsBean.execReflectionTimePunch(txtDYYYYMM, psDBBean);

       return  tmgResultsBean.monthlyMapInit(psDBBean);
    }

    /**
     * 日次実績を返却します
     *
     * @param psDBBean
     * @return
     */
    @GetMapping("getDailyData")
    @ResponseBody
    public Map<String, Object> getDailyData(@RequestAttribute("BeanName") PsDBBean psDBBean,
                                              @RequestParam("txtAction") String txtAction,
                                              @RequestParam("txtDYYYYMM") String txtDYYYYMM,
                                              @RequestParam("txtDYYYYMMDD") String txtDYYYYMMDD) throws Exception {

        //初期化対象
        tmgResultsBean.setMonth(txtDYYYYMM);
        psDBBean.setTargetUser(psDBBean.getUserCode());
        tmgResultsBean.setDay(txtDYYYYMMDD);

        return  tmgResultsBean.getDailyData(psDBBean);
    }


    /**
     * 出張区分を返却します
     *
     * @param psDBBean
     * @return
     */
    @GetMapping("getWorkingId")
    @ResponseBody
    public List<GenericDetailVO> getWorkingId(@RequestAttribute("BeanName") PsDBBean psDBBean,
                                              @RequestParam("txtDYYYYMMDD") String txtDYYYYMMDD) throws Exception {

        psDBBean.setTargetUser(psDBBean.getUserCode());
        tmgResultsBean.setDay(txtDYYYYMMDD);
        //初期化対象
        return tmgResultsBean.getWorkingId(psDBBean);
    }

}
