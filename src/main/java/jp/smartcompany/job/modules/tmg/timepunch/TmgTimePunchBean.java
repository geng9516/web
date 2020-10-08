package jp.smartcompany.job.modules.tmg.timepunch;

import cn.hutool.cache.impl.LRUCache;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.SpringUtil;
import jp.smartcompany.job.modules.core.service.ITmgDailyService;
import jp.smartcompany.job.modules.core.service.ITmgScheduleService;
import jp.smartcompany.job.modules.core.service.ITmgTimepunchService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.attendanceBook.AttendanceBookBean;
import jp.smartcompany.job.modules.tmg.schedule.dto.NpaidRestDTO;
import jp.smartcompany.job.modules.tmg.timepunch.dto.BaseTimesDTO;
import jp.smartcompany.job.modules.tmg.timepunch.dto.DutyAndRelaxDateDTO;
import jp.smartcompany.job.modules.tmg.timepunch.dto.DutyDaysAndHoursDTO;
import jp.smartcompany.job.modules.tmg.timepunch.dto.ScheduleInfoDTO;
import jp.smartcompany.job.modules.tmg.timepunch.vo.ClockInfoVO;
import jp.smartcompany.job.modules.tmg.timepunch.vo.ClockResultVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DetailOverhoursVO;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 陳毅力
 * @description 打刻システム
 * @objectSource ps.c01.tmg.TmgTimePunch.TmgTimePunchBean
 * @date 2020/06/25
 **/
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class TmgTimePunchBean {
    private final Logger logger = LoggerFactory.getLogger(TmgTimePunchBean.class);
    private final ITmgTimepunchService iTmgTimepunchService;
    private final ITmgScheduleService iTmgScheduleService;
    private final AttendanceBookBean attendanceBookBean;
    private final ITmgDailyService iTmgDailyService;
    private PsDBBean psDBBean;
    //private LRUCache<Object, Object> lruCache = (LRUCache<Object, Object>) SpringUtil.getBean("scCache");

    private final String Cs_MINDATE = "1900/01/01";
    private final String Cs_MAXDATE = "2222/12/31";
    private final String BEAN_DESC = "TimePunch";
    private final String DATE_FORMAT = "yyyy/MM/dd";
    private final String siteId = "TMG_INP";
    private final String CACHE_OVERWORK = "CACHE_OVERWORK";
    private final String CACHE_SCHEDULETIME = "CACHE_SCHEDULETIME";
    private final String CACHE_DUTYANDRELAXTIME = "CACHE_DUTYANDRELAXTIME";
    private final String CACHE_SCHEDULEINFO = "CACHE_SCHEDULEINFO";

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
     * 打刻反映処理
     */
    private final String Cn_PHASE_SET_TIMEPUNCH = "500";

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
        log.info("打刻時に打刻データ(未反映)情報に登録する");
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
        log.info("TMG_TRIGGERへINSERTする");
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
        log.info("TMG_TRIGGERへDELETEする");
        String modifierprogramid = BEAN_DESC + SEPARATOR_BETWEEN_ACT_PGID + psAction;
        iTmgTimepunchService.deleteTmgTrgger(custId, compCode, employeeId, modifierprogramid);
    }

    /**
     * 打刻処理
     *
     * @param psAction ホムページから打刻の場合、ログインしない状況が可能ですから、このパラメータが必要です
     */
    @Transactional(rollbackFor = GlobalException.class)
    public boolean clock(String employeeId, String custId, String compId, String psAction) {
        boolean result = false;
        //lock thread safe
        synchronized (this) {
            this.insertTmgTimePunch(custId, compId, employeeId, Cs_MINDATE, Cs_MAXDATE, psAction);
            this.insertTmgTrgger(custId, compId, employeeId, Cs_MINDATE, Cs_MAXDATE, psAction);
            this.deleteTmgTrgger(custId, compId, employeeId, psAction);
        }
        result = true;
        return result;
    }

    /**
     * 打刻処理
     *
     * @param employeeId
     * @param custId
     * @param compId
     * @param psAction
     * @return
     */
    public ClockResultVO execTimePunch(String employeeId, String custId, String compId, String psAction) {
        log.info("打刻タスクが始めます...");
        ClockResultVO clockResultVO = new ClockResultVO();
        String resultMsg = "";
        String resultCode = "10";
        boolean isPass = true;

        //1、パラメータをチェックする
        logger.info("打刻ユーザー：" + employeeId);
        if (null == employeeId || "".equals(employeeId)) {
            isPass = false;
            resultMsg = "社員番号が空です";
            log.warn(resultMsg);
        }
        if (null == custId || "".equals(custId)) {
            isPass = false;
            resultMsg = "顧客番号が空です";
            log.warn(resultMsg);
        }
        if (null == compId || "".equals(compId)) {
            isPass = false;
            resultMsg = "会社コードが空です";
            log.warn(resultMsg);
        }
        if (null == psAction || "".equals(psAction)) {
            isPass = false;
            resultMsg = "打刻タイプが空です";
            log.warn(resultMsg);
        }
        if (!psAction.equals(ACT_EXEC_OPEN) && !psAction.equals(ACT_EXEC_CLOSE)) {
            isPass = false;
            resultMsg = "打刻タイプが不正です";
            log.warn(resultMsg);
        }
        if (isPass) {
            //2、打刻をしますかとしないか
            String gsToday = this.getTimePunchday(custId, compId, employeeId);
            boolean isVIP = this.isNotTimePunch(custId, compId, employeeId, gsToday);
            String clockTime = "";
            if (!isVIP) {
                //3、打刻する
                boolean flag = this.clock(employeeId, custId, compId, psAction);
                clockTime = DateUtil.format(new Date(), "HH:mm");
                clockResultVO.setClockTime(clockTime);
                if (flag) {
                    //4、打刻結果メッセージを取得する
                    String checkMsgJson = this.getCheckMsg(custId, compId, employeeId);
                    if (null != checkMsgJson && !"".equals(checkMsgJson)) {
                        //結果メッセージはjsonフォマードですから、解析が必要です
                        log.info("checkMsgJson:" + checkMsgJson);
                        if (JSONUtil.isJson(checkMsgJson)) {
                            JSONObject jsonObject = JSONUtil.parseObj(checkMsgJson);
                            if (null != jsonObject.get("ERRCODE")) {
                                resultCode = jsonObject.get("ERRCODE").toString();
                            }
                            if (null != jsonObject.get("ERRMSG")) {
                                resultMsg = jsonObject.get("ERRMSG").toString();
                            }
                        } else {
                            resultCode = "0";
                        }
                    } else {
                        resultCode = "0";
                    }
                } else {
                    resultCode = "30";
                    resultMsg = "打刻時に、内部エラーが発生しました";
                }

            } else {
                //打刻しない
                resultMsg = "当社員が打刻しない";
            }
        }

        clockResultVO.setCompanyId(compId);
        clockResultVO.setCustomerId(custId);
        clockResultVO.setEmployeeId(employeeId);
        clockResultVO.setResultCode(resultCode);
        clockResultVO.setResultMsg(resultMsg);

        log.info("打刻タスクが終わりました...");
        return clockResultVO;
    }

    /**
     * 打刻画面表示判断（打刻しない場合、打刻画面を表示しない」）
     *
     * @param employeeId ホムページから打刻の場合、ログインしない状況が可能ですから、このパラメータが必要です
     * @return
     */
    public boolean isNotTimePunch(String custId, String compCode, String employeeId, String gsToday) {

        if (null != gsToday) {
            log.warn("打刻更新日が取得していない");
            return false;
        }
        String ret = iTmgTimepunchService.selectIsTimePunchTarget(custId, compCode, employeeId, gsToday);
        if (null == ret || "".equals(ret)) {
            return false;
        }
        return NOT_TIMEPUNCH_TARGET.equals(ret);
    }

    /**
     * 打刻画面表示判断（打刻しない場合、打刻画面を表示しない」）
     *
     * @param employeeId ホムページから打刻の場合、ログインしない状況が可能ですから、このパラメータが必要です
     * @return
     */
    public boolean isNotTimePunch(String custId, String compCode, String employeeId) {
        String gsToday = this.getTimePunchday(custId, compCode, employeeId);
        if (null != gsToday) {
            log.warn("打刻更新日が取得していない");
            return false;
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

       /* DutyAndRelaxDateDTO dutyAndRelaxDateDTO = (DutyAndRelaxDateDTO) lruCache.get(CACHE_DUTYANDRELAXTIME);
        if (null == dutyAndRelaxDateDTO) { */
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
           /* if (null != result.get(DUTYHOURS_KEY)) {
                String dutyHours = result.get(DUTYHOURS_KEY).toString();
                if (dutyHours.indexOf(":") > 0) {
                    dutyAndRelaxDateDTO.setDutyHours(dutyHours.substring(0, dutyHours.indexOf(":")) + "時" + dutyHours.substring(dutyHours.indexOf(":") + 1, dutyHours.length()) + "分");
                }
            }*/
        }
        String dutyHours = attendanceBookBean.selectWorkTime(psDBBean);
        dutyAndRelaxDateDTO.setDutyHours(dutyHours);

        // 2.超過勤務時間
        String overTime = this.selectOverTime(custId, compCode, employeeId, startDate, endDate);
        dutyAndRelaxDateDTO.setOverTime(overTime);

        // 3.年次休暇
        String npaidRestDaysHour = this.getNpaidRestDaysHour(employeeId, startDate, compCode, custId);
        dutyAndRelaxDateDTO.setNpaidRestDaysHour(npaidRestDaysHour);
        // lruCache.put(CACHE_DUTYANDRELAXTIME, dutyAndRelaxDateDTO);
        // logger.info("[出勤日数  出勤時間  超過勤務時間   年次休暇] Cache までロードする");
        return dutyAndRelaxDateDTO;
        /*} else {
            logger.info("[出勤日数  出勤時間  超過勤務時間   年次休暇] Cache から取り出す");
            return dutyAndRelaxDateDTO;
        }*/
    }

    /**
     * 予定時間
     *
     * @param targetDate
     * @return
     */
    public ScheduleInfoDTO selectScheduleInfo(String targetDate) {
      /*  ScheduleInfoDTO scheduleInfoDTO = (ScheduleInfoDTO) lruCache.get(CACHE_SCHEDULEINFO);
        if (null == scheduleInfoDTO) {*/
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
            log.warn("社員の休憩時間は取得してない");
        }
        // lruCache.put(CACHE_SCHEDULEINFO, scheduleInfoDTO);
        // logger.info("[予定時間] Cache までロードする");
        return scheduleInfoDTO;
        /*} else {
            logger.info("[予定時間] Cache から取り出す");
            return scheduleInfoDTO;
        }*/
    }

    /**
     * 予定データのフォマードを変更する
     *
     * @param scheduleInfoDTO
     * @return
     */
    public Object[] scheduleInfoConverse(ScheduleInfoDTO scheduleInfoDTO) {
        if (null != scheduleInfoDTO) {
            if (null != scheduleInfoDTO.getTda_nopen_p() && !"".equals(scheduleInfoDTO.getTda_nopen_p())
                    && null != scheduleInfoDTO.getTda_nclose_p() && !"".equals(scheduleInfoDTO.getTda_nclose_p())) {
                Vector vector = new Vector();
                //出勤時間
                String tda_nopen_p = scheduleInfoDTO.getTda_nopen_p();
                vector.add(Integer.parseInt(tda_nopen_p));
                //退勤時間
                String tda_nclose_p = scheduleInfoDTO.getTda_nclose_p();
                vector.add(Integer.parseInt(tda_nclose_p));
                //休憩時間
                Object[] restTimes = scheduleInfoDTO.getTimerange_arr();
                JSONObject jsonObject = null;
                for (Object rest : restTimes) {
                    jsonObject = JSONUtil.parseObj(rest);
                    if (null != jsonObject.get("NOPEN") && null != jsonObject.get("NCLOSE")) {
                        vector.add(Integer.parseInt(jsonObject.get("NOPEN").toString()));
                        vector.add(Integer.parseInt(jsonObject.get("NCLOSE").toString()));
                    }
                }
                //並び順
                List<Integer> result_sorted = (List<Integer>) vector.stream().sorted().collect(Collectors.toList());
                //時間フォーマットに変更する
                List<String> result_conver = new ArrayList<String>();
                for (int i = 0; i < result_sorted.size(); i++) {
                    Integer o = result_sorted.get(i);
                    result_conver.add(this.formatMinuteToDispTime(o));
                }
                //最終のデータに変更する
                Object[] result = this.scheduleDataResult(result_conver);
                return result;
            } else {
                logger.warn("出勤予定時間又は退勤予定時間が空です");
                return null;
            }
        } else {
            logger.warn("予定データが空です");
            return null;
        }
    }

    /**
     * 分数を表示形式時刻(**:**)に変換します
     *
     * @param minute 分数
     * @return String 表示形式時刻
     */
    private String formatMinuteToDispTime(int minute) {
        // 時間数と分数を求める
        int hour = (minute - (minute % 60)) / 60;
        int min = minute % 60;
        String sDispTime = new String("00" + min);
        return hour + ":" + (sDispTime.substring(sDispTime.length() - 2));
    }

    /**
     * list --> String[]
     *
     * @param result_conver 　並び順後データ
     * @return
     */
    private Object[] scheduleDataResult(List<String> result_conver) {
        Vector<String> vector = new Vector<String>();
        StringBuffer sb = new StringBuffer();
        if (null != result_conver) {
            for (int i = 1; i <= result_conver.size(); i++) {
                String o = result_conver.get(i - 1);
                if (i % 2 != 0) {
                    //奇数なると、容器まで追加する
                    vector.add(o + "-" + result_conver.get(i));
                } else {
                    //偶数なると、スキップする
                    continue;
                }
            }
            Object[] result = vector.toArray();
            return result;
        } else {
            logger.warn("予定データが空です");
            return null;
        }
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
            log.warn("年次休暇残対象が空です");
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
            log.warn("超過勤務時間が取得してない");
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

    /**
     * 打刻更新日を取得し返却する
     *
     * @return String 打刻更新日
     * @throws Exception
     */
    private String getTimePunchday(String custId, String compCode, String employeeId) {
        String gsToday = null;
        String sToday = "";
        String sYesterday = "";
        String sNow = "";
        String sStartMinutes = "";
        String sType = "";
        if (null != psDBBean) {
            sType = psDBBean.getSystemProperty("TMG_TIMEPUNCH_TYPE");
        } else {
            sType = "employees";
        }
        if (this.isEmpty(sType) || sType.equalsIgnoreCase("company")) {
            BaseTimesDTO baseTimesDTO = iTmgTimepunchService.selectBaseTimes(custId, compCode);
            if (null != baseTimesDTO) {
                sToday = baseTimesDTO.getSToday();
                sYesterday = baseTimesDTO.getSYesterday();
                sNow = baseTimesDTO.getSNow();
                sStartMinutes = baseTimesDTO.getSStartMinutes();
                // 更新条件用、更新値用の日付情報を作成する
                if (Integer.parseInt(sNow) < Integer
                        .parseInt(sStartMinutes)) {
                    // 本日日付 - 1日の日付を設定する
                    gsToday = sYesterday;
                } else {
                    // 本日日付を設定する
                    gsToday = sToday;
                }
            } else {
                log.warn("本日の日付情報と、法人情報(TMG_COMPANY)の開始時刻を取得ことが失敗しました");
            }
        } else {
            gsToday = iTmgTimepunchService.selectBaseTimesWithPattern(custId, compCode, employeeId);
        }
        return gsToday;
    }

    /**
     * エラーチェック結果の取得
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @return errMsg JSON 文字
     */
    private String getCheckMsg(String custId, String compCode, String employeeId) {
        String targetDate = this.getTimePunchday(custId, compCode, employeeId);
        String checkMsg = "";
        if (null != targetDate) {
            checkMsg = iTmgTimepunchService.selectErrMsg(employeeId, targetDate, this.Cn_PHASE_SET_TIMEPUNCH, custId, compCode);
        } else {
            log.warn("打刻更新日を取得しない");
        }
        return checkMsg;
    }

    /**
     * 打刻と予定データを取得する
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @return
     */
    public ClockInfoVO selectClockInfo(String custId, String compCode, String employeeId) {
        /*ClockInfoVO clockInfoVO = (ClockInfoVO) lruCache.get(CACHE_SCHEDULETIME);
        if (null == clockInfoVO) {*/
        ClockInfoVO clockInfoVO = iTmgTimepunchService.selectClockInfo(custId, compCode, employeeId);
        // lruCache.put(CACHE_SCHEDULETIME, clockInfoVO);
        //logger.info("[打刻と予定データ] Cache までロードする");
        return clockInfoVO;
        /*} else {
            logger.info("[打刻と予定データ] Cache から取り出す");
            return clockInfoVO;
        }*/

    }

    /**
     * 超過勤務時間
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @return
     */
    public String[] selectOverWorkTime(String custId, String compCode, String employeeId) {
        List<DetailOverhoursVO> detailOverhoursVOList = iTmgDailyService.buildSQLForSelectDetailOverhours(custId, compCode, employeeId, siteId, DateUtil.format(new Date(), "yyyy/MM/dd"), "ja", true);
        String[] overWorkTime = new String[detailOverhoursVOList.size()];
        String timeTmp = "";
        for (int i = 0; i < detailOverhoursVOList.size(); i++) {
            DetailOverhoursVO detailOverhoursVO = detailOverhoursVOList.get(i);
            timeTmp = detailOverhoursVO.getTdadNopen() + "-" + detailOverhoursVO.getTdadNclose();
            overWorkTime[i] = timeTmp;
        }
        return overWorkTime;
    }

    /**
     * 社員の時間帯時間を取得する
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @return
     */
    public Integer[] getEmpScheduleSection(String custId, String compCode, String employeeId) {
        //単位：分
        int changeTime = 0;
        try {
            changeTime = iTmgTimepunchService.selectEmpPattern(custId, compCode, employeeId);
        } catch (Exception e) {
            logger.error("社員の時間帯時間を取得することが失敗しました", e);
            //失敗の場合、変更時間を初期化になる
            changeTime = 300;
        }
        if (changeTime == 0) {
            changeTime = 300;
        }
        logger.info("社員「" + employeeId + "」の勤務変更時間は「" + changeTime + "」です");
        int startTime = changeTime / 60;
        Integer[] schSection = new Integer[]{startTime, startTime + 24};

        return schSection;
    }


}
