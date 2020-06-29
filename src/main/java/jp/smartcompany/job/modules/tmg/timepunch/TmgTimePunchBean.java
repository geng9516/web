package jp.smartcompany.job.modules.tmg.timepunch;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.job.modules.core.service.ITmgScheduleService;
import jp.smartcompany.job.modules.core.service.ITmgTimepunchService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.schedule.dto.NpaidRestDTO;
import jp.smartcompany.job.modules.tmg.timepunch.dto.BaseTimesDTO;
import jp.smartcompany.job.modules.tmg.timepunch.dto.DutyAndRelaxDateDTO;
import jp.smartcompany.job.modules.tmg.timepunch.dto.DutyDaysAndHoursDTO;
import jp.smartcompany.job.modules.tmg.timepunch.dto.ScheduleInfoDTO;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author 陳毅力
 * @description 打刻システム
 * @objectSource ps.c01.tmg.TmgTimePunch.TmgTimePunchBean
 * @date 2020/06/25
 **/
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgTimePunchBean {

    private final Logger logger = LoggerFactory.getLogger(TmgTimePunchBean.class);
    private final ITmgTimepunchService iTmgTimepunchService;
    private final ITmgScheduleService iTmgScheduleService;
    private PsDBBean psDBBean;
    private final String Cs_MINDATE = "1900/01/01";
    private final String Cs_MAXDATE = "2222/12/31";
    private final String BEAN_DESC = "TimePunch";
    private final String DATE_FORMAT = "yyyy/MM/dd";

    /**
     * プログラムIDとアクションの間の区切り文字
     */
    private final String SEPARATOR_BETWEEN_ACT_PGID = "_";

    /**
     * 開始打刻
     */
    private final String ACT_EXEC_OPEN = "ACT_EXEC_OPEN";

    /**
     * 終了打刻
     */
    private final String ACT_EXEC_CLOSE = "ACT_EXEC_CLOSE";

    /**
     * 打刻対象外の戻り値
     */
    private final String NOT_TIMEPUNCH_TARGET = "0";

    /**
     * 休憩開始時間キー
     */
    private final String NOPEN_KEY = "NOPEN";

    /**
     * 　休憩終了時間キー
     */
    private final String NCLOSE_KEY = "NCLOSE";

    /**
     * 勤務日数　キー
     */
    private final String DUTYDAYS_KEY = "COL0";

    /**
     * 勤務時間　キー
     */
    private final String DUTYHOURS_KEY = "COL1";

    /**
     * パラメータを初期化する
     */
    public void setExecuteParameters(String pBaseDate, PsDBBean psDBBean) {
        if (null == pBaseDate || "".equals(pBaseDate)) {
            pBaseDate = DateUtil.format(new Date(), DATE_FORMAT);
        }
        this.psDBBean = psDBBean;
    }

    /**
     * 打刻時に打刻データ(未反映)情報に登録する
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param minDate
     * @param maxDate
     * @param psAction
     */
    private void insertTmgTimePunch(String custId, String compCode, String employeeId, String minDate, String maxDate, String psAction) {
        logger.info("打刻時に打刻データ(未反映)情報に登録する");
        String modifierprogramid = BEAN_DESC + SEPARATOR_BETWEEN_ACT_PGID + psAction;
        String ctpTypeid = this.getTptType(psAction);
        iTmgTimepunchService.insertTmgTimePunch(custId, compCode, employeeId, minDate, maxDate, modifierprogramid, ctpTypeid);
    }

    /**
     * TMG_TRIGGERへINSERTする
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param minDate
     * @param maxDate
     * @param psAction
     */
    private void insertTmgTrgger(String custId, String compCode, String employeeId, String minDate, String maxDate, String psAction) {
        logger.info("TMG_TRIGGERへINSERTする");
        String modifierprogramid = BEAN_DESC + SEPARATOR_BETWEEN_ACT_PGID + psAction;
        iTmgTimepunchService.insertTmgTrgger(custId, compCode, employeeId, minDate, maxDate, modifierprogramid);
    }

    /**
     * TMG_TRIGGERへDELETEする
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param psAction
     */
    private void deleteTmgTrgger(String custId, String compCode, String employeeId, String psAction) {
        logger.info("TMG_TRIGGERへDELETEする");
        String modifierprogramid = BEAN_DESC + SEPARATOR_BETWEEN_ACT_PGID + psAction;
        iTmgTimepunchService.deleteTmgTrgger(custId, compCode, employeeId, modifierprogramid);
    }

    /**
     * 打刻処理
     *
     * @param psAction ホムページから打刻の場合、ログインしない状況が可能ですから、このパラメータが必要です
     */
    @Transactional(rollbackFor = GlobalException.class)
    public boolean execTimePunch(String employeeId, String psAction) {
        logger.info("打刻タスクが始めます...");
        boolean result = false;
        String custId = psDBBean.getCustID();
        String compCode = psDBBean.getCompCode();
        if (null == employeeId || "".equals(employeeId)) {
            //ホムページから打刻の場合、ログインしない状況が可能ですから、このパラメータが必要です
            employeeId = psDBBean.getUserCode();
        }
        if (null == psAction || "".equals(psAction)) {
            if (null != psDBBean.getRequestHash().get("txtAction") || "".equals(psDBBean.getRequestHash().get("txtAction"))) {
                psAction = psDBBean.getRequestHash().get("txtAction").toString();
            } else {
                logger.warn("psDBBean中でtxtActionが空です");
            }
        }
        this.insertTmgTimePunch(custId, compCode, employeeId, Cs_MINDATE, Cs_MAXDATE, psAction);
        this.insertTmgTrgger(custId, compCode, employeeId, Cs_MINDATE, Cs_MAXDATE, psAction);
        this.deleteTmgTrgger(custId, compCode, employeeId, psAction);
        logger.info("打刻タスクがおまりました...");
        result = true;
        return result;
    }

    /**
     * 打刻画面表示判断
     *
     * @param employeeId ホムページから打刻の場合、ログインしない状況が可能ですから、このパラメータが必要です
     * @return
     */
    public boolean isNotTimePunch(String employeeId) {
        String custId = psDBBean.getCustID();
        String compCode = psDBBean.getCompCode();
        if (null == employeeId || "".equals(employeeId)) {
            employeeId = psDBBean.getUserCode();
        }
        String gsToday = "";

        String sType = psDBBean.getSystemProperty("TMG_TIMEPUNCH_TYPE");
        if (this.isEmpty(sType) || sType.equalsIgnoreCase("company")) {
            BaseTimesDTO baseTimesDTO = iTmgTimepunchService.selectBaseTimes(custId, compCode);
            // 更新条件用、更新値用の日付情報を作成する
            if (Integer.parseInt(baseTimesDTO.getSNow()) < Integer.parseInt(baseTimesDTO.getSTosStartMinutesday())) {
                // 本日日付 - 1日の日付を設定する
                gsToday = baseTimesDTO.getSYesterday();
            } else {
                // 本日日付を設定する
                gsToday = baseTimesDTO.getSToday();
            }
        } else {
            gsToday = iTmgTimepunchService.selectBaseTimesWithPattern(custId, compCode, employeeId);
        }

        String ret = iTmgTimepunchService.selectIsTimePunchTarget(custId, compCode, employeeId, gsToday);
        if (null == ret || "".equals(ret)) {
            return false;
        }

        return NOT_TIMEPUNCH_TARGET.equals(ret);
    }

    /**
     * 出勤日数  出勤時間  超過勤務時間   年次休暇  取得する
     *
     * @param baseDate 2020/06/26
     * @return
     */
    public DutyAndRelaxDateDTO getDutyAndRelaxDate(String baseDate) {
        DutyAndRelaxDateDTO dutyAndRelaxDateDTO = new DutyAndRelaxDateDTO();
        String custId = psDBBean.getCustID();
        String compCode = psDBBean.getCompCode();
        String employeeId = psDBBean.getUserCode();
        String language = psDBBean.getLanguage();

        // 月初
        String startDate = "";
        //　月末
        String endDate = "";
        if (null == baseDate || "".equals(baseDate)) {
            startDate = DateUtil.format(DateUtil.beginOfMonth(new Date()), DATE_FORMAT);
            endDate = DateUtil.format(DateUtil.endOfMonth(new Date()), DATE_FORMAT);
        } else {
            startDate = DateUtil.format(DateUtil.beginOfMonth(DateUtil.parse(baseDate, DATE_FORMAT)), DATE_FORMAT);
            endDate = DateUtil.format(DateUtil.endOfMonth(DateUtil.parse(baseDate, DATE_FORMAT)), DATE_FORMAT);
        }

        // 1.出勤日数   出勤時間
        HashMap<String, Object> result = this.selectDutyDaysAndHours(custId, compCode, employeeId, language, startDate);
        if (null != result) {
            //出勤日数
            if (null != result.get(DUTYDAYS_KEY)) {
                String dutyDays = result.get(DUTYDAYS_KEY).toString() + "日";
                dutyAndRelaxDateDTO.setDutyDates(dutyDays);
            }
            //出勤時間
            if (null != result.get(DUTYHOURS_KEY)) {
                String dutyHours = result.get(DUTYHOURS_KEY).toString();
                if (dutyHours.indexOf(":") > 0) {
                    dutyAndRelaxDateDTO.setDutyHours(dutyHours.substring(0, dutyHours.indexOf(":")) + "時" + dutyHours.substring(dutyHours.indexOf(":") + 1, dutyHours.length()) + "分");
                }
            }
        }

        // 2.超過勤務時間
        String overTime = this.selectOverTime(custId, compCode, employeeId, startDate, endDate);
        dutyAndRelaxDateDTO.setOverTime(overTime);

        // 3.年次休暇
        String npaidRestDaysHour = this.getNpaidRestDaysHour(employeeId, startDate, compCode, custId);
        dutyAndRelaxDateDTO.setNpaidRestDaysHour(npaidRestDaysHour);

        return dutyAndRelaxDateDTO;
    }

    /**
     * 予定時間
     *
     * @param targetDate
     * @return
     */
    public ScheduleInfoDTO selectScheduleInfo(String targetDate) {

        if (null == targetDate || "".equals(targetDate)) {
            targetDate = DateUtil.format(new Date(), "yyyy/MM/dd");
        }
        String custId = psDBBean.getCustID();
        String compCode = psDBBean.getCompCode();
        String employeeId = psDBBean.getUserCode();
        ScheduleInfoDTO scheduleInfoDTO = iTmgTimepunchService.selectScheduleInfo(custId, compCode, employeeId, targetDate);
        scheduleInfoDTO.setTda_cemployeeid(employeeId);
        String timerRange = scheduleInfoDTO.getTimerange();
        List<HashMap<String, Object>> restList = new ArrayList<HashMap<String, Object>>();
        if (null != timerRange && !"".equals(timerRange) && JSONUtil.isJson(timerRange)) {
            //チェック
            scheduleInfoDTO.setTimerange_arr(JSONUtil.parseArray(timerRange).toArray());
        } else {
            logger.warn("社員の休憩時間は取得してない");
        }
        return scheduleInfoDTO;
    }


    /**
     * 年次休暇残
     *
     * @param employeeId
     * @param targetDate
     * @param compCode
     * @param custId
     * @return
     */
    private String getNpaidRestDaysHour(String employeeId, String targetDate, String compCode, String custId) {
        NpaidRestDTO npaidRestDTO = iTmgScheduleService.selectTmgMonthly(employeeId, targetDate, compCode, custId);
        String npaidRestDaysHour = "0日0時0分";
        if (null != npaidRestDTO) {
            //年次休暇残
            npaidRestDaysHour = npaidRestDTO.getTmo_npaid_rest_days() == null ? "0" : npaidRestDTO.getTmo_npaid_rest_days() + "日";
            int hour = 0;
            int min = 0;
            String hoursMins = npaidRestDTO.getTmo_npaid_rest_hours();
            if (null != hoursMins && !"".equals(hoursMins)) {
                int hoursMins_int = Integer.parseInt(hoursMins);

                if (hoursMins_int % 60 == 0) {
                    hour = hoursMins_int / 60;
                } else {
                    hour = (hoursMins_int - hoursMins_int % 60) / 60;
                    min = hoursMins_int % 60;
                }
            }
            npaidRestDaysHour += hour + "時";
            npaidRestDaysHour += min + "分";
        } else {
            logger.warn("年次休暇残対象が空です");
        }
        return npaidRestDaysHour;
    }


    /**
     * 超過勤務時間
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param startDate  　　月初　　2020/04/01
     * @param endDate    　　月末　　2020/04/30
     * @return
     */
    private String selectOverTime(String custId, String compCode, String employeeId, String startDate, String endDate) {
        String result = "0時0分";
        String overTime = iTmgTimepunchService.selectOverTime(custId, compCode, employeeId, startDate, endDate);
        if (null != overTime && !"".equals(overTime)) {
            if ("0".equals(overTime)) {
                result = "0時0分";
            } else {
                result = overTime.substring(0, overTime.indexOf(":")) + "時" + overTime.substring(overTime.indexOf(":") + 1, overTime.length()) + "分";
            }
        } else {
            logger.warn("超過勤務時間が取得してない");
        }
        return result;
    }

    /**
     * 出勤日数と出勤時間
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param targetDate 2020/04/01
     * @return
     */
    private HashMap<String, Object> selectDutyDaysAndHours(String custId, String compCode, String employeeId, String language, String targetDate) {
        List<DutyDaysAndHoursDTO> dutyDaysAndHoursDTOS = iTmgTimepunchService.selectDutyDaysAndHoursSQL(custId, compCode, language);
        /**
         * COL0:  出勤日数
         * COL1:  出勤時間
         */
        for (int i = 0; i < dutyDaysAndHoursDTOS.size(); i++) {
            //エイリアスを追加する　
            DutyDaysAndHoursDTO dutyDaysAndHoursDTO = dutyDaysAndHoursDTOS.get(i);
            dutyDaysAndHoursDTO.setMgd_csql_alias(dutyDaysAndHoursDTO.getMgd_csql() + " as " + "COL" + i);
        }
        HashMap<String, Object> result = iTmgTimepunchService.selectDutyDaysAndHours(custId, compCode, employeeId, targetDate, dutyDaysAndHoursDTOS);
        return result;
    }

    /**
     * 文字列がNULLまたは空白である事を確認します。
     *
     * @param sString 文字列
     * @return boolean
     */
    private boolean isEmpty(String sString) {
        if (sString == null || "".equalsIgnoreCase(sString)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param psAction アクションＩＤ
     * @return String マスタID
     */
    private String getTptType(String psAction) {

        if (ACT_EXEC_OPEN.equals(psAction)) {
            // 出勤
            return TmgUtil.Cs_MGD_TMG_TPTYPE_01;
        } else {
            // 退勤
            return TmgUtil.Cs_MGD_TMG_TPTYPE_02;
        }

    }


    public static void main(String[] args) {

    }

}
