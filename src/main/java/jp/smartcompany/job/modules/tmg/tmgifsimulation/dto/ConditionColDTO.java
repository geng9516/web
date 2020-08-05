package jp.smartcompany.job.modules.tmg.tmgifsimulation.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description 条件コラム
 * @objectSource
 * @date 2020/07/29
 **/
@Setter
@Getter
@ToString
public class ConditionColDTO {

    /**
     * コラム標識
     */
    private String mgd_excludecond_type;
    /**
     * コラム名称
     */
    private String mgd_excludecond_type_name;
    /**
     * 長さ約束
     */
    private String mgd_excludecond_length;

    /**
     * コラム値
     */
    private String value;

}
