package jp.smartcompany.job.modules.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.core.pojo.entity.TmgPatternDO;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.*;

import java.util.HashMap;
import java.util.List;


/**
 * @author 陳毅力
 * @description 勤務パターン設定
 * @date 2020/05/25
 **/
public interface IPatternSettingService extends IService<TmgPatternDO> {

    /**
     * TMG_PATTERNより利用可能な勤務パターンを取得する
     *
     * @param custId
     * @param compCode
     * @param sectionId
     * @return
     */
    List<TmgPatternDTO> selectTmgPattern(String custId, String compCode, String sectionId,String groupId);

    /**
     * TMG_PATTERNより利用可能な勤務パターンを取得する(自組織分)
     *
     * @param custId
     * @param compCode
     * @param groupId
     * @param sectionId
     * @return
     */
    List<TmgPatternDTO> selectTmgPatternOwn(String custId, String compCode, String groupId, String sectionId);

    /**
     * 該当者毎に設定されている勤務パターンの情報を取得する
     *
     * @param baseDate
     * @param targetEmployees
     * @param language
     * @return
     */
    List<TmgPatternAppliesDTO> selectTmgPatternApplies(String baseDate, String targetEmployees, String language);

    /**
     * 勤務時間・休憩時間の制限値取得
     *
     * @param custId
     * @param compCode
     * @param language
     * @return
     */
    List<RestTimeLimitDTO> selectRestTimeLimit(String custId, String compCode, String language);

    /**
     * パターン編集画面のパターン情報
     *
     * @param custId
     * @param compCode
     * @param groupId
     * @param patternId
     * @return
     */
    TmgPatternDTO selectTmgPatternById(String custId, String compCode, String groupId, String patternId);

    /**
     * 編集画面の翌日勤務パターンリスト
     *
     * @param custId
     * @param compCode
     * @param groupId
     * @param patternId
     * @return
     */
    List<TmgPatternDTO> selectPatternSelectList(String custId, String compCode, String groupId, String patternId);

    /**
     * DELETE TMG_PATTERN
     *
     * @param custId
     * @param compCode
     * @param groupId
     * @param patternId
     */
    void deleteTmgPattern(String custId, String compCode, String groupId, String sectionId, String patternId);

    /**
     * DELETE TMG_PATTERN_REST
     *
     * @param custId
     * @param compCode
     * @param groupId
     * @param patternId
     */
    void deleteTmgPatternRest(String custId, String compCode, String groupId, String sectionId, String patternId);

    /**
     * DELETE TMG_PATTERN_APPLIES
     *
     * @param custId
     * @param compCode
     * @param groupId
     * @param patternId
     */
    void deleteTmgPatternApplies(String custId, String compCode, String groupId, String sectionId, String patternId);

    /**
     * ディフォルトセット
     *
     * @param custId
     * @param compCode
     * @param groupId
     * @param sectionId
     * @param employeeId
     * @param modifierprogramid
     */
    void updateTmgPattern(String custId, String compCode, String groupId, String sectionId, String employeeId, String modifierprogramid);

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
     * @param compCode
     * @param patternId
     * @return
     */
    String checkPatternId(String compCode, String patternId);

    

}
