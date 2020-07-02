package jp.smartcompany.job.modules.tmg.overtimeInstruct;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgDailyDetailCheckDO;
import jp.smartcompany.job.modules.core.pojo.entity.TmgTriggerDO;
import jp.smartcompany.job.modules.core.service.*;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.dto.*;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.*;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.CompanyVO;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import jp.smartcompany.boot.util.SysUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 超過勤務命令bean -> 对应旧就业的ps.c01.tmg.OvertimeInstruct.OvertimeInstructBean
 *
 * @author Wang Ziyue
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OvertimeInstructBean {

    /**
     * 汎用参照オブジェクト
     */
    public TmgReferList referList = null;
    private final ITmgCalendarService iTmgCalendarService;
    private final ITmgMonthlyInfoService iTmgMonthlyInfoService;
    public final IMastGenericDetailService iMastGenericDetailService;
    public final ITmgDailyService iTmgDailyService;
    public final ITmgCompanyService iTmgCompanyService;
    public final ITmgGroupAttributeService iTmgGroupAttributeService;
    public final ITmgPatternService iTmgPatternService;
    public final ITmgDailyDetailService iTmgDailyDetailService;
    public final ITmgDailyDetailCheckService ITmgDailyDetailCheckService;
    public final ITmgTriggerService  iTmgTriggerService;
    //public PsDBBean psDBBean;

    ParamOvertimeInstructDto param = new ParamOvertimeInstructDto();
    OverTimeLimitDto overTimeLimitDtos= new OverTimeLimitDto();
    HolidayTimeLimitDto holidayTimeLimitDtos=new HolidayTimeLimitDto();
    /** 非勤務区分：超過勤務 */
    private static final String TMG_ITEM_OVERHOURS      = "TMG_ITEMS|Overhours";
    private static final String BEANDESC  = "OvertimeInstruct";


    /** アクションを表すコードです。 */
    public static final String ACT_DISP_RMONTHLY        = "ACT_DISP_RMONTHLY";
    public static final String ACT_EDIT_RDAILY          = "ACT_EDIT_RDAILY";
    public static final String ACT_REDIRECT             = "ACT_REDIRECT";
    public static final String ACT_EDIT_UOVERTIME       = "ACT_EDIT_UOVERTIME";
    public static final String ACT_DISP_GENERIC         = "ACT_DISP_GENERIC";
    public static final String ACT_DISP_RMONTHLY_RESULT = "ACT_DISP_RMONTHLY_RESULT";
    public static final String ACT_DISP_RMONTHLY_RESULT_OT100 = "ACT_DISP_RMONTHLY_RESULT_OT100";



    /** 超過勤務(日)警告Level1(背景) */
    public static final String STYLE_CLASS_OVER_WORK_WARNING_LVL1 = "chokinDailyLvl1";
    /** 警告Level1(背景：黄色) */
    public static final String STYLE_CLASS_WARNING_LVL1 = "chokinWarningLvl1";
    /** 警告Level2(背景：オレンジ色) */
    public static final String STYLE_CLASS_WARNING_LVL2 = "chokinWarningLvl2";
    /** 警告Level3(背景：ピンク色) */
    public static final String STYLE_CLASS_WARNING_LVL3 = "chokinWarningLvl3";
    /** 警告Level4(背景：赤色) */
    public static final String STYLE_CLASS_WARNING_LVL4 = "chokinWarningLvl4";
    /** 警告Level5(背景：紫色) */
    public static final String STYLE_CLASS_WARNING_LVL5 = "chokinWarningLvl5";
    /**超勤実績の月平均時間 */
    public static final String STYLE_OVERWORK_AVG_WAR_LVL1 = "chokinAvgWarningLvl1";
    /** ステータス関連クラス - 超過勤務命令（命令画面用） */
    public static final String STYLE_CLASS_CHOKIN_MEIREI = "chokin_meirei";

    /** 勤務時間(日) */
    public static final String CATEGORY_OVER_WORK = "5";
    /** 合計(月) */
    public static final String CATEGORY_SUM_MONTH = "1";
    /** 合計(年) */
    public static final String CATEGORY_SUM_YEAR  = "2";
    /** 月超過回数 */
    public static final String CATEGORY_COUNT     = "3";
    /** 休日出勤回数 */
    public static final String CATEGORY_HOL_CNT   = "4";
    /**超勤実績の月平均時間*/
    public static final String CATEGORY_OVERWORK_AVG_MONTH   = "6";

    //参数设置
//    private void paramSetting(){
//        param=null;
//
//        //基本信息
//        param.setUserCode(psDBBean.getUserCode());
//        param.setAction(psDBBean.getReqParam("txtACTION"));
//        param.setCustId(psDBBean.getCustID());
//        param.setCompId(psDBBean.getCompCode());
//        param.setSiteId(psDBBean.getSiteId());
//        param.setLang(psDBBean.getLanguage());
//        param.setTargetGroup(referList.getTargetGroup());
//
//        overTimeLimitDtos=iTmgGroupAttributeService.selectOverTimeLimit(psDBBean.getCustID(),psDBBean.getCompCode(),referList.getTargetSec(),param.getTargetGroup());
//
//        holidayTimeLimitDtos=iTmgGroupAttributeService.selectHolidayTimeLimit(psDBBean.getCustID(),psDBBean.getCompCode(),referList.getTargetSec(),param.getTargetGroup());
//
//
//
//
//    }


    // 凡例画面表示
    public void actionDemoDisp(ModelMap modelMap){

        DemoLimitVo demo=new DemoLimitVo();

        // 閾値(超過勤務:日)
        demo.setLIMIT_OVER_WORK_LVL1(overTimeLimitDtos.getOtDaily01());
        // 閾値(合計:月)
        demo.setLIMIT_SUM_MONTH_LVL1(overTimeLimitDtos.getOtMontly01());
        demo.setLIMIT_SUM_MONTH_LVL2(overTimeLimitDtos.getOtMontly02());
        demo.setLIMIT_SUM_MONTH_LVL3(overTimeLimitDtos.getOtMontly03());
        demo.setLIMIT_SUM_MONTH_LVL4(overTimeLimitDtos.getOtMontly04());
        demo.setLIMIT_SUM_MONTH_LVL5(overTimeLimitDtos.getOtMontly05());
        // 閾値(合計:年)
        demo.setLIMIT_SUM_YEAR_LVL1(overTimeLimitDtos.getOtYearly01());
        demo.setLIMIT_SUM_YEAR_LVL2(overTimeLimitDtos.getOtYearly02());
        demo.setLIMIT_SUM_YEAR_LVL3(overTimeLimitDtos.getOtYearly03());
        demo.setLIMIT_SUM_YEAR_LVL4(overTimeLimitDtos.getOtYearly04());
        demo.setLIMIT_SUM_YEAR_LVL5(overTimeLimitDtos.getOtYearly05());
        // 閾値(月超勤回数)
        demo.setLIMIT_COUNT(overTimeLimitDtos.getOtMonthlycount());
        //閾値(月平均時間)
        demo.setLIMIT_OVERWORK_MONTH_AVG(overTimeLimitDtos.getOtMonthlyavg());
        //超勤実績平均(月)
        demo.setLABEL_AVERAGE_TIME(overTimeLimitDtos.getOtMonthlyavg());

        // 閾値(休日出勤回数)
        demo.setLIMIT_HOL_CNT_LVL1(holidayTimeLimitDtos.getHtMontly01());
        demo.setLIMIT_HOL_CNT_LVL2(holidayTimeLimitDtos.getHtMontly02());
        demo.setLIMIT_HOL_CNT_LVL3(holidayTimeLimitDtos.getHtMontly03());
        demo.setLIMIT_HOL_CNT_LVL4(holidayTimeLimitDtos.getHtMontly04());
        demo.setLIMIT_HOL_CNT_LVL5(holidayTimeLimitDtos.getHtMontly05());


    }



    /**
     * 超過勤務実績一覧画面表示用に必要データを取得するメソッド
     *
     * 実績時間画面表示アクション、または実績時間（法定内を含む）画面表示アクションのどちらかのアクションコードを想定
     */
    public List<ResultMonthlyVo> actionExecuteDispResult(String action,String baseMonth,PsDBBean psDBBean) throws Exception {
        //汎用参照コンポーネント。
        referList = new TmgReferList(psDBBean, "OvertimeInstruct", baseMonth, TmgReferList.TREEVIEW_TYPE_LIST,
                true, true, false, false, true
        );
        /**
         * コンテンツＩＤはアクションコードによって、切替する。
         *   実績時間画面表示の場合は、「TMG_CONTENTID|OTR」を使用。
         *   実績時間（法定内含む）画面表示の場合は、「TMG_CONTENTID|OTRA」を使用。
         */
        String sDBContentId         = ""; // コンテンツID

        if (ACT_DISP_RMONTHLY_RESULT.equals(action)) {
            sDBContentId = TmgUtil.Cs_MGD_CONTENTID_OTR;
        } else if(ACT_DISP_RMONTHLY_RESULT_OT100.equals(action)) {
            sDBContentId = TmgUtil.Cs_MGD_CONTENTID_OTRA;
        } else {
            sDBContentId = TmgUtil.Cs_MGD_CONTENTID_OTR;
        }
        overTimeLimitDtos=iTmgGroupAttributeService.selectOverTimeLimit(psDBBean.getCustID(),psDBBean.getCompCode(),referList.getTargetSec(),referList.getTargetGroup());
        holidayTimeLimitDtos=iTmgGroupAttributeService.selectHolidayTimeLimit(psDBBean.getCustID(),psDBBean.getCompCode(),referList.getTargetSec(),param.getTargetGroup());
        // 2 表示対象社員の今月分の一日毎の超過勤務実績時間を取得
        List<MonthlyInfoOtVo> monthlyInfoOtVoList = iTmgMonthlyInfoService.selectMonthlyInfoOtr(psDBBean.getCustID(), psDBBean.getCompCode(), referList.getTargetSec(),
                sDBContentId, baseMonth, psDBBean.getLanguage(), referList.buildSQLForSelectEmployees());
        // 3 前月リンクを取得
        //String beforeBaseDate = iTmgMonthlyInfoService.selectAftBefBaseDate(psDBBean.getCustID(), psDBBean.getCompCode(), param.getBaseDate(), param.getEmployeeListSql(), 1);
        // 4 翌月リンクを取得
        //String AfterBaseDate = iTmgMonthlyInfoService.selectAftBefBaseDate(psDBBean.getCustID(), psDBBean.getCompCode(), param.getBaseDate(), param.getEmployeeListSql(), 0);
        // 5 表示対象社員の今年度分の合計超過実績時間と、月超過回数を取得
        List<YearlyInfoVo> yearlyInfoVoList = iTmgMonthlyInfoService.selectYearlyInfo(psDBBean.getCustID(), psDBBean.getCompCode(), sDBContentId,
                baseMonth, baseMonth, psDBBean.getLanguage(), referList.buildSQLForSelectEmployees());
        // 6 36協定における月の超勤限度時間表示用名称取得

        //String limit = iMastGenericDetailService.selectLimit(psDBBean.getCustID(), psDBBean.getCompCode(), baseMonth, psDBBean.getLanguage(), TmgUtil.Cs_MGD_LIMIT_MONTHLY_OVERTIME_36);

        //处理后MonthlyInfoOtVo　list
        List<ResultMonthlyVo> dealwitchMonthlyList=new ArrayList<ResultMonthlyVo>();

        for(MonthlyInfoOtVo monthlyInfoOtVo:monthlyInfoOtVoList){
            for(YearlyInfoVo yearlyInfoVo:yearlyInfoVoList){
                if(yearlyInfoVo.getTmiCemployeeid().equals(monthlyInfoOtVo.getEmpid())){
                    ResultMonthlyVo rVo= new ResultMonthlyVo();
                    rVo.setEmpid(monthlyInfoOtVo.getEmpid());
                    rVo.setEmpname(monthlyInfoOtVo.getEmpname());
                    //合計(月)
                    rVo.setOvertime(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getOvertime(),CATEGORY_SUM_MONTH)));
                    //合計(年)
                    rVo.setOverTimeYear(formatTime(getTypeOfStyleByLimit(yearlyInfoVo.getTmiCinfo01(),CATEGORY_SUM_YEAR)));
                    //45超(年)
                    rVo.setOverTimeOver45(getTypeOfStyleByLimit(yearlyInfoVo.getTmiCinfo02(),CATEGORY_COUNT));
                    //休日出勤回数
                    rVo.setWorkInHolidayCount(getTypeOfStyleByLimit(yearlyInfoVo.getTmiCinfo03(),CATEGORY_HOL_CNT));
                    //平均超勤時間（月）
                    rVo.setAvgOverTime(formatTime(getTypeOfStyleByLimit(yearlyInfoVo.getTmiCinfo04(),CATEGORY_OVERWORK_AVG_MONTH)));
                    rVo.setTmiCinfo1(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo1(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo2(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo2(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo3(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo3(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo4(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo4(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo5(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo5(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo6(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo6(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo7(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo7(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo8(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo8(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo9(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo9(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo10(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo10(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo11(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo11(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo12(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo12(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo13(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo13(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo14(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo14(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo15(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo15(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo16(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo16(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo17(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo17(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo18(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo18(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo19(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo19(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo20(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo20(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo21(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo21(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo22(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo22(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo23(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo23(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo24(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo24(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo25(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo25(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo26(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo26(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo27(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo27(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo28(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo28(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo29(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo29(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo30(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo30(),CATEGORY_OVER_WORK)));
                    rVo.setTmiCinfo31(formatTime(getTypeOfStyleByLimit(monthlyInfoOtVo.getTmiCinfo31(),CATEGORY_OVER_WORK)));
                    dealwitchMonthlyList.add(rVo);
                }
            }
        }
        return dealwitchMonthlyList;
    }


    public List<OneMonthDetailVo>  getTableTop(String baseMonth,PsDBBean psDBBean) throws Exception {
        if(StrUtil.hasEmpty(baseMonth)){
            return null;
        }
        //汎用参照コンポーネント。
        referList = new TmgReferList(psDBBean, "OvertimeInstruct", baseMonth, TmgReferList.TREEVIEW_TYPE_LIST,
                true, true, false, false, true
        );

        // 0 対象勤務年月の1ヶ月間の日付・曜日を取得
        List<OneMonthDetailVo> oneMonthDetailVoList = iTmgCalendarService.selectDayCount(baseMonth);
        // 1 カレンダーテーブルより休日フラグを取得
        CalenderVo calenderVoList = iTmgCalendarService.selectGetCalendarList(psDBBean.getCustID(),
                psDBBean.getCompCode(), referList.getTargetSec(), referList.getTargetGroup(), baseMonth.substring(0,4), baseMonth);

        Map map = BeanUtil.beanToMap(calenderVoList);
        //List<String> tcaCholflgList = new ArrayList<>();
        DecimalFormat nDayFormat = new DecimalFormat("00");

        int i = 1;
        for(OneMonthDetailVo vo:oneMonthDetailVoList){
            vo.setTcaCholflg(String.valueOf(map.get("tcaCholflg" + nDayFormat.format(i))));
            vo.setTableTop(vo.getSeq()+"\n"+vo.getDayOfWeek());
            vo.setToday(psDBBean.getSysDate().equals(vo.getDay()));
            i++;
        }
        return oneMonthDetailVoList;
    }






    // 月別情報一覧画面.超過勤務命令月別一覧画面
    public List<MonthlyInfoOtVo> actionExecuteDisp(String baseMonth,PsDBBean psDBBean) throws Exception {
        //汎用参照コンポーネント。
        referList = new TmgReferList(psDBBean, "OvertimeInstruct", baseMonth, TmgReferList.TREEVIEW_TYPE_LIST,
                true, true, false, false, true
        );

        List<MonthlyInfoOtVo> monthlyInfoOtVoList = iTmgMonthlyInfoService.selectMonthlyInfoOtr(psDBBean.getCustID(), psDBBean.getCompCode(), referList.getTargetSec(),
                "TMG_CONTENTID|OTI", baseMonth, psDBBean.getLanguage(), referList.buildSQLForSelectEmployees());

        List<MonthlyInfoOtVo> dealwitchMonthlyList=new ArrayList<MonthlyInfoOtVo>();
        for(MonthlyInfoOtVo monthlyInfoOtVo:monthlyInfoOtVoList){
            //合計(月)
            monthlyInfoOtVo.setOvertime(formatTime(monthlyInfoOtVo.getOvertime()));
            //承認状況ステータス
            Map<String,Object> monthlyMap=BeanUtil.beanToMap(monthlyInfoOtVo);

            for(String key:monthlyMap.keySet()){//keySet获取map集合key的集合  然后在遍历key即可
                if(!key.equals("empid")&&!key.equals("empname")&&!key.equals("overtime")){
                    monthlyMap.put(key,formatTime(getTypeOfStyleByState((String)monthlyMap.get(key))));
                }
            }
            dealwitchMonthlyList.add(BeanUtil.toBean(monthlyMap,MonthlyInfoOtVo.class));
        }
        return dealwitchMonthlyList;
    }

    //超過勤務実績月別平均画面
    public AvgMonthlyVo actionexecuteDisp6MonthsAvg(String empid,String baseDate,PsDBBean psDBBean){
        AvgMonthlyVo vo=new AvgMonthlyVo();
        //paramSetting();
        List<MonthlyInfoOverSumVo> monthlyInfoOverSumVoList=iTmgDailyService.selectMonthlyOverSum(psDBBean.getCustID(),psDBBean.getCompCode(),empid
        ,baseDate,"-5");
        vo.setWorkTypeName(iMastGenericDetailService.selectWorkerTypeName(psDBBean.getCustID(),psDBBean.getCompCode(),empid,baseDate));
        //平均超勤時間
        String monthOverTimesAvg = "";

        // 月超勤時間の時間と分
        String[] monthOverTimes = null;

        // 超勤時間纏めの分
        int monthOverTimesSumInt = 0;

        // 月平均超勤時間の時間
        int monthOverTimesAvgTInt = 0;
        // 月平均超勤時間の分
        int monthOverTimesAvgMInt = 0;

        //遡り1か月の平均時間(2か月)～遡り5か月(6か月)の平均時間を取得する
        for(int i = 0,j = 5 ; i < monthlyInfoOverSumVoList.size(); i++,j--) {

            // 月超勤時間の時間と分の配列を作成する
            monthOverTimes = monthlyInfoOverSumVoList.get(j).getTotalOvertime().split("\\.");

            // 超勤時間纏めの分を計算する
            monthOverTimesSumInt = monthOverTimesSumInt
                    + Integer.valueOf(monthOverTimes[0]) * 60
                    + Integer.valueOf(monthOverTimes[1]);

            // 月平均超勤時間の時間
            monthOverTimesAvgTInt = monthOverTimesSumInt / (i + 1) / 60;
            // 月平均超勤時間の分
            monthOverTimesAvgMInt = monthOverTimesSumInt / (i + 1) % 60;

            // 平均超勤時間を設定する
            if (monthOverTimesAvgMInt >= 10) {
                monthOverTimesAvg = monthOverTimesAvgTInt + "."
                        + monthOverTimesAvgMInt;
            } else {
                monthOverTimesAvg = monthOverTimesAvgTInt + ".0"
                        + monthOverTimesAvgMInt;
            }

            //2か月以上で平均値を表示する
            if(i > 0){
                //平均値表示対象
                vo.getAvgTimes().add(monthOverTimesAvg);
            }
        }
        if(monthlyInfoOverSumVoList.size()==6){
            List<Map<String,String>> vos=new ArrayList<>();
            Map<String,String> weekdaysOvertime = new HashMap<>();
            Map<String,String> sundaysOvertime = new HashMap<>();
            Map<String,String> totalOvertime = new HashMap<>();
            List<String> tableHeader = new ArrayList<>();
            weekdaysOvertime.put("name","時間外労働");
            sundaysOvertime.put("name","休日労働");
            totalOvertime.put("name","合計");
            int i=1;
            for(MonthlyInfoOverSumVo sumVo:monthlyInfoOverSumVoList) {
                weekdaysOvertime.put("m"+i,sumVo.getWeekdaysOvertime());
                sundaysOvertime.put("m"+i,sumVo.getSundaysOvertime());
                totalOvertime.put("m"+i,sumVo.getTotalOvertime());
                tableHeader.add(sumVo.getMonthId());
                i++;
            }
            weekdaysOvertime.put("average", "-");
            sundaysOvertime.put("average","-");
            totalOvertime.put("average","-");
            vos.add(weekdaysOvertime);
            vos.add(sundaysOvertime);
            vos.add(totalOvertime);
            vo.setTableData(vos);
            vo.setTableHeader(tableHeader);
        }else{
            return null;
        }
        return vo;
    }

    // 日別情報編集画面
    public List<DailyOverTimeVo> actionExecuteEdit(String baseDate,PsDBBean psDBBean) throws Exception {
        //汎用参照コンポーネント。
        referList = new TmgReferList(psDBBean, "OvertimeInstruct", baseDate.substring(0,7)+"/01", TmgReferList.TREEVIEW_TYPE_LIST,
                true, true, false, false, true
        );
        // 編集画面表示項目マスタ制御設定取得
        List<DispOverTimeItemsDto> DispOverTimeItemsDtos = iMastGenericDetailService.selectDispOverTimeItems(psDBBean.getCustID(),psDBBean.getCompCode(),baseDate,psDBBean.getLanguage());
        // 0 日別情報より予定出社・退社時間、超過勤務命令開始・終了時間を取得
        List<DailyVo> dailyVoList =iTmgDailyService.selectDaily(psDBBean.getCustID(),psDBBean.getCompCode(),referList.getTargetSec(),baseDate.substring(0,7)+"/01",baseDate,
                psDBBean.getLanguage(),referList.buildSQLForSelectEmployees(), DispOverTimeItemsDtos);

        // 1 予定出社時間・予定退社時間の基準値を取得
        CompanyVO companyVO = iTmgCompanyService.buildSQLSelectCompany(psDBBean.getCustID(), psDBBean.getCompCode(),baseDate);

        //2 日別詳細情報より超過勤務命令開始・終了時間を取得
        List<DailyDetailOverHoursVo> dailyDetailOverHoursVoList = iTmgDailyService.selectDailyDetailOverHours(psDBBean.getCustID(),psDBBean.getCompCode(),baseDate,referList.buildSQLForSelectEmployees());
        // 3 基準日時点の超勤限度時間取得
        List<LimitOfBaseDate> limitOfBaseDateList = iTmgGroupAttributeService.selectLimitOfBaseDate(psDBBean.getCustID(),psDBBean.getCompCode(),referList.getTargetSec(),baseDate);

        // 4 標準の勤務パターンを取得
        int workTime=iTmgPatternService.selectStandardWorkTime(psDBBean.getCustID(),psDBBean.getCompCode(),baseDate);

        // 5 日別詳細情報より勤務予定時間外の休憩開始・終了時間を取得
        List<ResultRest40tVo> resultRest40TVoList =iTmgDailyDetailService.selectResultRest40t(psDBBean.getCustID(),psDBBean.getCompCode(),baseDate,referList.buildSQLForSelectEmployees());



        List<DailyOverTimeVo> dailyOverTimeVos=new ArrayList<>();

        for(DailyVo dailyVo:dailyVoList){
            DailyOverTimeVo vo=new DailyOverTimeVo();
            vo.setEmpId(dailyVo.getTdaCemployeeid());
            vo.setEmpName(dailyVo.getTdaCemployeeidName());
            vo.setWorkingId(dailyVo.getTdaCworkingidR());
            vo.setOpenN(dailyVo.getTdaNopenN());
            vo.setCloseN(dailyVo.getTdaNcloseN());
            vo.setOpenTp(dailyVo.getTdaNopenTp());
            vo.setCloseTp(dailyVo.getTdaNcloseTp());
            for(DailyDetailOverHoursVo overtime:dailyDetailOverHoursVoList){
                if(vo.getEmpId().equals(overtime.getTdaCemployeeid())){
                    vo.getOverTimeList().add(overtime);
                }
            }
            for(ResultRest40tVo restTime:resultRest40TVoList){
                if(vo.getEmpId().equals(restTime.getTdaCemployeeid())){
                    vo.getRestTimeList().add(restTime);
                }
            }
            vo.setMessage(dailyVo.getTdaCmessage());
            dailyOverTimeVos.add(vo);
        }
        return dailyOverTimeVos;
    }

    // 日別情報更新処理
    public void actioneExecuteUpdate(List<UpdateDto> updateDtoList,PsDBBean psDBBean){
        //paramSetting();

        for (UpdateDto updateDto: updateDtoList) {
            // 日次詳細CHECKデータ（超過勤務）　クリア
            int deleteTmgDailyDetailCheck=ITmgDailyDetailCheckService.getBaseMapper().delete(SysUtil.<TmgDailyDetailCheckDO>query()
                    .eq("TDAD_CCUSTOMERID",param.getCustId())
                    .eq("TDAD_CCOMPANYID", param.getCompId())
                    .eq("TDAD_CEMPLOYEEID", updateDto.getSEmpId())
                    .eq("TDAD_DYYYYMM", param.getBaseDateMM())
                    .eq("TDAD_CNOTWORKID", TMG_ITEM_OVERHOURS));

            for (UpdateOverTimeDto updateTime:updateDto.getUpdateOverTimeDtoList()){
                // 日次詳細CHECKデータ（超過勤務）　登録
                if(!StrUtil.hasEmpty(updateTime.getSNOpen()) && !StrUtil.hasEmpty(updateTime.getSNClose()) && !StrUtil.hasEmpty(updateTime.getSCComment()) ){
                    int insertTmgDailyDetailCheck = insertTmgDailyDetailCheck(updateTime.getSNOpen(),
                            updateTime.getSNClose(),
                            updateDto.getSEmpId(),
                            param.getUserCode(),
                            TMG_ITEM_OVERHOURS);
                }
            }

            if(isUseableEditRest4OT()) {
                // 日次詳細CHECKデータ（休憩）　クリア
                int deleteTmgDailyDetailCheckResult = ITmgDailyDetailCheckService.getBaseMapper().delete(SysUtil.<TmgDailyDetailCheckDO>query()
                        .eq("TDAD_CCUSTOMERID", param.getCustId())
                        .eq("TDAD_CCOMPANYID", param.getCompId())
                        .eq("TDAD_CEMPLOYEEID", updateDto.getSEmpId())
                        .eq("TDAD_DYYYYMMDD", param.getBaseDate())
                        .eq("TDAD_CNOTWORKID", "TMG_ITEMS|ResultRest")
                        .eq("TDAD_CMODIFIERPROGRAMID", "OvertimeInstruct_" + param.getAction()));
                // 日次詳細CHECKデータ（超過勤務）　登録
                for (UpdateRestTimeDto updateRest : updateDto.getUpdateRestTimeDtoList()) {
                    int insertTmgDailyDetailCheck = insertTmgDailyDetailCheck(updateRest.getSNRestOpen(),
                            updateRest.getSNRestClose(),
                            updateDto.getSEmpId(),
                            param.getUserCode(),
                            TmgUtil.Cs_MGD_ITEMS_ResultRest);
                }
            }
            // 超勤命令反映処理（トリガー起動）
            int insertTrigger=insertTrigger(updateDto.getSEmpId());
            int deleteTrigger=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                                                .eq("TTR_CMODIFIERUSERID",param.getUserCode())
                                                .eq("TTR_CMODIFIERPROGRAMID",BEANDESC + "_" +param.getAction())
                                                .eq("TTR_CCUSTOMERID",param.getCustId())
                                                .eq("TTR_CCOMPANYID",param.getCompId()));
            // 超過勤務命令の編集休憩時間を登録した場合、事後処理として日次詳細CHECKデータの削除を行う。
            if(isUseableEditRest4OT()&& updateDto.getUpdateRestTimeDtoList().size()>0){
                // 日次詳細CHECKデータ削除
                int deleteTmgDailyDetailCheckResultAft= ITmgDailyDetailCheckService.getBaseMapper().delete(SysUtil.<TmgDailyDetailCheckDO>query()
                        .eq("TDAD_CCUSTOMERID", param.getCustId())
                        .eq("TDAD_CCOMPANYID", param.getCompId())
                        .eq("TDAD_CEMPLOYEEID", updateDto.getSEmpId())
                        .eq("TDAD_DYYYYMMDD", param.getBaseDate())
                        .eq("TDAD_CNOTWORKID", "TMG_ITEMS|ResultRest")
                        .eq("TDAD_CMODIFIERPROGRAMID", "OvertimeInstruct_" + param.getAction()));
            }

            // 日次詳細CHECKデータ（超過勤務）　クリア
            int deleteTmgDailyDetailCheckAfr=ITmgDailyDetailCheckService.getBaseMapper().delete(SysUtil.<TmgDailyDetailCheckDO>query()
                    .eq("TDAD_CCUSTOMERID",param.getCustId())
                    .eq("TDAD_CCOMPANYID", param.getCompId())
                    .eq("TDAD_CEMPLOYEEID", updateDto.getSEmpId())
                    .eq("TDAD_DYYYYMM", param.getBaseDateMM())
                    .eq("TDAD_CNOTWORKID", TMG_ITEM_OVERHOURS));
        }
    }

    /**
     * 日別情報（超過勤務命令）を登録するSQL
     */
    private int insertTmgDailyDetailCheck(String open,String close, String empId, String userCode,String workId){
        TmgDailyDetailCheckDO tdadDo=new TmgDailyDetailCheckDO();
        tdadDo.setTdadCcustomerid(param.getCustId());
        tdadDo.setTdadCcompanyid(param.getCompId());
        tdadDo.setTdadCemployeeid(empId);
        tdadDo.setTdadDstartdate(TmgUtil.minDate);
        tdadDo.setTdadDenddate(TmgUtil.maxDate);
        tdadDo.setTdadCmodifieruserid(userCode);
        tdadDo.setTdadDmodifieddate(DateTime.now());
        tdadDo.setTdadCmodifierprogramid("OvertimeInstruct_"+param.getAction());
        tdadDo.setTdadNyyyy(Long.parseLong(param.getBaseDateYYYY()));
        tdadDo.setTdadDyyyymm(DateUtil.parse(param.getBaseDateMM()));
        tdadDo.setTdadDyyyymmdd(param.getBaseDateD());
        tdadDo.setTdadCnotworkid(workId);
        tdadDo.setTdadNopen(iMastGenericDetailService.tmgFConvHhmi2Min(open));
        tdadDo.setTdadNclose(iMastGenericDetailService.tmgFConvHhmi2Min(close));
        tdadDo.setTdadNdeleteflg(0L);

        return ITmgDailyDetailCheckService.getBaseMapper().insert(tdadDo);
    }

    /**
     * 勤怠トリガーテーブルへ挿入するSQL文を返します。
     */
     private int insertTrigger(String empId){
        TmgTriggerDO ttDo=new TmgTriggerDO();
        ttDo.setTtrCcustomerid(param.getCustId());
        ttDo.setTtrCcompanyid(param.getCompId());
        ttDo.setTtrCemployeeid(empId);
        ttDo.setTtrDstartdate(TmgUtil.minDate);
        ttDo.setTtrDenddate(TmgUtil.maxDate);
        ttDo.setTtrCmodifieruserid(param.getUserCode());
        ttDo.setTtrDmodifieddate(DateTime.now());
        ttDo.setTtrCmodifierprogramid(BEANDESC + "_" +param.getAction());
        ttDo.setTtrCprogramid(BEANDESC + "_" +param.getAction());
        ttDo.setTtrCparameter1(param.getAction());
        ttDo.setTtrCparameter2(param.getBaseDate());
        return iTmgTriggerService.getBaseMapper().insert(ttDo);
    }

    /** 休憩時間編集機能の使用可否判定フラグ */
    private Boolean bUseEditRest4OTFlg = null;
    /**
     * 休憩時間編集機能が使用可能か判定します。
     * @return true：使用可能、false：使用不可能
     */
    public boolean isUseableEditRest4OT() {

        // 最初の１度目のみ判定を行う。
        if (bUseEditRest4OTFlg == null) {

            try {

                // システムプロパティの設定が'yes'の場合のみ、使用可能とする。
                //todo
                //String sVal = getSystemProperty(TmgUtil.Cs_CYC_PROPNAME_EDITABLE_REST_4OVERTIMEINSTRUCT);
                String sVal ="yes";
                if (sVal != null && "yes".equalsIgnoreCase(sVal)) {
                    bUseEditRest4OTFlg = true;
                } else {
                    bUseEditRest4OTFlg = false;
                }
            } catch(Exception e) {
                // 例外が発生した場合、使用不可設定とする。
                bUseEditRest4OTFlg = false;
            }
        }

        return bUseEditRest4OTFlg.booleanValue();
    }


    /**
     * "5.20"のような文字列なら"."の前に改行を入れて出力する
     * @param time
     * @return
     */
    private String formatTime(String time){
        if(time == null || time.equals("")){
            return "";
        }
        if(time.indexOf(".") < 0){
            if(time.indexOf("@")>-1){
                String[] s=time.split("@");
                return s[0]+"\n"+".00"+"@"+s[1];
            }
            return time+"\n"+".00";
        }
        return time.subSequence(0, time.indexOf("."))
                + "\n" + time.substring(time.indexOf(".")) ;
    }


    /**
     *
     * @param  sValue    閾値の値
     * @param  sLevel    閾値のレベル
     * @param  sNextLevel
     * @return
     */
    public int getTypeOfStyleByLevelLimit(String sValue, String sLevel, String sNextLevel) {
        /* 評価の閾値を5段階設定するか否かの判定 */
        if(sLevel != null){
            /* 対象範囲は[閾値-閾値]か[閾値- ]なのかの判定 */
            if(sNextLevel != null){
                /* 実労働時間が5段階目の閾値より多いか否かの判定 */
                if((Double.parseDouble(sLevel) < Double.parseDouble(sValue))
                        && (Double.parseDouble(sNextLevel) >= Double.parseDouble(sValue))){
                    return 1;
                }
            } else {
                /* 実労働時間が5段階目の閾値より多いか否かの判定 */
                if(Double.parseDouble(sLevel) < Double.parseDouble(sValue)){
                    return 1;
                }
            }
        }
        return 0;
    }


    /**
     * 超過勤務命令一覧画面の集計項目欄の表示スタイルのCSSクラス名を取得します。
     *
     * @param  sValue    値
     * @param  sCategory カテゴリ
     *                    1:合計(月)   2:合計(年)   3:月超過回数
     * @return CSS定義クラス名
     */
    public String getTypeOfStyleByLimit(String sValue, String sCategory) {
        if(StrUtil.hasEmpty(sValue)){
            return "";
        }
         //月超勤回数
        if(CATEGORY_OVER_WORK.equals(sCategory)) {

            if(overTimeLimitDtos.getOtDaily01() != null && sValue != null){
                if(Double.parseDouble(overTimeLimitDtos.getOtDaily01()) < Double.parseDouble(sValue)) {
                    return sValue+"@"+STYLE_CLASS_OVER_WORK_WARNING_LVL1;
                } else {
                    return sValue ;
                }
            } else {
                return sValue ;
            }

             //合計(月)
        } else if(CATEGORY_SUM_MONTH.equals(sCategory)) {
             //評価の閾値:1段階
            if (getTypeOfStyleByLevelLimit(sValue, overTimeLimitDtos.getOtMontly01(), overTimeLimitDtos.getOtMontly02()) != 0) {
                return sValue+"@"+STYLE_CLASS_WARNING_LVL1;
            }
             //評価の閾値:2段階
            if (getTypeOfStyleByLevelLimit(sValue, overTimeLimitDtos.getOtMontly02(), overTimeLimitDtos.getOtMontly03()) != 0) {
                return sValue+"@"+STYLE_CLASS_WARNING_LVL2;
            }
             //評価の閾値:3段階
            if (getTypeOfStyleByLevelLimit(sValue, overTimeLimitDtos.getOtMontly03(), overTimeLimitDtos.getOtMontly04()) != 0) {
                return sValue+"@"+STYLE_CLASS_WARNING_LVL3;
            }
             //評価の閾値:4段階
            if (getTypeOfStyleByLevelLimit(sValue, overTimeLimitDtos.getOtMontly04(), overTimeLimitDtos.getOtMontly05()) != 0) {
                return sValue+"@"+STYLE_CLASS_WARNING_LVL4;
            }
             //評価の閾値:5段階
            if (getTypeOfStyleByLevelLimit(sValue, overTimeLimitDtos.getOtMontly05(), null) != 0) {
                return sValue+"@"+STYLE_CLASS_WARNING_LVL5;
            }


             //合計(年)
        } else if(CATEGORY_SUM_YEAR.equals(sCategory)) {
             //評価の閾値:1段階
            if (getTypeOfStyleByLevelLimit(sValue, overTimeLimitDtos.getOtYearly01(), overTimeLimitDtos.getOtYearly02()) != 0) {
                return sValue+"@"+STYLE_CLASS_WARNING_LVL1;
            }
             //評価の閾値:2段階
            if (getTypeOfStyleByLevelLimit(sValue, overTimeLimitDtos.getOtYearly02(), overTimeLimitDtos.getOtYearly03()) != 0) {
                return sValue+"@"+STYLE_CLASS_WARNING_LVL2;
            }
             //評価の閾値:3段階
            if (getTypeOfStyleByLevelLimit(sValue, overTimeLimitDtos.getOtYearly03(), overTimeLimitDtos.getOtYearly04()) != 0) {
                return sValue+"@"+STYLE_CLASS_WARNING_LVL3;
            }
             //評価の閾値:4段階
            if (getTypeOfStyleByLevelLimit(sValue, overTimeLimitDtos.getOtYearly04(), overTimeLimitDtos.getOtYearly05()) != 0) {
                return sValue+"@"+STYLE_CLASS_WARNING_LVL4;
            }
             //評価の閾値:5段階
            if (getTypeOfStyleByLevelLimit(sValue, overTimeLimitDtos.getOtYearly05(), null) != 0) {
                return sValue+"@"+STYLE_CLASS_WARNING_LVL5;
            }
             //月超勤回数
        } else if(CATEGORY_COUNT.equals(sCategory)) {

            if(overTimeLimitDtos.getOtMonthlycount() != null){
                if(Double.parseDouble(overTimeLimitDtos.getOtMonthlycount()) < Double.parseDouble(sValue)) {
                    return sValue+"@"+STYLE_CLASS_WARNING_LVL2;
                }
            }
             //休日出勤回数
        } else if(CATEGORY_HOL_CNT.equals(sCategory)) {

             //評価の閾値:1段階
            if (getTypeOfStyleByLevelLimit(sValue, holidayTimeLimitDtos.getHtMontly01(), holidayTimeLimitDtos.getHtMontly02()) != 0) {
                return sValue+"@"+STYLE_CLASS_WARNING_LVL1;
            }
             //評価の閾値:2段階
            if (getTypeOfStyleByLevelLimit(sValue, holidayTimeLimitDtos.getHtMontly02(), holidayTimeLimitDtos.getHtMontly03()) != 0) {
                return sValue+"@"+STYLE_CLASS_WARNING_LVL2;
            }
             //評価の閾値:3段階
            if (getTypeOfStyleByLevelLimit(sValue, holidayTimeLimitDtos.getHtMontly03(), holidayTimeLimitDtos.getHtMontly04()) != 0) {
                return sValue+"@"+STYLE_CLASS_WARNING_LVL3;
            }
             //評価の閾値:4段階
            if (getTypeOfStyleByLevelLimit(sValue, holidayTimeLimitDtos.getHtMontly04(), holidayTimeLimitDtos.getHtMontly05()) != 0) {
                return sValue+"@"+STYLE_CLASS_WARNING_LVL4;
            }
             //評価の閾値:5段階
            if (getTypeOfStyleByLevelLimit(sValue, holidayTimeLimitDtos.getHtMontly05(), null) != 0) {
                return sValue+"@"+STYLE_CLASS_WARNING_LVL5;
            }

        }
         //超勤実績の月平均時間
        else if(CATEGORY_OVERWORK_AVG_MONTH.equals(sCategory)){
            if(overTimeLimitDtos.getOtMonthlyavg() != null && sValue != null){
                if(Double.parseDouble(overTimeLimitDtos.getOtMonthlyavg()) < Double.parseDouble(sValue)) {
                    return sValue+"@"+STYLE_OVERWORK_AVG_WAR_LVL1;
                } else {
                    return sValue ;
                }
            } else {
                return sValue ;
            }

        }
        return sValue;
    }


    /**
     * 月別一覧画面のスタイルを返却します。
     *
     * @param  state 承認状況ステータス
     * @return 表示スタイル
     */
    public String getTypeOfStyleByState(String state) {

        String retStyle = null;
        // 表示スタイルの設定
        if(state == null || state.length() == 0){
            retStyle = state;
        }else{
            retStyle = state+"@"+STYLE_CLASS_CHOKIN_MEIREI;
        }
        return retStyle;
    }

}
