package jp.smartcompany.job.modules.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.PatternSetting.dto.*;

import java.util.HashMap;
import java.util.List;


/**
 * @author 陳毅力
 * @description 勤務パターン設定
 * @date 2020/05/25
 **/
public interface IPatternSettingService extends IService<Object> {

    /**
     * TMG_PATTERNより利用可能な勤務パターンを取得する
     *
     * @param custId
     * @param compCode
     * @param groupId
     * @return
     */
    List<TmgPatternDTO> selectTmgPattern(String custId, String compCode, String groupId);

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


}
