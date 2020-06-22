package jp.smartcompany.controller;

import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.CalenderVo;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.OneMonthDetailVo;
import jp.smartcompany.job.modules.tmg.permStatList.PermStatListBean;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DispMonthlyVO;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * @author Nie Wanqun
 * @description controller
 * @objectSource
 * @date 2020/05/18
 **/
@RestController
@RequestMapping("sys/permStatList")
public class PermStatListController {

    @Autowired
    private PermStatListBean permStatListBean;


    /**
     * 表示月遷移リスト情報を取得する
     *
     * @param psDBBean PsDBBean
     *                 txtAction アクション (ACT_DISP_MONTHLY)
     *                 txtDYYYYMM　対象月
     *                 txtDYYYYMMDD　対象日　(NULL可)
     *                 txtCEMPLOYEEID　職員ID　(NULL可)
     *                 txtExecuteEmpId　チェックした対象者　(NULL可)
     * @return List<DispMonthlyVO>
     * @throws Exception
     */
    @GetMapping("dispTmgMonthlyList")
    @ResponseBody
    public List<DispMonthlyVO> dispMonthlyList(
            @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        TmgReferList referList = permStatListBean.execute(psDBBean);
        List<DispMonthlyVO> dispMonthlyList = permStatListBean.dispMonthlyList(psDBBean,referList);
        return dispMonthlyList;
    }


    /**
     * 前月リンクを作成する為の勤怠データ件数を取得する
     *
     * @param psDBBean PsDBBean
     *                 txtAction アクション(ACT_DISP_MONTHLY)
     *                 txtDYYYYMM　対象月
     *                 txtDYYYYMMDD　対象日
     *                 txtCEMPLOYEEID　職員ID
     *                 txtExecuteEmpId　チェックした対象者
     * @return int
     * @throws Exception
     */
    @GetMapping("dispMonthlyPrev")
    @ResponseBody
    public int dispMonthlyPrev(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        TmgReferList referList = permStatListBean.execute(psDBBean);
        int dispMonthlyPrev = permStatListBean.dispMonthlyPrev(referList);
        return dispMonthlyPrev;
    }


    /**
     * 翌月リンクを作成する為の勤怠データ件数を取得する
     *
     * @param psDBBean PsDBBean
     *                 txtAction アクション(ACT_DISP_MONTHLY)
     *                 txtDYYYYMM　対象月
     *                 txtDYYYYMMDD　対象日
     *                 txtCEMPLOYEEID　職員ID
     *                 txtExecuteEmpId　チェックした対象者
     * @return int
     * @throws Exception
     */
    @GetMapping("dispMonthlyNext")
    @ResponseBody
    public int dispMonthlyNext(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        TmgReferList referList =permStatListBean.execute(psDBBean);
        int dispMonthlyNext = permStatListBean.dispMonthlyNext(referList);
        return dispMonthlyNext;
    }

