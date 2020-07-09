package jp.smartcompany.controller;


import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.ParamNotificationListDto;
import jp.smartcompany.job.modules.tmg.tmgresults.TmgResultsBean;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.TmgResultsDto;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DispMonthlyVO;
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
    @GetMapping("getTitleData")
    @ResponseBody
    public Map<String, Object> getTitleData(@RequestAttribute("BeanName") PsDBBean psDBBean,
                                              @RequestParam("txtAction") String txtAction,
                                              @RequestParam("txtDYYYYMM") String txtDYYYYMM,
                                              @RequestParam("txtDYYYYMMDD") String txtDYYYYMMDD) throws Exception {

        //初期化対象
        tmgResultsBean.setMonth(txtDYYYYMM);
        psDBBean.setTargetUser(psDBBean.getUserCode());
        tmgResultsBean.setDay(txtDYYYYMMDD);
        tmgResultsBean.execReflectionTimePunch(txtDYYYYMM, psDBBean);

       return  tmgResultsBean.getTitleData(psDBBean);
    }



    /**
     * 画面表示用データを返却します
     *
     * @param psDBBean
     * @return
     */
    @GetMapping("dailyDetail")
    @ResponseBody
    public Map dailyDetail(@RequestAttribute("BeanName") PsDBBean psDBBean,
                           @RequestParam("txtDYYYYMMDD") String txtDYYYYMMDD,
                           @RequestParam("txtAction") String action) throws Exception {

        psDBBean.setTargetUser(psDBBean.getUserCode());
        tmgResultsBean.setDay(txtDYYYYMMDD);
        //初期化対象
        return tmgResultsBean.dailyDetail(psDBBean, action);
    }

    /**
     * 画面ドロップダウンを返却します
     *
     * @param psDBBean
     * @return
     */
    @GetMapping("updateInp")
    @ResponseBody
    public void updateInp(@RequestAttribute("BeanName") PsDBBean psDBBean,
                          @RequestParam("txtDYYYYMMDD") String txtDYYYYMMDD,
                          @RequestParam("txtAction") String action,
                          @RequestParam("holiday") String holiday,
                          @RequestParam("workingId") String workingId,
                          @RequestParam("selMgdCbusinessTrip") String selMgdCbusinessTrip,
                          @RequestParam("txtTdaNopenR") String txtTdaNopenR,
                          @RequestParam("txtTdaNcloseR") String txtTdaNcloseR,
                          @RequestParam("tdaCowncommentR") String tdaCowncommentR) throws Exception {

        psDBBean.setTargetUser(psDBBean.getUserCode());
        tmgResultsBean.setDay(txtDYYYYMMDD);

        TmgResultsDto tmgResultsDto = new TmgResultsDto();
        tmgResultsDto.setHoliday(holiday);
        tmgResultsDto.setWorkingId(workingId);
        tmgResultsDto.setSelMgdCbusinessTrip(selMgdCbusinessTrip);
        tmgResultsDto.setTxtTdaNopenR(txtTdaNopenR);
        tmgResultsDto.setTxtTdaNcloseR(txtTdaNcloseR);
        tmgResultsDto.setTdaCowncommentR(tdaCowncommentR);

        //初期化対象
        tmgResultsBean.updateInp(tmgResultsDto,psDBBean, action);
    }


    /**
     * 画面ドロップダウンを返却します
     *
     * @param psDBBean
     * @return
     */
    @PostMapping("updateDaily")
    @ResponseBody
    public void updateDaily(@RequestAttribute("BeanName") PsDBBean psDBBean,
                            @RequestBody TmgResultsDto tmgResultsDto) throws Exception {

        psDBBean.setTargetUser(psDBBean.getUserCode());

        tmgResultsBean.setDay(tmgResultsDto.getTxtDYYYYMMDD());

        //初期化対象
        tmgResultsBean.updateDaily(tmgResultsDto, psDBBean);
    }


}
