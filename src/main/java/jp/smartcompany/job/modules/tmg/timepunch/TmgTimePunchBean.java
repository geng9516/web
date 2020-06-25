package jp.smartcompany.job.modules.tmg.timepunch;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.job.modules.core.service.ITmgTimepunchService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.timepunch.dto.BaseTimesDTO;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
    private PsDBBean psDBBean;
    private final String Cs_MINDATE = "1900/01/01";
    private final String Cs_MAXDATE = "2222/12/31";
    private final String BEAN_DESC = "TimePunch";
    // プログラムIDとアクションの間の区切り文字
    private final String SEPARATOR_BETWEEN_ACT_PGID = "_";
    // 開始打刻
    private final String ACT_EXEC_OPEN = "ACT_EXEC_OPEN";
    // 終了打刻
    private final String ACT_EXEC_CLOSE = "ACT_EXEC_CLOSE";
    /**
     * 打刻対象外の戻り値
     */
    public static final String NOT_TIMEPUNCH_TARGET = "0";

    /**
     * パラメータを初期化する
     */
    public void setExecuteParameters(String pBaseDate, PsDBBean psDBBean) {
        if (null == pBaseDate || "".equals(pBaseDate)) {
            pBaseDate = DateUtil.format(new Date(), "yyyy/MM/dd");
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

}
