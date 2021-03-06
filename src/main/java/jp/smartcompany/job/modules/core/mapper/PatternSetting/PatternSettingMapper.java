package jp.smartcompany.job.modules.core.mapper.PatternSetting;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.core.pojo.entity.TmgPatternDO;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * @author 陳毅力
 * @description 勤務パターン設定
 * @date 2020/06/12
 **/
@Mapper
public interface PatternSettingMapper extends BaseMapper<TmgPatternDO> {

    /**
     * TMG_PATTERNより利用可能な勤務パターンを取得する
     *
     * @param params
     * @return
     */
    List<TmgPatternDTO> selectTmgPattern(HashMap<String, Object> params);

    /**
     * TMG_PATTERNより利用可能な勤務パターンを取得する(自組織分)
     *
     * @param params
     * @return
     */
    List<TmgPatternDTO> selectTmgPatternOwn(HashMap<String, Object> params);

    /**
     * 該当者毎に設定されている勤務パターンの情報を取得する
     *
     * @param params
     * @return
     */
    List<TmgPatternAppliesDTO> selectTmgPatternApplies(HashMap<String, Object> params);

    /**
     * 勤務時間・休憩時間の制限値取得
     *
     * @param params
     */
    List<RestTimeLimitDTO> selectRestTimeLimit(HashMap<String, Object> params);

    /**
     * パターン編集画面のパターン情報
     *
     * @param params
     * @return
     */
    TmgPatternDTO selectTmgPatternById(HashMap<String, Object> params);

    /**
     * パターン編集画面のパターン情報
     *
     * @param params
     * @return
     */
    List<TmgPatternDTO> selectPatternSelectList(HashMap<String, Object> params);

    /**
     * DELETE TMG_PATTERN
     *
     * @param params
     */
    void deleteTmgPattern(HashMap<String, Object> params);

    /**
     * DELETE TMG_PATTERN_REST
     *
     * @param params
     */
    void deleteTmgPatternRest(HashMap<String, Object> params);

    /**
     * DELETE TMG_PATTERN_APPLIES
     *
     * @param params
     */
    void deleteTmgPatternApplies(HashMap<String, Object> params);

    /**
     * ディフォルトセット
     *
     * @param params
     */
    void updateTmgPattern(HashMap<String, Object> params);

    /**
     * 勤務パターン　保存する
     *
     * @param tmgPatternInsertDTO
     */
    void insertTmgPattern(TmgPatternInsertDTO tmgPatternInsertDTO);

    /**
     * 勤務パターン休憩時間　保存する
     */
    void insertTmgPatternRestPlural(TmgPatternRestDTO tmgPatternRestDTO);

    /**
     * 勤務パターンを適用可能な最少日付を取得
     *
     * @return
     */
    String selectEditPeriodDate();

    /**
     * 時刻の規定範囲を取得します
     *
     * @return
     */
    HashMap<String, String> selectDayOpenClose();

    /**
     * 指定された勤務パターンIDがDBに存在している場合
     *
     * @return
     */
    String checkPatternId(HashMap<String, Object> params);

    /**
     * 文言：勤務パターンを適用しないを取得する
     *
     * @param params
     * @return
     */
    String selectTmgNoPtnAppliesName(HashMap<String, Object> params);

    /**
     * 勤務パターン適用のトリガー
     *
     * @param params
     * @return
     */
    int insertTrigger(HashMap<String, Object> params);

    /**
     * 勤務パターン適用項目チェックのインサート
     *
     * @param params
     * @return
     */
    int insertTmgPatternAppliesCheck(HashMap<String, Object> params);

    /**
     * DELETE TMG_PATTERN_APPLIES_CHECK
     *
     * @param params
     */
    int deleteTmgPatternAppliesCheck(HashMap<String, Object> params);
}
