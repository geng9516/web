package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgTimepunchDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.timepunch.dto.BaseTimesDTO;

import java.util.HashMap;

/**
 * [勤怠]打刻
 */
public interface ITmgTimepunchService extends IService<TmgTimepunchDO> {

    /**
     * 打刻時に打刻データ(未反映)情報に登録する
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param minDate
     * @param maxDate
     * @param modifierprogramid
     * @param ctpTypeid
     */
    void insertTmgTimePunch(String custId, String compCode, String employeeId, String minDate, String maxDate, String modifierprogramid, String ctpTypeid);

    /**
     * TMG_TRIGGERへINSERTする
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param minDate
     * @param maxDate
     * @param modifierprogramid
     */
    void insertTmgTrgger(String custId, String compCode, String employeeId, String minDate, String maxDate, String modifierprogramid);

    /**
     * TMG_TRIGGERへDELETEする
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param modifierprogramid
     */
    void deleteTmgTrgger(String custId, String compCode, String employeeId, String modifierprogramid);

    /**
     * 打刻画面表示判断
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param targetDate
     * @return
     */
    String selectIsTimePunchTarget(String custId, String compCode, String employeeId, String targetDate);


    /**
     * 本日の日付情報と、法人情報(TMG_COMPANY)の開始時刻を取得します
     *
     * @param custId
     * @param compCode
     * @return
     */
    BaseTimesDTO selectBaseTimes(String custId, String compCode);

    /**
     * 打刻更新先となる日と今日の日付を取得するクエリ
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @return
     */
    String selectBaseTimesWithPattern(String custId, String compCode, String employeeId);

}
