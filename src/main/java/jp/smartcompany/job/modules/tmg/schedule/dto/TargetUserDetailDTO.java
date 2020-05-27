package jp.smartcompany.job.modules.tmg.schedule.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description 対象ユーザー情報
 * @objectSource
 * @date 2020/05/26
 **/
@Getter
@Setter
@ToString
public class TargetUserDetailDTO {

    private String name;
    private String dept;
    private String workType;
    private String workTypeCode;
    private String posi;

}
