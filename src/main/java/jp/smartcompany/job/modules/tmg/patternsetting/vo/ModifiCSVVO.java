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
     *
     */
    private List lMsgList;
    /**
     *
     */
    private List lPatternList;

    private boolean[] insertFlg;

}
