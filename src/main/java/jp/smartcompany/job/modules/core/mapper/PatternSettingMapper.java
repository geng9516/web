package jp.smartcompany.job.modules.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.PatternSetting.dto.TmgPatternAppliesDTO;
import jp.smartcompany.job.modules.tmg.PatternSetting.dto.TmgPatternDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * @author 陳毅力
 * @description 勤務パターン設定
 * @date 2020/06/12
 **/
@Mapper
public interface PatternSettingMapper extends BaseMapper<Object> {

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
    TmgPatternDTO selectTmgPatternOwn(HashMap<String, Object> params);

    /**
     * 該当者毎に設定されている勤務パターンの情報を取得する
     *
     * @param params
     * @return
     */
    List<TmgPatternAppliesDTO> selectTmgPatternApplies(HashMap<String, Object> params);




}
