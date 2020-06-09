package jp.smartcompany.job.modules.tmg.overtimeInstruct;


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

import java.util.ArrayList;
import java.util.List;

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
    public final PsDBBean psDBBean;

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


    private void paramSetting(){
        param=null;

        //基本信息
        param.setUserCode(psDBBean.getUserCode());
        param.setAction(psDBBean.getReqParam("txtACTION"));
        param.setCustId(psDBBean.getCustID());
        param.setCompId(psDBBean.getCompCode());
        param.setSiteId(psDBBean.getSiteId());
        param.setLang(psDBBean.getLanguage());
        param.setTargetGroup(referList.getTargetGroup());

        overTimeLimitDtos=iTmgGroupAttributeService.selectOverTimeLimit(param.getCustId(),param.getCompId(),param.getTargetSec(),param.getTargetGroup());

        holidayTimeLimitDtos=iTmgGroupAttributeService.selectHolidayTimeLimit(param.getCustId(),param.getCompId(),param.getTargetSec(),param.getTargetGroup());




    }



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
    public void actionExecuteDispResult(ModelMap modelMap) {


        //paramSetting();

        // 対象者の組織コードが空白の場合は以下の処理を実行しません(勤怠管理サイト対応)
   /*     param.setTargetSec(referList.getTargetSec());
        if(StrUtil.hasEmpty(param.getTargetSec())) {

            // 組織未選択なので、呼出元メソッドへ制御を返す。
            return;
        }

        *//* 情報をDB用(SQLで使用できる形)へ加工 *//*
        *//*
         * コンテンツＩＤはアクションコードによって、切替する。
         *   実績時間画面表示の場合は、「TMG_CONTENTID|OTR」を使用。
         *   実績時間（法定内含む）画面表示の場合は、「TMG_CONTENTID|OTRA」を使用。
         *//**/


        param.setAction("ACT_DISP_RMONTHLY_RESULT");
        param.setCustId("01");
        param.setCompId("01");
        param.setTargetSec("201000000000");
        param.setBaseDate("2020/04/01");
        param.setBaseDateMM("2020/04/01");
        param.setLang("ja");
        param.setBaseDateYYYY("2020");
        param.setToday("2020/06/04");
        param.setEmployeeListSql("SELECT '29042924' as empid,'黒川 弘樹' as empname,1 as seq,'01' as cust,'01' as comp FROM DUAL UNION ALL SELECT '17452600' as empid,'日野原 友佳子' as empname,2 as seq,'01' as cust,'01' as comp FROM DUAL UNION ALL SELECT '46402406' as empid,'松野 強' as empname,3 as seq,'01' as cust,'01' as comp FROM DUAL UNION ALL SELECT '46404536' as empid,'吉田 正和' as empname,4 as seq,'01' as cust,'01' as comp FROM DUAL UNION ALL SELECT '32948535' as empid,'柴田 隆' as empname,5 as seq,'01' as cust,'01' as comp FROM DUAL UNION ALL SELECT '91544761' as empid,'内田 信也' as empname,6 as seq,'01' as cust,'01' as comp FROM DUAL UNION ALL SELECT '49271150' as empid,'渡邉 大輝' as empname,7 as seq,'01' as cust,'01' as comp FROM DUAL UNION ALL SELECT '33760045' as empid,'小西 雅治' as empname,8 as seq,'01' as cust,'01' as comp FROM DUAL ");
        String sDBContentId         = ""; // コンテンツID
        if (ACT_DISP_RMONTHLY_RESULT.equals(param.getAction())) {
            sDBContentId = TmgUtil.Cs_MGD_CONTENTID_OTR;
        } else if(ACT_DISP_RMONTHLY_RESULT_OT100.equals(param.getAction())) {
            sDBContentId = TmgUtil.Cs_MGD_CONTENTID_OTRA;
        } else {
            sDBContentId = TmgUtil.Cs_MGD_CONTENTID_OTR;
        }


        overTimeLimitDtos=iTmgGroupAttributeService.selectOverTimeLimit(param.getCustId(),param.getCompId(),param.getTargetSec(),param.getTargetGroup());

        holidayTimeLimitDtos=iTmgGroupAttributeService.selectHolidayTimeLimit(param.getCustId(),param.getCompId(),param.getTargetSec(),param.getTargetGroup());


        // 0 対象勤務年月の1ヶ月間の日付・曜日を取得
        List<OneMonthDetailVo> oneMonthDetailVoList = iTmgCalendarService.selectDayCount(param.getBaseDate());
        // 1 カレンダーテーブルより休日フラグを取得
        List<CalenderVo> calenderVoList = iTmgCalendarService.selectGetCalendarList(param.getCustId(),
                param.getCompId(), param.getTargetSec(), param.getTargetGroup(), param.getBaseDateYYYY(), param.getBaseDate());
        // 2 表示対象社員の今月分の一日毎の超過勤務実績時間を取得
        List<MonthlyInfoOtVo> monthlyInfoOtVoList = iTmgMonthlyInfoService.selectMonthlyInfoOtr(param.getCustId(), param.getCompId(), param.getTargetSec(),
                sDBContentId, param.getBaseDate(), param.getLang(), param.getEmployeeListSql());
        // 3 前月リンクを取得
        String beforeBaseDate = iTmgMonthlyInfoService.selectAftBefBaseDate(param.getCustId(), param.getCompId(), param.getBaseDate(), param.getEmployeeListSql(), 1);
        // 4 翌月リンクを取得
        String AfterBaseDate = iTmgMonthlyInfoService.selectAftBefBaseDate(param.getCustId(), param.getCompId(), param.getBaseDate(), param.getEmployeeListSql(), 0);
        // 5 表示対象社員の今年度分の合計超過実績時間と、月超過回数を取得
        List<YearlyInfoVo> yearlyInfoVoList = iTmgMonthlyInfoService.selectYearlyInfo(param.getCustId(), param.getCompId(), param.getTargetSec(), sDBContentId,
                param.getBaseDate(), param.getToday(), param.getLang(), param.getEmployeeListSql());
        // 6 36協定における月の超勤限度時間表示用名称取得
        String limit = iMastGenericDetailService.selectLimit(param.getCustId(), param.getCompId(), param.getBaseDate(), param.getLang(), TmgUtil.Cs_MGD_LIMIT_MONTHLY_OVERTIME_36);

        for(MonthlyInfoOtVo monthlyInfoOtVo:monthlyInfoOtVoList){

        }

        for(YearlyInfoVo yearlyInfoVo:yearlyInfoVoList){

        }
    }





    public void actionExecuteDisp(ModelMap modelMap) {
        /*param.setCompId("01");
        param.setCustId("01");
        param.setBaseDateYYYY("2020");
        param.setTargetGroup("");
        param.setTargetSec("");*/
        // 0 対象勤務年月の1ヶ月間の日付・曜日を取得
        List<OneMonthDetailVo> oneMonthDetailVoList = iTmgCalendarService.selectDayCount(param.getBaseDate());
        // 1 カレンダーテーブルより休日フラグを取得
        List<CalenderVo> calenderVoList = iTmgCalendarService.selectGetCalendarList(param.getCustId(),
                param.getCompId(), param.getTargetSec(), param.getTargetGroup(), param.getBaseDateYYYY(), param.getBaseDate());
        // 2 表示対象社員の超過勤務命令時間を取得
        //buildSQLForSelectTMG_MONTHLY_INFO_OTI
        List<MonthlyInfoOtVo> monthlyInfoOtVoList = iTmgMonthlyInfoService.selectMonthlyInfoOtr(param.getCustId(), param.getCompId(), param.getTargetSec(),
                null, param.getBaseDate(), param.getLang(), param.getEmployeeListSql());
        // 3 前月リンクを取得
        String beforeBaseDate = iTmgMonthlyInfoService.selectAftBefBaseDate(param.getCustId(), param.getCompId(), param.getBaseDate(), param.getEmployeeListSql(), 1);
        // 4 翌月リンクを取得
        String AfterBaseDate = iTmgMonthlyInfoService.selectAftBefBaseDate(param.getCustId(), param.getCompId(), param.getBaseDate(), param.getEmployeeListSql(), 0);

    }


    public void actionexecuteDisp6MonthsAvg(){
        List<MonthlyInfoOverSumVo> monthlyInfoOverSumVoList=iTmgDailyService.selectMonthlyOverSum(param.getCustId(),param.getCompId(),param.getTargetUser()
        ,param.getBaseDate(),"-5");
    }


    public void actionExecuteEdit(){
        // 編集画面表示項目マスタ制御設定取得
        List<DispOverTimeItemsDto> DispOverTimeItemsDtos = iMastGenericDetailService.selectDispOverTimeItems(param.getCustId(),param.getCompId(),param.getBaseDate(),param.getLang());
        // 0 日別情報より予定出社・退社時間、超過勤務命令開始・終了時間を取得
        List<DailyVo> dailyVoList =iTmgDailyService.selectDaily(param.getCustId(),param.getCompId(),param.getTargetSec(),param.getBaseDate(),param.getBaseDateMM(),
                param.getLang(),param.getEmployeeListSql(), DispOverTimeItemsDtos);
        // 1 予定出社時間・予定退社時間の基準値を取得
        CompanyVO companyVO = iTmgCompanyService.buildSQLSelectCompany(param.getCustId(), param.getCompId(), param.getBaseDate());
        //2 日別詳細情報より超過勤務命令開始・終了時間を取得
        List<DailyDetailOverHoursVo> dailyDetailOverHoursVoList = iTmgDailyService.selectDailyDetailOverHours(param.getCustId(),param.getCompId(),param.getTargetSec()
                ,param.getBaseDate(),param.getLang(),param.getEmployeeListSql());
        // 3 基準日時点の超勤限度時間取得
        List<LimitOfBaseDate> limitOfBaseDateList = iTmgGroupAttributeService.selectLimitOfBaseDate(param.getCustId(),param.getCompId(),param.getTargetSec(),param.getBaseDate());
        // 4 標準の勤務パターンを取得
        int workTime=iTmgPatternService.selectStandardWorkTime(param.getCustId(),param.getCompId(),param.getBaseDate());
        // 5 日別詳細情報より勤務予定時間外の休憩開始・終了時間を取得
        List<ResultRest40tVo> resultRest40TVoList =iTmgDailyDetailService.selectResultRest40t(param.getCustId(),param.getCompId(),param.getBaseDate(),param.getEmployeeListSql());
    }

    public void actioneExecuteUpdate(){

        List<UpdateDto> updateDtoList=new ArrayList<UpdateDto>();

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
            return time;
        }
        if(time.indexOf(".") < 0){
            return time+"<br />"+".00";
        }
        return time.subSequence(0, time.indexOf("."))
                + "<br />" + time.substring(time.indexOf(".")) ;
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


}
