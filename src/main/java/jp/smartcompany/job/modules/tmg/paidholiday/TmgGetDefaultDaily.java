package jp.smartcompany.job.modules.tmg.paidholiday;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.job.modules.base.pojo.dto.PluggableDTO;
import jp.smartcompany.job.modules.base.service.BaseExecute;
import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgCalendarRow;
import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgDailyRow;
import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgDailyTemp;
import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgTimeRange;
import jp.smartcompany.job.modules.tmg.patternsetting.TmgFGetPatternDefault;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.TmgPatternRow;
import jp.smartcompany.job.modules.tmg.util.TmgPKG;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Nie Wanqun
 * <p>
 * TMG_F_GET_DEFAULT_DAILY
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgGetDefaultDaily extends BaseExecute {

    /**
     * TmgGetMgdC
     */
    private final TmgGetMgdC tmgGetMgdC;

    /**
     * TmgFGetPatternDefault
     */
    private final TmgFGetPatternDefault tmgFGetPatternDefault;

    /**
     * TmgFGetPatternDefault
     */
    private final TmgGetCalendarList tmgGetCalendarList;


    @Override
    public Object execute(PluggableDTO pluggableDTO) {
        return null;
    }

    /**
     * TMG_F_GET_DEFAULT_DAILY
     *
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param employeeId 社員番号
     * @param startDate  開始日
     * @param endDate    終了日
     * @return List<TmgDailyRow>
     */
    public List<TmgDailyRow> init(String customerId, String companyId, String employeeId, Date startDate, Date endDate) {

        // 初期化
        List<TmgDailyRow> tmgDailyRowList = new ArrayList<TmgDailyRow>();

        // 開始日と終了日設定
        Date dStartDate;
        Date dEndDate;

        if (startDate == null && endDate == null) {

            // 当月
            dStartDate = DateUtil.beginOfMonth(DateUtil.date());
            dEndDate = DateUtil.endOfMonth(DateUtil.date());
        } else if (startDate != null && endDate == null) {

            // 指定年月
            dStartDate = DateUtil.beginOfMonth(startDate);
            dEndDate = DateUtil.endOfMonth(startDate);
        } else if (startDate == null && endDate != null) {

            // 指定年月
            dStartDate = DateUtil.beginOfMonth(endDate);
            dEndDate = DateUtil.endOfMonth(endDate);
        } else {

            // その他
            dStartDate = startDate;
            dEndDate = endDate;
        }

        // 勤務パターン
        String patternHolFlg;
        //休日フラグ
        String tcaCholFlg;
        // 日別勤務時間数
        int tppmNinfo;
        // 平日、休日の処理区分（0:平日/1:休日）
        int sSwitch;
        // 勤務パターンID
        String patternid;
        // 就業区分
        String workingid;

        // CURSOR_TMG_DAILY
        List<TmgDailyTemp> tmgDailyTempList = this.cursorTmgDaily(customerId, companyId, employeeId, dStartDate, dEndDate);
        for (TmgDailyTemp tmgDailyTemp : tmgDailyTempList) {

            List<TmgTimeRange> tmgTimeRangeList;

            // デフォルト勤務パターン取得
            TmgPatternRow tmgPatternRow = tmgFGetPatternDefault.init(customerId, companyId, employeeId, tmgDailyTemp.getYyyyMmDd());
            // 休日フラグの成型
            if (tmgPatternRow != null && tmgPatternRow.getCHolFlg() != null) {
                patternHolFlg = "TMG_HOLFLG|" + tmgPatternRow.getCHolFlg();
            } else {
                patternHolFlg = null;
            }

            // TMG_CALENDARが作成されていない場合はTMG_DAILYを作成しない？
            if (!StringUtils.isEmpty(tmgDailyTemp.getHolFlg())) {
                if (TmgPKG.CS_HOLFLG_2.equals(tmgDailyTemp.getHolFlg())) {
                    // 祝日が最優先
                    tcaCholFlg = tmgDailyTemp.getHolFlg();
                } else {
                    // 勤務パターンにない場合は、カレンダー
                    if (!StringUtils.isEmpty(patternHolFlg)) {
                        tcaCholFlg = patternHolFlg;
                    } else {
                        tcaCholFlg = tmgDailyTemp.getHolFlg();
                    }
                }

                // 日別勤務時間数
                tppmNinfo = tmgPatternRow.getNTime();

                // 休日フラグと勤務時間で就業区分を設定
                if (TmgPKG.CS_HOLFLG_0.equals(tcaCholFlg)) {
                    sSwitch = 1;
                } else if (tppmNinfo == 0) {
                    sSwitch = 1;
                } else {
                    sSwitch = 0;
                }
                // 処理区分により分岐
                if (sSwitch == 0) {
                    // 平日としてセット
                    tmgTimeRangeList = tmgPatternRow.getTimeRange();
                    workingid = TmgPKG.CS_WORK_000;
                    patternid = tmgPatternRow.getCPatternId();

                } else {
                    // 休日としてセット
                    tmgTimeRangeList = new ArrayList<TmgTimeRange>();
                    workingid = tmgGetMgdC.init("01", "01", tmgDailyTemp.getYyyyMmDd(), tcaCholFlg, 1, "ja");
                    patternid = "";
                }

                // 各項目設定
                TmgDailyRow tmgDailyRow = new TmgDailyRow();
                tmgDailyRow.setCCustomerId(tmgDailyTemp.getCustomerId());
                tmgDailyRow.setCCompanyId(tmgDailyTemp.getCompanyId());
                tmgDailyRow.setCEmployeeId(tmgDailyTemp.getEmployeeId());
                tmgDailyRow.setDStartDate(TmgPKG.CD_MINDATE);
                tmgDailyRow.setDDndDate(TmgPKG.CD_MAXDATE);
                tmgDailyRow.setNYyyy(tmgDailyTemp.getYyyy());
                tmgDailyRow.setDYyyyMm(tmgDailyTemp.getYyyyMm());
                tmgDailyRow.setDYyyyMmDd(tmgDailyTemp.getYyyyMmDd());
                tmgDailyRow.setCStatusFlg(TmgPKG.CS_DATASTATUS_0);
                tmgDailyRow.setCHolFlg(tcaCholFlg);
                tmgDailyRow.setCWorkingIdP(workingid);
                tmgDailyRow.setTimeRangeP(tmgTimeRangeList);
                tmgDailyRow.setNLockP(0);
                tmgDailyRow.setNRest45P(0);
                tmgDailyRow.setTimeRangeN(tmgTimeRangeList);
                tmgDailyRow.setCWorkingIdR(workingid);
                tmgDailyRow.setTimeRangeR(tmgTimeRangeList);
                tmgDailyRow.setCBusinessTripIdP(TmgPKG.CS_BUSINESS_TRIP_00);
                tmgDailyRow.setCBusinessTripIdR(TmgPKG.CS_BUSINESS_TRIP_00);
                tmgDailyRow.setCPatternId(patternid);
                tmgDailyRowList.add(tmgDailyRow);
            }
        }
        return tmgDailyRowList;
    }

    /**
     * CURSOR_TMG_DAILY
     *
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param employeeId 社員番号
     * @param startDate  開始日
     * @param endDate    終了日
     * @return List<CursorTmgDaily>
     */
    private List<TmgDailyTemp> cursorTmgDaily(String customerId, String companyId, String employeeId, Date startDate, Date endDate) {

        List<TmgDailyTemp> tmgDailyTempList = new ArrayList<TmgDailyTemp>();

        TmgDailyTemp tmgDailyTemp;
        // TODO TMG_F_GET_DATE
        List<Date> dateList = new ArrayList<Date>();
        for (Date date : dateList) {

            tmgDailyTemp = new TmgDailyTemp();
            tmgDailyTemp.setCustomerId(customerId);
            tmgDailyTemp.setCompanyId(companyId);
            tmgDailyTemp.setEmployeeId(employeeId);
            tmgDailyTemp.setYyyy(DateUtil.year(date));
            tmgDailyTemp.setYyyyMm(DateUtil.beginOfMonth(date));
            tmgDailyTemp.setYyyyMmDd(date);
            // TMG_F_GET_CALENDAR_LIST
            List<TmgCalendarRow> tmgCalendarRowList = tmgGetCalendarList.init(customerId,companyId,employeeId,"","",date);
            tmgDailyTemp.setHolFlg(this.getHolFlg(customerId, companyId, DateUtil.beginOfMonth(date), DateUtil.dayOfMonth(date), tmgCalendarRowList));
        }

        return tmgDailyTempList;
    }

    /**
     * 休日フラグ取得
     *
     * @param customerId         顧客コード
     * @param companyId          法人コード
     * @param yyyyMm             対象年月
     * @param tmgCalendarRowList List<TmgCalendarRow>
     * @return HolFlg　休日フラグ
     */
    private String getHolFlg(String customerId, String companyId, Date yyyyMm, int dd, List<TmgCalendarRow> tmgCalendarRowList) {

        for (TmgCalendarRow tmgCalendarRow : tmgCalendarRowList) {
            boolean flg = customerId.equals(tmgCalendarRow.getTcaCustomerId())
                    && companyId.equals(tmgCalendarRow.getTcaCompanyId())
                    && yyyyMm.equals(tmgCalendarRow.getTcaYyyyMm());
            if (flg) {
                String holFlg = "";
                if (dd == 1) {
                    holFlg = tmgCalendarRow.getTcaHolFlg01();
                } else if (dd == 2) {
                    holFlg = tmgCalendarRow.getTcaHolFlg02();
                } else if (dd == 3) {
                    holFlg = tmgCalendarRow.getTcaHolFlg03();
                } else if (dd == 4) {
                    holFlg = tmgCalendarRow.getTcaHolFlg04();
                } else if (dd == 5) {
                    holFlg = tmgCalendarRow.getTcaHolFlg05();
                } else if (dd == 6) {
                    holFlg = tmgCalendarRow.getTcaHolFlg06();
                } else if (dd == 7) {
                    holFlg = tmgCalendarRow.getTcaHolFlg07();
                } else if (dd == 8) {
                    holFlg = tmgCalendarRow.getTcaHolFlg08();
                } else if (dd == 9) {
                    holFlg = tmgCalendarRow.getTcaHolFlg09();
                } else if (dd == 10) {
                    holFlg = tmgCalendarRow.getTcaHolFlg10();
                } else if (dd == 11) {
                    holFlg = tmgCalendarRow.getTcaHolFlg11();
                } else if (dd == 12) {
                    holFlg = tmgCalendarRow.getTcaHolFlg12();
                } else if (dd == 13) {
                    holFlg = tmgCalendarRow.getTcaHolFlg13();
                } else if (dd == 14) {
                    holFlg = tmgCalendarRow.getTcaHolFlg14();
                } else if (dd == 15) {
                    holFlg = tmgCalendarRow.getTcaHolFlg15();
                } else if (dd == 16) {
                    holFlg = tmgCalendarRow.getTcaHolFlg16();
                } else if (dd == 17) {
                    holFlg = tmgCalendarRow.getTcaHolFlg17();
                } else if (dd == 18) {
                    holFlg = tmgCalendarRow.getTcaHolFlg18();
                } else if (dd == 19) {
                    holFlg = tmgCalendarRow.getTcaHolFlg19();
                } else if (dd == 20) {
                    holFlg = tmgCalendarRow.getTcaHolFlg20();
                } else if (dd == 21) {
                    holFlg = tmgCalendarRow.getTcaHolFlg21();
                } else if (dd == 22) {
                    holFlg = tmgCalendarRow.getTcaHolFlg22();
                } else if (dd == 23) {
                    holFlg = tmgCalendarRow.getTcaHolFlg23();
                } else if (dd == 24) {
                    holFlg = tmgCalendarRow.getTcaHolFlg24();
                } else if (dd == 25) {
                    holFlg = tmgCalendarRow.getTcaHolFlg25();
                } else if (dd == 26) {
                    holFlg = tmgCalendarRow.getTcaHolFlg27();
                } else if (dd == 27) {
                    holFlg = tmgCalendarRow.getTcaHolFlg27();
                } else if (dd == 28) {
                    holFlg = tmgCalendarRow.getTcaHolFlg28();
                } else if (dd == 29) {
                    holFlg = tmgCalendarRow.getTcaHolFlg29();
                } else if (dd == 30) {
                    holFlg = tmgCalendarRow.getTcaHolFlg30();
                } else if (dd == 31) {
                    holFlg = tmgCalendarRow.getTcaHolFlg31();
                }
                return holFlg;
            }
        }
        return "";
    }
}
