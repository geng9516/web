package jp.smartcompany.controller;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.ParamNotificationListDto;
import jp.smartcompany.job.modules.tmg.tmgresults.TmgResultsBean;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.ErrMsgDto;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.TmgResultsDto;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DispMonthlyVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.HatuReiVo;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
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
    public Map getWorkDateList(@RequestParam(value = "txtDYYYYMM",required = false) String txtDYYYYMM,
                               @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {

        //初期化対象
        List<DispMonthlyVO> monthLy =tmgResultsBean.tmgInpInit(txtDYYYYMM,psDBBean);

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
        if(StrUtil.hasEmpty(txtDYYYYMMDD)){
            tmgResultsBean.setDay(txtDYYYYMM);
        }else{
            tmgResultsBean.setDay(txtDYYYYMMDD);
        }
        tmgResultsBean.execReflectionTimePunch(txtDYYYYMM, psDBBean);

        return tmgResultsBean.getTitleData(psDBBean);
    }

    /**
     * 勤務状況チェック
     *
     * @param psDBBean   PsDBBean
     * @param txtAction  アクション
     * @param txtDYYYYMM 　対象月
     */
    @GetMapping("updateWorkChk")
    @ResponseBody
    public void updateWorkChk(@RequestAttribute("BeanName") PsDBBean psDBBean,
                              @RequestParam("txtAction") String txtAction,
                              @RequestParam("txtDYYYYMM") String txtDYYYYMM) {
        //初期化対象
        tmgResultsBean.setMonth(txtDYYYYMM);
        if(psDBBean.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_INP)){
            psDBBean.setTargetUser(psDBBean.getUserCode());
        }

        tmgResultsBean.updateStatus(psDBBean, "TMG_ITEMS|WorkStatus", txtAction, null);
    }

    /**
     * 健康状況チェック
     *
     * @param psDBBean   PsDBBean
     * @param txtAction  アクション
     * @param txtDYYYYMM 　対象月
     * @param selHealthStatus 健康状態
     */
    @GetMapping("updateHealthChk")
    @ResponseBody
    public void updateHealthChk(@RequestAttribute("BeanName") PsDBBean psDBBean,
                                @RequestParam("txtAction") String txtAction,
                                @RequestParam("txtDYYYYMM") String txtDYYYYMM,
                                @RequestParam("selHealthStatus") String selHealthStatus) {

        //初期化対象
        tmgResultsBean.setMonth(txtDYYYYMM);
        if(psDBBean.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_INP)){
            psDBBean.setTargetUser(psDBBean.getUserCode());
        }

        tmgResultsBean.updateStatus(psDBBean, "TMG_ITEMS|HealthStatus", txtAction, selHealthStatus);
    }



    /**
     * 月次一覧画面で　月次承認・月次解除ボタンを押下する
     *
     * @param psDBBean
     * @return
     */
     @PostMapping("updateMonth")
     @ResponseBody
     public GlobalResponse updateMonth(
                @RequestParam("txtAction") String action,
                @RequestParam("txtDYYYYMM") String month,
                @RequestAttribute("BeanName") PsDBBean psDBBean) {
            tmgResultsBean.setMonth(month);
            //メントのみ登録
            return tmgResultsBean.updateMonth(action,psDBBean);
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
                           @RequestParam("txtDYYYYMM") String txtDYYYYMM,
                           @RequestParam("txtDYYYYMMDD") String txtDYYYYMMDD,
                           @RequestParam("txtAction") String action) {

        if(psDBBean.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_INP)){
            psDBBean.setTargetUser(psDBBean.getUserCode());
        }
        tmgResultsBean.setToday(txtDYYYYMMDD);
        tmgResultsBean.setThisMonth(txtDYYYYMM);
        tmgResultsBean.setMonth(txtDYYYYMM);
        //tmgResultsBean.setReferList(TmgReferList.TREEVIEW_TYPE_EMP,psDBBean);
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
     * 登録・承認ボタンを押下する
     *
     * @param psDBBean
     * @return
     */
    @PostMapping("updateDaily")
    @ResponseBody
    public GlobalResponse updateDaily(@RequestBody TmgResultsDto tmgResultsDto,
                                      @RequestAttribute("BeanName") PsDBBean psDBBean
                                      ) throws Exception {
        if(tmgResultsDto.getPsSite().equals(TmgUtil.Cs_SITE_ID_TMG_INP)) {
            psDBBean.setTargetUser(psDBBean.getUserCode());
        }else{
            psDBBean.setTargetUser(tmgResultsDto.getTargetEmp());
        }
        tmgResultsBean.setDay(tmgResultsDto.getTxtDYYYYMMDD());
        //就業実績登録
        return tmgResultsBean.updateDaily(psDBBean,tmgResultsDto);
    }

    /**
     * 差戻ボタンを押下する
     *
     * @param psDBBean
     * @return
     */
    @PostMapping("updateRemandsStatus")
    @ResponseBody
    public GlobalResponse updateRemandsStatus(@RequestAttribute("BeanName") PsDBBean psDBBean,
                                      @RequestBody TmgResultsDto tmgResultsDto) throws Exception {

        if(tmgResultsDto.getPsSite().equals(TmgUtil.Cs_SITE_ID_TMG_INP)) {
            psDBBean.setTargetUser(psDBBean.getUserCode());
        }else{
            psDBBean.setTargetUser(tmgResultsDto.getTargetEmp());
        }
        tmgResultsBean.setDay(tmgResultsDto.getTxtDYYYYMMDD());
        //就業実績登録
        return tmgResultsBean.updateRemandsStatus(tmgResultsDto,psDBBean);
    }


    /**
     * 発令日表示処理
     *
     * @param psDBBean
     * @return
     */
    @GetMapping("hatuRei")
    @ResponseBody
    public HatuReiVo getHatuReiVoInfo(@RequestAttribute("BeanName") PsDBBean psDBBean) {

        tmgResultsBean.setReferList(TmgReferList.TREEVIEW_TYPE_EMP,psDBBean);
        return tmgResultsBean.getHatuReiVoInfo(tmgResultsBean.getReferList().getRecordDate(),psDBBean);
    }



}
