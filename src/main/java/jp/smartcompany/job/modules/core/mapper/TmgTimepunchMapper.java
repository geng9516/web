package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgTimepunchDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.timepunch.dto.BaseTimesDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

/**
 * [勤怠]打刻データMapper 接口
 */
@Mapper
public interface TmgTimepunchMapper extends BaseMapper<TmgTimepunchDO> {

    /**
     * 打刻時に打刻データ(未反映)情報に登録する
     *
     * @param params
     */
    void insertTmgTimePunch(HashMap<String, Object> params);

    /**
     * TMG_TRIGGERへINSERTする
     *
     * @param params
     */
    void insertTmgTrgger(HashMap<String, Object> params);

    /**
     * TMG_TRIGGERへDELETEする
     *
     * @param params
     */
    void deleteTmgTrgger(HashMap<String, Object> params);

    /**
     * 打刻画面表示判断
     *
     * @param params
     * @return
     */
    String selectIsTimePunchTarget(HashMap<String, Object> params);

    /**
     * 本日の日付情報と、法人情報(TMG_COMPANY)の開始時刻を取得します
     *
     * @param params
     * @return
     */
    BaseTimesDTO selectBaseTimes(HashMap<String, Object> params);

    /**
     * 打刻更新先となる日と今日の日付を取得するクエリ
     *
     * @param params
     * @return
     */
    String selectBaseTimesWithPattern(HashMap<String, Object> params);

}
