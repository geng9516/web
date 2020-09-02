package jp.smartcompany.job.modules.tmg.schedule.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description 予定作成年月リスト
 * @objectSource
 * @date 2020/09/02
 **/
@Setter
@Getter
@ToString
public class ScheduleDateListVO {

    private String tda_dyyyymm;
    private String flag;

}