    /**
     *  職員氏名と、承認ステータス状態を取得する
     *
     * @param psDBBean PsDBBean
     *                 txtAction アクション(ACT_DISP_MONTHLY)
     *                 txtDYYYYMM　対象月
     *                 txtDYYYYMMDD　対象日
     *                 txtCEMPLOYEEID　職員ID
     *                 txtExecuteEmpId　チェックした対象者
     * @return List<TmgMonthlyInfoVO>
     * [TmgMonthlyInfoVO(empid=40010001, empname=40010001 氏名, tmoCstatusflg=TMG_DATASTATUS|0, StatusName= , dailyCount=22, lastBaseDate=2020/03/31, disppermStatus1=TMG_DATASTATUS|0, disppermStatusName1= , disppermStatus2=TMG_DATASTATUS|0, disppermStatusName2= , disppermStatus3=TMG_DATASTATUS|0, disppermStatusName3= , disppermStatus4=TMG_DATASTATUS|0, disppermStatusName4= , disppermStatus5=TMG_DATASTATUS|0, disppermStatusName5= , disppermStatus6=TMG_DATASTATUS|0, disppermStatusName6= , disppermStatus7=TMG_DATASTATUS|0, disppermStatusName7= , disppermStatus8=TMG_DATASTATUS|0, disppermStatusName8= , disppermStatus9=TMG_DATASTATUS|0, disppermStatusName9= , disppermStatus10=TMG_DATASTATUS|0, disppermStatusName10= , disppermStatus11=TMG_DATASTATUS|0, disppermStatusName11= , disppermStatus12=TMG_DATASTATUS|0, disppermStatusName12= , disppermStatus13=TMG_DATASTATUS|0, disppermStatusName13= , disppermStatus14=TMG_DATASTATUS|0, disppermStatusName14= , disppermStatus15=TMG_DATASTATUS|0, disppermStatusName15= , disppermStatus16=TMG_DATASTATUS|0, disppermStatusName16= , disppermStatus17=TMG_DATASTATUS|0, disppermStatusName17= , disppermStatus18=TMG_DATASTATUS|0, disppermStatusName18= , disppermStatus19=TMG_DATASTATUS|0, disppermStatusName19= , disppermStatus20=TMG_DATASTATUS|0, disppermStatusName20= , disppermStatus21=TMG_DATASTATUS|0, disppermStatusName21= , disppermStatus22=TMG_DATASTATUS|0, disppermStatusName22= , disppermStatus23=TMG_DATASTATUS|5, disppermStatusName23=済, disppermStatus24=TMG_DATASTATUS|5, disppermStatusName24=済, disppermStatus25=TMG_DATASTATUS|5, disppermStatusName25=済, disppermStatus26=TMG_DATASTATUS|5, disppermStatusName26=済, disppermStatus27=TMG_DATASTATUS|5, disppermStatusName27=済, disppermStatus28=TMG_DATASTATUS|5, disppermStatusName28=済, disppermStatus29=TMG_DATASTATUS|5, disppermStatusName29=済, disppermStatus30=TMG_DATASTATUS|5, disppermStatusName30=済, disppermStatus31=TMG_DATASTATUS|5, disppermStatusName31=済),
     * TmgMonthlyInfoVO(empid=40070002, empname=40070002 氏名, tmoCstatusflg=TMG_DATASTATUS|0, StatusName= , dailyCount=31, lastBaseDate=2020/03/31, disppermStatus1=TMG_DATASTATUS|0, disppermStatusName1= , disppermStatus2=TMG_DATASTATUS|0, disppermStatusName2= , disppermStatus3=TMG_DATASTATUS|0, disppermStatusName3= , disppermStatus4=TMG_DATASTATUS|0, disppermStatusName4= , disppermStatus5=TMG_DATASTATUS|0, disppermStatusName5= , disppermStatus6=TMG_DATASTATUS|0, disppermStatusName6= , disppermStatus7=TMG_DATASTATUS|0, disppermStatusName7= , disppermStatus8=TMG_DATASTATUS|0, disppermStatusName8= , disppermStatus9=TMG_DATASTATUS|0, disppermStatusName9= , disppermStatus10=TMG_DATASTATUS|0, disppermStatusName10= , disppermStatus11=TMG_DATASTATUS|0, disppermStatusName11= , disppermStatus12=TMG_DATASTATUS|0, disppermStatusName12= , disppermStatus13=TMG_DATASTATUS|0, disppermStatusName13= , disppermStatus14=TMG_DATASTATUS|0, disppermStatusName14= , disppermStatus15=TMG_DATASTATUS|0, disppermStatusName15= , disppermStatus16=TMG_DATASTATUS|0, disppermStatusName16= , disppermStatus17=TMG_DATASTATUS|0, disppermStatusName17= , disppermStatus18=TMG_DATASTATUS|0, disppermStatusName18= , disppermStatus19=TMG_DATASTATUS|0, disppermStatusName19= , disppermStatus20=TMG_DATASTATUS|0, disppermStatusName20= , disppermStatus21=TMG_DATASTATUS|0, disppermStatusName21= , disppermStatus22=TMG_DATASTATUS|0, disppermStatusName22= , disppermStatus23=TMG_DATASTATUS|0, disppermStatusName23= , disppermStatus24=TMG_DATASTATUS|0, disppermStatusName24= , disppermStatus25=TMG_DATASTATUS|0, disppermStatusName25= , disppermStatus26=TMG_DATASTATUS|0, disppermStatusName26= , disppermStatus27=TMG_DATASTATUS|0, disppermStatusName27= , disppermStatus28=TMG_DATASTATUS|0, disppermStatusName28= , disppermStatus29=TMG_DATASTATUS|0, disppermStatusName29= , disppermStatus30=TMG_DATASTATUS|0, disppermStatusName30= , disppermStatus31=TMG_DATASTATUS|0, disppermStatusName31= )]
     * @throws Exception
     */
    @GetMapping("getTmgMonthlyInfoVOList")
    @ResponseBody
    public Map getTmgMonthlyInfoVOList(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        TmgReferList referList =permStatListBean.execute(psDBBean);
        Map map = permStatListBean.getTmgMonthlyInfoVOList(psDBBean, referList);
        return map;
    }


