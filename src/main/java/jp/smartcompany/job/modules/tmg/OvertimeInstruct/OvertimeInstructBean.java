package jp.smartcompany.job.modules.tmg.OvertimeInstruct;


import jp.smartcompany.job.modules.core.service.*;
import jp.smartcompany.job.modules.tmg.OvertimeInstruct.dto.dispOverTimeItemsDto;
import jp.smartcompany.job.modules.tmg.OvertimeInstruct.dto.paramOvertimeInstructDto;
import jp.smartcompany.job.modules.tmg.OvertimeInstruct.vo.*;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.CompanyVO;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

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

    paramOvertimeInstructDto param = new paramOvertimeInstructDto();

    public void actionExecuteDispResult(ModelMap modelMap) {
        // 0 対象勤務年月の1ヶ月間の日付・曜日を取得
        List<OneMonthDetailVo> oneMonthDetailVoList = iTmgCalendarService.selectDayCount(param.getBaseDate());
        // 1 カレンダーテーブルより休日フラグを取得
        List<CalenderVo> calenderVoList = iTmgCalendarService.selectGetCalendarList(param.getCustId(),
                param.getCompId(), param.getTargetSec(), param.getTargetGroup(), param.getBaseDateYYYY(), param.getBaseDate());
        // 2 表示対象社員の今月分の一日毎の超過勤務実績時間を取得
        List<monthlyInfoOtVo> monthlyInfoOtVoList = iTmgMonthlyInfoService.selectMonthlyInfoOtr(param.getCustId(), param.getCompId(), param.getTargetSec(),
                null, param.getBaseDate(), param.getLang(), param.getEmployeeListSql());
        // 3 前月リンクを取得
        String beforeBaseDate = iTmgMonthlyInfoService.selectAftBefBaseDate(param.getCustId(), param.getCompId(), param.getBaseDate(), param.getEmployeeListSql(), 1);
        // 4 翌月リンクを取得
        String AfterBaseDate = iTmgMonthlyInfoService.selectAftBefBaseDate(param.getCustId(), param.getCompId(), param.getBaseDate(), param.getEmployeeListSql(), 0);
        // 5 表示対象社員の今年度分の合計超過実績時間と、月超過回数を取得
        List<yearlyInfoVo> yearlyInfoVoList = iTmgMonthlyInfoService.selectYearlyInfo(param.getCustId(), param.getCompId(), param.getTargetSec(), null,
                param.getBaseDate(), param.getToday(), param.getLang(), param.getEmployeeListSql());
        // 6 36協定における月の超勤限度時間表示用名称取得
        String limit = iMastGenericDetailService.selectLimit(param.getCustId(), param.getCompId(), param.getBaseDate(), param.getLang(), TmgUtil.Cs_MGD_LIMIT_MONTHLY_OVERTIME_36);

    }


    public void actionExecuteDisp(ModelMap modelMap) {
        param.setCompId("01");
        param.setCustId("01");
        param.setBaseDateYYYY("2020");
        param.setTargetGroup("");
        param.setTargetSec("");
        // 0 対象勤務年月の1ヶ月間の日付・曜日を取得
        List<OneMonthDetailVo> oneMonthDetailVoList = iTmgCalendarService.selectDayCount(param.getBaseDate());
        // 1 カレンダーテーブルより休日フラグを取得
        List<CalenderVo> calenderVoList = iTmgCalendarService.selectGetCalendarList(param.getCustId(),
                param.getCompId(), param.getTargetSec(), param.getTargetGroup(), param.getBaseDateYYYY(), param.getBaseDate());
        // 2 表示対象社員の超過勤務命令時間を取得
        //buildSQLForSelectTMG_MONTHLY_INFO_OTI
        List<monthlyInfoOtVo> monthlyInfoOtVoList = iTmgMonthlyInfoService.selectMonthlyInfoOtr(param.getCustId(), param.getCompId(), param.getTargetSec(),
                null, param.getBaseDate(), param.getLang(), param.getEmployeeListSql());
        // 3 前月リンクを取得
        String beforeBaseDate = iTmgMonthlyInfoService.selectAftBefBaseDate(param.getCustId(), param.getCompId(), param.getBaseDate(), param.getEmployeeListSql(), 1);
        // 4 翌月リンクを取得
        String AfterBaseDate = iTmgMonthlyInfoService.selectAftBefBaseDate(param.getCustId(), param.getCompId(), param.getBaseDate(), param.getEmployeeListSql(), 0);

    }


    public void actionexecuteDisp6MonthsAvg(){
        List<monthlyInfoOverSumVo> monthlyInfoOverSumVoList=iTmgDailyService.selectMonthlyOverSum(param.getCustId(),param.getCompId(),param.getTargetUser()
        ,param.getBaseDate(),"-5");
    }


    public void actionExecuteEdit(){
        // 編集画面表示項目マスタ制御設定取得
        List<dispOverTimeItemsDto> dispOverTimeItemsDtos= iMastGenericDetailService.selectDispOverTimeItems(param.getCustId(),param.getCompId(),param.getBaseDate(),param.getLang());
        // 0 日別情報より予定出社・退社時間、超過勤務命令開始・終了時間を取得
        List<dailyDetailVo> dailyDetailVoList=iTmgDailyService.selectDailyDetail(param.getCustId(),param.getCompId(),param.getTargetSec(),param.getBaseDate(),param.getBaseDateMM(),
                param.getLang(),param.getEmployeeListSql(),dispOverTimeItemsDtos);
        // 1 予定出社時間・予定退社時間の基準値を取得
        CompanyVO companyVO = iTmgCompanyService.buildSQLSelectCompany(param.getCustId(), param.getCompId(), param.getBaseDate());


    }
}
