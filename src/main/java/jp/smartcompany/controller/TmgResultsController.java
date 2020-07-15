package jp.smartcompany.controller;


import cn.hutool.core.map.MapUtil;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.tmgresults.TmgResultsBean;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.ErrMsgDto;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.TmgResultsDto;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DispMonthlyVO;
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
     * 就業登録一覧画面の勤務年月取得
     *
     * @param psDBBean
     * @return
     */
    @GetMapping("workDateList")
    @ResponseBody
    public Map getWorkDateList(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {

        //初期化対象
        List<DispMonthlyVO> monthLy =tmgResultsBean.tmgInpInit(psDBBean);

        Map<String, Object> todayMonthLy = MapUtil.newHashMap();
        todayMonthLy.put("today",tmgResultsBean.getToday());
        todayMonthLy.put("monthLy",monthLy);
        return todayMonthLy;
    }


    /**
     * 就業登録一覧画面のデータ取得
     *
     * @param psDBBean     PsDBBean
     * @param txtDYYYYMM   対象月
     * @param txtDYYYYMMDD 　対象日
     * @return
     * @throws Exception
     */
    @GetMapping("getTitleData")
    @ResponseBody
    public Map<String, Object> getTitleData(@RequestAttribute("BeanName") PsDBBean psDBBean,
                                            @RequestParam("txtDYYYYMM") String txtDYYYYMM,
                                            @RequestParam("txtDYYYYMMDD") String txtDYYYYMMDD) {

        //初期化対象
        tmgResultsBean.setMonth(txtDYYYYMM);
        psDBBean.setTargetUser(psDBBean.getUserCode());
        tmgResultsBean.setDay(txtDYYYYMMDD);
        tmgResultsBean.execReflectionTimePunch(txtDYYYYMM, psDBBean);

        return tmgResultsBean.getTitleData(psDBBean);
    }

    /**
     * 就業登録画面初期化
     * @param psDBBean PsDBBean
     * @param txtDYYYYMMDD 対象日
     * @param action　Action
     * @return 初期化データ
     */
    @GetMapping("dailyDetail")
    @ResponseBody
    public Map dailyDetail(@RequestAttribute("BeanName") PsDBBean psDBBean,
                           @RequestParam("txtDYYYYMMDD") String txtDYYYYMMDD,
                           @RequestParam("txtAction") String action) {

        psDBBean.setTargetUser(psDBBean.getUserCode());
        tmgResultsBean.setDay(txtDYYYYMMDD);
        //初期化データ取得する
        return tmgResultsBean.dailyDetail(psDBBean, action);
    }

    /**
     * コメントのみ登録ボタンを押下する
     *
     * @param psDBBean
     * @return
     */
    @PostMapping("updateInp")
    @ResponseBody
    public GlobalResponse updateInp(@RequestAttribute("BeanName") PsDBBean psDBBean,
                                    @RequestBody TmgResultsDto tmgResultsDto) {

        psDBBean.setTargetUser(psDBBean.getUserCode());
        tmgResultsBean.setDay(tmgResultsDto.getTxtDYYYYMMDD());

        //メントのみ登録
        return tmgResultsBean.updateInp(psDBBean, tmgResultsDto);
    }


    /**
     * 登録ボタンを押下する
     *
     * @param psDBBean
     * @return
     */
    @PostMapping("getSQLVecForAjax")
    @ResponseBody
    public ErrMsgDto getSQLVecForAjax(@RequestAttribute("BeanName") PsDBBean psDBBean,
                                      @RequestBody TmgResultsDto tmgResultsDto) throws Exception {

        psDBBean.setTargetUser(psDBBean.getUserCode());
        tmgResultsBean.setDay(tmgResultsDto.getTxtDYYYYMMDD());

        //就業実績登録
        return tmgResultsBean.getSQLVecForAjax(tmgResultsDto,psDBBean);
    }



    /**
     * 登録ボタンを押下する
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

        //就業実績登録
        tmgResultsBean.updateDaily(psDBBean,tmgResultsDto);
    }


}
