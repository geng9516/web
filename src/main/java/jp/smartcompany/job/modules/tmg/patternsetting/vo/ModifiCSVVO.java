package jp.smartcompany.job.modules.tmg.patternsetting.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author 陳毅力
 * @description 取り込み対象VO
 * @objectSource
 * @date 2020/06/18
 **/
@Getter
@Setter
@ToString
public class ModifiCSVVO {

    /**
     * グループ
     */
    private String sGroupId;

    /**
     * 部局(組織)
     */
    private String sSectionId;

    /**
     * 取り込み結果メッセージ
     */
    private List lMsgList;

    /**
     * 　データリスト
     */
    private List lPatternList;

    /**
     * データのごとの取り込み結果
     */
    private boolean[] insertFlg;

}
