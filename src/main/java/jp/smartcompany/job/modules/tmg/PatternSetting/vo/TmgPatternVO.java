package jp.smartcompany.job.modules.tmg.PatternSetting.vo;

import jp.smartcompany.job.modules.tmg.PatternSetting.dto.TmgPatternDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;

/**
 * @author 陳毅力
 * @description 編集画面パターンVO
 * @objectSource
 * @date 2020/06/15
 **/
@Getter
@Setter
@ToString
public class TmgPatternVO {
    /**
     * 基本情報
     */
    private TmgPatternDTO tmgPatternDTO;
    /**
     * パターンリスト
     */
    private List<HashMap<String, String>> patternList;

}
