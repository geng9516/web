package jp.smartcompany.job.modules.tmg.patternsetting.vo;

import jp.smartcompany.job.modules.tmg.patternsetting.dto.TmgPatternDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;

/**
 * @author NSC
 * @description 勤務パターン適用VO
 * @objectSource
 * @date 2021/02/02
 **/
@Getter
@Setter
@ToString
public class TmgPatternSettingAppliesVO {
    /**
     * 基本情報
     */
    private String txtAction;
    /**
     * 基本情報
     */
    private String sectionId;
    /**
     * 基本情報
     */
    private String groupId;
    /**
     * 基本情報
     */
    private String txtExecuteEmpId;
    /**
     * 基本情報
     */
    private String txtexecutePatternId;
    /**
     * 基本情報
     */
    private String txtexecuteDate;
}