    /**
     * カレンダーテーブルより休日フラグを取得する
     *
     * @param psDBBean PsDBBean
     *                 txtAction アクション(ACT_DISP_MONTHLY)
     *                 txtDYYYYMM　対象月
     *                 txtDYYYYMMDD　対象日
     *                 txtCEMPLOYEEID　職員ID
     *                 txtExecuteEmpId　チェックした対象者
     * @return CalenderVo
     * @throws Exception
     */
    @GetMapping("selectGetCalendarList")
    @ResponseBody
    public CalenderVo selectGetCalendarList(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        TmgReferList referList =permStatListBean.execute(psDBBean);
        CalenderVo calenderVo = permStatListBean.selectGetCalendarList(psDBBean, referList);
        return calenderVo;
    }


    /**
     * 一覧のタイトル取得して、編集する
     *
     * @param psDBBean PsDBBean
     *                 txtAction アクション(ACT_DISP_MONTHLY)
     *                 txtDYYYYMM　対象月
     *                 txtDYYYYMMDD　対象日
     *                 txtCEMPLOYEEID　職員ID
     *                 txtExecuteEmpId　チェックした対象者
     * @return List<OneMonthDetailVo>
     * @throws Exception
     */
    @GetMapping("selectDayCount")
    @ResponseBody
    public ArrayList<HashMap> selectDayCount(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {

        ArrayList<HashMap> ids = new ArrayList<>();

        TmgReferList referList =permStatListBean.execute(psDBBean);
        List<OneMonthDetailVo> oneMonthDetailVoList = permStatListBean.selectDayCount(psDBBean, referList);

        for (OneMonthDetailVo item : oneMonthDetailVoList){
            LinkedHashMap listHashMap = new LinkedHashMap();
            listHashMap.put("name",item.getSeq()+"\n"+item.getDayOfWeek());
            listHashMap.put("tcaCholflg",item.getTcaCholflg());
            listHashMap.put("day",item.getDay());
            ids.add(listHashMap);
        }
        return ids;
    }

    /******************************************************************************************************************/

    /**
     * 所属情報を取得する
     *
     * @param psDBBean PsDBBean
     *                 txtAction アクション(ACT_EDIT_DAIRY)
     *                 txtDYYYYMM　対象月
     *                 txtDYYYYMMDD　対象日(必須)
     *                 txtCEMPLOYEEID　職員ID　(NULL可)
     *                 txtExecuteEmpId　チェックした対象者　(NULL可)
     * @return String
     * @throws Exception
     */
    @GetMapping("getSectionName")
    @ResponseBody
    public String getSectionName(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        TmgReferList referList =permStatListBean.execute(psDBBean);
        String sectionName = permStatListBean.getSectionName(psDBBean, referList);
        return sectionName;
    }

    /**
     * 日別一覧表示の為のプロセスを実行します。
     *
     * @param psDBBean PsDBBean
     *                 txtAction アクション(ACT_EDIT_DAIRY)
     *                 txtDYYYYMM　対象月
     *                 txtDYYYYMMDD　対象日(必須)
     *                 txtCEMPLOYEEID　職員ID　(NULL可)
     *                 txtExecuteEmpId　チェックした対象者　(NULL可)
     * @return Map
     * @throws Exception
     */
    @GetMapping("getReadTmgDaily")
    @ResponseBody
    public Map getReadTmgDaily(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        TmgReferList referList = permStatListBean.execute(psDBBean);
        Map resultMap = permStatListBean.getReadTmgDaily(psDBBean, referList);
        return resultMap;
    }

    /**
     * 日次一括承認処理の為のプロセスを実行します。
     *
     * @param psDBBean PsDBBean
     *                 txtAction アクション(ACT_EDIT_DAIRY)
     *                 txtDYYYYMM　対象月
     *                 txtDYYYYMMDD　対象日(必須)
     *                 txtCEMPLOYEEID　職員ID　(NULL可)
     *                 txtExecuteEmpId　チェックした対象者　(必須)[1001,1002]
     * @return Map
     * @throws Exception
     */
    @GetMapping("executeUpdateTmgDaily")
    @ResponseBody
    public void executeUpdateTmgDaily(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        TmgReferList referList = permStatListBean.execute(psDBBean);
        permStatListBean.executeUpdateTmgDaily(psDBBean, referList);

    }

    /**
     * 月次就業実績一括承認処理の為のプロセスを実行します。
     *
     * @param psDBBean PsDBBean
     *                 txtAction アクション(ACT_EDIT_DAIRY)
     *                 txtDYYYYMM　対象月
     *                 txtDYYYYMMDD　対象日(必須)
     *                 txtCEMPLOYEEID　職員ID　(NULL可)
     *                 txtExecuteEmpId　チェックした対象者　(必須)
     * @return Map
     * @throws Exception
     */
    @GetMapping("executeUpdateTmgMonthly")
    @ResponseBody
    public void executeUpdateTmgMonthly(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        TmgReferList referList = permStatListBean.execute(psDBBean);
        permStatListBean.executeUpdateTmgMonthly(psDBBean);

    }





}
